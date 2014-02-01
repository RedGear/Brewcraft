package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.potions.PotionExtension;

public class EffectFlight extends PotionExtension {

	public EffectFlight(int id) {
		super(id, false, 16777215);
		setPotionName("potion.flight");
		setIconIndex(2, 0);
	}

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
	
	@Override
	public boolean isInstant(){
		return true;
	}
}
