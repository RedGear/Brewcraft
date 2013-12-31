package redgear.brewcraft.common;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class BrewcraftTickHandler implements ITickHandler{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
		EntityPlayer player = (EntityPlayer) tickData[0];
		
		if(player.isPotionActive(Brewcraft.creeper)){

			if(player.isPotionActive(Brewcraft.creeper) && player.getActivePotionEffect(Brewcraft.creeper).getDuration() == 8){
				
			player.worldObj.playSoundAtEntity(player, "mob.creeper.say", 1F, 1F);
			
			}
			
			if(player.isPotionActive(Brewcraft.creeper) && player.getActivePotionEffect(Brewcraft.creeper).getDuration() == 4){
				
			player.worldObj.playSoundAtEntity(player, "mob.creeper.say", 1F, 1F);
			
			}
			
			if(player.isPotionActive(Brewcraft.creeper) && player.getActivePotionEffect(Brewcraft.creeper).getDuration() == 1 && player.capabilities.isCreativeMode == false){
				
			player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 4, true);
			player.attackEntityFrom(DamageSource.generic, 25F);
			
			}
			
			if(player.isPotionActive(Brewcraft.immunity) && player.getActivePotionEffect(Brewcraft.immunity).getAmplifier() == 0){
				player.removePotionEffect(Potion.poison.id);
			}
			
			if(player.isPotionActive(Brewcraft.creeper) && player.getActivePotionEffect(Brewcraft.immunity).getAmplifier() == 1){
				player.removePotionEffect(Potion.poison.id);
				player.removePotionEffect(Potion.hunger.id);
				player.removePotionEffect(Potion.weakness.id);
			}
		}
		
		if(player.isPotionActive(Brewcraft.angel)){
			
			if(Brewcraft.rand > 0.8D){
			player.heal(0.5F);
			player.getFoodStats().addStats(1, 0.1F);
			
			}
			
		}
		
		if(player.isPotionActive(Brewcraft.angel) && player.getActivePotionEffect(Brewcraft.angel).getAmplifier() == 1){
			
			if(Brewcraft.rand > 0.8D){
			player.heal(1F);
			player.getFoodStats().addStats(2, 0.5F);
			
			}
			
		}

		if(player.isPotionActive(Brewcraft.flight) && player.getActivePotionEffect(Brewcraft.flight).getDuration() > 1 && player.capabilities.isCreativeMode == false){
		player.capabilities.allowFlying = true;
		if(player.isPotionActive(Brewcraft.flight) && player.getActivePotionEffect(Brewcraft.flight).getDuration() == 1 || player.isPotionActive(Brewcraft.flight) && player.getActivePotionEffect(Brewcraft.flight).getDuration() < 1){
			
			player.capabilities.isFlying = false;
			player.capabilities.allowFlying = false;
			
		}
		
		}

		if(player.isPotionActive(Potion.fireResistance)){
			
			player.extinguish();
		}

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
