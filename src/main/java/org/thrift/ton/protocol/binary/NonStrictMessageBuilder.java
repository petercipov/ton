package org.thrift.ton.protocol.binary;

import org.thrift.ton.Builder;
import static org.thrift.ton.Builder.State.*;
import org.thrift.ton.tokens.MessageToken;
import org.thrift.ton.tokens.MessageType;
import org.thrift.ton.tokens.Token;
import org.thrift.ton.rx.Observer;
import org.thrift.ton.tokens.FieldType;
import java.nio.charset.Charset;
import java.util.Deque;

/**
 *
 * @author Peter Cipov
 */
public class NonStrictMessageBuilder implements Builder {

	private final Observer<Token> receivedTokens;
	private final Deque<Builder> stack;
	private final ByteReader bytes;

	private int state;
	private int nameSize;
	private MessageType type;
	private String name;
	private int seqId;

	public NonStrictMessageBuilder(Observer<Token> receivedTokens, Deque<Builder> stack, ByteReader bytes) {
		this.receivedTokens = receivedTokens;
		this.stack = stack;
		this.state = 0;
		this.bytes = bytes;
	}

	@Override
	public State work() {
		Charset utf = Charset.forName("UTF-8");
		while (true) {
			switch (state) {
				case 0:
					if (bytes.hasI32()) { //method name size
						state = 1;
						nameSize = bytes.readI32();
					} else {
						return NEED_MORE_BYTES;
					}
					break;
				case 1:
					if (bytes.has(nameSize)) { //method name bytes
						state = 2;
						name = new String(bytes.read(nameSize), utf);
					} else {
						return NEED_MORE_BYTES;
					}
					break;
				case 2:
					if (bytes.hasByte()) { //message type
						state = 3;
						type = MessageType.fromCode(bytes.readByte());
					} else {
						return NEED_MORE_BYTES;
					}
					break;
				case 3:
					if (bytes.hasI32()) { //sequence ids
						state = 4;
						seqId = bytes.readI32();
						receivedTokens.onNext(new MessageToken(name, type, seqId));
						stack.push(new ResponseBuilder(receivedTokens, stack, bytes));
						return NEW_BUILDER;
					} else {
						return NEED_MORE_BYTES;
					}
				default:
					if (bytes.hasByte()) {
						//skip till end
						if (bytes.readByte() == FieldType.STOP.getCode()) {
							return FINISHED;
						}
					} else {
						return NEED_MORE_BYTES;
					}
			}
		}
	}
}
