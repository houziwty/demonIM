package netty.common.transaction;

import java.util.concurrent.atomic.AtomicLong;

import netty.common.message.DemonRequest;
import netty.common.message.DemonResponse;
import netty.common.tracer.DemonTracer;

public class DemonTransaction {

	private DemonRequest _reques;
	private DemonResponse _response;
	private String _key;
	private boolean _isFake;
	private long _updatetime;
	private DemonTransaction _stateTransaction;
	private Object _stateObject;
	private DemonTransactionManager _maager;
	public DemonTransactionEvent  TransactionEvent;
	private static AtomicLong cseq=new AtomicLong();
	private static DemonTracer tracer=DemonTracer.getInstance(DemonTransaction.class,true);
}
