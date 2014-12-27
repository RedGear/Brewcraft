package redgear.brewcraft.blocks.unbottler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UnBottlerFactory implements ITileFactory{
	static int guiId = -1;

	public UnBottlerFactory() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityUnBottler.class, "BrewcraftUnBottler");
		}
	}

	@Override
	public TileEntity createTile() {
		return new TileEntityUnBottler();
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
		if (tile instanceof TileEntityUnBottler)
			return new GuiUnBottler(new ContainerUnBottler(inventoryPlayer, (TileEntityUnBottler) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityUnBottler)
			return new ContainerUnBottler(inventoryPlayer, (TileEntityUnBottler) tile);
		else
			return null;
	}

}
