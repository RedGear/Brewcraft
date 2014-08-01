package redgear.brewcraft.blocks.sprayer;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import redgear.brewcraft.packet.ParticleHandler;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementButton;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;
import redgear.core.render.gui.element.ElementSimple;
import cpw.mods.fml.common.Loader;

public class GuiSprayer extends GuiBase<ContainerSprayer> {

	ElementSimple delayBack;

	ElementButton addDelay;
	ElementButton subtractDelay;
	public static final ResourceLocation texture = new ResourceLocation("redgear_brewcraft:textures/gui/sprayer.png");

	public GuiSprayer(ContainerSprayer container) {
		super(container, texture);
		name = "container.sprayer";
	}

	@Override
	public void initGui() {
		super.initGui();
		TileEntitySprayer tile = myContainer.myTile;

		addElement(new ElementFluidTankWithGlass(this, 71, 13, tile.tank).setGauge(0));
		this.addDelay = ((ElementButton) addElement(new ElementButton(this, 91, 22, "AddDelay", 177, 1, 177, 13, 177,
				25, 15, 10, "redgear_brewcraft:textures/gui/sprayer.png")));
		this.subtractDelay = ((ElementButton) addElement(new ElementButton(this, 91, 53, "SubtractDelay", 196, 1, 196,
				13, 196, 25, 15, 10, "redgear_brewcraft:textures/gui/sprayer.png")));
		this.delayBack = ((ElementSimple) addElement(new ElementSimple(this, 91, 35).setSize(15, 15).setTexture(
				"redgear_brewcraft:textures/gui/sprayerdelay.png", 15, 15)));
	}

	@Override
	protected void updateElements() {
		this.delayBack.setVisible(true);
		this.addDelay.setToolTip("container.sprayer.adddelay");
		this.subtractDelay.setToolTip("container.sprayer.subdelay");

		if (this.myContainer.myTile.delay == 30)
			this.addDelay.setDisabled();
		else if (this.myContainer.myTile.delay == 1)
			this.subtractDelay.setDisabled();
		else {
			this.addDelay.setActive();
			this.subtractDelay.setActive();
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		TileEntitySprayer tile = myContainer.myTile;
		
		fontRendererObj.drawString(StatCollector.translateToLocal("container.sprayer"), 8, 4, 0x404040);
		fontRendererObj.drawString(String.valueOf(tile.delay), 96 - ((String.valueOf(tile.delay).length() - 1) * 3),
				39, 0xF7F7F7);
		if (!Loader.isModLoaded("NotEnoughItems") && mc.thePlayer.inventory.getItemStack() == null) {
			addTooltips(tooltip);
			drawTooltip(tooltip);
		}
	}

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {
		TileEntitySprayer tile = myContainer.myTile;

		if (buttonName.equals("AddDelay") && tile.delay < 30) {
			ParticleHandler.sendGuiSprayerPacket(tile, ++tile.delay);
		} else if (buttonName.equals("SubtractDelay") && tile.delay > 1) {
			ParticleHandler.sendGuiSprayerPacket(tile, --tile.delay);
		}
	}
}
