package redgear.brewcraft.plugins.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.block.MachinePlugin;
import redgear.brewcraft.plugins.item.IngredientPlugin;
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
			craftBrewery = new Achievement("CraftBrewery", "brewcraft.brewery", 0, 0,
					MachinePlugin.brewery.getStack(), null).registerStat().setSpecial();
			explode = new Achievement("PotionExplode", "brewcraft.explode", 1, 3, Items.gunpowder,
					craftBrewery).registerStat();
			freeze = new Achievement("PotionFreeze", "brewcraft.freeze", -2, 4, Items.snowball,
					craftBrewery).registerStat();
			fireproof = new Achievement("PotionFireproof", "brewcraft.fireproof", 2, -2,
					Blocks.netherrack, craftBrewery).registerStat();
			flight = new Achievement("PotionFlight", "brewcraft.flight", 4, 1,
					IngredientPlugin.goldenFeather.getStack(), craftBrewery).registerStat();
			immunity = new Achievement("PotionImmunity", "brewcraft.immunity", -1, 2,
					Items.milk_bucket, craftBrewery).registerStat();
			holywater = new Achievement("PotionHolyWater", "brewcraft.holyWater", -4, 0,
					IngredientPlugin.holyDust.getStack(), craftBrewery).registerStat();
			flame = new Achievement("PotionFlame", "brewcraft.flame", 6, 2, new ItemStack(
					Items.fire_charge), craftBrewery).registerStat();
			fireEater = new Achievement("PotionFireEater", "brewcraft.fireEater", 5, -3,
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
