package redgear.brewcraft.effects;

import redgear.brewcraft.plugins.core.EffectPlugin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class EffectVanillaThree extends Potion {

	public EffectVanillaThree(String name, int id, boolean isBad, int particleColor) {
		super(id, isBad, particleColor);
		setPotionName("potion." + name);
	}

	@Override
	public void performEffect(EntityLivingBase living, int strength) {
		if (this.id == EffectPlugin.regeneration.id) {
			if (living.getHealth() < living.getMaxHealth())
				living.heal(1.5F);
		} else if (this.id == EffectPlugin.poison.id) {
			if (living.getHealth() > 1.5F)
				living.attackEntityFrom(DamageSource.magic, 1.5F);
		} else if (this.id == EffectPlugin.wither.id)
			living.attackEntityFrom(DamageSource.wither, 1.5F);
	}

	@Override
	public boolean isReady(int par1, int par2) {
		int k;

		if (this.id == EffectPlugin.regeneration.id) {
			k = 50 >> par2;
			return k > 0 ? par1 % k == 0 : true;
		} else if (this.id == EffectPlugin.poison.id) {
			k = 25 >> par2;
			return k > 0 ? par1 % k == 0 : true;
		} else if (this.id == EffectPlugin.wither.id) {
			k = 40 >> par2;
			return k > 0 ? par1 % k == 0 : true;
		} else {
			return false;
		}
	}
}
