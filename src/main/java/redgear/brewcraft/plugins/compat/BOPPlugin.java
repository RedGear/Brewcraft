package redgear.brewcraft.plugins.compat;

import cpw.mods.fml.common.LoaderState.ModState;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;

public class BOPPlugin implements IPlugin{

	@Override
	public String getName() {
		return "Brewcraft|BOPPlugin";
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
		if(Brewcraft.inst.getBoolean("Plugins", "Biomes o' Plenty Plugin",
				"Toggle Biomes o' Plenty Plugin", true)) {
			if(Mods.BiomesOPlenty.isIn()) {
				
			}
		}
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
