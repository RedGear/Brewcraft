package redgear.brewcraft.blocks.keg;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKeg extends ModelBase {

	ModelRenderer BarrelRing1;
	ModelRenderer BarrelWall;
	ModelRenderer BarrelRing;
	ModelRenderer BarrelWall1;
	ModelRenderer BarrelRing2;
	ModelRenderer BarrelWall2;
	ModelRenderer BarrelRing3;
	ModelRenderer BarrelRing4;

	public ModelKeg() {
		textureWidth = 128;
		textureHeight = 128;

		BarrelRing1 = new ModelRenderer(this, 0, 0);
		BarrelRing1.addBox(0F, 0F, 0F, 9, 1, 9);
		BarrelRing1.setRotationPoint(-4.5F, 23F, -4.5F);
		BarrelRing1.setTextureSize(128, 128);
		BarrelRing1.mirror = true;
		setRotation(BarrelRing1, 0F, 0F, 0F);
		BarrelWall = new ModelRenderer(this, 0, 0);
		BarrelWall.addBox(0F, 0F, 0F, 10, 2, 10);
		BarrelWall.setRotationPoint(-5F, 21F, -5F);
		BarrelWall.setTextureSize(128, 128);
		BarrelWall.mirror = true;
		setRotation(BarrelWall, 0F, 0F, 0F);
		BarrelRing = new ModelRenderer(this, 0, 28);
		BarrelRing.addBox(0F, 0F, 0F, 12, 1, 12);
		BarrelRing.setRotationPoint(-6F, 20F, -6F);
		BarrelRing.setTextureSize(128, 128);
		BarrelRing.mirror = true;
		setRotation(BarrelRing, 0F, 0F, 0F);
		BarrelWall1 = new ModelRenderer(this, 0, 0);
		BarrelWall1.addBox(0F, 0F, 0F, 11, 8, 11);
		BarrelWall1.setRotationPoint(-5.5F, 12F, -5.5F);
		BarrelWall1.setTextureSize(128, 128);
		BarrelWall1.mirror = true;
		setRotation(BarrelWall1, 0F, 0F, 0F);
		BarrelRing2 = new ModelRenderer(this, 0, 28);
		BarrelRing2.addBox(0F, 0F, 0F, 12, 1, 12);
		BarrelRing2.setRotationPoint(-6F, 11F, -6F);
		BarrelRing2.setTextureSize(128, 128);
		BarrelRing2.mirror = true;
		setRotation(BarrelRing2, 0F, 0F, 0F);
		BarrelWall2 = new ModelRenderer(this, 0, 0);
		BarrelWall2.addBox(0F, 0F, 0F, 10, 2, 10);
		BarrelWall2.setRotationPoint(-5F, 9F, -5F);
		BarrelWall2.setTextureSize(128, 128);
		BarrelWall2.mirror = true;
		setRotation(BarrelWall2, 0F, 0F, 0F);
		BarrelRing3 = new ModelRenderer(this, 0, 2);
		BarrelRing3.addBox(0F, 0F, 0F, 9, 1, 9);
		BarrelRing3.setRotationPoint(-4.5F, 8F, -4.5F);
		BarrelRing3.setTextureSize(128, 128);
		BarrelRing3.mirror = true;
		setRotation(BarrelRing3, 0F, 0F, 0F);
		BarrelRing4 = new ModelRenderer(this, 0, 28);
		BarrelRing4.addBox(0F, 0F, 0F, 12, 1, 12);
		BarrelRing4.setRotationPoint(-6F, 15.5F, -6F);
		BarrelRing4.setTextureSize(128, 128);
		BarrelRing4.mirror = true;
		setRotation(BarrelRing4, 0F, 0F, 0F);
	}

	public void render(float f5) {
		BarrelRing1.render(f5);
		BarrelWall.render(f5);
		BarrelRing.render(f5);
		BarrelWall1.render(f5);
		BarrelRing2.render(f5);
		BarrelWall2.render(f5);
		BarrelRing3.render(f5);
		BarrelRing4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
