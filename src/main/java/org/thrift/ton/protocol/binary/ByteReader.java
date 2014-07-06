package org.thrift.ton.protocol.binary;

/**
 *
 * @author Peter Cipov
 */
public class ByteReader {
	
	private ByteChain head;
	private ByteChain tail;
	private int headPosition = 0;
	
	public void append(byte[] value) {
		if (head == null || tail == null) {
			head = new ByteChain(value);
			tail = head;
		} else {
			ByteChain n = new ByteChain(value);
			tail.next = n;
			tail = n;
		}
	}

	public boolean has(int byteCount) {
		ByteChain current = head;
		int counter = byteCount + headPosition;
		
		while(current != null) {
			counter -= current.bytes.length;
			if (counter <= 0) {
				return true;
			}
			current= current.next;
		}
		
		return false;
	}
	
	public boolean hasDouble() {
		return has(8);
	}
	
	public boolean hasI64() {
		return has(8);
	}
	
	public boolean hasI32() {
		return has(4);
	}
	
	public boolean hasI16() {
		return has(2);
	}
	
	public boolean hasByte() {
		return has(1);
	}
	
	public long readI64() {
		byte[] buf = read(8);
		return
			((long)(buf[0] & 0xff) << 56) |
			((long)(buf[1] & 0xff) << 48) |
			((long)(buf[2] & 0xff) << 40) |
			((long)(buf[3] & 0xff) << 32) |
			((long)(buf[4] & 0xff) << 24) |
			((long)(buf[5] & 0xff) << 16) |
			((long)(buf[6] & 0xff) <<  8) |
			((long)(buf[7] & 0xff))
		;
  }
	
	public int readI32() {
		byte[] buf = read(4);
		return  ( (buf[0] & 0xff) << 24)
				| ((buf[1] & 0xff) << 16)
				| ((buf[2] & 0xff) << 8)
				| ((buf[3] & 0xff));
	}
	
	public short readI16() {
		byte[] buf = read(2);
		return (short)
		(((buf[0] & 0xff) << 8) 
			| (buf[1] & 0xff)
		);
	}
	
	public double readDouble() {
		return Double.longBitsToDouble(readI64());
	}
	
	public byte readByte() {
		return read(1)[0];
	}

	public byte[] read(final int count) {
		byte[] raw = new byte[count];
		int toRead = count;
		int rawPosition = 0;

		while(head != null) {
			int canHave = head.bytes.length - headPosition;
			if (canHave > toRead) {
				//array larger than needed
				System.arraycopy(head.bytes, headPosition, raw, rawPosition, toRead);
				headPosition += toRead;
				return raw;
			} else {				
				//array equal or smaller than needed
				System.arraycopy(head.bytes, headPosition, raw, rawPosition, canHave);
				headPosition = 0;
				head = head.next;
				
				if (canHave == toRead) {
					return raw;
				}
				
				toRead -= canHave;
				rawPosition += canHave;
				
			}
		}
		
		throw new IllegalStateException("Not enough data to read.");
	}

	class ByteChain {
		final byte[] bytes;
		ByteChain next;

		public ByteChain(byte[] bytes) {
			this.bytes = bytes;
		}

	}

}
