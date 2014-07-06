package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class ShortToken extends FieldToken {
	private final short value;

	public ShortToken(short id, short value) {
		super(id, FieldType.I16);
		this.value = value;
	}

	public short getValue() {
		return value;
	}
}
