package com.cf.data.model.poloniex;

import com.google.gson.Gson;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 *
 * @author David
 */
public class PoloniexChartData {

    public final ZonedDateTime date;
    public final BigDecimal high;
    public final BigDecimal low;
    public final BigDecimal open;
    public final BigDecimal close;
    public final BigDecimal volume;
    public final BigDecimal quoteVolume;
    public final BigDecimal weightedAverage;

    public PoloniexChartData(ZonedDateTime date, BigDecimal high, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal volume, BigDecimal quoteVolume, BigDecimal weightedAverage) {
        this.date = date;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.quoteVolume = quoteVolume;
        this.weightedAverage = weightedAverage;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
