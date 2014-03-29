package redgear.brewcraft.plugins.compat;

import net.minecraft.item.ItemStack;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.brewcraft.plugins.common.PotionPlugin;
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
		if (Brewcraft.inst.getBoolean("Plugins", "Forestry Plugin", "Toggle Forestry Plugin", true)) {
			if (Mods.Forestry.isIn()) {
				BackpackManager.backpackItems[HUNTER].add(IngredientPlugin.goldenFeather.getStack());
				BackpackManager.backpackItems[HUNTER].add(IngredientPlugin.charredBone.getStack());
				BackpackManager.backpackItems[HUNTER].add(IngredientPlugin.obsidianTear.getStack());
				BackpackManager.backpackItems[ADVENTURER].add(IngredientPlugin.holyDust.getStack());
				BackpackManager.backpackItems[ADVENTURER].add(Brewcraft.brewery.getStack());
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
