package redgear.brewcraft.blocks.brewery;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerBrewery extends ContainerBase<TileEntityBrewery>{

	public ContainerBrewery(InventoryPlayer inventoryPlayer, TileEntityBrewery tile) {
		super(inventoryPlayer, tile);
		bindPlayerInventory(inventoryPlayer);
		
		for(Slot s : tile.getSlots())
			this.addSlotToContainer(s);
		
		
	}

}
