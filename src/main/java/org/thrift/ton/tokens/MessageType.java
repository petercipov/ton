package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public enum MessageType {
	CALL,
	REPLY,
	EXCEPTION,
	ONEWAY
	;
	
	public static MessageType fromCode(byte code) {
		switch(code) {
			case 1:
				return CALL;
			case 2:
				return REPLY;
			case 3:
				return EXCEPTION;
			case 4:
				return ONEWAY;
		}
		
		throw new UnsupportedOperationException("Unknown message type with code " +code);
	}
	
}
