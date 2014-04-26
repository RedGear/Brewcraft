package redgear.brewcraft.plugins.common;

import net.minecraft.util.DamageSource;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import cpw.mods.fml.common.LoaderState.ModState;

public class DamageSourcePlugin implements IPlugin {
	
	public static DamageSource fireEater;

	@Override
	public String getName() {
		return "DamageSourcePlugin";
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
		fireEater = new DamageSource("fireEater").setDamageBypassesArmor().setFireDamage();
	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
