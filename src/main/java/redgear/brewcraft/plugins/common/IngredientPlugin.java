package redgear.brewcraft.plugins.common;

import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.items.ItemHeart;
import redgear.brewcraft.items.ItemTear;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.common.LoaderState.ModState;

public class IngredientPlugin implements IPlugin {

	public static MetaItem ingredients;
	public static SimpleItem holyDust;
	public static SimpleItem goldenFeather;
	public static SimpleItem charredBone;
	public static SimpleItem spiderFang;
	public static SimpleItem tiredSpores;
	public static SimpleItem remedySalve;
	public static SimpleItem steelScales;
	public static SimpleItem splashBottle;
	
	public static ItemTear tears;
	public static SimpleItem obsidianTear;
	public static SimpleItem pureTear;
	
	public static ItemHeart hearts;
	public static SimpleItem heartGold;
	public static SimpleItem heartSmall;

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
		spiderFang = ingredients.addMetaItem(new SubItem("spiderfang"));
		tiredSpores = ingredients.addMetaItem(new SubItem("tiredspores"));
		remedySalve = ingredients.addMetaItem(new SubItem("remedysalve"));
		steelScales = ingredients.addMetaItem(new SubItem("steelscales"));
		splashBottle = ingredients.addMetaItem(new SubItem("splashBottle"));

		tears = new ItemTear("RedGear.Brewcraft.Tears");
		obsidianTear = tears.addMetaItem(new SubItem("obsidiantear"));
		pureTear = tears.addMetaItem(new SubItem("puretear"));
		
		hearts = new ItemHeart("RedGear.Brewcraft.Hearts");
		heartGold = hearts.addMetaItem(new SubItem("heartgold"));
		heartSmall = hearts.addMetaItem(new SubItem("heartsmall"));
		
		ingredients.setCreativeTab(Brewcraft.tab);
	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
