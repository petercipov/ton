package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class ListToken extends FieldToken {
	private final FieldType type;
	private final int length;
	
	public ListToken(short id, FieldType type, int length) {
		super(id, FieldType.LIST);
		this.type = type;
		this.length = length;
	}

	public FieldType getListType() {
		return type;
	}

	public int getListLength() {
		return length;
	}	
}
