package com.demon.mqtt.proto.message;

import java.io.ByteArrayInputStream;

public class MessageInputStream implements Cloneable {

	public MessageInputStream(ByteArrayInputStream byteArrayInputStream,
			int msgLength, int lengthSize, boolean isOutbound) {
	}

	public DemonMessage readMessage() {
		return null;
	}

	public void close() {
		
	}

}
