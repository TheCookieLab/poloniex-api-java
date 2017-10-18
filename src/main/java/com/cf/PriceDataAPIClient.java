package com.cf;

import com.cf.client.poloniex.enums.CurrencyPairEnum;

/**
 *
 * @author David
 */
public interface PriceDataAPIClient
{
    public String returnTicker();

    public String getUSDBTCChartData(Long periodInSeconds, Long startEpochInSeconds);

    public String getUSDETHChartData(Long periodInSeconds, Long startEpochInSeconds);

    public String getChartData(CurrencyPairEnum currencyPair, Long periodInSeconds, Long startEpochSeconds);

    public String getChartData(CurrencyPairEnum currencyPair, Long periodInSeconds, Long startEpochSeconds, Long endEpochSeconds);
}
