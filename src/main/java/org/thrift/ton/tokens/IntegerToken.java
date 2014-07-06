package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class IntegerToken extends FieldToken {
	private final int value;

	public IntegerToken(short id, int value) {
		super(id, FieldType.I32);
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
