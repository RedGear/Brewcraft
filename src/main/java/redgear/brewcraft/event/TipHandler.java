package redgear.brewcraft.event;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.brewcraft.plugins.common.KegPlugin;
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

	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent event) {
		if (event.itemStack.getItem() == ItemBlock.getItemFromBlock(KegPlugin.kegs)) {
			event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tooltip.keg"));
			event.toolTip.add(StatCollector.translateToLocal("tooltip.keg.prefix") + " " + EnumChatFormatting.BLUE
					+ StatCollector.translateToLocal("tooltip.keg.basic") + " " + EnumChatFormatting.GRAY
					+ StatCollector.translateToLocal("tooltip.keg.postfix"));
			if (event.itemStack.getItemDamage() == 6 || event.itemStack.getItemDamage() == 8)
				event.toolTip.add(StatCollector.translateToLocal("tooltip.keg.prefix") + " " + EnumChatFormatting.RED
						+ StatCollector.translateToLocal("tooltip.keg.molten") + " " + EnumChatFormatting.GRAY
						+ StatCollector.translateToLocal("tooltip.keg.postfix"));
			if (event.itemStack.getItemDamage() == 7 || event.itemStack.getItemDamage() == 8)
				event.toolTip.add(StatCollector.translateToLocal("tooltip.keg.prefix") + " " + EnumChatFormatting.GREEN
						+ StatCollector.translateToLocal("tooltip.keg.gaseous") + " " + EnumChatFormatting.GRAY
						+ StatCollector.translateToLocal("tooltip.keg.postfix"));
		}
		if (event.itemStack.getItem() == IngredientPlugin.ingredients && event.itemStack.getItemDamage() != 7
				|| event.itemStack.getItem() == IngredientPlugin.hearts
				|| event.itemStack.getItem() == IngredientPlugin.tears)
			event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tooltip.ingredient"));
		if (event.itemStack.getItem() == IngredientPlugin.hearts)
			event.toolTip.add(StatCollector.translateToLocal("tooltip.food"));
		if (event.itemStack.getItem() == IngredientPlugin.tears && event.itemStack.getItemDamage() == 0)
			event.toolTip.add(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("tooltip.tear.nether"));
		if (event.itemStack.getItem() == IngredientPlugin.tears && event.itemStack.getItemDamage() == 1)
			event.toolTip.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("tooltip.tear.overworld"));

		if (flag)
			if (FluidContainerRegistry.getFluidForFilledItem(event.itemStack) != null) {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(event.itemStack);
				if (!(fluid.getFluid().getTemperature() >= 1300) && !(fluid.getFluid().isGaseous()))
					event.toolTip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("tooltip.keg.basic"));
				if (fluid.getFluid().getTemperature() >= 1300)
					event.toolTip.add(EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.keg.molten"));
				if (fluid.getFluid().isGaseous())
					event.toolTip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("tooltip.keg.gaseous"));
			}
	}

}
