package redgear.brewcraft.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.brewcraft.blocks.RenderItemBrewery;
import redgear.brewcraft.blocks.TileEntityBrewery;
import redgear.brewcraft.blocks.TileRendererBrewery;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.entity.RenderBrewcraftPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.brewcraft.potions.effects.EffectAngel;
import redgear.brewcraft.potions.effects.EffectCreeper;
import redgear.brewcraft.potions.effects.EffectFlight;
import redgear.brewcraft.potions.effects.EffectImmunity;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.core.asm.RedGearCore;
import redgear.core.block.MetaTile;
import redgear.core.block.MetaTileSpecialRenderer;
import redgear.core.block.SubTileMachine;
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
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "RedGear|Brewcraft", name = "Brewcraft", version = "@ModVersion@", dependencies = "required-after:RedGear|Core;")
public class Brewcraft extends ModUtils {

	public Brewcraft() {
		super(3578, 11972);
	}

	@Instance("RedGear|Brewcraft")
	public static ModUtils inst;

	public static RecipeRegistry registry = new RecipeRegistry();

	public static MetaItem ingredients;
	public static SimpleItem holydust;
	public static SimpleItem goldenfeather;
	public static SimpleItem charredbone;
	public static SimpleItem splashBottle;

	public static SimpleItem emptyBottle = new SimpleItem(Item.glassBottle);

	public static MetaItemPotion potions;

	public static MetaTile brewing;
	public static SimpleItem brewery;

	private final String breweryTexture = "brewery";

	public static Fluid fluidHolyWater;
	public static Fluid fluidHolyWaterII;
	public static Fluid fluidHolyWaterLong;
	public static Fluid fluidFlying;
	public static Fluid fluidFlyingLong;
	public static Fluid fluidWither;
	public static Fluid fluidWitherLong;
	public static Fluid fluidWitherII;
	public static Fluid fluidAntidote;
	public static Fluid fluidAntidoteII;
	public static Fluid fluidAntidoteLong;
	public static Fluid fluidBoom;
	public static Fluid fluidBoomLong;
	public static Fluid fluidBoomII;
	public static Fluid fluidAwkward;
	public static Fluid fluidVision;
	public static Fluid fluidVisionLong;
	public static Fluid fluidInvisible;
	public static Fluid fluidInvisibleLong;
	public static Fluid fluidRegen;
	public static Fluid fluidRegenLong;
	public static Fluid fluidFast;
	public static Fluid fluidFastLong;
	public static Fluid fluidFastII;
	public static Fluid fluidWeakness;
	public static Fluid fluidStrength;
	public static Fluid fluidStrengthLong;
	public static Fluid fluidStrengthII;
	public static Fluid fluidFireResist;
	public static Fluid fluidFireResistLong;
	public static Fluid fluidSlowness;
	public static Fluid fluidSlownessLong;
	public static Fluid fluidPoison;
	public static Fluid fluidPoisonII;
	public static Fluid fluidPoisonLong;
	public static Fluid fluidHarm;
	public static Fluid fluidHarmII;
	public static Fluid fluidHealing;
	public static Fluid fluidHealingII;

	public static Potion angel;
	public static Potion flight;
	public static Potion creeper;
	public static Potion immunity;

	public final static int DEFAULT_TIME = 7;
	public final static int FLUID_CONSUMPTION_BASE = 100;
	public final static int ITEM_CONSUMPTION_BASE = 4;

