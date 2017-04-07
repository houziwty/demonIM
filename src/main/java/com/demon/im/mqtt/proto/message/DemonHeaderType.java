package com.demon.im.mqtt.proto.message;

import java.lang.reflect.Field;
import java.util.HashMap;

public class DemonHeaderType {




	public static final byte From = (byte) 0x01;
	public static final byte To = (byte) 0x02;
	public static final byte CallId = (byte) 0x03;
	public static final byte Csequence = (byte) 0x04;
	public static final byte MessageID = (byte) 0x05;
	public static final byte DateTime = (byte) 0x06;
	public static final byte Token = (byte) 0x07;
	public static final byte Password = (byte) 0x08;
	public static final byte Credential = (byte) 0x09;
	public static final byte Type = (byte) 0x0A;
	public static final byte MobileNo = (byte) 0x0B;
	public static final byte Expire = (byte) 0x0C;
	public static final byte Event = (byte) 0x0D;
	public static final byte Route = (byte) 0x0E;
	public static final byte RecordRoute = (byte) 0x0F;
	public static final byte Fpid = (byte) 0x10;
	public static final byte Tpid = (byte) 0x11;
	public static final byte Key = (byte) 0x12;
	public static final byte Status = (byte) 0x13;
	public static final byte AppleDeviceToken = (byte) 0x14;
	public static final byte Version = (byte) 0x15;
	public static final byte Index = (byte) 0x16;
	public static final byte Name = (byte) 0x17;
	public static final byte SubVersion = (byte) 0x18;
	public static final byte Email = (byte) 0x19;
	public static final byte Language = (byte) 0x1A;
	public static final byte ServerData = (byte) 0xFB;
	public static final byte ServerPid = (byte) 0xFC;
	public static final byte ServerKey = (byte) 0xFD;
	public static final byte Unknown = (byte) 0xFE;
	public static final byte Body = (byte) 0xFF;
	public static final byte End = (byte) 0x00;

	public static HashMap<Byte, String> _map;
	static {
		try {
			Class<?> headertype = Class.forName(DemonHeaderType.class
					.getCanonicalName());
			Field[] fields = headertype.getFields();
			_map = new HashMap<Byte, String>();
			for (Field field : fields) {
		      _map.put(Byte.valueOf(field.getByte(null)), field.getName());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static String get(byte type) {
		return _map.get(type);
	}

}
