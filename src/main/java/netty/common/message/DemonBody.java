package netty.common.message;

import netty.common.util.DemonLinkedNode;

public class DemonBody extends DemonHeader {
	DemonLinkedNode<DemonBody>Node;
	public DemonBody()
	{
		super(DemonHeaderType.Body);
	}
	public DemonBody(byte[]bodyValue){
		super(DemonHeaderType.Body,bodyValue);
	}

}
