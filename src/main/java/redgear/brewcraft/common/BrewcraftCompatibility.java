package redgear.brewcraft.common;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.compat.Mods;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import buildcraft.api.fuels.IronEngineFuel;
import forestry.api.storage.BackpackManager;

public class BrewcraftCompatibility {
	
	public static ItemStack brain;
	public static ItemStack goo;
	public static ItemStack tendril;
	
	public static void run() {
		
		brain = ItemApi.getItem("itemResource", 5);
		goo = ItemApi.getItem("itemResource", 11);
		tendril = ItemApi.getItem("itemResource", 12);
		
		if(Mods.Thaum.isIn() && Brewcraft.inst.getBoolean("Mod Compatibility",
				"Thaumcraft 4 Compatibility", "Toggle Thaumcraft 4 Compatibility", true)) {
			
			if (Brewcraft.inst.getBoolean("Compatibility", "Thaumcraft 4 Aspects on Items and Blocks",
					"Toggle Aspects from Thaumcraft 4",true)) {
				
				ThaumcraftApi.registerObjectTag(Brewcraft.brewery.id, 0,
						new AspectList().add(Aspect.MECHANISM, 11).add(Aspect.METAL, 7));
				ThaumcraftApi.registerObjectTag(Brewcraft.ingredients.itemID, 0,
						new AspectList().add(Aspect.LIFE, 3).add(Aspect.LIGHT, 2));
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
				
				if(!(goo == null))
					Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidAwkward, 100), new FluidStack(Brewcraft.fluidPoison, 100), goo,
							Brewcraft.ITEM_CONSUMPTION_BASE + 10, 4);
				
				if(!(tendril == null))
					Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidAwkward, 100), new FluidStack(Brewcraft.fluidPoison, 100), tendril,
							Brewcraft.ITEM_CONSUMPTION_BASE + 10, 4);
	
				if (!(brain == null)) {
					Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidVision, 100), new FluidStack(Brewcraft.fluidInvisible, 100),
							brain, Brewcraft.ITEM_CONSUMPTION_BASE, 4);
					Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidAwkward, 100), new FluidStack(Brewcraft.fluidWeakness, 100),
							brain, Brewcraft.ITEM_CONSUMPTION_BASE, 4);
					Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidStrength, 100), new FluidStack(Brewcraft.fluidWeakness, 100),
							brain, Brewcraft.ITEM_CONSUMPTION_BASE, 4);
					Brewcraft.registry.addRecipe(new FluidStack(Brewcraft.fluidFireResist, 100), new FluidStack(Brewcraft.fluidSlowness, 100),
							brain, Brewcraft.ITEM_CONSUMPTION_BASE, 4);
				}
			
		}
		
		if(Mods.Forestry.isIn() && Brewcraft.inst.getBoolean("Mod Compatibility", 
				"Forestry Compatibility", "Toggle Forestry Compatibility", true)) {
			
			BackpackManager.backpackItems[3].add(Brewcraft.goldenfeather.getStack());
			BackpackManager.backpackItems[3].add(Brewcraft.charredbone.getStack());
			BackpackManager.backpackItems[4].add(Brewcraft.holydust.getStack());
			BackpackManager.backpackItems[4].add(Brewcraft.brewery.getStack());
			BackpackManager.backpackItems[4].add(new ItemStack(Brewcraft.potions));
			
		}
		
		if(Mods.BuildcraftCore.isIn() && Brewcraft.inst.getBoolean("Mod Compatibility", 
				"Buildcraft Compatibility", "Toggle Buildcraft Compatibility", true)) {
			
			int power = 12;
			int time = 16000;
			
			IronEngineFuel.addFuel(Brewcraft.fluidBoom, power, time);
			IronEngineFuel.addFuel(Brewcraft.fluidBoomII, power * 2, time * 2 / 3);
			IronEngineFuel.addFuel(Brewcraft.fluidBoomLong, power * 2 / 3, time * 3);
			
		}
		
		
		
	}

}
