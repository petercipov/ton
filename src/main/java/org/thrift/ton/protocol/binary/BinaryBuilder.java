package org.thrift.ton.protocol.binary;

import org.thrift.ton.Builder;
import static org.thrift.ton.Builder.State.*;
import org.thrift.ton.rx.Observer;
import org.thrift.ton.tokens.BinaryToken;
import org.thrift.ton.tokens.Token;
import java.util.Deque;

/**
 *
 * @author Peter Cipov
 */
public class BinaryBuilder implements Builder{
	
	private final Observer<Token> tokens;
	private final Deque<Builder> stack;
	private final ByteReader bytes;

	private final short seqid;
	private int state = 0;
	private int stringSize;

	public BinaryBuilder(short seqid, Observer<Token> tokens, Deque<Builder> stack, ByteReader bytes) {
		this.tokens = tokens;
		this.stack = stack;
		this.bytes = bytes;
		this.seqid = seqid;
	}
	
	@Override
	public State work() {
		while(true) {
			switch(state) {
				case 0:
					if (bytes.hasI32()) {
						stringSize = bytes.readI32();
						state = 1;
					} else {
						return NEED_MORE_BYTES;
					}
					break;
				case 1:
					if (bytes.has(stringSize)) {
						byte[] value = bytes.read(stringSize);
						state = 2;
						tokens.onNext(new BinaryToken(seqid, value));
						return FINISHED;
					} else {
						return NEED_MORE_BYTES;
					}
				default:
					return ERROR;
			}
		}
	}
	
}
