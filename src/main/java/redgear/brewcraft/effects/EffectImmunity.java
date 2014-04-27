package redgear.brewcraft.effects;

import java.util.Collection;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import redgear.brewcraft.plugins.common.AchievementPlugin;

public class EffectImmunity extends PotionExtension {

	public EffectImmunity(int id) {
		super(id, false, 0x9933CC);
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

		if (strength >= 2) {
			living.removePotionEffect(Potion.wither.id);
			living.removePotionEffect(Potion.confusion.id);
			living.removePotionEffect(Potion.blindness.id);
		}

		/*if (strength >= 3) {
			Collection<PotionEffect> map = living.getActivePotionEffects();
			int id = 0;

					for (PotionEffect effect : map) {
						id = effect.getPotionID();

						if (id < Potion.potionTypes.length && Potion.potionTypes[id].isBadEffect())
							living.removePotionEffect(id);
					}
				}

		if (living instanceof EntityPlayer && AchievementPlugin.immunity != null) {
			((EntityPlayer) living).addStat(AchievementPlugin.immunity, 1);
		}*/

	}

}
