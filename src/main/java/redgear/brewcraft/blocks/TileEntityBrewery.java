package redgear.brewcraft.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.plugins.common.IngredientPlugin;
import redgear.brewcraft.plugins.common.PotionPlugin;
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

	private final int proccessBar;

	private SimpleItem currItem;
	private FluidStack output;

	public TileEntityBrewery() {
		super(0);

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

		//itemBar = addProgressBar(69, 31, 3, 24);
		proccessBar = addProgressBar(5, 13, 3, 60);
	}

	@Override
	protected void doPreWork() {
		if (work > 0) {
			inputTank.drain(output.amount, true);
			outputTank.fill(output, true);
		}

		ItemStack stack = getStackInSlot(itemSlot);
		if (stack != null) {
			SimpleItem item = new SimpleItem(stack);

			if (item.equals(IngredientPlugin.obsidianTear) || item.equals(IngredientPlugin.pureTear)) {
				IngredientPlugin.tears.onUpdate(stack, worldObj, this, itemSlot);
				stack = getStackInSlot(itemSlot);
				item = new SimpleItem(stack);
			}
		}

		fillTank(inFull, inEmpty, inputTank);
		emptyTank(outEmpty, outFull, outputTank);
		//ejectAllFluids();

	}

	@Override
	protected void checkWork() {
		if (!inputTank.isEmpty()) {
			ItemStack stack = getStackInSlot(itemSlot);
			if (stack != null) {
				SimpleItem item = new SimpleItem(stack);

				BreweryRecipe currRecipe = PotionPlugin.recipeRegistry.getBreweryRecipe(inputTank.getFluid(), item);

				if (currRecipe != null)
					if (inputTank.canDrain(currRecipe.input, true) && outputTank.canFill(currRecipe.output, true)) {
						currItem = item;
						decrStackSize(itemSlot, 1);
						output = currRecipe.output;
						addWork(currRecipe.input.amount + 1);
					}
			}
		}
	}

	@Override
	protected void doPostWork() {
		currItem = null;
		output = null;
	}

	@Override
	public ProgressBar updateProgressBars(ProgressBar prog) {
		if (prog.id == proccessBar) {
			prog.total = workTotal;
			prog.value = work;
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
	}

	public SimpleItem getCurrItem() {
		return currItem;
	}
}
