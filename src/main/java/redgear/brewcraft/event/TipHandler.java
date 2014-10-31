package redgear.brewcraft.event;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.block.KegPlugin;
import redgear.core.util.ItemStackUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TipHandler {

	private static TipHandler instance;

	private TipHandler() {

	}

	public static TipHandler register() {
		if (instance == null) {
			instance = new TipHandler();
			MinecraftForge.EVENT_BUS.register(instance);

		}
		return instance;
	}

	boolean flag = Brewcraft.inst.getBoolean("General",
			"Add tooltips to fluid containers to categorize the contained fluids.", false);
	boolean flag2 = Brewcraft.inst
			.getBoolean("General", "Add tooltips to fluid containers to display capacity.", false);

	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent event) {
		if (event.itemStack.getItem() == ItemBlock.getItemFromBlock(KegPlugin.kegs)) {
			event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tooltip.brewcraft.keg"));
			event.toolTip.add(StatCollector.translateToLocal("tooltip.brewcraft.keg.prefix") + " "
					+ EnumChatFormatting.BLUE + StatCollector.translateToLocal("tooltip.brewcraft.keg.basic") + " "
					+ EnumChatFormatting.GRAY + StatCollector.translateToLocal("tooltip.brewcraft.keg.postfix"));
			if (event.itemStack.getItemDamage() == 6 || event.itemStack.getItemDamage() == 8
					|| event.itemStack.getItemDamage() >= 9 && event.itemStack.getItemDamage() <= 13)
				event.toolTip.add(StatCollector.translateToLocal("tooltip.brewcraft.keg.prefix") + " "
						+ EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.brewcraft.keg.molten") + " "
						+ EnumChatFormatting.GRAY + StatCollector.translateToLocal("tooltip.brewcraft.keg.postfix"));
			if (event.itemStack.getItemDamage() == 7 || event.itemStack.getItemDamage() == 8
					|| event.itemStack.getItemDamage() == 14)
				event.toolTip.add(StatCollector.translateToLocal("tooltip.brewcraft.keg.prefix") + " "
						+ EnumChatFormatting.GREEN + StatCollector.translateToLocal("tooltip.brewcraft.keg.gaseous")
						+ " " + EnumChatFormatting.GRAY
						+ StatCollector.translateToLocal("tooltip.brewcraft.keg.postfix"));
			if (event.itemStack.getItemDamage() == 9 && ItemStackUtil.getOreWithName("blockSteel") == null
					|| event.itemStack.getItemDamage() == 10 && ItemStackUtil.getOreWithName("blockCopper") == null
					|| event.itemStack.getItemDamage() == 11 && ItemStackUtil.getOreWithName("blockSilver") == null
					|| event.itemStack.getItemDamage() == 12 && ItemStackUtil.getOreWithName("blockTungsten") == null
					|| event.itemStack.getItemDamage() == 13 && ItemStackUtil.getOreWithName("blockBrass") == null
					|| event.itemStack.getItemDamage() == 14 && ItemStackUtil.getOreWithName("blockRubber") == null)
				event.toolTip.add(EnumChatFormatting.RED
						+ StatCollector.translateToLocal("tooltip.brewcraft.keg.invalid"));
		}

		if (flag)
			if (FluidContainerRegistry.getFluidForFilledItem(event.itemStack) != null) {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(event.itemStack);
				if (!(fluid.getFluid().getTemperature() >= 1300) && !(fluid.getFluid().isGaseous()))
					event.toolTip.add(EnumChatFormatting.BLUE
							+ StatCollector.translateToLocal("tooltip.brewcraft.keg.basic"));
				if (fluid.getFluid().getTemperature() >= 1300)
					event.toolTip.add(EnumChatFormatting.RED
							+ StatCollector.translateToLocal("tooltip.brewcraft.keg.molten"));
				if (fluid.getFluid().isGaseous())
					event.toolTip.add(EnumChatFormatting.GREEN
							+ StatCollector.translateToLocal("tooltip.brewcraft.keg.gaseous"));
			}
		if (flag2)
			if (FluidContainerRegistry.isContainer(event.itemStack)) {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(event.itemStack);
				if (fluid != null && fluid.amount > 0) {
					event.toolTip.add(" ");
					event.toolTip.add(EnumChatFormatting.ITALIC
							+ StatCollector.translateToLocalFormatted("tooltip.brewcraft.capacity", fluid.amount));
					event.toolTip.add(EnumChatFormatting.ITALIC + fluid.getFluid().getLocalizedName());
				}
			}
	}
}
