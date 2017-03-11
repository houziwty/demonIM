package netty.common.connection;

import java.net.SocketAddress;

import netty.common.proto.message.DemonRequest;
import netty.common.transaction.DemonTransaction;
import netty.common.transaction.DemonTransactionCreateEvent;

public interface DemonDedicateConnection {
	
	String getKey();

	void registerDemonConnectionEvent(DemonDedicateConnectionEvent event);
	
	void registerDemonTransactionCreated(DemonTransactionCreateEvent event);
	
	void connect(String ip,int port);
	
	void connect(SocketAddress address);
	
	void connect(SocketAddress address,Object attachment);
	
	void disconnect();
	
	void disconnect(Object attachment);
	
	DemonTransaction createTransaction(DemonRequest req);
	
	DemonTransaction createTransaction(DemonRequest req,int timeout);
		
}
