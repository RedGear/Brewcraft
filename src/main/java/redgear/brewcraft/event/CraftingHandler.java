package redgear.brewcraft.event;

import net.minecraft.item.Item;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.common.AchievementPlugin;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class CraftingHandler {

	private static CraftingHandler instance;

	private CraftingHandler() {

	}

	public static CraftingHandler register() {
		if (instance == null) {
			instance = new CraftingHandler();
			FMLCommonHandler.instance().bus().register(instance);

		}
		return instance;
	}

	@SubscribeEvent
	public void collectBrewery(final PlayerEvent.ItemCraftedEvent event) {
		if (event.crafting.equals(Item.getItemFromBlock(Brewcraft.brewery.getBlock())))
			event.player.addStat(AchievementPlugin.craftBrewery, 1);
	}
}
