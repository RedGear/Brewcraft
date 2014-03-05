package redgear.brewcraft.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import redgear.core.world.WorldLocation;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BrewcraftEventHandler {

	private static BrewcraftEventHandler instance;

	private BrewcraftEventHandler() {

	}

	public static BrewcraftEventHandler create() {
		if (instance == null) {
			instance = new BrewcraftEventHandler();
			MinecraftForge.EVENT_BUS.register(instance);

		}
		return instance;
	}

	@SubscribeEvent
	public void checkBottledFire(final EntityInteractEvent event) {

		if (event.entityLiving instanceof EntityBlaze || event.entityLiving instanceof EntityMagmaCube)
			if (Brewcraft.emptyBottle.equals(event.entityPlayer.getHeldItem())) {

				event.entityPlayer.inventory.consumeInventoryItem(Brewcraft.emptyBottle.getItem());
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 0));
				event.entityLiving.attackEntityFrom(DamageSource.magic, 2.5F);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "mob.magmacube.jump", 1F, 1F);

			}

		if (Brewcraft.splashBottle.equals(event.entityPlayer.getHeldItem())) {

			if (event.entityLiving instanceof EntityGhast) {

				event.entityPlayer.inventory.consumeInventoryItem(Items.glass_bottle);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 1));
				event.entityLiving.attackEntityFrom(DamageSource.cactus, 30F);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "mob.ghast.death", 1F, 1F);

			}

			if (event.entityLiving instanceof EntityBlaze || event.entityLiving instanceof EntityMagmaCube) {

				event.entityPlayer.inventory.consumeInventoryItem(Items.glass_bottle);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 1));
				event.entityLiving.attackEntityFrom(DamageSource.magic, 2.5F);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "mob.magmacube.jump", 1F, 1F);

			}

		}

	}

	@SubscribeEvent
	public void editDrops(final LivingDropsEvent event) {
		final Random rand = event.entity.worldObj.rand;

		if (event.entity instanceof EntityGhast && event.source.equals(DamageSource.cactus))
			event.setCanceled(true);

		if (event.entity instanceof EntitySkeleton) {

			final EntitySkeleton skeleton = (EntitySkeleton) event.entity;

			if (skeleton.getSkeletonType() == 1 && rand.nextDouble() < 0.6D
					&& event.source.getDamageType().equals("player")) {

				skeleton.entityDropItem(
						Brewcraft.charredBone.getStack(event.entityLiving.worldObj.rand.nextInt(2) + 1), 0.0F);
				skeleton.worldObj.playSoundAtEntity(skeleton, "random.bowhit", 1F, 1F);

			}
		}
			
			if (event.entity instanceof EntityGhast) {

				if (event.source.getDamageType().equals("player") && rand.nextDouble() < 0.1D) {

					event.entity.entityDropItem(
							Brewcraft.obsidianTear.getStack(1), 0.0F);
					event.entity.worldObj.playSoundAtEntity(event.entity, "random.bowhit", 1F, 1F);

				}

		}

		if (event.entityLiving instanceof EntityChicken) {
			if (rand.nextDouble() < 0.05D) {

				event.entityLiving.entityDropItem(Brewcraft.goldenFeather.getStack(), 0.0F);
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "random.levelup", 1F, 1F);

			}
			
		}

	}

	@SubscribeEvent
	public void checkBottledFire2(final PlayerInteractEvent event) {
		if (Brewcraft.emptyBottle.equals(event.entityPlayer.getHeldItem())
				|| Brewcraft.splashBottle.equals(event.entityPlayer.getHeldItem())) {
			WorldLocation loc = new WorldLocation(event.x, event.y, event.z, event.entityPlayer.worldObj);
			Block block = loc.getBlock();

			if (block == Blocks.lava || block == Blocks.fire) {
				event.entityPlayer.inventory.consumeInventoryItem(Items.glass_bottle);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1,
						Brewcraft.emptyBottle.equals(event.entityPlayer.getHeldItem()) ? 0 : 1));
				loc.setAir();
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 1F, 1F);

			}

		}

	}
	
	@SubscribeEvent
	public void fireproofCheck(final LivingHurtEvent event) {
		if(event.source.equals(DamageSource.lava) 
				|| event.source.equals(DamageSource.inFire)) {
			if(event.entity instanceof EntityPlayer) {
				final EntityPlayer player = (EntityPlayer) event.entity;
				if(player.getActivePotionEffect(Brewcraft.fireproof) != null) {
					event.setCanceled(true);
					player.heal(1F);
				}
			}
		}
	}
}
