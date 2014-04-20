package redgear.brewcraft.event;

import java.util.Random;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import redgear.brewcraft.plugins.common.IngredientPlugin;
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
					&& event.source.getDamageType().equals("player"))
				skeleton.entityDropItem(
						IngredientPlugin.charredBone.getStack(event.entityLiving.worldObj.rand.nextInt(2) + 1), 0.0F);
		}
		
		if (event.entity.worldObj.rand.nextInt(200) == 0 && !(event.entity instanceof EntityPlayer)
				&& event.entity instanceof IMob && event.source.equals("player"))
			event.entity.dropItem(IngredientPlugin.heartSmall.item, 1);

		if (event.entity instanceof EntityGhast) {

			if (event.source.getDamageType().equals("player") && rand.nextDouble() < 0.1D)
				event.entity.entityDropItem(IngredientPlugin.obsidianTear.getStack(), 0.0F);
		}

		if (event.entityLiving instanceof EntityChicken) {
			if (rand.nextDouble() < 0.05D)
				event.entityLiving.entityDropItem(IngredientPlugin.goldenFeather.getStack(), 0.0F);
		}

		if (event.entityLiving instanceof EntityWitch) {
			if (rand.nextDouble() < 0.7D)
				event.entityLiving.entityDropItem(IngredientPlugin.tiredSpores.getStack(rand.nextInt(3)), 0.0F);
			if (rand.nextDouble() > 0.2D)
				event.entityLiving.entityDropItem(IngredientPlugin.remedySalve.getStack(rand.nextInt(3)), 0.0F);
		}

		if (event.entityLiving instanceof EntitySpider) {
			if (rand.nextDouble() < 0.25D)
				event.entityLiving.entityDropItem(IngredientPlugin.spiderFang.getStack(rand.nextInt(1) + 1), 0.0F);
		}

		if (event.entityLiving instanceof EntitySilverfish) {
			if (rand.nextDouble() < 0.7D)
				event.entityLiving.entityDropItem(IngredientPlugin.steelScales.getStack(rand.nextInt(3) + 1), 0.0F);
		}
		
		if (event.entityLiving instanceof EntityBlaze) {
			if (rand.nextDouble() < 0.5D)
				event.entityLiving.entityDropItem(IngredientPlugin.heartBlaze.getStack(), 0.0F);
		}
	}

}
