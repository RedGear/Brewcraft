package redgear.brewcraft.potions;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.entity.EntityBrewcraftPotion;
import redgear.brewcraft.plugins.core.EffectPlugin;
import redgear.core.item.MetaItem;
import redgear.core.util.SimpleItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaItemPotion extends MetaItem<SubItemPotion> {

	@SideOnly(Side.CLIENT)
	public static IIcon splash;
	@SideOnly(Side.CLIENT)
	public static IIcon bottle;
	@SideOnly(Side.CLIENT)
	public static IIcon overlay;

	public MetaItemPotion(String name) {
		super(name);
		setCreativeTab(Brewcraft.tabMisc);
		setMaxStackSize(1);
	}

	public SimpleItem addMetaItem(SubItemPotion newItem) {
		return super.addMetaItem(newItem);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
			player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle, 1, 0));
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
					+ StatCollector.translateToLocal("tooltip.brewcraft."
							+ potion.getEffect().getName()+ ".desc2"));
			par3List.add(EnumChatFormatting.BLUE
					+ StatCollector.translateToLocal("tooltip.brewcraft."
							+ potion.getEffect().getName() + ".desc3"));
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
		s1 = s1 + ".postfix";

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
		bottle = par1IconRegister.registerIcon("potion_bottle_drinkable");
		splash = par1IconRegister.registerIcon("potion_bottle_splash");
		overlay = par1IconRegister.registerIcon("potion_overlay");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon func_94589_d(String par0Str) {
		return par0Str.equals("bottle_drinkable") ? bottle : par0Str.equals("bottle_splash") ? splash : par0Str
				.equals("overlay") ? overlay : null;
	}
}
