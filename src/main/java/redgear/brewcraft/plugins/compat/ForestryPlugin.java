package redgear.brewcraft.plugins.compat;

import net.minecraft.item.ItemStack;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.brewcraft.plugins.common.KegPlugin;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import cpw.mods.fml.common.LoaderState.ModState;
import forestry.api.storage.BackpackManager;

public class ForestryPlugin implements IPlugin {

	private int HUNTER = 3;
	private int ADVENTURER = 4;

	private final ItemStack[] hunterItems = new ItemStack[] {IngredientPlugin.goldenFeather.getStack(),
			IngredientPlugin.charredBone.getStack(), IngredientPlugin.remedySalve.getStack(),
			IngredientPlugin.spiderFang.getStack(), IngredientPlugin.steelScales.getStack(),
			IngredientPlugin.tiredSpores.getStack(), new ItemStack(IngredientPlugin.tears),
			new ItemStack(IngredientPlugin.hearts) };
	private final ItemStack[] adventurerItems = new ItemStack[] {IngredientPlugin.holyDust.getStack(),
			Brewcraft.brewery.getStack(), Brewcraft.sprayer.getStack(), new ItemStack(KegPlugin.kegs) };

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
				for (ItemStack stack : hunterItems.clone())
					BackpackManager.backpackItems[HUNTER].add(stack);

				for (ItemStack stack : adventurerItems.clone())
					BackpackManager.backpackItems[ADVENTURER].add(stack);
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
