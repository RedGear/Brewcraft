package redgear.brewcraft.blocks;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.machine.IMachine;
import redgear.core.tile.ITileFactory;

public class BreweryFactory implements ITileFactory<TileEntityBrewery, ContainerBrewery, GuiBrewery> {

	@Override
	public TileEntityBrewery createTile() {
		return new TileEntityBrewery();
	}

	@Override
	public IMachine createMachine() {
		return null;
	}

	@Override
	public GuiBrewery createGui(InventoryPlayer inventoryPlayer, TileEntity tile) {
		return new GuiBrewery(createContainer(inventoryPlayer, tile));
	}

	@Override
	public ContainerBrewery createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityBrewery)
			return new ContainerBrewery(inventoryPlayer, (TileEntityBrewery) tile);
		else
			return null;
	}

}
