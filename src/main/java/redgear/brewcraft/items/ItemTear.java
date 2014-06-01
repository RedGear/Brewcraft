package redgear.brewcraft.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.IngredientPlugin;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;

public class ItemTear extends MetaItem<SubItem> {

	public ItemTear(String name) {
		super(name);
		setUnlocalizedName(name);
		setCreativeTab(Brewcraft.tab);
		setMaxStackSize(8);
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
