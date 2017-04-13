package com.demon.im.mqtt.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import com.demon.im.mqtt.handler.MqttMessageServerHandler;
import com.demon.im.mqtt.proto.code.MQTTDecoder;
import com.demon.im.mqtt.proto.code.MQTTEncoder;
import com.demon.im.mqtt.listener.MQTTServerListener;
import com.demon.im.mqtt.server.DemonSever;

public class MqttServer implements DemonSever {
	private MQTTServerListener listener;
	private Channel channel = null;
	private String ip;
	private int port;

	public MqttServer(String ip, int port, MQTTServerListener listener) {
		this.ip = ip;
		this.port = port;
		this.listener = listener;
	}

	@Override
	public void run(EventLoopGroup bossGroup, EventLoopGroup workerGroup) throws Exception {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline=ch.pipeline();
						pipeline.addLast("framer",new MQTTDecoder());
						pipeline.addLast("encoder",new MQTTEncoder());
						pipeline.addLast(new ReadTimeoutHandler(300));
						pipeline.addLast("handler", new MqttMessageServerHandler(listener));

					}
				});
		channel = b.bind(ip, port).sync().channel();

	}

	@Override
	public void close() throws Exception {
		channel.closeFuture().sync();
	}

}
