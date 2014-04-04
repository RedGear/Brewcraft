package redgear.brewcraft.potions;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import redgear.core.util.SimpleItem;
import redgear.core.util.StringHelper;

public class FluidPotion extends Fluid {

	private final int color;

	public FluidPotion(String fluidName, SimpleItem bottle) {
		this(fluidName, bottle.getStack());
	}

	public FluidPotion(String fluidName, ItemStack bottle) {
		super(fluidName);
		setUnlocalizedName(StringHelper.parseUnLocalName(fluidName));
		color = bottle.getItem().getColorFromItemStack(bottle, 0);
	}

	@Override
	public int getColor() {
		return color;
	}

}
