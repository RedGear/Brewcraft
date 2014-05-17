package redgear.brewcraft.blocks.keg;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;

public class KegTank extends AdvFluidTank {

	public final TileEntityKeg keg;
	
	public KegTank(TileEntityKeg keg, int capacity) {
		super(capacity);
		this.keg = keg;
	}

	@Override
	public boolean canAccept(int fluidId) {
			return FluidRegistry.getFluid(fluidId).getTemperature() < 1300 ? keg.type == "iron" : false;
	}

	@Override
	public boolean canFillWithMap(FluidStack other, boolean fully) {
		return FluidRegistry.getFluid(other.fluidID).getTemperature() < 1300 ? keg.type == "iron" : false;
	}
	
	@Override
	public boolean canEject(int fluidId) {
		return true;
	}
	
	private TransferRule getRule(int fluidId) {
		return TransferRule.BOTH;
	}

}
