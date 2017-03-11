package netty.common.proto.message;

import java.lang.reflect.Field;
import java.util.HashMap;

public class DemonResponseCode {
	public static final byte OK = (byte)0x80;
	public static final byte NotAvailable = (byte)0x81;
	public static final byte Error = (byte)0x82;
	public static final byte Busy = (byte)0x83;
	public static final byte NotExist = (byte)0x84;
	public static final byte NotSupport = (byte)0x85;
	public static final byte NeedVerifycation = (byte)0x86;
	public static final byte Trying = (byte)0xB0;
	public static final byte Processing = (byte)0xB1;
	public static final byte ClientOffLine = (byte)0xFD;
	public static final byte Unknown = (byte)0xFE;

	private static HashMap<Byte, String> _map;
	
	static{
		try {
			Class<?>headertype=Class.forName(DemonResponseCode.class.getCanonicalName());
			Field[]fields=headertype.getFields();
			_map=new HashMap<Byte,String>();
			for(Field field:fields){
				_map.put(Byte.valueOf(field.getByte(null)), field.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String get(byte type){
		return _map.get(type);
	}

}
