package redgear.brewcraft.potions;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.util.SimpleItem;

public class VanillaPotion extends FluidStack {
	public SimpleItem potionItem;
	public FluidPotion potionFluidWrapper;
	public Fluid potionFluid;
	public FluidStack potionStack;
	
	
	public VanillaPotion(FluidStack stack, int amount) {
		super(stack, amount);
	}
	
	public VanillaPotion getVanillaPotion() {
		return this;
	}
}
