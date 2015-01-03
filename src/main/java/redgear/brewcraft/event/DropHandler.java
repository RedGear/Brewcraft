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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.ItemPlugin;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DropHandler {

	private static DropHandler instance;

	private final int charredBoneDropRate;
	private final int heartDropRate;
	private final int tearDropRate;
	private final int goldenFeatherDropRate;
	private final int tiredSporesDropRate;
	private final int remedySalveDropRate;
	private final int spiderFangDropRate;
	private final int steelScalesDropRate;
	private final int heartBlazeDropRate;
	
	
	private DropHandler() {
		final String drops = "drops";
		
		charredBoneDropRate		= Brewcraft.inst.getInt(drops, "charredBoneDropRate", 	 20);
		heartDropRate 			= Brewcraft.inst.getInt(drops, "heartDropRate", 		200);
		tearDropRate 			= Brewcraft.inst.getInt(drops, "tearDropRate", 			 10);
		goldenFeatherDropRate 	= Brewcraft.inst.getInt(drops, "goldenFeatherDropRate",  20);
		tiredSporesDropRate 	= Brewcraft.inst.getInt(drops, "tiredSporesDropRate", 	 20);
		remedySalveDropRate 	= Brewcraft.inst.getInt(drops, "remedySalveDropRate", 	 50);
		spiderFangDropRate 		= Brewcraft.inst.getInt(drops, "spiderFangDropRate", 	 25);
		steelScalesDropRate 	= Brewcraft.inst.getInt(drops, "steelScalesDropRate", 	 10);
		heartBlazeDropRate 		= Brewcraft.inst.getInt(drops, "heartBlazeDropRate", 	 20);
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
				if (rand.nextInt(charredBoneDropRate) == 0 && event.source.getDamageType().equals("player"))
					skeleton.entityDropItem(
							ItemPlugin.charredBone.getStack(rand.nextInt(2) + 1),
							0.0F);
		}

		if (rand.nextInt(heartDropRate) == 0 && event.entity instanceof IMob && event.source.getDamageType().equals("player"))
			event.entity.entityDropItem(ItemPlugin.heartSmall.getStack(), 0.0F);

		if (event.entity instanceof EntityGhast) {

			if (event.source.getDamageType().equals("player") && rand.nextInt(tearDropRate) == 0)
				event.entity.entityDropItem(ItemPlugin.obsidianTear.getStack(), 0.0F);
		}

		if (event.entityLiving instanceof EntityChicken) {
			if (rand.nextInt(goldenFeatherDropRate) == 0)
				event.entityLiving.entityDropItem(ItemPlugin.goldenFeather.getStack(), 0.0F);
		}

		if (event.entityLiving instanceof EntityWitch) {
			if (rand.nextInt(tiredSporesDropRate) == 0)
				event.entityLiving.entityDropItem(ItemPlugin.tiredSpores.getStack(rand.nextInt(2) + 1), 0.0F);
			if (rand.nextInt(remedySalveDropRate) == 0)
				event.entityLiving.entityDropItem(ItemPlugin.remedySalve.getStack(rand.nextInt(2) + 1), 0.0F);
		}

		if (event.entityLiving instanceof EntitySpider) {
			if (rand.nextInt(spiderFangDropRate) == 0)
				event.entityLiving.entityDropItem(ItemPlugin.spiderFang.getStack(rand.nextInt(1) + 1), 0.0F);
		}

		if (event.entityLiving instanceof EntitySilverfish) {
			if (rand.nextInt(steelScalesDropRate) == 0)
				event.entityLiving.entityDropItem(ItemPlugin.steelScales.getStack(rand.nextInt(3) + 1), 0.0F);
		}

		if (event.entityLiving instanceof EntityBlaze) {
			if (rand.nextInt(heartBlazeDropRate) == 0)
				event.entityLiving.entityDropItem(ItemPlugin.heartBlaze.getStack(), 0.0F);
		}
	}

}
