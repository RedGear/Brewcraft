package redgear.brewcraft.blocks.keg;

import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.api.tile.IBucketableTank;
import redgear.core.tile.TileEntityTank;

public class TileEntityKeg extends TileEntityTank implements IBucketableTank {

	public final AdvFluidTank tank;
	public final EnumKegType type;

	public TileEntityKeg(EnumKegType type) {
		super(10);

		tank = new KegTank(this, FluidContainerRegistry.BUCKET_VOLUME * 8);
		this.type = type;

		addTank(tank);
	}

	@Override
	protected boolean doPreWork() {
		return false;
	}

	@Override
	protected int checkWork() {
		return 0;
	}

	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean tryUseEnergy(int energy) {
		return false;
	}

	@Override
	protected boolean doPostWork() {
		return false;
	}
}
