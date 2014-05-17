package redgear.brewcraft.blocks.barrel;

import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.tile.IBucketableTank;
import redgear.core.tile.TileEntityTank;

public class TileEntityBarrel extends TileEntityTank implements IBucketableTank {

	public final AdvFluidTank tank;
	public final int woodType;

	public TileEntityBarrel(int metadata) {
		super(10);

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 12);
		this.woodType = metadata;

		addTank(tank);
	}

	@Override
	protected boolean doPreWork() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int checkWork() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean doWork() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean tryUseEnergy(int energy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean doPostWork() {
		// TODO Auto-generated method stub
		return false;
	}
}
