package redgear.brewcraft.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import redgear.core.item.SubItem;

public class SubItemPotion extends SubItem {

	public final int potionId;
	public final boolean isSplash;
	public final int duration;
	public final int strength;

	public SubItemPotion(String name, boolean isSplash, Potion effect, int duration, int strength) {
		super(name);
		potionId = effect.id;
		this.isSplash = isSplash;
		this.duration = duration;
		this.strength = strength;
	}

	public void effect(EntityLivingBase entity) {
		entity.addPotionEffect(new PotionEffect(potionId, duration, strength));
	}
}
