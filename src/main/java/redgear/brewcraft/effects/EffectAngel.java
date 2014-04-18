package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import redgear.brewcraft.plugins.common.AchievementPlugin;

public class EffectAngel extends PotionExtension {

	public EffectAngel(int id) {
		super(id, false, 0xFFFF66);
		setPotionName("potion.angel");
		setIconIndex(1, 0);
	}

	/**
	 * Perform this potion's normal ever-tick effect.
	 */
	@Override
	public void performEffect(EntityLivingBase living, int strength) {

		if (living.isEntityUndead())
			living.attackEntityFrom(DamageSource.magic, strength * 2 + 2F);
		else
			living.heal(strength + 1 * 1F);

		if (living instanceof EntityPlayer && AchievementPlugin.holywater != null) {
			((EntityPlayer) living).addStat(AchievementPlugin.holywater, 1);
		}
	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
		return k > 0 ? duration % k == 0 : true;
	}
}
