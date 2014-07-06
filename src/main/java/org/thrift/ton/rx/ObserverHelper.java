package org.thrift.ton.rx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author pcipov
 */
public final class ObserverHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObserverHelper.class);

	private ObserverHelper() { //singleton static class
	}
	
	public static <T> void pushEmpty(Observer<T> l) { 
		pushSuc(l);
	}
	
	public static <T> void pushSingle(Observer<T> l, T value) { 
		try {
			l.onNext(value);
		} catch(Throwable th) {
			pushEx(l, th);
			return;
		}
		pushSuc(l);
	}
	
	public static <T> void pushSingle(final Observer<T> l, final T value, ExecutorService es) { 
		es.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				pushSingle(l, value);
				return null;
			}
		});	
	}
		
	public static <T> void pushSuc(Observer<T> l) {
		try {
			l.onSuccess();
		} catch(Throwable ex) {
			LOGGER.error("on success error ", ex);
		}
	}
	
	public static<T> void pushEx(Observer<T> l, Throwable ex) {
		try {
			l.onError(ex);
		} catch(Throwable e) {
			LOGGER.error("on error failure", e);
		}
	}

	public static void pushEx(final Observer l, final Throwable ex, ExecutorService es) {
		es.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				pushEx(l, ex);
				return null;
			}
		});
	}
}
