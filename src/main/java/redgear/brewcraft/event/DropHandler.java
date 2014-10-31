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
import redgear.brewcraft.plugins.item.ItemPlugin;
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

			if (skeleton.getSkeletonType() == 1 || skeleton.isBurning())
				if (rand.nextFloat() < 0.6D && event.source.getDamageType().equals("player"))
					skeleton.entityDropItem(
							ItemPlugin.charredBone.getStack(rand.nextInt(2) + 1),
							0.0F);
		}

		if (rand.nextInt(200) == 0 && event.entity instanceof IMob && event.source.getDamageType().equals("player"))
			event.entity.entityDropItem(ItemPlugin.heartSmall.getStack(), 0.0F);

		if (event.entity instanceof EntityGhast) {

			if (event.source.getDamageType().equals("player") && rand.nextFloat() < 0.1F)
				event.entity.entityDropItem(ItemPlugin.obsidianTear.getStack(), 0.0F);
		}

		if (event.entityLiving instanceof EntityChicken) {
			if (rand.nextFloat() < 0.05F)
				event.entityLiving.entityDropItem(ItemPlugin.goldenFeather.getStack(), 0.0F);
		}

		if (event.entityLiving instanceof EntityWitch) {
			if (rand.nextFloat() < 0.7F)
				event.entityLiving.entityDropItem(ItemPlugin.tiredSpores.getStack(rand.nextInt(2) + 1), 0.0F);
			if (rand.nextDouble() > 0.2F)
				event.entityLiving.entityDropItem(ItemPlugin.remedySalve.getStack(rand.nextInt(2) + 1), 0.0F);
		}

		if (event.entityLiving instanceof EntitySpider) {
			if (rand.nextFloat() < 0.25F)
				event.entityLiving.entityDropItem(ItemPlugin.spiderFang.getStack(rand.nextInt(1) + 1), 0.0F);
		}

		if (event.entityLiving instanceof EntitySilverfish) {
			if (rand.nextFloat() < 0.7F)
				event.entityLiving.entityDropItem(ItemPlugin.steelScales.getStack(rand.nextInt(3) + 1), 0.0F);
		}

		if (event.entityLiving instanceof EntityBlaze) {
			if (rand.nextFloat() < 0.5F)
				event.entityLiving.entityDropItem(ItemPlugin.heartBlaze.getStack(), 0.0F);
		}
	}

}
