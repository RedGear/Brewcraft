package redgear.brewcraft.blocks.brewery;

import cpw.mods.fml.common.Loader;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementDualScaled;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class GuiBrewery extends GuiBase<ContainerBrewery> {

	public static final ResourceLocation texture = new ResourceLocation("redgear_brewcraft:textures/gui/brewery.png");
	ElementDualScaled work;

	public GuiBrewery(ContainerBrewery container) {
		super(container, texture);
		name = "container.brewery";
	}

	@Override
	public void initGui() {
		super.initGui();
		TileEntityBrewery tile = myContainer.myTile;

		addElement(new ElementFluidTankWithGlass(this, 10, 13, tile.inputTank).setGauge(0));
		addElement(new ElementFluidTankWithGlass(this, 147, 13, tile.outputTank).setGauge(0));

		work = (ElementDualScaled) addElement(new ElementDualScaled(this, 62, 30).setSize(12, 29).setTexture(
				"redgear_brewcraft:textures/gui/brewoverlay.png", 24, 29));
	}

	@Override
	protected void updateElements() {
		TileEntityBrewery tile = myContainer.myTile;

		work.setQuantity(tile.getScaledWork(29));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		fontRendererObj.drawString(
				StatCollector.translateToLocal(name),
				this.xSize / 2
						- this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.brewery")) / 2
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
