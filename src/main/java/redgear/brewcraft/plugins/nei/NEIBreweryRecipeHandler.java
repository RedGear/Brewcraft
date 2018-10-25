package redgear.brewcraft.plugins.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import static net.minecraft.init.Items.potionitem;

import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.BrewingRecipeHandler.BrewingRecipe;
import codechicken.nei.recipe.BrewingRecipeHandler.CachedBrewingRecipe;
import codechicken.nei.recipe.BrewingRecipeHandler;
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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.blocks.brewery.TileEntityBrewery;
import redgear.brewcraft.blocks.brewery.BreweryInputTank;
import redgear.brewcraft.blocks.brewery.ContainerBrewery;
import redgear.brewcraft.blocks.brewery.GuiBrewery;
import redgear.brewcraft.plugins.item.ItemPlugin;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.brewcraft.utils.PotionRegistry;
import redgear.core.render.ContainerBase;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementDualScaled;
import redgear.core.render.gui.element.ElementFluidTank;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;
import redgear.core.util.SimpleItem;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.render.GuiBase;

public class NEIBreweryRecipeHandler extends TemplateRecipeHandler {
	
	@Override
	public String getRecipeName() {
		String name = StatCollector.translateToLocal("redgear.brewcraft.brewery");
		return "Brewery"; //ideally replace with language-independent StatCollector or similar
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation("redgear_brewcraft:textures/gui/brewery.png").toString();
	}
	
	//not ideal, but
	InventoryPlayer living = new InventoryPlayer(null); //dummy instance
	GuiBrewery gui = new GuiBrewery(new ContainerBrewery(living, new TileEntityBrewery())); //dummy instance
	
	/**
     * This Recipe Handler runs on this internal class
     * Fill the recipe array with subclasses of this to make transforming the different types of recipes out there into a nice format for NEI a much easier job.
     */
	public class CachedBreweryRecipe extends CachedRecipe {
		//Typed variables from brewcraft to add to CachedBreweryRecipe
		public BreweryRecipe recipe;
		
		/**
         * Returns a list of NEIPositionedFluidTank
         *
         */
		public List<NEIPositionedFluidTank> getFluidTanks() {
            List<NEIPositionedFluidTank> tankList = new ArrayList<NEIPositionedFluidTank>();
            if ((recipe.input != null) && (recipe.output != null)) {
				AdvFluidTank inputAdvTank = new AdvFluidTank(recipe.input, FluidContainerRegistry.BUCKET_VOLUME * 12); //12000mb
				AdvFluidTank outputAdvTank = new AdvFluidTank(recipe.output, FluidContainerRegistry.BUCKET_VOLUME * 12);
				
				tankList.add((NEIPositionedFluidTank) new NEIPositionedFluidTank(gui, 21, 2, inputAdvTank).setGauge(3000));
				tankList.add((NEIPositionedFluidTank) new NEIPositionedFluidTank(gui, 129, 2, outputAdvTank).setGauge(3000));
				
            }	
            return tankList;
        }
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
        	if (recipe.item != null)
            	inputs.add(new PositionedStack(recipe.item.getStack(), 74, 24));
        		//FMLLog.log(Level.INFO, "Stack Item  " + recipe.item.getItemId() + " " + recipe.item.getMeta());
            return inputs;
        }
		
