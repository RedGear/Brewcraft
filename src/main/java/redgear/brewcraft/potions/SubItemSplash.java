package redgear.brewcraft.potions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import redgear.core.item.SubItem;

public class SubItemSplash extends SubItem{

	public SubItemSplash(String name, String displayName) {
		super(name, displayName);
	}
	
	private SubSplashEffect effect;

	public SubItemSplash(String name, String displayName, SubSplashEffect effect) {
		super(name, displayName);
		this.effect = effect;
	}
	
	public void impact(World world, EntityPlayer player, Potion potion){
		effect.impact(world, player, potion);
	}
	
	public interface SubSplashEffect{
		public void impact(World world, EntityPlayer player, Potion potion);
	}

}
