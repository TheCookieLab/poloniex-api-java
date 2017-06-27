package com.cf.data.model.poloniex;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author cheolhee
 */
public class PoloniexLoanOffer
{
    public final String id;
    public final BigDecimal rate;
    public final BigDecimal amount;
    public final Integer range;
    public final Integer autoRenew;
    public final LocalDateTime date;

    public PoloniexLoanOffer(String id, BigDecimal rate, BigDecimal amount, Integer range, Integer autoRenew, LocalDateTime date)
    {
        this.id = id;
        this.rate = rate;
        this.amount = amount;
        this.range = range;
        this.autoRenew = autoRenew;
        this.date = date;
    }


    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
