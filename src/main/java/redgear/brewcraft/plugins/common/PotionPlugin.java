package redgear.brewcraft.plugins.common;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.api.BrewingAPI;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.utils.PotionRegistry;
import redgear.core.fluids.FluidUtil;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.common.LoaderState.ModState;

public class PotionPlugin implements IPlugin {

	public static MetaItemPotion potions;
	public static SimpleItem emptyBottle = new SimpleItem(Items.glass_bottle, 0);
	public static PotionRegistry registry = new PotionRegistry();
	public static final String potionTexture = "potionWhite";

	public static FluidStack fluidAwkward;
	public static FluidStack fluidVision;
	public static FluidStack fluidVisionLong;
	public static FluidStack fluidVisionVeryLong;
	public static FluidStack fluidInvisible;
	public static FluidStack fluidInvisibleLong;
	public static FluidStack fluidInvisibleVeryLong;
	public static FluidStack fluidWeakness;
	public static FluidStack fluidWeaknessLong;
	public static FluidStack fluidWeaknessVeryLong;
	public static FluidStack fluidFireResist;
	public static FluidStack fluidFireResistLong;
	public static FluidStack fluidFireResistVeryLong;
	public static FluidStack fluidSlowness;
	public static FluidStack fluidSlownessLong;
	public static FluidStack fluidSlownessVeryLong;
	public static FluidStack fluidRegen;
	public static FluidStack fluidRegenII;
	public static FluidStack fluidRegenIII;
	public static FluidStack fluidRegenLong;
	public static FluidStack fluidRegenVeryLong;
	public static FluidStack fluidFast;
	public static FluidStack fluidFastII;
	public static FluidStack fluidFastIII;
	public static FluidStack fluidFastLong;
	public static FluidStack fluidFastVeryLong;
	public static FluidStack fluidStrength;
	public static FluidStack fluidStrengthII;
	public static FluidStack fluidStrengthIII;
	public static FluidStack fluidStrengthLong;
	public static FluidStack fluidStrengthVeryLong;
	public static FluidStack fluidPoison;
	public static FluidStack fluidPoisonII;
	public static FluidStack fluidPoisonIII;
	public static FluidStack fluidPoisonLong;
	public static FluidStack fluidPoisonVeryLong;
	public static FluidStack fluidHaste;
	public static FluidStack fluidHasteII;
	public static FluidStack fluidHasteIII;
	public static FluidStack fluidHasteLong;
	public static FluidStack fluidHasteVeryLong;
	public static FluidStack fluidFatigue;
	public static FluidStack fluidFatigueII;
	public static FluidStack fluidFatigueIII;
	public static FluidStack fluidFatigueLong;
	public static FluidStack fluidFatigueVeryLong;
	public static FluidStack fluidJump;
	public static FluidStack fluidJumpII;
	public static FluidStack fluidJumpIII;
	public static FluidStack fluidJumpLong;
	public static FluidStack fluidJumpVeryLong;
	public static FluidStack fluidResistance;
	public static FluidStack fluidResistanceII;
	public static FluidStack fluidResistanceIII;
	public static FluidStack fluidResistanceLong;
	public static FluidStack fluidResistanceVeryLong;
	public static FluidStack fluidHunger;
	public static FluidStack fluidHungerII;
	public static FluidStack fluidHungerIII;
	public static FluidStack fluidHungerLong;
	public static FluidStack fluidHungerVeryLong;
	public static FluidStack fluidHealthBoost;
	public static FluidStack fluidHealthBoostII;
	public static FluidStack fluidHealthBoostIII;
	public static FluidStack fluidHealthBoostLong;
	public static FluidStack fluidHealthBoostVeryLong;
	public static FluidStack fluidAbsorption;
	public static FluidStack fluidAbsorptionII;
	public static FluidStack fluidAbsorptionIII;
	public static FluidStack fluidAbsorptionLong;
	public static FluidStack fluidAbsorptionVeryLong;
	public static FluidStack fluidSaturation;
	public static FluidStack fluidSaturationII;
	public static FluidStack fluidSaturationIII;
	public static FluidStack fluidSaturationLong;
	public static FluidStack fluidSaturationVeryLong;
	public static FluidStack fluidWither;
	public static FluidStack fluidWitherII;
	public static FluidStack fluidWitherIII;
	public static FluidStack fluidWitherLong;
	public static FluidStack fluidWitherVeryLong;
	public static FluidStack fluidHarm;
	public static FluidStack fluidHarmII;
	public static FluidStack fluidHarmIII;
	public static FluidStack fluidHeal;
	public static FluidStack fluidHealII;
	public static FluidStack fluidHealIII;
	public static FluidStack fluidWaterBreathe;
	public static FluidStack fluidWaterBreatheLong;
	public static FluidStack fluidWaterBreatheVeryLong;
	public static FluidStack fluidNausea;
	public static FluidStack fluidNauseaLong;
	public static FluidStack fluidNauseaVeryLong;
	public static FluidStack fluidBlindness;
	public static FluidStack fluidBlindnessLong;
	public static FluidStack fluidBlindnessVeryLong;
	public static FluidStack fluidFireImmunity;
	public static FluidStack fluidFireImmunityII;
	
