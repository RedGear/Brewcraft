package redgear.brewcraft.utils;

import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.brewcraft.api.PotionAPI;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.brewcraft.plugins.common.PotionPlugin;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.core.fluids.FluidUtil;
import redgear.core.util.SimpleItem;

public class PotionRegistry implements redgear.brewcraft.api.PotionAPI.IPotionRegistry {

	public PotionRegistry() {
		PotionAPI.POTION_REGISTRY = this;
	}

	public void addPotion(String name, Potion effect, int strength, int duration) {
		addPotion(name, effect, duration, strength, false);
	}

	@Override
	public void addPotion(String name, Potion effect, int strength, int duration, boolean hasDescription) {
		if (effect != null) {
			SimpleItem bottle = PotionPlugin.potions.addMetaItem(new SubItemPotion("bottle" + name, false, effect,
					duration, strength, hasDescription));
			SimpleItem splash = PotionPlugin.potions.addMetaItem(new SubItemPotion("splash" + name, true, effect,
					duration, strength, hasDescription));
			Fluid potion = FluidUtil.createFluid(new FluidPotion("potion" + name, bottle), PotionPlugin.potionTexture);

			FluidContainerRegistry.registerFluidContainer(potion, bottle.getStack(),
					PotionPlugin.emptyBottle.getStack());
			FluidContainerRegistry.registerFluidContainer(potion, splash.getStack(),
					IngredientPlugin.splashBottle.getStack());
		} else {
			StringBuilder message = new StringBuilder("Potion " + name + " errored during registry. Error found: ");

			if (effect == null)
				message.append("Potion's effect returned null.");

			Brewcraft.inst.myLogger.warn(message.toString());

		}
	}
}
