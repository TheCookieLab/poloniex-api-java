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

    public PoloniexOrderResult(Long orderNumber, String error, List<PoloniexTradeHistory> resultingTrades)
    {
        this.orderNumber = orderNumber;
        this.error = error;
        this.resultingTrades = resultingTrades;
    }
}
