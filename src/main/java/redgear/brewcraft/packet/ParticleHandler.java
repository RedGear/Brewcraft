package redgear.brewcraft.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import redgear.brewcraft.core.Brewcraft;
import redgear.brewcraft.plugins.item.PotionPlugin;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import redgear.core.world.Location;

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
	public void onClientPacket(ClientCustomPacketEvent event) {
		ParticleMessage message = new ParticleMessage(event.packet);

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

			ForgeDirection direction = ForgeDirection.getOrientation(message.direction);

			velocityX = Math.cos(distance) * velocityMultiplier + (0.75 * direction.offsetX);
			velocityY = 0.5 * random.nextDouble() + (0.75 * direction.offsetY);
			velocityZ = Math.sin(distance) * velocityMultiplier + (0.75 * direction.offsetZ);






//			Brewcraft.inst.logDebug("Starting x: ", velocityX, ", y: ", velocityY, ", z: ", velocityZ);
//			Brewcraft.inst.logDebug("Final x: ", velocityX, ", y: ", velocityY, ", z: ", velocityZ);

			EntityFX entityfx = renderGlobal.doSpawnParticle(particleName,
					message.x + (0.5 * direction.offsetX),
					message.y + (0.5 * direction.offsetY),
					message.z + (0.5 * direction.offsetZ),
					velocityX, velocityY, velocityZ);

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
		send(world, x, y, z, color, instant, particle, ForgeDirection.UP);
	}

	public static void send(World world, double x, double y, double z, int color, boolean instant, int particle, ForgeDirection direction) {
		send(world, new ParticleMessage(x, y, z, color, instant, particle, direction.ordinal()));
	}

	public static void send(World world, double x, double y, double z, Potion effect, int particle) {
		send(world, x, y, z, effect, particle, ForgeDirection.UP);
	}


	public static void send(World world, double x, double y, double z, Potion effect, int particle, ForgeDirection direction) {
		send(world, new ParticleMessage(x, y, z, effect, particle, direction.ordinal()));
	}

	private static void send(World world, ParticleMessage message){
		ByteBuf buf = Unpooled.buffer();
		message.toBytes(buf);
		FMLProxyPacket packet = new FMLProxyPacket(buf, name);
		net.sendToAllAround(packet, new TargetPoint(world.provider.dimensionId, message.x, message.y, message.z, 32));
	}

	public String getParticle(int par1, ParticleMessage message) {
		if (par1 == POTION)
			return message.instant ? "instantSpell" : "spell";
		else if (par1 == MIST)
			return "smoke";
		return "";
	}

}
