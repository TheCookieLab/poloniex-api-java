package com.cf.data.model.poloniex;

import com.google.gson.Gson;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 *
 * @author David
 */
public class PoloniexOrderTrade {

    public final Long globalTradeID;
    public final Long tradeID;
    public final String currencyPair;
    public final String type;
    public final BigDecimal rate;
    public final BigDecimal amount;
    public final BigDecimal total;
    public final BigDecimal fee;
    public final ZonedDateTime date;

    public PoloniexOrderTrade(Long globalTradeID, Long tradeID, String currencyPair, String type, BigDecimal rate, BigDecimal amount, BigDecimal total, BigDecimal fee, ZonedDateTime date) {
        this.globalTradeID = globalTradeID;
        this.tradeID = tradeID;
        this.currencyPair = currencyPair;
        this.type = type;
        this.rate = rate;
        this.amount = amount;
        this.total = total;
        this.fee = fee;
        this.date = date;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
