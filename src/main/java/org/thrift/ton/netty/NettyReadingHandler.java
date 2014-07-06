package org.thrift.ton.netty;

import org.thrift.ton.rx.Observer;
import org.thrift.ton.rx.ObserverHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author Peter Cipov
 */
public class NettyReadingHandler extends ChannelInboundHandlerAdapter {
	
	private final Observer<byte[]> outputObserver;

	public NettyReadingHandler(Observer<byte[]> outputObserver) {
		this.outputObserver = outputObserver;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf) msg;
			int size = buf.readableBytes();
			byte[] b = new byte[size];
			buf.readBytes(b);
			buf.release();
			outputObserver.onNext(b);
		} else {
			super.channelRead(ctx, msg);
		}	
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ObserverHelper.pushSuc(outputObserver);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		ObserverHelper.pushEx(outputObserver, cause);
	}
}
