package com.cf.data.model.poloniex;

import java.util.List;

/**
 *
 * @author David
 */
public class PoloniexOrderResult
{
    public final Long orderNumber;
    public final List<PoloniexTradeHistory> resultingTrades;

    public PoloniexOrderResult(Long orderNumber, List<PoloniexTradeHistory> resultingTrades)
    {
        this.orderNumber = orderNumber;
        this.resultingTrades = resultingTrades;
    }
}
