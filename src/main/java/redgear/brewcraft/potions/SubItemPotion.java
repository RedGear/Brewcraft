package redgear.brewcraft.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import redgear.core.item.SubItem;

public class SubItemPotion extends SubItem {

	private final SubPotionEffect effect;
	public boolean isSplash;

	public SubItemPotion(String name, boolean isSplash, SubPotionEffect effect) {
		super(name);
		this.effect = effect;
		this.isSplash = isSplash;
	}

	public boolean isSplash() {
		return isSplash;
	}

	public void effect(World world, EntityLivingBase player) {
		effect.effect(world, player);
	}

	public interface SubPotionEffect {
		public void effect(World world, EntityLivingBase player);
	}

}
