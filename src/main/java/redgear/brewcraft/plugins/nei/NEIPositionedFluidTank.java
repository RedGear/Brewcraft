package redgear.brewcraft.plugins.nei;

import net.minecraftforge.fluids.IFluidTank;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class NEIPositionedFluidTank extends ElementFluidTankWithGlass {

	public NEIPositionedFluidTank(GuiBase gui, int posX, int posY, IFluidTank tank) {
		super(gui, posX, posY, tank);
	}
	
}
