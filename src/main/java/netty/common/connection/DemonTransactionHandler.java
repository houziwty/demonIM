package netty.common.connection;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

@SuppressWarnings("deprecation")
public class DemonTransactionHandler extends ChannelDuplexHandler {

	public DemonTransactionHandler(DemonDedicateConnection conn) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {

	}

}
