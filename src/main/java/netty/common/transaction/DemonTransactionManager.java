package netty.common.transaction;

import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import netty.common.connection.DefaultDemonDedicateConnection;
import netty.common.message.DemonRequest;

public class DemonTransactionManager {

	private static Timer timer;
	private DefaultDemonDedicateConnection conn;
	private ConcurrentHashMap<String,DemonTransaction>trans;
	private ConcurrentHashMap<Integer,LinkedList<DemonTransaction>>sortTrans;
	
	public DemonTransactionManager(
			DefaultDemonDedicateConnection defaultDemonDedicateConnection) {
	}

	public void reset() {
		
	}

	public void addTransaction(DemonTransaction trans) {
		
	}

	public void removeTransaction(Object key) {
		
	}

	public DemonTransaction createTransaction(DemonRequest req) {
		return null;
	}

	public DemonTransaction getTransaction(String key) {
		return null;
	}

	public DemonTransaction createTransaction(DemonRequest req, int timeout) {
		return null;
	}

}
