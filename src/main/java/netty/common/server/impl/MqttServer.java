package netty.common.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.common.server.ConnectionListener;
import netty.common.server.DemonSever;

public class MqttServer implements DemonSever {
private ConnectionListener listener;
private Channel channel=null;
private String ip;
private int port;

public MqttServer(String ip, int port, ConnectionListener listener){
	this.ip = ip;
	this.port = port;
	this.listener = listener;	
}

@Override
public void run(EventLoopGroup bossGroup, EventLoopGroup workerGroup) throws Exception {
ServerBootstrap b=new ServerBootstrap();
b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
	}
});
channel=b.bind(ip,port).sync().channel();

}

@Override
public void close() throws Exception {
	channel.closeFuture().sync();	
}

}
