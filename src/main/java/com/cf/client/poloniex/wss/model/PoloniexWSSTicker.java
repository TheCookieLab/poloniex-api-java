package com.cf.client.poloniex.wss.model;

import com.google.gson.Gson;
import java.math.BigDecimal;

/**
 *
 * @author David
 */
public class PoloniexWSSTicker {

    public final Double currencyPair;
    public final BigDecimal lastPrice;
    public final BigDecimal lowestAsk;
    public final BigDecimal highestBid;
    public final BigDecimal percentChange;
    public final BigDecimal baseVolume;
    public final BigDecimal quoteVolume;
    public final Boolean isFrozen;
    public final BigDecimal twentyFourHourHigh;
    public final BigDecimal twentyFourHourLow;

    public PoloniexWSSTicker(Double currencyPair, BigDecimal lastPrice, BigDecimal lowestAsk, BigDecimal highestBid, BigDecimal percentChange, BigDecimal baseVolume, BigDecimal quoteVolume, Boolean isFrozen, BigDecimal twentyFourHourHigh, BigDecimal twentyFourHourLow) {
        this.currencyPair = currencyPair;
        this.lastPrice = lastPrice;
        this.lowestAsk = lowestAsk;
        this.highestBid = highestBid;
        this.percentChange = percentChange;
        this.baseVolume = baseVolume;
        this.quoteVolume = quoteVolume;
        this.isFrozen = isFrozen;
        this.twentyFourHourHigh = twentyFourHourHigh;
        this.twentyFourHourLow = twentyFourHourLow;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static class PoloniexWSSTickerBuilder {

        private Double currencyPair;
        private BigDecimal lastPrice;
        private BigDecimal lowestAsk;
        private BigDecimal highestBid;
        private BigDecimal percentChange;
        private BigDecimal baseVolume;
        private BigDecimal quoteVolume;
        private Boolean isFrozen;
        private BigDecimal twentyFourHourHigh;
        private BigDecimal twentyFourHourLow;

        public PoloniexWSSTickerBuilder() {
        }

        public PoloniexWSSTickerBuilder setCurrencyPair(Double currencyPair) {
            this.currencyPair = currencyPair;
            return this;
        }

        public PoloniexWSSTickerBuilder setLastPrice(BigDecimal lastPrice) {
            this.lastPrice = lastPrice;
            return this;
        }

        public PoloniexWSSTickerBuilder setLowestAsk(BigDecimal lowestAsk) {
            this.lowestAsk = lowestAsk;
            return this;
        }

        public PoloniexWSSTickerBuilder setHighestBid(BigDecimal highestBid) {
            this.highestBid = highestBid;
            return this;
        }

        public PoloniexWSSTickerBuilder setPercentChange(BigDecimal percentChange) {
            this.percentChange = percentChange;
            return this;
        }

        public PoloniexWSSTickerBuilder setBaseVolume(BigDecimal baseVolume) {
            this.baseVolume = baseVolume;
            return this;
        }

        public PoloniexWSSTickerBuilder setQuoteVolume(BigDecimal quoteVolume) {
            this.quoteVolume = quoteVolume;
            return this;
        }

        public PoloniexWSSTickerBuilder setIsFrozen(Boolean isFrozen) {
            this.isFrozen = isFrozen;
            return this;
        }

        public PoloniexWSSTickerBuilder setTwentyFourHourHigh(BigDecimal twentyFourHourHigh) {
            this.twentyFourHourHigh = twentyFourHourHigh;
            return this;
        }

        public PoloniexWSSTickerBuilder setTwentyFourHourLow(BigDecimal twentyFourHourLow) {
            this.twentyFourHourLow = twentyFourHourLow;
            return this;
        }

        public PoloniexWSSTicker buildPoloniexTicker() {
            return new PoloniexWSSTicker(currencyPair, lastPrice, lowestAsk, highestBid, percentChange, baseVolume, quoteVolume, isFrozen, twentyFourHourHigh, twentyFourHourLow);
        }
    }
}
