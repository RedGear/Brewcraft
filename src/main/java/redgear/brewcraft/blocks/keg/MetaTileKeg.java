package redgear.brewcraft.blocks.keg;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.block.MetaTileSpecialRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaTileKeg extends MetaTileSpecialRenderer {

	public MetaTileKeg(Material par2Material, String name, int renderId) {
		super(par2Material, name, renderId);
		
		setHardness(1.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(Brewcraft.tabMisc);
		setHarvestLevel("axe", 0);
		setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon("iron_block");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.iron_block.getIcon(side, 0);
	}

	@Override
	public int getDamageValue(World par1World, int par2, int par3, int par4) {
		return par1World.getBlockMetadata(par2, par3, par4);
	}
}
