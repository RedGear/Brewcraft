package redgear.brewcraft.blocks.sprayer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerSprayer extends ContainerBase<TileEntitySprayer>{

	public ContainerSprayer(InventoryPlayer inventoryPlayer, TileEntitySprayer tile) {
		super(inventoryPlayer, tile);
		bindPlayerInventory(inventoryPlayer);
		
		for(Slot s : tile.getSlots())
			this.addSlotToContainer(s);
	}
}
