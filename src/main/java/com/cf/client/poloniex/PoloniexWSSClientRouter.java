package com.cf.client.poloniex;

import com.cf.client.wss.handler.LoggerMessageHandler;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.CharsetUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import com.cf.client.wss.handler.IMessageHandler;

public class PoloniexWSSClientRouter extends SimpleChannelInboundHandler<Object> {

    private final static Logger LOG = LogManager.getLogger();
    private static final int MAX_FRAME_LENGTH = 1262144;

    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;    
    private boolean running;
    
    private Map<Double, IMessageHandler> subscriptions;
    private final IMessageHandler defaultSubscriptionMessageHandler;
    private final Gson gson;

    public PoloniexWSSClientRouter(URI url, Map<Double, IMessageHandler> subscriptions) throws URISyntaxException {
        this(WebSocketClientHandshakerFactory
                .newHandshaker(url, WebSocketVersion.V13, null, true, new DefaultHttpHeaders(), MAX_FRAME_LENGTH), subscriptions);
    }

    public PoloniexWSSClientRouter(WebSocketClientHandshaker handshaker, Map<Double, IMessageHandler> subscriptions) {
        this.handshaker = handshaker;
        this.subscriptions = subscriptions;
        this.defaultSubscriptionMessageHandler = new LoggerMessageHandler();
        this.gson = new Gson();
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.trace("WebSocket Client disconnected!");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            try {
                handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                running = true;
                LOG.trace("WebSocket Client connected!");
                handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException e) {
                LOG.trace("WebSocket Client failed to connect");
                running = false;
                handshakeFuture.setFailure(e);
            }
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content="
                    + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            LOG.trace("WebSocket Client received message: " + textFrame.text());
            List<Map> results = this.gson.fromJson(textFrame.text(), List.class);
            this.subscriptions.getOrDefault(results.get(0), this.defaultSubscriptionMessageHandler).handle(textFrame.text());
            
        } else if (frame instanceof CloseWebSocketFrame) {
            LOG.trace("WebSocket Client received closing");
            running = false;
            ch.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error(cause);
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        running = false;
        ctx.close();
    }

    public boolean isRunning() {
        return running;
    }
}
