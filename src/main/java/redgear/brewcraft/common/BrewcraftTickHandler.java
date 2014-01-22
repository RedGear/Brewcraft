package redgear.brewcraft.common;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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

		int creeper = getAmplifier(player, Brewcraft.creeper);

		if (creeper == 8)
			player.worldObj.playSoundAtEntity(player, "mob.creeper.say", 1F, 1F);

		if (creeper == 4)
			player.worldObj.playSoundAtEntity(player, "mob.creeper.death", 1F, 1F);

		if (creeper == 1)
			if (player.capabilities.isCreativeMode == false) {
				player.removePotionEffect(Brewcraft.creeper.id);
				player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, 4, true);
				player.attackEntityFrom(DamageSource.generic, 25F);
			}

		int immune = getAmplifier(player, Brewcraft.immunity);

		if (immune == 0)
			player.removePotionEffect(Potion.poison.id);

		if (immune == 1) {
			player.removePotionEffect(Potion.poison.id);
			player.removePotionEffect(Potion.hunger.id);
			player.removePotionEffect(Potion.weakness.id);
		}

		int angel = getAmplifier(player, Brewcraft.angel);

		if (angel == 1) {
			player.heal(1F);
			player.getFoodStats().addStats(2, 0.5F);

		}

		if (angel == 0) {
			player.heal(0.5F);
			player.getFoodStats().addStats(1, 0.1F);

		}

		if (player.isPotionActive(Brewcraft.flight) && !player.capabilities.isCreativeMode) {
			int flight = getAmplifier(player, Brewcraft.flight);
			
			if (flight > 1)
				player.capabilities.allowFlying = true;

			if (flight < 1) {

				player.capabilities.allowFlying = false;
				player.capabilities.isFlying = false;

			}

		}

		if (player.isPotionActive(Potion.fireResistance))
			player.extinguish();

	}

	private int getAmplifier(EntityPlayer player, Potion potion) {
		PotionEffect effect = player.getActivePotionEffect(potion);

		return effect == null ? -1 : effect.getAmplifier();
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
