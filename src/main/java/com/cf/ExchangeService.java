package com.cf;


import java.math.BigDecimal;
import java.util.List;

import com.cf.client.poloniex.enums.CurrencyEnum;
import com.cf.client.poloniex.enums.CurrencyPairEnum;
import com.cf.data.model.poloniex.PoloniexActiveLoanTypes;
import com.cf.data.model.poloniex.PoloniexChartData;
import com.cf.data.model.poloniex.PoloniexCompleteBalance;
import com.cf.data.model.poloniex.PoloniexFeeInfo;
import com.cf.data.model.poloniex.PoloniexOpenOrder;
import com.cf.data.model.poloniex.PoloniexOrderResult;
import com.cf.data.model.poloniex.PoloniexTicker;
import com.cf.data.model.poloniex.PoloniexTradeHistory;

/**
 *
 * @author David
 */
public interface ExchangeService
{
    public final static Long FIVE_MINUTES_TIME_PERIOD = 300L;
    public final static Long FIFTEEN_MINUTES_TIME_PERIOD = 900L;
    public final static Long FOUR_HOUR_TIME_PERIOD = 14_400L;
    public final static Long TWO_HOUR_TIME_PERIOD = 7_200L;
    public final static Long DAILY_TIME_PERIOD = 86_400L;
    public final static Long LONG_LONG_AGO = 1_439_000_000L;
    
    public List<PoloniexChartData> returnChartData(CurrencyPairEnum currencyPair, Long periodInSeconds, Long startEpochInSeconds);
    
    public PoloniexTicker returnTicker(CurrencyPairEnum currencyPair);
    
    public List<String> returnAllMarkets();

    public PoloniexCompleteBalance returnBalance(CurrencyEnum currencyName);

    public PoloniexFeeInfo returnFeeInfo();
    
    public List<PoloniexOpenOrder> returnOpenOrders(CurrencyPairEnum currencyPair);
    
    public List<PoloniexTradeHistory> returnTradeHistory(CurrencyPairEnum currencyPair);
    
    public boolean cancelOrder(String orderNumber);
    
    public PoloniexOrderResult moveOrder(String orderNumber, BigDecimal rate, Boolean immediateOrCancel, Boolean postOnly);
    
    public PoloniexOrderResult sell(CurrencyPairEnum currencyPair, BigDecimal sellPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

    public PoloniexOrderResult buy(CurrencyPairEnum currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

	public PoloniexActiveLoanTypes returnActiveLoans();

}
