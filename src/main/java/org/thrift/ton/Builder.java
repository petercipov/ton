package org.thrift.ton;

/**
 *
 * @author Peter Cipov
 */
public interface Builder {
	
	enum State {
		NEED_MORE_BYTES,
		NEW_BUILDER,
		ERROR,
		FINISHED
		;
	}
	
	public State work();
	
	
}