package redgear.brewcraft.plugins.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.ItemStackUtil;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingPlugin implements IPlugin {

	@Override
	public String getName() {
		return "CraftingPlugin";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public void preInit(ModUtils mod) {

	}

	@Override
	public void Init(ModUtils mod) {
		if (Brewcraft.inst.getBoolean("Recipes", "Golden Feather Recipe", "Toggle Golden Feather Recipe", true))
			GameRegistry.addShapedRecipe(IngredientPlugin.goldenFeather.getStack(), new Object[] {"!!!", "!@!", "!!!",
					Character.valueOf('!'), Items.gold_nugget, Character.valueOf('@'), Items.feather });

		if (Brewcraft.inst.getBoolean("Recipes", "Plagued Tear Recipe", "Toggle Plagued Tear Recipe", false))
			GameRegistry.addShapedRecipe(IngredientPlugin.obsidianTear.getStack(), new Object[] {"!!!", "!@!", "!!!",
					Character.valueOf('!'), Blocks.obsidian, Character.valueOf('@'), Items.ghast_tear });

		if (Brewcraft.inst.getBoolean("Recipes", "Splash Bottle Recipe", "Toggle Splash Bottle Recipe", true))
			GameRegistry.addShapedRecipe(IngredientPlugin.splashBottle.getStack(3), new Object[] {" @!", "@ @", " @ ",
					Character.valueOf('!'), Items.gunpowder, Character.valueOf('@'), Blocks.glass });

		boolean ironOverride = false;

		if (Brewcraft.inst.getBoolean("Recipes", "Lead Brewery", "Toggle crafting the Brewery with Lead if available"))
			ironOverride = breweryRecipe("ingotLead");

		if (Brewcraft.inst
				.getBoolean("Recipes", "Brass Brewery", "Toggle crafting the Brewery with Brass if available"))
			ironOverride = ironOverride || breweryRecipe("ingotBrass");

		if (!(ironOverride && !Brewcraft.inst.getBoolean("Recipes", "Iron Brewery",
				"Toggle crafting the Brewery with Iron. (Can only be disabled if Lead or Brass is available)")))
			breweryRecipe("ingotIron"); //Dat boolean expression!

		if (Brewcraft.inst.getBoolean("Recipes", "Charred Bone Recipe", "Toggle Charred Bone Smelting Recipe", true))
			GameRegistry.addSmelting(Items.bone, IngredientPlugin.charredBone.getStack(), 0.1F);
	}

	@Override
	public void postInit(ModUtils mod) {

	}

	private boolean breweryRecipe(String ingot) {
		ItemStack metal = ItemStackUtil.getOreWithName(ingot);

		if (metal != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(Brewcraft.brewery.getStack(), new Object[] {"! !", "!@!", "#!#",
					Character.valueOf('!'), ingot, Character.valueOf('@'), Items.brewing_stand, Character.valueOf('#'),
					Items.cauldron }));
			return true;
		} else
			return false;
	}
}
