package org.thrift.ton.rx;

/**
 *
 * @author pcipov
 */
public class Observable<T> {
	
	private final Action<T> action;

	private Observable(Action<T> action) {
		this.action = action;
	}
	
	public final void subscribe(Observer<T> observer)  {
		this.action.perform(observer);
	}
	
	public final static <T> Observable<T> create(Action<T> action) {
		if(action == null) {
			throw new IllegalArgumentException("action cannot be null");
		}
		return new Observable<T>(action);
	}
	
	public final static <T> Observable<T> wrap(final T ... values) {
		return create(new Action<T>() {

			@Override
			public void perform(Observer<T> observer) {
				for (T value : values) {
					observer.onNext(value);
				}
				observer.onSuccess();
			}
		});
	}
	
	public final static Observable empty() {
		return new Observable(Action.EMPTY_ACTION);
	}
	
	public interface Action<K> {
		
		Action EMPTY_ACTION = new Action() {
			@Override
			public void perform(Observer observer) {
				ObserverHelper.pushEmpty(observer);
			}
		};
		
		void perform(Observer<K> observer);
	}
	
	
}
