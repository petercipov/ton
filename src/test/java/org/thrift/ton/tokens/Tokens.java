package org.thrift.ton.tokens;

import org.thrift.ton.tokens.StructToken;
import org.thrift.ton.tokens.IntegerToken;
import org.thrift.ton.tokens.ShortToken;
import org.thrift.ton.tokens.FieldType;
import org.thrift.ton.tokens.TokenType;
import org.thrift.ton.tokens.MessageToken;
import org.thrift.ton.tokens.ByteToken;
import org.thrift.ton.tokens.Token;
import org.thrift.ton.tokens.BooleanToken;
import org.thrift.ton.tokens.MessageType;
import org.thrift.ton.tokens.LongToken;
import org.thrift.ton.tokens.DoubleToken;
import org.thrift.ton.tokens.BinaryToken;
import org.junit.Assert;

/**
 *
 * @author Peter Cipov
 */
public class Tokens {
	
	private static final short REPLY_STRUCT_ID = 0;
	
	public static MessageToken requireReply(Token token) {
		Assert.assertTrue(token instanceof MessageToken);
		Assert.assertEquals(TokenType.Message, token.getTokenType());
		MessageToken mt = (MessageToken) token;
		Assert.assertEquals(MessageType.REPLY, mt.getType());
		return mt;
	}

	public static StructToken requireStruct(Token token) {
		Assert.assertTrue(token instanceof StructToken);
		Assert.assertEquals(TokenType.Struct, token.getTokenType());
		StructToken st = (StructToken) token;
		Assert.assertEquals(FieldType.STRUCT, st.getFieldType());
		return st;
	}
	
	public static StructToken requireStruct(Token token, short fieldId) {
		StructToken st = requireStruct(token);
		Assert.assertEquals(fieldId, st.getFieldId());
		return st;
	}
	
	public static StructToken requireReplyStruct(Token token) {
		return requireStruct(token, REPLY_STRUCT_ID);
	}
	
	public static BinaryToken requireBinrayToken(Token token) {
		Assert.assertTrue(token instanceof BinaryToken);
		Assert.assertEquals(TokenType.Field, token.getTokenType());
		BinaryToken st = (BinaryToken) token;
		Assert.assertEquals(FieldType.STRING, st.getFieldType());
		return st;
	}
	
	public static BinaryToken requireBinaryToken(Token token, byte[] value) {
		BinaryToken st = requireBinrayToken(token);
		Assert.assertArrayEquals(value, st.getValue());
		return st;
	}
	
	public static BinaryToken requireBinaryToken(Token token, String value) {
		BinaryToken st = requireBinrayToken(token);
		Assert.assertEquals(value, st.toUtf8());
		return st;
	}
	
	public static DoubleToken requireDoubleToken(Token token) {
		Assert.assertTrue(token instanceof DoubleToken);
		Assert.assertEquals(TokenType.Field, token.getTokenType());
		DoubleToken dt = (DoubleToken) token;
		Assert.assertEquals(FieldType.DOUBLE, dt.getFieldType());
		return dt;
	}
	
	public static DoubleToken requireDoubleToken(Token token, double value) {
		DoubleToken dt = requireDoubleToken(token);
		Assert.assertEquals(value, dt.getValue(), 0);
		return dt;
	}
	
	public static ShortToken requireShortToken(Token token) {
		Assert.assertTrue(token instanceof ShortToken);
		Assert.assertEquals(TokenType.Field, token.getTokenType());
		ShortToken st = (ShortToken) token;
		Assert.assertEquals(FieldType.I16, st.getFieldType());
		return st;
	}
	
	public static ShortToken requireShortToken(Token token, short value) {
		ShortToken st = requireShortToken(token);
		Assert.assertEquals(value, st.getValue());
		return st;
	}
	
	public static IntegerToken requireIntegerToken(Token token) {
		Assert.assertTrue(token instanceof IntegerToken);
		Assert.assertEquals(TokenType.Field, token.getTokenType());
		IntegerToken it = (IntegerToken) token;
		Assert.assertEquals(FieldType.I32, it.getFieldType());
		return it;
	}
	
	public static IntegerToken requireIntegerToken(Token token, int value) {
		IntegerToken it = requireIntegerToken(token);
		Assert.assertEquals(value, it.getValue());
		return it;
	}
	
	public static LongToken requireLongToken(Token token) {
		Assert.assertTrue(token instanceof LongToken);
		Assert.assertEquals(TokenType.Field, token.getTokenType());
		LongToken lt = (LongToken) token;
		Assert.assertEquals(FieldType.I64, lt.getFieldType());
		return lt;
	}
	
	public static LongToken requireLongToken(Token token, long value) {
		LongToken lt = requireLongToken(token);
		Assert.assertEquals(value, lt.getValue());
		return lt;
	}
	
	
	public static ByteToken requireByteToken(Token token) {
		Assert.assertTrue(token instanceof ByteToken);
		Assert.assertEquals(TokenType.Field, token.getTokenType());
		ByteToken bt = (ByteToken) token;
		Assert.assertEquals(FieldType.BYTE, bt.getFieldType());
		return bt;
	}
	
	public static ByteToken requireByteToken(Token token, byte value) {
		ByteToken bt = requireByteToken(token);
		Assert.assertEquals(value, bt.getValue());
		return bt;
	}
	
	public static BooleanToken requireBooleanToken(Token token) {
		Assert.assertTrue(token instanceof BooleanToken);
		Assert.assertEquals(TokenType.Field, token.getTokenType());
		BooleanToken bt = (BooleanToken) token;
		Assert.assertEquals(FieldType.BOOLEAN, bt.getFieldType());
		return bt;
	}
	
	public static BooleanToken requireBooleanToken(Token token, boolean value) {
		BooleanToken bt = requireBooleanToken(token);
		Assert.assertEquals(value, bt.getValue());
		return bt;
	}
}
