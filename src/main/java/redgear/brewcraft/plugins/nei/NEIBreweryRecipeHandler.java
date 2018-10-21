package redgear.brewcraft.plugins.nei;

import static net.minecraft.init.Items.potionitem;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import codechicken.nei.ItemStackSet;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.BrewingRecipeHandler.BrewingRecipe;
import codechicken.nei.recipe.BrewingRecipeHandler.CachedBrewingRecipe;
import codechicken.nei.recipe.FurnaceRecipeHandler.FuelPair;
import codechicken.nei.recipe.FurnaceRecipeHandler.SmeltingPair;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.core.util.SimpleItem;

public class NEIBreweryRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		String name = StatCollector.translateToLocal("redgear.brewcraft.brewery");
    	FMLLog.log(Level.INFO, "You got the Brewcraft Stuff " + name);
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
        final long offset = System.currentTimeMillis();

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
            	inputs.add(new PositionedStack(new ItemStack(recipe.input.getFluid().getBlock()), 26, 13));
            if (recipe.item != null)
            	inputs.add(new PositionedStack(new ItemStack(recipe.item.getItem()), 75, 31));
            return inputs;
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(new ItemStack(recipe.output.getFluid().getBlock()), 134, 13);
        }
    }
	
	//public static final ItemStackSet ingredients = new ItemStackSet();
    //public static final HashSet<BreweryRecipe> apotions = new HashSet<BreweryRecipe>();
    public RecipeRegistry recipes = PotionPlugin.getRecipeList();
	
	@Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(46, 34, 24, 19), "fuel"));
        transferRects.add(new RecipeTransferRect(new Rectangle(104, 34, 24, 19), "potion_fluids"));
    }
	
	/** loading crafting for potion fluids
	 * 
	 */
	@Override
    public void loadCraftingRecipes(String outputId, Object... results) {
		FMLLog.log(Level.INFO, "Yes! You got CRAFTING recipes loading. Wait where are they?");
		if (outputId.equals("brewcraft.brewery") && getClass() == NEIBreweryRecipeHandler.class) {//don't want subclasses getting a hold of this
    		FMLLog.log(Level.INFO, "Loading validated. Get ready... ");
    		FMLLog.log(Level.INFO, "Potion 0 " + recipes.getBreweryRecipe(PotionPlugin.water, new SimpleItem(Items.nether_wart)).toString());
		}
        for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) {
        	arecipes.add(new CachedBreweryRecipe(recipe));
        }
        FMLLog.log(Level.INFO, "Length of Matched Recipes",arecipes.lastIndexOf(recipes.getBreweryRecipe(PotionPlugin.fluidAwkward,new SimpleItem(Items.sugar)).toString()));
    }
	
	@Override
    public void loadUsageRecipes(ItemStack ingredient) {
		FMLLog.log(Level.INFO, "Yes! You got USAGE recipes loading. Wait where are they?");
        for (BreweryRecipe recipe : this.recipes.getBreweryRecipeSet()) {
            if (NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(recipe.item.getItem()), ingredient)) {
                FluidPair arecipe = new FluidPair(new FluidStack(recipe.input.getFluid(), 1000), new FluidStack(recipe.output.getFluid(), 1000));
                //arecipe.setIngredientPermutation(recipes, recipe.item));
                FMLLog.log(Level.INFO, arecipe.toString() + "So there is a recipe");
                arecipes.add(arecipe);
            }
        }
	}
	
	public class FluidPair extends CachedRecipe
    {
        public FluidPair(FluidStack inputf, FluidStack outputf) {
            this.inputf = new PositionedStack(inputf, 51, 6);
            this.outputf = new PositionedStack(outputf, 111, 24);
        }

        PositionedStack inputf;
        PositionedStack outputf;
        
        public PositionedStack getResult() {
            return outputf;
        }
    }
	
	@Override
    public String getOverlayIdentifier() {
        return "brewcraft.brewery";
    }
	
}
