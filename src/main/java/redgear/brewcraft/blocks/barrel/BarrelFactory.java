package redgear.brewcraft.blocks.barrel;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BarrelFactory implements ITileFactory{

	static int guiId = -1;
	public final int woodType;

	public BarrelFactory(int type) {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityBarrel.class, "TileEntityBarrel");
		}
		this.woodType = type;
	}
	
	@Override
	public TileEntityBarrel createTile() {
		return new TileEntityBarrel(woodType);
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
		if (tile instanceof TileEntityBarrel)
			return new GuiBarrel(new ContainerBarrel(inventoryPlayer, (TileEntityBarrel) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityBarrel)
			return new ContainerBarrel(inventoryPlayer, (TileEntityBarrel) tile);
		else
			return null;
	}
}
