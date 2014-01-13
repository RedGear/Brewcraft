package redgear.brewcraft.common;

import java.util.Collection;
import java.util.logging.Level;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.brewcraft.blocks.RenderItemBrewery;
import redgear.brewcraft.blocks.TileEntityBrewery;
import redgear.brewcraft.blocks.TileRendererBrewery;
import redgear.brewcraft.potions.EffectExtension;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.brewcraft.potions.SubItemPotion.SubPotionEffect;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.core.block.MetaTile;
import redgear.core.block.MetaTileSpecialRenderer;
import redgear.core.block.SubTileMachine;
import redgear.core.compat.ModConfigHelper;
import redgear.core.compat.Mods;
import redgear.core.fluids.FluidUtil;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.ModUtils;
import redgear.core.mod.RedGear;
import redgear.core.network.CoreGuiHandler;
import redgear.core.util.CoreDungeonLoot;
import redgear.core.util.CoreDungeonLoot.LootRarity;
import redgear.core.util.CoreTradeHandler;
import redgear.core.util.SimpleItem;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

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

	public static MetaItemPotion potions;

	public static MetaTile brewing;
	public static SimpleItem brewery;

	private final String breweryTexture = "brewery";

	public static ItemStack soul;
	public static ItemStack dust;
	public static ItemStack crystal;
	public static ItemStack bone;
	public static ItemStack sulfur;
	public static ItemStack brain;
	public static ItemStack goo;
	public static ItemStack tendril;

	public static Potion angel;
	public static Potion flight;
	public static Potion creeper;
	public static Potion immunity;
	
	public Multimap<ItemStack, Fluid> potionMap = ArrayListMultimap.<ItemStack, Fluid> create();

	@Override
	protected void PreInit(FMLPreInitializationEvent event) {

		angel = new EffectExtension(getInt("Potion Effect IDs", "'Angelic' Effect ID",
				"Must be over 20 to avoid conflict with vanilla.", 25), false, 16114042).setPotionName("potion.angel");
		flight = new EffectExtension(getInt("Potion Effect IDs", "'Flight' Effect ID",
				"Must be over 20 to avoid conflict with vanilla.", 26), false, 16777215).setPotionName("potion.flight");
		creeper = new EffectExtension(getInt("Potion Effect IDs", "'Cumbustion' Effect ID",
				"Must be over 20 to avoid conflict with vanilla.", 27), true, 1987089).setPotionName("potion.creeper");
		immunity = new EffectExtension(getInt("Potion Effect IDs", "'Immunity' Effect ID",
				"Must be over 20 to avoid conflict with vanilla.", 28), false, 8131210)
				.setPotionName("potion.immunity");

		ingredients = new MetaItem(getItemId("ingredients"), "RedGear.Brewcraft.ingredients");
		holydust = ingredients.addMetaItem(new SubItem("holydust"));
		goldenfeather = ingredients.addMetaItem(new SubItem("goldenfeather"));
		charredbone = ingredients.addMetaItem(new SubItem("charredbone"));

		potions = new MetaItemPotion(getItemId("potions"), "RedGear.Brewcraft.Potions");
		createSpecialPotion("Fire", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.setFire(10);
			}
		});
		createPotion("HolyWater", "redgear_brewcraft:potionHoly", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(angel.id, 100, 0));
			}
		});
		createPotion("HolyWaterII", "redgear_brewcraft:potionHoly", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(angel.id, 50, 1));
			}
		});
		createPotion("HolyWaterLong", "redgear_brewcraft:potionHoly", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(angel.id, 200, 0));
			}
		});
		createPotion("Flying", "redgear_brewcraft:potionFlying", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(flight.id, 300, 0));
			}
		});
		createPotion("FlyingLong", "redgear_brewcraft:potionFlying", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(flight.id, 600, 0));
			}
		});
		createPotion("Wither", "redgear_brewcraft:potionWither", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(Potion.wither.id, 400, 0));
			}
		});
		createPotion("WitherII", "redgear_brewcraft:potionWither", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(Potion.wither.id, 200, 1));
			}
		});
		createPotion("WitherLong", "redgear_brewcraft:potionWither", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(Potion.wither.id, 800, 0));
			}
		});
		createSpecialPotion("Ghast", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 0));
			}
		});
		createPotion("Antidote", "redgear_brewcraft:potionAntidote", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(immunity.id, 600, 0));
			}
		});
		createPotion("AntidoteII", "redgear_brewcraft:potionAntidote", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(immunity.id, 300, 1));
			}
		});
		createPotion("AntidoteLong", "redgear_brewcraft:potionAntidote", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(immunity.id, 1200, 0));
			}
		});
		createPotion("Boom", "redgear_brewcraft:potionBoom", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(creeper.id, 160, 0));;
			}
		});
		createPotion("BoomII", "redgear_brewcraft:potionBoom", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(creeper.id, 80, 1));;
			}
		});
		createPotion("BoomLong", "redgear_brewcraft:potionBoom", new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.addPotionEffect(new PotionEffect(creeper.id, 320, 0));;
			}
		});

		brewing = new MetaTileSpecialRenderer(getBlockId("brewery"), Material.iron, "RedGear.Brewcraft.Brewery",
				new RenderItemBrewery(), new TileRendererBrewery());
		brewery = brewing.addMetaBlock(new SubTileMachine("Brewery", breweryTexture, TileEntityBrewery.class,
				CoreGuiHandler.addGuiMap("brewery", "Brewery")));
		
		createVanillaPotion("Awkward", "redgear_brewcraft:potionBlue", 16, 0);
		createVanillaPotion("Vision", "redgear_brewcraft:potionDarkBlue", 8198, 16390);
		createVanillaPotion("VisionLong", "redgear_brewcraft:potionDarkBlue", 8262, 16454);
		createVanillaPotion("Invisible", "redgear_brewcraft:potionGrey", 8206, 16398);
		createVanillaPotion("InvisibleLong", "redgear_brewcraft:potionGrey", 8270, 16462);
		createVanillaPotion("Regen", "redgear_brewcraft:potionPink", 8193, 16385);
		createVanillaPotion("RegenLong", "redgear_brewcraft:potionPink", 8257, 16449);
		createVanillaPotion("Fast", "redgear_brewcraft:potionLightBlue", 8194, 16386);
		createVanillaPotion("FastLong", "redgear_brewcraft:potionLightBlue", 8290, 16450);
		createVanillaPotion("FastII", "redgear_brewcraft:potionLightBlue", 8258, 16418);
		createVanillaPotion("Weakness", "redgear_brewcraft:potionPurple", 8200, 16456);
		createVanillaPotion("Strength", "redgear_brewcraft:potionMagenta", 8201, 16393);
		createVanillaPotion("StrengthLong", "redgear_brewcraft:potionMagenta", 8265, 16457);
		createVanillaPotion("StrengthII", "redgear_brewcraft:potionMagenta", 8297, 16425);
		createVanillaPotion("FireResist", "redgear_brewcraft:potionLightPink", 8195, 16387);
		createVanillaPotion("FireResistLong", "redgear_brewcraft:potionLightPink", 8259, 16451);
		createVanillaPotion("Slowness", "redgear_brewcraft:potionPurple", 8202, 16394);
		createVanillaPotion("SlownessLong", "redgear_brewcraft:potionPurple", 8266, 16458);
		createVanillaPotion("Poison", "redgear_brewcraft:potionGreen", 8196, 16388);
		createVanillaPotion("PoisonII", "redgear_brewcraft:potionGreen", 8260, 16420);
		createVanillaPotion("PoisonLong", "redgear_brewcraft:potionGreen", 8228, 16452);
		createVanillaPotion("Harm", "redgear_brewcraft:potionDarkPurple", 8204, 16396);
		createVanillaPotion("HarmII", "redgear_brewcraft:potionDarkPurple", 8236, 16428);
		createVanillaPotion("Healing", "redgear_brewcraft:potionRed", 8196, 16389);
		createVanillaPotion("HealingII", "redgear_brewcraft:potionRed", 8229, 16421);

		ingredients.setCreativeTab(CreativeTabs.tabBrewing);

		brewery.getBlock().setCreativeTab(CreativeTabs.tabBrewing);

	}

	@Override
	protected void Init(FMLInitializationEvent event) {
		if (getBoolean("Global", "Mod Compatibility", "Toggle Mod Compatibility", true))
			compatibility();
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

		TickRegistry.registerTickHandler(new BrewcraftTickHandler(), Side.CLIENT);

	}

	private void compatibility() {

		soul = ModConfigHelper.get("miscItems", 10);
		dust = ModConfigHelper.get("miscItems", 11);
		crystal = ModConfigHelper.get("materials", 7);
		bone = ModConfigHelper.get("materials", 8);
		sulfur = ModConfigHelper.get("plantItem", 4);
		brain = ModConfigHelper.get("ItemResource", 5);
		goo = ModConfigHelper.get("ItemResource", 11);
		tendril = ModConfigHelper.get("ItemResource", 12);

		if (Mods.BiomesOPlenty.isIn()
				&& getBoolean("Mod Compatibility", "Biomes o' Plenty Compatibility",
						"Toggle Biomes o' Plenty Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Biomes o' Plenty loaded, now running compatibility.");

			if (!(soul == null))
				registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), getFluidFromPotion(new ItemStack(potions, 1, 11)), soul, 1,
						4);
			if (!(dust == null))
				registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8193)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8193)), dust, 1, 4);
		}

		if (Mods.Thaum.isIn()
				&& getBoolean("Mod Compatibility", "Thaumcraft 4 Compatibility", "Toggle Thaumcraft 4 Compatibility",
						true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Thaumcraft 4 loaded, now running compatibility.");

			if (!(brain == null)) {
				registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8198)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8206)),
						ItemApi.getItem("ItemResource", 5), 1, 4);
				registry.addRecipe(new FluidStack(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), 100), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8200)),
						ItemApi.getItem("ItemResource", 5), 1, 4);
				registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8201)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8200)),
						ItemApi.getItem("ItemResource", 5), 1, 4);
				registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8195)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8202)),
						ItemApi.getItem("ItemResource", 5), 1, 4);
			}

			if (!(goo == null))
				registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8196)), goo, 1, 4);
			if (!(tendril == null))
				registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8196)), tendril, 1, 4);

			ThaumcraftApi.registerObjectTag(brewery.id, 0,
					new AspectList().add(Aspect.MECHANISM, 11).add(Aspect.METAL, 7));

			ThaumcraftApi.registerObjectTag(ingredients.itemID, 0,
					new AspectList().add(Aspect.LIFE, 3).add(Aspect.LIGHT, 2).add(Aspect.MAGIC, 2));
			ThaumcraftApi.registerObjectTag(ingredients.itemID, 1,
					new AspectList().add(Aspect.GREED, 3).add(Aspect.FLIGHT, 1));
			ThaumcraftApi.registerObjectTag(ingredients.itemID, 2,
					new AspectList().add(Aspect.DEATH, 2).add(Aspect.BEAST, 1));

			ThaumcraftApi.registerObjectTag(potions.itemID, 0, new AspectList().add(Aspect.MAGIC, 3)
					.add(Aspect.FIRE, 2));
			ThaumcraftApi.registerObjectTag(potions.itemID, 1,
					new AspectList().add(Aspect.LIGHT, 8).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 2,
					new AspectList().add(Aspect.LIGHT, 13).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 3,
					new AspectList().add(Aspect.LIGHT, 8).add(Aspect.MAGIC, 5));
			ThaumcraftApi.registerObjectTag(potions.itemID, 4,
					new AspectList().add(Aspect.FLIGHT, 8).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 5,
					new AspectList().add(Aspect.FLIGHT, 8).add(Aspect.MAGIC, 5));
			ThaumcraftApi.registerObjectTag(potions.itemID, 6,
					new AspectList().add(Aspect.DEATH, 8).add(Aspect.DARKNESS, 8).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 7,
					new AspectList().add(Aspect.DEATH, 13).add(Aspect.DARKNESS, 13).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 8,
					new AspectList().add(Aspect.DEATH, 8).add(Aspect.DARKNESS, 8).add(Aspect.MAGIC, 7));
			ThaumcraftApi.registerObjectTag(potions.itemID, 9,
					new AspectList().add(Aspect.BEAST, 8).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 10, new AspectList().add(Aspect.LIFE, 8)
					.add(Aspect.HEAL, 8).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 11,
					new AspectList().add(Aspect.LIFE, 13).add(Aspect.HEAL, 13).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 12, new AspectList().add(Aspect.LIFE, 8)
					.add(Aspect.HEAL, 8).add(Aspect.MAGIC, 5));
			ThaumcraftApi.registerObjectTag(potions.itemID, 13,
					new AspectList().add(Aspect.WEAPON, 8).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 14,
					new AspectList().add(Aspect.WEAPON, 13).add(Aspect.MAGIC, 3));
			ThaumcraftApi.registerObjectTag(potions.itemID, 15,
					new AspectList().add(Aspect.WEAPON, 8).add(Aspect.MAGIC, 5));
		}

		if (Mods.TConstruct.isIn()
				&& getBoolean("Mod Compatibility", "Tinkers' Construct Compatibility",
						"Toggle Tinkers' Construct Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Tinkers' Construct loaded, now running compatibility.");

			if (!(crystal == null))
				registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), new FluidStack(FluidRegistry.LAVA, 100),
						crystal, 1, 4);

			if (!(bone == null))
				registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), getFluidFromPotion(new ItemStack(potions, 1, 11)), bone, 1,
						4);
		}

		if (Mods.Natura.isIn()
				&& getBoolean("Mod Compatibility", "Natura Compatibility", "Toggle Natura Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Natura loaded, now running compatibility.");

			if (!(sulfur == null))
				registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 11)), getFluidFromPotion(new ItemStack(potions, 1, 24)), sulfur, 1, 7);
		}

	}

	private void recipes() {

		registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8193)), getFluidFromPotion(new ItemStack(potions, 1, 1)), holydust, 1, 5);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 1)), getFluidFromPotion(new ItemStack(potions, 1, 3)), new ItemStack(
				Item.glowstone), 1, 5);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 1)), getFluidFromPotion(new ItemStack(potions, 1, 5)), new ItemStack(
				Item.redstone), 1, 5);
		registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), getFluidFromPotion(new ItemStack(potions, 1, 7)), goldenfeather,
				1, 6);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 7)), getFluidFromPotion(new ItemStack(potions, 1, 9)), new ItemStack(
				Item.redstone), 1, 6);
		registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), getFluidFromPotion(new ItemStack(potions, 1, 11)), charredbone, 1, 4);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 11)), getFluidFromPotion(new ItemStack(potions, 1, 13)), new ItemStack(
				Item.glowstone), 1, 4);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 11)), getFluidFromPotion(new ItemStack(potions, 1, 15)), new ItemStack(
				Item.redstone), 1, 4);
		registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8197)), getFluidFromPotion(new ItemStack(potions, 1, 18)), new ItemStack(
				Item.redstone), 1, 6);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 18)), getFluidFromPotion(new ItemStack(potions, 1, 20)), new ItemStack(
				Item.glowstone), 1, 6);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 11)), getFluidFromPotion(new ItemStack(potions, 1, 24)), new ItemStack(
				Item.gunpowder), 1, 4);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 24)), getFluidFromPotion(new ItemStack(potions, 1, 26)), new ItemStack(
				Item.glowstone), 1, 4);
		registry.addRecipe(getFluidFromPotion(new ItemStack(potions, 1, 24)), getFluidFromPotion(new ItemStack(potions, 1, 28)), new ItemStack(
				Item.redstone), 1, 4);

		if (getBoolean("Recipes", "Vanilla Potions are Brewable", "Toggle Vanilla Potion Brewing Recipes", true)) {
			registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)),
					new ItemStack(Item.netherStalkSeeds), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8198)), new ItemStack(
					Item.goldenCarrot), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8198)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8262)), new ItemStack(
					Item.redstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8198)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8206)), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8206)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8270)),
					new ItemStack(Item.redstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8193)), new ItemStack(
					Item.ghastTear), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8193)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8257)), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8194)), new ItemStack(
					Item.sugar), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8194)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8258)), new ItemStack(
					Item.redstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8194)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8290)), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8200)), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8201)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8200)), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8201)), new ItemStack(
					Item.blazePowder), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8201)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8265)),
					new ItemStack(Item.redstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8201)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8233)), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8195)), new ItemStack(
					Item.magmaCream), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8195)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8259)),
					new ItemStack(Item.redstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8195)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8202)), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8202)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8266)),
					new ItemStack(Item.redstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8196)), new ItemStack(
					Item.spiderEye), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8196)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8260)), new ItemStack(
					Item.redstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8196)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8204)), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8204)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8236)), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 16)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8197)), new ItemStack(
					Item.speckledMelon), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8197)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8229)), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8196)), getFluidFromPotion(new ItemStack(Item.glassBottle, 1, 8228)), new ItemStack(
					Item.glowstone), 1, 4);
		}

		if (getBoolean("Recipes", "Golden Feather Recipe", "Toggle Golden Feather Recipe", true))
			GameRegistry.addShapedRecipe(goldenfeather.getStack(),
					new Object[] {"!!!", "!@!", "!!!", Character.valueOf('!'), Item.goldNugget, Character.valueOf('@'),
							Item.feather });

		for (String ingot : new String[] {"ingotLead", "ingotIron", "ingotBrass" })
			if (getBoolean("Ore Dictionary", "Brewery Recipe", "Toggle Brewery Recipe Ore Dictionary Use", true))
				GameRegistry.addRecipe(new ShapedOreRecipe(brewery.getStack(), new Object[] {"! !", "!@!", "#!#",
						Character.valueOf('!'), ingot, Character.valueOf('@'), Block.brewingStand,
						Character.valueOf('#'), Block.cauldron }));

		if (getBoolean("Recipes", "Brewery Recipe", "Toggle Brewery Recipe", true))
			GameRegistry.addRecipe(new ShapedOreRecipe(brewery.getStack(), new Object[] {"! !", "!@!", "#!#",
					Character.valueOf('!'), Item.ingotIron, Character.valueOf('@'), Block.brewingStand,
					Character.valueOf('#'), Block.cauldron }));

		if (getBoolean("Recipes", "Charred Bone Recipe", "Toggle Charred Bone Smelting Recipe", true))
			GameRegistry.addSmelting(Item.bone.itemID, Brewcraft.charredbone.getStack(), 0.1F);

	}

	private static SimpleItem emptyBottle = new SimpleItem(Item.glassBottle);

	/**
	 * Helper method for creating potions
	 * 
	 * @param name The in-code name for the potion that will have 'bottle',
	 * 'splash' and 'potion' added to it.
	 * @param iconName
	 * @param effect
	 */
	private void createPotion(String name, String iconName, SubPotionEffect effect) {
		SimpleItem bottle = potions.addMetaItem(new SubItemPotion("bottle" + name, false, effect));
		SimpleItem splash = potions.addMetaItem(new SubItemPotion("splash" + name, true, effect));
		Fluid potion = FluidUtil.createFluid("potion" + name, iconName);

		FluidContainerRegistry.registerFluidContainer(potion, bottle.getStack(), emptyBottle.getStack());
		FluidContainerRegistry.registerFluidContainer(potion, splash.getStack(), splashBottle.getStack());
		potionMap.put(bottle.getStack(), potion);
		potionMap.put(bottle.getStack(), potion);
	}
	
	private void createSpecialPotion(String name, SubPotionEffect effect) {
		SimpleItem bottle = potions.addMetaItem(new SubItemPotion("bottle" + name, false, effect));
		SimpleItem splash = potions.addMetaItem(new SubItemPotion("splash" + name, true, effect));
	}
	
	/**
	 * Helper method for vanilla potions.
	 * 
	 * @param name - The suffix for potion names, using 'splash', 'bottle', and 'potion.'
	 * @param iconName
	 * @param metaBottle - The meta of the corresponding vanilla potion.
	 * @param metaSplash - The meta of the corresponding vanilla splash potion.
	 */
	private void createVanillaPotion(String name, String iconName, int metaBottle, int metaSplash) {
		Fluid potion = FluidUtil.createFluid("potion" + name, iconName);
		FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Item.glassBottle, 1, metaBottle), emptyBottle.getStack());
		if(!(metaSplash == 0))
		FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Item.glassBottle, 1, metaSplash), splashBottle.getStack());
		potionMap.put(new ItemStack(Item.glassBottle, 1, metaBottle), potion);
		potionMap.put(new ItemStack(Item.glassBottle, 1, metaSplash), potion);
	}
	
	/**
	 * Method to get a fluid from a given potion.
	 * 
	 * @param potion - The potion that you're getting the fluid for.
	 */
	private FluidStack getFluidFromPotion(ItemStack potion){
		Collection<Fluid> fluidList = potionMap.get(potion);
		Fluid fluid = fluidList.iterator().next();
		
		return new FluidStack(fluid, 100);
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
