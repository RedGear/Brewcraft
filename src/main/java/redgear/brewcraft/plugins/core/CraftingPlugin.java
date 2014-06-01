package redgear.brewcraft.plugins.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.block.KegPlugin;
import redgear.brewcraft.plugins.block.MachinePlugin;
import redgear.brewcraft.plugins.item.IngredientPlugin;
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
		if (Brewcraft.inst.getBoolean("Recipes", "Golden Feather Crafting Recipe", true))
			GameRegistry.addShapedRecipe(IngredientPlugin.goldenFeather.getStack(), new Object[] {"!!!", "!@!", "!!!",
					Character.valueOf('!'), Items.gold_nugget, Character.valueOf('@'), Items.feather });

		if (Brewcraft.inst.getBoolean("Recipes", "Heart Medallion Crafting Recipe", true))
			GameRegistry.addShapedRecipe(
					IngredientPlugin.heartGold.getStack(),
					new Object[] {"!!!", "!@!", "!!!", Character.valueOf('!'), Items.gold_nugget,
							Character.valueOf('@'), IngredientPlugin.heartSmall.getItem() });

		if (Brewcraft.inst.getBoolean("Recipes", "Tired Spores Crafting Recipe", true))
			GameRegistry.addShapelessRecipe(IngredientPlugin.tiredSpores.getStack(3), new Object[] {
					Blocks.brown_mushroom, Blocks.red_mushroom, Items.spider_eye });

		if (Brewcraft.inst.getBoolean("Recipes", "Medicinal Salve Crafting Recipe", true))
			GameRegistry.addShapelessRecipe(IngredientPlugin.remedySalve.getStack(3), new Object[] {Items.paper,
					Items.sugar, Items.redstone });

		if (Brewcraft.inst.getBoolean("Recipes", "Splash Bottle Crafting Recipe", true))
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

		if (Brewcraft.inst.getBoolean("Recipes", "Charred Bone Crafting Recipe", true))
			GameRegistry.addSmelting(Items.bone, IngredientPlugin.charredBone.getStack(), 0.1F);

		if (Brewcraft.inst.getBoolean("Recipes", "Steel Scales Smelting Recipe", true)) {
			GameRegistry.addSmelting(Items.chainmail_boots, IngredientPlugin.steelScales.getStack(4), 0.1F);
			GameRegistry.addSmelting(Items.chainmail_chestplate, IngredientPlugin.steelScales.getStack(8), 0.3F);
			GameRegistry.addSmelting(Items.chainmail_helmet, IngredientPlugin.steelScales.getStack(5), 0.2F);
			GameRegistry.addSmelting(Items.chainmail_leggings, IngredientPlugin.steelScales.getStack(7), 0.3F);
		}

		addKegRecipe(KegPlugin.kegOak.getStack(), new ItemStack(Blocks.planks, 1, 0));
		addKegRecipe(KegPlugin.kegSpruce.getStack(), new ItemStack(Blocks.planks, 1, 1));
		addKegRecipe(KegPlugin.kegBirch.getStack(), new ItemStack(Blocks.planks, 1, 2));
		addKegRecipe(KegPlugin.kegJungle.getStack(), new ItemStack(Blocks.planks, 1, 3));
		addKegRecipe(KegPlugin.kegAcacia.getStack(), new ItemStack(Blocks.planks, 1, 4));
		addKegRecipe(KegPlugin.kegDark.getStack(), new ItemStack(Blocks.planks, 1, 5));
		addKegRecipe(KegPlugin.kegIron.getStack(), new ItemStack(Items.iron_ingot));
		//addKegRecipe(KegPlugin.kegSealed.getStack(), ?);
		//addKegRecipe(KegPlugin.kegPlated.getStack(), ?);
	}

	@Override
	public void postInit(ModUtils mod) {

	}

	private boolean breweryRecipe(String ingot) {
		ItemStack metal = ItemStackUtil.getOreWithName(ingot);

		if (metal != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(MachinePlugin.brewery.getStack(), new Object[] {"! !", "!@!", "#!#",
					Character.valueOf('!'), ingot, Character.valueOf('@'), Items.brewing_stand, Character.valueOf('#'),
					Items.cauldron }));
			return true;
		} else
			return false;
	}

	private void addKegRecipe(ItemStack output, ItemStack material) {
		if (Brewcraft.inst.getBoolean("Recipes", output.getDisplayName() + " Recipe", true))
			GameRegistry.addShapedRecipe(output, new Object[] {"!!!", "@@@", "!!!", Character.valueOf('!'), material,
					Character.valueOf('@'), Items.iron_ingot });
	}
}
