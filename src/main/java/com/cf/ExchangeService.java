package com.cf;

import com.cf.data.model.poloniex.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author David
 */
public interface ExchangeService {

    public final static String USDT_BTC_CURRENCY_PAIR = "USDT_BTC";
    public final static String USDT_ETH_CURRENCY_PAIR = "USDT_ETH";
    public final static String BTC_CURRENCY_TYPE = "BTC";
    public final static String ETH_CURRENCY_TYPE = "ETH";
    public final static Long FIVE_MINUTES_TIME_PERIOD = 300L;
    public final static Long FIFTEEN_MINUTES_TIME_PERIOD = 900L;
    public final static Long FOUR_HOUR_TIME_PERIOD = 14_400L;
    public final static Long TWO_HOUR_TIME_PERIOD = 7_200L;
    public final static Long DAILY_TIME_PERIOD = 86_400L;
    public final static Long LONG_LONG_AGO = 1_439_000_000L;

    public List<PoloniexChartData> returnChartData(String currencyPair, Long periodInSeconds, Long startEpochInSeconds);

    public Map<String, PoloniexTicker> returnTicker();

    public PoloniexTicker returnTicker(String currencyName);

    public List<String> returnAllMarkets();

    public Map<String, PoloniexCompleteBalance> returnBalance(boolean includeZeroBalances);

    public PoloniexCompleteBalance returnCurrencyBalance(String currencyName);

    public PoloniexFeeInfo returnFeeInfo();

    public List<PoloniexOpenOrder> returnOpenOrders(String currencyName);

    public List<PoloniexTradeHistory> returnTradeHistory(String currencyPair);

    public List<PoloniexOrderTrade> returnOrderTrades(String orderNumber);

    public boolean cancelOrder(String orderNumber);

    public PoloniexOrderResult moveOrder(String orderNumber, BigDecimal rate, Boolean immediateOrCancel, Boolean postOnly);

    public PoloniexOrderResult sell(String currencyPair, BigDecimal sellPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

    public PoloniexOrderResult buy(String currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

    public PoloniexActiveLoanTypes returnActiveLoans();

	public PoloniexOrderStatus returnOrderStatus(String orderNumber);

	public PoloniexWithdrawResult withdraw(String currency, BigDecimal amount, String address, String paymentId);

}
