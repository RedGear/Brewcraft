package redgear.brewcraft.plugins.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.blocks.brewery.ContainerBrewery;
import redgear.brewcraft.blocks.brewery.GuiBrewery;
import redgear.brewcraft.blocks.brewery.TileEntityBrewery;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.potions.MetaItemPotion;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.brewcraft.recipes.RecipeRegistry;
import redgear.brewcraft.utils.PotionRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.render.GuiBase;

public class NEIBreweryRecipeHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		String name = StatCollector.translateToLocal("redgear.brewcraft.brewery");
		return "Brewery"; // ideally replace with language-independent StatCollector or similar
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation("redgear_brewcraft:textures/gui/brewery.png").toString();
	}

	// not ideal, but
	InventoryPlayer living = new InventoryPlayer(null); // dummy instance
	GuiBrewery gui = new GuiBrewery(new ContainerBrewery(living, new TileEntityBrewery())); // dummy instance

	RecipeRegistry recipes = PotionPlugin.getRecipeList();
	PotionRegistry potions = PotionPlugin.getPotionList();

	/**
	 * This Recipe Handler runs on this internal class. Fill the recipe array with
	 * subclasses of this to make transforming the different types of recipes out
	 * there into a nice format for NEI a much easier job.
	 */
	public class CachedBreweryRecipe extends CachedRecipe {
		// Typed variables from brewcraft to add to CachedBreweryRecipe
		public BreweryRecipe recipe;
		List<NEIPositionedFluidTank> tankList = new ArrayList<NEIPositionedFluidTank>();

		/**
		 * Sets two NEIPositionedFluidTanks to be drawn up
		 *
		 */
		public void setFluidTanks() {
			if ((recipe.input != null) && (recipe.output != null)) {
				AdvFluidTank inputAdvTank = new AdvFluidTank(recipe.input, FluidContainerRegistry.BUCKET_VOLUME * 12); // 12000mb
				AdvFluidTank outputAdvTank = new AdvFluidTank(recipe.output, FluidContainerRegistry.BUCKET_VOLUME * 12);

				tankList.add(
						(NEIPositionedFluidTank) new NEIPositionedFluidTank(gui, 21, 2, inputAdvTank).setGauge(3000));
				tankList.add(
						(NEIPositionedFluidTank) new NEIPositionedFluidTank(gui, 129, 2, outputAdvTank).setGauge(3000));
			}
		}

		/**
		 * @return a list of NEIPositionedFluidTank
		 *
		 */
		public List<NEIPositionedFluidTank> getFluidTanks() {
			return tankList;
		}

		public CachedBreweryRecipe(BreweryRecipe recipe) {
			this.recipe = recipe;
			setFluidTanks();
		}

		/**
		 * The ingredients required to produce the result Use this if you have more than
		 * one ingredient
		 *
		 * @return A list of positioned ingredient items.
		 */
		@Override
		public List<PositionedStack> getIngredients() {
			ArrayList<PositionedStack> inputs = new ArrayList<PositionedStack>();
			if (recipe.item != null)
				inputs.add(new PositionedStack(recipe.item.getStack(), 74, 24));
			return inputs;
		}

		@Override
		public PositionedStack getResult() {
			return null; // no item output, only fluid
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(56, 19, 12, 29), "brewcraft.brewery"));
	}

	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("liquid") && ingredients[0] instanceof FluidStack
				&& ((FluidStack) ingredients[0]).getFluid() != null) {
			this.loadUsageRecipes((FluidStack) ingredients[0]);
		} else if (inputId.equals("item")) {
			loadUsageRecipes((ItemStack) ingredients[0]);
		}
	}

	/**
	 * Loads usage recipes for ItemStack
	 */
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if ((ingredient.getItem() instanceof ItemPotion) || (ingredient.getItem() instanceof MetaItemPotion)) {

			if (ingredient.getItem() instanceof MetaItemPotion) {
				System.out.println("BREWCRAFTED POTION");
				MetaItemPotion instancedPotion = (MetaItemPotion) ingredient.getItem(); // ItemStack to instance of potion

				loadUsageRecipes(FluidContainerRegistry.getFluidForFilledItem(ingredient));

			}
		} else {
			for (BreweryRecipe recipe : this.recipes.getBreweryRecipeSet()) {
				CachedBreweryRecipe arecipe = new CachedBreweryRecipe(recipe);
				if (recipe.input.isFluidEqual(ingredient)) { // may not work, fluid not equal to potion
					arecipes.add(arecipe);
				} else if (recipe.item.item.equals(ingredient.getItem())) { // recipe.item.item required for damage
					arecipes.add(arecipe);
				}
			}
		}
	}

	/**
	 * Loads crafting recipes for FluidStack
	 */
	public void loadCraftingRecipes(FluidStack fluid) {
		for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) { // for each recipe in the recipe registry
			if (recipe.output.isFluidEqual(fluid)) { // is the recipe's output equal to the fluid
				this.arecipes.add(new CachedBreweryRecipe(recipe));
			}
		}
	}

	public void loadUsageRecipes(FluidStack fluid) {
		for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) {
			if (recipe.input.isFluidEqual(fluid)) {
				this.arecipes.add(new CachedBreweryRecipe(recipe));
				System.out.println("WAY " + recipe.input.getLocalizedName() + recipe.input.getFluid().getColor());
				System.out.println("OUT " + recipe.output.getLocalizedName() + recipe.output.getFluid().getColor());
			}
		}
	}

	/**
	 * Getting crafting recipes for brewcraft potions
	 */
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if ((outputId.equals("item")) && (results.length == 1) && ((results[0] instanceof ItemStack))) {
			// all this instanceof and type casting would do people's heads in
			if (((ItemStack) results[0]).getItem() instanceof MetaItemPotion) {
				FluidStack resultFluid = FluidContainerRegistry.getFluidForFilledItem(((ItemStack) results[0]));

				for (BreweryRecipe recipe : recipes.getBreweryRecipeSet()) {
					if (recipe.output.isFluidEqual(resultFluid)) {
						this.arecipes.add(new CachedBreweryRecipe(recipe));
					}
				}
			}
		} else if (outputId.equals("liquid") && results[0] instanceof FluidStack
				&& ((FluidStack) results[0]).getFluid() != null) {
			this.loadCraftingRecipes((FluidStack) results[0]); // pass to loadCraftingRecipes, FluidStack version
		} else if (outputId.equals("brewcraft.brewery") && getClass() == NEIBreweryRecipeHandler.class)// don't want subclasses getting a hold of this
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

		CachedRecipe crecipe = arecipes.get(recipe);
		List<NEIPositionedFluidTank> fluidtanklist = ((CachedBreweryRecipe) crecipe).getFluidTanks();
		for (NEIPositionedFluidTank tank : fluidtanklist) {
			tank.draw();
		}
		drawExtras(recipe);
	}

	/**
	 * Including Brew Overlay (the bubbles if you're wondering)
	 */
	public void drawExtras(int recipe) {
		String overlaylocation = new ResourceLocation("redgear_brewcraft:textures/gui/brewoverlay.png").toString();
		changeTexture(overlaylocation);
		GuiBase overlayGui = new GuiBase(null);
		overlayGui.drawSizedTexturedModalRect(56, 19, 0, 0, 12, 29, (float) 24.0, (float) 29.0);
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int recipe) {
		if (button == 0) {
			if (this.clickedFluidTank(gui, recipe, false)) {
				return true;
			}
		} else if (button == 1) {
			if (this.clickedFluidTank(gui, recipe, true)) {
				return true;
			}
		}

		return super.mouseClicked(gui, button, recipe);
	}

	protected boolean clickedFluidTank(GuiRecipe guiRecipe, int recipe, boolean usage) {
		CachedBreweryRecipe crecipe = (CachedBreweryRecipe) this.arecipes.get(recipe);
		Point mousepos = GuiDraw.getMousePosition();
		Point offset = guiRecipe.getRecipePosition(recipe);
		Point relMouse = new Point(mousepos.x - (guiRecipe.width - 176) / 2 - offset.x,
				mousepos.y - (guiRecipe.height - 166) / 2 - offset.y);

		if (crecipe.getFluidTanks() != null) {
			for (NEIPositionedFluidTank tank : crecipe.getFluidTanks()) {
				if (tank.intersectsWith(relMouse.x, relMouse.y)) {
					return tank.transfer(usage);
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param tank
	 *            the NEIPositionedFluidTank
	 * @param usage
	 *            whether user requested for "Usage" or "Recipe" of tank
	 * @return
	 */
	@Override
	public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> currenttip, int recipe) {
		super.handleTooltip(guiRecipe, currenttip, recipe);
		CachedBreweryRecipe crecipe = (CachedBreweryRecipe) this.arecipes.get(recipe);
		if (GuiContainerManager.shouldShowTooltip(guiRecipe)) {
			Point mouse = GuiDraw.getMousePosition();
			Point offset = guiRecipe.getRecipePosition(recipe);
			Point relMouse = new Point(mouse.x - (guiRecipe.width - 176) / 2 - offset.x,
					mouse.y - (guiRecipe.height - 166) / 2 - offset.y);

			currenttip = this.provideTooltip(guiRecipe, currenttip, crecipe, relMouse);
		}
		return currenttip;
	}

	/**
	 * provides tooltips for fluid tanks in paticular
	 * 
	 * @param guiRecipe
	 * @param currenttip
	 * @param crecipe
	 * @param relMouse
	 * @return
	 */
	public List<String> provideTooltip(GuiRecipe guiRecipe, List<String> currenttip, CachedBreweryRecipe crecipe,
			Point relMouse) {
		if (crecipe.getFluidTanks() != null) {
			for (NEIPositionedFluidTank tank : crecipe.getFluidTanks()) {
				if (tank.intersectsWith(relMouse.x, relMouse.y)) {
					tank.addTooltip(currenttip);
				}
			}
		}
		return currenttip;
	}
}
