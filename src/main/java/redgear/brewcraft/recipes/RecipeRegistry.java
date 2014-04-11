package redgear.brewcraft.recipes;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.api.BrewingAPI;
import redgear.brewcraft.api.BrewingAPI.IRecipeRegistry;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.util.SimpleItem;

public class RecipeRegistry implements IRecipeRegistry {

	public Set<BreweryRecipe> recipes = new HashSet<BreweryRecipe>();

	public RecipeRegistry() {
		BrewingAPI.RECIPE_REGISTRY = this;
	}

	public FluidStack resizeStack(FluidStack input, int amount) {
		input.amount = amount;
		return input;
	}

	@Override
	public void addRecipe(FluidStack input, FluidStack output, ItemStack item) {
		addRecipe(input, output, item == null ? null : new SimpleItem(item));
	}

	public void addRecipe(FluidStack input, FluidStack output, SimpleItem item) {
		if (input != null && input.amount > 0 && output != null && output.amount > 0 && item != null) {
			if (!recipes.add(new BreweryRecipe(input, output, item)))
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

			Brewcraft.inst.myLogger.warn(message.toString());
		}
	}

	public BreweryRecipe getBreweryRecipe(FluidStack fluid, SimpleItem item) {
		if (fluid == null || item == null)
			return null;

		for (BreweryRecipe recipe : recipes)
			if (recipe.input.equals(fluid) && recipe.item.equals(item))
				return recipe;
		return null;

	}
}
