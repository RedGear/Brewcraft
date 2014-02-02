package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.potions.PotionExtension;

public class EffectCreeper extends PotionExtension {

	public EffectCreeper(int id) {
		super(id, true, 1299475);
		setPotionName("potion.creeper");
		setIconIndex(0, 0);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		int duration = living.getActivePotionEffect(this).duration;
		boolean flag = living.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

		if(living instanceof EntityCreeper) {
			EntityCreeper creeper = (EntityCreeper) living;
			if(duration == 1)
				creeper.getDataWatcher().updateObject(17, Byte.valueOf((byte)1));
				
		}
		
		if(living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if(player.capabilities.isCreativeMode)
				Brewcraft.inst.logDebug("Player is creative, not exploding.");
			
			if(!(player.capabilities.isCreativeMode)) {
				if(strength == 0)
					living.worldObj.createExplosion(null, living.posX, living.posY, living.posZ, 4, flag);
				
				if(strength == 1)
					living.worldObj.createExplosion(null, living.posX, living.posY, living.posZ, 7, flag);
			}
		}
		
		if(!(living instanceof EntityPlayer) && !(living instanceof EntityCreeper)) {
			if(duration == 8)
				living.worldObj.playSoundAtEntity(living, "mob.creeper.say", 1F, 1F);
			
			if(duration == 4)
				living.worldObj.playSoundAtEntity(living, "mob.creeper.say", 1F, 1F);
			
			if(duration == 1) {
				if(strength == 0) {
					living.worldObj.createExplosion(null, living.posX, living.posY, living.posZ, 4, flag);
					living.attackEntityFrom(DamageSource.generic, 30F);
				}
					
				
				if(strength == 1) {
					living.worldObj.createExplosion(null, living.posX, living.posY, living.posZ, 7, flag);
					living.attackEntityFrom(DamageSource.generic, 30F);
				}
			}
		}
	}
	
}
