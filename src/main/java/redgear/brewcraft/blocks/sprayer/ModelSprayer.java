package redgear.brewcraft.blocks.sprayer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSprayer extends ModelBase {

	ModelRenderer Base3;
	ModelRenderer Spout;
	ModelRenderer Base2;
	ModelRenderer Base1;
	ModelRenderer Nozzle;

	public ModelSprayer() {
		textureWidth = 128;
		textureHeight = 128;

		Base3 = new ModelRenderer(this, 0, 0);
		Base3.addBox(0F, 0F, 0F, 11, 5, 11);
		Base3.setRotationPoint(-5.5F, 15F, -5.5F);
		Base3.setTextureSize(128, 128);
		Base3.mirror = true;
		setRotation(Base3, 0F, 0F, 0F);
		Spout = new ModelRenderer(this, 0, 20);
		Spout.addBox(0F, 0F, 0F, 5, 5, 5);
		Spout.setRotationPoint(-2.5F, 10F, -2.5F);
		Spout.setTextureSize(128, 128);
		Spout.mirror = true;
		setRotation(Spout, 0F, 0F, 0F);
		Base2 = new ModelRenderer(this, 55, 0);
		Base2.addBox(0F, 0F, 0F, 13, 2, 13);
		Base2.setRotationPoint(-6.5F, 20F, -6.5F);
		Base2.setTextureSize(128, 128);
		Base2.mirror = true;
		setRotation(Base2, 0F, 0F, 0F);
		Base1 = new ModelRenderer(this, 40, 20);
		Base1.addBox(0F, 0F, 0F, 16, 2, 16);
		Base1.setRotationPoint(-8F, 22F, -8F);
		Base1.setTextureSize(128, 128);
		Base1.mirror = true;
		setRotation(Base1, 0F, 0F, 0F);
		Nozzle = new ModelRenderer(this, 0, 40);
		Nozzle.addBox(0F, 0F, 0F, 6, 2, 6);
		Nozzle.setRotationPoint(-3F, 8F, -3F);
		Nozzle.setTextureSize(128, 128);
		Nozzle.mirror = true;
		setRotation(Nozzle, 0F, 0F, 0F);
	}

	public void render(float f5) {
		Base3.render(f5);
		Spout.render(f5);
		Base2.render(f5);
		Base1.render(f5);
		Nozzle.render(f5);
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
