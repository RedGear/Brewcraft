package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.plugins.core.AchievementPlugin;

public class EffectFireproof extends PotionExtension {

	public EffectFireproof(int id) {
		super("fireproof", id, false, 0xCC0000);
		setIconIndex(5, 0);

	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {

		living.extinguish();

		//Maybe have health bar turn 'molten' or something while this is active?
		if (living instanceof EntityPlayer && AchievementPlugin.fireproof != null)
			((EntityPlayer) living).addStat(AchievementPlugin.fireproof, 1);

	}
	
	@Override
	public boolean isReady(int par1, int par2) {
		int k = 50 >> par2;
		return k > 0 ? par1 % k == 0 : true;
	}

}
