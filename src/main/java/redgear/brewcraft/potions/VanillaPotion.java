package redgear.brewcraft.potions;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.item.ItemPotion;
import redgear.core.util.SimpleItem;

public class VanillaPotion extends FluidStack {
	public SimpleItem potionItem;
	public FluidPotion potionFluidWrapper;
	public Fluid potionFluid;
	public FluidStack potionStack;
	public int metaBottle;
	public int metaSplash;
	
	
	public VanillaPotion(FluidStack stack, int amount) {
		super(stack, amount);
	}
	
	public VanillaPotion get() {
		return this;
	}
	
	public boolean isSplash() {
		return ItemPotion.isSplash(potionItem.meta);
	}	
	
    public boolean isEquivalent(Object o) {
		return false;
	}
}
