package redgear.brewcraft.entity;

import net.minecraft.potion.Potion;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class ParticleMessage implements IMessage{
	
	
	
	public double	x;
	public double	y;
	public double	z;
	public int		color;
	public boolean	instant;
	
	public ParticleMessage(){
		
	}
	
	public ParticleMessage(double x, double y, double z, int color, boolean instant){
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
		this.instant = instant;
	}
	
	public ParticleMessage(double x, double y, double z, Potion effect){
		this(x, y, z, effect.getLiquidColor(), effect.isInstant());
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		color = buf.readInt();
		instant = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(color);
		buf.writeBoolean(instant);
	}
	
}
