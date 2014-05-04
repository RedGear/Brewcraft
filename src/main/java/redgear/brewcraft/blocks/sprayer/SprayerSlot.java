package redgear.brewcraft.blocks.sprayer;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import redgear.core.inventory.InvSlot;

public class SprayerSlot extends InvSlot {

	public SprayerSlot(IInventory inventory, int x, int y) {
		super(inventory, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (stack.getItem().getPotionEffect(stack) != null)
			return true;
		else
			return false;
	}

}
