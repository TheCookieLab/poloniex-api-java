package com.cf;


import com.cf.data.model.poloniex.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cheolhee
 */
public interface LendingService
{
    public final static String BTC_CURRENCY_TYPE = "BTC";
    public final static String PROVIDED_LOAD_TYPE = "provided";
    public final static String USED_LOAD_TYPE = "used";

    public Map returnActiveLoans();

    public List<PoloniexLendingHistory> returnLendingHistory(int hours, int limit);

    public PoloniexLendingResult createLoanOffer(String currency, BigDecimal amount, BigDecimal lendingRate, int duration, boolean autoRenew);

    public PoloniexLendingResult cancelLoanOffer(String orderNumber);

    public List<PoloniexLoanOffer> returnOpenLoanOffers(String currency);

    public PoloniexLendingResult toggleAutoRenew(String orderNumber);

}
