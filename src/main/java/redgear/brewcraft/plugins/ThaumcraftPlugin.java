package redgear.brewcraft.plugins;

import cpw.mods.fml.common.LoaderState.ModState;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;

public class ThaumcraftPlugin implements IPlugin{

	@Override
	public String getName() {
		return "Brewcraft|ThaumcraftPlugin";
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
		if(Mods.Thaum.isIn() && Brewcraft.inst.getBoolean("Plugins",
				"Thaumcraft 4 Plugin", "Toggle Thaumcraft 4 Plugin", true)) {
			
		}
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
