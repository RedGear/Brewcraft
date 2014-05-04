package redgear.brewcraft.blocks.sprayer;

import redgear.core.tile.TileEntityInventory;

public class TileEntitySprayer extends TileEntityInventory {

	public int slot1;
	public int slot2;
	public int slot3;
	
	public TileEntitySprayer() {
		super(10);
		
		slot1 = addSlot(new SprayerSlot(this, 80, 14));
		slot2 = addSlot(new SprayerSlot(this, 80, 35));
		slot3 = addSlot(new SprayerSlot(this, 80, 56));
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
		return true;
	}

	@Override
	protected boolean doPostWork() {
		return false;
	}

}
