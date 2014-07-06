package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class LongToken extends FieldToken {
	private final long value;

	public LongToken(short id, long value) {
		super(id, FieldType.I64);
		this.value = value;
	}

	public long getValue() {
		return value;
	}
	
}
