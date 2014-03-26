package redgear.brewcraft.event;

import java.util.Random;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import redgear.brewcraft.common.Brewcraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DropHandler {
	
	private static DropHandler instance;

	private DropHandler() {

	}

	public static DropHandler register() {
		if (instance == null) {
			instance = new DropHandler();
			MinecraftForge.EVENT_BUS.register(instance);

		}
		return instance;
	}
	
	@SubscribeEvent
	public void editDrops(final LivingDropsEvent event) {
		final Random rand = event.entity.worldObj.rand;

		if (event.entity instanceof EntitySkeleton) {

			final EntitySkeleton skeleton = (EntitySkeleton) event.entity;

			if (skeleton.getSkeletonType() == 1 && rand.nextDouble() < 0.6D
					&& event.source.getDamageType().equals("player")) {

				skeleton.entityDropItem(
						Brewcraft.charredBone.getStack(event.entityLiving.worldObj.rand.nextInt(2) + 1), 0.0F);
				skeleton.worldObj.playSoundAtEntity(skeleton, "random.bowhit", 1F, 1F);

			}
		}
			
			if (event.entity instanceof EntityGhast) {

				if (event.source.getDamageType().equals("player") && rand.nextDouble() < 0.1D) {

					event.entity.entityDropItem(
							Brewcraft.obsidianTear.getStack(1), 0.0F);
					event.entity.worldObj.playSoundAtEntity(event.entity, "random.bowhit", 1F, 1F);

				}
		}

		if (event.entityLiving instanceof EntityChicken) {
			if (rand.nextDouble() < 0.05D) {

				event.entityLiving.entityDropItem(Brewcraft.goldenFeather.getStack(), 0.0F);
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "random.levelup", 1F, 1F);

			}
		}
	}

}
