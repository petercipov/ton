package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class BooleanToken extends FieldToken {
	private final boolean value;
	
	public BooleanToken(short id, boolean value) {
		super(id, FieldType.BOOLEAN);
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}	
}
