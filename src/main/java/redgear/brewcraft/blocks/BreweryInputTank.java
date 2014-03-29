package redgear.brewcraft.blocks;

import redgear.brewcraft.common.Brewcraft;
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
		if (Brewcraft.recipeRegistry.getBreweryRecipe(fluidId, tile.getCurrItem()) != null)
			return true;
		else
			return false;
	}

}
