package redgear.brewcraft.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import redgear.brewcraft.blocks.brewery.GuiBrewery;
import redgear.brewcraft.recipes.BreweryRecipe;

public class NEIBrewcraftPluginConfig implements IConfigureNEI {

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
		return "0.2";
	}

}
