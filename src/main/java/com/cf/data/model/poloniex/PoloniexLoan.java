package com.cf.data.model.poloniex;

import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author David
 */
public class PoloniexLoan
{
    public final Long id;
    public final BigDecimal rate;
    public final BigDecimal amount;
    public final Long duration;
    public final Long range;
    public final Long autoRenew;
    public final String date;
    public final BigDecimal fees;

    public PoloniexLoan(Long id, BigDecimal rate, BigDecimal amount, Long duration, Long range, Long autoRenew, String date, BigDecimal fees)
    {
        
        this.id = id;
        this.rate = rate;
        this.amount = amount;
        this.duration = duration;
        this.range = range;
        this.autoRenew = autoRenew;
        this.date = date;
        this.fees = fees;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
