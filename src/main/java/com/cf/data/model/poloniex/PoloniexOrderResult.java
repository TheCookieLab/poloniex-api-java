package com.cf.data.model.poloniex;

import java.util.List;

/**
 *
 * @author David
 */
public class PoloniexOrderResult
{
    public final Long orderNumber;
    public final String error;
    public final List<PoloniexTradeHistory> resultingTrades;

    public PoloniexOrderResult(Long orderNumber, List<PoloniexTradeHistory> resultingTrades, String error)
    {
        this.orderNumber = orderNumber;
        this.resultingTrades = resultingTrades;
        this.error = error;
    }
}
