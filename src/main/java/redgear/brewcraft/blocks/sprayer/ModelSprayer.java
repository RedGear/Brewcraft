package redgear.brewcraft.blocks.sprayer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSprayer extends ModelBase {

	ModelRenderer Base;
	ModelRenderer Top;

	public ModelSprayer() {
		textureWidth = 64;
		textureHeight = 64;

		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(0F, 0F, 0F, 16, 8, 16).setTextureSize(64, 64).setRotationPoint(-8F, 16F, -8F);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		Top = new ModelRenderer(this, 0, 40);
		Top.addBox(0F, 0F, 0F, 8, 8, 8).setTextureSize(64, 64).setRotationPoint(-4F, 8F, -4F);
		Top.mirror = true;
		setRotation(Top, 0F, 0F, 0F);
	}

	public void render(float f5) {
		Base.render(f5);
		Top.render(f5);
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
