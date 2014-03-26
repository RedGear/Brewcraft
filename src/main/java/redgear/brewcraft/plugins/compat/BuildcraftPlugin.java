package redgear.brewcraft.plugins.compat;

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
		if(Brewcraft.inst.getBoolean("Plugins", "Buildcraft Plugin",
				"Toggle Buildcraft Plugin", true)) {
			if(Mods.BCCore.isIn()) {
				
			}
		}
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}