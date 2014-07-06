package org.thrift.ton.protocol.binary;

import org.thrift.ton.Builder;
import org.thrift.ton.Builder.State;
import static org.thrift.ton.Builder.State.*;
import org.thrift.ton.rx.Observer;
import org.thrift.ton.tokens.BooleanToken;
import org.thrift.ton.tokens.ByteToken;
import org.thrift.ton.tokens.DoubleToken;
import org.thrift.ton.tokens.FieldType;
import org.thrift.ton.tokens.IntegerToken;
import org.thrift.ton.tokens.LongToken;
import org.thrift.ton.tokens.ShortToken;
import org.thrift.ton.tokens.StructToken;
import org.thrift.ton.tokens.Token;
import java.util.Deque;

/**
 *
 * @author Peter Cipov
 */
public class Reader {
	public static State readElement(short seqid, FieldType type, ByteReader bytes, Deque<Builder> stack, Observer<Token> tokens) {
		switch(type) {
			case STRING:
				stack.push(new BinaryBuilder(seqid, tokens, stack, bytes));
				return NEW_BUILDER;
			case DOUBLE:
				if (bytes.hasDouble()) {
					double value = bytes.readDouble();
					tokens.onNext(new DoubleToken(seqid, value));
					return FINISHED;
				} else {
					return NEED_MORE_BYTES;
				}
			case I64:
				if (bytes.hasI64()) {
					long value = bytes.readI64();
					tokens.onNext(new LongToken(seqid, value));
					return FINISHED;
				} else {
					return NEED_MORE_BYTES;
				}
			case I32:
				if (bytes.hasI32()) {
					int value = bytes.readI32();
					tokens.onNext(new IntegerToken(seqid, value));
					return FINISHED;
				} else {
					return NEED_MORE_BYTES;
				}
			case I16:
				if (bytes.hasI16()) {
					short value = bytes.readI16();
					tokens.onNext(new ShortToken(seqid, value));
					return FINISHED;
				} else {
					return NEED_MORE_BYTES;
				}
			case BYTE:
				if (bytes.hasByte()) {
					byte value = bytes.readByte();
					tokens.onNext(new ByteToken(seqid, value));
					return FINISHED;
				} else {
					return NEED_MORE_BYTES;
				}
			case BOOLEAN:
				if (bytes.hasByte()) {
					byte b = bytes.readByte();
					tokens.onNext(new BooleanToken(seqid, b == 1));
					return FINISHED;
				} else {
					return NEED_MORE_BYTES;
				}
			case STRUCT:
				stack.push(new StructBuilder(tokens, stack, bytes));
				tokens.onNext(new StructToken(seqid));
				return NEW_BUILDER;
			case ENUM:
				if (bytes.hasI32()) {
					int value = bytes.readI32();
					tokens.onNext(new IntegerToken(seqid, value));
					return FINISHED;
				} else {
					return NEED_MORE_BYTES;
				}
			case LIST:
				stack.push(new ListBuilder(seqid, tokens, stack, bytes));
				return NEW_BUILDER;
			case MAP:
				stack.push(new MapBuilder(seqid, tokens, stack, bytes));
				return NEW_BUILDER;
			case SET:
				stack.push(new SetBuilder(seqid, tokens, stack, bytes));
				return NEW_BUILDER;
			default:
				return ERROR;
		}
	}
}
