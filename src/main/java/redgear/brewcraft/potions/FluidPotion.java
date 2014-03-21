package redgear.brewcraft.potions;

import redgear.core.util.StringHelper;
import net.minecraftforge.fluids.Fluid;

public class FluidPotion extends Fluid{
	
	private final int color;

	public FluidPotion(String fluidName, int color) {
		super(fluidName);
		this.color = color;
		this.setUnlocalizedName(StringHelper.parseUnLocalName(fluidName));
	}
	
	public int getColor()
    {
        return color;
    }

}
