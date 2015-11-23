package netty.common.handler.inboud;

import netty.common.tracer.DemonTracer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@SuppressWarnings("deprecation")
public class DemonInboundHandler extends ChannelInboundHandlerAdapter {
	private static DemonTracer tracer = DemonTracer
			.getInstance(DemonInboundHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		tracer.error("DemonConnection" + ctx.toString()
				+ " encounters an exception.", cause);
	}

}
