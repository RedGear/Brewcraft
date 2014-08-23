package redgear.brewcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngredient extends MetaItem<SubItem> {

	public ItemIngredient(String name) {
		super(name);
		setUnlocalizedName(name);
		setCreativeTab(Brewcraft.tabMisc);
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tooltip.brewcraft.ingredient"));
	}

}
