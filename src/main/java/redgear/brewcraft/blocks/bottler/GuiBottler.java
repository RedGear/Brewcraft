package redgear.brewcraft.blocks.bottler;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;
import cpw.mods.fml.common.Loader;

public class GuiBottler extends GuiBase<ContainerBottler>{
	
	public static final ResourceLocation texture = new ResourceLocation("redgear_brewcraft:textures/gui/bottler.png");

	public GuiBottler(ContainerBottler container) {
		super(container, texture);
		name = "container.bottler";
	}

	
	@Override
	public void initGui() {
		super.initGui();
		TileEntityBottler tile = myContainer.myTile;

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
						- this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.bottler")) / 2
						- 1, 4, 0x404040);
		if (drawInventory) {
			fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), this.xSize / 2
					- this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.inventory")) / 2
					- 1, ySize - 96 + 3, 0x404040);
		}
		if (!Loader.isModLoaded("NotEnoughItems") && mc.thePlayer.inventory.getItemStack() == null) {
			addTooltips(tooltip);
			drawTooltip(tooltip);
		}
	}
}
