package com.cf.data.model.poloniex;

import com.google.gson.Gson;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author David
 */
public class PoloniexTradeHistory
{
    public final Long globalTradeID;
    public final String tradeID;
    public final LocalDateTime date;
    public final BigDecimal rate;
    public final BigDecimal amount;
    public final BigDecimal total;
    public final BigDecimal fee;
    public final String orderNumber;
    public final String type;
    public final String category;

    public PoloniexTradeHistory(Long globalTradeID, String tradeID, LocalDateTime date, BigDecimal rate, BigDecimal amount, BigDecimal total, BigDecimal fee, String orderNumber, String type, String category)
    {
        this.globalTradeID = globalTradeID;
        this.tradeID = tradeID;
        this.date = date;
        this.rate = rate;
        this.amount = amount;
        this.total = total;
        this.fee = fee;
        this.orderNumber = orderNumber;
        this.type = type;
        this.category = category;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
