package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.plugins.common.AchievementPlugin;

public class EffectFireproof extends PotionExtension{

	public EffectFireproof(int id) {
		super(id, false, 10034944);
		setPotionName("potion.fireproof");
		setIconIndex(6, 0);
		
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		
		living.extinguish();
		
		if(living instanceof EntityPlayer && AchievementPlugin.fireproof != null) {
			((EntityPlayer)living).addStat(AchievementPlugin.fireproof, 1);
		}

	}

}
