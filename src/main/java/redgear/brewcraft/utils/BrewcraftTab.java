package redgear.brewcraft.utils;

import redgear.brewcraft.plugins.block.MachinePlugin;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BrewcraftTab extends CreativeTabs {

	private final boolean background;

	public BrewcraftTab(boolean background) {
		super("brewcraft");

		this.background = background;
		if(background)
			setNoTitle();
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
