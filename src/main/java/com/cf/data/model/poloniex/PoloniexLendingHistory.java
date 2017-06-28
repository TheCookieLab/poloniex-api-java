package com.cf.data.model.poloniex;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author cheolhee
 */
public class PoloniexLendingHistory
{

    public final String id;
    public final String currency;
    public final BigDecimal rate;
    public final BigDecimal amount;
    public final BigDecimal duration;
    public final BigDecimal interest;
    public final BigDecimal fee;
    public final BigDecimal earned;
    public final LocalDateTime open;
    public final LocalDateTime close;

    public PoloniexLendingHistory(String id, String currency, BigDecimal rate, BigDecimal amount, BigDecimal duration, BigDecimal interest, BigDecimal fee, BigDecimal earned, LocalDateTime open, LocalDateTime close)
    {
        this.id = id;
        this.currency = currency;
        this.rate = rate;
        this.amount = amount;
        this.duration = duration;
        this.interest = interest;
        this.fee = fee;
        this.earned = earned;
        this.open = open;
        this.close = close;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
