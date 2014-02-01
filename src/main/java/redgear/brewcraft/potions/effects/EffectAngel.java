package redgear.brewcraft.potions.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.potions.PotionExtension;

public class EffectAngel extends PotionExtension {

	public EffectAngel(int id) {
		super(id, false, 16570666);
		setPotionName("potion.angel");
		setIconIndex(1, 0);
	}

	/**
	 * Perform this potion's normal ever-tick effect.
	 */
	@Override
	public void performEffect(EntityLivingBase living, int strength) {

		System.out.println("Test");
		Brewcraft.inst.logDebug("Angel effect on ", living, " at ", strength);

		if (strength == 0) {
			Brewcraft.inst.logDebug("Strength == 0.");
			if(living instanceof EntityZombie || living instanceof EntitySkeleton) {
				Brewcraft.inst.logDebug("Is undead, damaging...");
				living.attackEntityFrom(DamageSource.generic, 2F);
			}
			if (living instanceof EntityPlayer) {
				living.heal(0.5F);
				Brewcraft.inst.logDebug("Is player, filling hunger and healing...");
				((EntityPlayer) living).getFoodStats().addStats(1, 0.1F);
			}

		}

		if (strength == 1) {
			Brewcraft.inst.logDebug("Strength == 1.");
			if(living instanceof EntityZombie || living instanceof EntitySkeleton) {
				Brewcraft.inst.logDebug("Is undead, damaging...");
				living.attackEntityFrom(DamageSource.generic, 4F);
			}
			if (living instanceof EntityPlayer) {
				Brewcraft.inst.logDebug("Is player, filling hunger and healing...");
				living.heal(1F);
				((EntityPlayer) living).getFoodStats().addStats(2, 0.5F);
			}
		}
	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
		return k > 0 ? amplifier % k == 0 : true;
	}
}
