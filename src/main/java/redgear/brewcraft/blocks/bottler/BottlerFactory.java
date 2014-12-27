package redgear.brewcraft.blocks.bottler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BottlerFactory implements ITileFactory{
	static int guiId = -1;

	public BottlerFactory() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityBottler.class, "BrewcraftBottler");
		}
	}

	@Override
	public TileEntity createTile() {
		return new TileEntityBottler();
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
		if (tile instanceof TileEntityBottler)
			return new GuiBottler(new ContainerBottler(inventoryPlayer, (TileEntityBottler) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityBottler)
			return new ContainerBottler(inventoryPlayer, (TileEntityBottler) tile);
		else
			return null;
	}

}
