package redgear.brewcraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.asm.RedGearCore;
import redgear.core.block.MetaTile;
import redgear.core.block.SubTile;
import redgear.core.tile.Bucketable;
import redgear.core.tile.IWrenchableTile;

public class MetaTileBottlers extends MetaTile {

	public MetaTileBottlers(Material par2Material, String name) {
		super(par2Material, name);
		isBlockContainer = true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f, float g,
			float t) {
		SubTile block = getMetaBlock(world.getBlockMetadata(x, y, z));

		if (block != null) {

			//if (!world.isRemote)
			if (player.getHeldItem() != null && player.getHeldItem().getItem() != null)
				if (holdingWrench(player)) {
					TileEntity tile = world.getTileEntity(x, y, z);
					if (tile instanceof IWrenchableTile) {
						IWrenchableTile wrenchable = (IWrenchableTile) tile;
						boolean test;

						if (player.isSneaking())
							test = wrenchable.wrenchedShift(player, ForgeDirection.getOrientation(side));
						else
							test = wrenchable.wrenched(player, ForgeDirection.getOrientation(side));

						if (!test)//if tile returns true, continue to the gui. false means stop.
							return true;
					}
				}

				else if (FluidContainerRegistry.isContainer(player.getHeldItem())) {
					TileEntity tile = world.getTileEntity(x, y, z);
					if (tile instanceof Bucketable
							&& ((Bucketable) tile).bucket(player, player.inventory.currentItem,
									player.getHeldItem()))
						System.out.println("YOU CLICKED with potion on bottlers");
						return true;

				}

			if (block.hasGui() && !player.isSneaking()) {
				player.openGui(RedGearCore.inst, block.guiId(), world, x, y, z);
				return true;
			}
		}
		return false;
	}
}
