package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class MapToken extends FieldToken {
	private final FieldType keyType;
	private final FieldType valeuType;
	private final int length;

	public MapToken(short id, FieldType keyType, FieldType valeuType, int length) {
		super(id, FieldType.MAP);
		this.keyType = keyType;
		this.valeuType = valeuType;
		this.length = length;
	}

	public FieldType getKeyType() {
		return keyType;
	}

	public FieldType getValeuType() {
		return valeuType;
	}

	public int getLength() {
		return length;
	}
}
