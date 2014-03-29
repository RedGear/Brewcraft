package redgear.brewcraft.recipes;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.api.BrewingAPI;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.util.SimpleItem;

public class RecipeRegistry implements redgear.brewcraft.api.BrewingAPI.IRecipeRegistry {

	public final static int DEFAULT_TIME = 4;
	public final static int FLUID_CONSUMPTION_BASE = 100;
	public final static int ITEM_CONSUMPTION_BASE = 12;

	public Set<BreweryRecipe> recipes = new HashSet<BreweryRecipe>();

	public RecipeRegistry() {
		BrewingAPI.RECIPE_REGISTRY = this;
	}

	public void addRecipe(Fluid input, Fluid output, Item item) {
		addRecipe(input, output, item, ITEM_CONSUMPTION_BASE);
	}

	public void addRecipe(Fluid input, Fluid output, SimpleItem item) {
		addRecipe(input, output, item, ITEM_CONSUMPTION_BASE);
	}

	public void addRecipe(Fluid input, Fluid output, Item item, int itemAmount) {
		addRecipe(input, output, item, itemAmount, DEFAULT_TIME);
	}

	public void addRecipe(Fluid input, Fluid output, SimpleItem item, int itemAmount) {
		addRecipe(input, output, item, itemAmount, DEFAULT_TIME);
	}

	public void addRecipe(Fluid input, Fluid output, Item item, int itemAmount, int time) {
		addRecipe(input, output, item == null ? null : new SimpleItem(item), itemAmount, time);
	}

	public void addRecipe(Fluid input, Fluid output, SimpleItem item, int itemAmount, int time) {
		addRecipe(input == null ? null : new FluidStack(input, FLUID_CONSUMPTION_BASE), output == null ? null
				: new FluidStack(output, FLUID_CONSUMPTION_BASE), item, itemAmount, time);
	}

	@Override
	public void addRecipe(FluidStack input, FluidStack output, ItemStack item, int itemAmount, int time) {
		addRecipe(input, output, item == null ? null : new SimpleItem(item), itemAmount, time);
	}

	public void addRecipe(FluidStack input, FluidStack output, SimpleItem item, int itemAmount, int time) {
		if (input != null && input.amount > 0 && output != null && output.amount > 0 && item != null && itemAmount > 0
				&& time > 0) {
			if (!recipes.add(new BreweryRecipe(input, output, item, itemAmount, time)))
				Brewcraft.inst.myLogger
						.warn("There were issues trying to add recipe to Brewcraft Brewery block. Issues found are: Recipe already exists. ");
		} else { //There is a programming bug that someone needs to fix, so let's throw it as a warning. 
			StringBuilder message = new StringBuilder(
					"There were issues trying to add recipe to Brewcraft Brewery block. Issues found are: ");

			if (input == null)
				message.append("Input Fluid is null. ");
			else if (input.amount <= 0)
				message.append("Input fluid amount is less than 1. ");
			if (output == null)
				message.append("Output Fluid is null. ");
			else if (output.amount <= 0)
				message.append("Output fluid amount is less than 1. ");

			if (item == null)
				message.append("Input item is null. ");

			if (itemAmount <= 0)
				message.append("Item amount is less than 1. ");

			if (time <= 0)
				message.append("Time is less than 1. ");

			Brewcraft.inst.myLogger.warn(message.toString());
		}
	}

	public BreweryRecipe getBreweryRecipe(FluidStack fluid, SimpleItem item) {
		return fluid == null || item == null ? null : getBreweryRecipe(fluid.fluidID, item);
	}

	public BreweryRecipe getBreweryRecipe(int fluidId, SimpleItem item) {
		for (BreweryRecipe recipe : recipes)
			if (recipe.input.fluidID == fluidId && recipe.item.equals(item))
				return recipe;
		return null;
	}
}
