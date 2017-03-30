package com.cf.client.poloniex;

import com.cf.ExchangeService;
import com.cf.data.map.poloniex.PoloniexDataMapper;
import com.cf.data.model.poloniex.PoloniexChartData;
import com.cf.data.model.poloniex.PoloniexCompleteBalance;
import com.cf.data.model.poloniex.PoloniexFeeInfo;
import com.cf.data.model.poloniex.PoloniexOpenOrder;
import com.cf.data.model.poloniex.PoloniexOrderResult;
import com.cf.data.model.poloniex.PoloniexTicker;
import com.cf.data.model.poloniex.PoloniexTradeHistory;
import java.math.BigDecimal;
import java.util.List;
import org.apache.log4j.LogManager;

/**
 *
 * @author David
 */
public class PoloniexExchangeService implements ExchangeService
{
    private final PoloniexPublicAPIClient publicClient;
    private final PoloniexTradingAPIClient tradingClient;
    private final PoloniexDataMapper mapper;

    public PoloniexExchangeService(String apiKey, String apiSecret)
    {
        this.publicClient = new PoloniexPublicAPIClient();
        this.tradingClient = new PoloniexTradingAPIClient(apiKey, apiSecret);
        this.mapper = new PoloniexDataMapper();
    }

    public PoloniexExchangeService(PoloniexPublicAPIClient publicClient, PoloniexTradingAPIClient tradingClient, PoloniexDataMapper mapper)
    {
        this.publicClient = publicClient;
        this.tradingClient = tradingClient;
        this.mapper = mapper;
    }

    /**
     * *
     * Returns candlestick chart data for USDT_BTC pair
     *
     * @param periodInSeconds The candlestick chart data period. Valid values are 300 (5 min), 900 (15 minutes), 7200 (2 hours), 14400 (4 hours), 86400 (daily)
     * @param startEpochInSeconds UNIX timestamp format and used to specify the start date of the data returned
     * @return List of PoloniexChartData
     */
    @Override
    public List<PoloniexChartData> returnBTCChartData(Long periodInSeconds, Long startEpochInSeconds)
    {
        long start = System.currentTimeMillis();
        String chartDataResult = publicClient.getChartData(USDT_BTC_CURRENCY_PAIR, periodInSeconds, startEpochInSeconds);
        List<PoloniexChartData> chartData = mapper.mapChartData(chartDataResult);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped " + USDT_BTC_CURRENCY_PAIR + " chart data in " + (System.currentTimeMillis() - start) + " ms");
        return chartData;
    }

    /**
     * *
     * Returns candlestick chart data for USDT_ETH pair
     *
     * @param periodInSeconds The candlestick chart data period. Valid values are 300 (5 min), 900 (15 minutes), 7200 (2 hours), 14400 (4 hours), 86400 (daily)
     * @param startEpochInSeconds UNIX timestamp format and used to specify the start date of the data returned
     * @return List of PoloniexChartData
     */
    @Override
    public List<PoloniexChartData> returnETHChartData(Long periodInSeconds, Long startEpochInSeconds)
    {
        long start = System.currentTimeMillis();
        String chartDataResult = publicClient.getChartData(USDT_ETH_CURRENCY_PAIR, periodInSeconds, startEpochInSeconds);
        List<PoloniexChartData> chartData = mapper.mapChartData(chartDataResult);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped " + USDT_ETH_CURRENCY_PAIR + " chart data in " + (System.currentTimeMillis() - start) + " ms");
        return chartData;
    }

    /**
     * *
     * Returns the ticker for all a given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @return PoloniexTicker
     */
    @Override
    public PoloniexTicker returnTicker(String currencyPair)
    {
        long start = System.currentTimeMillis();
        String tickerData = publicClient.returnTicker();
        PoloniexTicker tickerResult = mapper.mapTickerForCurrency(currencyPair, tickerData);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped " + currencyPair + " ticker in " + (System.currentTimeMillis() - start) + " ms");
        return tickerResult;
    }

    /**
     * *
     * Returns the balance for specified currency type
     *
     * @param currencyType Examples: BTC, ETH, DASH
     * @return PoloniexCompleteBalance
     */
    @Override
    public PoloniexCompleteBalance returnBalance(String currencyType)
    {
        long start = System.currentTimeMillis();
        String completeBalancesResult = tradingClient.returnCompleteBalances();
        PoloniexCompleteBalance balance = mapper.mapCompleteBalanceResultForCurrency(currencyType, completeBalancesResult);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped " + currencyType + " complete balance in " + (System.currentTimeMillis() - start) + " ms");
        return balance;
    }

