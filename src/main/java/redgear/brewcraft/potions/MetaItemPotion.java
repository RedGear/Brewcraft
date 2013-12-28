package redgear.brewcraft.potions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.util.SimpleItem;

public class MetaItemPotion extends MetaItem{

	public MetaItemPotion(int itemID, String name) {
		super(itemID, name);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	public SimpleItem addMetaItem(SubItemPotion newItem){
		SimpleItem temp = super.addMetaItem(newItem);
		
		return temp;
	}
	
	@Override
	@Deprecated
	public SimpleItem addMetaItem(SubItem newItem){
		if(!(newItem instanceof SubItemPotion))
    		throw new ClassCastException("MetaItemPotion can only except SubItemPotion!");
        return addMetaItem((SubItemPotion) newItem);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
		if (!player.capabilities.isCreativeMode){
            --stack.stackSize;
            player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle, 1, 0));
        }
		
		if(!world.isRemote)
			((SubItemPotion)getMetaItem(stack.getItemDamage())).effect(world, player);
			
		return stack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack){
        return 32;
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack){
        return EnumAction.drink;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack){
		
		return true;
		
	}
	
	
}
