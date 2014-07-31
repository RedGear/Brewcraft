package redgear.brewcraft.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import redgear.brewcraft.core.Brewcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BrewcraftTab extends CreativeTabs {

	boolean background;
	Item display;

	public BrewcraftTab(String name) {
		super("brewcraft." + name);
		this.background = Brewcraft.inst.getBoolean("General", "Toggle Unconventional Creative Tab Overlays");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return display;
	}
	
	public CreativeTabs setTabIcon(Item item) {
		display = item;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getBackgroundImageName() {
		return background ? "background.png" : "items.png";
	}

}
