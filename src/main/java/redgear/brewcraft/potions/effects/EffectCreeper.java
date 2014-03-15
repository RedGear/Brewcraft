package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import redgear.brewcraft.plugins.common.AchievementPlugin;

public class EffectCreeper extends PotionExtension {

	public EffectCreeper(int id) {
		super(id, true, 1299475);
		setPotionName("potion.creeper");
		setIconIndex(0, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		int duration = living.getActivePotionEffect(this).getDuration();
		boolean flag = living.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

		if (living instanceof EntityCreeper && duration == 1)
			((EntityCreeper) living).getDataWatcher().updateObject(17, Byte.valueOf((byte) 1));

		else {
			
			if (duration == 1) {

				if (living instanceof EntityPlayer && ((EntityPlayer) living).capabilities.isCreativeMode)
					return;//Don't explode if target is player in creative. 

					living.worldObj.createExplosion(null, living.posX, living.posY, living.posZ, strength * 3 + 4, flag);
					living.attackEntityFrom(DamageSource.generic, 30F);
				}
				if(living instanceof EntityPlayer && AchievementPlugin.explode != null) {
					((EntityPlayer)living).addStat(AchievementPlugin.explode, 1);
				}
			}
		}
	}
