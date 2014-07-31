package redgear.brewcraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;

public class ItemHeart extends MetaItem<SubItem> {

	public ItemHeart(String name) {
		super(name);
		setCreativeTab(Brewcraft.tabMisc);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode)
			--stack.stackSize;

		switch (stack.getItemDamage()) {

		case 0:
			player.heal(2F);

		case 1:
			player.heal(4F);

		case 2:
			player.heal(3F);
		}
		return stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 8;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tooltip.brewcraft.food"));
	}
}