		@Override
		public PositionedStack getResult() {
			return null; //no item output, only fluid
		}	
	}
	
	@Override
    public void loadTransferRects() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(56, 19, 12, 29), "brewcraft.brewery"));        
        
    }

	RecipeRegistry recipes = PotionPlugin.getRecipeList();
	PotionRegistry potions = PotionPlugin.getPotionList();
	Object[] recipearray = recipes.getBreweryRecipeSet().toArray();
	
	
	/**
     * Loads usage recipes for ItemStack
     */
	@Override
    public void loadUsageRecipes(ItemStack ingredient) {
		if ((ingredient.getItem() instanceof ItemPotion) || (ingredient.getItem() instanceof MetaItemPotion)) {
			
			if (ingredient.getItem() instanceof MetaItemPotion) {				
				MetaItemPotion instancedPotion = (MetaItemPotion) ingredient.getItem(); //ItemStack to instance of potion
				//SubItemPotion readablePotion = instancedPotion.getMetaItem((instancedPotion).getDamage(ingredient)); //getting potion with readable properties (strength, duration, effect)
				//FMLLog.log(Level.INFO, "Finally, potion details from ingredient. Can you get the matching fluid?" + readablePotion.potion.getName());
				
				//for (int i = 1; i < recipearray.length; i++) {
					//FMLLog.log(Level.INFO, "Does this match " + ((BreweryRecipe) recipearray[i]).input.getLocalizedName());
					
				//}
				//FMLLog.log(Level.INFO,  "was it the fluidcontainerregistry all along " + FluidContainerRegistry.getFluidForFilledItem(ingredient).getLocalizedName());
				
				//((MetaItemPotion) ingredient.getItem()).getMetaItem(ingredient.getItem().getDamage)

				//Fluid theone = ((BreweryRecipe)recipearray[0]).input.getFluid();
				
				/* gets name, but what about fluid */
				//String thepotion = ((FluidPotion)theone).getLocalizedName();
				
				loadCraftingRecipes(FluidContainerRegistry.getFluidForFilledItem(ingredient));
				
				
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
	            } else if (recipe.item.item.equals(ingredient.getItem())){ //recipe.item.item required for damage values
	            	FMLLog.log(Level.INFO, "recipe itemmmmm: " + recipe.item.item.getUnlocalizedName());
	            	//FMLLog.log(Level.INFO, arecipe.toString() + "So there is a recipe");
	                arecipes.add(arecipe);
	            }
	            //}
	        }
	        FMLLog.log(Level.INFO, "Item equivalency: " + (ItemPlugin.steelScales).isItemEqual(ItemPlugin.holyDust, false));
		}
	}
	
	/**
	 * Loads crafting recipes for FluidStack
	 */
	public void loadCraftingRecipes(FluidStack fluid) {
		//FMLLog.log(Level.INFO, "Do you go here for fluids");
		for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) {
            if (recipe.output.isFluidEqual(fluid)) {
                this.arecipes.add(new CachedBreweryRecipe(recipe));
            }
        }
    }
	
	
	public void loadUsageRecipes(FluidStack fluid) {
		for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) {
            if (recipe.input.isFluidEqual(fluid)) {
                this.arecipes.add(new CachedBreweryRecipe(recipe));
            }
        }
    }
	
	/*
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if ((result.getItem() instanceof ItemPotion) || (result.getItem() instanceof MetaItemPotion)) {
			if (result.getItem() instanceof MetaItemPotion) {
				for (int i = 1; i < recipearray.length; i++) {
					//FMLLog.log(Level.INFO, "Does this result match " + ((BreweryRecipe) recipearray[i]).input.getLocalizedName());
					
				}
				// Possibly missing some additional type/validation checks, not sure really how to do those
				//FluidStack fluidForCrafting = FluidContainerRegistry.getFluidForFilledItem(result);
				//this.loadCraftingRecipes((FluidStack) fluidForCrafting[0]);
			}
		}
	}
	*/
	/**
	 * Getting crafting recipes for brewcraft potions
	 */
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		//FMLLog.log(Level.INFO, "Trying to get that liquid recipe");
		
		if ((outputId.equals("item")) && (results.length == 1) && ((results[0] instanceof ItemStack))) { //all this instanceof and type casting would do people's heads in
			if (((ItemStack) results[0]).getItem() instanceof MetaItemPotion) {
				FluidStack resultFluid = FluidContainerRegistry.getFluidForFilledItem(((ItemStack) results[0]));
				
				for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) {
					if (recipe.output.isFluidEqual(resultFluid)) {
						this.arecipes.add(new CachedBreweryRecipe(recipe));
					}
				}
			}
				
			FMLLog.log(Level.INFO, "What is this outputID?" + outputId);
			FMLLog.log(Level.INFO, "results length : class " + results.length + results[0].getClass());
		} else if (outputId.equals("liquid") && results[0] instanceof FluidStack && ((FluidStack) results[0]).getFluid() != null) {
            this.loadCraftingRecipes((FluidStack) results[0]); //pass to loadCraftingRecipes, FluidStack version
		} else if (outputId.equals("brewcraft.brewery") && getClass() == NEIBreweryRecipeHandler.class)//don't want subclasses getting a hold of this
            for (BreweryRecipe recipe : recipes.getBreweryRecipeSet())
                arecipes.add(new CachedBreweryRecipe(recipe));
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
		
		for (CachedRecipe crecipe : arecipes) {
			for (NEIPositionedFluidTank tank : (((CachedBreweryRecipe) crecipe).getFluidTanks())) {
				tank.draw();
			}
		}
		
        drawExtras(recipe);
	}
	/**
	 * Including Brew Overlay (the bubbles if you're wondering)
	 */
	public void drawExtras(int recipe) {
		String overlaylocation = new ResourceLocation("redgear_brewcraft:textures/gui/brewoverlay.png").toString();
		changeTexture(overlaylocation);
		/*
		ElementDualScaled<TileEntityBrewery, ContainerBrewery, GuiBrewery> work = new ElementDualScaled<TileEntityBrewery, ContainerBrewery, GuiBrewery>(gui, 62, 30);
		work.setSize(12, 29)
				.setTexture("redgear_brewcraft:textures/gui/brewoverlay.png", 24, 29);
		addElement(work);
		*/
		//drawSizedTexturedModalRect(8, 22, 0, 0, 12, 29, 12.0, 29.0);
		GuiBase overlayGui = new GuiBase(null);
		overlayGui.drawSizedTexturedModalRect(56, 19, 0, 0, 12, 29, (float)24.0, (float)29.0);
	}
	
	/*
	@Override
    public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe) {
        if (keyCode == NEIClientConfig.getKeyBinding("gui.recipe")) {
            if (this.transferFluidTank(gui, recipe, false)) {
                return true;
            }
        } else if (keyCode == NEIClientConfig.getKeyBinding("gui.usage")) {
            if (this.transferFluidTank(gui, recipe, true)) {
                return true;
            }
        }
        return super.keyTyped(gui, keyChar, keyCode, recipe);
    }
    
	*/
    @Override
    public boolean mouseClicked(GuiRecipe gui, int button, int recipe) {
    	//FMLLog.log(Level.INFO, "clickedtank");
    	if (button == 0) {
            if (this.clickedFluidTank(gui, recipe, false)) {
            	FMLLog.log(Level.INFO, "tank left clicked recipes");
                return true;
            }
        } else if (button == 1) {
            if (this.clickedFluidTank(gui, recipe, true)) {
            	FMLLog.log(Level.INFO, "tank right clicked usage");
                return true;
            }
        }
    	//FluidStack metatest = positionedFluidTankToFluidStack(recipe, true);
    	
        return super.mouseClicked(gui, button, recipe);
    }
	
    protected boolean clickedFluidTank(GuiRecipe guiRecipe, int recipe, boolean usage) {
        CachedBreweryRecipe crecipe = (CachedBreweryRecipe) this.arecipes.get(recipe);
        Point mousepos = GuiDraw.getMousePosition();
        Point offset = guiRecipe.getRecipePosition(recipe);
        Point relMouse = new Point(mousepos.x - (guiRecipe.width - 176) / 2 - offset.x, mousepos.y - (guiRecipe.height - 166) / 2 - offset.y);
        if (crecipe.getFluidTanks() != null) {
            for (NEIPositionedFluidTank tank : crecipe.getFluidTanks()) {
                if (tank.intersectsWith(relMouse.x,relMouse.y)) {
                    return tank.transfer(usage);
                }
            }
        }
        
        return false;
    }
    
    
    /**
     * 
     * @param tank	the NEIPositionedFluidTank
     * @param usage	whether user requested for "Usage" or "Recipe" of tank
     * @return
     */
    /*
    protected FluidStack positionedFluidTankToFluidStack(int recipeIndex, boolean usage) { //int recipe from codechicken.nei should be named recipeIndex
    	CachedBreweryRecipe crecipe = (CachedBreweryRecipe) this.arecipes.get(recipeIndex); //get cached recipe from arecipes
    	
    	Point mousepos = GuiDraw.getMousePosition();
        Point offset = guiRecipe.getRecipePosition(recipe);
        Point relMouse = new Point(mousepos.x - (guiRecipe.width - 176) / 2 - offset.x, mousepos.y - (guiRecipe.height - 166) / 2 - offset.y);
    	
    	
    	for (NEIPositionedFluidTank tank : crecipe.getFluidTanks()) {
    		if (tank)
    	}
    	NEIPositionedFluidTank tankToConvert = (NEIPositionedFluidTank) crecipe.posInputTank;
    	FluidStack convertedFluidStack = tankToConvert.getPositionedTankFluidStack();
    	
    	FluidContainerData [] fluidregistry = FluidContainerRegistry.getRegisteredFluidContainerData();
    	
    	//FMLLog.log(Level.INFO, fluidregistry[0].toString());
    	
    	for (int i = 0; i < fluidregistry.length; i++) {
    		if (convertedFluidStack.isFluidEqual(fluidregistry[i])) {
    			FMLLog.log(Level.INFO, fluidregistry[i]
    		}
    	}
    	
    	
    	FMLLog.log(Level.INFO, "GET THE FLUID " + convertedFluidStack.getLocalizedName());
    	
    	for (int i = 0; i < fluidregistry.length; i++) {
    		if(convertedFluidStack == fluidregistry[i].fluid) {
    			FMLLog.log(Level.INFO, "Fluid and ID " + i + fluidregistry[i].fluid.getLocalizedName());
    		}
    	}
    	
    	return null;
    }
    */
    
	@Override
    public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> currenttip, int recipe) {
        super.handleTooltip(guiRecipe, currenttip, recipe);
        CachedBreweryRecipe crecipe = (CachedBreweryRecipe) this.arecipes.get(recipe);
        if (GuiContainerManager.shouldShowTooltip(guiRecipe)) {
            Point mouse = GuiDraw.getMousePosition();
            Point offset = guiRecipe.getRecipePosition(recipe);
            Point relMouse = new Point(mouse.x - (guiRecipe.width - 176) / 2 - offset.x, mouse.y - (guiRecipe.height - 166) / 2 - offset.y);
            
            currenttip = this.provideTooltip(guiRecipe, currenttip, crecipe, relMouse);
        }
        return currenttip;
    }
    /*
    @Override
    public List<String> handleItemTooltip(GuiRecipe guiRecipe, ItemStack itemStack, List<String> currenttip, int recipe) {
        super.handleItemTooltip(guiRecipe, itemStack, currenttip, recipe);
        CachedBreweryRecipe crecipe = (CachedBreweryRecipe) this.arecipes.get(recipe);
        Point mouse = GuiDraw.getMousePosition();
        Point offset = guiRecipe.getRecipePosition(recipe);
        Point relMouse = new Point(mouse.x - (guiRecipe.width - 176) / 2 - offset.x, mouse.y - (guiRecipe.height - 166) / 2 - offset.y);
        
        currenttip = this.provideItemTooltip(guiRecipe, itemStack, currenttip, crecipe, relMouse);
        return currenttip;
    }
    */
    /**
     * provides tooltips for fluid tanks in paticular
     * @param guiRecipe
     * @param currenttip
     * @param crecipe
     * @param relMouse
     * @return
     */
    public List<String> provideTooltip(GuiRecipe guiRecipe, List<String> currenttip, CachedBreweryRecipe crecipe, Point relMouse) {
        if (crecipe.getFluidTanks() != null) {
            for (NEIPositionedFluidTank tank : crecipe.getFluidTanks()) {
                if (tank.intersectsWith(relMouse.x,relMouse.y)) {
                    tank.addTooltip(currenttip);
                }
            }
        }
        return currenttip;
    }
    
    /*
    public List<String> provideItemTooltip(GuiRecipe guiRecipe, ItemStack itemStack, List<String> currenttip, CachedBaseRecipe crecipe, Point relMouse) {
        for (PositionedStack stack : crecipe.getIngredients()) {
            if (stack instanceof PositionedStackAdv && ((PositionedStackAdv) stack).getRect().contains(relMouse)) {
                currenttip = ((PositionedStackAdv) stack).handleTooltip(guiRecipe, currenttip);
            }
        }
        for (PositionedStack stack : crecipe.getOtherStacks()) {
            if (stack instanceof PositionedStackAdv && ((PositionedStackAdv) stack).getRect().contains(relMouse)) {
                currenttip = ((PositionedStackAdv) stack).handleTooltip(guiRecipe, currenttip);
            }
        }
        PositionedStack stack = crecipe.getResult();
        if (stack instanceof PositionedStackAdv && ((PositionedStackAdv) stack).getRect().contains(relMouse)) {
            currenttip = ((PositionedStackAdv) stack).handleTooltip(guiRecipe, currenttip);
        }
        return currenttip;
    }
    
    */
}
