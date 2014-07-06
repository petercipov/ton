package org.thrift.ton.protocol.binary;

import org.thrift.ton.Builder;
import static org.thrift.ton.Builder.State.*;
import org.thrift.ton.rx.Observer;
import org.thrift.ton.tokens.FieldType;
import org.thrift.ton.tokens.MapToken;
import org.thrift.ton.tokens.Token;
import java.util.Deque;

/**
 *
 * @author Peter Cipov
 */
public class MapBuilder implements Builder {
	
	private final short seqid;
	private final Observer<Token> tokens;
	private final Deque<Builder> stack;
	private final ByteReader bytes;
	
	private int state = 0;
	private FieldType keyType;
	private FieldType valueType;
	private int mapSize;
	private int elementsToRead;

	public MapBuilder(short seqid, Observer<Token> tokens, Deque<Builder> stack, ByteReader bytes) {
		this.seqid = seqid;
		this.tokens = tokens;
		this.stack = stack;
		this.bytes = bytes;
	}
	
	@Override
	public State work() {
		while(true) {
			switch(state) {
				case 0:
					if (bytes.hasByte()) {
						byte code = bytes.readByte();
						keyType = FieldType.fromCode(code);
						state = 1;
					} else {
						return NEED_MORE_BYTES;
					}
				break;
				case 1:
					if (bytes.hasByte()) {
						byte code = bytes.readByte();
						valueType = FieldType.fromCode(code);
						state = 2;
					} else {
						return NEED_MORE_BYTES;
					}
				break;
				case 2:
					if (bytes.hasI32()) {
						mapSize = bytes.readI32();
						elementsToRead = mapSize;
						state = 3;
						tokens.onNext(new MapToken(seqid, keyType, valueType, mapSize));
					} else {
						return NEED_MORE_BYTES;
					}
				break;
				case 3:
					for (int i=0; i < elementsToRead;) {
						State s = Reader.readElement(seqid, keyType, bytes, stack, tokens);
						switch(s) {
							case NEED_MORE_BYTES:
								return s;
							case NEW_BUILDER:
								state = 4;
								return s;
							case FINISHED:
								state = 4;
								break;
							case ERROR:
								elementsToRead--;
								return s;
						}
					}
					return ERROR;
				case 4:
					State s = Reader.readElement(seqid, valueType, bytes, stack, tokens);
					switch(s) {
						case NEED_MORE_BYTES:
							return s;
						case NEW_BUILDER:
							elementsToRead--;
							state = 3;
							return s;
						case FINISHED:
							elementsToRead--;
							state = 3;
							break;
						case ERROR:
							elementsToRead--;
							state = 3;
							return s;
					}
				break;
					
			}
		}
	}
	
}
