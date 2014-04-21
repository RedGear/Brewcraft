package redgear.brewcraft.potions;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import redgear.core.asm.RedGearCore;
import redgear.core.util.SimpleItem;

public class FluidPotion extends Fluid {

	private final int color;
	private final String localName;
	public final int extension;
	public final int strength;

	public FluidPotion(String fluidName, SimpleItem bottle, int duration, int potency) {
		this(fluidName, bottle.getStack(), duration, potency);
	}

	public FluidPotion(String fluidName, ItemStack bottle, int duration, int potency) {
		super(fluidName);

		localName = bottle.getDisplayName();

		if (RedGearCore.inst.isClient())
			color = bottle.getItem().getColorFromItemStack(bottle, 0);
		else
			color = 0xFFFFFF;

		extension = duration;
		strength = potency;
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
		String s = localName;

		//So far, this gets the time even if the potoin isn't extended.
		//I need to fix this. . .
		//if (extension > 0)
			//s = s + " (" + Potion.getDurationString(new PotionEffect(Potion.confusion.id, extension, 0)) + ")";
		if (strength > 0)
			s = s + " " + StatCollector.translateToLocal("potion.potency." + strength);

		return s;
	}

}
