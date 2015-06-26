package netty.common.message;

import java.io.Serializable;

import netty.common.util.DemonLinkedList;

public class DemonMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5471528125749414349L;
	
	private DemonLinkedList<DemonHeader>_headers;
	private DemonLinkedList<DemonBody>_boyds;
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
    
    public DemonMessage(byte method){
       this._method=method;
       this._messageType=((this._method|0x7F)==0x7F)?DemonMessageType.Request:DemonMessageType.Response;
       this._headers=new DemonLinkedList<DemonHeader>();
       this._boyds=new DemonLinkedList<DemonBody>();
    }
    

}
