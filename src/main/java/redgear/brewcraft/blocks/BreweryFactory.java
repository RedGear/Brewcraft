package redgear.brewcraft.blocks;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BreweryFactory implements ITileFactory{

	@Override
	public TileEntityBrewery createTile() {
		return new TileEntityBrewery();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Object createGui(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityBrewery)
			return new GuiBrewery(new ContainerBrewery(inventoryPlayer, (TileEntityBrewery) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityBrewery)
			return new ContainerBrewery(inventoryPlayer, (TileEntityBrewery) tile);
		else
			return null;
	}

}
