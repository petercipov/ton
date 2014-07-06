package org.thrift.ton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Peter Cipov
 */
public class BlockingBytesObserver extends BlockingObserver<byte[]>{
		
	public byte[] get() {
		Collection<byte[]> list = list();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			for (byte[] b : list) {
				bos.write(b);
			}
		} catch(IOException ex) {
			throw new IllegalStateException(ex);
		}
		
		return bos.toByteArray();
	}
	
}
