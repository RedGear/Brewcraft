package redgear.brewcraft.plugins.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.core.EffectPlugin;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.VanillaPotion;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.brewcraft.utils.PotionRegistry;
import redgear.brewcraft.utils.VanillaPotionRegistry;
import redgear.core.fluids.FluidUtil;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.common.LoaderState.ModState;

public class PotionPlugin implements IPlugin {

	public static MetaItemPotion potions;
	public static MetaItemPotion vials;
	public static MetaItemPotion big;
	public static ItemStack emptyBottle = new ItemStack(Items.glass_bottle, 1, 0);
	public static FluidStack water = new FluidStack(FluidRegistry.WATER, 1000);
	public static FluidStack lava = new FluidStack(FluidRegistry.LAVA, 1000);
	public static PotionRegistry potionRegistry = new PotionRegistry();
	public static RecipeRegistry recipeRegistry = new RecipeRegistry();
	public static VanillaPotionRegistry vanillaRegistry = new VanillaPotionRegistry();
	public static final String potionTexture = "potionWhite";

	//Vanilla potions. (and III-level/Very Long for vanilla bases)
	public static FluidStack fluidAwkward, fluidThick, fluidMundaneExtended, fluidMundane, fluidVision, fluidVisionLong, fluidVisionVeryLong, fluidInvisible,
			fluidInvisibleLong, fluidInvisibleVeryLong, fluidWeakness, fluidWeaknessLong, fluidWeaknessVeryLong,
			fluidFireResist, fluidFireResistLong, fluidFireResistVeryLong, fluidSlowness, fluidSlownessLong,
			fluidSlownessVeryLong, fluidRegen, fluidRegenII, fluidRegenIII, fluidRegenLong, fluidRegenVeryLong,
			fluidFast, fluidFastII, fluidFastIII, fluidFastLong, fluidFastVeryLong, fluidStrength, fluidStrengthII,
			fluidStrengthIII, fluidStrengthLong, fluidStrengthVeryLong, fluidPoison, fluidPoisonII, fluidPoisonIII,
			fluidPoisonLong, fluidPoisonVeryLong, fluidHaste, fluidHasteII, fluidHasteIII, fluidHasteLong,
			fluidHasteVeryLong, fluidFatigue, fluidFatigueII, fluidFatigueIII, fluidFatigueLong, fluidFatigueVeryLong,
			fluidJump, fluidJumpII, fluidJumpIII, fluidJumpLong, fluidJumpVeryLong, fluidResistance, fluidResistanceII,
			fluidResistanceIII, fluidResistanceLong, fluidResistanceVeryLong, fluidHunger, fluidHungerII,
			fluidHungerIII, fluidHungerLong, fluidHungerVeryLong, fluidHealthBoost, fluidHealthBoostII,
			fluidHealthBoostIII, fluidHealthBoostLong, fluidHealthBoostVeryLong, fluidAbsorption, fluidAbsorptionII,
			fluidAbsorptionIII, fluidAbsorptionLong, fluidAbsorptionVeryLong, fluidSaturation, fluidSaturationII,
			fluidSaturationIII, fluidWither, fluidWitherII, fluidWitherIII, fluidWitherLong, fluidWitherVeryLong,
			fluidHarm, fluidHarmII, fluidHarmR, fluidHarmIII, fluidHeal, fluidHealII, fluidHealIII, fluidWaterBreathe,
			fluidWaterBreatheLong, fluidWaterBreatheVeryLong, fluidNausea, fluidNauseaLong, fluidNauseaVeryLong,
			fluidBlindness, fluidBlindnessLong, fluidBlindnessVeryLong;

	//Brewcraft potions.
	public static FluidStack fluidFireImmunity, fluidFireImmunityLong, fluidFireImmunityVeryLong, fluidFireImmunityII,
			fluidFireImmunityIII, fluidFireImmunityIIII, fluidHolyWater, fluidHolyWaterII, fluidHolyWaterIII,
			fluidHolyWaterLong, fluidHolyWaterVeryLong, fluidAntidote, fluidAntidoteII, fluidAntidoteIII,
			fluidAntidoteIIII, fluidAntidoteLong, fluidAntidoteVeryLong, fluidFlight, fluidFlightLong,
			fluidFlightVeryLong, fluidCryo, fluidCryoLong, fluidCryoVeryLong, fluidBoom, fluidBoomII, fluidBoomIII,
			fluidBoomLong, fluidBoomVeryLong, fluidEternalFlame, fluidEternalFlameLong, fluidEternalFlameVeryLong,
			fluidFireEater, fluidFireEaterII, fluidFireEaterIII, fluidFireEaterLong, fluidFireEaterVeryLong;

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
		potions = new MetaItemPotion("RedGear.Brewcraft.Potions", "potion_bottle_drinkable", "potion_overlay",
				"potion_bottle_splash");
		vials = new MetaItemPotion("RedGear.Brewcraft.Vials", "redgear_brewcraft:vial",
				"redgear_brewcraft:vialoverlay", "redgear_brewcraft:vialsplash");
		big = new MetaItemPotion("RedGear.Brewcraft.BigPotions", "redgear_brewcraft:bigbottle",
				"redgear_brewcraft:bigoverlay", "redgear_brewcraft:bigsplash");

