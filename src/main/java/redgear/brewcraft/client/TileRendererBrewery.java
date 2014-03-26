package redgear.brewcraft.client;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import redgear.brewcraft.blocks.ModelBrewery;
import redgear.brewcraft.blocks.TileEntityBrewery;
import redgear.brewcraft.common.Brewcraft;
import redgear.core.util.StringHelper;

public class TileRendererBrewery extends TileEntitySpecialRenderer {

	private final int rotationMap[] = {0, 3, 2, 1 };
	ModelBrewery model = new ModelBrewery();
	ResourceLocation texture = StringHelper.parseModelTexture(Brewcraft.inst.modId, "brewery");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {  //I think f is the shadow or something?
		bindTexture(texture);

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		TileEntityBrewery tile = (TileEntityBrewery) tileentity;

		int direction = tile.getDirectionId();
		GL11.glRotatef(rotationMap[direction & 3] * 90, 0.0F, 1.0F, 0.0F); //Rotates model to correct angel. 

		GL11.glScalef(1.0F, -1F, -1F); //Uh, not sure what this does
		model.render(0.0625F);//I don't know what that number is. 
		GL11.glPopMatrix();
	}

}
