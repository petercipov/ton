package org.thrift.ton.protocol.binary;

import org.thrift.ton.Builder;
import static org.thrift.ton.Builder.State.*;
import org.thrift.ton.rx.Observer;
import org.thrift.ton.tokens.FieldType;
import org.thrift.ton.tokens.Token;
import java.util.Deque;

/**
 *
 * @author Peter Cipov
 */
public class StructBuilder implements Builder {

	private final Observer<Token> tokens;
	private final Deque<Builder> stack;
	private final ByteReader bytes;

	private int state;
	private FieldType fieldType;
	private short id;

	public StructBuilder(Observer<Token> receivedTokens, Deque<Builder> stack, ByteReader bytes) {
		this.tokens = receivedTokens;
		this.stack = stack;
		this.bytes = bytes;
		this.state = FIELD_TYPE_STATE;
	}

	@Override
	public State work() {
		while (true) {
			switch (state) {
				case FIELD_TYPE_STATE:
					if (bytes.hasByte()) {
						fieldType = FieldType.fromCode(bytes.readByte());
						if (fieldType == FieldType.STOP) {
							return FINISHED;
						} else {
							state = FIELD_ID_STATE;
						}

					} else {
						return NEED_MORE_BYTES;
					}
					break;
					
				case FIELD_ID_STATE:
					if (bytes.hasI16()) {
						id = bytes.readI16();
						state = FIELD_VALUE_STATE;
					} else {
						return NEED_MORE_BYTES;
					}
				case FIELD_VALUE_STATE:
					State s = Reader.readElement(id, fieldType, bytes, stack, tokens);
					switch(s) {
						case NEED_MORE_BYTES:
							return s;
						case NEW_BUILDER:
							state =FIELD_TYPE_STATE;
							return s;
						case FINISHED:
							state = FIELD_TYPE_STATE;
							break;
						case ERROR:
							return s;
					}
					break;
			}
		}
	}

	private static final int FIELD_VALUE_STATE = 2;
	private static final int FIELD_ID_STATE = 1;
	private static final int FIELD_TYPE_STATE = 0;
}
