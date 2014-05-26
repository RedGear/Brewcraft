package redgear.brewcraft.blocks.keg;

public enum EnumKegType {
	
	OAK("Oak"),
	BIRCH("Birch"),
	JUNGLE("Jungle"),
	SPRUCE("Spruce"),
	DARK("Dark"),
	ACACIA("Acacia"),
	IRON("Iron", true),
	SEALED("Sealed", false, true),
	PLATED("Plated", true, true),
	STEEL("Steel", true),
	COPPER("Copper", true),
	SILVER("Silver", true),
	TUNGSTEN("Tungsten", true),
	BRASS("Brass", true),
	RUBBER("Rubber", false, true);
	
	private final boolean canHoldHeat;
	private final boolean canHoldGas;
	
	EnumKegType(String name) {
		this(name, false);
	}
	
	EnumKegType(String name, boolean canHoldHeat) {
		this(name, canHoldHeat, false);
	}
	
	EnumKegType(String name, boolean canHoldHeat, boolean canHoldGas) {
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
