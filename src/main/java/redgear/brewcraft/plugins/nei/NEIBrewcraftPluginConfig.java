package redgear.brewcraft.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.LoaderState.ModState;
import redgear.brewcraft.blocks.brewery.GuiBrewery;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;

public class NEIBrewcraftPluginConfig implements IConfigureNEI{

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new NEIBreweryRecipeHandler());
		API.registerUsageHandler(new NEIBreweryRecipeHandler());
		API.setGuiOffset(GuiBrewery.class, 0, 0);
	}

	@Override
	public String getName() {
		return "Brewcraft NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "0.5";
	}
}
