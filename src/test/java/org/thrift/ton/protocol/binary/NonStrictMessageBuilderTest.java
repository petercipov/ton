package org.thrift.ton.protocol.binary;

import org.thrift.ton.protocol.binary.BinaryProtocol;
import org.thrift.ton.BlockingObserver;
import org.thrift.ton.gen.ThriftInputGenerator;
import org.thrift.ton.tokens.Token;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Peter Cipov
 */
public class NonStrictMessageBuilderTest {
	
	@Test
	public void simpleTriftToTokens() {
		//given
		byte[] input = ThriftInputGenerator.nonStrictSimple();
		BlockingObserver<Token> bo = new BlockingObserver<Token>();
		BinaryProtocol bp = new BinaryProtocol(bo);
		
		//when
		//aplying input byte by byte
		for(byte b : input) {
			bp.onNext(new byte[] {b});
		}
		
		List<Token> tokens = bo.list();
		assertNotNull(tokens);
		assertFalse(tokens.isEmpty());
		
		ThriftInputGenerator.requireNonStrictSimple(tokens);
	}
	
	@Test
	public void substruct() {
		//given
		byte[] input = ThriftInputGenerator.nonStrictSubenum();
		BlockingObserver<Token> bo = new BlockingObserver<Token>();
		BinaryProtocol bp = new BinaryProtocol(bo);
		
		//when
		//aplying input byte by byte
		for(byte b : input) {
			bp.onNext(new byte[] {b});
		}
		
		List<Token> tokens = bo.list();
		assertNotNull(tokens);
		assertFalse(tokens.isEmpty());
		
		ThriftInputGenerator.requireNonStrictSubenum(tokens);
	}
}
