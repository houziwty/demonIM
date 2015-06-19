package netty.common.message;

import netty.common.util.DemonLinkedNode;

public class DemonBody extends DemonHeader {
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
			this.value = value;
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
