package redgear.brewcraft.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class SprayerDelayMessage implements IMessage{
	
	int dimension;
	int x;
	int y;
	int z;
	int value;
	
	public SprayerDelayMessage(){
		
	}
	
	public SprayerDelayMessage(TileEntity tile, int value){
		this.dimension = tile.getWorldObj().provider.dimensionId;
		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		if (buf == null)
			return;
		
		dimension = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (buf == null)
			return;
		
		buf.writeInt(dimension);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(value);
	}

}
