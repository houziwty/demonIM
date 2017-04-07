package com.demon.im.mqtt.proto.message;

import java.lang.reflect.Field;
import java.util.HashMap;

public class DemonRequestMethod {
	public static final byte Service = (byte) 1;
	public static final byte Message = (byte) 2;
	public static final byte Reply = (byte) 3;
	public static final byte ReadReply = (byte) 4;
	public static final byte KeepAlive = (byte) 0x05;
	public static final byte Challenge = (byte) 0x06;
	public static final byte Logon = (byte) 7;
	public static final byte CheckCredential = (byte) 0x08;
	public static final byte GetCredential = (byte) 0x09;
	public static final byte Notify = (byte) 0x0a;
	public static final byte Ask = (byte) 0x0b;
	public static final byte Typing = (byte) 0x0c;
	public static final byte Logoff = (byte) 0x0d;
	public static final byte Image = (byte) 0x0e;
	public static final byte Take = (byte) 0x0f;
	public static final byte Group = (byte) 0x10;
	public static final byte GroupMessage = (byte) 0x11;
	public static final byte Voice = (byte) 0x12;
	public static final byte LBSService = (byte) 0x13;
	public static final byte Video = (byte) 0x14;
	public static final byte Document = (byte) 0x16;
	public static final byte Verify = (byte) 0x17;
	public static final byte Note = (byte) 0x18;
	public static final byte Report = (byte) 0x19;

	public static final byte Emoticon = (byte) 0x1b;

	public static final byte File = (byte) 0x20;

	public static final byte DPService = (byte) 0x30;
	public static final byte DPSub = (byte) 0x31;
	public static final byte DPUnSub = (byte) 0x32;
	public static final byte DPNotify = (byte) 0x33;
	public static final byte DPTake = (byte) 0x34;

	public static final byte SMS = (byte) 0x63;

	public static final byte HotBackup = (byte) 0x76;
	public static final byte Observation = (byte) 0x77;
	public static final byte Log = (byte) 0x78;
	public static final byte Trace = (byte) 0x79;
	public static final byte Monitoring = (byte) 0x7A;
	public static final byte RPC = (byte) 0x7b;
	public static final byte InnerService = (byte) 0x7C;
	public static final byte Config = (byte) 0x7D;
	public static final byte Unknown = (byte) 0X7E;

	private static HashMap<Byte, String> _map;
	static {
		try {
			Class<?> headertype = Class.forName(DemonRequestMethod.class
					.getCanonicalName());
			Field[] fields = headertype.getFields();
			_map = new HashMap<Byte, String>();
			for (Field field : fields) {
				_map.put(Byte.valueOf(field.getByte(null)), field.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(byte type) {
		return _map.get(type);
	}
	
}
