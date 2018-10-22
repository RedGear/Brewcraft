package redgear.brewcraft.plugins.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import static net.minecraft.init.Items.potionitem;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.GuiRecipe;
import cpw.mods.fml.common.FMLLog;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.blocks.brewery.TileEntityBrewery;
import redgear.brewcraft.blocks.brewery.ContainerBrewery;
import redgear.brewcraft.blocks.brewery.GuiBrewery;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.brewcraft.utils.PotionRegistry;
import redgear.core.render.gui.element.ElementFluidTank;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;
import redgear.core.util.SimpleItem;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.render.GuiBase;

public class NEIBreweryRecipeHandler extends TemplateRecipeHandler {
	
	@Override
	public String getRecipeName() {
		String name = StatCollector.translateToLocal("redgear.brewcraft.brewery");
    	//FMLLog.log(Level.INFO, "You got the Brewcraft Stuff " + name);
		return "Brewery"; //ideally replace with language-independent StatCollector or similar
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation("redgear_brewcraft:textures/gui/brewery.png").toString();
	}
	
	/**
     * This Recipe Handler runs on this internal class
     * Fill the recipe array with subclasses of this to make transforming the different types of recipes out there into a nice format for NEI a much easier job.
     */
	public class CachedBreweryRecipe extends CachedRecipe {
		
		public BreweryRecipe recipe;

		public CachedBreweryRecipe(BreweryRecipe recipe) {
			this.recipe = recipe;
		}
		
		/**
         * The ingredients required to produce the result
         * Use this if you have more than one ingredient
         *
         * @return A list of positioned ingredient items.
         */
        @Override
        public List<PositionedStack> getIngredients() {
        	ArrayList<PositionedStack> inputs = new ArrayList<PositionedStack>();
            if (recipe.input != null)
            	//FMLLog.log(Level.INFO, "Input potion fluid?");
            	inputs.add(new PositionedStack(new ItemStack(recipe.input.getFluid().getBlock()), 21, 13));
            if (recipe.item != null)
            	inputs.add(new PositionedStack(new ItemStack(recipe.item.getItem()), 74, 24));
            return inputs;
        }
		
		@Override
		public PositionedStack getResult() {
			return new PositionedStack(new ItemStack(recipe.output.getFluid().getBlock()), 129, 13);
		}	
	}
	
