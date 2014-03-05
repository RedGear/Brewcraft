package redgear.brewcraft.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.brewcraft.blocks.TileEntityBrewery;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.brewcraft.potions.effects.EffectAngel;
import redgear.brewcraft.potions.effects.EffectCreeper;
import redgear.brewcraft.potions.effects.EffectFlight;
import redgear.brewcraft.potions.effects.EffectFrozen;
import redgear.brewcraft.potions.effects.EffectImmunity;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.core.asm.RedGearCore;
import redgear.core.block.MetaTile;
import redgear.core.block.SubTile;
import redgear.core.fluids.FluidUtil;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.ModUtils;
import redgear.core.network.CoreGuiHandler;
import redgear.core.util.CoreDungeonLoot;
import redgear.core.util.CoreDungeonLoot.LootRarity;
import redgear.core.util.CoreTradeHandler;
import redgear.core.util.ItemStackUtil;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "redgear_brewcraft", name = "Brewcraft", version = "@ModVersion@", dependencies = "required-after:redgear_core;")
public class Brewcraft extends ModUtils {

	@Instance("redgear_brewcraft")
	public static ModUtils inst;
	
	@SidedProxy(clientSide = "redgear.brewcraft.common.BrewcraftClientProxy", serverSide = "redgear.brewcraft.common.BrewcraftCommonProxy")
	public static BrewcraftCommonProxy proxy;

	public static RecipeRegistry registry = new RecipeRegistry();

	public static MetaItem ingredients;
	public static SimpleItem holyDust;
	public static SimpleItem goldenFeather;
	public static SimpleItem charredBone;
	public static SimpleItem splashBottle;
	public static SimpleItem obsidianTear;

	public static SimpleItem emptyBottle = new SimpleItem(Items.glass_bottle);

	public static MetaItemPotion potions;

	public static MetaTile brewing;
	public static SimpleItem brewery;

	public static Fluid fluidHolyWater;
	public static Fluid fluidHolyWaterII;
	public static Fluid fluidHolyWaterLong;
	public static Fluid fluidHolyWaterIII;
	public static Fluid fluidFlying;
	public static Fluid fluidFlyingLong;
	public static Fluid fluidWither;
	public static Fluid fluidWitherLong;
	public static Fluid fluidWitherII;
	public static Fluid fluidWitherIII;
	public static Fluid fluidAntidote;
	public static Fluid fluidAntidoteII;
	public static Fluid fluidAntidoteLong;
	public static Fluid fluidAntidoteIII;
	public static Fluid fluidBoom;
	public static Fluid fluidBoomLong;
	public static Fluid fluidBoomII;
	public static Fluid fluidBoomIII;
	public static Fluid fluidAwkward;
	public static Fluid fluidVision;
	public static Fluid fluidVisionLong;
	public static Fluid fluidInvisible;
	public static Fluid fluidInvisibleLong;
	public static Fluid fluidRegen;
	public static Fluid fluidRegenLong;
	public static Fluid fluidRegenII;
	public static Fluid fluidRegenIII;
	public static Fluid fluidFast;
	public static Fluid fluidFastLong;
	public static Fluid fluidFastII;
	public static Fluid fluidFastIII;
	public static Fluid fluidWeakness;
	public static Fluid fluidStrength;
	public static Fluid fluidStrengthLong;
	public static Fluid fluidStrengthII;
	public static Fluid fluidStrengthIII;
	public static Fluid fluidFireResist;
	public static Fluid fluidFireResistLong;
	public static Fluid fluidFireResistII;
	public static Fluid fluidFireResistIII;
	public static Fluid fluidFireResistIIII;
	public static Fluid fluidSlowness;
	public static Fluid fluidSlownessLong;
	public static Fluid fluidPoison;
	public static Fluid fluidPoisonII;
	public static Fluid fluidPoisonIII;
	public static Fluid fluidPoisonLong;
	public static Fluid fluidHarm;
	public static Fluid fluidHarmII;
	public static Fluid fluidHarmIII;
	public static Fluid fluidHealing;
	public static Fluid fluidHealingII;
	public static Fluid fluidHealingIII;
	public static Fluid fluidFreezing;
	public static Fluid fluidFreezingLong;

