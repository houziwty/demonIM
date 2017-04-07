package com.demon.im.mqtt.proto.message;

import io.netty.buffer.Unpooled;
import com.demon.im.mqtt.util.DemonLinkedNode;

public class DemonBody extends DemonHeader {
	/** 
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = -2227366637373373753L;
	
	DemonLinkedNode<DemonBody> Node;

	public DemonBody() {
		super(DemonHeaderType.Body);
	}

	public DemonBody(byte[] bodyValue) {
		super(DemonHeaderType.Body, bodyValue);
	}

	public DemonBody(String bodyValue) {
		super(DemonHeaderType.Body, bodyValue);
	}

	public void setValue(byte[] value) {
		if (value == null) {
			this.value = null;
		} else if (value.length > 0xffff) {
			this.value = null;
		} else {
			this.value = Unpooled.copiedBuffer(value);
		}
	}

	public String toString() {
		if (getValueLength() > 255)
			return "Body: TOOOOOOOOOOOOLOOOOOOONG";
		return super.toString();
	}
	public String toHexString(){
		if (getValueLength() > 255)
			return "Body: TOOOOOOOOOOOOLOOOOOOONG";
		return super.toString(false);
	}

}
