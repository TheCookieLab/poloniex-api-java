package com.cf.client.poloniex.wss.model;

import com.google.gson.Gson;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author David
 */
public class PoloniexOrderBook {

    public final Map<String, PoloniexOrderBookEntry> bids;
    public final Map<String, PoloniexOrderBookEntry> asks;

    public PoloniexOrderBook() {
        this.bids = new TreeMap<>();
        this.asks = new TreeMap<>();
    }

    public PoloniexOrderBook(Map<String, PoloniexOrderBookEntry> bids, Map<String, PoloniexOrderBookEntry> asks) {
        this.bids = bids;
        this.asks = asks;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
