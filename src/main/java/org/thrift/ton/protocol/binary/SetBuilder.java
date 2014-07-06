package org.thrift.ton.protocol.binary;

import org.thrift.ton.Builder;
import static org.thrift.ton.Builder.State.*;
import org.thrift.ton.rx.Observer;
import org.thrift.ton.tokens.FieldType;
import org.thrift.ton.tokens.SetToken;
import org.thrift.ton.tokens.Token;
import java.util.Deque;

/**
 *
 * @author Peter Cipov
 */
public class SetBuilder implements Builder {
	
	private final short seqid;
	private final Observer<Token> tokens;
	private final Deque<Builder> stack;
	private final ByteReader bytes;
	
	private int state = LIST_TYPE;
	private FieldType setType = null;
	private int setSize = 0;
	
	private int elementsToRead = 0;

	public SetBuilder(short seqid, Observer<Token> tokens, Deque<Builder> stack, ByteReader bytes) {
		this.seqid = seqid;
		this.tokens = tokens;
		this.stack = stack;
		this.bytes = bytes;
	}
	
	@Override
	public State work() {
		while(true) {
			switch(state) {
				case LIST_TYPE:
					if (bytes.hasByte()) {
						byte code = bytes.readByte();
						setType = FieldType.fromCode(code);
						state = LIST_SIZE;
						break;
					} else {
						return NEED_MORE_BYTES;
					}
				case LIST_SIZE:
					if (bytes.hasI32()) {
						setSize = bytes.readI32();
						elementsToRead = setSize;
						state = READING_ELEMENTS;
						tokens.onNext(new SetToken(seqid, setType, setSize));
						break;
					} else {
						return NEED_MORE_BYTES;
					}
				case READING_ELEMENTS:
					for (int i=0; i < elementsToRead;) {
						State s = Reader.readElement((short)0, setType, bytes, stack, tokens);
						switch(s) {
							case NEED_MORE_BYTES:
								return s;
							case NEW_BUILDER:
								elementsToRead--;
								return s;
							case FINISHED:
								elementsToRead--;
								break;
							case ERROR:
								elementsToRead--;
								return s;
						}
					}
				return ERROR;				
			}
		}
	}
	private static final int LIST_TYPE = 0;
	private static final int LIST_SIZE = 1;
	private static final int READING_ELEMENTS = 2;
	
}
