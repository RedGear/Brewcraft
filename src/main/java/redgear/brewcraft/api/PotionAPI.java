package redgear.brewcraft.api;

import net.minecraft.potion.Potion;

public class PotionAPI {

	public static IPotionRegistry POTION_REGISTRY;

	public interface IPotionRegistry {

		/**
		 * Use this API to add potions.
		 * 
		 * @param name - The name of the potion ('potion' is attached as a
		 * prefix).
		 * @param effect - The effect that this potion inflicts upon victims.
		 * @param hasDescription - Whether or not a string is generated for the
		 * potion
		 * to add information. The string, which mod authors will have to
		 * individually
		 * localize, is the potion effect's name (potion.example) followed by
		 * the strength
		 * as an integer (0) and the postfix '.desc'. For example, if you were
		 * to inflict
		 * the 'Example' potion effect on entities, with default strength of
		 * one, the string
		 * would generate like this - 'potion.example.0.desc'
		 */

		public void addPotion(String name, Potion effect, int strength, int duration, boolean hasDescription);

	}

}
