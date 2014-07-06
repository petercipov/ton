package org.thrift.ton.rx;

/**
 *
 * @author pcipov
 */
public interface Observer<T> {
	/**
	 * Any runtime exception thrrow from onNext will cause immediate onError call => end.
	 * @param value 
	 */
	void onNext(T value);
	
	void onSuccess();
	
	void onError(Throwable t);	
}
