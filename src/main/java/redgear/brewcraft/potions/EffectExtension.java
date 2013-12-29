package redgear.brewcraft.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class EffectExtension extends Potion{
	
	public int index = -1;

	public EffectExtension(int par1, boolean par2, int par3, int par4) {
		super(par1, par2, par3);
		this.index = par4;
		System.out.println("The index value is " + index);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("redgear_brewcraft:textures/potion/icons.png"));
            return index;

    }
	

}
