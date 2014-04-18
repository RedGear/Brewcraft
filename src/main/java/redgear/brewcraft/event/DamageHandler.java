package redgear.brewcraft.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import redgear.brewcraft.plugins.common.EffectPlugin;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DamageHandler {

	private static DamageHandler instance;

	private DamageHandler() {

	}

	public static DamageHandler register() {
		if (instance == null) {
			instance = new DamageHandler();
			MinecraftForge.EVENT_BUS.register(instance);

		}
		return instance;
	}

	@SubscribeEvent
	public void cancelFireDamage(final LivingHurtEvent event) {
		if (event.entity instanceof EntityLivingBase) {
			final EntityLivingBase living = (EntityLivingBase) event.entity;
			
			if(living.worldObj.rand.nextDouble() < 0.3D && !(living instanceof EntityPlayer))
				living.dropItem(IngredientPlugin.heartSmall.item, 1);
			
			if (living.getActivePotionEffect(EffectPlugin.fireproof) != null) {
				if (event.source.equals(DamageSource.lava) || event.source.equals(DamageSource.inFire)
						|| event.source.equals(DamageSource.onFire)) {
					event.ammount = 0;
					if (living.getActivePotionEffect(EffectPlugin.fireproof).getAmplifier() >= 1) {
						living.heal(1F);
					}
				}
			}
		}
	}

}
