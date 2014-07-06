package org.thrift.ton.protocol.binary;

import org.thrift.ton.Builder;
import static org.thrift.ton.Builder.State.*;
import org.thrift.ton.rx.Observer;
import org.thrift.ton.rx.ObserverHelper;
import org.thrift.ton.tokens.FieldType;
import org.thrift.ton.tokens.StructToken;
import org.thrift.ton.tokens.Token;
import java.util.Deque;

/**
 *
 * @author Peter Cipov
 */
public class ResponseBuilder implements Builder{
	
	private final Deque<Builder> queve;
	private final Observer<Token> tokens;
	private final ByteReader bytes;

	private int state;
	private short id;
	
	public ResponseBuilder(Observer<Token> tokens, Deque<Builder> queve, ByteReader bytes) {
		this.queve = queve;
		this.tokens = tokens;
		this.bytes = bytes;
		this.state = 0;
	}

	@Override
	public State work() {
		while(true) {
			switch(state) {
				case 0:
					if (bytes.hasByte()) {
						byte type = bytes.readByte();
						if (type == FieldType.STRUCT.getCode()) {
							state = 1;
							break;
						} else {
							ObserverHelper.pushEx(tokens, new IllegalStateException("Expecting response tructure but obtained "+ type));
							return ERROR;
						}
					} else {
						return NEED_MORE_BYTES;
					}
				case 1:
					if (bytes.hasI32()) {
						id = bytes.readI16();
						tokens.onNext(new StructToken(id));
						queve.push(new StructBuilder(tokens, queve, bytes));
						state = 2;
						return NEW_BUILDER;
					} else {
						return NEED_MORE_BYTES;
					}
				case 2:
					return FINISHED;
			}
		}
		
	}
	
}
