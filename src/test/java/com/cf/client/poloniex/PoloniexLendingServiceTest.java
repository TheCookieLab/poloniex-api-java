package com.cf.client.poloniex;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 *
 * @author cheolhee
 */
public class PoloniexLendingServiceTest
{
    static final String apiKey = "YOUR_API_KEY";
    static final String apiSecret = "YOUR_API_SECRET";

    ObjectMapper mapper = new ObjectMapper();
    String res = "";
    BigDecimal sum = BigDecimal.ZERO;

    PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

    @Test
    public void returnActiveLoans() throws Exception
    {
    }

    @Test
    public void returnLendingHistory() throws Exception
    {
    }

    @Test
    public void createLoanOffer() throws Exception
    {
    }

    @Test
    public void cancelLoanOffer() throws Exception
    {
    }


    @Test
    public void returnOpenLoanOffers() throws Exception
    {
    }

    @Test
    public void toggleAutoRenew() throws Exception
    {
    }

}