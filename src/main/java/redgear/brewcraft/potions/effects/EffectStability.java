package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import redgear.brewcraft.potions.PotionExtension;

public class EffectStability extends PotionExtension{

	public EffectStability(int id) {
		super(id, false, 12237498);
		setPotionName("potion.stability");
		setIconIndex(5, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		
		
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
