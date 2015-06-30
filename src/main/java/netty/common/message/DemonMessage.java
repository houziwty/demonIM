package netty.common.message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import netty.common.util.DemonLinkedList;
import netty.common.util.DemonLinkedNode;

public class DemonMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5471528125749414349L;

	private DemonLinkedList<DemonHeader> _headers;
	private DemonLinkedList<DemonBody> _bodys;
	private DemonMessageType _messageType;
	private byte _method;
	private transient DemonTransaction _parentTrans;

	public DemonHeader From;
	public DemonHeader To;
	public DemonHeader CallId;
	public DemonHeader Csequence;
	public DemonHeader Fpid;
	public DemonHeader Tpid;
	public DemonHeader Event;

	public DemonMessage(byte method) {
		this._method = method;
		this._messageType = ((this._method | 0x7F) == 0x7F) ? DemonMessageType.Request
				: DemonMessageType.Response;
		this._headers = new DemonLinkedList<DemonHeader>();
		this._bodys = new DemonLinkedList<DemonBody>();
	}

	public void addBody(DemonBody body) {
		if (body != null)
			body.Node = this._bodys.put(body);
	}

	public void addBody(byte[] bytes) {
		if (bytes != null) {
			DemonBody body = new DemonBody(bytes);
			body.Node = this._bodys.put(body);
		}
	}

	public void addBodys(ArrayList<DemonBody> bodys) {
		for (DemonBody body : bodys) {
			body.Node = this._bodys.put(body);
		}
	}

	public void addHeader(DemonHeader header) {
		if (header != null) {
			if (header.Node != null)
				header = new DemonHeader(header.getType(), header.getValue());
			switch (header.getType()) {
			case DemonHeaderType.From: {
				header.Node = this._headers.put(header);
				this.From = header;
			}
				break;
			case DemonHeaderType.To: {
				header.Node = this._headers.put(header);
				this.To = header;
			}
				break;
			case DemonHeaderType.CallId: {
				header.Node = this._headers.put(header);
				this.CallId = header;
			}
				break;
			case DemonHeaderType.Csequence: {
				header.Node = this._headers.put(header);
				this.Csequence = header;
			}
				break;
			case DemonHeaderType.Fpid: {
				header.Node = this._headers.put(header);
				this.Fpid = header;
			}
				break;
			case DemonHeaderType.Tpid: {
				header.Node = this._headers.put(header);
				this.Tpid = header;
			}
				break;
			case DemonHeaderType.Event: {
				header.Node = this._headers.put(header);
				this.Event = header;
			}
				break;
			default: {
				header.Node = this._headers.put(header);
			}
				break;
			}
		}
	}

	public DemonTransaction get_parentTrans() {
		return _parentTrans;
	}

	public void set_parentTrans(DemonTransaction _parentTrans) {
		this._parentTrans = _parentTrans;
	}

	public synchronized DemonBody getBody() {
		try {
			_bodys.moveToHead();
			return _bodys.get().obj();
		} catch (Exception e) {
			return null;
		}
	}

	public synchronized ArrayList<DemonBody> getBodys() {
		ArrayList<DemonBody> ret = new ArrayList<DemonBody>();
		_bodys.moveToHead();
		DemonLinkedNode<DemonBody> bodyNode = null;
		while ((bodyNode = _bodys.get()) != null) {
			ret.add(bodyNode.obj());
		}
		return ret;
	}

	public synchronized DemonHeader getHeader(byte headerType) {
		_headers.moveToHead();
		DemonLinkedNode<DemonHeader> _headerNode = null;
		while ((_headerNode = _headers.get()) != null) {
			if (_headerNode.obj().isTypeOf(headerType))
				return _headerNode.obj();
		}
		return null;
	}

	public synchronized ArrayList<DemonHeader> getHeaders() {
		ArrayList<DemonHeader> ret = new ArrayList<DemonHeader>();
		_headers.moveToHead();
		DemonLinkedNode<DemonHeader> _headerNode = null;
		while ((_headerNode = _headers.get()) != null) {
			ret.add(_headerNode.obj());
		}
		return ret;
	}

	public synchronized ArrayList<DemonHeader> getHeaders(byte headerType) {
		ArrayList<DemonHeader> ret = new ArrayList<DemonHeader>();
		_headers.moveToHead();
		DemonLinkedNode<DemonHeader> _headerNode = null;
		while ((_headerNode = _headers.get()) != null) {
			if (_headerNode.obj().isTypeOf(headerType))
				ret.add(_headerNode.obj());
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
		sb.append(diretion);
		return sb.toString();
	}

	public DemonMessageType getMessageType() {
		return _messageType;
	}

	public byte getMethodValue() {
		return _method;
	}

	public boolean isMessageType(DemonMessageType messageType) {
		return this._messageType == messageType;
	}

	public boolean isMethod(byte method) {
		return this._method == method;
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
			this._headers.remove(header.Node);
			From = null;
		}
			break;
		case DemonHeaderType.To: {
			this._headers.remove(header.Node);
			To = null;
		}
			break;
		case DemonHeaderType.Fpid: {
			this._headers.remove(header.Node);
			Fpid = null;
		}
			break;
		case DemonHeaderType.Tpid: {
			this._headers.remove(header.Node);
			Tpid = null;
		}
			break;
		case DemonHeaderType.CallId: {
			this._headers.remove(header.Node);
			CallId = null;
		}
			break;
		case DemonHeaderType.Csequence: {
			this._headers.remove(header.Node);
			Csequence = null;
		}
			break;
		case DemonHeaderType.Event: {
			this._headers.remove(header.Node);
			Event = null;
		}
		default: {
			this._headers.remove(header.Node);
		}
			break;
		}
	}

	public void removeHeaders(byte headerType) {
		ArrayList<DemonHeader> headers = this.getHeaders(headerType);
		for (DemonHeader header : headers) {
			removeHeader(header);
		}
	}

	public synchronized void releaseBodys() {
		_bodys.moveToHead();
		DemonLinkedNode<DemonBody> bodyNode = null;
		while ((bodyNode = _bodys.get()) != null) {
			_bodys.remove(bodyNode);
		}
	}

	public boolean containsHeader(byte typebyte) {
		return getHeader(typebyte) != null;
	}

	public synchronized ByteBuffer toByteBuffer() {
		int messageSize = 2;
		_headers.moveToHead();
		DemonLinkedNode<DemonHeader> headerNode = null;
		while ((headerNode = _headers.get()) != null) {
			messageSize += headerNode.obj().getValueLength() + 2;
		}
		_bodys.moveToHead();
		DemonLinkedNode<DemonBody> bodyNode = null;
		while ((bodyNode = _bodys.get()) != null) {
			messageSize += bodyNode.obj().getValueLength() + 3;
		}
		ByteBuffer buffer = ByteBuffer.allocate(messageSize);
		buffer.put(_method);
		_headers.moveToHead();
		while ((headerNode = _headers.get()) != null) {
			DemonHeader h = headerNode.obj();
			int length = h.getValueLength();
			buffer.put(h.type);
			buffer.put((byte) length);
			if (length > 0) {
				buffer.put(h.getValue(), 0, length);
			}
		}
		_bodys.moveToHead();
		while ((bodyNode = _bodys.get()) != null) {
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
       StringBuffer sb=new StringBuffer();
		sb.append("This message is: ");
		sb.append(this.getMessageType());
		sb.append("\r\n");
		if(this.isMessageType(DemonMessageType.Request)){
			sb.append("Method:");
			sb.append(DemonRequestMethod.get(this._method));
			sb.append("\r\n");
		}
		return sb.toString();
	}

}
