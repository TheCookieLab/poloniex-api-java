package com.cf.data.model.poloniex;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author cheolhee
 */
public class PoloniexActiveLoan extends PoloniexLoanOffer
{
    public final String currency;
    public final BigDecimal fees;

    public PoloniexActiveLoan(String id, String currency, BigDecimal rate, BigDecimal amount, Integer range, Integer autoRenew, LocalDateTime date, BigDecimal fees)
    {
        super(id, rate, amount, range, autoRenew, date);
        this.currency = currency;
        this.fees = fees;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
