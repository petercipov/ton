package org.thrift.ton.protocol.binary;

import org.thrift.ton.protocol.binary.ByteReader;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter Cipov
 */
public class ByteReaderTest {
	
	public ByteReaderTest() {
	}

	@Test
	public void singleBlob() {
		byte[] input = new byte[46];
		
		ByteReader b = new ByteReader();
		b.append(input);
		
		b.read(45);
		b.readByte();
	}
	
}
