package netty.common.message;

import java.io.Serializable;
import java.util.ArrayList;

import netty.common.util.DemonLinkedList;

public class DemonMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5471528125749414349L;

	private DemonLinkedList<DemonHeader> _headers;
	private DemonLinkedList<DemonBody> _boyds;
	private DemonMessageType _messageType;
	private byte _method;
	private transient DemonTransaction _parentTrans;
	public DemonHeader From;
	public DemonHeader To;
	public DemonHeader CallId;
	public DemonHeader Casequence;
	public DemonHeader Fpid;
	public DemonHeader Tpid;
	public DemonHeader Event;

	public DemonMessage(byte method) {
		this._method = method;
		this._messageType = ((this._method | 0x7F) == 0x7F) ? DemonMessageType.Request
				: DemonMessageType.Response;
		this._headers = new DemonLinkedList<DemonHeader>();
		this._boyds = new DemonLinkedList<DemonBody>();
	}

	public void addBody(DemonBody body) {
		if (body != null)
			body.Node = this._boyds.put(body);
	}

	public void addBody(byte[] bytes) {
		if (bytes != null) {
			DemonBody body = new DemonBody(bytes);
			body.Node = this._boyds.put(body);
		}
	}
	public void addBodys(ArrayList<DemonBody>bodys){
		for(DemonBody body:bodys){
			body.Node=this._boyds.put(body);
		}
	}
	public void addHeader(DemonHeader header){
		if(header!=null){
			if(header.Node!=null)
				header=new DemonHeader(header.getType(),header.getValue());
			switch(header.getType()){
			
			}
		}
	}

}
