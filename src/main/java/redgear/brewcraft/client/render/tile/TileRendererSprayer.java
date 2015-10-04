package redgear.brewcraft.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import redgear.brewcraft.blocks.sprayer.ModelSprayer;
import redgear.brewcraft.blocks.sprayer.TileEntitySprayer;
import redgear.brewcraft.core.Brewcraft;
import redgear.core.util.StringHelper;

public class TileRendererSprayer extends TileEntitySpecialRenderer {

	ModelSprayer model = new ModelSprayer();
	ResourceLocation texture = StringHelper.parseModelTexture(Brewcraft.inst.modId, "sprayer");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		//I think f is the fraction of a tick since the last tick. The last tick being what gives us the solid TileEntity values to work with.
		// That way you can use f to predict future behavior (IE: if your block is moving or rotating or something you can render it in time with the client looking at it
		// even if the server is partly off.
		bindTexture(texture);

		GL11.glPushMatrix();
//		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		GL11.glTranslated(x, y, z);
		GL11.glScaled(1.0, -1.0, -1.0);

		TileEntitySprayer tile = (TileEntitySprayer) tileentity;
		
		//ForgeDirection direction = tile.getDirection();

//		GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);

		GL11.glTranslated(0.5, -0.5, -0.5);

		switch (tile.getDirection()) {
			case DOWN:
				GL11.glRotated(180.0, 0.0, 0.0, 1.0);
				break;

			case NORTH:
				GL11.glRotated(-90.0, 1.0, 0.0, 0.0);
//				GL11.glTranslated(0.0, -1.0, 0.0);
				break;

			case SOUTH:
				GL11.glRotated(90.0, 1.0, 0.0, 0.0);
//				GL11.glTranslated(0.0, 1.0, 1.0);
				break;

			case EAST:
				GL11.glRotated(90.0, 0.0, 0.0, 1.0);
//				GL11.glTranslated(1.0, 1.0, 0.0);
				break;

			case WEST:
				GL11.glRotated(-90.0, 0.0, 0.0, 1.0);
//				GL11.glTranslated(-1.0, 1.0, 0.0);
				break;

			case UP:
			case UNKNOWN:
			default:
//				GL11.glRotated(90.0, 0.0, 1.0, 0.0);
//				GL11.glScaled(1.0, -1.0, 1.0);
//				GL11.glTranslated(0.0, -1.0, 0.0);
				break;
		}

		GL11.glTranslated(0.0, -1.0, 0.0);
//		GL11.glTranslated(0.0, 1.0, -1.0);
//		GL11.glTranslated(0.5, 1.5, 0.5);

//		GL11.glRotatef(rotationMap[direction & 3] * 90, 0.0F, 1.0F, 0.0F); //Rotates model to correct angel.


//		GL11.glScalef(1.0F, -1F, -1F); //Uh, not sure what this does
		model.render(0.0625F);//I don't know what that number is. 
		GL11.glPopMatrix();
	}

}