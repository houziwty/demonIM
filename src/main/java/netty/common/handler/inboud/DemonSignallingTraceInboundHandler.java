package netty.common.handler.inboud;

import netty.common.proto.message.DemonMessage;
import netty.common.proto.message.DemonRequest;
import netty.common.proto.message.DemonResponse;
import netty.common.tracer.DemonTracer;
import io.netty.channel.ChannelHandlerContext;

public class DemonSignallingTraceInboundHandler extends DemonInboundHandler {

	private static DemonTracer tracer = DemonTracer
			.getInstance(DemonSignallingTraceInboundHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		tracer.info("Connection has been connected. "
				+ ctx.channel().toString());
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		tracer.info("CinConnection has been disconnected. "
				+ ctx.channel().toString());
		ctx.fireChannelInactive();
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		DemonMessage message = (DemonMessage) msg;
		if (message.isRequest())
			tracer.info("CinRequest has been received."
					+ ctx.channel().toString(), (DemonRequest) message);
		else
			tracer.info("CinResponse has been received."
					+ ctx.channel().toString(), (DemonResponse) message);
		ctx.fireChannelRead(msg);
	}
}
