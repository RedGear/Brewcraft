package redgear.brewcraft.plugins.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import cpw.mods.fml.common.LoaderState.ModState;

public class AchievementPlugin implements IPlugin{
	
	public static Achievement craftBrewery;
	public static Achievement explode;
	public static Achievement freeze;
	public static Achievement fireproof;
	public static Achievement flight;
	public static Achievement immunity;
	public static Achievement holywater;
	
	public static AchievementPage pageBrewcraft;

	@Override
	public String getName() {
		return "Brewcraft|AchievementPlugin";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils mod) {
		
	}

	@Override
	public void Init(ModUtils mod) {
		if (Brewcraft.inst.getBoolean("Plugins", "Achievement Plugin", "Toggle Achievement Plugin", true)) {
			/**
			 * @param String - The ID of the achievement.
			 * @paran String - The name of the achievement.
			 * @param int - the X coord of the achievement on the GUI.
			 * @param int - the Y coord of the achievement on the GUI.
			 * @param ItemStack - the icon for the achievement.
			 * @param Achievement - the precursor for the achievement.
			 */
		
			craftBrewery = new Achievement("craftBrewery", "craftBrewery", 0, 0, Brewcraft.brewery.getStack(), null)
				.registerStat().setSpecial();
			explode = new Achievement("potionExplode", "potionExplode", 1, 3, Items.gunpowder, craftBrewery)
				.registerStat();
			freeze = new Achievement("potionFreeze", "potionFreeze", -2, 4, Items.snowball, craftBrewery)
				.registerStat();
			fireproof = new Achievement("potionFireproof", "potionFireproof", 2, -2, Blocks.netherrack, craftBrewery)
				.registerStat();
			flight = new Achievement("potionFlight", "potionFlight", 4, 1, Brewcraft.goldenFeather.getStack(), craftBrewery)
				.registerStat();
			immunity = new Achievement("potionImmunity", "potionImmunity", -1, 2, Items.milk_bucket, craftBrewery)
				.registerStat();
			holywater = new Achievement("potionHolyWater", "potionHolyWater", -4, 0, Brewcraft.holyDust.getStack(), craftBrewery)
				.registerStat();
		
			pageBrewcraft = new AchievementPage("Brewcraft", craftBrewery, explode, freeze, fireproof, flight,
				immunity, holywater);
		
			AchievementPage.registerAchievementPage(pageBrewcraft);
		}
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
