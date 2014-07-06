package org.thrift.ton.gen;

import java.io.ByteArrayOutputStream;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author Peter Cipov
 */
public class TWriteOnlyTransport extends TTransport {
	
	private final ByteArrayOutputStream bos;
	private boolean opened;

	public TWriteOnlyTransport() {
		bos = new ByteArrayOutputStream();
		opened = true;
	}
	
	@Override
	public boolean isOpen() {
		return opened;
	}

	@Override
	public void open() throws TTransportException {
		opened = true;
	}

	@Override
	public void close() {
		opened = false;
	}

	@Override
	public int read(byte[] buf, int off, int len) throws TTransportException {
		throw new TTransportException();
	}

	@Override
	public void write(byte[] buf, int off, int len) throws TTransportException {
		bos.write(buf, off, len);
	}
	
	public byte[] array() {
		return bos.toByteArray();
	}
	
	public TReadOnlyTransport toReadOnlyTransport() {
		return new TReadOnlyTransport(array());
	}
}
