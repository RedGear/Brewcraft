package redgear.brewcraft.blocks.barrel;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerBarrel extends ContainerBase<TileEntityBarrel>{

	public ContainerBarrel(InventoryPlayer inventoryPlayer, TileEntityBarrel tile) {
		super(inventoryPlayer, tile);
		bindPlayerInventory(inventoryPlayer);
		
		for(Slot s : tile.getSlots())
			this.addSlotToContainer(s);
	}
}