	public static FluidStack fluidHolyWater;
	public static FluidStack fluidHolyWaterII;
	public static FluidStack fluidHolyWaterIII;
	public static FluidStack fluidHolyWaterLong;
	public static FluidStack fluidHolyWaterVeryLong;
	public static FluidStack fluidAntidote;
	public static FluidStack fluidAntidoteII;
	public static FluidStack fluidAntidoteIII;
	public static FluidStack fluidAntidoteLong;
	public static FluidStack fluidAntidoteVeryLong;
	public static FluidStack fluidFlight;
	public static FluidStack fluidFlightLong;
	public static FluidStack fluidFlightVeryLong;
	public static FluidStack fluidCryo;
	public static FluidStack fluidCryoLong;
	public static FluidStack fluidCryoVeryLong;
	public static FluidStack fluidBoom;
	public static FluidStack fluidBoomII;
	public static FluidStack fluidBoomIII;
	public static FluidStack fluidBoomLong;
	public static FluidStack fluidBoomVeryLong;

	@Override
	public String getName() {
		return "PotionPlugin";
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
		potions = new MetaItemPotion("RedGear.Brewcraft.Potions"); // Items MUST be created in pre init! NO exceptions!
	}

	@Override
	public void Init(ModUtils mod) {
		fluidAwkward = createVanillaPotion("Awkward", 16, 0);
		fluidVision = createVanillaPotion("Vision", 8198, 16390);
		fluidVisionLong = createVanillaPotion(fluidVision.getFluid(), 8262, 16454, 480, 0);
		fluidInvisible = createVanillaPotion("Invisible", 8206, 16398);
		fluidInvisibleLong = createVanillaPotion(fluidInvisible.getFluid(), 8270, 16462, 480, 0);
		fluidRegen = createVanillaPotion("Regen", 8193, 16385);
		fluidRegenII = createVanillaPotion(fluidRegen.getFluid(), 8289, 16481, 0, 1);
		fluidRegenLong = createVanillaPotion(fluidRegen.getFluid(), 8257, 16449, 120, 0);
		fluidFast = createVanillaPotion("Fast", 8194, 16386);
		fluidFastLong = createVanillaPotion(fluidFast.getFluid(), 8290, 16450, 480, 0);
		fluidFastII = createVanillaPotion(fluidFast.getFluid(), 8258, 16418, 0, 1);
		fluidWeakness = createVanillaPotion("Weakness", 8200, 16392);
		fluidWeaknessLong = createVanillaPotion(fluidWeakness.getFluid(), 8264, 16456, 240, 0);
		fluidStrength = createVanillaPotion("Strength", 8201, 16393);
		fluidStrengthLong = createVanillaPotion(fluidStrength.getFluid(), 8265, 16457, 480, 0);
		fluidStrengthII = createVanillaPotion(fluidStrength.getFluid(), 8297, 16425, 0, 1);
		fluidFireResist = createVanillaPotion("FireResist", 8195, 16387);
		fluidFireResistLong = createVanillaPotion(fluidFireResist.getFluid(), 8259, 16451, 480, 0);
		fluidSlowness = createVanillaPotion("Slowness", 8202, 16394);
		fluidSlownessLong = createVanillaPotion(fluidSlowness.getFluid(), 8266, 16458, 240, 0);
		fluidPoison = createVanillaPotion("Poison", 8196, 16388);
		fluidPoisonII = createVanillaPotion(fluidPoison.getFluid(), 8260, 16420, 0, 1);
		fluidPoisonLong = createVanillaPotion(fluidPoison.getFluid(), 8228, 16452, 120, 0);
		fluidHarm = createVanillaPotion("Harm", 8204, 16396);
		fluidHarmII = createVanillaPotion(fluidHarm.getFluid(), 8236, 16428, 0, 1);
		fluidHeal = createVanillaPotion("Heal", 8196, 16389);
		fluidHealII = createVanillaPotion(fluidHeal.getFluid(), 8229, 16421, 0, 1);
		fluidWaterBreathe = createVanillaPotion("WaterBreathe", 8205, 16397);
		fluidWaterBreatheLong = createVanillaPotion(fluidWaterBreathe.getFluid(), 8269, 16461, 480, 0);

		fluidHaste = registry.addPotion("Haste", Potion.digSpeed, 90, 0, true);
		fluidHasteII = registry.addPotion(fluidHaste.getFluid(), "HasteII", Potion.digSpeed, 45, 1, true);
		fluidHasteIII = registry.addPotion(fluidHaste.getFluid(), "HasteIII", Potion.digSpeed, 25, 2, true);
		fluidHasteLong = registry.addPotion(fluidHaste.getFluid(), "HasteLong", Potion.digSpeed, 180, 0, true);
		fluidHasteVeryLong = registry.addPotion(fluidHaste.getFluid(), "HasteVeryLong", Potion.digSpeed, 360, 0, true);
		fluidFatigue = registry.addPotion("Fatigue", Potion.digSlowdown, 90, 0, true);
		fluidFatigueII = registry.addPotion(fluidFatigue.getFluid(), "FatigueII", Potion.digSlowdown, 45, 1, true);
		fluidFatigueIII = registry.addPotion(fluidFatigue.getFluid(), "FatigueIII", Potion.digSlowdown, 25, 2, true);
		fluidFatigueLong = registry.addPotion(fluidFatigue.getFluid(), "FatigueLong", Potion.digSlowdown, 180, 0, true);
		fluidFatigueVeryLong = registry.addPotion(fluidFatigue.getFluid(), "FatigueVeryLong", Potion.digSlowdown, 360, 0, true);
		fluidJump = registry.addPotion("Jump", Potion.jump, 60, 0, true);
		fluidJumpII = registry.addPotion(fluidJump.getFluid(), "JumpII", Potion.jump, 30, 1, true);
		fluidJumpIII = registry.addPotion(fluidJump.getFluid(), "JumpIII", Potion.jump, 15, 2, true);
		fluidJumpLong = registry.addPotion(fluidJump.getFluid(), "JumpLong", Potion.jump, 120, 0, true);
		fluidJumpVeryLong = registry.addPotion(fluidJump.getFluid(), "JumpVeryLong", Potion.jump, 240, 0, true);
		fluidNausea = registry.addPotion("Nausea", Potion.confusion, 30, 0);
		fluidNauseaLong = registry.addPotion(fluidNausea.getFluid(), "NauseaLong", Potion.confusion, 60, 0);
		fluidNauseaVeryLong = registry.addPotion(fluidNausea.getFluid(), "NauseaVeryLong", Potion.confusion, 120, 0);
		fluidResistance = registry.addPotion("Resistance", Potion.resistance, 90, 0, true);
		fluidResistanceII = registry.addPotion(fluidResistance.getFluid(), "ResistanceII", Potion.resistance, 45, 1, true);
		fluidResistanceIII = registry.addPotion(fluidResistance.getFluid(), "ResistanceIII", Potion.resistance, 20, 2, true);
		fluidResistanceLong = registry.addPotion(fluidResistance.getFluid(), "ResistanceLong", Potion.resistance, 180, 0, true);
		fluidResistanceVeryLong = registry.addPotion(fluidResistance.getFluid(), "ResistanceVeryLong", Potion.resistance, 360, 0, true);
		fluidBlindness = registry.addPotion("Blindness", Potion.blindness, 30, 0);
		fluidBlindnessLong = registry.addPotion(fluidBlindness.getFluid(), "BlindnessLong", Potion.blindness, 60, 0);
		fluidBlindnessVeryLong = registry.addPotion(fluidBlindness.getFluid(), "BlindnessVeryLong", Potion.blindness, 120, 0);
		fluidHunger = registry.addPotion("Hunger", Potion.hunger, 60, 0);
		fluidHungerII = registry.addPotion(fluidHunger.getFluid(), "HungerII", Potion.hunger, 30, 1);
		fluidHungerIII = registry.addPotion(fluidHunger.getFluid(), "HungerIII", Potion.hunger, 15, 2);
		fluidHungerLong = registry.addPotion(fluidHunger.getFluid(), "HungerLong", Potion.hunger, 120, 0);
		fluidHungerVeryLong = registry.addPotion(fluidHunger.getFluid(), "HungerVeryLong", Potion.hunger, 240, 0);
		fluidHealthBoost = registry.addPotion("HealthBoost", Potion.field_76434_w, 120, 0, true);
		fluidHealthBoostII = registry.addPotion(fluidHealthBoost.getFluid(), "HealthBoostII", Potion.field_76434_w, 60, 1, true);
		fluidHealthBoostIII = registry.addPotion(fluidHealthBoost.getFluid(), "HealthBoostIII", Potion.field_76434_w, 30, 2, true);
		fluidHealthBoostLong = registry.addPotion(fluidHealthBoost.getFluid(), "HealthBoostLong", Potion.field_76434_w, 240, 0, true);
		fluidHealthBoostVeryLong = registry.addPotion(fluidHealthBoost.getFluid(), "HealthBoostVeryLong", Potion.field_76434_w, 480, 0, true);
		fluidAbsorption = registry.addPotion("Absorption", Potion.field_76444_x, 60, 0);
		fluidAbsorptionII = registry.addPotion(fluidAbsorption.getFluid(), "AbsorptionII", Potion.field_76444_x, 30, 1);
		fluidAbsorptionIII = registry.addPotion(fluidAbsorption.getFluid(), "AbsorptionIII", Potion.field_76444_x, 15, 2);
		fluidAbsorptionLong = registry.addPotion(fluidAbsorption.getFluid(), "AbsorptionLong", Potion.field_76444_x, 120, 0);
		fluidAbsorptionVeryLong = registry.addPotion(fluidAbsorption.getFluid(), "AbsorptionVeryLong", Potion.field_76444_x, 240, 0);
		fluidSaturation = registry.addPotion("Saturation", Potion.field_76443_y, 10, 0);
		fluidSaturationII = registry.addPotion(fluidSaturation.getFluid(), "SaturationII", Potion.field_76443_y, 5, 1);
		fluidSaturationIII = registry.addPotion(fluidSaturation.getFluid(), "SaturationIII", Potion.field_76443_y, 3, 2);
		fluidSaturationLong = registry.addPotion(fluidSaturation.getFluid(), "SaturationLong", Potion.field_76443_y, 20, 0);
		fluidSaturationVeryLong = registry.addPotion(fluidSaturation.getFluid(), "SaturationVeryLong", Potion.field_76443_y, 40, 0);
		fluidWither = registry.addPotion("Wither", Potion.wither, 20, 0);
		fluidWitherII = registry.addPotion(fluidWither.getFluid(), "WitherII", Potion.wither, 10, 1);
		fluidWitherIII = registry.addPotion(fluidWither.getFluid(), "WitherIII", Potion.wither, 5, 2);
		fluidWitherLong = registry.addPotion(fluidWither.getFluid(), "WitherLong", Potion.wither, 40, 0);
		fluidWitherVeryLong = registry.addPotion(fluidWither.getFluid(), "WitherVeryLong", Potion.wither, 80, 0);

		fluidHolyWater = registry.addPotion("HolyWater", EffectPlugin.angel, 10, 0, true);
		fluidHolyWaterII = registry.addPotion(fluidHolyWater.getFluid(), "HolyWaterII", EffectPlugin.angel, 5, 1, true);
		fluidHolyWaterLong = registry.addPotion(fluidHolyWater.getFluid(), "HolyWaterLong", EffectPlugin.angel, 10, 0, true);
		fluidHolyWaterVeryLong = registry.addPotion(fluidHolyWater.getFluid(), "HolyWaterVeryLong", EffectPlugin.angel, 20, 0, true);
		fluidHolyWaterIII = registry.addPotion(fluidHolyWater.getFluid(), "HolyWaterIII", EffectPlugin.angel, 3, 2, true);
		fluidFlight = registry.addPotion("Flight", EffectPlugin.flight, 15, 0);
		fluidFlightLong = registry.addPotion(fluidFlight.getFluid(), "FlightLong", EffectPlugin.flight, 30, 0);
		fluidFlightVeryLong = registry.addPotion(fluidFlight.getFluid(), "FlightVeryLong", EffectPlugin.flight, 60, 0);
		fluidAntidote = registry.addPotion("Antidote", EffectPlugin.immunity, 60, 0, true);
		fluidAntidoteII = registry.addPotion(fluidAntidote.getFluid(), "AntidoteII", EffectPlugin.immunity, 45, 1, true);
		fluidAntidoteIII = registry.addPotion(fluidAntidote.getFluid(), "AntidoteIII", EffectPlugin.immunity, 30, 2, true);
		fluidAntidoteLong = registry.addPotion(fluidAntidote.getFluid(), "AntidoteLong", EffectPlugin.immunity, 120, 0, true);
		fluidAntidoteVeryLong = registry.addPotion(fluidAntidote.getFluid(), "AntidoteVeryLong", EffectPlugin.immunity, 240, 0, true);
		fluidBoom = registry.addPotion("Boom", EffectPlugin.creeper, 8, 0, true);
		fluidBoomII = registry.addPotion(fluidBoom.getFluid(), "BoomII", EffectPlugin.creeper, 4, 1, true);
		fluidBoomIII = registry.addPotion(fluidBoom.getFluid(), "BoomIII", EffectPlugin.creeper, 4, 2, true);
		fluidBoomLong = registry.addPotion(fluidBoom.getFluid(), "BoomLong", EffectPlugin.creeper, 16, 0, true);
		fluidBoomVeryLong = registry.addPotion(fluidBoom.getFluid(), "BoomVeryLong", EffectPlugin.creeper, 32, 0, true);
		fluidCryo = registry.addPotion("Cryo", EffectPlugin.frozen, 8, 0, true);
		fluidCryoLong = registry.addPotion(fluidCryo.getFluid(), "CryoLong", EffectPlugin.frozen, 16, 0, true);
		fluidCryoVeryLong = registry.addPotion(fluidCryo.getFluid(), "CryoVeryLong", EffectPlugin.frozen, 30, 0, true);

		fluidRegenIII = registry.addPotion(fluidRegen.getFluid(), "RegenIII", Potion.regeneration, 8, 2);
		fluidRegenVeryLong = registry.addPotion(fluidRegen.getFluid(), "RegenVeryLong", Potion.regeneration, 180, 0);
		fluidFastIII = registry.addPotion(fluidFast.getFluid(), "FastIII", Potion.moveSpeed, 8, 2, true);
		fluidFastVeryLong = registry.addPotion(fluidFast.getFluid(), "FastVeryLong", Potion.moveSpeed, 960, 0);
		fluidStrengthIII = registry.addPotion(fluidStrength.getFluid(), "StrengthIII", Potion.damageBoost, 40, 2, true);
		fluidStrengthVeryLong = registry.addPotion(fluidStrength.getFluid(), "StrengthVeryLong", Potion.damageBoost, 960, 0);
		fluidFireImmunity = registry.addPotion("FireImmunity", EffectPlugin.fireproof, 35, 0, true);
		fluidFireImmunityII = registry.addPotion(fluidFireImmunity.getFluid(), "FireImmunityII", EffectPlugin.fireproof, 15, 1, true);
		fluidFireResistVeryLong = registry.addPotion(fluidFireResist.getFluid(), "FireResistVeryLong", Potion.fireResistance, 960, 0);
		fluidPoisonIII = registry.addPotion(fluidPoison.getFluid(), "PoisonIII", Potion.poison, 8, 2);
		fluidPoisonVeryLong = registry.addPotion(fluidPoison.getFluid(), "PoisonVeryLong", Potion.poison, 240, 0);
		fluidHarmIII = registry.addPotion(fluidHarm.getFluid(), "HarmIII", Potion.harm, 1, 2);
		fluidHealIII = registry.addPotion(fluidHarm.getFluid(), "HealIII", Potion.heal, 1, 2);
		fluidVisionVeryLong = registry.addPotion(fluidVision.getFluid(), "VisionVeryLong", Potion.nightVision, 960, 0);
		fluidWeaknessVeryLong = registry.addPotion(fluidWeakness.getFluid(), "WeaknessVeryLong", Potion.weakness, 480, 0, true);
		fluidSlownessVeryLong = registry.addPotion(fluidSlowness.getFluid(), "SlownessVeryLong", Potion.moveSlowdown, 480, 0, true);
		fluidWaterBreatheVeryLong = registry.addPotion(fluidWaterBreathe.getFluid(), "WaterBreatheVeryLong", Potion.waterBreathing, 960, 0);
	}

