package redgear.brewcraft.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import redgear.brewcraft.plugins.common.PotionPlugin;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

public class ParticleHandler {

	public static final String name = "Brewcraft|PotionEntity";
	private static FMLEventChannel net;
	public static ParticleHandler inst;

	public static void register() {
		if(inst == null){
			net = NetworkRegistry.INSTANCE.newEventDrivenChannel(name);
			
			inst = new ParticleHandler();
			net.register(inst);
		}
	}
	
	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) {
		
	}		
		
	@SubscribeEvent
	public void onClientPacket(ClientCustomPacketEvent event) {	
		ParticleMessage message = new ParticleMessage();
		message.fromBytes(event.packet.payload());
		
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
	}
	
	public static void send(double x, double y, double z, Potion effect){
		ParticleMessage message = new ParticleMessage(x, y, z, effect);
		ByteBuf buf = Unpooled.buffer();
		message.toBytes(buf);
		FMLProxyPacket packet = new FMLProxyPacket(buf, name);
		net.sendToAll(packet);
	}

}
