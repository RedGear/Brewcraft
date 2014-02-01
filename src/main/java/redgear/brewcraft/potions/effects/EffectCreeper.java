package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import redgear.brewcraft.potions.PotionExtension;

public class EffectCreeper extends PotionExtension {

	public EffectCreeper(int id) {
		super(id, true, 1987089);
		setPotionName("potion.creeper");
		setIconIndex(0, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		int duration = living.getActivePotionEffect(this).duration;

		if (duration == 8)
			living.worldObj.playSoundAtEntity(living, "mob.creeper.say", 1F, 1F);

		if (duration == 4)
			living.worldObj.playSoundAtEntity(living, "mob.creeper.death", 1F, 1F);

		if (duration == 1)
			living.worldObj.createExplosion(null, living.posX, living.posY, living.posZ, 4, true);
	}
}
