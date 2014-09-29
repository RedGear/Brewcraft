package redgear.brewcraft.event;

import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import redgear.brewcraft.plugins.core.EffectPlugin;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class UpdateHandler {

	private static UpdateHandler instance;

	private UpdateHandler() {

	}

	public static UpdateHandler register() {
		if (instance == null) {
			instance = new UpdateHandler();
			MinecraftForge.EVENT_BUS.register(instance);

		}
		return instance;
	}
	
	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (player.getActivePotionEffect(EffectPlugin.immunity) != null) {
				int strength = player.getActivePotionEffect(EffectPlugin.immunity).getAmplifier();

				player.removePotionEffect(Potion.poison.id);
				player.removePotionEffect(EffectPlugin.poison.id);

				if (strength >= 1) {
					player.removePotionEffect(Potion.hunger.id);
					player.removePotionEffect(Potion.weakness.id);
					player.removePotionEffect(Potion.moveSlowdown.id);
					player.removePotionEffect(EffectPlugin.frozen.id);
				}

				if (strength >= 2) {
					player.removePotionEffect(Potion.wither.id);
					player.removePotionEffect(EffectPlugin.wither.id);
					player.removePotionEffect(Potion.confusion.id);
					player.removePotionEffectClient(Potion.blindness.id);
				}

				if (strength >= 3) {
					Collection<PotionEffect> map = player.getActivePotionEffects();
					int id = 0;

					for (PotionEffect effect : map) {
						id = effect.getPotionID();

						if (id < Potion.potionTypes.length && Potion.potionTypes[id].isBadEffect())
							player.removePotionEffect(id);
					}
				}
			}
		}
	}
}
