package org.thrift.ton.tokens;

import java.nio.charset.Charset;

/**
 *
 * @author Peter Cipov
 */
public class BinaryToken extends FieldToken{
	private static final Charset utf8 = Charset.forName("UTF-8");
	private final byte[] value;

	public BinaryToken(short id, byte[] value) {
		super(id, FieldType.STRING);
		this.value = value;
	}

	public byte[] getValue() {
		return value;
	}
	
	public String toUtf8() {
		return new String(value, utf8);
	}
}
