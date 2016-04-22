package netty.common.transaction;

import java.util.concurrent.atomic.AtomicLong;

import netty.common.connection.DemonDedicateConnection;
import netty.common.connection.DemonDedicateConnectionProcessor;
import netty.common.message.DemonRequest;
import netty.common.message.DemonResponse;
import netty.common.tracer.DemonTracer;

/** 
* @ClassName: DemonTransaction 
* @Description:  
* @author wangtianyu 
* @date 2016年4月22日 下午9:40:51 
*  
*/
public class DemonTransaction {

	private static AtomicLong cseq=new AtomicLong(0);
	public DemonTransactionEvent  event;
	
	private DemonRequest request;
	private DemonResponse response;
	private int timeout;
	private DemonDedicateConnectionProcessor conn;
	private long sendRequestTime;
	private Object attachment;
	
	
	private static DemonTracer tracer=DemonTracer.getInstance(DemonTransaction.class);
	
	private DemonTransaction(DemonRequest request,int timeout){
		this.request=request;
		this.request.setParentTrans(this);
		
	}
	
	
	public void doSendRequestFailed() {
		
	}
	
	public void setDemonConnection(
			DemonDedicateConnection conn) {
		
	}
	public void receiveResponse(DemonResponse resp) {
		
	}
}