		//potions.setEmptyItems(new ItemStack(Items.glass_bottle, 1, 0), new ItemStack(Items.potionitem, 1, 0));
		potions.setEmptyItems(PotionPlugin.emptyBottle, ItemPlugin.splashBottle.getStack());
		potions.setCreativeTab(Brewcraft.tabPotions);
		vials.setEmptyItems(ItemPlugin.emptyVial.getStack(), ItemPlugin.splashVial.getStack());
		vials.setFluidCapacity(250);
		vials.setCreativeTab(Brewcraft.tabVials);
		big.setEmptyItems(ItemPlugin.emptyBigBottle.getStack(), ItemPlugin.splashBigBottle.getStack());
		big.setFluidCapacity(2000);
		big.setCreativeTab(Brewcraft.tabBig);
	}

	@Override
	public void Init(ModUtils mod) {

		//Vanilla potions. (and III-level/Very Long for vanilla bases)
		fluidAwkward = registerVanillaPotion("Awkward", 16, 0, null, 0, 0);
		fluidThick = registerVanillaPotion("Thick", 32, 0, null, 0, 0);
		fluidMundaneExtended = registerVanillaPotion("MundaneExtended", 64, 0, null, 0, 0);
		fluidMundane = registerVanillaPotion("MundaneExt", 8192, 16384, null, 0, 0);
		fluidVision = registerVanillaPotion("Vision", 8198, 16390, Potion.nightVision, 180, 0);
		fluidVisionLong = registerVanillaPotion("VisionLong", 8262, 16454, Potion.nightVision, 480, 0);
		fluidInvisible = registerVanillaPotion("Invisible", 8206, 16398, Potion.invisibility, 180, 0);
		fluidInvisibleLong = registerVanillaPotion("InvisibleLong", 8270, 16462, Potion.invisibility, 480, 0);
		fluidRegen = registerVanillaPotion("Regen", 8193, 16385, Potion.regeneration, 45, 0);
		fluidRegenII = registerVanillaPotion("RegenII", 8225, 16417, Potion.regeneration, 120, 1);
		fluidRegenLong = registerVanillaPotion("RegenLong", 8257, 16449, Potion.regeneration, 120, 0);
		fluidFast = registerVanillaPotion("Fast", 8194, 16386, Potion.moveSpeed, 180, 0);
		fluidFastII = registerVanillaPotion("FastII", 8226, 16418, Potion.moveSpeed, 90, 1);
		fluidFastLong = registerVanillaPotion("FastLong", 8258, 16450, Potion.moveSpeed, 480, 0);
		fluidWeakness = registerVanillaPotion("Weakness", 8200, 16392, Potion.weakness, 90, 0);
		//fluidWeaknessR = registerVanillaPotion("WeaknessR", 8232, 16424, Potion.weakness, 90, 0);
		fluidWeaknessLong = registerVanillaPotion("WeaknessLong", 8264, 16456, Potion.weakness, 240, 0);
		fluidStrength = registerVanillaPotion("Strength", 8201, 16393, Potion.damageBoost, 180, 0);
		fluidStrengthII = registerVanillaPotion("StrengthII", 8233, 16425, Potion.damageBoost, 90, 1);
		fluidStrengthLong = registerVanillaPotion("StrengthLong", 8265, 16457, Potion.damageBoost, 480, 0);		
		fluidFireResist = registerVanillaPotion("FireResist", 8195, 16387, Potion.fireResistance, 180, 0);
		fluidFireResistLong = registerVanillaPotion("FireResistLong", 8259, 16451, Potion.fireResistance, 480, 0);
		fluidSlowness = registerVanillaPotion("Slowness", 8202, 16394, Potion.moveSlowdown, 90, 0);
		//fluidSlownessR = registerVanillaPotion("SlownessR", 8234, 16426, Potion.moveSlowdown, 90, 0);
		fluidSlownessLong = registerVanillaPotion("SlownessLong", 8266, 16458, Potion.moveSlowdown, 240, 0);
		fluidPoison = registerVanillaPotion("Poison", 8196, 16388, Potion.poison, 45, 0);
		fluidPoisonII = registerVanillaPotion("PoisonII", 8228, 16420, Potion.poison, 22, 1);
		fluidPoisonLong = registerVanillaPotion("PoisonLong", 8260, 16452, Potion.poison, 120, 0);
		fluidHarm = registerVanillaPotion("Harm", 8204, 16396, Potion.harm, 0, 0);
		fluidHarmII = registerVanillaPotion("HarmII", 8236, 16428, Potion.harm, 0, 1);
		fluidHarmR = registerVanillaPotion("HarmR", 8268, 16460, Potion.harm, 0, 0);
		fluidHeal = registerVanillaPotion("Heal", 8197, 16389, Potion.heal, 0, 0);
		fluidHealII = registerVanillaPotion("HealII", 8229, 16421, Potion.heal, 0, 1);
		fluidWaterBreathe = registerVanillaPotion("WaterBreathe", 8205, 16397, Potion.waterBreathing, 180, 0);
		fluidWaterBreatheLong = registerVanillaPotion("WaterBreatheLong", 8269, 16461, Potion.waterBreathing, 480, 0);
		fluidHaste = potionRegistry.addPotion(null, "Haste", Potion.digSpeed, 90, 0, true);
		fluidHasteII = potionRegistry.addPotion(null, "HasteII", Potion.digSpeed, 45, 1, true);
		fluidHasteIII = potionRegistry.addPotion(null, "HasteIII", Potion.digSpeed, 25, 2, true);
		fluidHasteLong = potionRegistry.addPotion(null, "HasteLong", Potion.digSpeed, 180, 0, true);
		fluidHasteVeryLong = potionRegistry.addPotion(null, "HasteVeryLong", Potion.digSpeed, 360, 0, true);
		fluidFatigue = potionRegistry.addPotion(null, "Fatigue", Potion.digSlowdown, 90, 0, true);
		fluidFatigueII = potionRegistry.addPotion(null, "FatigueII", Potion.digSlowdown, 45, 1, true);
		fluidFatigueIII = potionRegistry.addPotion(null, "FatigueIII", Potion.digSlowdown, 25, 2, true);
		fluidFatigueLong = potionRegistry.addPotion(null, "FatigueLong", Potion.digSlowdown, 180, 0, true);
		fluidFatigueVeryLong = potionRegistry.addPotion(null, "FatigueVeryLong", Potion.digSlowdown, 360, 0, true);
		fluidJump = potionRegistry.addPotion(null, "Jump", Potion.jump, 60, 0, true);
		fluidJumpII = potionRegistry.addPotion(null, "JumpII", Potion.jump, 30, 1, true);
		fluidJumpIII = potionRegistry.addPotion(null, "JumpIII", Potion.jump, 15, 2, true);
		fluidJumpLong = potionRegistry.addPotion(null, "JumpLong", Potion.jump, 120, 0, true);
		fluidJumpVeryLong = potionRegistry.addPotion(null, "JumpVeryLong", Potion.jump, 240, 0, true);
		fluidNausea = potionRegistry.addPotion(null, "Nausea", Potion.confusion, 30, 0);
		fluidNauseaLong = potionRegistry.addPotion(null, "NauseaLong", Potion.confusion, 60, 0);
		fluidNauseaVeryLong = potionRegistry.addPotion(null, "NauseaVeryLong", Potion.confusion, 120, 0);
		fluidResistance = potionRegistry.addPotion(null, "Resistance", Potion.resistance, 90, 0, true);
		fluidResistanceII = potionRegistry.addPotion(null, "ResistanceII", Potion.resistance, 45, 1, true);
		fluidResistanceIII = potionRegistry.addPotion(null, "ResistanceIII", Potion.resistance, 20, 2, true);
		fluidResistanceLong = potionRegistry.addPotion(null, "ResistanceLong", Potion.resistance, 180, 0, true);
		fluidResistanceVeryLong = potionRegistry.addPotion(null, "ResistanceVeryLong", Potion.resistance, 360, 0, true);
		fluidBlindness = potionRegistry.addPotion(null, "Blindness", Potion.blindness, 30, 0);
		fluidBlindnessLong = potionRegistry.addPotion(null, "BlindnessLong", Potion.blindness, 60, 0);
		fluidBlindnessVeryLong = potionRegistry.addPotion(null, "BlindnessVeryLong", Potion.blindness, 120, 0);
		fluidHunger = potionRegistry.addPotion(null, "Hunger", Potion.hunger, 60, 0);
		fluidHungerII = potionRegistry.addPotion(null, "HungerII", Potion.hunger, 30, 1);
		fluidHungerIII = potionRegistry.addPotion(null, "HungerIII", Potion.hunger, 15, 2);
		fluidHungerLong = potionRegistry.addPotion(null, "HungerLong", Potion.hunger, 120, 0);
		fluidHungerVeryLong = potionRegistry.addPotion(null, "HungerVeryLong", Potion.hunger, 240, 0);
		fluidHealthBoost = potionRegistry.addPotion(null, "HealthBoost", Potion.field_76434_w, 120, 0, true);
		fluidHealthBoostII = potionRegistry.addPotion(null, "HealthBoostII", Potion.field_76434_w, 60, 1, true);
		fluidHealthBoostIII = potionRegistry.addPotion(null, "HealthBoostIII", Potion.field_76434_w, 30, 2, true);
		fluidHealthBoostLong = potionRegistry.addPotion(null, "HealthBoostLong", Potion.field_76434_w, 240, 0, true);
		fluidHealthBoostVeryLong = potionRegistry.addPotion(null, "HealthBoostVeryLong", Potion.field_76434_w, 480, 0, true);
		fluidAbsorption = potionRegistry.addPotion(null, "Absorption", Potion.field_76444_x, 60, 0);
		fluidAbsorptionII = potionRegistry.addPotion(null, "AbsorptionII", Potion.field_76444_x, 30, 1);
		fluidAbsorptionIII = potionRegistry.addPotion(null, "AbsorptionIII", Potion.field_76444_x, 15, 2);
		fluidAbsorptionLong = potionRegistry.addPotion(null, "AbsorptionLong", Potion.field_76444_x, 120, 0);
		fluidAbsorptionVeryLong = potionRegistry.addPotion(null, "AbsorptionVeryLong", Potion.field_76444_x, 240, 0);
		fluidSaturation = potionRegistry.addPotion(null, "Saturation", Potion.field_76443_y, 1, 0);
		fluidSaturationII = potionRegistry.addPotion(null, "SaturationII", Potion.field_76443_y, 1, 1);
		fluidSaturationIII = potionRegistry.addPotion(null, "SaturationIII", Potion.field_76443_y, 1, 2);
		fluidWither = potionRegistry.addPotion(null, "Wither", Potion.wither, 20, 0);
		fluidWitherII = potionRegistry.addPotion(null, "WitherII", Potion.wither, 10, 1);
		fluidWitherIII = potionRegistry.addPotion(null, "WitherIII", EffectPlugin.wither, 5, 2);
		fluidWitherLong = potionRegistry.addPotion(null, "WitherLong", Potion.wither, 40, 0);
		fluidWitherVeryLong = potionRegistry.addPotion(null, "WitherVeryLong", Potion.wither, 80, 0);
		fluidRegenIII = potionRegistry.addPotion(null, "RegenIII", EffectPlugin.regeneration, 8, 2);
		fluidRegenVeryLong = potionRegistry.addPotion(null, "RegenVeryLong", Potion.regeneration, 180, 0);
		fluidFastIII = potionRegistry.addPotion(null, "FastIII", Potion.moveSpeed, 8, 2, true);
		fluidFastVeryLong = potionRegistry.addPotion(null, "FastVeryLong", Potion.moveSpeed, 960, 0);
		fluidStrengthIII = potionRegistry.addPotion(null, "StrengthIII", Potion.damageBoost, 40, 2, true);
		fluidStrengthVeryLong = potionRegistry.addPotion(null, "StrengthVeryLong", Potion.damageBoost, 960, 0);
		fluidFireResistVeryLong = potionRegistry.addPotion(null, "FireResistVeryLong", Potion.fireResistance, 960, 0);
		fluidPoisonIII = potionRegistry.addPotion(null, "PoisonIII", EffectPlugin.poison, 8, 2);
		fluidPoisonVeryLong = potionRegistry.addPotion(null, "PoisonVeryLong", Potion.poison, 240, 0);
		fluidHarmIII = potionRegistry.addPotion(null, "HarmIII", Potion.harm, 1, 2);
		fluidHealIII = potionRegistry.addPotion(null, "HealIII", Potion.heal, 1, 2);
		fluidVisionVeryLong = potionRegistry.addPotion(null, "VisionVeryLong", Potion.nightVision, 960, 0);
		fluidInvisibleVeryLong = potionRegistry.addPotion(null, "InvisibleVeryLong", Potion.invisibility, 960, 0);
		fluidWeaknessVeryLong = potionRegistry.addPotion(null, "WeaknessVeryLong", Potion.weakness, 480, 0, true);
		fluidSlownessVeryLong = potionRegistry.addPotion(null, "SlownessVeryLong", Potion.moveSlowdown, 480, 0, true);
		fluidWaterBreatheVeryLong = potionRegistry.addPotion(null, "WaterBreatheVeryLong", Potion.waterBreathing, 960, 0);

		//Brewcraft potions.
		fluidHolyWater = potionRegistry.addPotion(null, "HolyWater", EffectPlugin.angel, 10, 0, true);
		fluidHolyWaterII = potionRegistry.addPotion(null, "HolyWaterII", EffectPlugin.angel, 5, 1, true);
		fluidHolyWaterLong = potionRegistry.addPotion(null, "HolyWaterLong", EffectPlugin.angel, 20, 0, true);
		fluidHolyWaterVeryLong = potionRegistry.addPotion(null, "HolyWaterVeryLong", EffectPlugin.angel, 40, 0, true);
		fluidHolyWaterIII = potionRegistry.addPotion(null, "HolyWaterIII", EffectPlugin.angel, 3, 2, true);
		fluidFlight = potionRegistry.addPotion(null, "Flight", EffectPlugin.flight, 15, 0);
		fluidFlightLong = potionRegistry.addPotion(null, "FlightLong", EffectPlugin.flight, 30, 0);
		fluidFlightVeryLong = potionRegistry.addPotion(null, "FlightVeryLong", EffectPlugin.flight, 60, 0);
		fluidAntidote = potionRegistry.addPotion(null, "Antidote", EffectPlugin.immunity, 60, 0, true);
		fluidAntidoteII = potionRegistry.addPotion(null, "AntidoteII", EffectPlugin.immunity, 45, 1, true);
		fluidAntidoteIII = potionRegistry.addPotion(null, "AntidoteIII", EffectPlugin.immunity, 30, 2, true);
		fluidAntidoteIIII = potionRegistry.addPotion(null, "AntidoteIIII", EffectPlugin.immunity, 20, 3, true);
		fluidAntidoteLong = potionRegistry.addPotion(null, "AntidoteLong", EffectPlugin.immunity, 120, 0, true);
		fluidAntidoteVeryLong = potionRegistry.addPotion(null, "AntidoteVeryLong", EffectPlugin.immunity, 240, 0, true);
		fluidBoom = potionRegistry.addPotion(null, "Boom", EffectPlugin.creeper, 8, 0, true);
		fluidBoomII = potionRegistry.addPotion(null, "BoomII", EffectPlugin.creeper, 4, 1, true);
		fluidBoomIII = potionRegistry.addPotion(null, "BoomIII", EffectPlugin.creeper, 4, 2, true);
		fluidBoomLong = potionRegistry.addPotion(null, "BoomLong", EffectPlugin.creeper, 16, 0, true);
		fluidBoomVeryLong = potionRegistry.addPotion(null, "BoomVeryLong", EffectPlugin.creeper, 32, 0, true);
		fluidCryo = potionRegistry.addPotion(null, "Cryo", EffectPlugin.frozen, 8, 0, true);
		fluidCryoLong = potionRegistry.addPotion(null, "CryoLong", EffectPlugin.frozen, 16, 0, true);
		fluidCryoVeryLong = potionRegistry.addPotion(null, "CryoVeryLong", EffectPlugin.frozen, 30, 0, true);
		fluidEternalFlame = potionRegistry.addPotion(null, "EternalFlame", EffectPlugin.flame, 30, 0);
		fluidEternalFlameLong = potionRegistry.addPotion(null, "EternalFlameLong", EffectPlugin.flame, 60, 0);
		fluidEternalFlameVeryLong = potionRegistry.addPotion(null, "EternalFlameVeryLong", EffectPlugin.flame, 120, 0);
		fluidFireImmunity = potionRegistry.addPotion(null, "FireImmunity", EffectPlugin.fireproof, 35, 0, true);
		fluidFireImmunityLong = potionRegistry.addPotion(null, "FireImmunityLong", EffectPlugin.fireproof, 70, 0, true);
		fluidFireImmunityVeryLong = potionRegistry.addPotion(null, "FireImmunityVeryLong", EffectPlugin.fireproof, 140, 0, true);
		fluidFireImmunityII = potionRegistry.addPotion(null, "FireImmunityII", EffectPlugin.fireproof, 15, 1, true);
		fluidFireImmunityIII = potionRegistry.addPotion(null, "FireImmunityIII", EffectPlugin.fireproof, 10, 2, true);
		fluidFireImmunityIIII = potionRegistry.addPotion(null, "FireImmunityIIII", EffectPlugin.fireproof, 5, 3, true);
		fluidFireEater = potionRegistry.addPotion(null, "FireEater", EffectPlugin.fireEater, 45, 0, true);
		fluidFireEaterII = potionRegistry.addPotion(null, "FireEaterII", EffectPlugin.fireEater, 30, 1, true);
		fluidFireEaterIII = potionRegistry.addPotion(null, "FireEaterIII", EffectPlugin.fireEater, 15, 2, true);
		fluidFireEaterLong = potionRegistry.addPotion(null, "FireEaterLong", EffectPlugin.fireEater, 90, 0, true);
		fluidFireEaterVeryLong = potionRegistry.addPotion(null, "FireEaterVeryLong", EffectPlugin.fireEater, 180, 0, true);

		//Vials
		//potionRegistry.addPotion(fluidAwkward, "Awkward", null, 0, 0, 250);
		potionRegistry.addPotion(fluidVision, vials, "Vision", Potion.nightVision, 45, 0);
		potionRegistry.addPotion(fluidVisionLong, vials, "VisionLong", Potion.nightVision, 90, 0);
		potionRegistry.addPotion(fluidVisionVeryLong, vials, "VisionVeryLong", Potion.nightVision, 480, 0);
		potionRegistry.addPotion(fluidInvisible, vials, "Invisible", Potion.invisibility, 45, 0);
		potionRegistry.addPotion(fluidInvisibleLong, vials, "InvisibleLong", Potion.invisibility, 90, 0);
		potionRegistry.addPotion(fluidInvisibleVeryLong, vials, "InvisibleVeryLong", Potion.invisibility, 180, 0);
		potionRegistry.addPotion(fluidRegen, vials, "Regen", Potion.regeneration, 15, 0);
		potionRegistry.addPotion(fluidRegenII, vials, "RegenII", Potion.regeneration, 7, 1);
		potionRegistry.addPotion(fluidRegenLong, vials, "RegenLong", Potion.regeneration, 45, 0);
		potionRegistry.addPotion(fluidRegenIII, vials, "RegenIII", EffectPlugin.regeneration, 3, 2);
		potionRegistry.addPotion(fluidRegenVeryLong, vials, "RegenVeryLong", Potion.regeneration, 90, 0);
		potionRegistry.addPotion(fluidFast, vials, "Fast", Potion.moveSpeed, 20, 0, true);
		potionRegistry.addPotion(fluidFastLong, vials, "FastLong", Potion.moveSpeed, 40, 0, true);
		potionRegistry.addPotion(fluidFastII, vials, "FastII", Potion.moveSpeed, 10, 1, true);
		potionRegistry.addPotion(fluidFastIII, vials, "FastIII", Potion.moveSpeed, 3, 2, true);
		potionRegistry.addPotion(fluidFastVeryLong, vials, "FastVeryLong", Potion.moveSpeed, 480, 0);
		potionRegistry.addPotion(fluidWeakness, vials, "Weakness", Potion.weakness, 20, 0, true);
		potionRegistry.addPotion(fluidWeaknessLong, vials, "WeaknessLong", Potion.weakness, 40, 0, true);
		potionRegistry.addPotion(fluidWeaknessVeryLong, vials, "WeaknessVeryLong", Potion.weakness, 240, 0, true);
		potionRegistry.addPotion(fluidStrength, vials, "Strength", Potion.damageBoost, 45, 0, true);
		potionRegistry.addPotion(fluidStrengthLong, vials, "StrengthLong", Potion.damageBoost, 90, 0, true);
		potionRegistry.addPotion(fluidStrengthII, vials, "StrengthII", Potion.damageBoost, 20, 1, true);
		potionRegistry.addPotion(fluidStrengthIII, vials, "StrengthIII", Potion.damageBoost, 10, 2, true);
		potionRegistry.addPotion(fluidStrengthVeryLong, vials, "StrengthVeryLong", Potion.damageBoost, 480, 0);
		potionRegistry.addPotion(fluidFireResist, vials, "FireResist", Potion.fireResistance, 45, 0);
		potionRegistry.addPotion(fluidFireResistLong, vials, "FireResistLong", Potion.fireResistance, 90, 0);
		potionRegistry.addPotion(fluidFireResistVeryLong, vials, "FireResistVeryLong", Potion.fireResistance, 480, 0);
		potionRegistry.addPotion(fluidSlowness, vials, "Slowness", Potion.moveSlowdown, 23, 0, true);
		potionRegistry.addPotion(fluidSlownessLong, vials, "SlownessLong", Potion.moveSlowdown, 45, 0, true);
		potionRegistry.addPotion(fluidSlownessVeryLong, vials, "SlownessVeryLong", Potion.moveSlowdown, 240, 0, true);
		potionRegistry.addPotion(fluidPoison, vials, "Poison", Potion.poison, 7, 0);
		potionRegistry.addPotion(fluidPoisonII, vials, "PoisonII", Potion.poison, 4, 1);
		potionRegistry.addPotion(fluidPoisonLong, vials, "PoisonLong", Potion.poison, 15, 0);
		potionRegistry.addPotion(fluidPoisonIII, vials, "PoisonIII", EffectPlugin.poison, 3, 2);
		potionRegistry.addPotion(fluidPoisonVeryLong, vials, "PoisonVeryLong", Potion.poison, 120, 0);
		potionRegistry.addPotion(fluidHarm, vials, "Harm", Potion.harm, 1, 0);
		potionRegistry.addPotion(fluidHarmII, vials, "HarmII", Potion.harm, 1, 1);
		potionRegistry.addPotion(fluidHarmIII, vials, "HarmIII", Potion.harm, 1, 2);
		potionRegistry.addPotion(fluidHeal, vials, "Heal", Potion.heal, 1, 0);
		potionRegistry.addPotion(fluidHealII, vials, "HealII", Potion.heal, 1, 1);
		potionRegistry.addPotion(fluidHealIII, vials, "HealIII", Potion.heal, 1, 2);
		potionRegistry.addPotion(fluidWaterBreathe, vials, "WaterBreathe", Potion.waterBreathing, 45, 0);
		potionRegistry.addPotion(fluidWaterBreatheLong, vials, "WaterBreatheLong", Potion.waterBreathing, 120, 0);
		potionRegistry.addPotion(fluidWaterBreatheVeryLong, vials, "WaterBreatheVeryLong", Potion.waterBreathing, 480, 0);
		potionRegistry.addPotion(fluidHaste, vials, "Haste", Potion.digSpeed, 25, 0, true);
		potionRegistry.addPotion(fluidHasteII, vials, "HasteII", Potion.digSpeed, 15, 1, true);
		potionRegistry.addPotion(fluidHasteIII, vials, "HasteIII", Potion.digSpeed, 5, 2, true);
		potionRegistry.addPotion(fluidHasteLong, vials, "HasteLong", Potion.digSpeed, 90, 0, true);
		potionRegistry.addPotion(fluidHasteVeryLong, vials, "HasteVeryLong", Potion.digSpeed, 180, 0, true);
		potionRegistry.addPotion(fluidFatigue, vials, "Fatigue", Potion.digSlowdown, 25, 0, true);
		potionRegistry.addPotion(fluidFatigueII, vials, "FatigueII", Potion.digSlowdown, 14, 1, true);
		potionRegistry.addPotion(fluidFatigueIII, vials, "FatigueIII", Potion.digSlowdown, 5, 2, true);
		potionRegistry.addPotion(fluidFatigueLong, vials, "FatigueLong", Potion.digSlowdown, 90, 0, true);
		potionRegistry.addPotion(fluidFatigueVeryLong, vials, "FatigueVeryLong", Potion.digSlowdown, 180, 0, true);
		potionRegistry.addPotion(fluidJump, vials, "Jump", Potion.jump, 15, 0, true);
		potionRegistry.addPotion(fluidJumpII, vials, "JumpII", Potion.jump, 10, 1, true);
		potionRegistry.addPotion(fluidJumpIII, vials, "JumpIII", Potion.jump, 5, 2, true);
		potionRegistry.addPotion(fluidJumpLong, vials, "JumpLong", Potion.jump, 60, 0, true);
		potionRegistry.addPotion(fluidJumpVeryLong, vials, "JumpVeryLong", Potion.jump, 120, 0, true);
		potionRegistry.addPotion(fluidNausea, vials, "Nausea", Potion.confusion, 10, 0);
		potionRegistry.addPotion(fluidNauseaLong, vials, "NauseaLong", Potion.confusion, 30, 0);
		potionRegistry.addPotion(fluidNauseaVeryLong, vials, "NauseaVeryLong", Potion.confusion, 60, 0);
		potionRegistry.addPotion(fluidResistance, vials, "Resistance", Potion.resistance, 25, 0, true);
		potionRegistry.addPotion(fluidResistanceII, vials, "ResistanceII", Potion.resistance, 15, 1, true);
		potionRegistry.addPotion(fluidResistanceIII, vials, "ResistanceIII", Potion.resistance, 5, 2, true);
		potionRegistry.addPotion(fluidResistanceLong, vials, "ResistanceLong", Potion.resistance, 90, 0, true);
		potionRegistry.addPotion(fluidResistanceVeryLong, vials, "ResistanceVeryLong", Potion.resistance, 180, 0, true);
		potionRegistry.addPotion(fluidBlindness, vials, "Blindness", Potion.blindness, 10, 0);
		potionRegistry.addPotion(fluidBlindnessLong, vials, "BlindnessLong", Potion.blindness, 30, 0);
		potionRegistry.addPotion(fluidBlindnessVeryLong, vials, "BlindnessVeryLong", Potion.blindness, 60, 0);
		potionRegistry.addPotion(fluidHunger, vials, "Hunger", Potion.hunger, 15, 0);
		potionRegistry.addPotion(fluidHungerII, vials, "HungerII", Potion.hunger, 10, 1);
		potionRegistry.addPotion(fluidHungerIII, vials, "HungerIII", Potion.hunger, 5, 2);
		potionRegistry.addPotion(fluidHungerLong, vials, "HungerLong", Potion.hunger, 60, 0);
		potionRegistry.addPotion(fluidHungerVeryLong, vials, "HungerVeryLong", Potion.hunger, 120, 0);
		potionRegistry.addPotion(fluidHealthBoost, vials, "HealthBoost", Potion.field_76434_w, 30, 0, true);
		potionRegistry.addPotion(fluidHealthBoostII, vials, "HealthBoostII", Potion.field_76434_w, 15, 1, true);
		potionRegistry.addPotion(fluidHealthBoostIII, vials, "HealthBoostIII", Potion.field_76434_w, 10, 2, true);
		potionRegistry.addPotion(fluidHealthBoostLong, vials, "HealthBoostLong", Potion.field_76434_w, 120, 0, true);
		potionRegistry.addPotion(fluidHealthBoostVeryLong, vials, "HealthBoostVeryLong", Potion.field_76434_w, 240, 0, true);
		potionRegistry.addPotion(fluidAbsorption, vials, "Absorption", Potion.field_76444_x, 15, 0);
		potionRegistry.addPotion(fluidAbsorptionII, vials, "AbsorptionII", Potion.field_76444_x, 10, 1);
		potionRegistry.addPotion(fluidAbsorptionIII, vials, "AbsorptionIII", Potion.field_76444_x, 5, 2);
		potionRegistry.addPotion(fluidAbsorptionLong, vials, "AbsorptionLong", Potion.field_76444_x, 60, 0);
		potionRegistry.addPotion(fluidAbsorptionVeryLong, vials, "AbsorptionVeryLong", Potion.field_76444_x, 120, 0);
		potionRegistry.addPotion(fluidSaturation, vials, "Saturation", Potion.field_76443_y, 1, 0);
		potionRegistry.addPotion(fluidSaturationII, vials, "SaturationII", Potion.field_76443_y, 1, 1);
		potionRegistry.addPotion(fluidSaturationIII, vials, "SaturationIII", Potion.field_76443_y, 1, 2);
		potionRegistry.addPotion(fluidWither, vials, "Wither", Potion.wither, 5, 0);
		potionRegistry.addPotion(fluidWitherII, vials, "WitherII", Potion.wither, 3, 1);
		potionRegistry.addPotion(fluidWitherIII, vials, "WitherIII", EffectPlugin.wither, 3, 2);
		potionRegistry.addPotion(fluidWitherLong, vials, "WitherLong", Potion.wither, 20, 0);
		potionRegistry.addPotion(fluidWitherVeryLong, vials, "WitherVeryLong", Potion.wither, 40, 0);
		potionRegistry.addPotion(fluidHolyWater, vials, "HolyWater", EffectPlugin.angel, 5, 0, true);
		potionRegistry.addPotion(fluidHolyWaterII, vials, "HolyWaterII", EffectPlugin.angel, 5, 1, true);
		potionRegistry.addPotion(fluidHolyWaterLong, vials, "HolyWaterLong", EffectPlugin.angel, 10, 0, true);
		potionRegistry.addPotion(fluidHolyWaterVeryLong, vials, "HolyWaterVeryLong", EffectPlugin.angel, 20, 0, true);
		potionRegistry.addPotion(fluidHolyWaterIII, vials, "HolyWaterIII", EffectPlugin.angel, 3, 2, true);
		potionRegistry.addPotion(fluidFlight, vials, "Flight", EffectPlugin.flight, 5, 0);
		potionRegistry.addPotion(fluidFlightLong, vials, "FlightLong", EffectPlugin.flight, 20, 0);
		potionRegistry.addPotion(fluidFlightVeryLong, vials, "FlightVeryLong", EffectPlugin.flight, 30, 0);
		potionRegistry.addPotion(fluidAntidote, vials, "Antidote", EffectPlugin.immunity, 15, 0, true);
		potionRegistry.addPotion(fluidAntidoteII, vials, "AntidoteII", EffectPlugin.immunity, 10, 1, true);
		potionRegistry.addPotion(fluidAntidoteIII, vials, "AntidoteIII", EffectPlugin.immunity, 10, 2, true);
		potionRegistry.addPotion(fluidAntidoteIIII, vials, "AntidoteIIII", EffectPlugin.immunity, 5, 3, true);
		potionRegistry.addPotion(fluidAntidoteLong, vials, "AntidoteLong", EffectPlugin.immunity, 60, 0, true);
		potionRegistry.addPotion(fluidAntidoteVeryLong, vials, "AntidoteVeryLong", EffectPlugin.immunity, 120, 0, true);
		potionRegistry.addPotion(fluidBoom, vials, "Boom", EffectPlugin.creeper, 4, 0, true);
		potionRegistry.addPotion(fluidBoomII, vials, "BoomII", EffectPlugin.creeper, 4, 1, true);
		potionRegistry.addPotion(fluidBoomIII, vials, "BoomIII", EffectPlugin.creeper, 4, 2, true);
		potionRegistry.addPotion(fluidBoomLong, vials, "BoomLong", EffectPlugin.creeper, 8, 0, true);
		potionRegistry.addPotion(fluidBoomVeryLong, vials, "BoomVeryLong", EffectPlugin.creeper, 16, 0, true);
		potionRegistry.addPotion(fluidCryo, vials, "Cryo", EffectPlugin.frozen, 2, 0, true);
		potionRegistry.addPotion(fluidCryoLong, vials, "CryoLong", EffectPlugin.frozen, 8, 0, true);
		potionRegistry.addPotion(fluidCryoVeryLong, vials, "CryoVeryLong", EffectPlugin.frozen, 20, 0, true);
		potionRegistry.addPotion(fluidEternalFlame, vials, "EternalFlame", EffectPlugin.flame, 10, 0);
		potionRegistry.addPotion(fluidEternalFlameLong, vials, "EternalFlameLong", EffectPlugin.flame, 30, 0);
		potionRegistry.addPotion(fluidEternalFlameVeryLong, vials, "EternalFlameVeryLong", EffectPlugin.flame, 60, 0);
		potionRegistry.addPotion(fluidFireImmunity, vials, "FireImmunity", EffectPlugin.fireproof, 10, 0, true);
		potionRegistry.addPotion(fluidFireImmunityLong, vials, "FireImmunityLong", EffectPlugin.fireproof, 70, 0, true);
		potionRegistry.addPotion(fluidFireImmunityVeryLong, vials, "FireImmunityVeryLong", EffectPlugin.fireproof, 140, 0, true);
		potionRegistry.addPotion(fluidFireImmunityII, vials, "FireImmunityII", EffectPlugin.fireproof, 15, 1, true);
		potionRegistry.addPotion(fluidFireImmunityIII, vials, "FireImmunityIII", EffectPlugin.fireproof, 10, 2, true);
		potionRegistry.addPotion(fluidFireImmunityIIII, vials, "FireImmunityIIII", EffectPlugin.fireproof, 5, 3, true);
		potionRegistry.addPotion(fluidFireEater, vials, "FireEater", EffectPlugin.fireEater, 15, 0, true);
		potionRegistry.addPotion(fluidFireEaterII, vials, "FireEaterII", EffectPlugin.fireEater, 10, 1, true);
		potionRegistry.addPotion(fluidFireEaterIII, vials, "FireEaterIII", EffectPlugin.fireEater, 5, 2, true);
		potionRegistry.addPotion(fluidFireEaterLong, vials, "FireEaterLong", EffectPlugin.fireEater, 45, 0, true);
		potionRegistry.addPotion(fluidFireEaterVeryLong, vials, "FireEaterVeryLong", EffectPlugin.fireEater, 90, 0,  true);

		//Big Potions
		//potionRegistry.addPotion(fluidAwkward, "Awkward", null, 0, 0);
		potionRegistry.addPotion(fluidVision, big, "Vision", Potion.nightVision, 360, 0);
		potionRegistry.addPotion(fluidVisionLong, big, "VisionLong", Potion.nightVision, 720, 0);
		potionRegistry.addPotion(fluidVisionVeryLong, big, "VisionVeryLong", Potion.nightVision, 1440, 0);
		potionRegistry.addPotion(fluidInvisible, big, "Invisible", Potion.invisibility, 360, 0);
		potionRegistry.addPotion(fluidInvisibleLong, big, "InvisibleLong", Potion.invisibility, 720, 0);
		potionRegistry.addPotion(fluidInvisibleVeryLong, big, "InvisibleVeryLong", Potion.invisibility, 1440, 0);
		potionRegistry.addPotion(fluidRegen, big, "Regen", Potion.regeneration, 90, 0);
		potionRegistry.addPotion(fluidRegenII, big, "RegenII", Potion.regeneration, 45, 1);
		potionRegistry.addPotion(fluidRegenLong, big, "RegenLong", Potion.regeneration, 180, 0);
		potionRegistry.addPotion(fluidRegenIII, big, "RegenIII", EffectPlugin.regeneration, 23, 2);
		potionRegistry.addPotion(fluidRegenVeryLong, big, "RegenVeryLong", Potion.regeneration, 360, 0);
		potionRegistry.addPotion(fluidFast, big, "Fast", Potion.moveSpeed, 360, 0, true);
		potionRegistry.addPotion(fluidFastLong, big, "FastLong", Potion.moveSpeed, 720, 0, true);
		potionRegistry.addPotion(fluidFastII, big, "FastII", Potion.moveSpeed, 180, 1, true);
		potionRegistry.addPotion(fluidFastIII, big, "FastIII", Potion.moveSpeed, 90, 2, true);
		potionRegistry.addPotion(fluidFastVeryLong, big, "FastVeryLong", Potion.moveSpeed, 1440, 0);
		potionRegistry.addPotion(fluidWeakness, big, "Weakness", Potion.weakness, 180, 0, true);
		potionRegistry.addPotion(fluidWeaknessLong, big, "WeaknessLong", Potion.weakness, 360, 0, true);
		potionRegistry.addPotion(fluidWeaknessVeryLong, big, "WeaknessVeryLong", Potion.weakness, 720, 0, true);
		potionRegistry.addPotion(fluidStrength, big, "Strength", Potion.damageBoost, 360, 0, true);
		potionRegistry.addPotion(fluidStrengthLong, big, "StrengthLong", Potion.damageBoost, 720, 0, true);
		potionRegistry.addPotion(fluidStrengthII, big, "StrengthII", Potion.damageBoost, 180, 1, true);
		potionRegistry.addPotion(fluidStrengthIII, big, "StrengthIII", Potion.damageBoost, 90, 2, true);
		potionRegistry.addPotion(fluidStrengthVeryLong, big, "StrengthVeryLong", Potion.damageBoost, 1440, 0);
		potionRegistry.addPotion(fluidFireResist, big, "FireResist", Potion.fireResistance, 360, 0);
		potionRegistry.addPotion(fluidFireResistLong, big, "FireResistLong", Potion.fireResistance, 720, 0);
		potionRegistry.addPotion(fluidFireResistVeryLong, big, "FireResistVeryLong", Potion.fireResistance, 1440, 0);
		potionRegistry.addPotion(fluidSlowness, big, "Slowness", Potion.moveSlowdown, 180, 0, true);
		potionRegistry.addPotion(fluidSlownessLong, big, "SlownessLong", Potion.moveSlowdown, 360, 0, true);
		potionRegistry.addPotion(fluidSlownessVeryLong, big, "SlownessVeryLong", Potion.moveSlowdown, 720, 0, true);
		potionRegistry.addPotion(fluidPoison, big, "Poison", Potion.poison, 90, 0);
		potionRegistry.addPotion(fluidPoisonII, big, "PoisonII", Potion.poison, 45, 1);
		potionRegistry.addPotion(fluidPoisonLong, big, "PoisonLong", Potion.poison, 180, 0);
		potionRegistry.addPotion(fluidPoisonIII, big, "PoisonIII", EffectPlugin.poison, 23, 2);
		potionRegistry.addPotion(fluidPoisonVeryLong, vials, "PoisonVeryLong", Potion.poison, 360, 0);
		potionRegistry.addPotion(fluidHarm, big, "Harm", Potion.harm, 1, 0);
		potionRegistry.addPotion(fluidHarmII, big, "HarmII", Potion.harm, 1, 1);
		potionRegistry.addPotion(fluidHarmIII, big, "HarmIII", Potion.harm, 1, 2);
		potionRegistry.addPotion(fluidHeal, big, "Heal", Potion.heal, 1, 0);
		potionRegistry.addPotion(fluidHealII, big, "HealII", Potion.heal, 1, 1);
		potionRegistry.addPotion(fluidHealIII, big, "HealIII", Potion.heal, 1, 2);
		potionRegistry.addPotion(fluidWaterBreathe, big, "WaterBreathe", Potion.waterBreathing, 360, 0);
		potionRegistry.addPotion(fluidWaterBreatheLong, big, "WaterBreatheLong", Potion.waterBreathing, 720, 0);
		potionRegistry.addPotion(fluidWaterBreatheVeryLong, big, "WaterBreatheVeryLong", Potion.waterBreathing, 1440, 0);
		potionRegistry.addPotion(fluidHaste, big, "Haste", Potion.digSpeed, 180, 0, true);
		potionRegistry.addPotion(fluidHasteII, big, "HasteII", Potion.digSpeed, 90, 1, true);
		potionRegistry.addPotion(fluidHasteIII, big, "HasteIII", Potion.digSpeed, 45, 2, true);
		potionRegistry.addPotion(fluidHasteLong, big, "HasteLong", Potion.digSpeed, 360, 0, true);
		potionRegistry.addPotion(fluidHasteVeryLong, big, "HasteVeryLong", Potion.digSpeed, 720, 0, true);
		potionRegistry.addPotion(fluidFatigue, big, "Fatigue", Potion.digSlowdown, 180, 0, true);
		potionRegistry.addPotion(fluidFatigueII, big, "FatigueII", Potion.digSlowdown, 90, 1, true);
		potionRegistry.addPotion(fluidFatigueIII, big, "FatigueIII", Potion.digSlowdown, 45, 2, true);
		potionRegistry.addPotion(fluidFatigueLong, big, "FatigueLong", Potion.digSlowdown, 360, 0, true);
		potionRegistry.addPotion(fluidFatigueVeryLong, big, "FatigueVeryLong", Potion.digSlowdown, 720, 0, true);
		potionRegistry.addPotion(fluidJump, big, "Jump", Potion.jump, 120, 0, true);
		potionRegistry.addPotion(fluidJumpII, big, "JumpII", Potion.jump, 60, 1, true);
		potionRegistry.addPotion(fluidJumpIII, big, "JumpIII", Potion.jump, 30, 2, true);
		potionRegistry.addPotion(fluidJumpLong, big, "JumpLong", Potion.jump, 240, 0, true);
		potionRegistry.addPotion(fluidJumpVeryLong, big, "JumpVeryLong", Potion.jump, 480, 0, true);
		potionRegistry.addPotion(fluidNausea, big, "Nausea", Potion.confusion, 60, 0);
		potionRegistry.addPotion(fluidNauseaLong, big, "NauseaLong", Potion.confusion, 120, 0);
		potionRegistry.addPotion(fluidNauseaVeryLong, big, "NauseaVeryLong", Potion.confusion, 240, 0);
		potionRegistry.addPotion(fluidResistance, big, "Resistance", Potion.resistance, 180, 0, true);
		potionRegistry.addPotion(fluidResistanceII, big, "ResistanceII", Potion.resistance, 90, 1, true);
		potionRegistry.addPotion(fluidResistanceIII, big, "ResistanceIII", Potion.resistance, 45, 2, true);
		potionRegistry.addPotion(fluidResistanceLong, big, "ResistanceLong", Potion.resistance, 360, 0, true);
		potionRegistry.addPotion(fluidResistanceVeryLong, big, "ResistanceVeryLong", Potion.resistance, 720, 0, true);
		potionRegistry.addPotion(fluidBlindness, big, "Blindness", Potion.blindness, 60, 0);
		potionRegistry.addPotion(fluidBlindnessLong, big, "BlindnessLong", Potion.blindness, 120, 0);
		potionRegistry.addPotion(fluidBlindnessVeryLong, big, "BlindnessVeryLong", Potion.blindness, 240, 0);
		potionRegistry.addPotion(fluidHunger, big, "Hunger", Potion.hunger, 240, 0);
		potionRegistry.addPotion(fluidHungerII, big, "HungerII", Potion.hunger, 120, 1);
		potionRegistry.addPotion(fluidHungerIII, big, "HungerIII", Potion.hunger, 60, 2);
		potionRegistry.addPotion(fluidHungerLong, big, "HungerLong", Potion.hunger, 480, 0);
		potionRegistry.addPotion(fluidHungerVeryLong, big, "HungerVeryLong", Potion.hunger, 960, 0);
		potionRegistry.addPotion(fluidHealthBoost, big, "HealthBoost", Potion.field_76434_w, 240, 0, true);
		potionRegistry.addPotion(fluidHealthBoostII, big, "HealthBoostII", Potion.field_76434_w, 120, 1, true);
		potionRegistry.addPotion(fluidHealthBoostIII, big, "HealthBoostIII", Potion.field_76434_w, 60, 2, true);
		potionRegistry.addPotion(fluidHealthBoostLong, big, "HealthBoostLong", Potion.field_76434_w, 480, 0, true);
		potionRegistry.addPotion(fluidHealthBoostVeryLong, big, "HealthBoostVeryLong", Potion.field_76434_w, 960, 0, true);
		potionRegistry.addPotion(fluidAbsorption, big, "Absorption", Potion.field_76444_x, 120, 0);
		potionRegistry.addPotion(fluidAbsorptionII, big, "AbsorptionII", Potion.field_76444_x, 60, 1);
		potionRegistry.addPotion(fluidAbsorptionIII, big, "AbsorptionIII", Potion.field_76444_x, 30, 2);
		potionRegistry.addPotion(fluidAbsorptionLong, big, "AbsorptionLong", Potion.field_76444_x, 240, 0);
		potionRegistry.addPotion(fluidAbsorptionVeryLong, big, "AbsorptionVeryLong", Potion.field_76444_x, 480, 0);
		potionRegistry.addPotion(fluidSaturation, big, "Saturation", Potion.field_76443_y, 1, 0);
		potionRegistry.addPotion(fluidSaturationII, big, "SaturationII", Potion.field_76443_y, 1, 1);
		potionRegistry.addPotion(fluidSaturationIII, big, "SaturationIII", Potion.field_76443_y, 1, 2);
		potionRegistry.addPotion(fluidWither, big, "Wither", Potion.wither, 40, 0);
		potionRegistry.addPotion(fluidWitherII, big, "WitherII", Potion.wither, 20, 1);
		potionRegistry.addPotion(fluidWitherIII, big, "WitherIII", EffectPlugin.wither, 10, 2);
		potionRegistry.addPotion(fluidWitherLong, big, "WitherLong", Potion.wither, 80, 0);
		potionRegistry.addPotion(fluidWitherVeryLong, big, "WitherVeryLong", Potion.wither, 160, 0);
		potionRegistry.addPotion(fluidHolyWater, big, "HolyWater", EffectPlugin.angel, 20, 0, true);
		potionRegistry.addPotion(fluidHolyWaterII, big, "HolyWaterII", EffectPlugin.angel, 10, 1, true);
		potionRegistry.addPotion(fluidHolyWaterLong, big, "HolyWaterLong", EffectPlugin.angel, 40, 0, true);
		potionRegistry.addPotion(fluidHolyWaterVeryLong, big, "HolyWaterVeryLong", EffectPlugin.angel, 80, 0, true);
		potionRegistry.addPotion(fluidHolyWaterIII, big, "HolyWaterIII", EffectPlugin.angel, 5, 2, true);
		potionRegistry.addPotion(fluidFlight, big, "Flight", EffectPlugin.flight, 30, 0);
		potionRegistry.addPotion(fluidFlightLong, big, "FlightLong", EffectPlugin.flight, 60, 0);
		potionRegistry.addPotion(fluidFlightVeryLong, big, "FlightVeryLong", EffectPlugin.flight, 120, 0);
		potionRegistry.addPotion(fluidAntidote, big, "Antidote", EffectPlugin.immunity, 120, 0, true);
		potionRegistry.addPotion(fluidAntidoteII, big, "AntidoteII", EffectPlugin.immunity, 60, 1, true);
		potionRegistry.addPotion(fluidAntidoteIII, big, "AntidoteIII", EffectPlugin.immunity, 30, 2, true);
		potionRegistry.addPotion(fluidAntidoteIIII, big, "AntidoteIIII", EffectPlugin.immunity, 15, 3, true);
		potionRegistry.addPotion(fluidAntidoteLong, big, "AntidoteLong", EffectPlugin.immunity, 240, 0, true);
		potionRegistry.addPotion(fluidAntidoteVeryLong, big, "AntidoteVeryLong", EffectPlugin.immunity, 480, 0, true);
		potionRegistry.addPotion(fluidBoom, big, "Boom", EffectPlugin.creeper, 16, 0, true);
		potionRegistry.addPotion(fluidBoomII, big, "BoomII", EffectPlugin.creeper, 8, 1, true);
		potionRegistry.addPotion(fluidBoomIII, big, "BoomIII", EffectPlugin.creeper, 4, 2, true);
		potionRegistry.addPotion(fluidBoomLong, big, "BoomLong", EffectPlugin.creeper, 32, 0, true);
		potionRegistry.addPotion(fluidBoomVeryLong, big, "BoomVeryLong", EffectPlugin.creeper, 64, 0, true);
		potionRegistry.addPotion(fluidCryo, big, "Cryo", EffectPlugin.frozen, 16, 0, true);
		potionRegistry.addPotion(fluidCryoLong, big, "CryoLong", EffectPlugin.frozen, 32, 0, true);
		potionRegistry.addPotion(fluidCryoVeryLong, big, "CryoVeryLong", EffectPlugin.frozen, 64, 0, true);
		potionRegistry.addPotion(fluidEternalFlame, big, "EternalFlame", EffectPlugin.flame, 60, 0);
		potionRegistry.addPotion(fluidEternalFlameLong, big, "EternalFlameLong", EffectPlugin.flame, 120, 0);
		potionRegistry.addPotion(fluidEternalFlameVeryLong, big, "EternalFlameVeryLong", EffectPlugin.flame, 240, 0);
		potionRegistry.addPotion(fluidFireImmunity, big, "FireImmunity", EffectPlugin.fireproof, 70, 0, true);
		potionRegistry.addPotion(fluidFireImmunityLong, big, "FireImmunityLong", EffectPlugin.fireproof, 140, 0, true);
		potionRegistry.addPotion(fluidFireImmunityVeryLong, big, "FireImmunityVeryLong", EffectPlugin.fireproof, 280, 0, true);
		potionRegistry.addPotion(fluidFireImmunityII, big, "FireImmunityII", EffectPlugin.fireproof, 35, 1, true);
		potionRegistry.addPotion(fluidFireImmunityIII, big, "FireImmunityIII", EffectPlugin.fireproof, 20, 2, true);
		potionRegistry.addPotion(fluidFireImmunityIIII, big, "FireImmunityIIII", EffectPlugin.fireproof, 10, 3, true);
		potionRegistry.addPotion(fluidFireEater, big, "FireEater", EffectPlugin.fireEater, 90, 0, true);
		potionRegistry.addPotion(fluidFireEaterII, big, "FireEaterII", EffectPlugin.fireEater, 45, 1, true);
		potionRegistry.addPotion(fluidFireEaterIII, big, "FireEaterIII", EffectPlugin.fireEater, 23, 2, true);
		potionRegistry.addPotion(fluidFireEaterLong, big, "FireEaterLong", EffectPlugin.fireEater, 180, 0, true);
		potionRegistry.addPotion(fluidFireEaterVeryLong, big, "FireEaterVeryLong", EffectPlugin.fireEater, 360, 0, true);
	}

	@Override
	public void postInit(ModUtils mod) {
		recipeRegistry.addRecipe(water, fluidAwkward, Items.nether_wart);
		recipeRegistry.addRecipe(fluidAwkward, fluidFireResist, Items.magma_cream);
		recipeRegistry.addRecipe(fluidAwkward, fluidFast, Items.sugar);
		recipeRegistry.addRecipe(fluidAwkward, fluidHeal, Items.speckled_melon);
		recipeRegistry.addRecipe(fluidAwkward, fluidPoison, Items.spider_eye);
		recipeRegistry.addRecipe(fluidAwkward, fluidRegen, Items.ghast_tear);
		recipeRegistry.addRecipe(fluidAwkward, fluidStrength, Items.blaze_powder);
		recipeRegistry.addRecipe(fluidAwkward, fluidWeakness, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAwkward, fluidResistance, ItemPlugin.steelScales.getStack());
		recipeRegistry.addRecipe(fluidResistance, fluidResistanceII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidResistanceII, fluidResistance, Items.redstone);
		recipeRegistry.addRecipe(fluidResistance, fluidResistanceLong, Items.redstone);
		recipeRegistry.addRecipe(fluidResistanceLong, fluidResistance, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidResistanceII, fluidResistanceIII, ItemPlugin.obsidianTear.getStack());
		recipeRegistry.addRecipe(fluidResistanceLong, fluidResistanceVeryLong, ItemPlugin.pureTear.getStack());
		recipeRegistry.addRecipe(fluidWeakness, fluidWeaknessLong, new ItemStack(Items.redstone));
		recipeRegistry.addRecipe(fluidWeaknessLong, fluidWeakness, new ItemStack(Items.glowstone_dust));
		recipeRegistry.addRecipe(fluidWeaknessLong, fluidWeaknessVeryLong, ItemPlugin.pureTear.getStack());
		recipeRegistry.addRecipe(fluidAwkward, fluidVision, Items.golden_carrot);
		recipeRegistry.addRecipe(fluidAwkward, fluidWaterBreathe, new ItemStack(Items.fish, 1, 3));
		recipeRegistry.addRecipe(fluidFireResist, fluidFireResistLong, Items.redstone);
		recipeRegistry.addRecipe(fluidFireResistLong, fluidFireResist, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidFireResistLong, fluidFireResistVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidHeal, fluidHealII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHealII, fluidHeal, Items.redstone);
		recipeRegistry.addRecipe(fluidHealII, fluidHealIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidRegen, fluidRegenLong, Items.redstone);
		recipeRegistry.addRecipe(fluidRegenLong, fluidRegen, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidRegenLong, fluidRegenVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidRegen, fluidRegenII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidRegenII, fluidRegen, Items.redstone);
		recipeRegistry.addRecipe(fluidRegenII, fluidRegenIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidStrength, fluidStrengthII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidStrengthII, fluidStrength, Items.redstone);
		recipeRegistry.addRecipe(fluidStrengthII, fluidStrengthIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidStrength, fluidStrengthLong, Items.redstone);
		recipeRegistry.addRecipe(fluidStrengthLong, fluidStrength, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidStrengthLong, fluidStrengthVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidFast, fluidFastLong, Items.redstone);
		recipeRegistry.addRecipe(fluidFastLong, fluidFast, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidFastLong, fluidFastVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidFast, fluidFastII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidFastII, fluidFast, Items.redstone);
		recipeRegistry.addRecipe(fluidFastII, fluidFastIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidVision, fluidVisionLong, Items.redstone);
		recipeRegistry.addRecipe(fluidVisionLong, fluidVision, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidVisionLong, fluidVisionVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidVision, fluidInvisible, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidVisionLong, fluidInvisibleLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidVisionVeryLong, fluidInvisibleVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidInvisible, fluidInvisibleLong, Items.redstone);
		recipeRegistry.addRecipe(fluidInvisibleLong, fluidInvisible, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidInvisibleLong, fluidInvisibleVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidWaterBreathe, fluidWaterBreatheLong, Items.redstone);
		recipeRegistry.addRecipe(fluidWaterBreatheLong, fluidWaterBreathe, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidWaterBreatheLong, fluidWaterBreatheVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidHeal, fluidHarm, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHealII, fluidHarmII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHealIII, fluidHarmIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidPoison, fluidHarm, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidPoisonII, fluidHarmII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidPoisonIII, fluidHarmIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHarm, fluidHarmII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHarmII, fluidHarm, Items.redstone);
		recipeRegistry.addRecipe(fluidPoison, fluidPoisonLong, Items.redstone);
		recipeRegistry.addRecipe(fluidPoisonLong, fluidPoison, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidPoison, fluidPoisonII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidPoisonII, fluidPoison, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidVisionLong, fluidInvisibleLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFireResist, fluidSlowness, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFireResistLong, fluidSlownessLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFast, fluidSlowness, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFastLong, fluidSlownessLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFastVeryLong, fluidSlownessVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidSlowness, fluidSlownessLong, Items.redstone);
		recipeRegistry.addRecipe(fluidSlownessLong, fluidSlowness, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidStrength, fluidWeakness, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidStrengthLong, fluidWeaknessLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidStrengthVeryLong, fluidWeaknessVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidRegen, fluidWeakness, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidRegenLong, fluidWeaknessLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidRegenVeryLong, fluidWeaknessVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHealII, fluidHeal, Items.redstone);
		recipeRegistry.addRecipe(fluidHarmII, fluidHeal, Items.redstone);
		recipeRegistry.addRecipe(fluidFireResistLong, fluidFireResist, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidSlownessLong, fluidSlowness, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidWeaknessLong, fluidWeakness, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidWaterBreatheLong, fluidWaterBreathe, Items.glowstone_dust);

		recipeRegistry.addRecipe(fluidAwkward, fluidFlight, ItemPlugin.goldenFeather);
		recipeRegistry.addRecipe(fluidFlight, fluidFlightLong, Items.redstone);
		recipeRegistry.addRecipe(fluidFlightLong, fluidFlight, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidFlightLong, fluidFlightVeryLong, ItemPlugin.pureTear.getStack());
		recipeRegistry.addRecipe(fluidRegen, fluidHolyWater, ItemPlugin.holyDust.getStack());
		recipeRegistry.addRecipe(fluidRegenII, fluidHolyWaterII, ItemPlugin.holyDust.getStack());
		recipeRegistry.addRecipe(fluidRegenIII, fluidHolyWaterIII, ItemPlugin.holyDust.getStack());
		recipeRegistry.addRecipe(fluidRegenLong, fluidHolyWaterLong, ItemPlugin.holyDust.getStack());
		recipeRegistry.addRecipe(fluidRegenVeryLong, fluidHolyWaterVeryLong, ItemPlugin.holyDust.getStack());
		recipeRegistry.addRecipe(fluidHolyWater, fluidHarm, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHolyWaterII, fluidHarmII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHolyWaterIII, fluidHarmIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHolyWater, fluidHolyWaterII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHolyWaterII, fluidHolyWater, Items.redstone);
		recipeRegistry.addRecipe(fluidHolyWater, fluidHolyWaterLong, Items.redstone);
		recipeRegistry.addRecipe(fluidHolyWaterLong, fluidHolyWater, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHolyWaterII, fluidHolyWaterIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidHolyWaterLong, fluidHolyWaterVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidAwkward, fluidFlight, ItemPlugin.goldenFeather);
		recipeRegistry.addRecipe(fluidAntidoteII, fluidAntidoteIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidAntidoteLong, fluidAntidoteVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidWither, fluidBoom, Items.gunpowder);
		recipeRegistry.addRecipe(fluidWitherII, fluidBoomII, Items.gunpowder);
		recipeRegistry.addRecipe(fluidWitherIII, fluidBoomIII, Items.gunpowder);
		recipeRegistry.addRecipe(fluidWitherLong, fluidBoomLong, Items.gunpowder);
		recipeRegistry.addRecipe(fluidWitherVeryLong, fluidBoomVeryLong, Items.gunpowder);
		recipeRegistry.addRecipe(fluidBoom, fluidBoomII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidBoomII, fluidBoom, Items.redstone);
		recipeRegistry.addRecipe(fluidBoom, fluidBoomLong, Items.redstone);
		recipeRegistry.addRecipe(fluidBoomLong, fluidBoom, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidBoomII, fluidBoomIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidBoomLong, fluidBoomVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidSlowness, fluidCryo, Items.snowball);
		recipeRegistry.addRecipe(fluidCryo, fluidCryoLong, new ItemStack(Items.redstone));
		recipeRegistry.addRecipe(fluidCryoLong, fluidCryo, new ItemStack(Items.glowstone_dust));
		recipeRegistry.addRecipe(fluidCryoLong, fluidCryoVeryLong, ItemPlugin.pureTear.getStack());
		recipeRegistry.addRecipe(fluidSlownessLong, fluidCryoLong, Items.snowball);
		recipeRegistry.addRecipe(fluidSlownessVeryLong, fluidCryoVeryLong, Items.snowball);
		recipeRegistry.addRecipe(fluidSlowness, fluidSlownessLong, Items.redstone);
		recipeRegistry.addRecipe(fluidSlownessLong, fluidSlowness, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidSlownessLong, fluidSlownessVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(lava, fluidEternalFlame, new ItemStack(Items.fire_charge));
		recipeRegistry.addRecipe(fluidEternalFlame, fluidEternalFlameLong, new ItemStack(Items.redstone));
		recipeRegistry.addRecipe(fluidEternalFlameLong, fluidEternalFlame, new ItemStack(Items.glowstone_dust));
		recipeRegistry.addRecipe(fluidEternalFlameLong, fluidEternalFlameVeryLong, ItemPlugin.pureTear.getStack());
		recipeRegistry.addRecipe(lava, fluidFireEater, ItemPlugin.heartBlaze.getStack());
		recipeRegistry.addRecipe(fluidFireEater, fluidFireEaterII, new ItemStack(Items.glowstone_dust));
		recipeRegistry.addRecipe(fluidFireEaterII, fluidFireEater, new ItemStack(Items.redstone));
		recipeRegistry.addRecipe(fluidFireEaterII, fluidFireEaterIII, ItemPlugin.obsidianTear.getStack());
		recipeRegistry.addRecipe(fluidFireEater, fluidFireEaterLong, new ItemStack(Items.redstone));
		recipeRegistry.addRecipe(fluidFireEaterLong, fluidFireEater, new ItemStack(Items.glowstone_dust));
		recipeRegistry.addRecipe(fluidFireEaterLong, fluidFireEaterVeryLong, ItemPlugin.pureTear.getStack());

		recipeRegistry.addRecipe(fluidAwkward, fluidNausea, Items.poisonous_potato);
		recipeRegistry.addRecipe(fluidNausea, fluidNauseaLong, Items.redstone);
		recipeRegistry.addRecipe(fluidNauseaLong, fluidNausea, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidNauseaLong, fluidNauseaVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidAwkward, fluidBlindness, new ItemStack(Items.dye, 1, 0));
		recipeRegistry.addRecipe(fluidBlindness, fluidBlindnessLong, Items.redstone);
		recipeRegistry.addRecipe(fluidBlindnessLong, fluidBlindnessVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidBlindness, fluidVision, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidBlindnessLong, fluidVisionLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidBlindnessVeryLong, fluidVisionVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAwkward, fluidHunger, Items.rotten_flesh);
		recipeRegistry.addRecipe(fluidHunger, fluidHungerII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHunger, fluidHungerLong, Items.rotten_flesh);
		recipeRegistry.addRecipe(fluidHungerII, fluidHungerIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidHungerLong, fluidHungerVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidHunger, fluidSaturation, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHungerII, fluidSaturationII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHungerIII, fluidSaturationIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAwkward, fluidSaturation, Items.cake);
		recipeRegistry.addRecipe(fluidHunger, fluidSaturation, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidSaturation, fluidHunger, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHungerII, fluidSaturationII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidSaturationII, fluidHungerII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHungerIII, fluidSaturationIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidSaturationIII, fluidHungerIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidSaturation, fluidSaturationII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidSaturationII, fluidSaturationIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidAwkward, fluidHaste, ItemPlugin.spiderFang);
		recipeRegistry.addRecipe(fluidFatigue, fluidHaste, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHaste, fluidFatigue, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFatigueII, fluidHasteII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHasteII, fluidFatigueII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFatigueIII, fluidHasteIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHasteIII, fluidFatigueIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFatigueLong, fluidHasteLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHasteLong, fluidFatigueLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidFatigueVeryLong, fluidHasteVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHasteVeryLong, fluidFatigueVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidHaste, fluidHasteII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHasteII, fluidHaste, Items.redstone);
		recipeRegistry.addRecipe(fluidHasteLong, fluidHaste, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHasteII, fluidHasteIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidHaste, fluidHasteLong, Items.redstone);
		recipeRegistry.addRecipe(fluidHasteLong, fluidHasteVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidFatigue, fluidHaste, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAwkward, fluidFatigue, ItemPlugin.tiredSpores);
		recipeRegistry.addRecipe(fluidFatigue, fluidFatigueII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidFatigueII, fluidFatigue, Items.redstone);
		recipeRegistry.addRecipe(fluidFatigueII, fluidFatigueIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidFatigue, fluidFatigueLong, Items.redstone);
		recipeRegistry.addRecipe(fluidFatigueLong, fluidFatigue, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidFatigueLong, fluidFatigueVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidAwkward, fluidHealthBoost, ItemPlugin.heartGold);
		recipeRegistry.addRecipe(fluidHealthBoost, fluidHealthBoostII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHealthBoostII, fluidHealthBoost, Items.redstone);
		recipeRegistry.addRecipe(fluidHealthBoostII, fluidHealthBoostIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidHealthBoost, fluidHealthBoostLong, Items.redstone);
		recipeRegistry.addRecipe(fluidHealthBoostLong, fluidHealthBoost, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidHealthBoostLong, fluidHealthBoostVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidAwkward, fluidAntidote, ItemPlugin.remedySalve);
		recipeRegistry.addRecipe(fluidAntidote, fluidAntidoteII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidAntidoteII, fluidAntidote, Items.redstone);
		recipeRegistry.addRecipe(fluidAntidoteII, fluidAntidoteIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidAntidote, fluidAntidoteLong, Items.redstone);
		recipeRegistry.addRecipe(fluidAntidoteLong, fluidAntidote, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidAntidoteLong, fluidAntidoteVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidAntidote, fluidWither, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidWither, fluidAntidote, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAntidoteII, fluidWitherII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidWitherII, fluidAntidoteII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAntidoteIII, fluidWitherIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidWitherIII, fluidAntidoteIII, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAntidoteLong, fluidWitherLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidWitherLong, fluidAntidoteLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAntidoteVeryLong, fluidWitherVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidWitherVeryLong, fluidAntidoteVeryLong, Items.fermented_spider_eye);
		recipeRegistry.addRecipe(fluidAwkward, fluidJump, Items.carrot);
		recipeRegistry.addRecipe(fluidJump, fluidJumpII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidJumpII, fluidJump, Items.redstone);
		recipeRegistry.addRecipe(fluidJumpII, fluidJumpIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidJump, fluidJumpLong, Items.redstone);
		recipeRegistry.addRecipe(fluidJumpLong, fluidJump, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidJumpLong, fluidJumpVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(lava, fluidWither, ItemPlugin.charredBone);
		recipeRegistry.addRecipe(fluidWither, fluidWitherII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidWitherII, fluidWitherIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidWither, fluidWitherLong, Items.redstone);
		recipeRegistry.addRecipe(fluidWitherLong, fluidWitherVeryLong, ItemPlugin.pureTear);
		recipeRegistry.addRecipe(fluidWitherII, fluidWither, Items.redstone);
		recipeRegistry.addRecipe(fluidWitherLong, fluidWither, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidAwkward, fluidAbsorption, ItemPlugin.heartSmall);
		recipeRegistry.addRecipe(fluidAbsorption, fluidAbsorptionII, Items.glowstone_dust);
		recipeRegistry.addRecipe(fluidAbsorptionII, fluidAbsorption, Items.redstone);
		recipeRegistry.addRecipe(fluidAbsorptionII, fluidAbsorptionIII, ItemPlugin.obsidianTear);
		recipeRegistry.addRecipe(fluidAbsorption, fluidAbsorptionLong, Items.redstone);
		recipeRegistry.addRecipe(fluidAbsorptionLong, fluidAbsorption, Items.glowstone_dust);
	}
	
	private VanillaPotion registerVanillaPotion(String name, int metaBottle, int metaSplash, Potion effect, int duration, int strength) {
		
		//FluidStack potion = new FluidStack(FluidUtil.createFluid(new FluidPotion("potion" + name, new SimpleItem(
		//		Items.potionitem, metaBottle), duration * 20, strength), potionTexture), 1000);
		
		SimpleItem potionItem = new SimpleItem(Items.potionitem, metaBottle);
		FluidPotion potionFluidWrapper = new FluidPotion("potion" + name, potionItem, duration * 20, strength);
		Fluid potionFluid = FluidUtil.createFluid(potionFluidWrapper, PotionPlugin.potionTexture);
		FluidStack potionStack = new FluidStack(potionFluid, 1000);
		VanillaPotion potion = new VanillaPotion(potionStack, 1000);
		
		potion.potionItem = potionItem;
		potion.potionFluidWrapper = potionFluidWrapper;
		potion.potionFluid = potionFluid;
		potion.potionStack = potionStack;
		potion.metaBottle = metaBottle;
		potion.metaSplash = metaSplash;

		//potionRegistry.addPotion(potion, name, effect, duration, strength);
		FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Items.potionitem, 1, metaBottle),
				emptyBottle);
		if (metaSplash != 0)
			FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Items.potionitem, 1, metaSplash),
					ItemPlugin.splashBottle.getStack());
		
		vanillaRegistry.registerPotion(potion);
		return potion;
	}
	
	//public FluidStack FluidStackFromItemPotion(ItemPotion potion) {
		
	//}

	public static RecipeRegistry getRecipeList() {
		return recipeRegistry;
	}

	public static PotionRegistry getPotionList() {
		return potionRegistry;
	}
}