	public RecipeRegistry recipes = PotionPlugin.getRecipeList();
	public PotionRegistry potions = PotionPlugin.getPotionList();
	Object[] recipearray = recipes.getBreweryRecipeSet().toArray();
	
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(46, 34, 24, 19), "fuel"));
        transferRects.add(new RecipeTransferRect(new Rectangle(104, 34, 24, 19), "brewcraft.brewery"));
    }

	/**
     * In this function you need to fill up the empty recipe array with recipes
     * The default passes it to a cleaner handler if inputId is an item
     *
     * @param inputId     A String identifier representing the type of ingredients used. Eg. {"item", "fuel"}
     * @param ingredients Objects representing the ingredients that matching recipes must contain.
     */
	@Override
    public void loadUsageRecipes(ItemStack ingredient) {
		//FMLLog.log(Level.INFO, "Yes! You got USAGE recipes loading (ingredient). Wait where are they?");
		//FMLLog.log(Level.INFO, "Ingredient: " + ingredient.getItem().getClass());
		
		//FMLLog.log(Level.INFO, "Get Blocks" + PotionPlugin.fluidAwkward.getFluid().getBlock().getLocalizedName());
		
		if ((ingredient.getItem() instanceof ItemPotion) || (ingredient.getItem() instanceof MetaItemPotion)) {
			
			FMLLog.log(Level.INFO, "What? So Fluids Exist?   " + ingredient.getItem().getItemStackDisplayName(ingredient).toString());
			//FMLLog.log(Level.INFO, ingredient.getItem().getUnlocalizedName());
			for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) {
				//FMLLog.log(Level.INFO, "YOU " + recipe.input.getFluid().getName());
				//FMLLog.log(Level.INFO, recipe.input.getFluid().getLocalizedName(recipe.input) + " : " + ingredient.getDisplayName());
				//FMLLog.log(Level.INFO, recipe.input.getFluid().getLocalizedName(recipe.input) + " : " + ingredient.getDisplayName());
				//FMLLog.log(Level.INFO, recipe.input.getFluid().getID() + " : " + recipe.input.getFluid().getLocalizedName(recipe.input));
				if (ingredient.getItemDamage() == recipe.input.getFluid().getID()) { //not really correct, but getting this to work
					FMLLog.log(Level.INFO, ingredient.getItemDamage() + " : " + recipe.input.getFluid().getID());
					FMLLog.log(Level.INFO, "EUREKA!!!!!!!!! YOU GOT: " + recipe.input.getFluid().getLocalizedName(recipe.input));
				}
				if (ingredient.getItem() instanceof MetaItemPotion) {
					//ItemStack theone = new ItemStack(PotionPlugin.potions);
					
					Fluid theone = ((BreweryRecipe)recipearray[0]).input.getFluid();
					
					/* gets name, but what about fluid */
					String thepotion = ((FluidPotion)theone).getLocalizedName();
					
				}
			}		
		} else {		
	        for (BreweryRecipe recipe : this.recipes.getBreweryRecipeSet()) {
	        	//FMLLog.log(Level.INFO, "Recipe Item :" + new ItemStack(recipe.item.getItem()).toString());
	        	//FMLLog.log(Level.INFO, "Held Item   :" + ingredient.toString());
	        	
	            //if (NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(recipe.item.getItem()), ingredient)) {
	            	CachedBreweryRecipe arecipe = new CachedBreweryRecipe(recipe);
	            	//FMLLog.log(Level.INFO, "CHECKCKCKCHCK");
	        		if (recipe.input.isFluidEqual(ingredient)) { //may not work, fluid not equal to potion
		                //FMLLog.log(Level.INFO, arecipe.toString() + "So there is a recipe (fluid)");
		                arecipes.add(arecipe);
		            } else if (recipe.item.isItemEqual(new SimpleItem(ingredient.getItem()), true)){
		            	//FMLLog.log(Level.INFO, arecipe.toString() + "So there is a recipe");
		                arecipes.add(arecipe);
		            }
	            //}
	        }
		}
	}
	
	public void loadUsageRecipes(ItemPotion ingredient) {
		FMLLog.log(Level.INFO, "DO YOU EVEN");
	}
	
	public void loadUsageRecipes(FluidStack ingredient) {
		FMLLog.log(Level.INFO, "What? So Fluids Exist?$%)(#$&^");
	}
	
	@Override
    public String getOverlayIdentifier() {
        return "brewcraft.brewery";
    }
	
	@Override
	public void drawForeground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_LIGHTING);
        changeTexture(getGuiTexture());
        drawExtras(recipe);
		
		//this.drawProgressBar(153, 2, 176, 60, 19, 79, 50, 3);
		//GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 65);
		FMLLog.log(Level.INFO, "Draw, mister!");
		
		//GuiBrewery gui = new GuiBrewery();
		
		
		FluidStack theone = ((BreweryRecipe)recipearray[0]).input;

		AdvFluidTank inputadvtank = new AdvFluidTank(theone, 3000);
		
		InventoryPlayer living = new InventoryPlayer(null);
		GuiBrewery gui = new GuiBrewery(new ContainerBrewery(living, new TileEntityBrewery()));
		ElementFluidTank setinput = new NEIFluidTank(gui, 26, 13, inputadvtank).setGauge(3000);
		//FMLLog.log(Level.INFO, "cmon!");
		//ElementFluidTank setoutput = new ElementFluidTankWithGlass(gui, 134, 13, inputadvtank).setGauge(3000);
		setinput.draw();
	}
	
	
	
	
	public boolean PositionedFluidTank (GuiRecipe gui, BreweryRecipe recipe) {
		//"copyhack" for now from TE
		int minX1 = 153;
		int maxX1 = 169;
		int minY1 = 19;
		int maxY1 = 79;
		int yOffset = 65;
		Point mousepos = GuiDraw.getMousePosition();
		FluidStack fluid = null;

		//if ((mousepos.x >= minX1 + gui.guiLeft) && (mousepos.x < maxX1 + gui.guiLeft) && (mousepos.y >= minY1 + gui.guiTop)
		//		&& (mousepos.y < maxY1 + gui.guiTop) && (this.arecipe[0] == recipe)) {
			fluid = ((CachedBreweryRecipe) arecipes.get(0)).recipe.input;
		//} else if ((mousepos.x >= minX1 + gui.guiLeft) && (mousepos.x < maxX1 + gui.guiLeft) && (mousepos.y >= minY1 + gui.guiTop + yOffset)
		//		&& (mousepos.y < maxY1 + gui.guiTop + yOffset) && (this.arecipe[1] == recipe)) {
		//	fluid = ((CachedBreweryRecipe) arecipes.get(0)).recipe.input;
		//}

		if ((fluid != null) && (fluid.amount > 0) ) {
			return true;
		}
		return false;
	}
	
	
	/*
	
	int arecipe[] = { -1, -1 };
	
	public void drawFluid(int recipe, boolean increase) {

		int recipeIndex = 0;

		if (arecipe[0] == -1) {
			arecipe[0] = recipe;
		} else if (arecipe[1] == -1 && arecipe[0] != recipe) {
			arecipe[1] = recipe;
		}
		if (arecipe[0] != recipe && arecipe[1] != recipe) {
			resetCounters();
			drawFluid(recipe, increase);
			return;
		}
		if (arecipe[1] == recipe) {
			recipeIndex = 1;
		}
		drawTexturedModalRect(147, 2, 32, 96, 18, scaleFluid + 2);

		int fluid = getScaledFluid(fluidAmount[recipeIndex]);

		if (increase) {
			drawFluidRect(148, 3 + scaleFluid - fluid, ((NEIRecipeBase) arecipes.get(recipe)).fluid, 16, fluid);
		} else {
			drawFluidRect(148, 3 + fluid, ((NEIRecipeBase) arecipes.get(recipe)).fluid, 16, scaleFluid - fluid);
		}

		if (cycleticks % 20 == 0 && cycleticks != lastCycle[recipeIndex]) {
			if (fluidAmount[recipeIndex] == maxFluid) {
				fluidAmount[recipeIndex] = 0;
			}
			fluidAmount[recipeIndex] += ((NEIRecipeBase) arecipes.get(recipe)).fluid.amount;

			if (fluidAmount[recipeIndex] > maxFluid) {
				fluidAmount[recipeIndex] = maxFluid;
			}
		}
		drawTexturedModalRect(148, 2, 80, 96, 18, scaleFluid + 2);
	}
	
	*/
}
