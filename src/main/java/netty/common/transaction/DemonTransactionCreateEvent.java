package netty.common.transaction;

public interface DemonTransactionCreateEvent {

	void onTransactionCreated(DemonTransaction trans);

}
