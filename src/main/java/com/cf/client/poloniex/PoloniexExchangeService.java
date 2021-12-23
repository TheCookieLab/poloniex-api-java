package com.cf.client.poloniex;

import com.cf.ExchangeService;
import com.cf.PriceDataAPIClient;
import com.cf.TradingAPIClient;
import com.cf.data.map.poloniex.PoloniexDataMapper;
import com.cf.data.model.poloniex.*;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author David
 */
public class PoloniexExchangeService implements ExchangeService {

    private final PriceDataAPIClient publicClient;
    private final TradingAPIClient tradingClient;
    private final PoloniexDataMapper mapper;

    private final static Logger LOG = LogManager.getLogger(PoloniexExchangeService.class);

    public PoloniexExchangeService(String apiKey, String apiSecret) {
        this.publicClient = new PoloniexPublicAPIClient();
        this.tradingClient = new PoloniexTradingAPIClient(apiKey, apiSecret);
        this.mapper = new PoloniexDataMapper();
    }

	public PoloniexExchangeService(String apiKey, String apiSecret, HttpHost httpHost) {
		this.publicClient = new PoloniexPublicAPIClient(httpHost);
		this.tradingClient = new PoloniexTradingAPIClient(apiKey, apiSecret, httpHost);
		this.mapper = new PoloniexDataMapper();
	}

    public PoloniexExchangeService(PriceDataAPIClient publicClient, TradingAPIClient tradingClient, PoloniexDataMapper mapper) {
        this.publicClient = publicClient;
        this.tradingClient = tradingClient;
        this.mapper = mapper;
    }

    /**
     * *
     * Returns candlestick chart data for the given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param periodInSeconds The candlestick chart data period. Valid values
     * are 300 (5 min), 900 (15 minutes), 7200 (2 hours), 14400 (4 hours), 86400
     * (daily)
     * @param startEpochInSeconds UNIX timestamp format and used to specify the
     * start date of the data returned
     * @return List of PoloniexChartData
     */
    @Override
    public List<PoloniexChartData> returnChartData(String currencyPair, Long periodInSeconds, Long startEpochInSeconds) {
        long start = System.currentTimeMillis();
        List<PoloniexChartData> chartData = new ArrayList<PoloniexChartData>();
        try {
            String chartDataResult = publicClient.getChartData(currencyPair, periodInSeconds, startEpochInSeconds);
            chartData = mapper.mapChartData(chartDataResult);
            LOG.debug("Retrieved and mapped {} chart data in {} ms", currencyPair, (System.currentTimeMillis() - start));
        } catch (Exception ex) {
            LOG.error("Error retrieving chart data for {} - {}", currencyPair, ex.getMessage());
        }

        return chartData;
    }

