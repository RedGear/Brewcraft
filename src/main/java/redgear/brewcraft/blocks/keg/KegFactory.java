package redgear.brewcraft.blocks.keg;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KegFactory implements ITileFactory{

	static int guiId = -1;
	public final String woodType;

	public KegFactory(String type) {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityKeg.class, "TileEntityBarrel");
		}
		this.woodType = type;
	}
	
	@Override
	public TileEntityKeg createTile() {
		return new TileEntityKeg(woodType);
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
		if (tile instanceof TileEntityKeg)
			return new GuiKeg(new ContainerKeg(inventoryPlayer, (TileEntityKeg) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityKeg)
			return new ContainerKeg(inventoryPlayer, (TileEntityKeg) tile);
		else
			return null;
	}
}
