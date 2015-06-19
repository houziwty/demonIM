package netty.common.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DemonMessageEncoder extends MessageToByteEncoder<DemonMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, DemonMessage msg,
			ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		if(!(msg instanceof DemonMessage))
			throw new Exception("message type is error");
		
		byte[]data=null;//=((DemonMessage) msg).
		
		out.writeBytes(data);
	}

}
