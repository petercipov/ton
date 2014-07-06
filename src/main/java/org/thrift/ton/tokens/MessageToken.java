package org.thrift.ton.tokens;

/**
 *
 * @author Peter Cipov
 */
public class MessageToken implements Token{
	private final String name;
	private final MessageType type;
	private final int seqId;

	public MessageToken(String name, MessageType type, int seqId) {
		this.name = name;
		this.type = type;
		this.seqId = seqId;
	}

	public String getName() {
		return name;
	}

	public MessageType getType() {
		return type;
	}

	public int getSeqId() {
		return seqId;
	}

	@Override
	public TokenType getTokenType() {
		return TokenType.Message;
	}
}
