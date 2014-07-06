package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class SetToken extends FieldToken {
	private final FieldType setType;
	private final int length;

	public SetToken(short id, FieldType setType, int length) {
		super(id, FieldType.SET);
		this.setType = setType;
		this.length = length;
	}

	public FieldType getSetType() {
		return setType;
	}

	public int getLength() {
		return length;
	}

}