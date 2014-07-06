package org.thrift.ton.protocol.binary;

import org.thrift.ton.Builder;
import org.thrift.ton.tokens.Token;
import org.thrift.ton.rx.Observer;
import org.thrift.ton.rx.ObserverHelper;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author Peter Cipov
 */
public class BinaryProtocol implements Observer<byte[]> {
	
	private final Deque<Builder> stack;
	private final ByteReader bytes;
	private final Observer<Token> receivedTokens;

	public BinaryProtocol(Observer<Token> receivedTokens) {
		this.receivedTokens = receivedTokens;
		this.bytes = new ByteReader();
		this.stack = new ArrayDeque<Builder>();
		stack.push(new NonStrictMessageBuilder(receivedTokens, stack, bytes));
	}

	@Override
	public void onNext(byte[] value) {
		bytes.append(value);
		
		while(! stack.isEmpty()) {
			Builder b = stack.peek();
			switch(b.work()) {
				case NEED_MORE_BYTES:
					//noop & finish
					return;
				case NEW_BUILDER:
					//noop
					break;
				case ERROR:
					stack.clear();
					return;
				case FINISHED:
					stack.pop();
				break;
			}
		}
		ObserverHelper.pushSuc(receivedTokens);
	}
	
	public void onWriterAvaible(Observer<byte[]> writer) {
	}

	@Override
	public void onSuccess() {}

	@Override
	public void onError(Throwable t) {}

	
	
}


