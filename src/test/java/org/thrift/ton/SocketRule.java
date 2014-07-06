package org.thrift.ton;

import org.thrift.ton.rx.Observable;
import org.thrift.ton.rx.Observer;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.rules.ExternalResource;

/**
 *
 * @author Peter Cipov
 */
public class SocketRule extends ExternalResource {
	
	private final int port;
	private ServerSocket ssocket;
	private ExecutorService executor;
	
	private Observable<Socket> accepeted;

	public SocketRule(int port) {
		this.port = port;
	}

	@Override
	protected void before() throws Throwable {
		this.ssocket = new ServerSocket(port);
		this.executor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("socket-server-thread"));
		
		this.accepeted = Observable.create(new Observable.Action<Socket>() {

			@Override
			public void perform(final Observer<Socket> observer) {
				executor.submit(new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						while(true) {
							observer.onNext(ssocket.accept());
						}
					}
				});
			}
		});
	}

	public Observable<Socket> accepeted() {
		return accepeted;
	}
	
	public int getPort() {
		return port;
	}
	
	@Override
	protected void after() {
		executor.shutdownNow();
		try {
			if (ssocket != null) {
				ssocket.close();
			}
		}catch(IOException ex) {
			throw new IllegalStateException(ex);
		}
	}
	
}