	@Override
	public void postInit(ModUtils mod) {
		BrewingAPI.RECIPE_REGISTRY.addRecipe(new FluidStack(FluidRegistry.WATER, 100), fluidAwkward, new ItemStack(Items.nether_wart));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidFireResist, new ItemStack(Items.magma_cream));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidFast, new ItemStack(Items.sugar));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidHeal, new ItemStack(Items.speckled_melon));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidPoison, new ItemStack(Items.spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidRegen, new ItemStack(Items.ghast_tear));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidStrength, new ItemStack(Items.blaze_powder));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidWeakness, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidVision, new ItemStack(Items.golden_carrot));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidWaterBreathe, new ItemStack(Items.fish, 1, 3));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFireResist, fluidFireResistLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFireResistLong, fluidFireResistVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHeal, fluidHealII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHealII, fluidHealIII, IngredientPlugin.obsidianTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegen, fluidRegenLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegenLong, fluidRegenVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegen, fluidRegenII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegenII, fluidRegenIII, IngredientPlugin.obsidianTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidStrength, fluidStrengthII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidStrengthII, fluidStrengthIII, IngredientPlugin.obsidianTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidStrength, fluidStrengthLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidStrengthLong, fluidStrengthVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFast, fluidFastLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFastLong, fluidFastVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFast, fluidFastII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFastII, fluidFastIII, IngredientPlugin.obsidianTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidVision, fluidVisionLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidVisionLong, fluidVisionVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidVision, fluidInvisible, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidVisionLong, fluidInvisibleLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidVisionVeryLong, fluidInvisibleVeryLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidInvisible, fluidInvisibleLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidInvisibleLong, fluidInvisibleVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWaterBreathe, fluidWaterBreatheLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWaterBreatheLong, fluidWaterBreatheVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHeal, fluidHarm, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHealII, fluidHarmII, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHealIII, fluidHarmIII, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidPoison, fluidHarm, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidPoisonII, fluidHarmII, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidPoisonIII, fluidHarmIII, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHarm, fluidHarmII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidPoison, fluidPoisonLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidPoison, fluidPoisonII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidVisionLong, fluidInvisibleLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFireResist, fluidSlowness, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFireResistLong, fluidSlownessLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFast, fluidSlowness, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFastLong, fluidSlownessLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFastVeryLong, fluidSlownessVeryLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidSlowness, fluidSlownessLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidStrength, fluidWeakness, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidStrengthLong, fluidWeaknessLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidStrengthVeryLong, fluidWeaknessVeryLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegen, fluidWeakness, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegenLong, fluidWeaknessLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegenVeryLong, fluidWeaknessVeryLong, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHealII, fluidHeal, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHarmII, fluidHeal, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidFireResistLong, fluidFireResist, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidSlownessLong, fluidSlowness, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWeaknessLong, fluidWeakness, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWaterBreatheLong, fluidWaterBreathe, new ItemStack(Items.glowstone_dust));
		
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegen, fluidHolyWater, IngredientPlugin.holyDust.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegenII, fluidHolyWaterII, IngredientPlugin.holyDust.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegenIII, fluidHolyWaterIII, IngredientPlugin.holyDust.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegenLong, fluidHolyWaterLong, IngredientPlugin.holyDust.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidRegenVeryLong, fluidHolyWaterVeryLong, IngredientPlugin.holyDust.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHolyWater, fluidHarm, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHolyWaterII, fluidHarmII, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHolyWaterIII, fluidHarmIII, new ItemStack(Items.fermented_spider_eye));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHolyWater, fluidHolyWaterII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHolyWater, fluidHolyWaterLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHolyWaterII, fluidHolyWaterIII, IngredientPlugin.obsidianTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidHolyWaterLong, fluidHolyWaterVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidFlight, IngredientPlugin.goldenFeather.getStack());
		//BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAwkward, fluidAntidote, ???);
		//New ingredient for Potion of Remedy?
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAntidote, fluidAntidoteII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAntidote, fluidAntidoteLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAntidoteII, fluidAntidoteIII, IngredientPlugin.obsidianTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidAntidoteLong, fluidAntidoteVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWither, fluidBoom, new ItemStack(Items.gunpowder));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWitherII, fluidBoomII, new ItemStack(Items.gunpowder));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWitherIII, fluidBoomIII, new ItemStack(Items.gunpowder));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWitherLong, fluidBoomLong, new ItemStack(Items.gunpowder));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidWitherVeryLong, fluidBoomVeryLong, new ItemStack(Items.gunpowder));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidBoom, fluidBoomII, new ItemStack(Items.glowstone_dust));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidBoom, fluidBoomLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidBoomII, fluidBoomIII, IngredientPlugin.obsidianTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidBoomLong, fluidBoomVeryLong, IngredientPlugin.pureTear.getStack());
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidSlowness, fluidCryo, new ItemStack(Items.snowball));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidSlownessLong, fluidCryoLong, new ItemStack(Items.snowball));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidSlownessVeryLong, fluidCryoVeryLong, new ItemStack(Items.snowball));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidSlowness, fluidSlownessLong, new ItemStack(Items.redstone));
		BrewingAPI.RECIPE_REGISTRY.addRecipe(fluidSlownessLong, fluidSlownessVeryLong, IngredientPlugin.pureTear.getStack());
		
		//We can discuss the ingredients we're going to use for the unused vanilla potions at some point.
		//I also think we should move the Brewery recipes to a new plugin, but they're here for now.
	}

	private FluidStack createVanillaPotion(String name, int metaBottle, int metaSplash) {
		FluidStack potion = new FluidStack(FluidUtil.createFluid(new FluidPotion("potion" + name, new SimpleItem(
				Items.potionitem, metaBottle)), potionTexture), 100);
		createVanillaPotion(potion, metaBottle, metaSplash);
		return potion;
	}

	private FluidStack createVanillaPotion(Fluid base, int metaBottle, int metaSplash, int duration, int strength) {
		FluidStack potion = registry.NBTHelper(base, duration, strength);
		createVanillaPotion(potion, metaBottle, metaSplash);
		return potion;
	}

	private FluidStack createVanillaPotion(FluidStack potion, int metaBottle, int metaSplash) {
		FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Items.potionitem, 1, metaBottle),
				emptyBottle.getStack());
		if (metaSplash != 0)
			FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Items.potionitem, 1, metaSplash),
					IngredientPlugin.splashBottle.getStack());
		return potion;
	}
}
