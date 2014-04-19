package redgear.brewcraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.core.item.MetaItem;

public class ItemHeart extends MetaItem {

	public ItemHeart(String name) {
		super(name);
		setCreativeTab(Brewcraft.tab);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode)
			--stack.stackSize;
		if (IngredientPlugin.heartGold.equals(stack))
			player.heal(4F);
		else if (IngredientPlugin.heartSmall.equals(stack))
			player.heal(2F);
		else if (IngredientPlugin.heartBlaze.equals(stack))
			player.heal(3F);
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

}
