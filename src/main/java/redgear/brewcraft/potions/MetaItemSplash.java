package redgear.brewcraft.potions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.core.item.MetaItem;
import redgear.core.util.SimpleItem;

public class MetaItemSplash extends MetaItem{

	public MetaItemSplash(int itemID, String name) {
		super(itemID, name);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	public SimpleItem addMetaItem(SubItemPotion newItem){
		SimpleItem temp = super.addMetaItem(newItem);
		
		return temp;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityBrewcraftPotion(par2World, par3EntityPlayer, par1ItemStack));
        }
        return par1ItemStack;
    }
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack){
		
		return true;
		
	}

}
