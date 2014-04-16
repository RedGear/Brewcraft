package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class EffectFireEater extends PotionExtension {

	public EffectFireEater(int id) {
		super(id, false, 0xFFCC00);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		if(living.isBurning())
			living.heal(2 * strength);
		else
			living.attackEntityFrom(DamageSource.magic, 2 * strength);
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
