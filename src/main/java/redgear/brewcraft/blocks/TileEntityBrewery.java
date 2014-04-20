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
import redgear.core.tile.TileEntityTank;
import redgear.core.util.SimpleItem;

public class TileEntityBrewery extends TileEntityTank {

	public final AdvFluidTank inputTank;
	public final AdvFluidTank outputTank;

	public final int inFull;
	public final int inEmpty;
	public final int outEmpty;
	public final int outFull;
	public final int itemSlot;

	private SimpleItem currItem;
	private FluidStack output;

	public TileEntityBrewery() {
		super(10);

		inFull = addSlot(new TankSlot(this, 32, 13, true, -1));
		inEmpty = addSlot(new TankSlot(this, 32, 57, false, 1));
		outEmpty = addSlot(new TankSlot(this, 124, 13, false, -1));
		outFull = addSlot(new TankSlot(this, 124, 57, true, 1));
		itemSlot = addSlot(79, 36);

		inputTank = new BreweryInputTank(FluidContainerRegistry.BUCKET_VOLUME * 12, this);
		outputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 12).addFluidMap(-1, TransferRule.OUTPUT);

		
		addTank(inputTank);
		addTank(outputTank);
	}

	@Override
	protected boolean doPreWork() {
		boolean check = false;
		
		ItemStack stack = getStackInSlot(itemSlot);
		if (stack != null) {
			SimpleItem item = new SimpleItem(stack);

			if (item.equals(IngredientPlugin.obsidianTear) || item.equals(IngredientPlugin.pureTear)) {
				IngredientPlugin.tears.onUpdate(stack, worldObj, this, itemSlot);
				stack = getStackInSlot(itemSlot);
				item = new SimpleItem(stack);
				check = true;
			}
		}

		check |= fillTank(inFull, inEmpty, inputTank);
		check |= emptyTank(outEmpty, outFull, outputTank);
		//ejectAllFluids();
		
		return check;
	}
	
	

	@Override
	protected int checkWork() {
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
						return currRecipe.input.amount + 1;
					}
			}
		}
		
		return 0;
	}
	
	@Override
	protected boolean doWork(){
		inputTank.drain(output.amount, true);
		outputTank.fill(output, true);
		
		return this.getIdle() % 5 == 0;
	}

	@Override
	protected boolean doPostWork() {
		currItem = null;
		output = null;
		return true;
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

	@Override
	protected boolean tryUseEnergy(int energy) {
		return true;
	}

	public int getScaledWork(int total) {
		if (work == 0 || workTotal == 0)
			return 0;
		else
			return (int) ((float) work / (float) workTotal * total);
	}
}
