package redgear.brewcraft.plugins.nei;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.render.GuiBase;
import redgear.core.render.RenderHelper;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class NEIPositionedFluidTank extends ElementFluidTankWithGlass {
	
	AdvFluidTank advTank;

	public NEIPositionedFluidTank(GuiBase gui, int posX, int posY, IFluidTank tank) {
		super(gui, posX, posY, tank);
		this.advTank = (AdvFluidTank) tank;
	}
	
	public FluidStack getPositionedTankFluidStack() {
		return this.advTank.getFluid();
	}
	
	// not quite sure what all this does (taken from NEI Integration)
	public boolean transfer(boolean usage) {
        if (this.tank.getFluid() != null && this.tank.getFluid().amount > 0) {
            if (usage) {
                if (!GuiUsageRecipe.openRecipeGui("liquid", new Object[] { this.tank.getFluid() })) {
                    return false;
                }
            } else {
                if (!GuiCraftingRecipe.openRecipeGui("liquid", new Object[] { this.tank.getFluid() })) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
	
	@Override
	//needed because super class was drawing wrong fluid, still drawing wrong fluid
	public void draw() {
		if (!visible) {
			return;
		}
		
		int amount = tank.getFluidAmount() * sizeY / tank.getCapacity();
		
		RenderHelper.bindTexture(texture);
		drawTexturedModalRect(posX, posY, 0, 1, sizeX, sizeY);
		gui.drawFluid(posX, posY + sizeY - amount, tank.getFluid(), sizeX, amount);
		RenderHelper.bindTexture(texture);
		drawTexturedModalRect(posX, posY, 17, 1, sizeX, sizeY);
		drawTexturedModalRect(posX, posY, 33 + gaugeType * 16, 1, sizeX - 1, sizeY);
	}
	
}
