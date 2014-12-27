package redgear.brewcraft.blocks.unbottler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerUnBottler extends ContainerBase<TileEntityUnBottler>{

	public ContainerUnBottler(InventoryPlayer inventoryPlayer, TileEntityUnBottler tile) {
		super(inventoryPlayer, tile);
		bindPlayerInventory(inventoryPlayer);
		
		for(Slot s : tile.getSlots())
			this.addSlotToContainer(s);
	}

}
