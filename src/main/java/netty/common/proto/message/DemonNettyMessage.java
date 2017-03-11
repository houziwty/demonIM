package netty.common.proto.message;

public abstract class DemonNettyMessage {
	public enum Type {
		CONNECT(1), CONNACK(2), PUBLISH(3), PUBACK(4), QUERY(5), QUERYACK(6), // PUBREL
		QUERYCON(7), // PUBCOMP
		SUBSCRIBE(8), SUBACK(9), UNSUBSCRIBE(10), UNSUBACK(11), PINGREQ(12), PINGRESP(
				13), DISCONNECT(14);
		final private int val;

		Type(int val) {
			this.val = val;
		}

		static Type valueOf(int i) {
			for (Type t : Type.values()) {
				if (t.val == i)
					return t;
			}
			return null;
		}
	}
}
