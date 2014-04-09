package redgear.brewcraft.entity;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.Item;
import redgear.brewcraft.plugins.common.PotionPlugin;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class ParticleHandler implements IMessageHandler<ParticleMessage, IMessage> {

	public static SimpleNetworkWrapper net;

	public static void register() {
		if(net == null){
			net = NetworkRegistry.INSTANCE.newSimpleChannel("Brewcraft|PotionEntity");
			net.registerMessage(ParticleHandler.class, ParticleMessage.class, 0, Side.SERVER);
		}
	}

	@Override
	public IMessage onMessage(ParticleMessage message, MessageContext ctx) {
		RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
		Random random = Minecraft.getMinecraft().theWorld.rand;
		
		
		String crackParticleName = "iconcrack_" + Item.getIdFromItem(PotionPlugin.potions);

		double distance;
		float colorMultiplier;
		double velocityX;
		double velocityY;
		double velocityZ;
		double velocityMultiplier;

		for (int i = 0; i < 8; ++i)
			renderGlobal.spawnParticle(crackParticleName, message.x, message.y, message.z,
					random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);

		float r = (message.color >> 16 & 255) / 255.0F;
		float g = (message.color >> 8 & 255) / 255.0F;
		float b = (message.color >> 0 & 255) / 255.0F;
		String particleName = message.instant ? "instanceSpell" : "spell";

		for (int i = 0; i < 100; ++i) {
			velocityMultiplier = random.nextDouble() * 4.0D;
			distance = random.nextDouble() * Math.PI * 2.0D;
			velocityX = Math.cos(distance) * velocityMultiplier;
			velocityY = 0.01D + random.nextDouble() * 0.5D;
			velocityZ = Math.sin(distance) * velocityMultiplier;
			EntityFX entityfx = renderGlobal.doSpawnParticle(particleName, message.x + velocityX * 0.1D,
					message.y + 0.3D, message.z + velocityZ * 0.1D, velocityX, velocityY, velocityZ);

			if (entityfx != null) {
				colorMultiplier = 0.75F + random.nextFloat() * 0.25F;
				entityfx.setRBGColorF(r * colorMultiplier, g * colorMultiplier, b * colorMultiplier);
				entityfx.multiplyVelocity((float) velocityMultiplier);
			}
		}

		//No reply for this.
		return null;
	}

}
