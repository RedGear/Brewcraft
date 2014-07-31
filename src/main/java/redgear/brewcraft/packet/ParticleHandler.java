package redgear.brewcraft.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redgear.brewcraft.blocks.sprayer.TileEntitySprayer;
import redgear.brewcraft.plugins.item.PotionPlugin;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

public class ParticleHandler {

	public static final String name = "Brewcraft|Particles";
	private static FMLEventChannel net;
	public static ParticleHandler inst;

	public static int POTION = 0;
	public static int MIST = 1;

	public static void register() {
		if (inst == null) {
			net = NetworkRegistry.INSTANCE.newEventDrivenChannel(name);

			inst = new ParticleHandler();
			net.register(inst);
		}
	}

	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) {
		SprayerDelayMessage message = new SprayerDelayMessage();
		message.fromBytes(event.packet.payload());
		
		if(message.dimension >= MinecraftServer.getServer().worldServers.length)
			return;
		
		World world = MinecraftServer.getServer().worldServers[message.dimension];
		TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
		
		if(tile instanceof TileEntitySprayer){
			TileEntitySprayer sprayer = (TileEntitySprayer) tile;
			sprayer.delay = message.value;
		}
		
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

		if (message.particle == POTION)
			for (int i = 0; i < 8; ++i)
				renderGlobal.spawnParticle(crackParticleName, message.x, message.y, message.z,
						random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);

		String particleName = getParticle(message.particle, message);

		for (int i = 0; i < 100; ++i) {
			velocityMultiplier = random.nextDouble() * message.particle == POTION ? 4.0F : 0.5F;
			distance = random.nextDouble() * Math.PI * 2.0D;
			velocityX = Math.cos(distance) * velocityMultiplier;
			velocityY = 0.01D + random.nextDouble() * 0.5D;
			velocityZ = Math.sin(distance) * velocityMultiplier;
			EntityFX entityfx = renderGlobal.doSpawnParticle(particleName, message.x + velocityX * 0.1D,
					message.y + 0.3D, message.z + velocityZ * 0.1D, velocityX, velocityY, velocityZ);

			if (entityfx != null) {

				float r = 0;
				float g = 0;
				float b = 0;

				if (message.color > 0) {
					r = (message.color >> 16 & 255) / 255.0F;
					g = (message.color >> 8 & 255) / 255.0F;
					b = (message.color >> 0 & 255) / 255.0F;
				}
				colorMultiplier = 0.75F + random.nextFloat() * 0.25F;
				entityfx.setRBGColorF(r * colorMultiplier, g * colorMultiplier, b * colorMultiplier);
				entityfx.multiplyVelocity((float) velocityMultiplier);
			}
		}
	}

	public static void send(World world, double x, double y, double z, int color, boolean instant, int particle) {
		ParticleMessage message = new ParticleMessage(x, y, z, color, instant, particle);
		ByteBuf buf = Unpooled.buffer();
		message.toBytes(buf);
		FMLProxyPacket packet = new FMLProxyPacket(buf, name);
		net.sendToAllAround(packet, new TargetPoint(world.provider.dimensionId, x, y, z, 32));
	}

	public static void send(World world, double x, double y, double z, Potion effect, int particle) {
		ParticleMessage message = new ParticleMessage(x, y, z, effect, particle);
		ByteBuf buf = Unpooled.buffer();
		message.toBytes(buf);
		FMLProxyPacket packet = new FMLProxyPacket(buf, name);
		net.sendToAllAround(packet, new TargetPoint(world.provider.dimensionId, x, y, z, 32));
	}
	
	public static void sendGuiSprayerPacket(TileEntitySprayer tile, int newDelay){
		SprayerDelayMessage message = new SprayerDelayMessage(tile, newDelay);
		ByteBuf buf = Unpooled.buffer();
		message.toBytes(buf);
		FMLProxyPacket packet = new FMLProxyPacket(buf, name);
		net.sendToServer(packet);
		
	}

	public String getParticle(int par1, ParticleMessage message) {
		if (par1 == POTION)
			return message.instant ? "instantSpell" : "spell";
		else if (par1 == MIST)
			return "smoke";
		return "";
	}

}
