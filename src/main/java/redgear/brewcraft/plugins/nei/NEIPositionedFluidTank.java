package redgear.brewcraft.plugins.nei;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.render.GuiBase;
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
}
