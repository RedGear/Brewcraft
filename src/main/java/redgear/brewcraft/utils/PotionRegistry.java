package redgear.brewcraft.utils;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.api.PotionAPI;
import redgear.brewcraft.api.PotionAPI.IPotionRegistry;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.brewcraft.plugins.common.PotionPlugin;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.core.fluids.FluidUtil;
import redgear.core.util.SimpleItem;

public class PotionRegistry implements IPotionRegistry {

	public PotionRegistry() {
		PotionAPI.POTION_REGISTRY = this;
	}

	public FluidStack addPotion(String name, Potion effect, int duration, int strength) {
		return addPotion(name, effect, duration, strength, false);
	}

	public FluidStack addPotion(String name, Potion effect, int duration, int strength, boolean hasDescription) {
		return addPotion(PotionPlugin.potions, name, effect, duration, strength, hasDescription);
	}

	public FluidStack addPotion(Fluid base, String name, Potion effect, int duration, int strength) {
		return addPotion(base, name, effect, duration, strength, false);
	}

	public FluidStack addPotion(Fluid base, String name, Potion effect, int duration, int strength,
			boolean hasDescription) {
		return addPotion(PotionPlugin.potions, base, name, effect, duration, strength, hasDescription);
	}

	@Override
	public FluidStack addPotion(Item item, String name, Potion effect, int duration, int strength,
			boolean hasDescription) {
		return addPotion(item, null, name, effect, duration, strength, hasDescription);
	}

	@Override
	public FluidStack addPotion(Item item, Fluid base, String name, Potion effect, int duration, int strength,
			boolean hasDescription) {
		if (effect != null && item instanceof MetaItemPotion) {
			MetaItemPotion metaItem = (MetaItemPotion) item;

			SimpleItem bottle = metaItem.addMetaItem(new SubItemPotion("bottle" + name, false, effect, duration * 20,
					strength, hasDescription));
			SimpleItem splash = metaItem.addMetaItem(new SubItemPotion("splash" + name, true, effect, duration * 20,
					strength, hasDescription));
			FluidStack potion;
			if (base == null)
				potion = new FluidStack(FluidUtil.createFluid(new FluidPotion("potion" + name, bottle),
						PotionPlugin.potionTexture), 1000);
			else
				potion = NBTHelper(base, duration, strength);

			FluidContainerRegistry.registerFluidContainer(potion, bottle.getStack(),
					PotionPlugin.emptyBottle.getStack());
			FluidContainerRegistry.registerFluidContainer(potion, splash.getStack(),
					IngredientPlugin.splashBottle.getStack());

			return potion;
		} else {
			StringBuilder message = new StringBuilder("Potion " + name + " errored during registry. Error(s) found: ");

			if (effect == null)
				message.append("Potion's effect returned null. ");

			if (!(item instanceof MetaItemPotion))
				message.append("Passed Item is NOT a potion bottle! ");

			Brewcraft.inst.myLogger.warn(message.toString());
			return null;
		}
	}

	@Override
	public Item createPotionItem(String itemName) {
		return new MetaItemPotion(itemName);
	}

	public FluidStack NBTHelper(Fluid fluid, int duration, int strength) {
		FluidStack stack = new FluidStack(fluid, 1000);

		if (duration > 0)
			stack.tag.setInteger("duration", duration);

		if (strength > 0)
			stack.tag.setInteger("strength", strength);

		return stack;
	}
}
