package netty.common.message;

import java.io.Serializable;

import netty.common.util.DemonLinkedList;

public class DemonMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5471528125749414349L;
	
	private DemonLinkedList<DemonHeader>_hdeaders;

}
