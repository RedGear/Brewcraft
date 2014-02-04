package redgear.brewcraft.common;

import java.util.logging.Level;

import redgear.core.compat.ModConfigHelper;
import redgear.core.compat.Mods;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BrewcraftCompatibility {
	
	public static ItemStack soul;
	public static ItemStack dust;
	public static ItemStack crystal;
	public static ItemStack bone;
	public static ItemStack sulfur;
	public static ItemStack brain;
	public static ItemStack goo;
	public static ItemStack tendril;
	
	public static void init() {
		
		soul = ModConfigHelper.get("miscItems", 10);
		dust = ModConfigHelper.get("miscItems", 11);
		crystal = ModConfigHelper.get("materials", 7);
		bone = ModConfigHelper.get("materials", 8);
		sulfur = ModConfigHelper.get("plantItem", 4);
		brain = ModConfigHelper.get("ItemResource", 5);
		goo = ModConfigHelper.get("ItemResource", 11);
		tendril = ModConfigHelper.get("ItemResource", 12);

		if (Mods.BiomesOPlenty.isIn()
				&& Brewcraft.inst.getBoolean("Mod Compatibility", "Biomes o' Plenty Compatibility",
						"Toggle Biomes o' Plenty Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + Brewcraft.inst.modName + "] has found Biomes o' Plenty loaded, now running compatibility.");

			if (!(soul == null))
				Brewcraft.registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), new FluidStack(Brewcraft.fluidWither, 100), soul, 1,
						4);
			if (!(dust == null))
				Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidRegen, 100), new FluidStack(Brewcraft.fluidHolyWater, 100), dust, 1, 4);
		}

		if (Mods.Thaum.isIn()
				&& Brewcraft.inst.getBoolean("Mod Compatibility", "Thaumcraft 4 Compatibility", "Toggle Thaumcraft 4 Compatibility",
						true)) {

			FMLLog.log(Level.INFO, "[" + Brewcraft.inst.modName + "] has found Thaumcraft 4 loaded, now running compatibility.");

			if (!(brain == null)) {
				Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidVision, 100), new FluidStack(Brewcraft.fluidInvisible, 100),
						ItemApi.getItem("ItemResource", 5), Brewcraft.ITEM_CONSUMPTION_BASE, 4);
				Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidAwkward, 100), new FluidStack(Brewcraft.fluidWeakness, 100),
						ItemApi.getItem("ItemResource", 5), Brewcraft.ITEM_CONSUMPTION_BASE, 4);
				Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidStrength, 100), new FluidStack(Brewcraft.fluidWeakness, 100),
						ItemApi.getItem("ItemResource", 5), Brewcraft.ITEM_CONSUMPTION_BASE, 4);
				Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidFireResist, 100), new FluidStack(Brewcraft.fluidSlowness, 100),
						ItemApi.getItem("ItemResource", 5), Brewcraft.ITEM_CONSUMPTION_BASE, 4);
			}

			if (!(goo == null))
				Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidAwkward, 100), new FluidStack(Brewcraft.fluidPoison, 100), goo,
						Brewcraft.ITEM_CONSUMPTION_BASE + 10, 4);
			if (!(tendril == null))
				Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidAwkward, 100), new FluidStack(Brewcraft.fluidPoison, 100), tendril,
						Brewcraft.ITEM_CONSUMPTION_BASE + 10, 4);

			if (Brewcraft.inst.getBoolean("Compatibility", "Thaumcraft 4 Aspects on Items and Blocks",
					"Toggle Aspects from Thaumcraft 4", true)) {
				ThaumcraftApi.registerObjectTag(Brewcraft.brewery.id, 0,
						new AspectList().add(Aspect.MECHANISM, 11).add(Aspect.METAL, 7));
				ThaumcraftApi.registerObjectTag(Brewcraft.ingredients.itemID, 0,
						new AspectList().add(Aspect.LIFE, 3).add(Aspect.LIGHT, 2).add(Aspect.MAGIC, 2));
				ThaumcraftApi.registerObjectTag(Brewcraft.ingredients.itemID, 1,
						new AspectList().add(Aspect.GREED, 3).add(Aspect.FLIGHT, 1));
				ThaumcraftApi.registerObjectTag(Brewcraft.ingredients.itemID, 2,
						new AspectList().add(Aspect.DEATH, 2).add(Aspect.BEAST, 1));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 0,
						new AspectList().add(Aspect.MAGIC, 3).add(Aspect.FIRE, 2));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 1,
						new AspectList().add(Aspect.LIGHT, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 2,
						new AspectList().add(Aspect.LIGHT, 13).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 3,
						new AspectList().add(Aspect.LIGHT, 8).add(Aspect.MAGIC, 5));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 4,
						new AspectList().add(Aspect.FLIGHT, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 5,
						new AspectList().add(Aspect.FLIGHT, 8).add(Aspect.MAGIC, 5));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 6,
						new AspectList().add(Aspect.DEATH, 8).add(Aspect.DARKNESS, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 7,
						new AspectList().add(Aspect.DEATH, 13).add(Aspect.DARKNESS, 13).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 8,
						new AspectList().add(Aspect.DEATH, 8).add(Aspect.DARKNESS, 8).add(Aspect.MAGIC, 7));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 9,
						new AspectList().add(Aspect.BEAST, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 10,
						new AspectList().add(Aspect.LIFE, 8).add(Aspect.HEAL, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 11,
						new AspectList().add(Aspect.LIFE, 13).add(Aspect.HEAL, 13).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 12,
						new AspectList().add(Aspect.LIFE, 8).add(Aspect.HEAL, 8).add(Aspect.MAGIC, 5));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 13,
						new AspectList().add(Aspect.WEAPON, 8).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 14,
						new AspectList().add(Aspect.WEAPON, 13).add(Aspect.MAGIC, 3));
				ThaumcraftApi.registerObjectTag(Brewcraft.potions.itemID, 15,
						new AspectList().add(Aspect.WEAPON, 8).add(Aspect.MAGIC, 5));
			}
		}

		if (Mods.TConstruct.isIn()
				&& Brewcraft.inst.getBoolean("Mod Compatibility", "Tinkers' Construct Compatibility",
						"Toggle Tinkers' Construct Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + Brewcraft.inst.modName + "] has found Tinkers' Construct loaded, now running compatibility.");

			if (!(crystal == null))
				Brewcraft.registry.addRecipe(new FluidStack(FluidRegistry.WATER, 100), new FluidStack(FluidRegistry.LAVA, 100),
						crystal, Brewcraft.ITEM_CONSUMPTION_BASE + 10, Brewcraft.DEFAULT_TIME);

			if (!(bone == null))
				Brewcraft.registry.addRecipe(new FluidStack(FluidRegistry.LAVA, 100), new FluidStack(Brewcraft.fluidWither, 100), bone,
						Brewcraft.ITEM_CONSUMPTION_BASE + 30, Brewcraft.DEFAULT_TIME);
		}

		if (Mods.Natura.isIn()
				&& Brewcraft.inst.getBoolean("Mod Compatibility", "Natura Compatibility", "Toggle Natura Compatibility", true)) {

			FMLLog.log(Level.INFO, "[" + Brewcraft.inst.modName + "] has found Natura loaded, now running compatibility.");

			if (!(sulfur == null))
				Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidWither, 100), new FluidStack(Brewcraft.fluidBoom, 100), sulfur,
						Brewcraft.ITEM_CONSUMPTION_BASE + 30, Brewcraft.DEFAULT_TIME + 5);
		}

		
	}

}
