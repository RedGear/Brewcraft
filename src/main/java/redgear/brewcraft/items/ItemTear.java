package redgear.brewcraft.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.core.item.ItemGeneric;

public class ItemTear extends ItemGeneric {

	private String name;

	public ItemTear(String name) {
		super(name);
		setUnlocalizedName(name);
		this.name = name;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (par3Entity != null && par3Entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) par3Entity;
			if (par1ItemStack != null) {
				if (player.dimension == -1) {
					if (name == "puretear") {
						player.inventory.consumeInventoryItem(par1ItemStack.getItem());
						player.inventory.addItemStackToInventory(new ItemStack(IngredientPlugin.obsidianTear, 1));
					}
				} else if (player.dimension == 0) {
					if (name == "obsidiantear") {
						player.inventory.consumeInventoryItem(par1ItemStack.getItem());
						player.inventory.addItemStackToInventory(new ItemStack(IngredientPlugin.pureTear, 1));
					}
				}
			}
		}
	}
}
