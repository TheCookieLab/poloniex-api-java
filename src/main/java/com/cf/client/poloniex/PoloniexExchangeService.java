package com.cf.client.poloniex;

import com.cf.ExchangeService;
import com.cf.PriceDataAPIClient;
import com.cf.TradingAPIClient;
import com.cf.data.map.poloniex.PoloniexDataMapper;
import com.cf.data.model.poloniex.PoloniexChartData;
import com.cf.data.model.poloniex.PoloniexCompleteBalance;
import com.cf.data.model.poloniex.PoloniexFeeInfo;
import com.cf.data.model.poloniex.PoloniexOpenOrder;
import com.cf.data.model.poloniex.PoloniexOrderResult;
import com.cf.data.model.poloniex.PoloniexTicker;
import com.cf.data.model.poloniex.PoloniexTradeHistory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author David
 */
public class PoloniexExchangeService implements ExchangeService
{
    private final PriceDataAPIClient publicClient;
    private final TradingAPIClient tradingClient;
    private final PoloniexDataMapper mapper;

    private final static Logger LOG = LogManager.getLogger(PoloniexExchangeService.class);

    public PoloniexExchangeService(String apiKey, String apiSecret)
    {
        this.publicClient = new PoloniexPublicAPIClient();
        this.tradingClient = new PoloniexTradingAPIClient(apiKey, apiSecret);
        this.mapper = new PoloniexDataMapper();
    }

    public PoloniexExchangeService(PriceDataAPIClient publicClient, TradingAPIClient tradingClient, PoloniexDataMapper mapper)
    {
        this.publicClient = publicClient;
        this.tradingClient = tradingClient;
        this.mapper = mapper;
    }

    /**
     * *
     * Returns candlestick chart data for the given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param periodInSeconds The candlestick chart data period. Valid values are 300 (5 min), 900 (15 minutes), 7200 (2 hours), 14400 (4 hours), 86400 (daily)
     * @param startEpochInSeconds UNIX timestamp format and used to specify the start date of the data returned
     * @return List of PoloniexChartData
     */
    @Override
    public List<PoloniexChartData> returnChartData(String currencyPair, Long periodInSeconds, Long startEpochInSeconds)
    {
        long start = System.currentTimeMillis();
        String chartDataResult = publicClient.getChartData(currencyPair, periodInSeconds, startEpochInSeconds);
        List<PoloniexChartData> chartData = mapper.mapChartData(chartDataResult);
        LOG.debug("Retrieved and mapped " + currencyPair + " chart data in " + (System.currentTimeMillis() - start) + " ms");
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
        try
        {
            String tickerData = publicClient.returnTicker();
            PoloniexTicker tickerResult = mapper.mapTickerForCurrency(currencyPair, tickerData);
            LOG.trace("Retrieved and mapped {} ticker in {} ms", currencyPair, System.currentTimeMillis() - start);
            return tickerResult;
        }
        catch (Exception ex)
        {
            LOG.error("Error retrieving ticker for {} - {}", currencyPair, ex.getMessage());
            return null;
        }
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
        try
        {
            String completeBalancesResult = tradingClient.returnCompleteBalances();
            PoloniexCompleteBalance balance = mapper.mapCompleteBalanceResultForCurrency(currencyType, completeBalancesResult);
            LOG.trace("Retrieved and mapped {} complete balance in {} ms", currencyType, System.currentTimeMillis() - start);
            return balance;
        }
        catch (Exception ex)
        {
            LOG.error("Error retrieving complete balance for {} - {}", currencyType, ex.getMessage());
            return null;
        }
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
        try
        {
            String feeInfoResult = tradingClient.returnFeeInfo();
            PoloniexFeeInfo feeInfo = mapper.mapFeeInfo(feeInfoResult);
            LOG.trace("Retrieved and mapped Poloniex fee info in {} ms", System.currentTimeMillis() - start);
            return feeInfo;
        }
        catch (Exception ex)
        {
            LOG.error("Error retrieving fee info - {}", ex.getMessage());
            return null;
        }
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
        try
        {
            String openOrdersData = tradingClient.returnOpenOrders(currencyPair);
            List<PoloniexOpenOrder> openOrders = mapper.mapOpenOrders(currencyPair, openOrdersData);
            LOG.trace("Retrieved and mapped {} {} open orders in {} ms", openOrders.size(), currencyPair, System.currentTimeMillis() - start);
            return openOrders;
        }
        catch (Exception ex)
        {
            LOG.error("Error retrieving open orders for {} - {}", currencyPair, ex.getMessage());
            return new ArrayList<>();
        }
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
        try
        {
            String tradeHistoryData = tradingClient.returnTradeHistory(currencyPair);
            List<PoloniexTradeHistory> tradeHistory = mapper.mapTradeHistory(tradeHistoryData);
            LOG.trace("Retrieved and mapped {} {} trade history in {} ms", tradeHistory.size(), currencyPair, System.currentTimeMillis() - start);
            return tradeHistory;
        }
        catch (Exception ex)
        {
            LOG.error("Error retrieving trade history for {} - {}", currencyPair, ex.getMessage());
            return new ArrayList<>();
        }
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
        try
        {
            String sellTradeResult = tradingClient.sell(currencyPair, sellPrice, amount, fillOrKill, immediateOrCancel, postOnly);
            PoloniexOrderResult orderResult = mapper.mapTradeOrder(sellTradeResult);
            LOG.trace("Executed and mapped {} sell order {} in {} ms", currencyPair, sellTradeResult, System.currentTimeMillis() - start);
            return orderResult;
        }
        catch (Exception ex)
        {
            LOG.error("Error executing sell order for {} - {}", currencyPair, ex.getMessage());
            return null;
        }
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
        try
        {
            String buyTradeResult = tradingClient.buy(currencyPair, buyPrice, amount, fillOrKill, immediateOrCancel, postOnly);
            PoloniexOrderResult orderResult = mapper.mapTradeOrder(buyTradeResult);
            LOG.trace("Executed and mapped {} buy order {} in {} ms", currencyPair, buyTradeResult, System.currentTimeMillis() - start);
            return orderResult;
        }
        catch (Exception ex)
        {
            LOG.error("Error executing buy order for {} - {}", currencyPair, ex.getMessage());
            return null;
        }
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
        try
        {
            String cancelOrderResult = tradingClient.cancelOrder(orderNumber);
            boolean success = mapper.mapCancelOrder(cancelOrderResult);
            LOG.trace("Executed and mapped cancel order for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
            return success;
        }
        catch (Exception ex)
        {
            LOG.error("Error executing cancel order for {} - {}", orderNumber, ex.getMessage());
            return false;
        }
    }

    @Override
    public PoloniexOrderResult moveOrder(String orderNumber, BigDecimal rate, Boolean immediateOrCancel, Boolean postOnly)
    {
        long start = System.currentTimeMillis();
        try
        {
            String moveOrderResult = tradingClient.moveOrder(orderNumber, rate);
            PoloniexOrderResult orderResult = mapper.mapTradeOrder(moveOrderResult);
            LogManager.getLogger(PoloniexExchangeService.class).trace("Executed and mapped move order for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
            return orderResult;
        }
        catch (Exception ex)
        {
            LogManager.getLogger(PoloniexExchangeService.class).error("Error executing move order for {} - {}", orderNumber, ex.getMessage());
            return null;
        }
    }

}
