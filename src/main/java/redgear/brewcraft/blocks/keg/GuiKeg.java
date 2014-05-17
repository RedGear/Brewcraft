package redgear.brewcraft.blocks.keg;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;
import cpw.mods.fml.common.Loader;

public class GuiKeg extends GuiBase<ContainerKeg> {

	public static final ResourceLocation texture = new ResourceLocation("redgear_brewcraft:textures/gui/sprayer.png");

	public GuiKeg(ContainerKeg container) {
		super(container, texture);
		name = "container.barrel";
	}

	@Override
	public void initGui() {
		super.initGui();
		TileEntityKeg tile = myContainer.myTile;
		
		addElement(new ElementFluidTankWithGlass(this, 80, 13, tile.tank).setGauge(0));
	}

	@Override
	protected void updateElements() {
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		fontRendererObj.drawString(
				StatCollector.translateToLocal(name),
				this.xSize / 2
						- this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.barrel")) / 2,
				3, 0x404040);
		if (drawInventory) {
			fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), this.xSize / 2
					- this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.inventory")) / 2,
					ySize - 96 + 4, 0x404040);
		}
		if (!Loader.isModLoaded("NotEnoughItems") && mc.thePlayer.inventory.getItemStack() == null) {
			addTooltips(tooltip);
			drawTooltip(tooltip);
		}
	}

}
