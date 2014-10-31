package redgear.brewcraft.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class PotionExtension extends Potion {

	public PotionExtension(String name, int id, boolean isBad, int particleColor) {
		super(id, isBad, particleColor);
		setPotionName("potion.brewcraft." + name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(
				"redgear_brewcraft:textures/potion/icons.png"));
		return super.getStatusIconIndex();
	}

	@Override
	/**
	 * Perform this potion's normal every-tick effect. 
	 */
	public abstract void performEffect(EntityLivingBase living, int strength);

	/**
	 * Hits the provided entity with this potion's instant effect.
	 */
	@Override
	public void affectEntity(EntityLivingBase thrower, EntityLivingBase reciver, int potionStrength,
			double distanceFromSplash) {

	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
}
