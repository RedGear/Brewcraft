package redgear.brewcraft.plugins.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import cpw.mods.fml.common.LoaderState.ModState;

public class AchievementPlugin implements IPlugin {

	public static Achievement craftBrewery;
	public static Achievement explode;
	public static Achievement freeze;
	public static Achievement fireproof;
	public static Achievement flight;
	public static Achievement immunity;
	public static Achievement holywater;
	public static Achievement flame;
	public static Achievement fireEater;

	public static AchievementPage pageBrewcraft;

	@Override
	public String getName() {
		return "AchievementPlugin";
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
		if (Brewcraft.inst.getBoolean("General", "Toggle Achievements", true)) {
			craftBrewery = new Achievement("craftBrewery", "craftBrewery", 0, 0, Brewcraft.brewery.getStack(), null)
					.registerStat().setSpecial();
			explode = new Achievement("potionExplode", "potionExplode", 1, 3, Items.gunpowder, craftBrewery)
					.registerStat();
			freeze = new Achievement("potionFreeze", "potionFreeze", -2, 4, Items.snowball, craftBrewery)
					.registerStat();
			fireproof = new Achievement("potionFireproof", "potionFireproof", 2, -2, Blocks.netherrack, craftBrewery)
					.registerStat();
			flight = new Achievement("potionFlight", "potionFlight", 4, 1, IngredientPlugin.goldenFeather.getStack(),
					craftBrewery).registerStat();
			immunity = new Achievement("potionImmunity", "potionImmunity", -1, 2, Items.milk_bucket, craftBrewery)
					.registerStat();
			holywater = new Achievement("potionHolyWater", "potionHolyWater", -4, 0,
					IngredientPlugin.holyDust.getStack(), craftBrewery).registerStat();
			flame = new Achievement("potionFlame", "potionFlame", 6, 2,
					new ItemStack(Items.fire_charge), craftBrewery).registerStat();
			fireEater = new Achievement("potionFireEater", "potionFireEater", 5, -3,
					IngredientPlugin.heartBlaze.getStack(), craftBrewery).registerStat();

			pageBrewcraft = new AchievementPage("Brewcraft", craftBrewery, explode, freeze, fireproof, flight,
					immunity, holywater, flame, fireEater);

			AchievementPage.registerAchievementPage(pageBrewcraft);
		}
	}

	@Override
	public void postInit(ModUtils mod) {

	}

}
