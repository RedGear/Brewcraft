package redgear.brewcraft.blocks.sprayer;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;
import cpw.mods.fml.common.Loader;

public class GuiSprayer extends GuiBase<ContainerSprayer> {

	public static final ResourceLocation texture = new ResourceLocation("redgear_brewcraft:textures/gui/sprayer.png");

	public GuiSprayer(ContainerSprayer container) {
		super(container, texture);
		name = "container.sprayer";
	}

	@Override
	public void initGui() {
		super.initGui();
		TileEntitySprayer tile = myContainer.myTile;

		addElement(new ElementFluidTankWithGlass(this, 80, 14, tile.tank).setGauge(0));
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
