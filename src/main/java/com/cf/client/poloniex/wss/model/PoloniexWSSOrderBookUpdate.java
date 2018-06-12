package com.cf.client.poloniex.wss.model;

import com.google.gson.Gson;

/**
 *
 * @author David
 */
public class PoloniexWSSOrderBookUpdate {

    public final Double currencyPair;
    public final Double orderNumber;
    public final PoloniexOrderBookEntry previousEntry;
    public final PoloniexWSSOrderBookUpdate replacementEntry;

    public PoloniexWSSOrderBookUpdate(Double currencyPair, Double orderNumber, PoloniexOrderBookEntry previousEntry, PoloniexWSSOrderBookUpdate replacementEntry) {
        this.currencyPair = currencyPair;
        this.orderNumber = orderNumber;
        this.previousEntry = previousEntry;
        this.replacementEntry = replacementEntry;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
