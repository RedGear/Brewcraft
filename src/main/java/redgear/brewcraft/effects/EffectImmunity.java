package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import redgear.brewcraft.plugins.common.AchievementPlugin;

public class EffectImmunity extends PotionExtension {

	public EffectImmunity(int id) {
		super(id, false, 11534336);
		setPotionName("potion.immunity");
		setIconIndex(3, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {	
		living.removePotionEffect(Potion.poison.id);
		
		if (strength == 1) {
			living.removePotionEffect(Potion.hunger.id);
			living.removePotionEffect(Potion.weakness.id);
		}
		
		if (strength >= 2) {
			living.removePotionEffect(Potion.hunger.id);
			living.removePotionEffect(Potion.weakness.id);
			living.removePotionEffect(Potion.wither.id);
			living.removePotionEffect(Potion.confusion.id);
			living.removePotionEffect(Potion.blindness.id);
		}
		
		if(living instanceof EntityPlayer && AchievementPlugin.immunity != null) {
			((EntityPlayer)living).addStat(AchievementPlugin.immunity, 1);
		}

	}

}
