package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.plugins.common.AchievementPlugin;

public class EffectFrozen extends PotionExtension {

	public EffectFrozen(int id) {
		super(id, true, 13107192);
		setPotionName("potion.frozen");
		setIconIndex(4, 0);
	}

	/**
	 * Perform this potion's normal ever-tick effect.
	 */
	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		
		if(living instanceof EntityPlayer && AchievementPlugin.freeze != null) {
			((EntityPlayer)living).addStat(AchievementPlugin.freeze, 1);
		}
		
	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
		return k > 0 ? duration % k == 0 : true;
	}
}
