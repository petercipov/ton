package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class ByteToken extends FieldToken {
	private final byte value;
	
	public ByteToken(short id, byte value) {
		super(id, FieldType.BYTE);
		this.value = value;
	}

	public byte getValue() {
		return value;
	}
}
