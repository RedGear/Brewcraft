package redgear.brewcraft.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EffectExtension extends Potion{

	public int color;

	public EffectExtension(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
		
		this.color = par3;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("redgear_brewcraft:textures/potion/icons.png"));
            if(this.color == 1987089){
            return 0;
            }
            if(this.color == 16114042){
            return 1;
            }
            if(this.color == 16777215){
            return 2;
            }
            if(this.color == 8131210){
            return 3;
            }
            return -1;

    }
	

}
