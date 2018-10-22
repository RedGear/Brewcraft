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
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.blocks.brewery.TileEntityBrewery;
import redgear.brewcraft.blocks.brewery.BreweryInputTank;
import redgear.brewcraft.blocks.brewery.ContainerBrewery;
import redgear.brewcraft.blocks.brewery.GuiBrewery;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.potions.SubItemPotion;
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
		//Typed variables from brewcraft to add to CachedBreweryRecipe
		public BreweryRecipe recipe;
		public NEIPositionedFluidTank posInputTank;
		public NEIPositionedFluidTank posOutputTank;

		public CachedBreweryRecipe(BreweryRecipe recipe) {
			this.recipe = recipe;
			if (recipe.input != null) {
				AdvFluidTank inputAdvTank = new AdvFluidTank(recipe.input, FluidContainerRegistry.BUCKET_VOLUME * 12); //12000mb
				AdvFluidTank outputAdvTank = new AdvFluidTank(recipe.output, FluidContainerRegistry.BUCKET_VOLUME * 12);
				
				InventoryPlayer living = new InventoryPlayer(null); //dummy instance
				GuiBrewery gui = new GuiBrewery(new ContainerBrewery(living, new TileEntityBrewery())); //dummy instance
				
				this.posInputTank = (NEIPositionedFluidTank) new NEIPositionedFluidTank(gui, 21, 2, inputAdvTank).setGauge(3000);
				this.posOutputTank = (NEIPositionedFluidTank) new NEIPositionedFluidTank(gui, 129, 2, outputAdvTank).setGauge(3000);
				//FMLLog.log(Level.INFO, "cmon!");
            }
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
            	inputs.add(new PositionedStack(new ItemStack(recipe.item.getItem()), 74, 24));
            return inputs;
        }
		
		@Override
		public PositionedStack getResult() {
			return null; //no item output, only fluid
		}	
	}
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(46, 34, 24, 19), "fuel"));
        transferRects.add(new RecipeTransferRect(new Rectangle(104, 34, 24, 19), "brewcraft.brewery"));
    }
	
	public RecipeRegistry recipes = PotionPlugin.getRecipeList();
	public PotionRegistry potions = PotionPlugin.getPotionList();
	Object[] recipearray = recipes.getBreweryRecipeSet().toArray();
	
	
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
				//if (ingredient.getItemDamage() == recipe.input.getFluid().getID()) { //not really correct, but getting this to work
				//	FMLLog.log(Level.INFO, ingredient.getItemDamage() + " : " + recipe.input.getFluid().getID());
				//	FMLLog.log(Level.INFO, "EUREKA!!!!!!!!! YOU GOT: " + recipe.input.getFluid().getLocalizedName(recipe.input));
			}
			if (ingredient.getItem() instanceof MetaItemPotion) {
				//ItemStack theone = new ItemStack(PotionPlugin.potions);
				
				//FMLLog(Level.INFO, "Chase " + 
				
				MetaItemPotion instancedPotion = (MetaItemPotion) ingredient.getItem();
				SubItemPotion readablePotion = instancedPotion.getMetaItem((instancedPotion).getDamage(ingredient));
				//FMLLog.log(Level.INFO, "Finally, potion details from ingredient. Can you get the matching fluid?" + readablePotion.potion.getName());
				
				for (int i = 1; i < recipearray.length; i++) {
					//FMLLog.log(Level.INFO, "Does this match " + ((BreweryRecipe) recipearray[i]).input.getLocalizedName());
					
				}
				//FMLLog.log(Level.INFO,  "was it the fluidcontainerregistry all along " + FluidContainerRegistry.getFluidForFilledItem(ingredient).getLocalizedName());
				
				//((MetaItemPotion) ingredient.getItem()).getMetaItem(ingredient.getItem().getDamage)
				
				/*
				for (Field field : ingredient.getItem().getClass().getDeclaredFields()) {
				    field.setAccessible(true);
				    String name = field.getName();
				    Object value;
					try {
						value = field.get(ingredient.getItem());
						FMLLog.log(Level.INFO, name + " | " + value);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
				*/

				Fluid theone = ((BreweryRecipe)recipearray[0]).input.getFluid();
				
				/* gets name, but what about fluid */
				String thepotion = ((FluidPotion)theone).getLocalizedName();
				
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
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		FMLLog.log(Level.INFO, "Trying to get that liquid recipe");
		
		
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
		}
		/*
		if (outputId.equals("liquid")) {
			FMLLog.log(Level.INFO, "Pass check 1");
			if (results.length == 1) {
				FMLLog.log(Level.INFO, "Pass check 2");
			}
		}
			
			
		if ((outputId.equals("liquid")) && (results.length == 1) && ((results[0] instanceof FluidStack))) {
			for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) {
				if (recipe.output.isFluidEqual((FluidStack) results[0])) {
					this.arecipes.add(new CachedBreweryRecipe(recipe));
				}
			}
		}
		*/
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
		//FMLLog.log(Level.INFO, "Draw, mister!");
		
		//GuiBrewery gui = new GuiBrewery();
		
		for (CachedRecipe reciperef : arecipes) {
			((CachedBreweryRecipe) reciperef).posInputTank.draw();
			((CachedBreweryRecipe) reciperef).posOutputTank.draw();
			
		}
		
		CachedBreweryRecipe crecipe = (CachedBreweryRecipe) this.arecipes.get(recipe);
		// Not fully classified, see neiintegration/RecipeHandlerBase, which I didn't fully understand
        if (crecipe.posInputTank != null) {
        	crecipe.posInputTank.draw();
        }
        if (crecipe.posOutputTank != null) {
            crecipe.posOutputTank.draw();
        }
	}
	
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
    	if (crecipe.posInputTank != null) {
            if (crecipe.posInputTank.intersectsWith(relMouse.x,relMouse.y)) {
            	crecipe.posInputTank.addTooltip(currenttip);
            }
        }
    	if (crecipe.posOutputTank != null) {
            if (crecipe.posOutputTank.intersectsWith(relMouse.x,relMouse.y)) {
            	crecipe.posOutputTank.addTooltip(currenttip);
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
