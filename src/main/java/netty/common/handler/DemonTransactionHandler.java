package netty.common.handler;

import netty.common.connection.DemonDedicateConnectionProcessor;
import netty.common.message.DemonMessage;
import netty.common.message.DemonRequest;
import netty.common.message.DemonResponse;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

@SuppressWarnings("deprecation")
public class DemonTransactionHandler extends ChannelDuplexHandler {
	private DemonDedicateConnectionProcessor conn;

	public DemonTransactionHandler(DemonDedicateConnectionProcessor conn) {
		this.conn = conn;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		DemonMessage message = (DemonMessage) msg;
		if (message.isRequest())
			conn.processReceiveRequest((DemonRequest) message);
		else
			conn.processReceiveResponse((DemonResponse)message);

	}

}
