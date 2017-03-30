package com.cf.data.model.poloniex;

import com.google.gson.Gson;
import java.math.BigDecimal;

/**
 *
 * @author David
 */
public class PoloniexTicker
{
    public final BigDecimal last;
    public final BigDecimal lowestAsk;
    public final BigDecimal highestBid;
    public final BigDecimal percentageChange;
    public final BigDecimal baseVolume;
    public final BigDecimal quoteVolume;

    public PoloniexTicker(BigDecimal last, BigDecimal lowestAsk, BigDecimal highestBid, BigDecimal percentageChange, BigDecimal baseVolume, BigDecimal quoteVolume)
    {
        this.last = last;
        this.lowestAsk = lowestAsk;
        this.highestBid = highestBid;
        this.percentageChange = percentageChange;
        this.baseVolume = baseVolume;
        this.quoteVolume = quoteVolume;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
