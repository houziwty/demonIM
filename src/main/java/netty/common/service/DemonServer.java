package netty.common.service;

import netty.common.service.impl.IDemonSever;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DemonServer implements IDemonSever {
	private DemonServiceListener listener;
	private Channel channel = null;
	private int port;

	public DemonServer(int port, DemonServiceListener listener) {
		this.port = port;
		this.listener = listener;
	}

	@Override
	public void run(EventLoopGroup bossGroup, EventLoopGroup workerGroup)
			throws Exception {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(null);
					}

				});
		channel = b.bind(port).sync().channel();
	}

	@Override
	public void close() throws Exception {
		channel.closeFuture().sync();
	}

}
