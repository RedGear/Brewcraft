package redgear.brewcraft.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.brewcraft.blocks.brewery.TileEntityBrewery;
import redgear.brewcraft.potions.SubItemPotion;
import redgear.core.tile.TileEntityTank;

public class ReturnBottle {
	public static boolean addBottleToInventory (World world, TileEntity tile, EntityPlayer player, int side, float f, float g,
			float t) {
		if (!player.capabilities.isCreativeMode) {
			ItemStack stack = player.getHeldItem();
			//int damage = stack.getItemDamage();
			FluidStack potionFluid = FluidContainerRegistry.getFluidForFilledItem(stack);
			int capacity = FluidContainerRegistry.getContainerCapacity(stack);
			
			
			if (tile instanceof TileEntityBrewery) {
				TileEntityBrewery tileEntity = (TileEntityBrewery) tile;
				if (tileEntity.inputTank.canFill(capacity) &&
						tileEntity.inputTank.canFillWithMap(potionFluid, false)) {
					System.out.println("THIS IS WHERE YOU FILL BREWERY");
					ItemStack fillTank = FluidContainerRegistry.drainFluidContainer(stack);
					if (fillTank != null) {
						player.inventory.addItemStackToInventory(fillTank);
					}
				}
			} else {
				TileEntityTank tileEntity = (TileEntityTank) tile;
				if (tileEntity.getTank(0).canFill(capacity) &&
						tileEntity.getTank(0).canFillWithMap(potionFluid, false)) {
					System.out.println("THIS IS WHERE YOU FILL (OTHER MACHINE)");
					ItemStack fillTank = FluidContainerRegistry.drainFluidContainer(stack);
					if (fillTank != null) {
						player.inventory.addItemStackToInventory(fillTank);
					}
				}
			}
		}
		return false;
	}	
}