    /**
     * *
     * If you are enrolled in the maker-taker fee schedule, returns your current trading fees and trailing 30-day volume in BTC. This information is updated once every 24 hours.
     *
     * @return PoloniexFeeInfo
     */
    @Override
    public PoloniexFeeInfo returnFeeInfo()
    {
        long start = System.currentTimeMillis();
        String feeInfoResult = tradingClient.returnFeeInfo();
        PoloniexFeeInfo feeInfo = mapper.mapFeeInfo(feeInfoResult);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped Poloniex fee info in " + (System.currentTimeMillis() - start) + " ms");
        return feeInfo;
    }

    /**
     * *
     * Returns your open orders for a given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @return List of PoloniexOpenOrder
     */
    @Override
    public List<PoloniexOpenOrder> returnOpenOrders(String currencyPair)
    {
        long start = System.currentTimeMillis();
        String openOrdersData = tradingClient.returnOpenOrders();
        List<PoloniexOpenOrder> openOrders = mapper.mapOpenOrders(currencyPair, openOrdersData);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped " + currencyPair + " open orders in " + (System.currentTimeMillis() - start) + " ms");
        return openOrders;
    }

    /**
     * *
     * Returns up to 50,000 trades for given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @return List of PoloniexTradeHistory
     */
    @Override
    public List<PoloniexTradeHistory> returnTradeHistory(String currencyPair)
    {
        long start = System.currentTimeMillis();
        String tradeHistoryData = tradingClient.returnTradeHistory(currencyPair);
        List<PoloniexTradeHistory> tradeHistory = mapper.mapTradeHistory(tradeHistoryData);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped " + currencyPair + " trade history in " + (System.currentTimeMillis() - start) + " ms");
        return tradeHistory;
    }

    /**
     * *
     * Places a sell order in a given market
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param sellPrice
     * @param amount
     * @param fillOrKill Will either fill in its entirety or be completely aborted
     * @param immediateOrCancel Order can be partially or completely filled, but any portion of the order that cannot be filled immediately will be canceled rather than left on the order book
     * @param postOnly A post-only order will only be placed if no portion of it fills immediately; this guarantees you will never pay the taker fee on any part of the order that fills
     * @return PoloniexOrderResult
     */
    @Override
    public PoloniexOrderResult sell(String currencyPair, BigDecimal sellPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly)
    {
        long start = System.currentTimeMillis();
        String sellTradeResult = tradingClient.sell(currencyPair, sellPrice, amount, fillOrKill, immediateOrCancel, postOnly);
        PoloniexOrderResult orderResult = mapper.mapTradeOrder(sellTradeResult);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped " + currencyPair + " sell order in " + (System.currentTimeMillis() - start) + " ms");
        return orderResult;
    }

    /**
     * *
     * Places a buy order in a given market
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param buyPrice
     * @param amount
     * @param fillOrKill Will either fill in its entirety or be completely aborted
     * @param immediateOrCancel Order can be partially or completely filled, but any portion of the order that cannot be filled immediately will be canceled rather than left on the order book
     * @param postOnly A post-only order will only be placed if no portion of it fills immediately; this guarantees you will never pay the taker fee on any part of the order that fills
     * @return PoloniexOrderResult
     */
    @Override
    public PoloniexOrderResult buy(String currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly)
    {
        long start = System.currentTimeMillis();
        String buyTradeResult = tradingClient.buy(currencyPair, buyPrice, amount, fillOrKill, immediateOrCancel, postOnly);
        PoloniexOrderResult orderResult = mapper.mapTradeOrder(buyTradeResult);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped " + currencyPair + " buy order in " + (System.currentTimeMillis() - start) + " ms");
        return orderResult;
    }

    /**
     * *
     * Cancels an order you have placed in a given market
     *
     * @param orderNumber
     * @return true if successful, false otherwise
     */
    @Override
    public boolean cancelOrder(String orderNumber)
    {
        long start = System.currentTimeMillis();
        String cancelOrderResult = tradingClient.cancelOrder(orderNumber);
        boolean success = mapper.mapCancelOrder(cancelOrderResult);
        LogManager.getLogger(PoloniexExchangeService.class).debug("Retrieved and mapped cancel order for " + orderNumber + " in " + (System.currentTimeMillis() - start) + " ms");
        return success;
    }

}
