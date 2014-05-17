package redgear.brewcraft.blocks.keg;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import redgear.core.block.MetaTileSpecialRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaTileKeg extends MetaTileSpecialRenderer {

	public MetaTileKeg(Material par2Material, String name, int renderId) {
		super(par2Material, name, renderId);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon("iron_block");
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (side == 1) {
            return Blocks.iron_block.getIcon(side, 0);
            //Adds break particles for the iron rings around the keg.
        }
        if (side == 0) {
            switch (meta) {
            case 0: return Blocks.oak_stairs.getIcon(side, 0);
            case 1: return Blocks.birch_stairs.getIcon(side, 0);
            case 2: return Blocks.jungle_stairs.getIcon(side, 0);
            case 3: return Blocks.spruce_stairs.getIcon(side, 0);
            case 4: return Blocks.dark_oak_stairs.getIcon(side, 0);
            case 5: return Blocks.acacia_stairs.getIcon(side, 0);
            case 6: return Blocks.iron_block.getIcon(side, 0);
            //Adds break particles based on the meta.
            }
        }
        return null;
    }
}
