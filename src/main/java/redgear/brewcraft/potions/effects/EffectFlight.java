package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.potions.PotionExtension;

public class EffectFlight extends PotionExtension {

	public EffectFlight(int id) {
		super(id, false, 13750737);
		setPotionName("potion.flight");
		setIconIndex(2, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		Brewcraft.inst.logDebug("Stop!");
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
		Brewcraft.inst.logDebug("Fly!");
		if (reciver instanceof EntityPlayer)
			((EntityPlayer) reciver).capabilities.allowFlying = true;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		Brewcraft.inst.logDebug("Is Ready");
		return duration == 1;
	}

	@Override
	public boolean isInstant() {
		Brewcraft.inst.logDebug("Is Instant");
		return true;
	}
}
