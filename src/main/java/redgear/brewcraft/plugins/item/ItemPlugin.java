package redgear.brewcraft.plugins.item;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.items.ItemHeart;
import redgear.brewcraft.items.ItemTear;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.common.LoaderState.ModState;

public class ItemPlugin implements IPlugin {

	public static MetaItem<SubItem> ingredients;
	public static SimpleItem holyDust;
	public static SimpleItem goldenFeather;
	public static SimpleItem charredBone;
	public static SimpleItem spiderFang;
	public static SimpleItem tiredSpores;
	public static SimpleItem remedySalve;
	public static SimpleItem steelScales;
	
	public static MetaItem<SubItem> misc;
	public static SimpleItem splashBottle;
	public static SimpleItem emptyVial;
	public static SimpleItem splashVial;
	public static SimpleItem emptyBigBottle;
	public static SimpleItem splashBigBottle;
	public static SimpleItem waterSplashBottle;
	public static SimpleItem waterVial;
	public static SimpleItem waterSplashVial;
	public static SimpleItem waterBigBottle;
	public static SimpleItem waterSplashBigBottle;
	public static SimpleItem metalRing;

	public static ItemTear tears;
	public static SimpleItem obsidianTear;
	public static SimpleItem pureTear;

	public static ItemHeart hearts;
	public static SimpleItem heartGold;
	public static SimpleItem heartSmall;
	public static SimpleItem heartBlaze;

	@Override
	public String getName() {
		return "ItemPlugin";
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
		ingredients = new MetaItem<SubItem>("RedGear.Brewcraft.Ingredients");
		holyDust = ingredients.addMetaItem(new SubItem("holydust"));
		goldenFeather = ingredients.addMetaItem(new SubItem("goldenfeather"));
		charredBone = ingredients.addMetaItem(new SubItem("charredbone"));
		spiderFang = ingredients.addMetaItem(new SubItem("spiderfang"));
		tiredSpores = ingredients.addMetaItem(new SubItem("tiredspores"));
		remedySalve = ingredients.addMetaItem(new SubItem("remedysalve"));
		steelScales = ingredients.addMetaItem(new SubItem("steelscales"));
		
		misc = new MetaItem<SubItem>("RedGear.Brewcraft.Misc");
		splashBottle = misc.addMetaItem(new SubItem("splashbottle"));
		emptyVial = misc.addMetaItem(new SubItem("vial"));
		splashVial = misc.addMetaItem(new SubItem("vialsplash"));
		emptyBigBottle = misc.addMetaItem(new SubItem("bigbottle"));
		splashBigBottle = misc.addMetaItem(new SubItem("bigsplash"));
		waterSplashBottle = misc.addMetaItem(new SubItem("watersplashbottle"));
		waterVial = misc.addMetaItem(new SubItem("watervial"));
		waterSplashVial = misc.addMetaItem(new SubItem("watersplashvial"));
		waterBigBottle = misc.addMetaItem(new SubItem("waterbigbottle"));
		waterSplashBigBottle = misc.addMetaItem(new SubItem("watersplashbigbottle"));
		metalRing = misc.addMetaItem(new SubItem("metalring"));

		tears = new ItemTear("RedGear.Brewcraft.Tears");
		obsidianTear = tears.addMetaItem(new SubItem("obsidiantear"));
		pureTear = tears.addMetaItem(new SubItem("puretear"));

		hearts = new ItemHeart("RedGear.Brewcraft.Hearts");
		heartSmall = hearts.addMetaItem(new SubItem("heartsmall"));
		heartGold = hearts.addMetaItem(new SubItem("heartgold"));
		heartBlaze = hearts.addMetaItem(new SubItem("heartblaze"));

		ingredients.setCreativeTab(Brewcraft.tabMisc);
		misc.setCreativeTab(Brewcraft.tabMisc);

		FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 250), waterVial.getStack());
		FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 1000),
				waterSplashBottle.getStack());
		FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 250),
				waterSplashVial.getStack());
		FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 2000),
				waterSplashBigBottle.getStack());
		FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 2000),
				waterBigBottle.getStack());
	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
