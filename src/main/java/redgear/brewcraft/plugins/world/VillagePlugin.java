package redgear.brewcraft.plugins.world;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.village.ComponentWitchHut;
import redgear.brewcraft.village.VillageWitchHutHandler;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class VillagePlugin implements IPlugin {

	@Override
	public String getName() {
		return "VillagePlugin";
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
		VillagerRegistry.instance().registerVillagerId(Brewcraft.inst.getInt("General", "Witch Profession ID", 15));

		VillagerRegistry.instance().registerVillageCreationHandler(new VillageWitchHutHandler());
		if (Brewcraft.inst.getBoolean("General", "Witch Hut Generation", true)) {
			try {
				MapGenStructureIO.func_143031_a(ComponentWitchHut.class, "redgear_brewcraft:VillagerWitchHutStructure");
			} catch (Throwable e) {
				Brewcraft.inst.myLogger.error("Error registering witch hut: " + e.getStackTrace());
			}
		}
	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
