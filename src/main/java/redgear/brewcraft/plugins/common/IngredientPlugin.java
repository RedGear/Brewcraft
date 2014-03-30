package redgear.brewcraft.plugins.common;

import cpw.mods.fml.common.LoaderState.ModState;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;

public class IngredientPlugin implements IPlugin{
	
	public static MetaItem ingredients;
	public static SimpleItem holyDust;
	public static SimpleItem goldenFeather;
	public static SimpleItem charredBone;
	public static SimpleItem obsidianTear;
	public static SimpleItem pureTear;
	public static SimpleItem splashBottle;
	@Override
	public String getName() {
		return "IngredientPlugin";
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
		ingredients = new MetaItem("RedGear.Brewcraft.Ingredients");
		holyDust = ingredients.addMetaItem(new SubItem("holydust"));
		goldenFeather = ingredients.addMetaItem(new SubItem("goldenfeather"));
		charredBone = ingredients.addMetaItem(new SubItem("charredbone"));
		obsidianTear = ingredients.addMetaItem(new SubItem("obsidiantear"));
		pureTear = ingredients.addMetaItem(new SubItem("puretear"));
		splashBottle = ingredients.addMetaItem(new SubItem("splashBottle"));
		
		obsidianTear.getItem().setMaxStackSize(8);
		pureTear.getItem().setMaxStackSize(8);
		ingredients.setCreativeTab(Brewcraft.tab);
	}
	@Override
	public void Init(ModUtils mod) {

	}
	@Override
	public void postInit(ModUtils mod) {

	}
}
