package com.cf.client.poloniex;

import com.cf.LendingService;
import com.cf.TradingAPIClient;
import com.cf.data.map.poloniex.PoloniexDataMapper;
import com.cf.data.model.poloniex.PoloniexActiveLoanTypes;
import com.cf.data.model.poloniex.PoloniexLendingHistory;
import com.cf.data.model.poloniex.PoloniexLendingResult;
import com.cf.data.model.poloniex.PoloniexLoanOffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author cheolhee
 */
public class PoloniexLendingService implements LendingService
{
    private final TradingAPIClient tradingClient;
    private final PoloniexDataMapper mapper;

    private final static Logger LOG = LogManager.getLogger(PoloniexLendingService.class);

    public PoloniexLendingService(String apiKey, String apiSecret)
    {
        this.tradingClient = new PoloniexTradingAPIClient(apiKey, apiSecret);
        this.mapper = new PoloniexDataMapper();
    }

    public PoloniexLendingService(TradingAPIClient tradingClient, PoloniexDataMapper mapper)
    {
        this.tradingClient = tradingClient;
        this.mapper = mapper;
    }

    /**
     * Returns lending history
     *
     * @param hours time range
     * @return limit number of rows returned
     */
    @Override
    public List<PoloniexLendingHistory> returnLendingHistory(int hours, int limit)
    {
        long start = System.currentTimeMillis();
        List<PoloniexLendingHistory> lendingHistory = new ArrayList<>();
        try
        {
            String lendingHistoryData = tradingClient.returnLendingHistory(hours, limit);
            lendingHistory = mapper.mapLendingHistory(lendingHistoryData);
            LOG.trace("Retrieved and mapped {} {} {} lendingHistory in {} ms", lendingHistory.size(), hours, limit, System.currentTimeMillis() - start);
            return lendingHistory;
        }
        catch (Exception ex)
        {
            LOG.error("Error retrieving lendingHistory for {} {} - {}", hours, limit, ex.getMessage());
        }

        return lendingHistory;
    }

    /**
     * @param currency
     * @param amount
     * @param lendingRate
     * @param duration
     * @param autoRenew
     * @return
     */
    @Override
    public PoloniexLendingResult createLoanOffer(String currency, BigDecimal amount, BigDecimal lendingRate, int duration, boolean autoRenew)
    {
        long start = System.currentTimeMillis();
        PoloniexLendingResult result = null;
        try
        {
            String res = tradingClient.createLoanOffer(currency, amount, lendingRate, duration, autoRenew);
            result = mapper.mapLendingResult(res);
            LogManager.getLogger(PoloniexLendingService.class).trace("Executed and mapped createLoanOffer for {} {} {} {} {} in {} ms",
                    currency, amount.toPlainString(), lendingRate.toPlainString(), duration, autoRenew ? 1 : 0,
                    System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
            LogManager.getLogger(PoloniexLendingService.class).error("Error executing createLoanOffer for {} {} {} {} {} - {}",
                    currency, amount.toPlainString(), lendingRate.toPlainString(), duration, autoRenew ? 1 : 0,
                    ex.getMessage());
        }

        return result;
    }

    /**
     * @param orderNumber
     * @return
     */
    @Override
    public PoloniexLendingResult cancelLoanOffer(String orderNumber)
    {
        long start = System.currentTimeMillis();
        PoloniexLendingResult result = null;
        try
        {
            String res = tradingClient.cancelLoanOffer(orderNumber);
            result = mapper.mapLendingResult(res);
            LogManager.getLogger(PoloniexLendingService.class).trace("Executed and mapped cancelLoanOffer for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
            LogManager.getLogger(PoloniexLendingService.class).error("Error executing cancelLoanOffer for {} - {}", orderNumber, ex.getMessage());
        }

        return result;
    }

    /**
     * @return
     */
    @Override
    public PoloniexActiveLoanTypes returnActiveLoans()
    {
        long start = System.currentTimeMillis();
        PoloniexActiveLoanTypes activeLoanTypes = null;

        try
        {
            String res = tradingClient.returnActiveLoans();
            activeLoanTypes = mapper.mapActiveLoans(res);
            LOG.trace("Retrieved ActiveLoans in {} ms", System.currentTimeMillis() - start);
            return activeLoanTypes;
        }
        catch (Exception ex)
        {
            LOG.error("Error retrieving ActiveLoans - {}", ex.getMessage());
        }

        return activeLoanTypes;
    }

    @Override
    public List<PoloniexLoanOffer> returnOpenLoanOffers(String currency)
    {
        long start = System.currentTimeMillis();
        List<PoloniexLoanOffer> offers = Collections.EMPTY_LIST;
        try
        {
            String res = tradingClient.returnOpenLoanOffers();
            offers = mapper.mapOpenLoanOffers(currency, res);
            LOG.trace("Retrieved and mapped {} {} OpenLoanOffers in {} ms", currency, offers.size(), System.currentTimeMillis() - start);
            return offers;
        }
        catch (Exception ex)
        {
            LOG.error("Retrieved and mapped {} {} OpenLoanOffers - {}", currency, offers.size(), ex.getMessage());
        }

        return offers;
    }

    @Override
    public PoloniexLendingResult toggleAutoRenew(String orderNumber)
    {
        long start = System.currentTimeMillis();
        PoloniexLendingResult result = null;
        try
        {
            String res = tradingClient.toggleAutoRenew(orderNumber);
            result = mapper.mapLendingResult(res);
            LogManager.getLogger(PoloniexLendingService.class).trace("Executed and mapped toggleAutoRenew for {} in {} ms", orderNumber, System.currentTimeMillis() - start);
        }
        catch (Exception ex)
        {
            LogManager.getLogger(PoloniexLendingService.class).error("Error executing toggleAutoRenew for {} - {}", orderNumber, ex.getMessage());
        }

        return result;
    }
}
