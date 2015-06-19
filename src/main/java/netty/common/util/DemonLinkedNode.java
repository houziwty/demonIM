package netty.common.util;

import java.io.Serializable;

public class DemonLinkedNode<K> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3515193484358395115L;

	DemonLinkedNode<K> previous;

	DemonLinkedNode<K> next;

	DemonLinkedList<K> list;

	private K _obj;

	public DemonLinkedNode(K node) {
		_obj = node;
	}

	public K obj() {
		return _obj;
	}

}
