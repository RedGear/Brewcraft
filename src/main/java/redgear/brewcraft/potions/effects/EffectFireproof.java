package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.potions.PotionExtension;

public class EffectFireproof extends PotionExtension{

	public EffectFireproof(int id) {
		super(id, false, 10034944);
		setPotionName("potion.fireproof");
		setIconIndex(6, 0);
		
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		
		living.extinguish();

	}

}
