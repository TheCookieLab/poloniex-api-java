package com.cf.client.wss.subscription;

import com.google.gson.Gson;

/**
 *
 * @author David
 */
public class Subscription {
    
    public final static transient Subscription TICKER = new Subscription("subscribe", "1002");
    public final static transient Subscription HEARTBEAT = new Subscription("subscribe", "1010");
    public final static transient Subscription BASE_COIN_DAILY_VOLUME_STATS = new Subscription("subscribe", "1003");
    public final static transient Subscription USDT_BTC = new Subscription("subscribe", "121");
    public final static transient Subscription USDT_ETH = new Subscription("subscribe", "149");

    public final String command;
    public final String channel;

    public Subscription(String command, String channel) {
        this.command = command;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
