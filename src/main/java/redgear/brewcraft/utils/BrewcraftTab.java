package redgear.brewcraft.utils;

import redgear.brewcraft.core.Brewcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BrewcraftTab extends CreativeTabs {

	private boolean hasBackground;

	public BrewcraftTab(String lable, boolean background) {
		super(lable);

		this.hasBackground = background;
		if(hasBackground)
			setNoTitle();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Brewcraft.brewery.getItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getBackgroundImageName() {
		return hasBackground ? "background.png" : "items.png";
	}

}
