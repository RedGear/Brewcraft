package redgear.brewcraft.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.render.ProgressBar;
import redgear.core.tile.TileEntityFreeMachine;
import redgear.core.util.SimpleItem;

public class TileEntityBrewery extends TileEntityFreeMachine {

	private final AdvFluidTank inputTank;
	private final AdvFluidTank outputTank;

	private final int inFull;
	private final int inEmpty;
	private final int outEmpty;
	private final int outFull;
	private final int itemSlot;

	private final int itemBar;
	private final int proccessBar;

	private SimpleItem currItem;
	private int itemLeft;
	private FluidStack output;

	public TileEntityBrewery() {
		super(10);

		inFull = addSlot(new TankSlot(this, 32, 13, true, -1));
		inEmpty = addSlot(new TankSlot(this, 32, 57, false, 1));
		outEmpty = addSlot(new TankSlot(this, 124, 13, false, -1));
		outFull = addSlot(new TankSlot(this, 124, 57, true, 1));
		itemSlot = addSlot(79, 36);

		inputTank = new BreweryInputTank(FluidContainerRegistry.BUCKET_VOLUME * 4, this);
		outputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1, TransferRule.OUTPUT);

		addTank(inputTank, 10, 13, 16, 60);
		addTank(outputTank, 147, 13, 16, 60);

		addDrawSnippet(10, 13, 16, 60, 176, 0);
		addDrawSnippet(147, 13, 16, 60, 176, 0);

		itemBar = addProgressBar(69, 31, 3, 24);
		proccessBar = addProgressBar(5, 13, 3, 60);
	}

	@Override
	protected void doPreWork() {
		if (itemLeft <= 0) {
			ItemStack stack = getStackInSlot(itemSlot);
			if (stack != null) {
				currItem = new SimpleItem(stack);
				
				if(currItem.equals(IngredientPlugin.obsidianTear) || currItem.equals(IngredientPlugin.pureTear)){
					IngredientPlugin.tears.onUpdate(stack, this.worldObj, this, itemSlot);
					stack = getStackInSlot(itemSlot);
					currItem = new SimpleItem(stack);
				}
				
				if (Brewcraft.recipeRegistry.getBreweryRecipe(inputTank.getFluid(), currItem) != null) {
					decrStackSize(itemSlot, 1);
					itemLeft = 120;
				}
			}
		}

		fillTank(inFull, inEmpty, inputTank);
		emptyTank(outEmpty, outFull, outputTank);
		ejectAllFluids();

	}

	@Override
	protected void checkWork() {
		if (!inputTank.isEmpty()) {
			BreweryRecipe currRecipe = Brewcraft.recipeRegistry.getBreweryRecipe(inputTank.getFluid(), currItem);

			if (currRecipe != null)
				if (inputTank.canDrain(currRecipe.input) && outputTank.canFill(currRecipe.output)
						&& itemLeft >= currRecipe.amount) {
					inputTank.drain(currRecipe.input.amount, true);
					output = currRecipe.output;
					itemLeft -= currRecipe.amount;
					addWork(currRecipe.time, 0);
				}
		}
	}

	@Override
	protected void doPostWork() {
		outputTank.fill(output, true);
	}

	@Override
	public ProgressBar updateProgressBars(ProgressBar prog) {
		if (prog.id == proccessBar) {
			prog.total = workTotal;
			prog.value = work;
		}

		if (prog.id == itemBar) {
			prog.total = 120;
			prog.value = itemLeft;
		}

		return prog;
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		writeFluidStack(tag, "output", output);
		if (currItem != null)
			currItem.writeToNBT(tag, "currItem");
		tag.setInteger("itemLeft", itemLeft);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		output = readFluidStack(tag, "output");
		currItem = new SimpleItem(tag, "currItem");
		itemLeft = tag.getInteger("itemLeft");
	}

	public SimpleItem getCurrItem() {
		return currItem;
	}
}
