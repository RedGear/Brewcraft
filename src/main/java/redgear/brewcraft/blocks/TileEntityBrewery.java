package redgear.brewcraft.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.common.Brewcraft;
import redgear.brewcraft.recipes.BreweryRecipe;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.render.ProgressBar;
import redgear.core.tile.TileEntityFreeMachine;
import redgear.core.util.SimpleItem;

public class TileEntityBrewery extends TileEntityFreeMachine{
	
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
		outFull =  addSlot(new TankSlot(this, 124, 57, true, 1)); 
		itemSlot = addSlot(79, 36);
		
		inputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		outputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
        
        for(BreweryRecipe recipe : Brewcraft.registry.recipes)
        	inputTank.addFluidMap(recipe.input.fluidID);
        
        inputTank.setPressure(-1);
        outputTank.setPressure(1);
        addTank(inputTank, 10, 13, 16, 60);
        addTank(outputTank, 147, 13, 16, 60);
        
        itemBar = addProgressBar(20, 13, 3, 60);
        proccessBar = addProgressBar(20, 13, 3, 60);
	}

	@Override
	protected void doPreWork() {
		fillTank(inFull, inEmpty, inputTank);
		emptyTank(outEmpty, outFull, outputTank);
		ejectAllFluids();
		
		
		if(itemLeft <= 240){ //if there is two or less, then there is room for one more. Capacity is three. 
			ItemStack stack = getStackInSlot(itemSlot);
			if(stack != null && (itemLeft == 0 || currItem.equals(stack))){
				currItem = new SimpleItem(stack); //it's either the same or empty.
				for(BreweryRecipe recipe : Brewcraft.registry.recipes)
					if(recipe.item.equals(currItem)){
						decrStackSize(itemSlot, 1);
						itemLeft += 120;
						break;
					}
			}
		}	
	}

	@Override
	protected void checkWork() {
		if(!inputTank.isEmpty()){
			BreweryRecipe currRecipe = Brewcraft.registry.getBreweryRecipe(inputTank.getFluid(), currItem);
			
			if(currRecipe != null)
				if(inputTank.getFluidAmount() >= currRecipe.input.amount 
				&& (outputTank.isEmpty() || outputTank.contains(currRecipe.output.getFluid())) 
				&& outputTank.getSpace() >= currRecipe.output.amount
				&& itemLeft >= currRecipe.amount){
					
					inputTank.drain(currRecipe.input.amount, true);
					output = currRecipe.output;
					itemLeft -= currRecipe.amount;
					addWork(currRecipe.time, 0);
				}
		}
	}

	@Override
	protected void doPostWork() {
		outputTank.fillOverride(output, true);
	}
	
	@Override
    public ProgressBar updateProgressBars(ProgressBar prog){
    	if(prog.id == proccessBar){
    		prog.total = workTotal;
    		prog.value = work;
    	}
    	
    	if(prog.id == itemBar){
    		prog.total = 360;
    		prog.value = itemLeft;
    	}
    	
    	return prog;
    }
	
	/**
     * Don't forget to override this function in all children if you want more vars!
     */
    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        writeFluidStack(tag, "output", output);
        if(currItem != null)
        	currItem.writeToNBT(tag, "currItem");
        tag.setInteger("itemLeft", itemLeft);
    }

    /**
     * Don't forget to override this function in all children if you want more vars!
     */
    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        output = readFluidStack(tag, "output");
        currItem = new SimpleItem(tag, "currItem");
        itemLeft = tag.getInteger("itemLeft");
    }

}
