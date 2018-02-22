package com.cf.client;

import com.cf.client.wss.subscription.Subscription;
import com.cf.client.wss.handler.SubscriptionMessageHandler;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import com.cf.client.wss.router.WebSocketClientRouter;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author thiko
 */
public class WSSClient implements AutoCloseable {

    private static final int MAX_CONTENT_BYTES = 8192;
    private static final String SCHEME_WSS = "wss";

    private final URI uri;
    private final SslContext sslCtx;
    private final EventLoopGroup group;

    private Map<Subscription, SubscriptionMessageHandler> subscriptions;

    public WSSClient(String url) throws Exception {
        uri = new URI(url);

        if (!SCHEME_WSS.equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("Only WSS is supported");
        }

        // FIXME: use secure trust manager
        sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        group = new NioEventLoopGroup();
        subscriptions = new HashMap<>();
    }

    /**
     * *
     *
     * @param subscription
     * @param subscriptionMessageHandler
     */
    public void addSubscription(Subscription subscription, SubscriptionMessageHandler subscriptionMessageHandler) {
        this.subscriptions.put(subscription, subscriptionMessageHandler);
    }

    /**
     * *
     *
     * @param runTimeInMillis The subscription time expressed in milliseconds.
     * The minimum runtime is 1 minute.
     * @throws InterruptedException
     * @throws IOException
     * @throws java.net.URISyntaxException
     */
    public void run(long runTimeInMillis) throws InterruptedException, IOException, URISyntaxException {

        final WebSocketClientRouter router = new WebSocketClientRouter(uri, subscriptions.entrySet().stream()
                .collect(Collectors.toMap((Map.Entry<Subscription, SubscriptionMessageHandler> e) -> Double.parseDouble(e.getKey().channel), Map.Entry::getValue)));

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline p = ch.pipeline();
                p.addLast(sslCtx.newHandler(ch.alloc(), uri.getHost(), 443));
                p.addLast(new HttpClientCodec(), new HttpObjectAggregator(MAX_CONTENT_BYTES),
                        WebSocketClientCompressionHandler.INSTANCE, router);
            }
        });

        Channel ch = b.connect(uri.getHost(), 443).sync().channel();
        router.handshakeFuture().sync();

        for (Entry<Subscription, SubscriptionMessageHandler> subscription : subscriptions.entrySet()) {
            WebSocketFrame frame = new TextWebSocketFrame(subscription.getKey().toString());
            ch.writeAndFlush(frame);
        }

        long startTime = System.currentTimeMillis();

        while (router.isRunning() == true && (startTime + runTimeInMillis > System.currentTimeMillis())) {
            TimeUnit.MINUTES.sleep(1);
        }
    }

    @Override
    public void close() throws Exception {
        group.shutdownGracefully();
    }
}
