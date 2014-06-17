package redgear.brewcraft.event;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.ItemPlugin;
import redgear.brewcraft.plugins.item.PotionPlugin;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class TradeHandler implements IVillageTradeHandler {

	private static TradeHandler instance;

	public static TradeHandler register() {
		if (instance == null) {
			instance = new TradeHandler();
			for (int i = 0; i < 5; ++i)
				VillagerRegistry.instance().registerVillageTradeHandler(i, instance);
			VillagerRegistry.instance().registerVillageTradeHandler(
					Brewcraft.inst.getInt("General", "Witch Profession ID", 15), instance);

		}
		return instance;
	}

	public boolean powderTrade = Brewcraft.inst.getBoolean("General", "Blessed Powder Priest Trade", true);

	@SuppressWarnings("unchecked")
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		if (villager.getProfession() == 2) {
			if (powderTrade) {
				recipeList.add(new MerchantRecipe(ItemPlugin.holyDust.getStack(random.nextInt(1) + 2),
						new ItemStack(Items.emerald, random.nextInt(4) + 1)));
				recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(5) + 3),
						ItemPlugin.holyDust.getStack(random.nextInt(3) + 1)));
			}

			if (villager.worldObj.getWorldTime() > 13000)
				recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(5) + 4),
						ItemPlugin.pureTear.getStack(random.nextInt(1) + 1)));
		}

		if (villager.getProfession() == 0) {
			recipeList.add(new MerchantRecipe(ItemPlugin.heartSmall.getStack(random.nextInt(3) + 1),
					new ItemStack(Items.emerald, random.nextInt(5) + 3)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(5) + 1),
					ItemPlugin.heartSmall.getStack(random.nextInt(3) + 1)));
		}

		if (villager.getProfession() == Brewcraft.inst.getInt("General", "Villager Profession ID", 15)) {

			//All base level potions.
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 3)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1))));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 3)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 10)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 20)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 6)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 30)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 36)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 6)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 46)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 6)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 52)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 8)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 62)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 8)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 72)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 82)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 9)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 92)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 9)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 102)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 8)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 112)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 6)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 118)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 7)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 130)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 140)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 7)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 146)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 7)),
					new ItemStack(PotionPlugin.potions, 1, random.nextInt(1) + 152)));

			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(Items.potionitem, 1, 8193)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(Items.potionitem, 1, 16385)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 8194)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 16386)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 3)),
					new ItemStack(Items.potionitem, 1, 8227)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 3)),
					new ItemStack(Items.potionitem, 1, 16419)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(Items.potionitem, 1, 8196)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(Items.potionitem, 1, 16388)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 8261)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 16453)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 6)),
					new ItemStack(Items.potionitem, 1, 8230)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 6)),
					new ItemStack(Items.potionitem, 1, 16422)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 2)),
					new ItemStack(Items.potionitem, 1, 8232)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 16424)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(Items.potionitem, 1, 8201)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(Items.potionitem, 1, 16393)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 2)),
					new ItemStack(Items.potionitem, 1, 8234)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 16426)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 2)),
					new ItemStack(Items.potionitem, 1, 8268)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 5)),
					new ItemStack(Items.potionitem, 1, 16460)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 8237)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 16429)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 8238)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 4)),
					new ItemStack(Items.potionitem, 1, 16430)));

			//Most ingredients.
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 1)),
					new ItemStack(Items.spider_eye, random.nextInt(2) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(3) + 2)),
					new ItemStack(Items.magma_cream, 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 3)),
					new ItemStack(Items.speckled_melon, 2)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 4)),
					new ItemStack(Items.blaze_powder, random.nextInt(2) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(3) + 1)),
					new ItemStack(Items.sugar, random.nextInt(2) + 3)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(3) + 4)),
					new ItemStack(Items.ghast_tear, random.nextInt(2) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 3)),
					new ItemStack(Items.fermented_spider_eye, random.nextInt(2) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 4)),
					new ItemStack(Items.golden_carrot, random.nextInt(2) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 3)),
					new ItemStack(Items.redstone, random.nextInt(3) + 4)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 3)),
					new ItemStack(Items.glowstone_dust, random.nextInt(3) + 4)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(5) + 3)),
					new ItemStack(Items.nether_wart, random.nextInt(2) + 2)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 2)),
					ItemPlugin.goldenFeather.getStack(random.nextInt(2) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(3) + 1)),
					ItemPlugin.charredBone.getStack(random.nextInt(4) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 2)),
					ItemPlugin.spiderFang.getStack()));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 4)),
					ItemPlugin.tiredSpores.getStack(random.nextInt(2) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 3)),
					ItemPlugin.remedySalve.getStack(random.nextInt(2) + 1)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 2)),
					ItemPlugin.splashBottle.getStack(3)));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 6)),
					ItemPlugin.heartGold.getStack()));
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, random.nextInt(random.nextInt(4) + 4)),
					ItemPlugin.steelScales.getStack(random.nextInt(2) + 3)));

		}
	}
}
