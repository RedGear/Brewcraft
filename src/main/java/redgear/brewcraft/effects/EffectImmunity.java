package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.plugins.core.AchievementPlugin;

public class EffectImmunity extends PotionExtension {

	public EffectImmunity(int id) {
		super("immunity", id, false, 0x9933CC);
		setIconIndex(3, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		if (living instanceof EntityPlayer && AchievementPlugin.immunity != null)
			((EntityPlayer) living).addStat(AchievementPlugin.immunity, 1);

	}
}
