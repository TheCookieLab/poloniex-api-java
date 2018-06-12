package com.cf.client.poloniex.wss.model;

import com.google.gson.Gson;
import java.math.BigDecimal;

/**
 *
 * @author David
 */
public class PoloniexOrderBookEntry {

    public final String type;
    public final BigDecimal rate;
    public final BigDecimal amount;

    public PoloniexOrderBookEntry(String type, BigDecimal rate, BigDecimal amount) {
        this.type = type;
        this.rate = rate;
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