	public static Potion angel;
	public static Potion flight;
	public static Potion creeper;
	public static Potion immunity;
	public static Potion frozen;
	public static Potion fireproof;

	public final static int DEFAULT_TIME = 7;
	public final static int FLUID_CONSUMPTION_BASE = 100;
	public final static int ITEM_CONSUMPTION_BASE = 4;

	@Override
	protected void PreInit(FMLPreInitializationEvent event) {

		if (getBoolean("Potion List Expansion", "Toggle Potion List Expansion",
				"Disable it you are running another mod that expands the list.", true))
			expandPotionList();

		EntityRegistry.registerModEntity(EntityBrewcraftPotion.class, "Potion",
				EntityRegistry.findGlobalUniqueEntityId(), RedGearCore.inst, 128, 10, true);
		
		proxy.registerRenders();

		ingredients = new MetaItem("RedGear.Brewcraft.Ingredients");
		holyDust = ingredients.addMetaItem(new SubItem("holydust"));
		goldenFeather = ingredients.addMetaItem(new SubItem("goldenfeather"));
		charredBone = ingredients.addMetaItem(new SubItem("charredbone"));
		splashBottle = ingredients.addMetaItem(new SubItem("splashBottle"));
		obsidianTear = ingredients.addMetaItem(new SubItem("obsidiantear"));

		potions = new MetaItemPotion("RedGear.Brewcraft.Potions");

		angel = new EffectAngel(getInt("Potion Effect IDs", "'Angelic' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 40));

		flight = new EffectFlight(getInt("Potion Effect IDs", "'Flight' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 41));

		creeper = new EffectCreeper(getInt("Potion Effect IDs", "'Combustion' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 42));

		immunity = new EffectImmunity(inst.getInt("Potion Effect IDs", "'Immunity' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 43));
		
		frozen = new EffectFrozen(inst.getInt("Potion Effect IDs", "'Frozen' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 44))
				.func_111184_a(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160891", -0.95000000596046448D, 2);
		
		fireproof = new EffectFrozen(inst.getInt("Potion Effect IDs", "'Fireproof' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 45));
		/*
		 * createSpecialPotion("Fire", new SubPotionEffect() {
		 * 
		 * @Override
		 * public void effect(World world, EntityLivingBase player) {
		 * player.setFire(10);
		 * }
		 * });
		 */

		fluidHolyWater = createPotion("HolyWater", "potionGold", angel, 100, 0);
		fluidHolyWaterII = createPotion("HolyWaterII", "potionGold", angel, 50, 1);
		fluidHolyWaterLong = createPotion("HolyWaterLong", "potionGold", angel, 200, 0);
		fluidHolyWaterIII = createPotion("HolyWaterIII", "potionGold", angel, 100, 2);
		fluidFlying = createPotion("Flying", "potionWhite", flight, 300, 0);
		fluidFlyingLong = createPotion("FlyingLong", "potionWhite", flight, 600, 0);
		fluidWither = createPotion("Wither", "potionBlack", Potion.wither, 400, 0);
		fluidWitherII = createPotion("WitherII", "potionBlack", Potion.wither, 200, 1);
		fluidWitherIII = createPotion("WitherIII", "potionBlack", Potion.wither, 200, 2);
		fluidWitherLong = createPotion("WitherLong", "potionBlack", Potion.wither, 800, 0);
		fluidAntidote = createPotion("Antidote", "potionDarkPurple", immunity, 600, 0);
		fluidAntidoteII = createPotion("AntidoteII", "potionDarkPurple", immunity, 300, 1);
		fluidAntidoteIII = createPotion("AntidoteIII", "potionDarkPurple", immunity, 300, 2);
		fluidAntidoteLong = createPotion("AntidoteLong", "potionDarkPurple", immunity, 1200, 0);
		fluidBoom = createPotion("Boom", "potionDarkGreen", creeper, 160, 0);
		fluidBoomII = createPotion("BoomII", "potionDarkGreen", creeper, 80, 1);
		fluidBoomIII = createPotion("BoomIII", "potionDarkGreen", creeper, 80, 2);
		fluidBoomLong = createPotion("BoomLong", "potionDarkGreen", creeper, 320, 0);	
		fluidFreezing = createPotion("Freezing", "potionCyan", frozen, 300, 0);
		fluidFreezingLong = createPotion("FreezingLong", "potionCyan", frozen, 600, 0);
		
		createSpecialPotion("Ghast", Potion.confusion, 400, 0);

		brewing = proxy.createBrewery();

		brewing.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);

		brewery = brewing.addMetaBlock(new SubTile("Brewery", TileEntityBrewery.class,
				CoreGuiHandler.addGuiMap("brewery", "Brewery")));

		fluidAwkward = createVanillaPotion("Awkward", "potionBlue", 16, 0);
		fluidVision = createVanillaPotion("Vision", "potionDarkBlue", 8198, 16390);
		fluidVisionLong = createVanillaPotion("VisionLong", "potionDarkBlue", 8262, 16454);
		fluidInvisible = createVanillaPotion("Invisible", "potionGrey", 8206, 16398);
		fluidInvisibleLong = createVanillaPotion("InvisibleLong", "potionGrey", 8270, 16462);
		fluidRegen = createVanillaPotion("Regen", "potionPink", 8193, 16385);
		fluidRegenII = createVanillaPotion("RegenII", "potionPink", 8289, 16481);
		fluidRegenLong = createVanillaPotion("RegenLong", "potionPink", 8257, 16449);
		fluidFast = createVanillaPotion("Fast", "potionLightBlue", 8194, 16386);
		fluidFastLong = createVanillaPotion("FastLong", "potionLightBlue", 8290, 16450);
		fluidFastII = createVanillaPotion("FastII", "potionLightBlue", 8258, 16418);
		fluidWeakness = createVanillaPotion("Weakness", "potionPurple", 8200, 16456);
		fluidStrength = createVanillaPotion("Strength", "potionMagenta", 8201, 16393);
		fluidStrengthLong = createVanillaPotion("StrengthLong", "potionMagenta", 8265, 16457);
		fluidStrengthII = createVanillaPotion("StrengthII", "potionMagenta", 8297, 16425);
		fluidFireResist = createVanillaPotion("FireResist", "potionLightPink", 8195, 16387);
		fluidFireResistLong = createVanillaPotion("FireResistLong", "potionLightPink", 8259, 16451);
		fluidSlowness = createVanillaPotion("Slowness", "potionPurple", 8202, 16394);
		fluidSlownessLong = createVanillaPotion("SlownessLong", "potionPurple", 8266, 16458);
		fluidPoison = createVanillaPotion("Poison", "potionGreen", 8196, 16388);
		fluidPoisonII = createVanillaPotion("PoisonII", "potionGreen", 8260, 16420);
		fluidPoisonLong = createVanillaPotion("PoisonLong", "potionGreen", 8228, 16452);
		fluidHarm = createVanillaPotion("Harm", "potionDarkPurple", 8204, 16396);
		fluidHarmII = createVanillaPotion("HarmII", "potionDarkPurple", 8236, 16428);
		fluidHealing = createVanillaPotion("Healing", "potionRed", 8196, 16389);
		fluidHealingII = createVanillaPotion("HealingII", "potionRed", 8229, 16421);
		
		fluidRegenIII = createPotion("RegenIII", "potionPink", Potion.regeneration, 20 * 60, 2);
		fluidFastIII = createPotion("FastIII", "potionLightBlue", Potion.moveSpeed, 20 * 180, 2);
		fluidStrengthIII = createPotion("StrengthIII", "potionMagenta", Potion.damageBoost, 20 * 180, 2);
		fluidFireResistII = createPotion("FireResistII", "potionLightPink", Potion.fireResistance, 20 * 180, 1);
		fluidFireResistIII = createPotion("FireResistIII", "potionLightPink", fireproof, 20 * 180, 2);
		fluidPoisonIII = createPotion("PoisonIII", "potionGreen", Potion.poison, 20 * 45, 2);
		fluidHarmIII = createPotion("HarmIII", "potionDarkPurple", Potion.harm, 20, 2);
		fluidHealingIII = createPotion("HealingIII", "potionRed", Potion.heal, 20, 2);

		brewery.getBlock().setCreativeTab(CreativeTabs.tabBrewing);
		ingredients.setCreativeTab(CreativeTabs.tabBrewing);

	}

	@Override
	protected void Init(FMLInitializationEvent event) {
		if (getBoolean("Global", "Mod Compatibility", "Toggle Mod Compatibility", true))
			BrewcraftCompatibility.run();
		recipes();

		if (getBoolean("Dungeon Loot", "Golden Feather Dungeon Loot", "Toggle Golden Feather as Dungeon Loot", true))
			CoreDungeonLoot.addLootToDungeons(goldenFeather.getStack(), LootRarity.RARE);
		if (getBoolean("Global", "Golden Feather Villager Trades", "Toggle Golden Feather Villager Trades", true))
			CoreTradeHandler.addTradeRecipe(2, new ItemStack(Items.emerald, 7, 0), goldenFeather.getStack());
		if (getBoolean("Global", "Blessed Powder Villager Trades", "Toggle Blessed Powder Villager Trades", true))
			CoreTradeHandler.addTradeRecipe(2, new ItemStack(Items.emerald, 11, 0), holyDust.getStack());

	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {

	}

	private void recipes() {

		registry.addRecipe(fluidRegen, fluidHolyWater, holyDust, ITEM_CONSUMPTION_BASE + 1);
		registry.addRecipe(fluidHolyWater, fluidHolyWaterII, Items.glowstone_dust);
		registry.addRecipe(fluidHolyWater, fluidHolyWaterLong, Items.redstone);
		registry.addRecipe(FluidRegistry.WATER, fluidFlying, goldenFeather, ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 1);
		registry.addRecipe(fluidFlying, fluidFlyingLong, Items.redstone);
		registry.addRecipe(FluidRegistry.LAVA, fluidWither, charredBone, ITEM_CONSUMPTION_BASE - 1, DEFAULT_TIME - 3);
		registry.addRecipe(FluidRegistry.LAVA, fluidWither, new SimpleItem(Items.skull, 1), ITEM_CONSUMPTION_BASE - 3, DEFAULT_TIME - 5);
		registry.addRecipe(fluidWither, fluidWitherII, Items.glowstone_dust);
		registry.addRecipe(fluidWither, fluidWitherLong, Items.redstone);
		registry.addRecipe(fluidHealing, fluidAntidote, Items.redstone, ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 2);
		registry.addRecipe(fluidAntidote, fluidAntidoteII, Items.glowstone_dust, ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 2);
		registry.addRecipe(fluidWither, fluidBoom, Items.gunpowder);
		registry.addRecipe(fluidBoom, fluidBoomII, Items.glowstone_dust);
		registry.addRecipe(fluidBoom, fluidBoomLong, Items.redstone);
		registry.addRecipe(fluidSlowness, fluidFreezing, Items.snowball);
		registry.addRecipe(fluidFreezing, fluidFreezingLong, Items.redstone);
		registry.addRecipe(fluidFireResist, fluidFireResistII, Items.glowstone_dust);
		registry.addRecipe(fluidFireResistII, fluidFireResistIII, obsidianTear);
		registry.addRecipe(fluidHolyWaterII, fluidHolyWaterIII, obsidianTear);
		registry.addRecipe(fluidWitherII, fluidWitherIII, obsidianTear);
		registry.addRecipe(fluidAntidoteII, fluidAntidoteIII, obsidianTear);
		registry.addRecipe(fluidBoomII, fluidBoomIII, obsidianTear);
		registry.addRecipe(fluidRegenII, fluidRegenIII, obsidianTear);
		registry.addRecipe(fluidFastII, fluidFastIII, obsidianTear);
		registry.addRecipe(fluidStrengthII, fluidStrengthIII, obsidianTear);
		registry.addRecipe(fluidPoisonII, fluidPoisonIII, obsidianTear);
		registry.addRecipe(fluidHarmII, fluidHarmIII, obsidianTear);
		registry.addRecipe(fluidHealingII, fluidHealingIII, obsidianTear);
		
		/**
		* registry.addRecipe(fluidFireResistIII, fluidFireResistIIII, ?);
		* Creative only?
		*/

		if (getBoolean("Recipes", "Vanilla Potions are Brewable", "Toggle Vanilla Potion Brewing Recipes", true)) {
			registry.addRecipe(FluidRegistry.WATER, fluidAwkward, Items.nether_wart, ITEM_CONSUMPTION_BASE, 4);
			registry.addRecipe(fluidAwkward, fluidVision, Items.golden_carrot);
			registry.addRecipe(fluidVision, fluidVisionLong, Items.redstone);
			registry.addRecipe(fluidVision, fluidInvisible, Items.fermented_spider_eye);
			registry.addRecipe(fluidInvisible, fluidInvisibleLong, Items.redstone);
			registry.addRecipe(fluidAwkward, fluidRegen, Items.ghast_tear);
			registry.addRecipe(fluidRegen, fluidRegenLong, Items.glowstone_dust);
			registry.addRecipe(fluidAwkward, fluidFast, Items.sugar);
			registry.addRecipe(fluidFast, fluidFastLong, Items.redstone);
			registry.addRecipe(fluidFast, fluidFastII, Items.glowstone_dust);
			registry.addRecipe(fluidAwkward, fluidWeakness, Items.fermented_spider_eye);
			registry.addRecipe(fluidStrength, fluidWeakness, Items.fermented_spider_eye);
			registry.addRecipe(fluidAwkward, fluidStrength, Items.blaze_powder);
			registry.addRecipe(fluidStrength, fluidStrengthLong, Items.redstone);
			registry.addRecipe(fluidStrength, fluidStrengthII, Items.glowstone_dust);
			registry.addRecipe(fluidAwkward, fluidFireResist, Items.magma_cream);
			registry.addRecipe(fluidFireResist, fluidFireResistLong, Items.redstone);
			registry.addRecipe(fluidFireResist, fluidSlowness, Items.fermented_spider_eye);
			registry.addRecipe(fluidSlowness, fluidSlownessLong, Items.redstone);
			registry.addRecipe(fluidAwkward, fluidPoison, Items.spider_eye);
			registry.addRecipe(fluidPoison, fluidPoisonLong, Items.redstone);
			registry.addRecipe(fluidPoison, fluidHarm, Items.fermented_spider_eye);
			registry.addRecipe(fluidHarm, fluidHarmII, Items.glowstone_dust);
			registry.addRecipe(fluidAwkward, fluidHealing, Items.speckled_melon);
			registry.addRecipe(fluidHealing, fluidHealingII, Items.glowstone_dust);
			registry.addRecipe(fluidPoison, fluidPoisonII, Items.glowstone_dust);
		}

		if (getBoolean("Recipes", "Golden Feather Recipe", "Toggle Golden Feather Recipe", true))
			GameRegistry.addShapedRecipe(
					goldenFeather.getStack(),
					new Object[] {"!!!", "!@!", "!!!", Character.valueOf('!'), Items.gold_nugget,
							Character.valueOf('@'), Items.feather });
		
		if (getBoolean("Recipes", "Plagued Tear Recipe", "Toggle Plagued Tear Recipe", false))
			GameRegistry.addShapedRecipe(
					obsidianTear.getStack(),
					new Object[] {"!!!", "!@!", "!!!", Character.valueOf('!'), Blocks.obsidian,
							Character.valueOf('@'), Items.ghast_tear });

		if (getBoolean("Recipes", "Splash Bottle Recipe", "Toggle Splash Bottle Recipe", true))
			GameRegistry.addShapedRecipe(splashBottle.getStack(3),
					new Object[] {" @!", "@ @", " @ ", Character.valueOf('!'), Items.gunpowder, Character.valueOf('@'),
							Blocks.glass });

		boolean ironOverride = false;

		if (getBoolean("Recipes", "Lead Brewery", "Toggle crafting the Brewery with Lead if available"))
			ironOverride = breweryRecipe("ingotLead");

		if (getBoolean("Recipes", "Brass Brewery", "Toggle crafting the Brewery with Brass if available"))
			ironOverride = ironOverride || breweryRecipe("ingotBrass");

		if (!(ironOverride && !getBoolean("Recipes", "Iron Brewery",
				"Toggle crafting the Brewery with Iron. (Can only be disabled if Lead or Brass is available)")))
			breweryRecipe("ingotIron"); //Dat boolean expression!

		if (getBoolean("Recipes", "Charred Bone Recipe", "Toggle Charred Bone Smelting Recipe", true))
			GameRegistry.addSmelting(Items.bone, Brewcraft.charredBone.getStack(), 0.1F);

	}

	private boolean breweryRecipe(String ingot) {
		ItemStack metal = ItemStackUtil.getOreWithName(ingot);

		if (metal != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(brewery.getStack(), new Object[] {"! !", "!@!", "#!#",
					Character.valueOf('!'), ingot, Character.valueOf('@'), Items.brewing_stand, Character.valueOf('#'),
					Items.cauldron }));
			return true;
		} else
			return false;
	}

	/**
	 * Helper method for creating potions
	 * 
	 * @param name The in-code name for the potion that will have 'bottle',
	 * 'splash' and 'potion' added to it.
	 * @param iconName
	 * @param effect
	 */
	private Fluid createPotion(String name, String iconName, Potion effect, int duration, int strength) {
		SimpleItem bottle = potions.addMetaItem(new SubItemPotion("bottle" + name, false, effect, duration, strength));
		SimpleItem splash = potions.addMetaItem(new SubItemPotion("splash" + name, true, effect, duration, strength));
		Fluid potion = FluidUtil.createFluid("potion" + name, iconName);

		FluidContainerRegistry.registerFluidContainer(potion, bottle.getStack(), emptyBottle.getStack());
		FluidContainerRegistry.registerFluidContainer(potion, splash.getStack(), splashBottle.getStack());

		return potion;
	}

	private void createSpecialPotion(String name, Potion effect, int duration, int strength) {
		potions.addMetaItem(new SubItemPotion("bottle" + name, false, effect, duration, strength));
		potions.addMetaItem(new SubItemPotion("splash" + name, true, effect, duration, strength));
	}

	/**
	 * Helper method for vanilla potions.
	 * 
	 * @param name - The suffix for potion names.
	 * @param iconName
	 * @param metaBottle - The meta of the corresponding vanilla potion.
	 * @param metaSplash - The meta of the corresponding vanilla splash potion.
	 */
	private Fluid createVanillaPotion(String name, String iconName, int metaBottle, int metaSplash) {
		Fluid potion = FluidUtil.createFluid("potion" + name, iconName);
		FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Items.potionitem, 1, metaBottle),
				emptyBottle.getStack());
		if (!(metaSplash == 0))
			FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Items.potionitem, 1, metaSplash),
					splashBottle.getStack());

		return potion;
	}

	private void expandPotionList() {
		int targetSize = getInt("Potion List Expansion", "Potion List Extension Size",
				"Will only do something if expanding the potion list is set to true.", 64);

		if (Potion.potionTypes.length != targetSize)
			for (Field f : Potion.class.getDeclaredFields()) {
				f.setAccessible(true);
				try {
					if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
						Field modfield = Field.class.getDeclaredField("modifiers");
						modfield.setAccessible(true);
						modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

						final Potion[] newPotionTypes = new Potion[targetSize];
						for (int i = 0; i < Potion.potionTypes.length; i++)
							newPotionTypes[i] = Potion.potionTypes[i];
						f.set(null, newPotionTypes);
					}
				} catch (Exception e) {
					logDebug(e);
				}
			}
	}

	@Override
	@Mod.EventHandler
	public void PreInitialization(FMLPreInitializationEvent event) {
		super.PreInitialization(event);
	}

	@Override
	@Mod.EventHandler
	public void Initialization(FMLInitializationEvent event) {
		super.Initialization(event);
	}

	@Override
	@Mod.EventHandler
	public void PostInitialization(FMLPostInitializationEvent event) {
		super.PostInitialization(event);
	}
}
