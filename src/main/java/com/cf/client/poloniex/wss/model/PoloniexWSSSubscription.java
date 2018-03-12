package com.cf.client.poloniex.wss.model;

import com.google.gson.Gson;

/**
 *
 * @author David
 */
public class PoloniexWSSSubscription {

    public final static transient PoloniexWSSSubscription TICKER = new PoloniexWSSSubscription("1002");
    public final static transient PoloniexWSSSubscription HEARTBEAT = new PoloniexWSSSubscription("1010");
    public final static transient PoloniexWSSSubscription BASE_COIN_DAILY_VOLUME_STATS = new PoloniexWSSSubscription("1003");
    public final static transient PoloniexWSSSubscription USDT_BTC = new PoloniexWSSSubscription("121");
    public final static transient PoloniexWSSSubscription USDT_ETH = new PoloniexWSSSubscription("149");

    public final String command;
    public final String channel;

    public PoloniexWSSSubscription(String channel) {
        this.command = "subscribe";
        this.channel = channel;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
