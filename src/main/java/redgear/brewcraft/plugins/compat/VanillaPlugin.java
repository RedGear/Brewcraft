package redgear.brewcraft.plugins.compat;

import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.ItemPlugin;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.CoreDungeonLoot;
import redgear.core.util.CoreDungeonLoot.LootRarity;
import cpw.mods.fml.common.LoaderState.ModState;

public class VanillaPlugin implements IPlugin {

	@Override
	public String getName() {
		return "VanillaPlugin";
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
		if (Brewcraft.inst.getBoolean("General", "Golden Feather Dungeon Loot", true))
			CoreDungeonLoot.addLootToDungeons(ItemPlugin.goldenFeather.getStack(), LootRarity.RARE);
	}

	@Override
	public void postInit(ModUtils mod) {

	}

}
