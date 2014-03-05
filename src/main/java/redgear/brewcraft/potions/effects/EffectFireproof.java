package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import redgear.brewcraft.potions.PotionExtension;

public class EffectFireproof extends PotionExtension{

	public EffectFireproof(int id) {
		super(id, false, 10034944);
		
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		
		living.extinguish();

	}

}
