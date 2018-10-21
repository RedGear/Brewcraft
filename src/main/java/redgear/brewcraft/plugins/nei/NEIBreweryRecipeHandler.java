package redgear.brewcraft.plugins.nei;

import static net.minecraft.init.Items.potionitem;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.potions.FluidPotion;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.brewcraft.utils.PotionRegistry;
import redgear.core.util.SimpleItem;

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
			}		
		} else {		
	        for (BreweryRecipe recipe : this.recipes.getBreweryRecipeSet()) {
	        	//FMLLog.log(Level.INFO, "Recipe Item :" + new ItemStack(recipe.item.getItem()).toString());
	        	//FMLLog.log(Level.INFO, "Held Item   :" + ingredient.toString());
	        	
	            //if (NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(recipe.item.getItem()), ingredient)) {
	            	CachedBreweryRecipe arecipe = new CachedBreweryRecipe(recipe);
	            	//FMLLog.log(Level.INFO, "CHECKCKCKCHCK");
	        		if (recipe.input.isFluidEqual(ingredient)) {
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
		FMLLog.log(Level.INFO, "What? So Fluids Exist?");
	}
	
}
