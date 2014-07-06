package org.thrift.ton;

import org.thrift.ton.tokens.Token;
import org.thrift.ton.protocol.binary.BinaryProtocol;
import org.thrift.ton.netty.NettyByteTransporter;
import org.thrift.ton.rx.Observer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter Cipov
 */
public class Executioner {
	
	private static final Logger logger = LoggerFactory.getLogger(Executioner.class);
	
	private final NioEventLoopGroup loop;
	private final NettyByteTransporter nbt;
	private final String host;
	private final int port;

	public Executioner(String host, int port) {
		this(host, port, "ton-loop");
	}
	
	public Executioner(String host, int port, String threadnamePrefix) {
		this.loop = new NioEventLoopGroup(1, new DefaultThreadFactory(threadnamePrefix));
		this.nbt =  new NettyByteTransporter(loop);
		this.host = host;
		this.port = port;
	}
	
	
	public void execute(Observer<Token> receivedTokens) {
		
		final BinaryProtocol message = new BinaryProtocol(receivedTokens);
		final Promise<Observer<byte[]>> promise = nbt.start(host, port, message);
		promise.addListener(new GenericFutureListener<Future<Observer<byte[]>>>() {

			@Override
			public void operationComplete(Future<Observer<byte[]>> future) throws Exception {				
				message.onWriterAvaible(future.get());
			}
		});
		
	}
	
	public Future close() {
		return loop.shutdownGracefully();
	}
	
	
}
