package org.thrift.ton;

import org.thrift.ton.rx.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Peter Cipov
 */
public class BlockingObserver <T> implements Observer<T> {
	
	private final CountDownLatch cdl = new CountDownLatch(1);
	private final ConcurrentLinkedQueue<T> buff = new ConcurrentLinkedQueue<T>();
	private volatile Throwable t;

	@Override
	public void onNext(T value) {
		buff.add(value);
	}

	@Override
	public void onSuccess() {
		cdl.countDown();
	}

	@Override
	public void onError(Throwable t) {
		this.t = t;
		cdl.countDown();
	}
	
	public List<T> list() {
		try {
			cdl.await();
		} catch(InterruptedException ex) {
			throw new IllegalStateException(ex);
		}
		
		if (t != null) {
			throw new IllegalStateException(t);
		}
		return new ArrayList<T>(buff);
	}
	
	public T single() {
		List<T> l = list();
		if (l.size() == 1 ) {
			return l.get(0);
		} else {
			throw new IllegalStateException("Expecting single but obtained "+ l.size());
		}
	}
	
}
