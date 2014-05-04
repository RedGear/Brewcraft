package redgear.brewcraft.blocks.brewery;

import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.plugins.common.PotionPlugin;
import redgear.core.fluids.AdvFluidTank;

public class BreweryInputTank extends AdvFluidTank {

	private final TileEntityBrewery tile;

	public BreweryInputTank(int capacity, TileEntityBrewery tile) {
		super(capacity);
		this.tile = tile;
	}

	/**
	 * @param fluidId Fluid ID to try to add
	 * @return True if this tank could accept this type of fluid through the
	 * fillWithMap() method
	 */
	@Override
	public boolean canAccept(int fluidId) {
		//This one shouldn't be called since canFillWithMap is also being overridden, but just to be safe ...
		if (PotionPlugin.recipeRegistry.getBreweryRecipe(new FluidStack(fluidId, 1), tile.getCurrItem()) != null)
			return true;
		else
			return false;
	}

	/**
	 * @param other FluidStack to try to add
	 * @return True if other could be added to this tank with the
	 * fillWithMap()
	 * method.
	 */
	@Override
	public boolean canFillWithMap(FluidStack other, boolean fully) {
		return other == null || PotionPlugin.recipeRegistry.isValidFluid(other) && canFill(other, fully);
	}

}
