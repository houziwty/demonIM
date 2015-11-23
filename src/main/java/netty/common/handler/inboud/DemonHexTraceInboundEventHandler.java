package netty.common.handler.inboud;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import netty.common.tracer.DemonTracer;

public class DemonHexTraceInboundEventHandler extends DemonInboundHandler {
	private static DemonTracer tracer = DemonTracer
			.getInstance(DemonHexTraceInboundEventHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		tracer.info("CinConnection has received data."
				+ ctx.channel().toString() + "\r\n" + ByteBufUtil.hexDump(buf));
		ctx.fireChannelRead(msg);
	}
}
