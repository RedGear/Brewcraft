package redgear.brewcraft.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import redgear.brewcraft.common.Brewcraft;

import net.minecraft.potion.Potion;

public class PotionArrayExpander {

	public static void init() {
		int targetSize = Brewcraft.inst.getInt("Potion List Expansion", "Potion List Extension Size",
				"Will only do something if expanding the potion list is set to true.", 64);
		boolean enabled = Brewcraft.inst.getBoolean("Potion List Expansion", "Toggle Potion List Extension",
				"Disable if another installed mod does this.", true);

		if (enabled)
			if (Potion.potionTypes.length != targetSize)
				for (Field f : Potion.class.getDeclaredFields()) {
					f.setAccessible(true);
					try {
						if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
							Field modfield = Field.class.getDeclaredField("modifiers");
							modfield.setAccessible(true);
							modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

							final Potion[] newPotionTypes = new Potion[targetSize];
							for (int i = 0; i < Potion.potionTypes.length; i++)
								newPotionTypes[i] = Potion.potionTypes[i];
							f.set(null, newPotionTypes);
						}
					} catch (Exception e) {
						Brewcraft.inst.logDebug(e);
					}
				}
	}

}
