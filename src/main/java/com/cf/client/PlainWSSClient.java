
package com.cf.client;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import com.cf.data.handler.poloniex.WebSocketClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 *
 * @author thiko
 */
public class PlainWSSClient implements AutoCloseable {

	private static final int MAX_CONTENT_BYTES = 8192;
	private static final String SCHEME_WSS = "wss";

	private final URI uri;
	private final SslContext sslCtx;
	private final EventLoopGroup group;

	// Connect with V13 (RFC 6455 aka HyBi-17).
	private final WebSocketVersion webSocketVersion = WebSocketVersion.V13;

	public PlainWSSClient(String url) throws Exception {
		uri = new URI(url);

		if (!SCHEME_WSS.equalsIgnoreCase(uri.getScheme())) {
			throw new IllegalArgumentException("Only WSS is supported");
		}

		// FIXME: use secure trust manager
		sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		group = new NioEventLoopGroup();
	}

	/***
	 * 
	 * @param runTimeInMillis
	 *            The subscription time expressed in milliseconds. The minimum
	 *            runtime is 1 minute.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void run(long runTimeInMillis) throws InterruptedException, IOException {

		final WebSocketClientHandler handler = new WebSocketClientHandler(WebSocketClientHandshakerFactory
				.newHandshaker(uri, webSocketVersion, null, true, new DefaultHttpHeaders()));

		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) {
				ChannelPipeline p = ch.pipeline();
				p.addLast(sslCtx.newHandler(ch.alloc(), uri.getHost(), 443));
				p.addLast(new HttpClientCodec(), new HttpObjectAggregator(MAX_CONTENT_BYTES),
						WebSocketClientCompressionHandler.INSTANCE, handler);
			}
		});

		Channel ch = b.connect(uri.getHost(), 443).sync().channel();
		handler.handshakeFuture().sync();

		WebSocketFrame frame = new TextWebSocketFrame("{\"command\":\"subscribe\", \"channel\": \"1002\"}");
		ch.writeAndFlush(frame);

		long startTime = System.currentTimeMillis();

		while (handler.isRunning() == true && (startTime + runTimeInMillis > System.currentTimeMillis())) {
			TimeUnit.MINUTES.sleep(1);
		}
	}

	@Override
	public void close() throws Exception {
		group.shutdownGracefully();
	}
}
