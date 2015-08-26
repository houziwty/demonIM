package netty.common.message;

import java.util.ArrayList;

public class DemonRequest extends DemonMessage implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DemonRequest(byte method) {
		super(method);
		// TODO Auto-generated constructor stub
	}

	public byte getMethod() {
		return super.getMethodValue();
	}

	public boolean isMethod(byte method) {
		return method == super.getMethodValue();
	}

	public DemonRequest clone() {
		DemonRequest request = new DemonRequest(this.getMethodValue());
		ArrayList<DemonHeader> list = this.getHeaders();
		ArrayList<DemonBody> bodys = this.getBodys();
		for (DemonHeader header : list) {
			if (!(header.isTypeOf(DemonHeaderType.RecordRoute) || header
					.isTypeOf(DemonHeaderType.Route))) {
				request.addHeader(new DemonHeader(header.getType(), header
						.getValue()));
			}
		}
		for (DemonBody body : bodys) {
			request.addBody(new DemonBody(body.getValue()));
		}
		return request;
	}

	public void setMethod(byte method) {
		super.setMethod(method);
	}

	@Override
	public boolean isRequest() {
		// TODO Auto-generated method stub
		return false;
	}


}
