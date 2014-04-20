package redgear.brewcraft.blocks;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementDualScaled;
import redgear.core.render.gui.element.ElementFluidTank;

public class GuiBrewery extends GuiBase<ContainerBrewery> {

	public static final ResourceLocation texture = new ResourceLocation("redgear_brewcraft:textures/gui/brewery.png");
	ElementDualScaled work;

	public GuiBrewery(ContainerBrewery container) {
		super(container, texture);
		name = "Brewery";
	}

	@Override
	public void initGui() {
		super.initGui();
		TileEntityBrewery tile = myContainer.myTile;

		addElement(new ElementFluidTank(this, 10, 13, tile.inputTank).setGauge(1));
		addElement(new ElementFluidTank(this, 147, 13, tile.outputTank).setGauge(1));

		work = (ElementDualScaled) addElement(new ElementDualScaled(this, 62, 30).setSize(12, 29).setTexture(
				"redgear_brewcraft:textures/gui/brewoverlay.png", 24, 29));
	}

	@Override
	protected void updateElements() {
		TileEntityBrewery tile = myContainer.myTile;

		work.setQuantity(tile.getScaledWork(29));
	}

}
