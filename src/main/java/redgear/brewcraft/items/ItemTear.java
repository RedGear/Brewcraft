package redgear.brewcraft.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.core.item.MetaItem;

public class ItemTear extends MetaItem {

	public ItemTear(String name) {
		super(name);
		setUnlocalizedName(name);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeldItem) {
		if (entity != null && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			onUpdate(stack, world, player.inventory, slot);
		}
	}

	//Called by the brewery as well as player. 
	public void onUpdate(ItemStack stack, World world, IInventory tile, int slot) {
		if (stack != null)
			if (world.provider.dimensionId == -1) {
				if (IngredientPlugin.pureTear.equals(stack))
					tile.setInventorySlotContents(slot, IngredientPlugin.obsidianTear.getStack(stack.stackSize));
			} else if (IngredientPlugin.obsidianTear.equals(stack))
				tile.setInventorySlotContents(slot, IngredientPlugin.pureTear.getStack(stack.stackSize));
	}
}
