package redgear.brewcraft.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class PotionAPI {

	public static IPotionRegistry POTION_REGISTRY;

	public interface IPotionRegistry {

		/**
		 * Use this to create a new Brewcraft potion item. Useful for those who
		 * want to
		 * make new potions without having to deal with the items. You NEED to
		 * use this for
		 * all the other methods except the addPotionFluid ones. You should only
		 * need one of these per mod.
		 * 
		 * @param itemName The mod-unique name of this item. Not used for
		 * anything except ItemRegistry.
		 * @return Your potion bottle item. This can hold potion effects.
		 */
		Item createPotionItem(String itemName);

		/**
		 * Add a potion effect to this bottle. Item MUST be one received from
		 * {@link createPotionItem}.
		 * 
		 * @param item Potion item created with {@link createPotionItem}.
		 * @param name The name of this item. Only used in
		 * CustomItemStackRegistry. (bottle or splash are appended as prefixes)
		 * @param effect The potion effect this bottle holds.
		 * @param duration The duration (in seconds) this effect lasts.
		 * @param strength The strength of this effect, this will be available
		 * to the effect when it's active and can be used by it.
		 * @param hasDescription If this bottle will have a special description.
		 * Descriptions are created in lang files with
		 * "potion.name.(strength as int).desc=(descripton)". Such as
		 * "potion.test.0.desc=+40% More Tests".
		 * @param isSplash If this potion will be a splash potion. If you want
		 * to create normal and splash potions for the same effect
		 * you must call this method twice changing only this boolean.
		 * @return And ItemStack of the new bottle. Obviously the item is the
		 * same as the input, the important thing is that this
		 * stack includes the new meta-value of this bottle.
		 */
		ItemStack addPotionEffect(Item item, String name, Potion effect, int duration, int strength,
				boolean hasDescription, boolean isSplash);

		/**
		 * Creates a new potion fluid for this potion bottle and returns it in a
		 * FluidStack for use in Brewery Recipes.
		 * 
		 * @param bottle The input bottle. Can be any item. Display name and
		 * color of potion are taken from this item's
		 * getItemStackDisplayName() and getColorFromItemStack() methods
		 * respectively. Unless it's null, then it's taken from splash.
		 * @param splash The splash form of the bottle. This OR bottle could be
		 * null, but if they're BOTH null it will crash.
		 * @param name The name of the fluid. "potion" will be appended as a
		 * prefix. This name must be totally unique as it is used in the Fluid
		 * Registry.
		 * So using "Test" would create a fluid named "potionTest" in the Fluid
		 * Registry.
		 * @param duration The duration of this effect. Should be same as the
		 * item, but not necessary.
		 * @param strength The strength of this effect. Should be same as the
		 * item, but not necessary.
		 * @return FluidStack of the new fluid, bearing the duration and
		 * strength as NBT. (Unless they are 0, values of 0 are not stored).
		 * Use this stack for Brewery recipes.
		 */
		FluidStack addPotionFluid(ItemStack bottle, ItemStack splash, String name, int duration, int strength);

		/**
		 * This is a shortcut method. It creates the bottle, splash bottle, and
		 * fluid all at once.
		 * 
		 * @param item Potion item created with {@link createPotionItem}.
		 * @param name The name of both the item and fluid. Used in
		 * CustomItemStackRegistry and the name of the fluid.
		 * "potion" will be appended as a prefix to the fluid name. This name
		 * must be totally unique as it is used in the Fluid Registry.
		 * So using "Test" would create a fluid named "potionTest" in the Fluid
		 * Registry.
		 * @param effect The potion effect this bottle holds.
		 * @param duration The duration (in seconds) this effect lasts.
		 * @param strength The strength of this effect, this will be available
		 * to the effect when it's active and can be used by it.
		 * @param hasDescription If this bottle will have a special description.
		 * Descriptions are created in lang files with
		 * "potion.name.(strength as int).desc=(descripton)". Such as
		 * "potion.test.0.desc=+40% More Tests".
		 * @return FluidStack of the new fluid, bearing the duration and
		 * strength as NBT. (Unless they are 0, values of 0 are not stored).
		 * Use this stack for Brewery recipes.
		 */
		FluidStack addPotion(Item item, String name, Potion effect, int duration, int strength, boolean hasDescription);

		/**
		 * This is a shortcut method. It creates the bottle, (optionally) splash
		 * bottle, and fluid all at once.
		 * 
		 * @param item Potion item created with {@link createPotionItem}.
		 * @param name The name of both the item and fluid. Used in
		 * CustomItemStackRegistry and the name of the fluid.
		 * "potion" will be appended as a prefix to the fluid name. This name
		 * must be totally unique as it is used in the Fluid Registry.
		 * So using "Test" would create a fluid named "potionTest" in the Fluid
		 * Registry.
		 * @param effect The potion effect this bottle holds.
		 * @param duration The duration (in seconds) this effect lasts.
		 * @param strength The strength of this effect, this will be available
		 * to the effect when it's active and can be used by it.
		 * @param hasDescription If this bottle will have a special description.
		 * Descriptions are created in lang files with
		 * "potion.name.(strength as int).desc=(descripton)". Such as
		 * "potion.test.0.desc=+40% More Tests".
		 * @param createSplash Setting this to false will NOT create a splash
		 * bottle. You can just omit this argument if you want splash bottles.
		 * @return FluidStack of the new fluid, bearing the duration and
		 * strength as NBT. (Unless they are 0, values of 0 are not stored).
		 * Use this stack for Brewery recipes.
		 */
		FluidStack addPotion(Item item, String name, Potion effect, int duration, int strength, boolean hasDescription,
				boolean createSplash);

	}

}
