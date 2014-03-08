package redgear.brewcraft.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BrewcraftTab extends CreativeTabs{

	public BrewcraftTab(String lable) {
		super(lable);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Brewcraft.brewery.getItem();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public String getBackgroundImageName()
    {
        return "background.png";
    }

}
