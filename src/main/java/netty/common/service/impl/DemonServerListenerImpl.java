package netty.common.service.impl;

import io.netty.channel.ChannelHandlerContext;

public interface DemonServerListenerImpl {
	void create(ChannelHandlerContext ctx) throws Exception;
}
