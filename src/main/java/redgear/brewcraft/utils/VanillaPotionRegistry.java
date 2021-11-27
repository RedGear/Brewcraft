package redgear.brewcraft.utils;

import java.util.HashSet;

import redgear.brewcraft.potions.VanillaPotion;

public class VanillaPotionRegistry {
	
	private HashSet<VanillaPotion> potionRegistry = new HashSet<VanillaPotion>();
	
	public boolean registerPotion(VanillaPotion potion) {
		if(potion != null) {
			potionRegistry.add(potion);
			return true;
		} else {
			return false;
		}
	}
	
	public HashSet<VanillaPotion> getRegistry() {
		return potionRegistry;
	}
	/**
	 * 
	 * Uses a Splash Potion's meta value to get the Regular Potion equivalent
	 */
	public VanillaPotion getRegularPotion(int meta) {
		for(VanillaPotion potion : this.getRegistry()) {
			if(potion.metaBottle == meta) {
				//System.out.println("Found potion " + potion.metaBottle);
				return potion;
			}
		}
		return null;
	}
	
	public VanillaPotion getRegularPotionEquivalent (int meta) {
		for(VanillaPotion potion : this.getRegistry()) {
			if(potion.metaSplash == meta) {
				//System.out.println("Found Equivalent for " + potion.metaBottle + " | " + potion.metaSplash);
				return potion;
			}
		}
		return null;
	}
}
