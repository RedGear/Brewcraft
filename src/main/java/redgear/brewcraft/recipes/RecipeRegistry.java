package redgear.brewcraft.recipes;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.api.BrewingAPI;
import redgear.brewcraft.api.BrewingAPI.IRecipeRegistry;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.util.SimpleItem;

public class RecipeRegistry implements IRecipeRegistry {

	public Set<BreweryRecipe> recipes = new HashSet<BreweryRecipe>();
	public static final int defaultAmount = 3000;

	public RecipeRegistry() {
		BrewingAPI.RECIPE_REGISTRY = this;
	}

	public FluidStack resizeStack(FluidStack input, int amount) {
		input.amount = amount;
		return input;
	}

	public void addRecipe(FluidStack input, FluidStack output, Item item) {
		addRecipe(input, output, item == null ? null : new SimpleItem(item));
	}

	@Override
	public void addRecipe(FluidStack input, FluidStack output, ItemStack item) {
		addRecipe(input, output, item, defaultAmount);
	}

	@Override
	public void addRecipe(FluidStack input, FluidStack output, ItemStack item, int amount) {
		addRecipe(input, output, item == null ? null : new SimpleItem(item), amount);
	}

	@Override
	public void addRecipe(Fluid input, Fluid output, ItemStack item) {
		addRecipe(new FluidStack(input, defaultAmount), new FluidStack(output, defaultAmount), item);

	}

	@Override
	public void addRecipe(Fluid input, Fluid output, ItemStack item, int amount) {
		addRecipe(new FluidStack(input, amount), new FluidStack(output, amount), item, amount);
	}

	public void addRecipe(FluidStack input, FluidStack output, SimpleItem item) {
		addRecipe(input, output, item, defaultAmount);
	}

	public void addRecipe(FluidStack input, FluidStack output, SimpleItem item, int amount) {
		if (input != null && output != null && item != null) {
			if (Brewcraft.inst.getBoolean("Brewery Recipes", "Toggle " + output.getFluid().getLocalizedName()
					+ " Recipe", "Allow " + output.getFluid().getLocalizedName() + " Recipe?")) {
				if (!recipes.add(new BreweryRecipe(resizeStack(input.copy(), amount), output, item)))
					Brewcraft.inst.myLogger
							.warn("There were issues trying to add recipe to Brewcraft Brewery block. Issues found are: Recipe already exists. ");
			}
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

	public boolean isValidFluid(FluidStack fluid) {
		if (fluid == null)
			return false;
		else
			for (BreweryRecipe recipe : recipes)
				if (recipe.input.isFluidEqual(fluid))
					return true;

		return false;
	}

	public BreweryRecipe getBreweryRecipe(FluidStack fluid, SimpleItem item) {
		if (fluid == null || item == null)
			return null;

		for (BreweryRecipe recipe : recipes)
			if (recipe.input.isFluidEqual(fluid) && recipe.item.equals(item))
				return recipe;
		return null;

	}
}
