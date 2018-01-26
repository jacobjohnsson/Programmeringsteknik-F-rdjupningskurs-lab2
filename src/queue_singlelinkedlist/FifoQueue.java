package queue_singlelinkedlist;
import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**
	 * Inserts the specified element into this queue, if possible
	 * post:	The specified element is added to the rear of this queue
	 * @param	e the element to insert
	 * @return	true if it was possible to add the element
	 * 			to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> q = new QueueNode<E>(e);
		if(last == null) {
				last = q;
				last.next = last;
		} else {
				q.next = last.next;
				last.next = q;
				last = q;
		}
		size ++;
		return true;
	}

	/**
	 * Returns the number of elements in this queue
	 * @return the number of elements in this queueue
	 */
	public int size() {
		return size;
	}

	public QueueNode<E> getLast() {
		return last;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue,
	 * returning null if this queue is empty
	 * @return 	the head element of this queue, or null
	 * 			if this queue is empty
	 */
	public E peek() {
		if (size() == 0) {
			return null;
		} else {
			return last.next.element;
		}
	}

	/**
	 * Retrieves and removes the head of this queue,
	 * or null if this queue is empty.
	 * post:	the head of the queue is removed if it was not empty
	 * @return 	the head of this queue, or null if the queue is empty
	 */
	public E poll() {
		if (size() == 0) {
			return null;
		} else if (size() == 1) {
				E e = last.element;
				last = null;
				size --;
				return e;
		} else {
			E e = last.next.element;
			last.next = last.next.next;
			size --;
			return e;
		}
	}

	public void append(FifoQueue<E> q) {
		if(q == this){
			throw new IllegalArgumentException();
		} else if (size == 0 && q.size == 0) {
				return;
		} else if (size == 0 && q.size > 0) {
			last = q.last;
			size = q.size();
		} else if (size > 0 && q.size == 0) {
			return;
		} else {
		//Move pointers last from both this and q
		size = size + q.size();
		QueueNode<E> temp = last.next;
		last.next = q.last.next;
		q.last.next = temp;
		last = q.last;
		}
	}

	/**
	 * Returns an iterator over the elements in this queue
	 * @return an iterator over the elements in this queue
	 */
	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<E> {
		private QueueNode <E> pos;
		private boolean hasStarted;

		private QueueIterator(){
				if (last == null) {
					hasStarted = false;
					pos = null;
				} else if(last == last.next) {
					hasStarted = false;
					pos = last;
				} else {
					hasStarted = false;
					pos = last.next;
				}
		}

		public boolean hasNext(){
			if (pos == null) {
				return false;
			} else if (pos == last.next && hasStarted) {
				return false;
			} else {
				return true;
			}
		}

		public E next() {
			if (pos == null) {
				throw new NoSuchElementException();
			}	else if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				hasStarted = true;
				E e = pos.element;
				pos = pos.next;
				return e;
			}
		}
	}

	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

}
