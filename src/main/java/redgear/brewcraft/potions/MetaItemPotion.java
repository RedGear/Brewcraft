package redgear.brewcraft.potions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.util.SimpleItem;

public class MetaItemPotion extends MetaItem {

	public MetaItemPotion(int itemID, String name) {
		super(itemID, name);
		setCreativeTab(CreativeTabs.tabBrewing);
		setMaxStackSize(1);
	}

	public SimpleItem addMetaItem(SubItemPotion newItem) {
		return super.addMetaItem(newItem);
	}

	@Override
	@Deprecated
	/**
	 * MetaItemPotion can only except SubItemPotion!
	 */
	public SimpleItem addMetaItem(SubItem newItem) {
		if (!(newItem instanceof SubItemPotion))
			throw new ClassCastException("MetaItemPotion can only except SubItemPotion!");
		return addMetaItem((SubItemPotion) newItem);
	}

	@Override
	public SubItemPotion getMetaItem(int meta) {
		return (SubItemPotion) super.getMetaItem(meta);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
			player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle, 1, 0));
		}

		if (!world.isRemote)
			getMetaItem(stack.getItemDamage()).effect(player);

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		SubItemPotion potion = getMetaItem(stack.getItemDamage());

		if (!world.isRemote && potion.isSplash) {

			if (!player.capabilities.isCreativeMode)
				--stack.stackSize;

			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityBrewcraftPotion(world, player).setPotion(new ItemStack(this.itemID, 1, stack.getItemDamage())));

		} else
			player.setItemInUse(stack, getMaxItemUseDuration(stack));

		return stack;
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

}
