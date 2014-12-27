package redgear.brewcraft.blocks.bottler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerBottler extends ContainerBase<TileEntityBottler>{

	public ContainerBottler(InventoryPlayer inventoryPlayer, TileEntityBottler tile) {
		super(inventoryPlayer, tile);
		bindPlayerInventory(inventoryPlayer);
		
		for(Slot s : tile.getSlots())
			this.addSlotToContainer(s);
	}

}
