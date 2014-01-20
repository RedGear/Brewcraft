package redgear.brewcraft.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

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

	@ForgeSubscribe
	public void checkBottledFire(final EntityInteractEvent event) {

		if (event.entityLiving instanceof EntityBlaze || event.entityLiving instanceof EntityMagmaCube)
			if (Item.glassBottle.equals(event.entityPlayer.getHeldItem())) {

				event.entityPlayer.inventory.consumeInventoryItem(Item.glassBottle.itemID);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 0));
				event.entityLiving.attackEntityFrom(DamageSource.magic, 2.5F);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "mob.magmacube.jump", 1F, 1F);

			}
		
		if(Brewcraft.splashBottle.equals(event.entityPlayer.getHeldItem())){
			
			if(event.entityLiving instanceof EntityGhast){
				
				event.entityPlayer.inventory.consumeInventoryItem(Item.glassBottle.itemID);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 1));
				event.entityLiving.attackEntityFrom(DamageSource.cactus, 30F);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "mob.ghast.death", 1F, 1F);
				
			}
			
			if(event.entityLiving instanceof EntityBlaze || event.entityLiving instanceof EntityMagmaCube){
				
				event.entityPlayer.inventory.consumeInventoryItem(Item.glassBottle.itemID);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 1));
				event.entityLiving.attackEntityFrom(DamageSource.magic, 2.5F);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "mob.magmacube.jump", 1F, 1F);
				
			}
			
		}

	}

	@ForgeSubscribe
	public void editDrops(final LivingDropsEvent event) {
		final Random rand = event.entity.worldObj.rand;

		if (event.entity instanceof EntityGhast && event.source.equals(DamageSource.cactus))
			event.setCanceled(true);
		if (event.entity instanceof EntitySkeleton) {

			final EntitySkeleton skeleton = (EntitySkeleton) event.entity;

			if (skeleton.getSkeletonType() == 1 && rand.nextDouble() < 0.6D
					&& event.source.getDamageType().equals("player")) {

				skeleton.entityDropItem(
						Brewcraft.charredbone.getStack(event.entityLiving.worldObj.rand.nextInt(2) + 1), 0.0F);
				skeleton.worldObj.playSoundAtEntity(skeleton, "random.bowhit", 1F, 1F);

			}

		}

		if (event.entityLiving instanceof EntityChicken)
			if (rand.nextDouble() < 0.05D) {

				event.entityLiving.entityDropItem(Brewcraft.goldenfeather.getStack(), 0.0F);
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "random.levelup", 1F, 1F);

			}

	}

	@ForgeSubscribe
	public void checkBottledFire2(final PlayerInteractEvent event) {
		
		/**
		 * Don't know if this also works for vanilla items.
		 */
		if(Item.glassBottle.equals(event.entityPlayer.getHeldItem())){
			
			if(event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Block.lavaMoving.blockID){
				
				event.entityPlayer.inventory.consumeInventoryItem(Item.glassBottle.itemID);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 0));
				event.entityPlayer.worldObj.setBlockToAir(event.x, event.y, event.z);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 1F, 1F);
				
			}
			
			if(event.entityPlayer.worldObj.getBlockId(event.x, event.y + 1, event.z) == Block.fire.blockID){
				
				event.entityPlayer.inventory.consumeInventoryItem(Item.glassBottle.itemID);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 0));
				event.entityPlayer.worldObj.setBlockToAir(event.x, event.y + 1, event.z);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 1F, 1F);
				
			}
			
		}
		
		if(Brewcraft.splashBottle.equals(event.entityPlayer.getHeldItem())){
			
			if(event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Block.lavaMoving.blockID){
				
				event.entityPlayer.inventory.consumeInventoryItem(Item.glassBottle.itemID);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 1));
				event.entityPlayer.worldObj.setBlockToAir(event.x, event.y, event.z);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 1F, 1F);
				
			}
			
			if(event.entityPlayer.worldObj.getBlockId(event.x, event.y + 1, event.z) == Block.fire.blockID){
				
				event.entityPlayer.inventory.consumeInventoryItem(Item.glassBottle.itemID);
				event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Brewcraft.potions, 1, 1));
				event.entityPlayer.worldObj.setBlockToAir(event.x, event.y + 1, event.z);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 1F, 1F);
				
			}
			
		}

	}
}
