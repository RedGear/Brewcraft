package redgear.brewcraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.ItemPlugin;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;

public class ItemTear extends MetaItem<SubItem> {

	public ItemTear(String name) {
		super(name);
		setUnlocalizedName(name);
		setCreativeTab(Brewcraft.tabMisc);
		setMaxStackSize(8);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeldItem) {
		if (entity != null && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			onUpdate(stack, world, player.inventory, slot);
		}
	}

	//Called by the brewery as well as player. 
	public void onUpdate(ItemStack stack, World world, IInventory tile, int slot) {
		if (stack != null)
			if (world.provider.dimensionId == -1) {
				if (ItemPlugin.pureTear.equals(stack))
					tile.setInventorySlotContents(slot, ItemPlugin.obsidianTear.getStack(stack.stackSize));
			} else if (ItemPlugin.obsidianTear.equals(stack))
				tile.setInventorySlotContents(slot, ItemPlugin.pureTear.getStack(stack.stackSize));
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		if (par1ItemStack.getItemDamage() == 0)
			list.add(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("tooltip.brewcraft.tear.nether"));
		else
			list.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("tooltip.brewcraft.tear.overworld"));
	}

}
