package netty.common.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.common.proto.message.AbstractMessage;
import netty.common.server.ConnectionListener;

public class MqttMessageServerHandler extends SimpleChannelInboundHandler<AbstractMessage> {

	public MqttMessageServerHandler(ConnectionListener listener) {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
		
	}

}
