package netty.common.stack;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.nio.NioEventLoopGroup;
import netty.common.connection.DefaultDemonDedicateConnection;
import netty.common.connection.DemonDedicateConnection;
import netty.common.tracer.DemonTracer;

public class DemonStack {

	private static DemonTracer _tracer = DemonTracer
			.getInstance(DemonStack.class);
	private static DemonStack _instance;
	private DemonStackConfiguration _config;
	private NioEventLoopGroup _group;
	private ConcurrentHashMap<String, DemonDedicateConnection> _connections;
	static {
		try {
			_instance = new DemonStack(new DemonStackConfiguration());
		} catch (Throwable t) {

		}
	}

	public static DemonStack getInstance() {
		return _instance;
	}

	public DemonStack(DemonStackConfiguration config) {
		_config=config;
		_connections=new ConcurrentHashMap<String, DemonDedicateConnection>();
		if(config.getWorkThreadCount()==0)
			_group=new NioEventLoopGroup();
		else
			_group=new NioEventLoopGroup(config.getWorkThreadCount());
	}
	public synchronized DemonDedicateConnection createConnection(String key){
		if(_config.getModel()!=DemonStackModel.Dedicate)
			throw new UnsupportedOperationException("The stack's mode MUST be Dedicate.");
		DemonDedicateConnection conn=_connections.get(key);
		if(conn==null)
			_connections.put(key, conn=new DefaultDemonDedicateConnection(key,_config,_group));
		return conn;
	}
	public void shutdown(){
		_group.shutdownGracefully();
	}

}
