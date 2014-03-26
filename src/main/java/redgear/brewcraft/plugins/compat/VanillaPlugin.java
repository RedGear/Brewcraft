package redgear.brewcraft.plugins.compat;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.CoreDungeonLoot;
import redgear.core.util.CoreTradeHandler;
import redgear.core.util.SimpleItem;
import redgear.core.util.CoreDungeonLoot.LootRarity;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;

public class VanillaPlugin implements IPlugin{

	@Override
	public String getName() {
		return "Brewcraft|VanillaPlugin";
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
		if (Brewcraft.inst.getBoolean("Dungeon Loot", "Golden Feather Dungeon Loot", "Toggle Golden Feather as Dungeon Loot", true))
			CoreDungeonLoot.addLootToDungeons(IngredientPlugin.goldenFeather.getStack(), LootRarity.RARE);
		if (Brewcraft.inst.getBoolean("Global", "Golden Feather Villager Trades", "Toggle Golden Feather Villager Trades", true))
			CoreTradeHandler.addTradeRecipe(2, new ItemStack(Items.emerald, 7, 0), IngredientPlugin.goldenFeather.getStack());
		if (Brewcraft.inst.getBoolean("Global", "Blessed Powder Villager Trades", "Toggle Blessed Powder Villager Trades", true))
			CoreTradeHandler.addTradeRecipe(2, new ItemStack(Items.emerald, 11, 0), IngredientPlugin.holyDust.getStack());

	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
