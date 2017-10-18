package com.cf;

import java.math.BigDecimal;

import com.cf.client.poloniex.enums.CurrencyPairEnum;

/**
 *
 * @author David
 */
public interface TradingAPIClient
{
    public String returnBalances();

    public String returnCompleteBalances();

    public String returnFeeInfo();

    public String returnOpenOrders(CurrencyPairEnum currencyPair);

    public String returnTradeHistory(CurrencyPairEnum currencyPair);

    public String cancelOrder(String orderNumber);

    public String moveOrder(String orderNumber, BigDecimal rate);

    public String sell(CurrencyPairEnum currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

    public String buy(CurrencyPairEnum currencyPair, BigDecimal buyPrice, BigDecimal amount, boolean fillOrKill, boolean immediateOrCancel, boolean postOnly);

    // Lending APIs
    public String returnActiveLoans();

    public String returnLendingHistory(int hours, int limit);

    public String createLoanOffer(String currency, BigDecimal amount, BigDecimal lendingRate, int duration, boolean autoRenew);

    public String cancelLoanOffer(String orderNumber);

    public String returnOpenLoanOffers();

    public String toggleAutoRenew(String orderNumber);

}
