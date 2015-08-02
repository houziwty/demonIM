package netty.common.connection;

import netty.common.message.DemonMessage;
import netty.common.message.DemonRequest;
import netty.common.message.DemonResponse;
import netty.common.transaction.DemonTransaction;

public interface DemonDedicateConnectionProcessor {
	
	void processSendMessage(DemonMessage msg,final DemonTransaction trans);
	
	void processReceiveRequest(DemonRequest req);
	
	void processReceiveResponse(DemonResponse resp);

}
