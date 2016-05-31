package netty.common.stack;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.nio.NioEventLoopGroup;
import netty.common.connection.DefaultDemonDedicateConnection;
import netty.common.connection.DemonDedicateConnection;
import netty.common.tracer.DemonTracer;

public class DemonStack {

	private static DemonTracer _tracer = DemonTracer
			.getInstance(DemonStack.class);
	private static DemonStack instance;
	private DemonStackConfiguration config;
	private NioEventLoopGroup group;
	private ConcurrentHashMap<String, DemonDedicateConnection> _connections;
	static {
		try {
			instance = new DemonStack(new DemonStackConfiguration());
		} catch (Throwable t) {

		}
	}

	public static DemonStack getInstance() {
		return instance;
	}

	public DemonStack(DemonStackConfiguration config) {
		config=config;
		_connections=new ConcurrentHashMap<String, DemonDedicateConnection>();
		if(config.getWorkThreadCount()==0)
			group=new NioEventLoopGroup();
		else
			group=new NioEventLoopGroup(config.getWorkThreadCount());
	}
	public synchronized DemonDedicateConnection createConnection(String key){
		if(config.getModel()!=DemonStackModel.Dedicate)
			throw new UnsupportedOperationException("The stack's mode MUST be Dedicate.");
		DemonDedicateConnection conn=_connections.get(key);
		if(conn==null)
			_connections.put(key, conn=new DefaultDemonDedicateConnection(key,config,group));
		return conn;
	}
	public void shutdown(){
		group.shutdownGracefully();
	}

}
