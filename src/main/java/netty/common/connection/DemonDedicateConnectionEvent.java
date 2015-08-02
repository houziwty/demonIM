package netty.common.connection;

public interface DemonDedicateConnectionEvent {

	void onConnected(DemonDedicateConnection conn, Object attachment);

	void onDisconnected(DemonDedicateConnection conn, Object attachment);

}
