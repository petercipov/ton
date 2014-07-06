package org.thrift.ton;

import org.thrift.ton.netty.NettyByteTransporter;
import org.thrift.ton.rx.Observer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Promise;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author Peter Cipov
 */
public class ByteTransporterTest {
	
	@Rule
	public SocketRule server  = new SocketRule(15211);

	@Test
	public void testSomeMethod() throws Exception {
		NioEventLoopGroup loop = new NioEventLoopGroup(1, new DefaultThreadFactory("ton-loop"));
		NettyByteTransporter bt = new NettyByteTransporter(loop);
		
		final byte[] inBytes = 
		(
			"GET / HTTP/1.1\n" +
			"Host: www.zive.cz\n" +
			"Connection: keep-alive\n" +
			"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
			"User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.137 Safari/537.36\n" +
			"Accept-Encoding: gzip,deflate,sdch\n" +
			"Accept-Language: sk-SK,sk;q=0.8,cs;q=0.6,en-US;q=0.4,en;q=0.2\n\n"
		).getBytes("UTF-8");
		
		final byte[] outByteOK = 
		(	
			"HTTP 1.1 200 OK"
		).getBytes("UTF-8");
		
		
		final byte[] outByteFAIL = 
		(	
			"HTTP 1.1 500 INTERNAL_ERROR"
		).getBytes("UTF-8");
		
		
		server.accepeted().subscribe(new Observer<Socket>() {

			@Override
			public void onNext(Socket socket) {
				try {
					InputStream in = socket.getInputStream();
					
					byte[] buf = new byte[inBytes.length];
					in.read(buf);
					
					OutputStream out = socket.getOutputStream();
					
					if (Arrays.equals(buf, inBytes)) {
						out.write(outByteOK);
					} else {
						out.write(outByteFAIL);
					}
					out.close();
					in.close();
				} catch(IOException ex) {
				} finally{
					try {
						socket.close();
					} catch(IOException ex) {}
				}
			}

			@Override
			public void onSuccess() {
			}

			@Override
			public void onError(Throwable t) {
			}
		});
		
		
		BlockingBytesObserver bbo = new BlockingBytesObserver();
		Promise<Observer<byte[]>> promise = bt.start("localhost", server.getPort(), bbo);
		
		promise.get().onNext(inBytes);
		byte[] returnedBytes = bbo.get();
		
		Assert.assertArrayEquals(outByteOK, returnedBytes);
		
	}
	
}
