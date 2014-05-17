package redgear.brewcraft.packet;

import net.minecraft.potion.Potion;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class ParticleMessage implements IMessage {

	public double x;
	public double y;
	public double z;
	public int color;
	public boolean instant;
	public int particle;

	public ParticleMessage() {

	}

	public ParticleMessage(FMLProxyPacket packet) {
		if (packet != null)
			fromBytes(packet.payload());
	}

	public ParticleMessage(double x, double y, double z, int color, boolean instant, int particle) {
		this.x = x;
		this.y = y;
		this.z = z;
		if (color > 0) {
			this.color = color;
			this.instant = instant;
		}
		this.particle = particle;
	}

	public ParticleMessage(double x, double y, double z, Potion effect, int particle) {
		this(x, y, z, effect == null ? 0 : effect.getLiquidColor(), effect == null ? false : effect.isInstant(), particle);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		if (buf == null)
			return;

		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		color = buf.readInt();
		instant = buf.readBoolean();
		particle = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (buf == null)
			return;

		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(color);
		buf.writeBoolean(instant);
		buf.writeInt(particle);
	}

}
