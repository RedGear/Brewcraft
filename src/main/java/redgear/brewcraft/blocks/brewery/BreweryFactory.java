package redgear.brewcraft.blocks.brewery;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BreweryFactory implements ITileFactory {
	static int guiId = -1;

	public BreweryFactory() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityBrewery.class, "TileEntityBrewery");
		}
	}

	@Override
	public TileEntityBrewery createTile() {
		return new TileEntityBrewery();
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	@Override
	public int guiId() {
		return guiId;
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
