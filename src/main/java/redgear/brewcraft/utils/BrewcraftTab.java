package redgear.brewcraft.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import redgear.brewcraft.plugins.block.MachinePlugin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BrewcraftTab extends CreativeTabs {

	private final boolean background;

	public BrewcraftTab(boolean background) {
		super("brewcraft");

		this.background = background;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return MachinePlugin.brewery.getItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getBackgroundImageName() {
		return background ? "background.png" : "items.png";
	}

}
