package redgear.brewcraft.blocks.brewery;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.plugins.item.IngredientPlugin;
import redgear.brewcraft.plugins.item.PotionPlugin;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.core.api.tile.IBucketableTank;
import redgear.core.api.tile.IFacedTile;
import redgear.core.api.util.FacedTileHelper;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.core.util.SimpleItem;

public class TileEntityBrewery extends TileEntityTank implements IBucketableTank, IFacedTile {

	ForgeDirection face;
	
	public final AdvFluidTank inputTank;
	public final AdvFluidTank outputTank;

	public final int itemSlot;

	private SimpleItem currItem;
	private FluidStack output;

	public TileEntityBrewery() {
		super(10);

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
						output.amount = 10;
						return currRecipe.input.amount / 10;
					}
			}
		}

		return 0;
	}

	@Override
	protected boolean doWork() {
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
		tag.setByte("face", (byte) face.ordinal());
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
		face = ForgeDirection.getOrientation(tag.getByte("face"));
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
	
	@Override
	public int getDirectionId() {
		return face.ordinal();
	}

	@Override
	public ForgeDirection getDirection() {
		return face;
	}

	@Override
	public boolean setDirection(int id) {
		if (id >= 0 && id < 6) {
			face = ForgeDirection.getOrientation(id);
			return true;
		} else
			return false;
	}

	@Override
	public boolean setDirection(ForgeDirection side) {
		face = side;
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		face = FacedTileHelper.facePlayerFlat(entity);
	}
}
