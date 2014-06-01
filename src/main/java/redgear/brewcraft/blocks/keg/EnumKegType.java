package redgear.brewcraft.blocks.keg;

public enum EnumKegType {
	
	OAK(),
	BIRCH(),
	JUNGLE(),
	SPRUCE(),
	DARK(),
	ACACIA(),
	IRON(true),
	SEALED(false, true),
	PLATED(true, true),
	STEEL(true),
	COPPER(true),
	SILVER(true),
	TUNGSTEN(true),
	BRASS(true),
	RUBBER(false, true);
	
	private final boolean canHoldHeat;
	private final boolean canHoldGas;
	
	EnumKegType() {
		this(false);
	}
	
	EnumKegType(boolean canHoldHeat) {
		this(canHoldHeat, false);
	}
	
	EnumKegType(boolean canHoldHeat, boolean canHoldGas) {
		this.canHoldHeat = canHoldHeat;
		this.canHoldGas = canHoldGas;
	}
	
	public boolean getCanHoldHeat() {
		return this.canHoldHeat;
	}
	
	public boolean getCanHoldGas() {
		return this.canHoldGas;
	}
}
