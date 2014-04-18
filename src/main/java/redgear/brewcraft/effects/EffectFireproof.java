package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.plugins.common.AchievementPlugin;

public class EffectFireproof extends PotionExtension {

	public EffectFireproof(int id) {
		super(id, false, 0x330000);
		setPotionName("potion.fireproof");
		setIconIndex(7, 0);

	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {

		living.extinguish();

		if (living instanceof EntityPlayer && AchievementPlugin.fireproof != null) {
			((EntityPlayer) living).addStat(AchievementPlugin.fireproof, 1);
		}

	}

}
