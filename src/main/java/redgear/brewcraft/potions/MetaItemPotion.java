package redgear.brewcraft.potions;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.blocks.brewery.TileEntityBrewery;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.plugins.core.EffectPlugin;
import redgear.core.item.MetaItem;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaItemPotion extends MetaItem<SubItemPotion> {

	@SideOnly(Side.CLIENT)
	public IIcon splash;
	@SideOnly(Side.CLIENT)
	public IIcon bottle;
	@SideOnly(Side.CLIENT)
	public IIcon overlay;

	public final String bottleIcon;
	public final String overlayIcon;
	public final String splashIcon;

	public ItemStack potionBottle;
	public ItemStack potionSplash;

	public int capacity = 1000;

	public MetaItemPotion(String name, String bottle, String overlay, String splash) {
		super(name);
		this.bottleIcon = bottle;
		this.overlayIcon = overlay;
		this.splashIcon = splash;
		setMaxStackSize(1);
	}

	public SimpleItem addMetaItem(SubItemPotion newItem) {
		return super.addMetaItem(newItem);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote)
			getMetaItem(stack.getItemDamage()).effect(player);
		
		if (!player.capabilities.isCreativeMode) {
			if(--stack.stackSize <= 0)
				return potionBottle.copy();
			else
				player.inventory.addItemStackToInventory(potionBottle.copy());
		}
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
		if (potion.isSplash) {
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityBrewcraftPotion(world, player, stack.copy()));
			if (!player.capabilities.isCreativeMode)
				--stack.stackSize;
		} else
			player.setItemInUse(stack, getMaxItemUseDuration(stack));

		return stack;
	}
	
	
	/**
     * This is called when the item is used, before the block is activated.
     */
	@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {	
		if (!player.capabilities.isCreativeMode) {
			TileEntity usedOn = world.getTileEntity(x, y, z);			
			if (usedOn instanceof TileEntityBrewery) {
				SubItemPotion potion = getMetaItem(stack.getItemDamage());
				TileEntityBrewery tileEntity = (TileEntityBrewery) usedOn;
				FluidStack potionFluid = FluidContainerRegistry.getFluidForFilledItem(stack);
				if (tileEntity.inputTank.canFill(this.getFluidCapacity()) && 
						(tileEntity.inputTank.canFillWithMap(potionFluid, false))) {
					if (potion.isSplash) {
						player.inventory.addItemStackToInventory(potionSplash.copy());
					} else {
						player.inventory.addItemStackToInventory(potionBottle.copy());
					}
				}			
			}				
		}
		return false;
        
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		SubItemPotion potion = getMetaItem(par1);
		return potion.isSplash ? splash : bottle;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 == 0 ? overlay : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		SubItemPotion potion = getMetaItem(par1ItemStack.getItemDamage());
		String s1 = StatCollector.translateToLocal(potion.getEffect().getName()).trim();

		if (potion.strength > 0)
			s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potion.strength).trim();

		if (!potion.getEffect().isInstant())
			s1 = s1 + " ("
					+ Potion.getDurationString(new PotionEffect(potion.potionId, potion.duration, potion.strength))
					+ ")";

		par3List.add((potion.getEffect().isBadEffect() ? EnumChatFormatting.RED : EnumChatFormatting.GRAY) + s1);

		if (potion.hasDesc) {
			par3List.add("");
			par3List.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
			par3List.add((potion.getEffect().isBadEffect() ? EnumChatFormatting.RED : EnumChatFormatting.BLUE)
					+ StatCollector.translateToLocal("tooltip.brewcraft." + potion.getEffect().getName()
							+ potion.strength));
		}
		if (potion.potionId == EffectPlugin.angel.id) {
			par3List.add(EnumChatFormatting.BLUE
					+ StatCollector.translateToLocal("tooltip.brewcraft." + potion.getEffect().getName() + ".desc2"));
			par3List.add(EnumChatFormatting.BLUE
					+ StatCollector.translateToLocal("tooltip.brewcraft." + potion.getEffect().getName() + ".desc3"));
		}

	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		SubItemPotion potion = getMetaItem(par1ItemStack.getItemDamage());
		String s = "";

		if (potion.isSplash)
			s = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";

		String s1 = "";
		s1 = potion.getEffect().getName();
		s1 = s1 + (".postfix");

		return s + StatCollector.translateToLocal(s1).trim();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		SubItemPotion potion = getMetaItem(par1ItemStack.getItemDamage());
		return par2 > 0 || potion == null ? 16777215 : potion.getEffect().getLiquidColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return pass == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		bottle = par1IconRegister.registerIcon(bottleIcon);
		splash = par1IconRegister.registerIcon(splashIcon);
		overlay = par1IconRegister.registerIcon(overlayIcon);
	}

	@SideOnly(Side.CLIENT)
	public IIcon func_94589_d(String par0Str) {
		return par0Str.equals(bottleIcon) ? bottle : par0Str.equals(splashIcon) ? splash
				: par0Str.equals(overlayIcon) ? overlay : null;
	}

	public void setEmptyItems(ItemStack bottle, ItemStack splash) {
		this.potionBottle = bottle;
		this.potionSplash = splash;
	}

	public void setFluidCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getFluidCapacity() {
		return this.capacity;
	}
}
