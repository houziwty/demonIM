package netty.common.service;

import netty.common.handler.DemonHandler;
import netty.common.message.DemonMessageDecoder;
import netty.common.message.DemonMessageEncoder;
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
	private String ip;
	private int port;

	public DemonServer(String ip, int port, DemonServiceListener listener) {
		this.ip = ip;
		this.port = port;
		this.listener = listener;
	}

	@Override
	public void run(EventLoopGroup bossGroup, EventLoopGroup workerGroup) throws Exception {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast("framer",new DemonMessageDecoder(6000));
						pipeline.addLast("encoder",new DemonMessageEncoder());
						pipeline.addLast("handler",new DemonHandler(listener));
					}

				});
		channel = b.bind(ip,port).sync().channel();
	}

	@Override
	public void close() throws Exception {
		channel.closeFuture().sync();
	}

}
