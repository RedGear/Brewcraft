package redgear.brewcraft.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IRecipeRegistry {

	/**
	 * Use this to add a recipe to the Brewery
	 * @param input Starting fluid, can be water or another potion. Stack includes the amount.
	 * @param output Resultant fluid, including amount. 
	 * @param item Item used in brewing recipe. Does NOT include amount.
	 * @param itemAmount Amount of item to use, with a full item being equal to 120. (Because 120 can be divided by more numbers than 100)
	 * @param time Number of cycles this operation will take. 
	 */
	public void addRecipe(FluidStack input, FluidStack output, ItemStack item, int itemAmount, int time);

}
