package redgear.brewcraft.blocks.keg;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.api.tile.IBucketableTank;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.tile.TileEntityTank;

public class TileEntityKeg extends TileEntityTank implements IBucketableTank {

	public final AdvFluidTank tank;
	public EnumKegType type;
	
	public TileEntityKeg(){
		super(10);

		tank = new KegTank(this, FluidContainerRegistry.BUCKET_VOLUME * 8);
		addTank(tank);
	}

	public TileEntityKeg(EnumKegType type) {
		this();
		this.type = type;	
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
	
	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setString("kegType", type.name());
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		type = EnumKegType.valueOf(tag.getString("kegType"));
	}
}
