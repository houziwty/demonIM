package netty.common.message;

public class DemonResponse extends DemonMessage implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5021522053950213638L;

	public DemonResponse(byte method) {
		super(method);
		// TODO Auto-generated constructor stub
	}

	public DemonResponse(DemonRequest request) {
		this(request, DemonResponseCode.OK);
	}

	public DemonResponse(DemonRequest request, byte ok) {
		super(ok);
		for (DemonHeader header : request.getHeaders()) {
			if (header != null) {
				switch (header.getType()) {
				case DemonHeaderType.From:
				case DemonHeaderType.To:
				case DemonHeaderType.CallId:
				case DemonHeaderType.Csequence:
				case DemonHeaderType.Fpid:
				case DemonHeaderType.Tpid:
					this.addHeader(header.clone());
					break;
				default:
					break;
				}
			}
		}
	}

	public byte getStatusCode() {
		return super.getMethodValue();
	}

	public boolean isResponseCode(byte code) {
		return super.getMethodValue() == code;
	}

	@Override
	public boolean isRequest() {
		// TODO Auto-generated method stub
		return false;
	}

}
