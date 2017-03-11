package netty.common.handler.codec;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import netty.common.proto.message.DemonMessage;

public class DemonEncoder extends MessageToMessageEncoder<DemonMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, DemonMessage msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
