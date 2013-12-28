package redgear.brewcraft.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.util.SimpleItem;

public class BreweryRecipe {
	public final FluidStack input;
	public final FluidStack output;
	public final SimpleItem item;
	public final int amount;
	public final int time;
	
	BreweryRecipe(FluidStack input, FluidStack output, SimpleItem item, int amount, int time){
		this.input = input;
		this.output = output;
		this.item = item;
		this.amount = amount;
		this.time = time;
	}
	
	BreweryRecipe(FluidStack input, FluidStack output, ItemStack item, int amount, int time){
		 this(input, output, new SimpleItem(item), amount, time);
	}
}
