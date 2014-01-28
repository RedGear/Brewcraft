package redgear.brewcraft.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.mod.ModUtils;

public class CustomPotionEffects{

	public final Potion angel;
	public final Potion flight;
	public final Potion creeper;
	public final Potion immunity;

	public CustomPotionEffects(ModUtils inst) {
		angel = new PotionExtension(inst.getInt("Potion Effect IDs", "'Angelic' Effect ID",
				"Must be over 20 to avoid conflict with vanilla.", 29), false, 16114042) {
			/**
			 * Perform this potion's normal ever-tick effect.
			 */
			@Override
			public void performEffect(EntityLivingBase living, int strength) {
				Brewcraft.inst.logDebug("Angel effect on ", living, " at ", strength);
				
				if (strength == 0) {
					Brewcraft.inst.logDebug("Strength == 0. Healing...");
					living.heal(0.5F);
					if (living instanceof EntityPlayer){
						Brewcraft.inst.logDebug("Is player, filling hunger...");
						((EntityPlayer) living).getFoodStats().addStats(1, 0.1F);
					}

				}

				if (strength == 1) {
					Brewcraft.inst.logDebug("Strength == 1. Healing...");
					living.heal(1F);
					if (living instanceof EntityPlayer){
						Brewcraft.inst.logDebug("Is player, filling hunger...");
						((EntityPlayer) living).getFoodStats().addStats(2, 0.5F);
					}
				}
			}
		}.setPotionName("potion.angel").setIconIndex(1, 0);

		flight = new PotionExtension(inst.getInt("Potion Effect IDs", "'Flight' Effect ID",
				"Must be over 20 to avoid conflict with vanilla.", 26), false, 16777215) {

			@Override
			public void performEffect(EntityLivingBase living, int strength) {
				if (living instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) living;
					if (!player.capabilities.isCreativeMode) {
						player.capabilities.allowFlying = false;
						player.capabilities.isFlying = false;
					}
				}
			}

			/**
			 * Hits the provided entity with this potion's instant effect.
			 */
			@Override
			public void affectEntity(EntityLivingBase thrower, EntityLivingBase reciver, int potionStrength,
					double distanceFromSplash) {
				if (reciver instanceof EntityPlayer)
					((EntityPlayer) reciver).capabilities.allowFlying = true;
			}

			@Override
			public boolean isReady(int duration, int amplifier) {
				return duration == 1;
			}
		}.setPotionName("potion.flight").setIconIndex(2, 0);

		creeper = new PotionExtension(inst.getInt("Potion Effect IDs", "'Cumbustion' Effect ID",
				"Must be over 20 to avoid conflict with vanilla.", 27), true, 1987089) {

			@Override
			public void performEffect(EntityLivingBase living, int strength) {
				int duration = living.getActivePotionEffect(creeper).duration;

				if (duration == 8)
					living.worldObj.playSoundAtEntity(living, "mob.creeper.say", 1F, 1F);

				if (duration == 4)
					living.worldObj.playSoundAtEntity(living, "mob.creeper.death", 1F, 1F);

				if (duration == 1) {
					living.removePotionEffect(creeper.id);
					living.worldObj.createExplosion(living, living.posX, living.posY, living.posZ, 4, true);
					living.attackEntityFrom(DamageSource.generic, 25F);
				}
			}
		}.setPotionName("potion.creeper").setIconIndex(0, 0);

		immunity = new PotionExtension(inst.getInt("Potion Effect IDs", "'Immunity' Effect ID",
				"Must be over 20 to avoid conflict with vanilla.", 28), false, 8131210) {

			@Override
			public void performEffect(EntityLivingBase living, int strength) {
				if (strength == 0)
					living.removePotionEffect(Potion.poison.id);

				if (strength == 1) {
					living.removePotionEffect(Potion.poison.id);
					living.removePotionEffect(Potion.hunger.id);
					living.removePotionEffect(Potion.weakness.id);
				}

			}

		}.setPotionName("potion.immunity").setIconIndex(3, 0);

	}
}
