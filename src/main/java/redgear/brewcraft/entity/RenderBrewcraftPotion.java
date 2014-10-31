package redgear.brewcraft.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import redgear.brewcraft.potions.MetaItemPotion;

public class RenderBrewcraftPotion extends Render {

	public void doRender(EntityBrewcraftPotion entity, double x, double y, double z, float f1, float f2) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		bindEntityTexture(entity);
		Tessellator tessellator = Tessellator.instance;

		MetaItemPotion item = (MetaItemPotion)entity.getPotionStack().getItem();
		int color = item.getColorFromItemStack(entity.getPotionStack(), 0);
		float red = (color >> 16 & 255) / 255.0F;
		float blue = (color >> 8 & 255) / 255.0F;
		float green = (color & 255) / 255.0F;
		GL11.glColor3f(red, blue, green);
		GL11.glPushMatrix();
		drawIcon(tessellator, item.overlay);
		GL11.glPopMatrix();
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		drawIcon(tessellator, item.splash);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();

	}

	private void drawIcon(Tessellator tessellator, IIcon icon) {
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
		tessellator.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
		tessellator.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
		tessellator.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
		tessellator.draw();
	}

	@Override
	public void doRender(Entity arg0, double arg1, double arg2, double arg3, float arg4, float arg5) {
		doRender((EntityBrewcraftPotion) arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity arg0) {
		return TextureMap.locationItemsTexture;
	}

}
