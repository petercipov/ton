package org.thrift.ton.netty;

import org.thrift.ton.rx.Observer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter Cipov
 */
public class NettyWritingHandler implements Observer<byte[]> {
	
	private static final Logger logger = LoggerFactory.getLogger(NettyWritingHandler.class);
	
	private final Channel channel;

	public NettyWritingHandler(Channel channel) {
		this.channel = channel;
	}
	
	@Override
	public void onNext(byte[] value) {
		channel.writeAndFlush(Unpooled.wrappedBuffer(value));
	}

	@Override
	public void onSuccess() {
	}

	@Override
	public void onError(Throwable t) {
		logger.warn("error while reading input ->closing channel", t);
		closeChannel();
	}
	
	private void closeChannel() {
		try {
			channel.close().sync();
		} catch(Exception ex) {
			logger.warn("could not properly close", ex);
		}
	}
}
