package netty.common.connection;

import netty.common.proto.message.DemonMessage;
import netty.common.proto.message.DemonRequest;
import netty.common.proto.message.DemonResponse;
import netty.common.transaction.DemonTransaction;

public interface DemonDedicateConnectionProcessor {
	
	void processSendMessage(DemonMessage msg,final DemonTransaction trans);
	
	void processReceiveRequest(DemonRequest msg);
	
	void processReceiveResponse(DemonResponse msg);

}
