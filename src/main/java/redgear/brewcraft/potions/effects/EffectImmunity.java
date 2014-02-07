package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import redgear.brewcraft.potions.PotionExtension;

public class EffectImmunity extends PotionExtension {

	public EffectImmunity(int id) {
		super(id, false, 13371061);
		setPotionName("potion.immunity");
		setIconIndex(3, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		living.removePotionEffect(Potion.poison.id);

		if (strength >= 1) {
			living.removePotionEffect(Potion.hunger.id);
			living.removePotionEffect(Potion.weakness.id);
		}

	}

}
