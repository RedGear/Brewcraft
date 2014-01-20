package redgear.brewcraft.common;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class BrewcraftTickHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {

		EntityPlayer player = (EntityPlayer) tickData[0];
			
			if(player.isPotionActive(Brewcraft.creeper)) {
				
				if(player.getActivePotionEffect(Brewcraft.creeper).getDuration() == 8)
					player.worldObj.playSoundAtEntity(player, "mob.creeper.say", 1F, 1F);
				
				if(player.getActivePotionEffect(Brewcraft.creeper).getDuration() == 4)
					player.worldObj.playSoundAtEntity(player, "mob.creeper.death", 1F, 1F);
				
				if(player.getActivePotionEffect(Brewcraft.creeper).getDuration() == 1) {
					if(player.capabilities.isCreativeMode == false) {
						player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 4, true);
						player.attackEntityFrom(DamageSource.generic, 25F);
						
					  	 	}
					}	
			}

			if (player.isPotionActive(Brewcraft.immunity)
					&& player.getActivePotionEffect(Brewcraft.immunity).getAmplifier() == 0)
				player.removePotionEffect(Potion.poison.id);

			if (player.isPotionActive(Brewcraft.creeper)
					&& player.getActivePotionEffect(Brewcraft.immunity).getAmplifier() == 1) {
				player.removePotionEffect(Potion.poison.id);
				player.removePotionEffect(Potion.hunger.id);
				player.removePotionEffect(Potion.weakness.id);
			}
		
		if(player.isPotionActive(Brewcraft.angel)) {
			
			if(player.getActivePotionEffect(Brewcraft.angel).getAmplifier() == 1) {
				player.heal(1F);
				player.getFoodStats().addStats(2, 0.5F);
				
			}
			
			if(player.getActivePotionEffect(Brewcraft.angel).getAmplifier() == 0) {
				player.heal(0.5F);
				player.getFoodStats().addStats(1, 0.1F);
				
			}
		}
		
		if(player.isPotionActive(Brewcraft.flight) && player.capabilities.isCreativeMode == false) {
			
			if(player.getActivePotionEffect(Brewcraft.flight).getDuration() > 1) 
				player.capabilities.allowFlying = true;

			if(player.getActivePotionEffect(Brewcraft.flight).getDuration() < 1) {
				
				player.capabilities.allowFlying = false;
				player.capabilities.isFlying = false;
				
			}
			
		}

		if (player.isPotionActive(Potion.fireResistance))
			player.extinguish();

	}

	@Override
	public EnumSet<TickType> ticks() {

		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {

		return "Brewcraft tick update.";
	}

}
