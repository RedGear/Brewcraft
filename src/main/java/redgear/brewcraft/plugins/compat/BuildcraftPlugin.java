package redgear.brewcraft.plugins.compat;

import buildcraft.api.fuels.IronEngineFuel;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import cpw.mods.fml.common.LoaderState.ModState;

public class BuildcraftPlugin implements IPlugin {

	int power = 12;
	int time = 16000;

	@Override
	public String getName() {
		return "BuildcraftPlugin";
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
		if (Brewcraft.inst.getBoolean("Plugins", "Buildcraft Plugin", true)) {
			if (Mods.BCCore.isIn()) {
				IronEngineFuel.addFuel(PotionPlugin.fluidBoom.getFluid(), power, time);
				IronEngineFuel.addFuel(PotionPlugin.fluidBoomII.getFluid(), power * 2, time / 2);
				IronEngineFuel.addFuel(PotionPlugin.fluidBoomIII.getFluid(), power * 3, time / 3);
				IronEngineFuel.addFuel(PotionPlugin.fluidBoomLong.getFluid(), power / 2, time * 2);
				IronEngineFuel.addFuel(PotionPlugin.fluidBoomVeryLong.getFluid(), power / 3, time * 3);
			}
		}
	}

	@Override
	public void postInit(ModUtils mod) {

	}

}
