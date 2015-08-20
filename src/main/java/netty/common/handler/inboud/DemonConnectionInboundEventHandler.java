package netty.common.handler.inboud;

import java.net.SocketAddress;

import netty.common.tracer.DemonTracer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class DemonConnectionInboundEventHandler extends DemonInboundHandler {
	private static DemonTracer _tracer = DemonTracer
			.getInstance(DemonConnectionInboundEventHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		_tracer.debug("TCP Connection has been connected "
				+ ctx.channel().toString());
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		_tracer.debug("TCP Connection has been disconnected. " + ctx.channel().toString());
		ctx.fireChannelInactive();
	}

}
