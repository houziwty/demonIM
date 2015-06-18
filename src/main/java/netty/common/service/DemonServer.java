package netty.common.service;

import netty.common.service.impl.DemonSeverImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DemonServer implements DemonSeverImpl {
	private Channel channel = null;
    private int port;
	@Override
	public void run(EventLoopGroup bossGroup, EventLoopGroup workerGroup)throws Exception{
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChildChannelHandler());
		channel=b.bind(port).sync().channel();
	}

	@Override
	public void close() throws Exception {
		channel.closeFuture().sync();
	}

}