	@Override
	protected void PreInit(FMLPreInitializationEvent event) {

		if (getBoolean("Potion List Expansion", "Toggle Potion List Expansion",
				"Disable it you are running another mod that expands the list.", true))
			expandPotionList();

		EntityRegistry.registerModEntity(EntityBrewcraftPotion.class, "Potion",
				EntityRegistry.findGlobalUniqueEntityId(), RedGearCore.instance, 128, 10, true);
		RenderingRegistry.registerEntityRenderingHandler(EntityBrewcraftPotion.class, new RenderBrewcraftPotion());

		ingredients = new MetaItem(getItemId("ingredients"), "RedGear.Brewcraft.Ingredients");
		holydust = ingredients.addMetaItem(new SubItem("holydust"));
		goldenfeather = ingredients.addMetaItem(new SubItem("goldenfeather"));
		charredbone = ingredients.addMetaItem(new SubItem("charredbone"));
		splashBottle = ingredients.addMetaItem(new SubItem("splashBottle"));

		potions = new MetaItemPotion(getItemId("potions"), "RedGear.Brewcraft.Potions");

		angel = new EffectAngel(getInt("Potion Effect IDs", "'Angelic' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 40));

		flight = new EffectFlight(getInt("Potion Effect IDs", "'Flight' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 41));

		creeper = new EffectCreeper(getInt("Potion Effect IDs", "'Combustion' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 42));

		immunity = new EffectImmunity(inst.getInt("Potion Effect IDs", "'Immunity' Effect ID",
				"Must be over 20. Must also be lowered if you have disabled the potion list expansion.", 43));

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

		fluidFlying = createPotion("Flying", "potionWhite", flight, 300, 0);
		fluidFlyingLong = createPotion("FlyingLong", "potionWhite", flight, 600, 0);
		fluidWither = createPotion("Wither", "potionBlack", Potion.wither, 400, 0);
		fluidWitherII = createPotion("WitherII", "potionBlack", Potion.wither, 200, 1);
		fluidWitherLong = createPotion("WitherLong", "potionBlack", Potion.wither, 800, 0);

		createSpecialPotion("Ghast", Potion.confusion, 400, 0);

		fluidAntidote = createPotion("Antidote", "potionDarkPurple", immunity, 600, 0);
		fluidAntidoteII = createPotion("AntidoteII", "potionDarkPurple", immunity, 300, 1);
		fluidAntidoteLong = createPotion("AntidoteLong", "potionDarkPurple", immunity, 1200, 0);
		fluidBoom = createPotion("Boom", "potionDarkGreen", creeper, 160, 0);
		fluidBoomII = createPotion("BoomII", "potionDarkGreen", creeper, 80, 1);
		fluidBoomLong = createPotion("BoomLong", "potionDarkGreen", creeper, 320, 0);

		brewing = new MetaTileSpecialRenderer(getBlockId("brewery"), Material.iron, "RedGear.Brewcraft.Brewery",
				new RenderItemBrewery(), new TileRendererBrewery());

		brewing.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep);

		brewery = brewing.addMetaBlock(new SubTileMachine("Brewery", breweryTexture, TileEntityBrewery.class,
				CoreGuiHandler.addGuiMap("brewery", "Brewery")));

		fluidAwkward = createVanillaPotion("Awkward", "potionBlue", 16, 0);
		fluidVision = createVanillaPotion("Vision", "potionDarkBlue", 8198, 16390);
		fluidVisionLong = createVanillaPotion("VisionLong", "potionDarkBlue", 8262, 16454);
		fluidInvisible = createVanillaPotion("Invisible", "potionGrey", 8206, 16398);
		fluidInvisibleLong = createVanillaPotion("InvisibleLong", "potionGrey", 8270, 16462);
		fluidRegen = createVanillaPotion("Regen", "potionPink", 8193, 16385);
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

		brewery.getBlock().setCreativeTab(CreativeTabs.tabBrewing);
		ingredients.setCreativeTab(CreativeTabs.tabBrewing);

	}

	@Override
	protected void Init(FMLInitializationEvent event) {
		if (getBoolean("Global", "Mod Compatibility", "Toggle Mod Compatibility", true))
			BrewcraftCompatibility.run();
		recipes();

		if (getBoolean("Dungeon Loot", "Golden Feather Dungeon Loot", "Toggle Golden Feather as Dungeon Loot", true))
			CoreDungeonLoot.addLootToDungeons(goldenfeather.getStack(), LootRarity.RARE);
		if (getBoolean("Global", "Golden Feather Villager Trades", "Toggle Golden Feather Villager Trades", true))
			CoreTradeHandler.addTradeRecipe(2, new ItemStack(Item.emerald, 7, 0), goldenfeather.getStack());
		if (getBoolean("Global", "Blessed Powder Villager Trades", "Toggle Blessed Powder Villager Trades", true))
			CoreTradeHandler.addTradeRecipe(2, new ItemStack(Item.emerald, 11, 0), holydust.getStack());

	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {

	}

	private void recipes() {

		registry.addRecipe(fluidRegen, fluidHolyWater, holydust, ITEM_CONSUMPTION_BASE + 1);
		registry.addRecipe(fluidHolyWater, fluidHolyWaterII, Item.glowstone);
		registry.addRecipe(fluidHolyWater, fluidHolyWaterLong, Item.redstone);
		registry.addRecipe(FluidRegistry.WATER, fluidFlying, goldenfeather, ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 1);
		registry.addRecipe(fluidFlying, fluidFlyingLong, Item.redstone);
		registry.addRecipe(FluidRegistry.LAVA, fluidWither, charredbone, ITEM_CONSUMPTION_BASE - 1, DEFAULT_TIME - 3);
		registry.addRecipe(fluidWither, fluidWitherII, Item.glowstone);
		registry.addRecipe(fluidWither, fluidWitherLong, Item.redstone);
		registry.addRecipe(fluidHealing, fluidAntidote, Item.redstone, ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 2);
		registry.addRecipe(fluidAntidote, fluidAntidoteII, Item.glowstone, ITEM_CONSUMPTION_BASE, DEFAULT_TIME + 2);
		registry.addRecipe(fluidWither, fluidBoom, Item.gunpowder);
		registry.addRecipe(fluidBoom, fluidBoomII, Item.glowstone);
		registry.addRecipe(fluidBoom, fluidBoomLong, Item.redstone);

		if (getBoolean("Recipes", "Vanilla Potions are Brewable", "Toggle Vanilla Potion Brewing Recipes", true)) {
			registry.addRecipe(FluidRegistry.WATER, fluidAwkward, Item.netherStalkSeeds, ITEM_CONSUMPTION_BASE, 4);
			registry.addRecipe(fluidAwkward, fluidVision, Item.goldenCarrot);
			registry.addRecipe(fluidVision, fluidVisionLong, Item.redstone);
			registry.addRecipe(fluidVision, fluidInvisible, Item.fermentedSpiderEye);
			registry.addRecipe(fluidInvisible, fluidInvisibleLong, Item.redstone);
			registry.addRecipe(fluidAwkward, fluidRegen, Item.ghastTear);
			registry.addRecipe(fluidRegen, fluidRegenLong, Item.glowstone);
			registry.addRecipe(fluidAwkward, fluidFast, Item.sugar);
			registry.addRecipe(fluidFast, fluidFastLong, Item.redstone);
			registry.addRecipe(fluidFast, fluidFastII, Item.glowstone);
			registry.addRecipe(fluidAwkward, fluidWeakness, Item.fermentedSpiderEye);
			registry.addRecipe(fluidStrength, fluidWeakness, Item.fermentedSpiderEye);
			registry.addRecipe(fluidAwkward, fluidStrength, Item.blazePowder);
			registry.addRecipe(fluidStrength, fluidStrengthLong, Item.redstone);
			registry.addRecipe(fluidStrength, fluidStrengthII, Item.glowstone);
			registry.addRecipe(fluidAwkward, fluidFireResist, Item.magmaCream);
			registry.addRecipe(fluidFireResist, fluidFireResistLong, Item.redstone);
			registry.addRecipe(fluidFireResist, fluidSlowness, Item.fermentedSpiderEye);
			registry.addRecipe(fluidSlowness, fluidSlownessLong, Item.redstone);
			registry.addRecipe(fluidAwkward, fluidPoison, Item.spiderEye);
			registry.addRecipe(fluidPoison, fluidPoisonLong, Item.redstone);
			registry.addRecipe(fluidPoison, fluidHarm, Item.fermentedSpiderEye);
			registry.addRecipe(fluidHarm, fluidHarmII, Item.glowstone);
			registry.addRecipe(fluidAwkward, fluidHealing, Item.speckledMelon);
			registry.addRecipe(fluidHealing, fluidHealingII, Item.glowstone);
			registry.addRecipe(fluidPoison, fluidPoisonII, Item.glowstone);
		}

		if (getBoolean("Recipes", "Golden Feather Recipe", "Toggle Golden Feather Recipe", true))
			GameRegistry.addShapedRecipe(goldenfeather.getStack(),
					new Object[] {"!!!", "!@!", "!!!", Character.valueOf('!'), Item.goldNugget, Character.valueOf('@'),
							Item.feather });

		if (getBoolean("Recipes", "Splash Bottle Recipe", "Toggle Splash Bottle Recipe", true))
			GameRegistry.addShapedRecipe(splashBottle.getStack(3),
					new Object[] {" @!", "@ @", " @ ", Character.valueOf('!'), Item.gunpowder, Character.valueOf('@'),
							Block.glass });

		boolean ironOverride = false;

		if (getBoolean("Recipes", "Lead Brewery", "Toggle crafting the Brewery with Lead if available"))
			ironOverride = breweryRecipe("ingotLead");

		if (getBoolean("Recipes", "Brass Brewery", "Toggle crafting the Brewery with Brass if available"))
			ironOverride = ironOverride || breweryRecipe("ingotBrass");

		if (!(ironOverride && !getBoolean("Recipes", "Iron Brewery",
				"Toggle crafting the Brewery with Iron. (Can only be disabled if Lead or Brass is available)")))
			breweryRecipe("ingotIron"); //Dat boolean expression!

		if (getBoolean("Recipes", "Charred Bone Recipe", "Toggle Charred Bone Smelting Recipe", true))
			GameRegistry.addSmelting(Item.bone.itemID, Brewcraft.charredbone.getStack(), 0.1F);

	}

	private boolean breweryRecipe(String ingot) {
		ItemStack metal = ItemStackUtil.getOreWithName(ingot);

		if (metal != null) {
			GameRegistry.addRecipe(new ShapedOreRecipe(brewery.getStack(), new Object[] {"! !", "!@!", "#!#",
					Character.valueOf('!'), ingot, Character.valueOf('@'), Item.brewingStand, Character.valueOf('#'),
					Item.cauldron }));
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
		FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Item.potion, 1, metaBottle),
				emptyBottle.getStack());
		if (!(metaSplash == 0))
			FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Item.potion, 1, metaSplash),
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
