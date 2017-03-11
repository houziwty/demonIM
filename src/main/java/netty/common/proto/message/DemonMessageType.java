package netty.common.proto.message;

public enum DemonMessageType {
	Request(1), Response(2);
	final private int val;

	DemonMessageType(int val) {
		this.val = val;
	}

	public static DemonMessageType valueOf(int i) {
		for (DemonMessageType m : DemonMessageType.values()) {
			if (m.val == i)
				return m;
		}
		return null;
	}
}
