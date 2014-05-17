package redgear.brewcraft.blocks.keg;

import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;

public class KegTank extends AdvFluidTank {

	public KegTank(int capacity) {
		super(capacity);
	}

	@Override
	public boolean canAccept(int fluidId) {
			return true;
	}

	@Override
	public boolean canFillWithMap(FluidStack other, boolean fully) {
		return true;
	}
	
	@Override
	public boolean canEject(int fluidId) {
		return true;
	}
	
	private TransferRule getRule(int fluidId) {
		return TransferRule.BOTH;
	}

}
