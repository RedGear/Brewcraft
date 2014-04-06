package redgear.brewcraft.event;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class TradeHandler implements IVillageTradeHandler {

	/**
	 * This is called EVERY time a villager's trade window is opened. Only
	 * causes changes if it is 1) the first time the villager's trade window
	 * has been opened, or 2) a situation when a new trade is unlocked.
	 */

	private static TradeHandler instance;

	public static TradeHandler register() {
		if (instance == null) {
			instance = new TradeHandler();
			for (int i = 0; i < 5; ++i)
				VillagerRegistry.instance().registerVillageTradeHandler(i, instance);

		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		if (villager.getProfession() == 2) {
			if (Brewcraft.inst.getBoolean("World", "Blessed Powder Priest Trade", "Toggle Blessed Powder Trade", true)) {
				recipeList.add(new MerchantRecipe(IngredientPlugin.holyDust.getStack(random.nextInt(1) + 2),
						new ItemStack(Items.emerald, random.nextInt(4) + 1)));
				recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(5) + 3),
						IngredientPlugin.holyDust.getStack(random.nextInt(3) + 1)));
			}
			if (Brewcraft.inst.getBoolean("World", "Pure Tear Priest Trade", "Toggle Pure Tear Trade", true)) {
				if (villager.worldObj.getWorldTime() > 14000) {
					recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(5) + 4),
							new ItemStack(IngredientPlugin.pureTear, villager.worldObj.rand.nextInt(1) + 1)));
				}
			}
		}
	}
}
