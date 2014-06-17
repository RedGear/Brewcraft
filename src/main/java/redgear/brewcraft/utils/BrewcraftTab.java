package redgear.brewcraft.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.block.MachinePlugin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BrewcraftTab extends CreativeTabs {

	private boolean background;

	public BrewcraftTab() {
		super("brewcraft");

		this.background = Brewcraft.inst.getBoolean("General", "Toggle Unconventional Creative Tab Overlays");
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
