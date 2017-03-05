package netty.common.handler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.common.service.DemonServiceListener;

public  class DemonHandler extends SimpleChannelInboundHandler<Object> {
	DemonServiceListener listener;
	public DemonHandler(DemonServiceListener listener) {
		this.listener=listener;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		
	}

}


