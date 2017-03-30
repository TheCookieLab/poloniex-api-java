package com.cf;

/**
 *
 * @author David
 */
public interface PriceDataAPIClient
{
    public String returnTicker();

    public String getUSDBTCChartData(Long periodInSeconds, Long startEpochInSeconds);

    public String getUSDETHChartData(Long periodInSeconds, Long startEpochInSeconds);

    public String getChartData(String currencyPair, Long periodInSeconds, Long startEpochSeconds);

    public String getChartData(String currencyPair, Long periodInSeconds, Long startEpochSeconds, Long endEpochSeconds);
}
