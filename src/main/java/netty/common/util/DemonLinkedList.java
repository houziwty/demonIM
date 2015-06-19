package netty.common.util;

import java.io.Serializable;



public class DemonLinkedList<K> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3515193484358395115L;
	private DemonLinkedNode<K> _head;
	private DemonLinkedNode<K> _tail;
	private DemonLinkedNode<K> _current;
	private long _length;

	public DemonLinkedList() {
		_head = new DemonLinkedNode<K>(null);
		_tail = new DemonLinkedNode<K>(null);
		_head.next = _tail;
		_tail.previous = _head;
		_current = _head;
		_length = 0;
	}

	public synchronized long length() {
		return _length;
	}

	public synchronized boolean hasMore() {
		return _length > 0;
	}
	public synchronized DemonLinkedNode<K> put(K object)
	{
		DemonLinkedNode<K> node = new DemonLinkedNode<K>(object);
		_tail.previous.next = node;
		node.previous = _tail.previous;
		_tail.previous = node;
		node.next = _tail;
		node.list = this;
		_length++;
		return node;
	}

	private synchronized void put(DemonLinkedNode<K> node)
	{
		_tail.previous.next = node;
		node.previous = _tail.previous;
		_tail.previous = node;
		node.next = _tail;
		_length++;
	}
	public synchronized void moveToHead() {
		_current = _head;
	}

	public synchronized DemonLinkedNode<K> takeAwayFirst() {
       if(_head.next.equals(_tail))
          return null;
       else{
    	   DemonLinkedNode<K>result=_head.next;
    	   remove(result);
    	   return result;
       }
	}
	public synchronized DemonLinkedNode<K>takeAwayLast(){
		if(_tail.previous.equals(_head))
			return null;
		else{
			 DemonLinkedNode<K>result=_head.previous;
	    	   remove(result);
	    	   return result;
		}
	}
	public synchronized DemonLinkedNode<K>get(){
		if(_current.next.equals(_tail))
			return null;
		else{
			_current=_current.next;
			return _current;
		}
	}
	public synchronized void kick(DemonLinkedNode<K> node){
		if (node == null)
			return;
		remove(node);
		put(node);
	}
	public synchronized boolean remove(DemonLinkedNode<K>node){
		if (node == null)
			return false;
		if (node.next == null)
			return false;
		if (node.list != this)
			return false;
		if (_current.equals(node))
			_current = node.previous;
		node.previous.next = node.next;
		node.next.previous = node.previous;
		node.previous = null;
		node.next = null;
		_length--;
		return true;
	}
}
