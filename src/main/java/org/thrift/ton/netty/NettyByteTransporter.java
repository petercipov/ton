package org.thrift.ton.netty;

import org.thrift.ton.rx.Observer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultProgressivePromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
/**
 *
 * @author Peter Cipov
 */
public class NettyByteTransporter {
	
	private final NioEventLoopGroup executor;
	
	public NettyByteTransporter(NioEventLoopGroup executor) {
		this.executor = executor;
	}
	
	public Promise<Observer<byte[]>> start(final String host, final int port, final Observer<byte[]> bytesReceived) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.group(executor);
		bootstrap.handler(new NettyReadingHandler(bytesReceived));

		final ChannelFuture channelFuture = bootstrap.connect(host, port);
		
		final Promise<Observer<byte[]>> sendingPrommise = new DefaultProgressivePromise<Observer<byte[]>>(executor.iterator().next());
		sendingPrommise.setUncancellable();
		
		channelFuture.addListener(new GenericFutureListener() {

			@Override
			public void operationComplete(Future f) throws Exception {
				if (f.isSuccess()) {
					sendingPrommise.setSuccess(new NettyWritingHandler(channelFuture.channel()));
				} else {
					sendingPrommise.setFailure(f.cause());
				}
			}
		});
		
		return sendingPrommise;
	}
}