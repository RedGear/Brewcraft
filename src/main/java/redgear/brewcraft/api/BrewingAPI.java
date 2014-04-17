package redgear.brewcraft.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class BrewingAPI {

	public static IRecipeRegistry RECIPE_REGISTRY;

	public interface IRecipeRegistry {

		/**
		 * Use this to add a recipe to the Brewery
		 * 
		 * @param input Starting fluid, can be water or another potion. Stack
		 * includes the amount (Cannot be larger than 4,000).
		 * @param output Resultant fluid, EXCLUDING amount (ratio of input to
		 * output is always one-to-one).
		 * @param item Item used in brewing recipe.
		 */
		public void addRecipe(FluidStack input, FluidStack output, ItemStack item);
		
		public void addRecipe(FluidStack input, FluidStack output, ItemStack item, int amount);
		
		public void addRecipe(Fluid input, Fluid output, ItemStack item);
		
		public void addRecipe(Fluid input, Fluid output, ItemStack item, int amount);


	}
}
