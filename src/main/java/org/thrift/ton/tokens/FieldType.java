package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public enum FieldType {	
	STOP(0),
	VOID(1),
	BOOLEAN(2),
	BYTE(3),
	DOUBLE(4),
	I16(6),
	I32(8),
	I64(10),
	STRING(11),
	STRUCT(12),
	MAP(13),
	SET(14),
	LIST(15),
	ENUM(16)
	;
	
	private final byte code;

	private FieldType(int code) {
		this.code = (byte)code;
	}

	public byte getCode() {
		return code;
	}
		
	public static FieldType fromCode(byte code) {
		switch(code) {
			case 0:
				return STOP;
			case 1:
				return VOID;
			case 2:
				return BOOLEAN;
			case 3:
				return BYTE;
			case 4:
				return DOUBLE;
			case 6:
				return I16;
			case 8:
				return I32;
			case 10:
				return I64;
			case 11:
				return STRING;
			case 12:
				return STRUCT;
			case 13:
				return MAP;
			case 14:
				return SET;	
			case 15:
				return LIST;
			case 16:
				return ENUM;
			default:
				throw new UnsupportedOperationException("unknown code "+ code);
		}
	}	
}
