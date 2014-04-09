package redgear.brewcraft.plugins.common;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.common.Brewcraft;
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
	public static SimpleItem emptyBottle = new SimpleItem(Items.glass_bottle);
	public static PotionRegistry registry = new PotionRegistry();
	public static final String potionTexture = "potionWhite";

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
		createVanillaPotion("Awkward", 16, 0);
		createVanillaPotion("Vision", 8198, 16390);
		createVanillaPotion("VisionLong", 8262, 16454);
		createVanillaPotion("Invisible", 8206, 16398);
		createVanillaPotion("InvisibleLong", 8270, 16462);
		createVanillaPotion("Regen", 8193, 16385);
		createVanillaPotion("RegenII", 8289, 16481);
		createVanillaPotion("RegenLong", 8257, 16449);
		createVanillaPotion("Fast", 8194, 16386);
		createVanillaPotion("FastLong", 8290, 16450);
		createVanillaPotion("FastII", 8258, 16418);
		createVanillaPotion("Weakness", 8200, 16392);
		createVanillaPotion("WeaknessLong", 8264, 16456);
		createVanillaPotion("Strength", 8201, 16393);
		createVanillaPotion("StrengthLong", 8265, 16457);
		createVanillaPotion("StrengthII", 8297, 16425);
		createVanillaPotion("FireResist", 8195, 16387);
		createVanillaPotion("FireResistLong", 8259, 16451);
		createVanillaPotion("Slowness", 8202, 16394);
		createVanillaPotion("SlownessLong", 8266, 16458);
		createVanillaPotion("Poison", 8196, 16388);
		createVanillaPotion("PoisonII", 8260, 16420);
		createVanillaPotion("PoisonLong", 8228, 16452);
		createVanillaPotion("Harm", 8204, 16396);
		createVanillaPotion("HarmII", 8236, 16428);
		createVanillaPotion("Healing", 8196, 16389);
		createVanillaPotion("HealingII", 8229, 16421);
		createVanillaPotion("WaterBreath", 8205, 16397);
		createVanillaPotion("WaterBreathLong", 8269, 16461);
		
		if (Brewcraft.inst.getBoolean("Global", "Toggle Unused Vanilla Potions", "Toggle Mojang's unused potions.",
				true)) {
			registry.addPotion("Haste", Potion.digSpeed, 90, 0, true);
			registry.addPotion("HasteII", Potion.digSpeed, 45, 1, true);
			registry.addPotion("HasteIII", Potion.digSpeed, 25, 2, true);
			registry.addPotion("HasteLong", Potion.digSpeed, 180, 0, true);
			registry.addPotion("HasteVeryLong", Potion.digSpeed, 360, 0, true);
			registry.addPotion("Fatigue", Potion.digSlowdown, 90, 0, true);
			registry.addPotion("FatigueII", Potion.digSlowdown, 45, 1, true);
			registry.addPotion("FatigueIII", Potion.digSlowdown, 25, 2, true);
			registry.addPotion("FatigueLong", Potion.digSlowdown, 180, 0, true);
			registry.addPotion("FatigueVeryLong", Potion.digSlowdown, 360, 0, true);
			registry.addPotion("Jump", Potion.jump, 60, 0, true);
			registry.addPotion("JumpII", Potion.jump, 30, 1, true);
			registry.addPotion("JumpIII", Potion.jump, 15, 2, true);
			registry.addPotion("JumpLong", Potion.jump, 120, 0, true);
			registry.addPotion("JumpVeryLong", Potion.jump, 240, 0, true);
			registry.addPotion("Nausea", Potion.confusion, 30, 0);
			registry.addPotion("NauseaLong", Potion.confusion, 60, 0);
			registry.addPotion("NauseaVeryLong", Potion.confusion, 120, 0);
			registry.addPotion("Resistance", Potion.resistance, 90, 0, true);
			registry.addPotion("ResistanceII", Potion.resistance, 45, 1, true);
			registry.addPotion("ResistanceIII", Potion.resistance, 20, 2, true);
			registry.addPotion("ResistanceLong", Potion.resistance, 180, 0, true);
			registry.addPotion("ResistanceVeryLong", Potion.resistance, 360, 0, true);
			registry.addPotion("Blindness", Potion.blindness, 30, 0, true);
			registry.addPotion("BlindnessLong", Potion.blindness, 60, 0, true);
			registry.addPotion("BlindnessVeryLong", Potion.blindness, 120, 0, true);
			registry.addPotion("Hunger", Potion.hunger, 60, 0);
			registry.addPotion("HungerII", Potion.hunger, 30, 1);
			registry.addPotion("HungerIII", Potion.hunger, 15, 2);
			registry.addPotion("HungerLong", Potion.hunger, 120, 0);
			registry.addPotion("HungerVeryLong", Potion.hunger, 240, 0);
			registry.addPotion("HealthBoost", Potion.field_76434_w, 60, 0, true);
			registry.addPotion("HealthBoostII", Potion.field_76434_w, 30, 1, true);
			registry.addPotion("HealthBoostIII", Potion.field_76434_w, 15, 2, true);
			registry.addPotion("HealthBoostLong", Potion.field_76434_w, 120, 0, true);
			registry.addPotion("HealthBoostVeryLong", Potion.field_76434_w, 240, 0, true);
			registry.addPotion("Absorption", Potion.field_76444_x, 60, 0);
			registry.addPotion("AbsorptionII", Potion.field_76444_x, 30, 1);
			registry.addPotion("AbsorptionIII", Potion.field_76444_x, 15, 2);
			registry.addPotion("AbsorptionLong", Potion.field_76444_x, 120, 0);
			registry.addPotion("AbsorptionVeryLong", Potion.field_76444_x, 240, 0);
			registry.addPotion("Saturation", Potion.field_76443_y, 10, 0);
			registry.addPotion("SaturationII", Potion.field_76443_y, 5, 1);
			registry.addPotion("SaturationIII", Potion.field_76443_y, 3, 2);
			registry.addPotion("SaturationLong", Potion.field_76443_y, 20, 0);
			registry.addPotion("SaturationVeryLong", Potion.field_76443_y, 40, 0);
		}

		registry.addPotion("HolyWater", EffectPlugin.angel, 10, 0, true);
		registry.addPotion("HolyWaterII", EffectPlugin.angel, 5, 1, true);
		registry.addPotion("HolyWaterLong", EffectPlugin.angel, 10, 0, true);
		registry.addPotion("HolyWaterVeryLong", EffectPlugin.angel, 20, 0, true);
		registry.addPotion("HolyWaterIII", EffectPlugin.angel, 3, 2, true);
		registry.addPotion("Flying", EffectPlugin.flight, 15, 0);
		registry.addPotion("FlyingLong", EffectPlugin.flight, 30, 0);
		registry.addPotion("FlyingVeryLong", EffectPlugin.flight, 60, 0);
		registry.addPotion("Wither", Potion.wither, 20, 0);
		registry.addPotion("WitherII", Potion.wither, 10, 1);
		registry.addPotion("WitherIII", Potion.wither, 5, 2);
		registry.addPotion("WitherLong", Potion.wither, 40, 0);
		registry.addPotion("WitherVeryLong", Potion.wither, 80, 0);
		registry.addPotion("Antidote", EffectPlugin.immunity, 60, 0, true);
		registry.addPotion("AntidoteII", EffectPlugin.immunity, 45, 1, true);
		registry.addPotion("AntidoteIII", EffectPlugin.immunity, 30, 2, true);
		registry.addPotion("AntidoteLong", EffectPlugin.immunity, 120, 0, true);
		registry.addPotion("AntidoteVeryLong", EffectPlugin.immunity, 240, 0, true);
		registry.addPotion("Boom", EffectPlugin.creeper, 8, 0, true);
		registry.addPotion("BoomII", EffectPlugin.creeper, 4, 1, true);
		registry.addPotion("BoomIII", EffectPlugin.creeper, 4, 2, true);
		registry.addPotion("BoomLong", EffectPlugin.creeper, 16, 0, true);
		registry.addPotion("BoomVeryLong", EffectPlugin.creeper, 32, 0, true);
		registry.addPotion("Freezing", EffectPlugin.frozen, 8, 0, true);
		registry.addPotion("FreezingLong", EffectPlugin.frozen, 16, 0, true);
		registry.addPotion("FreezingVeryLong", EffectPlugin.frozen, 30, 0, true);

		registry.addPotion("RegenIII", Potion.regeneration, 8, 2);
		registry.addPotion("RegenVeryLong", Potion.regeneration, 180, 0);
		registry.addPotion("FastIII", Potion.moveSpeed, 8, 2, true);
		registry.addPotion("FastVeryLong", Potion.moveSpeed, 960, 0);
		registry.addPotion("StrengthIII", Potion.damageBoost, 40, 2, true);
		registry.addPotion("StrengthVeryLong", Potion.damageBoost, 960, 0);
		registry.addPotion("FireResistII", Potion.fireResistance, 67, 1);
		registry.addPotion("FireResistIII", EffectPlugin.fireproof, 35, 0, true);
		registry.addPotion("FireResistIIII", EffectPlugin.fireproof, 15, 1, true);
		registry.addPotion("FireResistVeryLong", Potion.fireResistance, 960, 0, true);
		registry.addPotion("PoisonIII", Potion.poison, 8, 2);
		registry.addPotion("PoisonVeryLong", Potion.poison, 240, 0);
		registry.addPotion("HarmIII", Potion.harm, 1, 2);
		registry.addPotion("HealingIII", Potion.heal, 1, 2);
		registry.addPotion("VisionVeryLong", Potion.nightVision, 960, 0);
		registry.addPotion("WeaknessVeryLong", Potion.weakness, 480, 0, true);
		registry.addPotion("SlownessVeryLong", Potion.moveSlowdown, 480, 0, true);
		registry.addPotion("WaterBreathVeryLong", Potion.waterBreathing, 960, 0);
	}

	@Override
	public void postInit(ModUtils mod) {

	}

	private FluidStack createVanillaPotion(String name, int metaBottle, int metaSplash) {
		FluidStack potion = new FluidStack(FluidUtil.createFluid(new FluidPotion("potion" + name, new SimpleItem(
				Items.potionitem, metaBottle)), potionTexture), 1000);
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
