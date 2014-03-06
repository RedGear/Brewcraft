package redgear.brewcraft.plugins;

import buildcraft.api.fuels.IronEngineFuel;
import cpw.mods.fml.common.LoaderState.ModState;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;

public class BuildcraftPlugin implements IPlugin{
	
	int power = 12;
	int time = 16000;

	@Override
	public String getName() {
		return "Brewcraft|BuildcraftPlugin";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return false;
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
		if(Mods.BCCore.isIn() && Brewcraft.inst.getBoolean("Plugins", 
				"Buildcraft Plugin", "Toggle Buildcraft Plugin", true)) {
			IronEngineFuel.addFuel(Brewcraft.fluidBoom, power, time);
			IronEngineFuel.addFuel(Brewcraft.fluidBoomII, power * 2, time * 2 / 3);
			IronEngineFuel.addFuel(Brewcraft.fluidBoomIII, power * 4, time / 4);
			IronEngineFuel.addFuel(Brewcraft.fluidBoomLong, power * 2 / 3, time * 3);
		}
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
