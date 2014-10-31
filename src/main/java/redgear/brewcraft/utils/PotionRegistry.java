package redgear.brewcraft.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.api.PotionAPI;
import redgear.brewcraft.api.PotionAPI.IPotionRegistry;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.core.fluids.FluidUtil;
import redgear.core.util.SimpleItem;

public class PotionRegistry implements IPotionRegistry {

	public PotionRegistry() {
		PotionAPI.POTION_REGISTRY = this;
	}

	public FluidStack addPotion(FluidStack fluid, String name, Potion effect, int duration, int strength) {
		return addPotion(fluid, name, effect, duration, strength, false);
	}

	public FluidStack addPotion(FluidStack fluid, String name, Potion effect, int duration, int strength,
			boolean hasDescription) {
		return addPotion(fluid, PotionPlugin.potions, name, effect, duration, strength, hasDescription);
	}

	public FluidStack addPotion(FluidStack fluid, Item item, String name, Potion effect, int duration, int strength) {
		return addPotion(fluid, item, name, effect, duration, strength, false);
	}

	@Override
	public FluidStack addPotion(FluidStack fluid, Item item, String name, Potion effect, int duration, int strength,
			boolean hasDescription) {
		return addPotion(fluid, item, name, effect, duration, strength, hasDescription, true);
	}

	@Override
	public FluidStack addPotion(FluidStack fluid, Item item, String name, Potion effect, int duration, int strength,
			boolean hasDescription, boolean createSplash) {
		if (effect != null && item instanceof MetaItemPotion) {
			MetaItemPotion metaItem = (MetaItemPotion) item;
			int capacity = metaItem.getFluidCapacity();

			SimpleItem bottle = metaItem.addMetaItem(new SubItemPotion("bottle" + name, false, effect, duration * 20,
					strength, hasDescription));

			SimpleItem splash = null;
			if (createSplash)
				splash = metaItem.addMetaItem(new SubItemPotion("splash" + name, true, effect, duration * 20, strength,
						hasDescription));

			FluidStack potion;

			if (fluid == null) {
				potion = addPotionFluid(bottle.getStack(), splash == null ? null : splash.getStack(), name, duration,
						strength, item);

				return potion;
			}

			registerItems(fluid, bottle.getStack(), splash.getStack(), item, capacity);

			return fluid;
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
	public ItemStack addPotionEffect(Item item, String name, Potion effect, int duration, int strength,
			boolean hasDescription, boolean isSplash) {
		if (effect != null && item instanceof MetaItemPotion) {
			MetaItemPotion metaItem = (MetaItemPotion) item;

			SimpleItem bottle;
			if (isSplash)
				bottle = metaItem.addMetaItem(new SubItemPotion("splash" + name, true, effect, duration * 20, strength,
						hasDescription));
			else
				bottle = metaItem.addMetaItem(new SubItemPotion("bottle" + name, false, effect, duration * 20,
						strength, hasDescription));

			return bottle.getStack();
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
	public FluidStack addPotionFluid(ItemStack bottle, ItemStack splash, String name, int duration, int strength,
			Item item) {
		MetaItemPotion metaItem = (MetaItemPotion) item;
		int amount = metaItem.getFluidCapacity();
		FluidStack potion;
		if (bottle != null)
			potion = new FluidStack(FluidUtil.createFluid(new FluidPotion("potion" + name, bottle, duration * 20,
					strength), PotionPlugin.potionTexture), amount);
		else
			potion = new FluidStack(FluidUtil.createFluid(new FluidPotion("potion" + name, splash, duration * 20,
					strength), PotionPlugin.potionTexture), amount);

		registerItems(potion, bottle, splash, item, amount);
		return potion;
	}

	private void registerItems(FluidStack potion, ItemStack bottle, ItemStack splash, Item item, int fluidAmount) {
		MetaItemPotion stack = (MetaItemPotion) item;

		if (bottle != null)
			FluidContainerRegistry.registerFluidContainer(new FluidStack(potion, fluidAmount), bottle,
					stack.potionBottle);
		if (splash != null)
			FluidContainerRegistry.registerFluidContainer(new FluidStack(potion, fluidAmount), splash,
					stack.potionSplash);
	}

	@Override
	public Item createPotionItem(String itemName) {
		return createPotionItem(itemName, "potion_bottle_drinkable", "potion_overlay", "potion_bottle_splash");
	}

	@Override
	public Item createPotionItem(String itemName, String bottle, String overlay, String splash) {
		return new MetaItemPotion(itemName, bottle, overlay, splash);
	}

	@Override
	public void setEmptyItems(Item item, ItemStack bottle, ItemStack splash) {
		MetaItemPotion potion = (MetaItemPotion) item;
		potion.setEmptyItems(bottle, splash);
	}

	@Override
	public void setFluidCapacity(Item item, int capacity) {
		if (item instanceof MetaItemPotion) {
			MetaItemPotion metaItem = (MetaItemPotion)item;
			metaItem.setFluidCapacity(capacity);
		}
	}
}
