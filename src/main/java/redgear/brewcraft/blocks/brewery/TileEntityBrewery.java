package redgear.brewcraft.blocks.brewery;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.plugins.item.ItemPlugin;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.InvSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.Faced;
import redgear.core.tile.TileEntityTank;
import redgear.core.util.SimpleItem;

public class TileEntityBrewery extends TileEntityTank implements Faced {

	public final AdvFluidTank inputTank;
	public final AdvFluidTank outputTank;

	public final InvSlot itemSlot;

	private SimpleItem currItem;
	private FluidStack output;

	public TileEntityBrewery() {
		super(10);

		itemSlot = addSlot(new InvSlot(this, 79, 36) {
			@Override
			public boolean stackAllowed(ItemStack stack) {
				SimpleItem item = new SimpleItem(stack);

				for (BreweryRecipe recipe : PotionPlugin.recipeRegistry.recipes)
					if (recipe.item.equals(item))
						return true;

				return false;
			}
		});

		inputTank = new BreweryInputTank(FluidContainerRegistry.BUCKET_VOLUME * 12, this);
		outputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 12).addFluidMap(-1, TransferRule.OUTPUT);

		addTank(inputTank);
		addTank(outputTank);
	}

	@Override
	public boolean doPreWork() {
		ItemStack stack = itemSlot.getStack();
		if (stack != null) {
			SimpleItem item = new SimpleItem(stack);

			if (item.equals(ItemPlugin.obsidianTear) || item.equals(ItemPlugin.pureTear)) {
				ItemPlugin.tears.onUpdate(stack, worldObj, this, itemSlot.slotNumber);
				return true;
			}
		}
		return false;
	}

	@Override
	public int checkWork() {
		if (!inputTank.isEmpty()) {
			ItemStack stack = itemSlot.getStack();
			if (stack != null) {
				SimpleItem item = new SimpleItem(stack);

				BreweryRecipe currRecipe = PotionPlugin.recipeRegistry.getBreweryRecipe(inputTank.getFluid(), item);

				if (currRecipe != null)
					if (inputTank.canDrain(currRecipe.input, true) && outputTank.canFill(currRecipe.output, true)) {
						currItem = item;
						itemSlot.decrStackSize(1);
						output = currRecipe.output;
						output.amount = 10;
						return currRecipe.input.amount / 10;
					}
			}
		}

		return 0;
	}

	@Override
	public boolean doWork() {
		inputTank.drain(output.amount, true);
		outputTank.fill(output, true);

		return idle() % 5 == 0;
	}

	@Override
	public boolean doPostWork() {
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
	public boolean tryUseEnergy(int energy) {
		return true;
	}

	public int getScaledWork(int total) {
		if (getWork() == 0 || getWorkTotal() == 0)
			return 0;
		else
			return (int) ((float) getWork() / (float) getWorkTotal() * total);
	}
}
