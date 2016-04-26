package netty.common.message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

import netty.common.transaction.DemonTransaction;
import netty.common.util.DemonLinkedList;
import netty.common.util.DemonLinkedNode;

public abstract class DemonMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5471528125749414349L;

	private LinkedBlockingQueue<DemonHeader> headers;
	private LinkedBlockingQueue<DemonBody> bodys;
	private DemonMessageType messageType;
	private byte method;
	private transient DemonTransaction _parentTrans;

	public DemonHeader From;
	public DemonHeader To;
	public DemonHeader CallId;
	public DemonHeader Csequence;
	public DemonHeader Fpid;
	public DemonHeader Tpid;
	public DemonHeader Event;

	public DemonMessage(byte method) {
		this.method = method;
		this.messageType = ((this.method | 0x7F) == 0x7F) ? DemonMessageType.Request
				: DemonMessageType.Response;
		this.headers = new LinkedBlockingQueue<DemonHeader>();
		this.bodys = new LinkedBlockingQueue<DemonBody>();
	}

	public abstract boolean isRequest();

	public void addBody(DemonBody body) {
		if (body != null)
			bodys.add(body);

	}

	public void addBody(String s) {
		addBody(s.getBytes(Charset.forName("utf-8")));
	}

	public void addBody(byte[] bytes) {
		if (bytes != null) {
			DemonBody body = new DemonBody(bytes);
			addBody(body);
		}
	}

	public void addBodys(ArrayList<DemonBody> bodys) {
		for (DemonBody body : bodys) {
			addBody(body);
		}
	}

	public LinkedBlockingQueue<DemonHeader> getHeaderQueue() {
		return this.headers;
	}

	public LinkedBlockingQueue<DemonBody> getBodyQueue() {
		return this.bodys;
	}

	public void addHeader(byte type, long value) {
		addHeader(new DemonHeader(type, value));
	}

	public void addHeader(byte type, String value) {
		addHeader(new DemonHeader(type, value));
	}

	public void addHeader(byte type, byte[] value) {
		addHeader(new DemonHeader(type, value));
	}

	public void addHeader(DemonHeader header) {
		if (header != null) {
			switch (header.getType()) {
			case DemonHeaderType.From: {
				this.headers.add(header);
				this.From = header;
			}
				break;
			case DemonHeaderType.To: {
				this.headers.add(header);
				this.To = header;
			}
				break;
			case DemonHeaderType.CallId: {
				this.headers.add(header);
				this.CallId = header;
			}
				break;
			case DemonHeaderType.Fpid: {
				this.headers.add(header);
				this.Fpid = header;
			}
				break;
			case DemonHeaderType.Tpid: {
				this.headers.add(header);
				this.Tpid = header;
			}
				break;
			case DemonHeaderType.Event: {
				this.headers.add(header);
				this.Event = header;
			}
				break;
			default: {
				this.headers.add(header);
			}
				break;
			}
		}
	}

	public DemonTransaction get_parentTrans() {
		return _parentTrans;
	}

	public void setParentTrans(DemonTransaction _parentTrans) {
		this._parentTrans = _parentTrans;
	}

	public synchronized DemonBody getBody() {
		try {
			return bodys.peek();
		} catch (Exception e) {
			return null;
		}
	}

	public synchronized ArrayList<DemonBody> getBodys() {
		ArrayList<DemonBody> ret = new ArrayList<DemonBody>();
		ret.addAll(bodys);
		return ret;
	}

	public synchronized DemonHeader getHeader(byte headerType) {
		for (DemonHeader header : headers) {
			if (header.isTypeOf(headerType))
				return header;
		}
		return null;
	}

	public synchronized ArrayList<DemonHeader> getHeaders() {
		ArrayList<DemonHeader> ret = new ArrayList<DemonHeader>();
		ret.addAll(headers);
		return ret;
	}

	public synchronized ArrayList<DemonHeader> getHeaders(byte headerType) {
		ArrayList<DemonHeader> ret = new ArrayList<DemonHeader>();
		for (DemonHeader header : headers) {
			if (header.isTypeOf(headerType)) {
				ret.add(header);
			}
		}
		return ret;
	}

	public String getKey(boolean diretion) {
		StringBuilder sb = new StringBuilder();
		for (DemonHeader header : new DemonHeader[] { this.From, this.To,
				this.CallId, this.Csequence, this.Fpid, this.Tpid }) {
			if (header != null && header.isNotNullValue()) {
				if (header.getType() != DemonHeaderType.Fpid
						&& header.getType() != DemonHeaderType.Tpid)
					sb.append(header.getInt64());
				else
					sb.append(header.getHexString());
			}
			sb.append("-");
		}
		sb.append(sb.length() - 1);
		return sb.toString();
	}

	public byte getMethodValue() {
		return method;
	}

	public void setMethod(byte value) {
		method = value;
	}

	public DemonMessageType getMessageType() {
		return messageType;
	}

	public boolean isMessageType(DemonMessageType messageType) {
		return this.messageType == messageType;
	}

	public boolean isMethod(byte method) {
		return this.method == method;
	}

	public boolean isEvent(byte evnet) {
		if (Event != null && Event.getValueLength() == 1) {
			return Event.getValue()[0] == evnet;
		}
		return false;
	}

	public boolean isEvent(Long event) {
		if (Event != null && Event.getValueLength() > 0) {
			return Event.getInt64() == event;
		}
		return false;
	}

	public void removeHeader(DemonHeader header) {

		switch (header.getType()) {
		case DemonHeaderType.From: {
			From = null;
		}
			break;
		case DemonHeaderType.To: {
			To = null;
		}
			break;
		case DemonHeaderType.Fpid: {
			Fpid = null;
		}
			break;
		case DemonHeaderType.Tpid: {
			Tpid = null;
		}
			break;
		case DemonHeaderType.CallId: {
			CallId = null;
		}
			break;
		case DemonHeaderType.Csequence: {
			Csequence = null;
		}
			break;
		case DemonHeaderType.Event: {
			Event = null;
		}
		default: {
			break;
		}

		}
		headers.remove(header);
	}

	public void removeHeaders(byte headerType) {
		ArrayList<DemonHeader> headers = this.getHeaders(headerType);
		for (DemonHeader header : headers) {
			removeHeader(header);
		}
	}
	public synchronized void releaseBodys() {
		bodys.clear();
	}

	public boolean containsHeader(byte typebyte) {
		return getHeader(typebyte) != null;
	}

	public synchronized ByteBuffer toByteBuffer() {
		int messageSize = 2;
		headers.moveToHead();
		DemonLinkedNode<DemonHeader> headerNode = null;
		while ((headerNode = headers.get()) != null) {
			messageSize += headerNode.obj().getValueLength() + 2;
		}
		bodys.moveToHead();
		DemonLinkedNode<DemonBody> bodyNode = null;
		while ((bodyNode = bodys.get()) != null) {
			messageSize += bodyNode.obj().getValueLength() + 3;
		}
		ByteBuffer buffer = ByteBuffer.allocate(messageSize);
		buffer.put(_method);
		headers.moveToHead();
		while ((headerNode = headers.get()) != null) {
			DemonHeader h = headerNode.obj();
			int length = h.getValueLength();
			buffer.put(h.type);
			buffer.put((byte) length);
			if (length > 0) {
				buffer.put(h.getValue(), 0, length);
			}
		}
		bodys.moveToHead();
		while ((bodyNode = bodys.get()) != null) {
			DemonBody b = bodyNode.obj();
			int length = b.getValueLength();
			buffer.put(b.getType());
			buffer.put((byte) (length & 0x000000FF));
			buffer.put((byte) ((length >> 8) & 0x000000FF));
			if (length > 0) {
				buffer.put(b.getValue(), 0, length);
			}
		}

		buffer.put((byte) 0);
		buffer.flip();

		return buffer;
	}

	public byte[] toBytes() {
		return toByteBuffer().array();
	}

	public DemonRequest toRequest() {
		if (_messageType == DemonMessageType.Request) {
			return (DemonRequest) this;
		} else {
			return null;
		}
	}

	public DemonResponse toResponse() {
		if (_messageType == DemonMessageType.Response) {
			return (DemonResponse) this;
		} else {
			return null;
		}
	}

	public String toString(boolean printBody) {
		StringBuffer sb = new StringBuffer();
		if(isRequest()){
			sb.append("Method:");
			sb.append(DemonRequestMethod.get(this.method));
			sb.append("\r\n");	
		}else{
			sb.append("ResponseCode : ");
			sb.append(DemonResponseCode.get(this.method));
			sb.append("\r\n");
		}
		Iterator<DemonHeader> fi = this.getHeaders().iterator();
		while (fi.hasNext()) {
			sb.append(fi.next().toString(printBody));
		}
		Iterator<DemonBody> bi = this.getBodys().iterator();
		while (bi.hasNext()) {
			sb.append(bi.next().toString());
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return this.toString(true);
	}

	public String toHexString() {
		StringBuffer sb = new StringBuffer();
		if (isRequest()) {
			sb.append("Method:");
			sb.append(DemonRequestMethod.get(this.method));
			sb.append("\r\n");
		} else {
			sb.append("ResponseCode : ");
			sb.append(DemonResponseCode.get(this.method));
			sb.append("\r\n");
		}
		Iterator<DemonHeader> fi = this.getHeaders().iterator();
		while (fi.hasNext()) {
			sb.append(fi.next().toString(false));

		}
		Iterator<DemonBody> bi = this.getBodys().iterator();
		while (bi.hasNext()) {
			sb.append(bi.next().toHexString());
		}
		return sb.toString();
	}

}
