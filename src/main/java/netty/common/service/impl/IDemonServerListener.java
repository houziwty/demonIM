package netty.common.service.impl;

import io.netty.channel.ChannelHandlerContext;

public interface IDemonServerListener {
	void create(ChannelHandlerContext ctx) throws Exception;
}
