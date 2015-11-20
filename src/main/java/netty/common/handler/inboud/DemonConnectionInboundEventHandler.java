package netty.common.handler.inboud;


import netty.common.tracer.DemonTracer;
import io.netty.channel.ChannelHandlerContext;

public class DemonConnectionInboundEventHandler extends DemonInboundHandler {
	private static DemonTracer _tracer = DemonTracer
			.getInstance(DemonConnectionInboundEventHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		_tracer.debug("TCP Connection has been connected"
				+ ctx.channel().toString());
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		_tracer.debug("TCP Connection has been disconnected"+ctx.channel().toString());
		ctx.fireChannelInactive();
	}

}
