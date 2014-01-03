package redgear.brewcraft.common;

import java.util.logging.Level;

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

	public static MetaItemPotion potions;
	public static SimpleItem bottleFire;
	public static SimpleItem bottleHolyWater;
	public static SimpleItem bottleHolyWaterII;
	public static SimpleItem bottleHolyWaterLong;
	public static SimpleItem bottleFlying;
	public static SimpleItem bottleFlyingLong;
	public static SimpleItem bottleWither;
	public static SimpleItem bottleWitherII;
	public static SimpleItem bottleWitherLong;
	public static SimpleItem bottleGhast;
	public static SimpleItem bottleAntidote;
	public static SimpleItem bottleAntidoteII;
	public static SimpleItem bottleAntidoteLong;
	public static SimpleItem bottleBoom;
	public static SimpleItem bottleBoomII;
	public static SimpleItem bottleBoomLong;

	public static MetaItemPotion splashes;
	public static SimpleItem splashEmpty;
	public static SimpleItem splashFire;
	public static SimpleItem splashHolyWater;
	public static SimpleItem splashHolyWaterII;
	public static SimpleItem splashHolyWaterLong;
	public static SimpleItem splashFlying;
	public static SimpleItem splashFlyingLong;
	public static SimpleItem splashWither;
	public static SimpleItem splashWitherII;
	public static SimpleItem splashWitherLong;
	public static SimpleItem splashAntidote;
	public static SimpleItem splashAntidoteII;
	public static SimpleItem splashAntidoteLong;
	public static SimpleItem splashBoom;
	public static SimpleItem splashBoomII;
	public static SimpleItem splashBoomLong;

	public static MetaTile brewing;
	public static SimpleItem brewery;

	public static Fluid fluidHolyWater;
	public static Fluid fluidHolyWaterII;
	public static Fluid fluidHolyWaterLong;
	public static Fluid fluidFlying;
	public static Fluid fluidFlyingLong;
	public static Fluid fluidWither;
	public static Fluid fluidWitherII;
	public static Fluid fluidWitherLong;
	public static Fluid fluidAntidote;
	public static Fluid fluidAntidoteII;
	public static Fluid fluidAntidoteLong;
	public static Fluid fluidBoom;
	public static Fluid fluidBoomII;
	public static Fluid fluidBoomLong;
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
		holydust = ingredients.addMetaItem(new SubItem("holydust", "Blessed Powder"));
		goldenfeather = ingredients.addMetaItem(new SubItem("goldenfeather", "Golden Feather"));
		charredbone = ingredients.addMetaItem(new SubItem("charredbone", "Charred Bone"));

		potions = new MetaItemPotion(getItemId("potions"), "RedGear.Brewcraft.Potions");
		bottleFire = potions.addMetaItem(new SubItemPotion("bottleFire", "Bottled Fire", false, new SubPotionEffect() {
			@Override
			public void effect(World world, EntityLivingBase player) {
				player.setFire(10);
			}
		}));
		bottleHolyWater = potions.addMetaItem(new SubItemPotion("bottleHolyWater", "Potion of Holy Water", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(angel.id, 100, 0));
					}
				}));
		bottleHolyWaterII = potions.addMetaItem(new SubItemPotion("bottleHolyWaterII", "Potion of Holy Water II",
				false, new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(angel.id, 100, 1));
					}
				}));
		bottleHolyWaterLong = potions.addMetaItem(new SubItemPotion("bottleHolyWaterLong", "Long Potion of Holy Water",
				false, new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(angel.id, 100, 1));
					}
				}));
		bottleFlying = potions.addMetaItem(new SubItemPotion("bottleFlying", "Potion of Ascention", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(flight.id, 300, 0));
					}
				}));
		bottleFlyingLong = potions.addMetaItem(new SubItemPotion("bottleFlyingLong", "Long Potion of Ascention", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(flight.id, 600, 0));
					}
				}));
		bottleWither = potions.addMetaItem(new SubItemPotion("bottleWither", "Potion of Wither", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(Potion.wither.id, 400, 0));
					}
				}));
		bottleWitherII = potions.addMetaItem(new SubItemPotion("bottleWitherII", "Potion of Wither II", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(Potion.wither.id, 200, 1));
					}
				}));
		bottleWitherLong = potions.addMetaItem(new SubItemPotion("bottleWitherLong", "Long Potion of Wither", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(Potion.wither.id, 800, 0));
					}
				}));
		bottleGhast = potions.addMetaItem(new SubItemPotion("bottleGhast", "Ghast in a Bottle", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 1));
						//TODO: Check to see if this is a splash potion. If so, drop on ground, otherwise go into player inventory.
						//player.inventory.addItemStackToInventory(new ItemStack(Item.ghastTear, 1, 0));
					}
				}));
		bottleAntidote = potions.addMetaItem(new SubItemPotion("bottleAntidote", "Potion of Antidote", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(immunity.id, 1200, 0));
					}
				}));
		bottleAntidoteII = potions.addMetaItem(new SubItemPotion("bottleAntidoteII", "Potion of Antidote II", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(immunity.id, 600, 1));
					}
				}));
		bottleAntidoteLong = potions.addMetaItem(new SubItemPotion("bottleAntidoteLong", "Long Potion of Antidote",
				false, new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(immunity.id, 1200, 1));
					}
				}));
		bottleBoom = potions.addMetaItem(new SubItemPotion("bottleBoom", "Potion of Combustion", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(creeper.id, 160, 0));
					}
				}));
		bottleBoomII = potions.addMetaItem(new SubItemPotion("bottleBoomII", "Potion of Combustion II", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(creeper.id, 160, 0));
					}
				}));
		bottleBoomLong = potions.addMetaItem(new SubItemPotion("bottleBoomLong", "Long Potion of Combustion", false,
				new SubPotionEffect() {
					@Override
					public void effect(World world, EntityLivingBase player) {
						player.addPotionEffect(new PotionEffect(creeper.id, 320, 0));
					}
				}));

		brewing = new MetaTileSpecialRenderer(getBlockId("brewery"), Material.iron, "RedGear.Brewcraft.Brewery",
				new RenderItemBrewery(), new TileRendererBrewery());
		brewery = brewing.addMetaBlock(new SubTileMachine("Brewery", breweryTexture, TileEntityBrewery.class,
				CoreGuiHandler.addGuiMap("brewery", "Brewery")));

		fluidHolyWater = FluidUtil.createFluid("potionHoly", "redgear_brewcraft:potionHoly");
		fluidHolyWaterII = FluidUtil.createFluid("potionHolyII", "redgear_brewcraft:potionHoly");
		fluidHolyWaterLong = FluidUtil.createFluid("potionHolyLong", "redgear_brewcraft:potionHoly");
		fluidFlying = FluidUtil.createFluid("potionFlying", "redgear_brewcraft:potionWhite");
		fluidFlyingLong = FluidUtil.createFluid("potionFlyingLong", "redgear_brewcraft:potionWhite");
		fluidWither = FluidUtil.createFluid("potionWither", "redgear_brewcraft:potionBlack");
		fluidWitherII = FluidUtil.createFluid("potionWitherII", "redgear_brewcraft:potionBlack");
		fluidWitherLong = FluidUtil.createFluid("potionWitherLong", "redgear_brewcraft:potionBlack");
		fluidAntidote = FluidUtil.createFluid("potionAntidote", "redgear_brewcraft:potionPurple");
		fluidAntidoteII = FluidUtil.createFluid("potionAntidoteII", "redgear_brewcraft:potionPurple");
		fluidBoom = FluidUtil.createFluid("potionBoom", "redgear_brewcraft:potionDarkGreen");
		fluidBoomII = FluidUtil.createFluid("potionBoomII", "redgear_brewcraft:potionDarkGreen");
		fluidBoomLong = FluidUtil.createFluid("potionBoomLong", "redgear_brewcraft:potionDarkGreen");
		fluidAwkward = FluidUtil.createFluid("potionAwkward", "redgear_brewcraft:potionBlue");
		fluidVision = FluidUtil.createFluid("potionVision", "redgear_brewcraft:potionDarkBlue");
		fluidVisionLong = FluidUtil.createFluid("potionVisionLong", "redgear_brewcraft:potionDarkBlue");
		fluidInvisible = FluidUtil.createFluid("potionInvisible", "redgear_brewcraft:potionGrey");
		fluidInvisibleLong = FluidUtil.createFluid("potionInvisibleLong", "redgear_brewcraft:potionGrey");
		fluidRegen = FluidUtil.createFluid("potionRegen", "redgear_brewcraft:potionPink");
		fluidRegenLong = FluidUtil.createFluid("potionRegenLong", "redgear_brewcraft:potionPink");
		fluidFast = FluidUtil.createFluid("potionFast", "redgear_brewcraft:potionLightBlue");
		fluidFastLong = FluidUtil.createFluid("potionFastLong", "redgear_brewcraft:potionLightBlue");
		fluidFastII = FluidUtil.createFluid("potionFastII", "redgear_brewcraft:potionLightBlue");
		fluidWeakness = FluidUtil.createFluid("potionWeakness", "redgear_brewcraft:potionPurple");
		fluidStrength = FluidUtil.createFluid("potionStrong", "redgear_brewcraft:potionMagenta");
		fluidStrengthLong = FluidUtil.createFluid("potionStrongLong", "redgear_brewcraft:potionMagenta");
		fluidStrengthII = FluidUtil.createFluid("potionStrongII", "redgear_brewcraft:potionMagenta");
		fluidFireResist = FluidUtil.createFluid("potionFireResist", "redgear_brewcraft:potionLightPink");
		fluidFireResistLong = FluidUtil.createFluid("potionFireResistLong", "redgear_brewcraft:potionLightPink");
		fluidSlowness = FluidUtil.createFluid("potionSlowness", "redgear_brewcraft:potionPurple");
		fluidSlownessLong = FluidUtil.createFluid("potionSlownessLong", "redgear_brewcraft:potionPurple");
		fluidPoison = FluidUtil.createFluid("potionPoison", "redgear_brewcraft:potionGreen");
		fluidPoisonII = FluidUtil.createFluid("potionPoisonII", "redgear_brewcraft:potionGreen");
		fluidPoisonLong = FluidUtil.createFluid("potionPoisonLong", "redgear_brewcraft:potionGreen");
		fluidHarm = FluidUtil.createFluid("potionHarm", "redgear_brewcraft:potionDarkPurple");
		fluidHarmII = FluidUtil.createFluid("potionHarmII", "redgear_brewcraft:potionDarkPurple");
		fluidHealing = FluidUtil.createFluid("potionHeal", "redgear_brewcraft:potionRed");
		fluidHealingII = FluidUtil.createFluid("potionHealII", "redgear_brewcraft:potionRed");

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

		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidHolyWater, 1000), bottleHolyWater.getStack(),
				new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidHolyWaterII, 1000),
				bottleHolyWaterII.getStack(), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidHolyWaterLong, 1000),
				bottleHolyWaterLong.getStack(), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidFlying, 1000), bottleFlying.getStack(),
				new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidFlyingLong, 1000),
				bottleFlyingLong.getStack(), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidWither, 1000), bottleWither.getStack(),
				new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidWitherLong, 1000),
				bottleWitherLong.getStack(), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidWitherII, 1000), bottleWitherII.getStack(),
				new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidAntidote, 1000), bottleAntidote.getStack(),
				new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidAntidoteII, 1000),
				bottleAntidoteII.getStack(), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidAntidoteLong, 1000),
				bottleAntidoteLong.getStack(), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidBoom, 1000), bottleBoom.getStack(),
				new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidBoomII, 1000), bottleBoomII.getStack(),
				new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidBoomLong, 1000), bottleBoomLong.getStack(),
				new ItemStack(Item.glassBottle));

		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidAwkward, 1000), new ItemStack(
				Item.glassBottle, 1, 16), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidVision, 1000), new ItemStack(
				Item.glassBottle, 1, 8198), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidVisionLong, 1000), new ItemStack(
				Item.glassBottle, 1, 8262), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidInvisible, 1000), new ItemStack(
				Item.glassBottle, 1, 8206), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidInvisibleLong, 1000), new ItemStack(
				Item.glassBottle, 1, 8270), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidRegen, 1000), new ItemStack(Item.glassBottle,
				1, 8193), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidRegenLong, 1000), new ItemStack(
				Item.glassBottle, 1, 8257), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidFast, 1000), new ItemStack(Item.glassBottle,
				1, 8194), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidFastII, 1000), new ItemStack(
				Item.glassBottle, 1, 8290), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidFastLong, 1000), new ItemStack(
				Item.glassBottle, 1, 8258), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidWeakness, 1000), new ItemStack(
				Item.glassBottle, 1, 8200), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidStrength, 1000), new ItemStack(
				Item.glassBottle, 1, 8201), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidStrengthLong, 1000), new ItemStack(
				Item.glassBottle, 1, 8265), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidStrengthII, 1000), new ItemStack(
				Item.glassBottle, 1, 8297), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidFireResist, 1000), new ItemStack(
				Item.glassBottle, 1, 8195), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidFireResistLong, 1000), new ItemStack(
				Item.glassBottle, 1, 8259), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidSlowness, 1000), new ItemStack(
				Item.glassBottle, 1, 8202), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidSlownessLong, 1000), new ItemStack(
				Item.glassBottle, 1, 8266), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidPoison, 1000), new ItemStack(
				Item.glassBottle, 1, 8196), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidPoisonLong, 1000), new ItemStack(
				Item.glassBottle, 1, 8260), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidPoisonII, 1000), new ItemStack(
				Item.glassBottle, 1, 8228), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidHarm, 1000), new ItemStack(Item.glassBottle,
				1, 8204), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidHarmII, 1000), new ItemStack(
				Item.glassBottle, 1, 8236), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidHealing, 1000), new ItemStack(
				Item.glassBottle, 1, 8196), new ItemStack(Item.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidHealingII, 1000), new ItemStack(
				Item.glassBottle, 1, 8229), new ItemStack(Item.glassBottle));

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
				registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), new FluidStack(fluidWither, 100), soul, 1,
						4);
			if (!(dust == null))
				registry.addRecipe(new FluidStack(fluidRegen, 100), new FluidStack(fluidHolyWater, 100), dust, 1, 4);
		}

		if (Mods.Thaum.isIn()
				&& getBoolean("Mod Compatibility", "Thaumcraft 4 Compatibility", "Toggle Thaumcraft 4 Compatibility",
						true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Thaumcraft 4 loaded, now running compatibility.");

			if (!(brain == null)) {
				registry.addRecipe(new FluidStack(fluidVision, 100), new FluidStack(fluidInvisible, 100),
						ItemApi.getItem("ItemResource", 5), 1, 4);
				registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidWeakness, 100),
						ItemApi.getItem("ItemResource", 5), 1, 4);
				registry.addRecipe(new FluidStack(fluidStrength, 100), new FluidStack(fluidWeakness, 100),
						ItemApi.getItem("ItemResource", 5), 1, 4);
				registry.addRecipe(new FluidStack(fluidFireResist, 100), new FluidStack(fluidSlowness, 100),
						ItemApi.getItem("ItemResource", 5), 1, 4);
			}

			if (!(goo == null))
				registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidPoison, 100), goo, 1, 4);
			if (!(tendril == null))
				registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidPoison, 100), tendril, 1, 4);

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
				registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), new FluidStack(fluidWither, 100), bone, 1,
						4);
		}

		if (Mods.Natura.isIn()
				&& getBoolean("Mod Compatibility", "Natura Compatibility", "Toggle Natura Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + RedGear.BrewcraftName
					+ "] has found Natura loaded, now running compatibility.");

			if (!(sulfur == null))
				registry.addRecipe(new FluidStack(fluidWither, 100), new FluidStack(fluidBoom, 100), sulfur, 1, 7);
		}

	}

	private void recipes() {

		registry.addRecipe(new FluidStack(fluidRegen, 100), new FluidStack(fluidHolyWater, 100), holydust, 1, 5);
		registry.addRecipe(new FluidStack(fluidHolyWater, 100), new FluidStack(fluidHolyWaterII, 100), new ItemStack(
				Item.glowstone), 1, 5);
		registry.addRecipe(new FluidStack(fluidHolyWater, 100), new FluidStack(fluidHolyWaterLong, 100), new ItemStack(
				Item.redstone), 1, 5);
		registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), new FluidStack(fluidFlying, 100), goldenfeather,
				1, 6);
		registry.addRecipe(new FluidStack(fluidFlying, 100), new FluidStack(fluidFlyingLong, 100), new ItemStack(
				Item.redstone), 1, 6);
		registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), new FluidStack(fluidWither, 100), charredbone, 1, 4);
		registry.addRecipe(new FluidStack(fluidWither, 100), new FluidStack(fluidWitherII, 100), new ItemStack(
				Item.glowstone), 1, 4);
		registry.addRecipe(new FluidStack(fluidWither, 100), new FluidStack(fluidWitherLong, 100), new ItemStack(
				Item.redstone), 1, 4);
		registry.addRecipe(new FluidStack(fluidHealing, 100), new FluidStack(fluidAntidote, 100), new ItemStack(
				Item.redstone), 1, 6);
		registry.addRecipe(new FluidStack(fluidAntidote, 100), new FluidStack(fluidAntidoteII, 100), new ItemStack(
				Item.glowstone), 1, 6);
		registry.addRecipe(new FluidStack(fluidWither, 100), new FluidStack(fluidBoom, 100), new ItemStack(
				Item.gunpowder), 1, 4);
		registry.addRecipe(new FluidStack(fluidBoom, 100), new FluidStack(fluidBoomII, 100), new ItemStack(
				Item.glowstone), 1, 4);
		registry.addRecipe(new FluidStack(fluidBoom, 100), new FluidStack(fluidBoomLong, 100), new ItemStack(
				Item.redstone), 1, 4);

		if (getBoolean("Recipes", "Vanilla Potions are Brewable", "Toggle Vanilla Potion Brewing Recipes", true)) {
			registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), new FluidStack(fluidAwkward, 100),
					new ItemStack(Item.netherStalkSeeds), 1, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidVision, 100), new ItemStack(
					Item.goldenCarrot), 1, 4);
			registry.addRecipe(new FluidStack(fluidVision, 100), new FluidStack(fluidVisionLong, 100), new ItemStack(
					Item.redstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidVision, 100), new FluidStack(fluidInvisible, 100), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(new FluidStack(fluidInvisible, 100), new FluidStack(fluidInvisibleLong, 100),
					new ItemStack(Item.redstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidRegen, 100), new ItemStack(
					Item.ghastTear), 1, 4);
			registry.addRecipe(new FluidStack(fluidRegen, 100), new FluidStack(fluidRegenLong, 100), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidFast, 100), new ItemStack(
					Item.sugar), 1, 4);
			registry.addRecipe(new FluidStack(fluidFast, 100), new FluidStack(fluidFastLong, 100), new ItemStack(
					Item.redstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidFast, 100), new FluidStack(fluidFastII, 100), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidWeakness, 100), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(new FluidStack(fluidStrength, 100), new FluidStack(fluidWeakness, 100), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidStrength, 100), new ItemStack(
					Item.blazePowder), 1, 4);
			registry.addRecipe(new FluidStack(fluidStrength, 100), new FluidStack(fluidStrengthLong, 100),
					new ItemStack(Item.redstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidStrength, 100), new FluidStack(fluidStrengthII, 100), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidFireResist, 100), new ItemStack(
					Item.magmaCream), 1, 4);
			registry.addRecipe(new FluidStack(fluidFireResist, 100), new FluidStack(fluidFireResistLong, 100),
					new ItemStack(Item.redstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidFireResist, 100), new FluidStack(fluidSlowness, 100), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(new FluidStack(fluidSlowness, 100), new FluidStack(fluidSlownessLong, 100),
					new ItemStack(Item.redstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidPoison, 100), new ItemStack(
					Item.spiderEye), 1, 4);
			registry.addRecipe(new FluidStack(fluidPoison, 100), new FluidStack(fluidPoisonLong, 100), new ItemStack(
					Item.redstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidPoison, 100), new FluidStack(fluidHarm, 100), new ItemStack(
					Item.fermentedSpiderEye), 1, 4);
			registry.addRecipe(new FluidStack(fluidHarm, 100), new FluidStack(fluidHarmII, 100), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidAwkward, 100), new FluidStack(fluidHealing, 100), new ItemStack(
					Item.speckledMelon), 1, 4);
			registry.addRecipe(new FluidStack(fluidHealing, 100), new FluidStack(fluidHealingII, 100), new ItemStack(
					Item.glowstone), 1, 4);
			registry.addRecipe(new FluidStack(fluidPoison, 100), new FluidStack(fluidPoisonII, 100), new ItemStack(
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
	 * @param displayName The display name for this potion. Users will see this.
	 * @param iconName
	 * @param effect
	 */
	private void createPotion(String name, String iconName, SubPotionEffect effect) {
		SimpleItem bottle = potions.addMetaItem(new SubItemPotion("bottle" + name, false, effect));
		SimpleItem splash = potions.addMetaItem(new SubItemPotion("splash" + name, true, effect));
		Fluid potion = FluidUtil.createFluid("potion" + name, iconName);

		FluidContainerRegistry.registerFluidContainer(potion, bottle.getStack(), emptyBottle.getStack());
		//TODO: when empty splash bottle is added, just copy the above line and replace bottle with splash and emptyBottle with emptySplashBottle
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
