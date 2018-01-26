package testqueue;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import queue_singlelinkedlist.FifoQueue;

public class TestAppendFifoQueue {
	FifoQueue<Integer> q1;
	FifoQueue<Integer> q2;

	@Before
	public void setUp() throws Exception {
		q1 = new FifoQueue<Integer>();
		q2= new FifoQueue<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		q1 = null;
		q2 = null;
	}

	@Test
	public void testTwoEmptyList() {
			q1.append(q2);
			assertTrue("The size should be zero", q1.size() == 0);
			assertTrue("The size should be zero", q2.size() == 0);
	}

	@Test
	public void testFirstEmptyList() {
		//fill q2
		for(int i = 1; i <= 5; i++) {
			q2.offer(i);
		}
		// itr q2
		Iterator<Integer> itr2 = q2.iterator();
		q1.append(q2);
		//itr q1 after append method
		Iterator<Integer> itr1 = q1.iterator();
		assertTrue("q1 should have same size as q2 ", q1.size() == q2.size());
		assertTrue("q1 should have reference as q2 ", q1.getLast() == q2.getLast());
		//also checking through iterator
		while(itr2.hasNext()) {
			assertTrue("q1 should have same reference as q2", itr1.next() == itr2.next());
		}
	}

	@Test
	public void testSecoundEmptyList() {
		//fill q1
		for(int i = 1; i <= 5; i++) {
			q1.offer(i);
		}
		Iterator<Integer> itr1 = q1.iterator();
		q1.append(q2);
		Iterator<Integer> itr2 = q1.iterator();
		while(itr2.hasNext()) {
			assertTrue("itr1 should have same reference as itr2 after append", itr1.next() == itr2.next());
		}
	}

	@Test
	public void testTwoNonEmpty() {

		for(int i = 1; i <= 3; i++) {
			q1.offer(i);
			q2.offer(i + 10);
		}

		int totSize = q1.size() + q2.size();
		int q1FristSize = q1.size();
		// efterblivet, ty itr1 och itr3 pekar på samma instansiereing. Denna instansiereing beror på vad q1 är och således samma.
		Iterator<Integer> itr1 = q1.iterator();
		Iterator<Integer> itr2 = q2.iterator();
		q1.append(q2);
		Iterator<Integer> itr3 = q1.iterator();
		assertTrue("q1 should be totSize and have new last reference ", q1.size() == totSize);
		assertTrue("q1 should be totSize and have new last reference ", q1.getLast() == q2.getLast());

		int i = 1;
		while(i <= q1FristSize) {
			assertTrue("Should be in correct order", itr3.next() == itr1.next());
			i++;
		}
		while(i <= totSize) {
			assertTrue("Should be in correct order", itr3.next() == itr2.next());
			i++;
		}
}

	@Test
	public void testAppendSameList() {
		try	{
			q1.append(q1);
			fail("Should raise IllegalArgumentException");
		} catch(IllegalArgumentException e){
		} //assertTrue("Två tomma köer ska size == 0", q1.size() == 0);
	}
}
