package redgear.brewcraft.potions;

import net.minecraftforge.fluids.Fluid;
import redgear.core.util.SimpleItem;
import redgear.core.util.StringHelper;

public class FluidPotion extends Fluid{
	
	private SimpleItem item;

	public FluidPotion(String fluidName, SimpleItem bottle, SimpleItem splash) {
		super(fluidName);
		this.setUnlocalizedName(StringHelper.parseUnLocalName(fluidName));
		this.item = bottle;
	}
	
	@Override
    public int getColor()
    {
        return item.getItem().getColorFromItemStack(item.getStack(), 0);
    }
	
}
