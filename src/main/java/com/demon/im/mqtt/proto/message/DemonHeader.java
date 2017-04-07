package com.demon.im.mqtt.proto.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.demon.im.mqtt.util.DemonLinkedNode;
import com.demon.im.mqtt.util.TextUtil;

public class DemonHeader implements Cloneable, Serializable {

	/**
	 * 验证版本一致性
	 */
	private static final long serialVersionUID = 5474969772337149469L;

	private static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	DemonLinkedNode<DemonHeader> Node;
	byte type;
	ByteBuf value;

	public DemonHeader(byte type) {
		this.setType(type);
	}

	public DemonHeader(byte type, byte[] value) {
		this.setType(type);
		this.setValue(value);
	}

	public DemonHeader(byte type, ByteBuf buf) {
		this.setType(type);
		this.setByteBuf(buf);
	}

	public DemonHeader(byte type, long value) {
		this.setType(type);
		this.setInt64(value);
	}

	public DemonHeader(byte type, String value) {
		this.setType(type);
		this.setString(value);
	}

	public DemonHeader(byte type, byte event) {
		this(type, new byte[] { event });
	}

	public long getInt64() {
		if (value == null || value.readableBytes() > 8) {
			return -1;
		}
		byte[] buff = new byte[8];
		int i = 7;
		value.resetReaderIndex();
		while (value.isReadable()) {
			buff[i--] = value.readByte();
		}
		return ByteBuffer.wrap(buff).getLong();
	}

	public String toString(boolean printBody) {
		StringBuffer sb = new StringBuffer();
		sb.append((this.getType() == DemonHeaderType.Unknown) ? String.format(
				"%02X", type) : DemonHeaderType.get(this.getType()) + "["
				+ String.format("%02X", type) + "]");
		if (getValueLength() > 0) {
			sb.append(" : (Long)");
			sb.append(this.getInt64());
			sb.append("|(HexString)");
			value.resetReaderIndex();
			if (printBody) {
				sb.append("|(String)");
				sb.append(this.getString());
			}
		} else {
			sb.append(" : (Null)");
		}
		sb.append(TextUtil.RETURN);
		return sb.toString();
	}

	public String toString() {
		return this.toString(true);
	}

	public String getString() {
		if (this.getValueLength() > 0) {
			return value.toString(DEFAULT_CHARSET);
		} else {
			return TextUtil.EmptyString;
		}
	}

	public String getHexString() {
//		StringBuffer sb = new StringBuffer();
//		if (getValueLength() > 0) {
//			for (byte b : value) {
//				sb.append(String.format("%02X", b));
//			}
//		}
//		return sb.toString();
		return this.value==null?"":ByteBufUtil.hexDump(this.value);
	}

	public boolean isNotNullValue() {
		return value != null;
	}

	public boolean isNullValue() {
		return value == null;
	}

	public void setType(byte typebyte) {
		this.type = typebyte;
	}

	public byte getType() {
		return type;
	}

	public byte getTypeByte() {
		return type;
	}

	public boolean isTypeOf(byte headerType) {
		return headerType == this.type;
	}

	public void setValue(byte[] value) {
		if (value != null && value.length > 255) {
			this.value = null;
		} else {
			this.value =Unpooled.copiedBuffer(value);
		}
	}

	public void setString(String value) {
//		try {
//			if (value == null)
//				this.setValue(TextUtil.EmptyString.getBytes("UTF-8"));
//			else
//				this.setValue(value.getBytes("UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			this.value = null;
//		}
		this.value = Unpooled.wrappedBuffer(value.getBytes(DEFAULT_CHARSET));
	}

	public void setInt64(long value) {
		if (value != 0) {
			int zeros = Long.numberOfLeadingZeros(value);
			int length = 8 - zeros / 8;
			byte[] rawValue = new byte[length];
			for (int i = 0; i < length; i++) {
				rawValue[i] = (byte) (value >>> ((i) * 8));
			}
			this.setValue(rawValue);
		} else {
			this.setValue(new byte[] { (byte) 0 });
		}
	}

	public void setByteBuf(ByteBuf buf) {
		this.value = buf;
	}

	public byte[] getValue() {
		return value == null ? null : value.array();
	}
	public ByteBuf getBuf() {
		return this.value;
	}

	public int getValueLength() {
		if (value == null){
			return 0;
		}
		else{
			value.resetReaderIndex();
			return value.readableBytes();
		}
	}

	@Override
	public DemonHeader clone() {
		return new DemonHeader(this.type, this.value == null ? null
				: this.value.copy());
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof DemonHeader))
			return false;
		DemonHeader header=(DemonHeader)obj;
		if(header.getType()!=type)
			return false;
		if(header.getBuf()==null&&header.getBuf()!=null){
			return false;
		}
		return this.value.compareTo(header.value)==0;
	}

}
