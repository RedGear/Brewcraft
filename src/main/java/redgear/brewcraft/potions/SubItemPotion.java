package redgear.brewcraft.potions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import redgear.core.item.SubItem;

public class SubItemPotion extends SubItem{
	
	private SubPotionEffect effect;

	public SubItemPotion(String name, String displayName, SubPotionEffect effect) {
		super(name, displayName);
		this.effect = effect;
	}
	
	public void effect(World world, EntityPlayer player){
		effect.effect(world, player);
	}
	
	public interface SubPotionEffect{
		public void effect(World world, EntityPlayer player);
	}

}
