package netty.common.connection;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.google.common.util.concurrent.UncheckedExecutionException;

import netty.common.message.DemonMessage;
import netty.common.message.DemonRequest;
import netty.common.message.DemonResponse;
import netty.common.stack.DemonStackConfiguration;
import netty.common.stack.DemonStackModel;
import netty.common.tracer.DemonTracer;
import netty.common.transaction.DemonTransaction;
import netty.common.transaction.DemonTransactionCreateEvent;
import netty.common.transaction.DemonTransactionManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DefaultDemonDedicateConnection extends
		ChannelInitializer<SocketChannel> implements DemonDedicateConnection,
		DemonDedicateConnectionProcessor {

	private static DemonTracer _tracer = DemonTracer
			.getInstance(DefaultDemonDedicateConnection.class);

	private String _key;

	private DemonStackConfiguration _config;

	private DemonTransactionManager _transMgr;

	private DemonDedicateConnectionEvent _connEvent;

	private DemonTransactionCreateEvent _createEvent;

	private NioEventLoopGroup _group;

	private Channel _channel;

	public DefaultDemonDedicateConnection(String key,
			DemonStackConfiguration config, NioEventLoopGroup group) {
		_key = key;
		_config = config;
		_group = group;
		_transMgr = new DemonTransactionManager(this);
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return _key;
	}

	@Override
	public void registerDemonConnectionEvent(DemonDedicateConnectionEvent event) {
		if (_config.getModel() != DemonStackModel.Dedicate)
			throw new UnsupportedOperationException(
					"The stack's mode MUST be Dedicate.");
		_connEvent = event;
	}

	@Override
	public void registerDemonTransactionCreated(
			DemonTransactionCreateEvent event) {
		if (_config.getModel() != DemonStackModel.Dedicate)
			throw new UnsupportedOperationException(
					"The stack's mode MUST be Dedicate.");
		_createEvent = event;
	}

	@Override
	public void connect(String ip, int port) {
		// TODO Auto-generated method stub
		connect(new InetSocketAddress(ip, port));
	}

	@Override
	public void connect(SocketAddress address) {
		// TODO Auto-generated method stub
		connect(address, null);
	}

	@Override
	public void connect(SocketAddress address, final Object attachment) {
		// TODO Auto-generated method stub
		Bootstrap b = new Bootstrap();
		b.group(_group);
		b.channel(NioSocketChannel.class);
		b.handler(this);
		b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60 * 1000);
		ChannelFuture f = b.connect(address);
		f.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess())
					processConnectionConnected(future.channel(), attachment);
				else
					processDisconnectionConnected(attachment);

			}
		});

	}

	// 处理连接
	protected void processConnectionConnected(Channel channel, Object attachment) {
		_channel = channel;
		if (_connEvent != null)
			_connEvent.onConnected(this, attachment);
	}

	protected void processDisconnectionConnected(Object attachment) {
		if (_connEvent != null)
			_connEvent.onDisconnected(this, attachment);
		_transMgr.reset();
		_channel = null;
	}

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
        // ChannelPipleine line
	}

	@Override
	public void disconnect() {
		disconnect(null);
	}

	@Override
	public void disconnect(Object attachment) {
		if(_channel==null)
			return;
		ChannelFuture f=_channel.disconnect();
		f.addListener(new ChannelFutureListener(){

			@Override
			public void operationComplete(ChannelFuture arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}});

	}

	@Override
	public DemonTransaction createTransaction(DemonRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DemonTransaction createTransaction(DemonRequest req, int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processSendMessage(DemonMessage msg, DemonTransaction trans) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processReceiveRequest(DemonRequest req) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processReceiveResponse(DemonResponse resp) {
		// TODO Auto-generated method stub

	}

}
