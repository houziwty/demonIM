package netty.common.connection;

import netty.common.stack.DemonStackConfiguration;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class DemonListener extends ChannelInitializer<SocketChannel> {
	
	public DemonListener(DemonStackConfiguration config){
		
	}

	@Override
	protected void initChannel(SocketChannel sock) throws Exception {
		
	}

}
