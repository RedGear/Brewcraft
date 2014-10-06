package redgear.brewcraft.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redgear.brewcraft.blocks.sprayer.TileEntitySprayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

public class SprayerDelayHandler {
	
	public static final String name = "Brewcraft|SprayerDelay";
	
	private static FMLEventChannel net;
	public static SprayerDelayHandler inst;
	
	public static void register() {
		if (inst == null) {
			net = NetworkRegistry.INSTANCE.newEventDrivenChannel(name);

			inst = new SprayerDelayHandler();
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
	
	public static void sendGuiSprayerPacket(TileEntitySprayer tile, int newDelay){
		SprayerDelayMessage message = new SprayerDelayMessage(tile, newDelay);
		ByteBuf buf = Unpooled.buffer();
		message.toBytes(buf);
		FMLProxyPacket packet = new FMLProxyPacket(buf, name);
		net.sendToServer(packet);
		
	}

}
