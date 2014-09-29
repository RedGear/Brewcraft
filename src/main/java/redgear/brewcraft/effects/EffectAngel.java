package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import redgear.brewcraft.plugins.core.AchievementPlugin;

public class EffectAngel extends PotionExtension {

	public EffectAngel(int id) {
		super("angel", id, false, 0xFFFF33);
		setIconIndex(1, 0);
	}

	/**
	 * Perform this potion's normal ever-tick effect.
	 */
	@Override
	public void performEffect(EntityLivingBase living, int strength) {

		//Maybe have health bar turn white/gold while this is active?
		if (living instanceof EntityZombie) {
			final EntityZombie villager = (EntityZombie) living;
			if (villager.isVillager()) {
				convertToVillager(villager);
				return;
			}
		}

		if (living.isEntityUndead())
			living.attackEntityFrom(DamageSource.magic, strength * 2 + 2F);
		else
			living.heal(strength + 1 * 1F);

		if (living instanceof EntityPlayer && AchievementPlugin.holywater != null)
			((EntityPlayer) living).addStat(AchievementPlugin.holywater, 1);
	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
		return k > 0 ? duration % k == 0 : true;
	}

	/**
	 * Convert this zombie into a villager.
	 * The method in EntityZombie is protected.
	 */
	protected void convertToVillager(EntityZombie living) {
		EntityVillager entityvillager = new EntityVillager(living.worldObj);
		entityvillager.copyLocationAndAnglesFrom(living);
		entityvillager.onSpawnWithEgg((IEntityLivingData) null);
		entityvillager.setLookingForHome();

		if (living.isChild()) {
			entityvillager.setGrowingAge(-24000);
		}

		living.worldObj.removeEntity(living);
		living.worldObj.spawnEntityInWorld(entityvillager);
		entityvillager.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
		living.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1017, (int) living.posX, (int) living.posY,
				(int) living.posZ, 0);
	}
}
