package redgear.brewcraft.blocks.keg;

import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.api.tile.IBucketableTank;
import redgear.core.tile.TileEntityTank;

public class TileEntityKeg extends TileEntityTank implements IBucketableTank {

	public final AdvFluidTank tank;
	public final String type;

	public TileEntityKeg(String type) {
		super(10);

		tank = new KegTank(this, FluidContainerRegistry.BUCKET_VOLUME * 8);
		this.type = type;

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
