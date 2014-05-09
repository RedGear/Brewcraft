package redgear.brewcraft.blocks.sprayer;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.potions.FluidPotion;
import redgear.core.fluids.AdvFluidTank;

public class SprayerTank extends AdvFluidTank{

	public SprayerTank(int capacity) {
		super(capacity);
	}

	@Override
	public boolean canAccept(int fluidId) {
		if (FluidRegistry.getFluid(fluidId) instanceof FluidPotion)
			return true;
		else
			return false;
	}

	@Override
	public boolean canFillWithMap(FluidStack other, boolean fully) {
		return FluidRegistry.getFluid(other.getFluid().getID()) instanceof FluidPotion;
	}

}
