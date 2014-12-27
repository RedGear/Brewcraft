package redgear.brewcraft.blocks.bottler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.InvSlot;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;

public class TileEntityBottler extends TileEntityTank {

	public final AdvFluidTank tank;

	public final List<InvSlot> input = new ArrayList<InvSlot>(6);
	public final List<InvSlot> output = new ArrayList<InvSlot>(6);

	public TileEntityBottler() {
		super(80);

		tank = new AdvFluidTank(8000).addFluidMap(-1, TransferRule.BOTH);

		addTank(tank);
		
		int inputX = 8;
		int inputY = 39;

		int outputX = 116;
		int outputY = 39;

		int slotWidth = 18;
		int slotHeight = 18;

		for (int y = 0; y < 2; y++)
			for (int x = 0; x < 3; x++) {
				input.add(addSlot(new TankSlot(this, inputX + slotWidth * x, inputY + slotHeight * y, false).setMachineRule(TransferRule.INPUT)));
				output.add(addSlot(outputX + slotWidth * x, outputY + slotHeight * y).setRules(TransferRule.OUTPUT));
			}

	}

	@Override
	public boolean doPreWork() {
		return false;
	}

	@Override
	public int checkWork() {
		if (!tank.isEmpty())
			for (InvSlot slot : input)
				if (!slot.isEmpty()) {
					ItemStack filled = FluidContainerRegistry.fillFluidContainer(tank.getFluid().copy(), slot.getStack().copy());

					if (filled != null)
						for (InvSlot out : output)
							if (out.canAddStack(filled))
								return 10;
				}

		return 0;
	}

	@Override
	public boolean doWork() {
		return false;
	}

	@Override
	public boolean doPostWork() {
		if (!tank.isEmpty())
			for (InvSlot slot : input)
				if (!slot.isEmpty()) {
					ItemStack filled = FluidContainerRegistry.fillFluidContainer(tank.getFluid().copy(), slot.getStack().copy());
					if (filled != null) {
						int capacity = FluidContainerRegistry.getContainerCapacity(tank.getFluid(), filled);
						if (tank.canDrainWithMap(capacity))
							for (InvSlot out : output)
								if (out.canAddStack(filled)) {
									tank.drainWithMap(capacity, true);
									slot.decrStackSize(1);
									out.addStack(filled);
									return true;
								}
					}

				}
		return false;
	}
}
