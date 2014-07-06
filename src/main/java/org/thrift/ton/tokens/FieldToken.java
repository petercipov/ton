package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class FieldToken implements Token{
	private final FieldType type;
	private final short fieldId;

	public FieldToken(short fieldId, FieldType type) {
		this.type = type;
		this.fieldId = fieldId;
	}
	
	public short getFieldId() {
		return fieldId;
	}

	public FieldType getFieldType() {
		return type;
	}

	@Override
	public TokenType getTokenType() {
		return TokenType.Field;
	}
}
