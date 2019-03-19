package redgear.brewcraft.plugins.nei;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import net.minecraftforge.fluids.IFluidTank;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class NEIPositionedFluidTank extends ElementFluidTankWithGlass {

    public NEIPositionedFluidTank(GuiBase gui, int posX, int posY, IFluidTank tank) {
        super(gui, posX, posY, tank);
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
