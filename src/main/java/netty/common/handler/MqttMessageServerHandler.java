package netty.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.common.proto.message.AbstractMessage;
import netty.common.server.MQTTServerListener;

public class MqttMessageServerHandler extends SimpleChannelInboundHandler<AbstractMessage> {
	public static Logger LOGGER = LoggerFactory.getLogger(MqttMessageServerHandler.class);
	private MQTTServerListener listener;

	public MqttMessageServerHandler(MQTTServerListener listener) {
		this.listener = listener;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
		handleMessage(msg,ctx);
	}
	
	private void handleMessage(AbstractMessage msg,ChannelHandlerContext ctx){
		if (msg == null) {
			return;
		}
	}

}
