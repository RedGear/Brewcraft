package redgear.brewcraft.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import redgear.brewcraft.plugins.common.AchievementPlugin;

public class EffectFlight extends PotionExtension {

	public EffectFlight(int id) {
		super(id, false, 13750737);
		setPotionName("potion.flight");
		setIconIndex(2, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		int duration = living.getActivePotionEffect(this).getDuration();

		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if (duration <= 1) {
				if (!player.capabilities.isCreativeMode) {
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
				}
			} else
				player.capabilities.allowFlying = true;
			if (!player.worldObj.isRemote)
				player.sendPlayerAbilities();
			if (AchievementPlugin.flight != null) {
				player.addStat(AchievementPlugin.flight, 1);
			}
		}
	}
}