    /**
     * *
     * Returns the ticker for all currency pairs
     *
     * @return ticker data mapped to pair
     */
    @Override
    public Map<String, PoloniexTicker> returnTicker() {
        long start = System.currentTimeMillis();
        Map<String, PoloniexTicker> tickerResult = null;
        try {
            String tickerData = publicClient.returnTicker();
            tickerResult = mapper.mapTicker(tickerData);

            LOG.trace("Retrieved and mapped ticker in {} ms", System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error retrieving ticker - {}", ex.getMessage());
        }

        return tickerResult;
    }

    /**
     * *
     * Returns the ticker for a given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @return PoloniexTicker
     */
    @Override
    public PoloniexTicker returnTicker(String currencyPair) {
        long start = System.currentTimeMillis();
        PoloniexTicker tickerResult = null;
        try {
            String tickerData = publicClient.returnTicker();
            tickerResult = mapper.mapTickerForCurrency(currencyPair, tickerData);
            LOG.trace("Retrieved and mapped {} ticker in {} ms", currencyPair, System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error retrieving ticker for {} - {}", currencyPair, ex.getMessage());
        }

        return tickerResult;
    }

    @Override
    public List<String> returnAllMarkets() {
        long start = System.currentTimeMillis();
        List<String> allMarkets = new ArrayList<>();
        try {
            String tickerData = publicClient.returnTicker();
            allMarkets = mapper.mapMarkets(tickerData);
            LOG.trace("Retrieved and mapped market pairs in {} ms", System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error retrieving all markets - {}", ex.getMessage());
        }

        return allMarkets;
    }

    /**
     * *
     * Returns the complete balances inclusive non-zero balances or not
     * depending on parameter includeZeroBalances
     *
     * @param includeZeroBalances The includeZeroBalances
     * @return Map of String, PoloniexCompleteBalance
     */
    @Override
    public Map<String, PoloniexCompleteBalance> returnBalance(boolean includeZeroBalances) {
        long start = System.currentTimeMillis();
        Map<String, PoloniexCompleteBalance> balance = null;
        try {
            String completeBalancesResult = tradingClient.returnCompleteBalances();
            if (includeZeroBalances) {
                balance = mapper.mapCompleteBalanceResult(completeBalancesResult);
                LOG.trace("Retrieved and mapped complete balance in {} ms", System.currentTimeMillis() - start);
            } else {
                balance = mapper.mapCompleteBalanceResultForNonZeroCurrencies(completeBalancesResult);
                LOG.trace("Retrieved and mapped non-zero balances in {} ms", System.currentTimeMillis() - start);
            }
        } catch (Exception ex) {
            LOG.error("Error retrieving complete balance - {}", ex.getMessage());
        }

        return balance;
    }

    /**
     * *
     * Returns the balance for specified currency type
     *
     * @param currencyType Examples: BTC, ETH, DASH
     * @return PoloniexCompleteBalance
     */
    @Override
    public PoloniexCompleteBalance returnCurrencyBalance(String currencyType) {
        long start = System.currentTimeMillis();
        PoloniexCompleteBalance balance = null;
        try {
            String completeBalancesResult = tradingClient.returnCompleteBalances();
            balance = mapper.mapCompleteBalanceResultForCurrency(currencyType, completeBalancesResult);
            LOG.trace("Retrieved and mapped {} complete balance in {} ms", currencyType, System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error retrieving complete balance for {} - {}", currencyType, ex.getMessage());
        }

        return balance;
    }

    /**
     * *
     * If you are enrolled in the maker-taker fee schedule, returns your current
     * trading fees and trailing 30-day volume in BTC. This information is
     * updated once every 24 hours.
     *
     * @return PoloniexFeeInfo
     */
    @Override
    public PoloniexFeeInfo returnFeeInfo() {
        long start = System.currentTimeMillis();
        PoloniexFeeInfo feeInfo = null;
        try {
            String feeInfoResult = tradingClient.returnFeeInfo();
            feeInfo = mapper.mapFeeInfo(feeInfoResult);
            LOG.trace("Retrieved and mapped Poloniex fee info in {} ms", System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error retrieving fee info - {}", ex.getMessage());
        }

        return feeInfo;
    }

    /**
     * *
     * Returns the active loans from Poloniex
     *
     * @return PoloniexActiveLoanTypes
     */
    @Override
    public PoloniexActiveLoanTypes returnActiveLoans() {
        long start = System.currentTimeMillis();
        PoloniexActiveLoanTypes activeLoanTypes = null;
        try {
            String activeLoansResult = tradingClient.returnActiveLoans();
            activeLoanTypes = mapper.mapActiveLoans(activeLoansResult);
            LOG.trace("Retrieved and mapped Poloniex active loans in {} ms", System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error retrieving active loans - {}", ex.getMessage());
        }

        return activeLoanTypes;
    }

    /**
     * Returns order status  for a given orderNumber
     *
     * @param orderNumber
     * @return PoloniexOrderStatus
     */
    @Override
    public PoloniexOrderStatus returnOrderStatus(String orderNumber) {
        long start = System.currentTimeMillis();
        PoloniexOrderStatus orderStatus = null;
        PoloniexOrderStatusCheck orderStatusCheck;
        try {
            String orderStatusStr = tradingClient.returnOrderStatus(orderNumber);
            orderStatusCheck = mapper.mapOrderStatusCheck(orderStatusStr);
            if (orderStatusCheck.success == 1) {
                orderStatus = mapper.mapOrderStatus(orderStatusStr);
            } else {
                PoloniexOrderStatusError error = mapper.mapOrderStatusError(orderStatusStr);
                orderStatus = new PoloniexOrderStatus(0, error.result.get("error"), null);
            }
            LOG.trace("Retrieved and mapped {} {} order status in {} ms", orderStatusStr, orderNumber, System.currentTimeMillis() - start);
            return orderStatus;
        } catch (Exception ex) {
            LOG.error("Error retrieving order status for {} - {}", orderNumber, ex.getMessage());
        }

        return orderStatus;
    }

    /**
     * Places a withdraw order
     *
     * @param currency  Examples: USDT ETH
     * @param amount    the amount to withdraw
     * @param address   the address of currency
     * @param paymentId For XMR withdrawals, you may optionally specify "paymentId".
     * @return PoloniexWithdrawResult
     */
    @Override
    public PoloniexWithdrawResult withdraw(String currency, BigDecimal amount, String address, String paymentId) {
        long start = System.currentTimeMillis();
        PoloniexWithdrawResult withdrawResult = null;
        try {
            String withdrawResultStr = tradingClient.withdraw(currency, amount, address, paymentId);
            withdrawResult = mapper.mapWithdrawResult(withdrawResultStr);
            LOG.trace("Retrieved and mapped {} {} withdraw in {} ms", withdrawResultStr, currency, System.currentTimeMillis() - start);
            return withdrawResult;
        } catch (Exception ex) {
            LOG.error("Error retrieving withdraw for {} - {}", currency, ex.getMessage());
        }
        return withdrawResult;
    }

    /**
     * *
     * Returns your open orders for a given currency pair
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @return List of PoloniexOpenOrder
     */
    @Override
    public List<PoloniexOpenOrder> returnOpenOrders(String currencyPair) {
        long start = System.currentTimeMillis();
        List<PoloniexOpenOrder> openOrders = new ArrayList<PoloniexOpenOrder>();
        try {
            String openOrdersData = tradingClient.returnOpenOrders(currencyPair);
            openOrders = mapper.mapOpenOrders(openOrdersData);
            LOG.trace("Retrieved and mapped {} {} open orders in {} ms", openOrders.size(), currencyPair, System.currentTimeMillis() - start);
            return openOrders;
        } catch (Exception ex) {
            LOG.error("Error retrieving open orders for {} - {}", currencyPair, ex.getMessage());
        }

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
    public List<PoloniexTradeHistory> returnTradeHistory(String currencyPair) {
        long start = System.currentTimeMillis();
        List<PoloniexTradeHistory> tradeHistory = new ArrayList<PoloniexTradeHistory>();
        try {
            String tradeHistoryData = tradingClient.returnTradeHistory(currencyPair);
            tradeHistory = mapper.mapTradeHistory(tradeHistoryData);
            LOG.trace("Retrieved and mapped {} {} trade history in {} ms", tradeHistory.size(), currencyPair, System.currentTimeMillis() - start);
            return tradeHistory;
        } catch (Exception ex) {
            LOG.error("Error retrieving trade history for {} - {}", currencyPair, ex.getMessage());
        }

        return tradeHistory;
    }

    @Override
    public List<PoloniexOrderTrade> returnOrderTrades(String orderNumber) {
        long start = System.currentTimeMillis();
        List<PoloniexOrderTrade> orderTrades = new ArrayList<>();

        try {
            String orderTradesResult = tradingClient.returnOrderTrades(orderNumber);
            orderTrades = mapper.mapOrderTrades(orderTradesResult);
            LOG.trace("Executed and mapped return order trades for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error executing return order trades for {} - {}", orderNumber, ex.getMessage());
        }

        return orderTrades;
    }

    /**
     * *
     * Places a sell order in a given market
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param sellPrice the sell price
     * @param amount the amount to sell
     * @param fillOrKill Will either fill in its entirety or be completely
     * aborted
     * @param immediateOrCancel Order can be partially or completely filled, but
     * any portion of the order that cannot be filled immediately will be
     * canceled rather than left on the order book
     * @param postOnly A post-only order will only be placed if no portion of it
     * fills immediately; this guarantees you will never pay the taker fee on
     * any part of the order that fills
     * @return PoloniexOrderResult
     */
    @Override
    public PoloniexOrderResult sell(String currencyPair, BigDecimal sellPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly) {
        long start = System.currentTimeMillis();
        PoloniexOrderResult orderResult = null;
        try {
            String sellTradeResult = tradingClient.sell(currencyPair, sellPrice, amount, fillOrKill, immediateOrCancel, postOnly);
            orderResult = mapper.mapTradeOrder(sellTradeResult);
            LOG.trace("Executed and mapped {} sell order {} in {} ms", currencyPair, sellTradeResult, System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error executing sell order for {} - {}", currencyPair, ex.getMessage());
        }

        return orderResult;
    }

    /**
     * *
     * Places a buy order in a given market
     *
     * @param currencyPair Examples: USDT_ETH, USDT_BTC, BTC_ETH
     * @param buyPrice the buy price
     * @param amount the amount to buy
     * @param fillOrKill Will either fill in its entirety or be completely
     * aborted
     * @param immediateOrCancel Order can be partially or completely filled, but
     * any portion of the order that cannot be filled immediately will be
     * canceled rather than left on the order book
     * @param postOnly A post-only order will only be placed if no portion of it
     * fills immediately; this guarantees you will never pay the taker fee on
     * any part of the order that fills
     * @return PoloniexOrderResult
     */
    @Override
    public PoloniexOrderResult buy(String currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly) {
        long start = System.currentTimeMillis();
        PoloniexOrderResult orderResult = null;
        try {
            String buyTradeResult = tradingClient.buy(currencyPair, buyPrice, amount, fillOrKill, immediateOrCancel, postOnly);
            orderResult = mapper.mapTradeOrder(buyTradeResult);
            LOG.trace("Executed and mapped {} buy order {} in {} ms", currencyPair, buyTradeResult, System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOG.error("Error executing buy order for {} - {}", currencyPair, ex.getMessage());
        }

        return orderResult;
    }

    /**
     * *
     * Cancels an order you have placed in a given market
     *
     * @param orderNumber order identifier on the exchange
     * @return true if successful, false otherwise
     */
    @Override
    public boolean cancelOrder(String orderNumber) {
        long start = System.currentTimeMillis();
        boolean success = false;
        try {
            String cancelOrderResult = tradingClient.cancelOrder(orderNumber);
            success = mapper.mapCancelOrder(cancelOrderResult);
            LOG.trace("Executed and mapped cancel order for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
            return success;
        } catch (Exception ex) {
            LOG.error("Error executing cancel order for {} - {}", orderNumber, ex.getMessage());
        }

        return success;
    }

    @Override
    public PoloniexOrderResult moveOrder(String orderNumber, BigDecimal rate, Boolean immediateOrCancel, Boolean postOnly) {
        long start = System.currentTimeMillis();
        PoloniexOrderResult orderResult = null;
        try {
            String moveOrderResult = tradingClient.moveOrder(orderNumber, rate);
            orderResult = mapper.mapTradeOrder(moveOrderResult);
            LogManager.getLogger(PoloniexExchangeService.class).trace("Executed and mapped move order for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LogManager.getLogger(PoloniexExchangeService.class).error("Error executing move order for {} - {}", orderNumber, ex.getMessage());
        }

        return orderResult;
    }
}
