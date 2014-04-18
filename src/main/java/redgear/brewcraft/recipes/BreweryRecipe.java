package redgear.brewcraft.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.util.SimpleItem;

public class BreweryRecipe {
	public final FluidStack input;
	public final FluidStack output;
	public final SimpleItem item;

	BreweryRecipe(FluidStack input, FluidStack output, SimpleItem item) {
		this.input = input.copy();
		this.output = output.copy();
		this.item = (SimpleItem) item.copy();

		this.output.amount = 1;
	}

	BreweryRecipe(FluidStack input, FluidStack output, ItemStack item) {
		this(input, output, new SimpleItem(item));
	}
}
