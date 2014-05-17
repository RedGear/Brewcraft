package redgear.brewcraft.blocks.keg;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerKeg extends ContainerBase<TileEntityKeg>{

	public ContainerKeg(InventoryPlayer inventoryPlayer, TileEntityKeg tile) {
		super(inventoryPlayer, tile);
		bindPlayerInventory(inventoryPlayer);
		
		for(Slot s : tile.getSlots())
			this.addSlotToContainer(s);
	}
}
