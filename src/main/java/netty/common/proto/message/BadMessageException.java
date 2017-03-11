package netty.common.proto.message;

public class BadMessageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4952666048709853699L;
	
	public static BadMessageException INSTANCE=new BadMessageException();

}
