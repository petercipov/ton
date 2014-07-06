package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class StructToken extends FieldToken {

	public StructToken(short id) {
		super(id, FieldType.STRUCT);
	}

	@Override
	public TokenType getTokenType() {
		return TokenType.Struct;
	}
}
