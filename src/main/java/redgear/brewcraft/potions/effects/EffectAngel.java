package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.potions.PotionExtension;

public class EffectAngel extends PotionExtension {

	public EffectAngel(int id) {
		super(id, false, 16114042);
		setPotionName("potion.angel");
		setIconIndex(1, 0);
	}

	/**
	 * Perform this potion's normal ever-tick effect.
	 */
	@Override
	public void performEffect(EntityLivingBase living, int strength) {

		if (strength == 0) {
			living.heal(0.5F);
			if (living instanceof EntityPlayer)
				((EntityPlayer) living).getFoodStats().addStats(1, 0.1F);

		}

		if (strength == 1) {
			living.heal(1F);
			if (living instanceof EntityPlayer)
				((EntityPlayer) living).getFoodStats().addStats(2, 0.5F);
		}
	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
		return k > 0 ? amplifier % k == 0 : true;
	}
}
