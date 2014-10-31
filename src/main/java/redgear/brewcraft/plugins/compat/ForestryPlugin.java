package redgear.brewcraft.plugins.compat;

import net.minecraft.item.ItemStack;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.block.KegPlugin;
import redgear.brewcraft.plugins.block.MachinePlugin;
import redgear.brewcraft.plugins.item.ItemPlugin;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import cpw.mods.fml.common.LoaderState.ModState;
import forestry.api.storage.BackpackManager;

public class ForestryPlugin implements IPlugin {

	private int HUNTER = 3;
	private int ADVENTURER = 4;

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils mod) {

	}

	@Override
	public void Init(ModUtils mod) {
		if (Brewcraft.inst.getBoolean("Plugins", "Forestry Plugin", true)) {
			if (Mods.Forestry.isIn()) {
				BackpackManager.backpackItems[HUNTER].add(ItemPlugin.goldenFeather.getStack());
				BackpackManager.backpackItems[HUNTER].add(ItemPlugin.charredBone.getStack());
				BackpackManager.backpackItems[HUNTER].add(ItemPlugin.remedySalve.getStack());
				BackpackManager.backpackItems[HUNTER].add(ItemPlugin.spiderFang.getStack());
				BackpackManager.backpackItems[HUNTER].add(ItemPlugin.steelScales.getStack());
				BackpackManager.backpackItems[HUNTER].add(ItemPlugin.tiredSpores.getStack());
				BackpackManager.backpackItems[HUNTER].add(new ItemStack(ItemPlugin.tears));
				BackpackManager.backpackItems[HUNTER].add(new ItemStack(ItemPlugin.hearts));
				BackpackManager.backpackItems[ADVENTURER].add(ItemPlugin.holyDust.getStack());
				BackpackManager.backpackItems[ADVENTURER].add(MachinePlugin.brewery.getStack());
				BackpackManager.backpackItems[ADVENTURER].add(MachinePlugin.sprayer.getStack());
				BackpackManager.backpackItems[ADVENTURER].add(new ItemStack(KegPlugin.kegs));
				BackpackManager.backpackItems[ADVENTURER].add(new ItemStack(PotionPlugin.potions));
			}
		}
	}

	@Override
	public void postInit(ModUtils mod) {

	}

	@Override
	public String getName() {
		return "ForestryPlugin";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}

}
