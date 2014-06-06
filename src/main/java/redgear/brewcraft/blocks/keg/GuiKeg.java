package redgear.brewcraft.blocks.keg;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;
import cpw.mods.fml.common.Loader;

public class GuiKeg extends GuiBase<ContainerKeg> {

	public static final ResourceLocation texture = new ResourceLocation("redgear_brewcraft:textures/gui/keg.png");

	public GuiKeg(ContainerKeg container) {
		super(container, texture);
		name = "container.keg";
	}

	@Override
	public void initGui() {
		super.initGui();
		TileEntityKeg tile = myContainer.myTile;
		
		addElement(new ElementFluidTankWithGlass(this, 80, 7, tile.tank).setGauge(0));
	}

	@Override
	protected void updateElements() {
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		if (!Loader.isModLoaded("NotEnoughItems") && mc.thePlayer.inventory.getItemStack() == null) {
			addTooltips(tooltip);
			drawTooltip(tooltip);
		}
	}

}
