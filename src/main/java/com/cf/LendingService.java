package com.cf;

import java.math.BigDecimal;
import java.util.List;

import com.cf.data.model.poloniex.PoloniexActiveLoanTypes;
import com.cf.data.model.poloniex.PoloniexLendingHistory;
import com.cf.data.model.poloniex.PoloniexLendingResult;
import com.cf.data.model.poloniex.PoloniexLoanOffer;

/**
 *
 * @author cheolhee
 */
public interface LendingService
{
    public PoloniexActiveLoanTypes returnActiveLoans();

    public List<PoloniexLendingHistory> returnLendingHistory(int hours, int limit);

    public PoloniexLendingResult createLoanOffer(String currency, BigDecimal amount, BigDecimal lendingRate, int duration, boolean autoRenew);

    public PoloniexLendingResult cancelLoanOffer(String orderNumber);

    public List<PoloniexLoanOffer> returnOpenLoanOffers(String currency);

    public PoloniexLendingResult toggleAutoRenew(String orderNumber);

}
