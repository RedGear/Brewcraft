package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.common.Brewcraft;
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
		System.out.println("Test");
		Brewcraft.inst.logDebug("Angel effect on ", living, " at ", strength);

		if (strength == 0) {
			Brewcraft.inst.logDebug("Strength == 0. Healing...");
			living.heal(0.5F);
			if (living instanceof EntityPlayer) {
				Brewcraft.inst.logDebug("Is player, filling hunger...");
				((EntityPlayer) living).getFoodStats().addStats(1, 0.1F);
			}

		}

		if (strength == 1) {
			Brewcraft.inst.logDebug("Strength == 1. Healing...");
			living.heal(1F);
			if (living instanceof EntityPlayer) {
				Brewcraft.inst.logDebug("Is player, filling hunger...");
				((EntityPlayer) living).getFoodStats().addStats(2, 0.5F);
			}
		}
	}
}
