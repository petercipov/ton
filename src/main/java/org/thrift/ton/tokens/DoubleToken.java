package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class DoubleToken extends FieldToken {
	private final double value;

	public DoubleToken(short id, double value) {
		super(id, FieldType.DOUBLE);
		this.value = value;
	}

	public double getValue() {
		return value;
	}
}
