package redgear.brewcraft.plugins.common;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.utils.PotionRegistry;
import redgear.core.fluids.FluidUtil;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.common.LoaderState.ModState;

public class PotionPlugin implements IPlugin{

	public static MetaItemPotion potions;
	public static SimpleItem emptyBottle = new SimpleItem(Items.glass_bottle);
	public static PotionRegistry registry = new PotionRegistry();
	public static final String potionTexture = "potionWhite";

	@Override
	public String getName() {
		return "Brewcraft|PotionPlugin";
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
		potions = new MetaItemPotion("RedGear.Brewcraft.Potions");
		
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
		createVanillaPotion("Weakness", 8200, 16456);
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

		registry.addPotion("HolyWater", EffectPlugin.angel, 100, 0, true);
		registry.addPotion("HolyWaterII", EffectPlugin.angel, 50, 1, true);
		registry.addPotion("HolyWaterLong", EffectPlugin.angel, 200, 0, true);
		registry.addPotion("HolyWaterIII", EffectPlugin.angel, 100, 2, true);
		registry.addPotion("Flying", EffectPlugin.flight, 300, 0);
		registry.addPotion("FlyingLong", EffectPlugin.flight, 600, 0);
		registry.addPotion("Wither", Potion.wither, 400, 0);
		registry.addPotion("WitherII", Potion.wither, 200, 1);
		registry.addPotion("WitherIII", Potion.wither, 200, 2);
		registry.addPotion("WitherLong", Potion.wither, 800, 0);
		registry.addPotion("Antidote", EffectPlugin.immunity, 600, 0, true);
		registry.addPotion("AntidoteII", EffectPlugin.immunity, 300, 1, true);
		registry.addPotion("AntidoteIII", EffectPlugin.immunity, 300, 2, true);
		registry.addPotion("AntidoteLong", EffectPlugin.immunity, 1200, 0, true);
		registry.addPotion("Boom", EffectPlugin.creeper, 160, 0, true);
		registry.addPotion("BoomII", EffectPlugin.creeper, 80, 1, true);
		registry.addPotion("BoomIII", EffectPlugin.creeper, 80, 2, true);
		registry.addPotion("BoomLong", EffectPlugin.creeper, 320, 0, true);
		registry.addPotion("Freezing", EffectPlugin.frozen, 300, 0, true);
		registry.addPotion("FreezingLong", EffectPlugin.frozen, 600, 0, true);
		
		registry.addPotion("RegenIII", Potion.regeneration, 20 * 8, 2);
		registry.addPotion("FastIII", Potion.moveSpeed, 20 * 40, 2, true);
		registry.addPotion("StrengthIII", Potion.damageBoost, 20 * 40, 2, true);
		registry.addPotion("FireResistII", Potion.fireResistance, 20 * 67, 1);
		registry.addPotion("FireResistIII", EffectPlugin.fireproof, 20 * 35, 0, true);
		registry.addPotion("FireResistIIII", EffectPlugin.fireproof, 20 * 15, 1, true);
		registry.addPotion("PoisonIII", Potion.poison, 20 * 8, 2);
		registry.addPotion("HarmIII", Potion.harm, 20, 2);
		registry.addPotion("HealingIII", Potion.heal, 20, 2);
	}
	@Override
	public void postInit(ModUtils mod) {
		
	}
	
	private void createVanillaPotion(String name, int metaBottle, int metaSplash) {
		Fluid potion = FluidUtil.createFluid(
				new FluidPotion("potion" + name, Items.potionitem.getColorFromDamage(metaBottle), new SimpleItem(Items.potionitem, metaBottle), new SimpleItem(Items.potionitem, metaSplash)), potionTexture);
		FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Items.potionitem, 1, metaBottle),
				emptyBottle.getStack());
		if (metaSplash != 0)
			FluidContainerRegistry.registerFluidContainer(potion, new ItemStack(Items.potionitem, 1, metaSplash),
					IngredientPlugin.splashBottle.getStack());
	}
}
