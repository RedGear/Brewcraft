package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.plugins.core.AchievementPlugin;

public class EffectFrozen extends PotionExtension {

	public EffectFrozen(int id) {
		super("frozen", id, true, 0xCCFFFF);
		setIconIndex(4, 0);
	}

	/**
	 * Perform this potion's normal ever-tick effect.
	 */
	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		
		//In the future maybe cause snow to appear around the player,
		//or possibly have their health bar turn 'frozen or something?
		if (living instanceof EntityPlayer && AchievementPlugin.freeze != null)
			((EntityPlayer) living).addStat(AchievementPlugin.freeze, 1);
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
