package redgear.brewcraft.common;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import redgear.brewcraft.plugins.common.AchievementPlugin;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BrewcraftEventHandler {

	private static BrewcraftEventHandler instance;

	private BrewcraftEventHandler() {

	}

	public static BrewcraftEventHandler forge() {
		if (instance == null) {
			instance = new BrewcraftEventHandler();
			MinecraftForge.EVENT_BUS.register(instance);

		}
		return instance;
	}

	@SubscribeEvent
	public void checkForGhast(final EntityInteractEvent event) {

		if (Brewcraft.splashBottle.equals(event.entityPlayer.getHeldItem())) {

			if (event.entityLiving instanceof EntityGhast) {

				event.entityPlayer.inventory.consumeInventoryItem(Items.glass_bottle);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 1));
				event.entityLiving.attackEntityFrom(DamageSource.cactus, 30F);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "mob.ghast.death", 1F, 1F);

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
	public void collectBrewery(final LivingUpdateEvent event) {
		if(event.entity instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) event.entity;
			if(player.inventory.hasItemStack(Brewcraft.brewery.getStack()))
				player.addStat(AchievementPlugin.craftBrewery, 1);
		}
	}
	
	@SubscribeEvent
	public void cancelFireDamage(final LivingHurtEvent event) {
		if(event.entity instanceof EntityLivingBase) {
			final EntityLivingBase living = (EntityLivingBase) event.entity;
			if(living.getActivePotionEffect(Brewcraft.fireproof) != null) {
				if(event.source.equals(DamageSource.lava) || event.source.equals(DamageSource.inFire) 
			    	    || event.source.equals(DamageSource.onFire)) {
					event.ammount = 0;
					if(living.getActivePotionEffect(Brewcraft.fireproof).getAmplifier() >= 1) {
						living.heal(1F);
					}
				}
			}
		}
	}
}
