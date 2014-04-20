package redgear.brewcraft.blocks;

import net.minecraft.util.ResourceLocation;
import redgear.cofh.gui.element.ElementDualScaled;
import redgear.cofh.gui.element.ElementFluidTank;
import redgear.core.render.GuiBase;

public class GuiBrewery extends GuiBase<ContainerBrewery>{
	
	public static final ResourceLocation texture = new ResourceLocation("redgear_brewcraft:textures/gui/brewery.png");
	ElementDualScaled work;
	
	public GuiBrewery(ContainerBrewery container) {
		super(container, texture);
		name = "Brewery";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		TileEntityBrewery tile = this.myContainer.myTile;
		final String overlay = "redgear_brewcraft:textures/gui/tankoverlay.png";
		
		addElement(new ElementFluidTank(this, 10, 13, tile.inputTank, overlay));
		addElement(new ElementFluidTank(this, 147, 13, tile.outputTank, overlay));
		
		work = (ElementDualScaled)addElement( new ElementDualScaled(this, 62, 30).setSize(12, 29).setTexture("redgear_brewcraft:textures/gui/brewoverlay.png", 24, 29));
	}
	
	@Override
	protected void updateElements() {
		TileEntityBrewery tile = this.myContainer.myTile;
		
		work.setQuantity(tile.getScaledWork(29));
	}

}
