package org.thrift.ton.gen;

import org.thrift.ton.gen.nonStrictSimple.Servica;
import org.thrift.ton.gen.nonStrictSimple.Simple;
import org.thrift.ton.gen.nonStrictSubenum.Struktura;
import org.thrift.ton.gen.nonStrictSubenum.SubServica;
import org.thrift.ton.gen.nonStrictSubenum.SubStruct;
import org.thrift.ton.gen.nonStrictSubenum.Subenum;
import org.thrift.ton.tokens.Token;
import org.thrift.ton.tokens.Tokens;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.junit.Assert;

/**
 *
 * @author Peter Cipov
 */
public class ThriftInputGenerator {

	private static final Charset utf8 = Charset.forName("UTF-8");
	private static final boolean STRICT_WRITE = true;
	private static final boolean STRICT_READ = true;
	public static byte[] nonStrictSimple() {
		try {
			Servica.Processor processor = new Servica.Processor(new Servica.Iface() {

				@Override
				public Simple dajSimple() throws TException {
					return new Simple(
						true,
						(byte) 100,
						(short) 1234,
						1234567,
						1234567890,
						48.55,
						"string1",
						ByteBuffer.wrap(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}),
						"senum"
					);
				}
			});

			TWriteOnlyTransport clientOutput = new TWriteOnlyTransport();
			Servica.Client client = new Servica.Client(new TBinaryProtocol(
				clientOutput, 
				!STRICT_READ, 
				!STRICT_WRITE)
			);
			try {
				client.dajSimple();
			} catch(Exception ex) {
				// no output socket is bound, no response from server => exception
			}
			
			TReadOnlyTransport serverInput = clientOutput.toReadOnlyTransport();
			TWriteOnlyTransport serverOutput = new TWriteOnlyTransport();
			processor.process(
				new TBinaryProtocol(
					serverInput, 
					!STRICT_READ, 
					!STRICT_WRITE
				), 
				new TBinaryProtocol(
					serverOutput, 
					!STRICT_READ, 
					!STRICT_WRITE
				)
			);
			return serverOutput.array();
		} catch (TException ex) {
			throw new IllegalStateException(ex);
		}
	}

	public static void requireNonStrictSimple(List<Token> tokens) {
		Assert.assertEquals(11, tokens.size());
		Tokens.requireReply(tokens.get(0));
		Tokens.requireReplyStruct(tokens.get(1));

		Tokens.requireBooleanToken(tokens.get(2), true);
		Tokens.requireByteToken(tokens.get(3), (byte) 100);
		Tokens.requireShortToken(tokens.get(4), (short) 1234);
		Tokens.requireIntegerToken(tokens.get(5), (int) 1234567);
		Tokens.requireLongToken(tokens.get(6), (long) 1234567890);
		Tokens.requireDoubleToken(tokens.get(7), 48.55);
		Tokens.requireBinaryToken(tokens.get(8), "string1");
		Tokens.requireBinaryToken(tokens.get(9), new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
		Tokens.requireBinaryToken(tokens.get(10), "senum");
	}

	public static byte[] nonStrictSubenum() {
		try {
			SubServica.Processor processor = new SubServica.Processor(new  SubServica.Iface() {

				@Override
				public Struktura dajStrukturu() throws TException {
					return new Struktura(new SubStruct(Subenum.a, (short) 1234));
				}
			});
			
			TWriteOnlyTransport clientOutput = new TWriteOnlyTransport();
			SubServica.Client client = new SubServica.Client(new TBinaryProtocol(
				clientOutput, 
				!STRICT_READ, 
				!STRICT_WRITE)
			);
			
			try {
				client.dajStrukturu();
			} catch(Exception ex) {
				// no output socket is bound, no response from server => exception
			}

			TReadOnlyTransport serverInput = clientOutput.toReadOnlyTransport();
			TWriteOnlyTransport serverOutput = new TWriteOnlyTransport();
			processor.process(
				new TBinaryProtocol(
					serverInput, 
					!STRICT_READ, 
					!STRICT_WRITE
				), 
				new TBinaryProtocol(
					serverOutput, 
					!STRICT_READ, 
					!STRICT_WRITE
				)
			);
			return serverOutput.array();
		} catch (TException ex) {
			throw new IllegalStateException(ex);
		}
	}

	public static void requireNonStrictSubenum(List<Token> tokens) {
		Assert.assertEquals(5, tokens.size());
		Tokens.requireReply(tokens.get(0));
		Tokens.requireReplyStruct(tokens.get(1));
		Tokens.requireStruct(tokens.get(2), (short) 1);
		Tokens.requireIntegerToken(tokens.get(3), Subenum.a.getValue());
		Tokens.requireShortToken(tokens.get(4), (short) 1234);
	}

}
