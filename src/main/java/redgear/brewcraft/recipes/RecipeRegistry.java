package redgear.brewcraft.recipes;

import java.util.HashSet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.api.BrewingAPI;
import redgear.brewcraft.api.IRecipeRegistry;
import redgear.core.util.SimpleItem;

public class RecipeRegistry implements IRecipeRegistry {
	
	public HashSet<BreweryRecipe> recipes = new HashSet();
	
	public RecipeRegistry(){
		BrewingAPI.registry = this;
	}

	@Override
	public void addRecipe(FluidStack input, FluidStack output, ItemStack item, int itemAmount, int time) {
		if(item != null)
			addRecipe(input, output, new SimpleItem(item), itemAmount, time);
	}
	
	public void addRecipe(FluidStack input, FluidStack output, SimpleItem item, int itemAmount, int time) {
		if(input != null && input.amount > 0 && output != null && output.amount > 0 && item != null && itemAmount > 0)
			recipes.add(new BreweryRecipe(input, output, item, itemAmount, time));
	}
	
	public BreweryRecipe getBreweryRecipe(FluidStack fluid, SimpleItem item) {
		return fluid == null || item == null ? null : getBreweryRecipe(fluid.fluidID, item);
	}
	
	public BreweryRecipe getBreweryRecipe(int fluidId, SimpleItem item) {
		for(BreweryRecipe recipe : recipes)
			if(recipe.input.fluidID == fluidId && recipe.item.equals(item))
				return recipe;
		return null;
	}
}
