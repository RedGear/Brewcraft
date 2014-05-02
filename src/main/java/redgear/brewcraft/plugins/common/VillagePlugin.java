package redgear.brewcraft.plugins.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.ItemStackUtil;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;
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

	}

	@Override
	public void Init(ModUtils mod) {
		VillagerRegistry.instance().registerVillagerId(Brewcraft.inst.getInt("General", "Villager Profession ID", 15));
	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
