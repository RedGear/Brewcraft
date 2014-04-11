package redgear.brewcraft.potions;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import redgear.core.asm.RedGearCore;
import redgear.core.util.SimpleItem;

public class FluidPotion extends Fluid {

	private final int color;
	private final String localName;

	public FluidPotion(String fluidName, SimpleItem bottle) {
		this(fluidName, bottle.getStack());
	}

	public FluidPotion(String fluidName, ItemStack bottle) {
		super(fluidName);

		localName = bottle.getDisplayName();

		if (RedGearCore.inst.isClient())
			color = bottle.getItem().getColorFromItemStack(bottle, 0);
		else
			color = 0xFFFFFF;
	}

	@Override
	public int getColor() {
		return color;
	}

	/**
	 * Returns the localized name of this fluid.
	 */
	@Override
	public String getLocalizedName() {
		return localName;
	}

}
