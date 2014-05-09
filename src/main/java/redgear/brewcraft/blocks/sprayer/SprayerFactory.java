package redgear.brewcraft.blocks.sprayer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SprayerFactory implements ITileFactory{

	static int guiId = -1;

	public SprayerFactory() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntitySprayer.class, "TileEntitySprayer");
		}
	}
	
	@Override
	public TileEntitySprayer createTile() {
		return new TileEntitySprayer();
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
		if (tile instanceof TileEntitySprayer)
			return new GuiSprayer(new ContainerSprayer(inventoryPlayer, (TileEntitySprayer) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntitySprayer)
			return new ContainerSprayer(inventoryPlayer, (TileEntitySprayer) tile);
		else
			return null;
	}
}
