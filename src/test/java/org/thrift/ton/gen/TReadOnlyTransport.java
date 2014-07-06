package org.thrift.ton.gen;

import java.io.ByteArrayInputStream;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author Peter Cipov
 */
public class TReadOnlyTransport extends TTransport {
	private final ByteArrayInputStream bis;
	private boolean opened;

	public TReadOnlyTransport(byte [] arr) {
		this.bis = new ByteArrayInputStream(arr);
		opened = false;
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
		return bis.read(buf, off, len);
	}

	@Override
	public void write(byte[] buf, int off, int len) throws TTransportException {
		throw new TTransportException();
	}
	
	
}
