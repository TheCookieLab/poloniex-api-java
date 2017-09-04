package com.cf.data.map.poloniex;

import com.cf.data.model.poloniex.PoloniexFeeInfo;
import com.cf.data.model.poloniex.PoloniexOpenOrder;
import com.cf.data.model.poloniex.PoloniexOrderResult;
import com.cf.data.model.poloniex.PoloniexTradeHistory;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author David
 */
public class PoloniexDataMapperTest
{
    private final PoloniexDataMapper mapper = new PoloniexDataMapper();

    @Test
    public void mapCompleteBalanceResultForCurrencyReturnsCorrectBalanceResult()
    {
        String currencyType = "BTC";
        String completeBalanceResults = "{\n"
                + "	\"YACC\" : {\n"
                + "		\"available\" : \"0.00000000\",\n"
                + "		\"onOrders\" : \"0.00000000\",\n"
                + "		\"btcValue\" : \"0.00000000\"\n"
                + "	},\n"
                + "	\"YANG\" : {\n"
                + "		\"available\" : \"0.00000000\",\n"
                + "		\"onOrders\" : \"0.00000000\",\n"
                + "		\"btcValue\" : \"0.00000000\"\n"
                + "	},\n"
                + "	\"YC\" : {\n"
                + "		\"available\" : \"0.00000000\",\n"
                + "		\"onOrders\" : \"0.00000000\",\n"
                + "		\"btcValue\" : \"0.00000000\"\n"
                + "	},\n"
                + "	\"BTC\" : {\n"
                + "		\"available\" : \"5.00000000\",\n"
                + "		\"onOrders\" : \"1.00000000\",\n"
                + "		\"btcValue\" : \"2.00000000\"\n"
                + "	}\n"
                + "}";
        assertNotNull(mapper.mapCompleteBalanceResultForCurrency(currencyType, completeBalanceResults));
    }

    @Test
    public void mapCompleteBalanceResultReturnsNullForInvalidCurrencyType()
    {
        String currencyType = "BTC";
        String completeBalanceResults = "{\n"
                + "	\"YACC\" : {\n"
                + "		\"available\" : \"0.00000000\",\n"
                + "		\"onOrders\" : \"0.00000000\",\n"
                + "		\"btcValue\" : \"0.00000000\"\n"
                + "	},\n"
                + "	\"YANG\" : {\n"
                + "		\"available\" : \"0.00000000\",\n"
                + "		\"onOrders\" : \"0.00000000\",\n"
                + "		\"btcValue\" : \"0.00000000\"\n"
                + "	},\n"
                + "	\"YC\" : {\n"
                + "		\"available\" : \"0.00000000\",\n"
                + "		\"onOrders\" : \"0.00000000\",\n"
                + "		\"btcValue\" : \"0.00000000\"\n"
                + "	},\n"
                + "	\"YIN\" : {\n"
                + "		\"available\" : \"0.00000000\",\n"
                + "		\"onOrders\" : \"0.00000000\",\n"
                + "		\"btcValue\" : \"0.00000000\"\n"
                + "	}\n"
                + "}";
        assertNull(mapper.mapCompleteBalanceResultForCurrency(currencyType, completeBalanceResults));
    }

    @Test
    public void mapPoloniexFeeInfo()
    {
        String data = "{\"makerFee\":\"0.00150000\",\"takerFee\":\"0.00250000\",\"thirtyDayVolume\":\"3.30872191\",\"nextTier\":\"600.00000000\"}";
        PoloniexFeeInfo feeInfo = mapper.mapFeeInfo(data);
        assertNotNull(feeInfo);
    }

    @Test
    public void mapCancelOrderSuccessReturnsTrue()
    {
        String data = "{\"success\":1}";
        boolean result = mapper.mapCancelOrder(data);
        assertTrue(result);
    }

    @Test
    public void mapCancelOrderFailureReturnsFalse()
    {
        String data = "{\"success\":0}";
        boolean result = mapper.mapCancelOrder(data);
        assertFalse(result);
    }

    @Test
    public void mapBuyTradeOrder()
    {
        String data = "{\"orderNumber\":31226040,\"resultingTrades\":[{\"amount\":\"338.8732\",\"date\":\"2014-10-18 23:03:21\",\"rate\":\"0.00000173\",\"total\":\"0.00058625\",\"tradeID\":\"16164\",\"type\":\"buy\"}]}";
        PoloniexOrderResult orderResult = mapper.mapTradeOrder(data);
        assertEquals(31226040L, orderResult.orderNumber.longValue());
        assertEquals(1, orderResult.resultingTrades.size());
        assertEquals(BigDecimal.valueOf(338.8732), orderResult.resultingTrades.get(0).amount);
        assertEquals("2014-10-18T23:03:21", orderResult.resultingTrades.get(0).date.toString());
        assertEquals(BigDecimal.valueOf(0.00000173), orderResult.resultingTrades.get(0).rate);
        assertEquals(BigDecimal.valueOf(0.00058625), orderResult.resultingTrades.get(0).total);
        assertEquals("16164", orderResult.resultingTrades.get(0).tradeID);
        assertEquals("buy", orderResult.resultingTrades.get(0).type);
        assertNull(orderResult.error);
    }

    @Test
    public void mapFailedBuyTradeOrderWithError()
    {
        String data = "{\"error\":\"Unable to fill order completely.\"}";
        PoloniexOrderResult orderResult = mapper.mapTradeOrder(data);
        assertNull(orderResult.orderNumber);
        assertNull(orderResult.resultingTrades);
        assertNotNull(orderResult.error);
    }

    @Test
    public void mapEmptyOpenOrders()
    {
        String data = "[]";
        List<PoloniexOpenOrder> openOrders = mapper.mapOpenOrders(data);
        assertTrue(openOrders.isEmpty());
    }

    @Test
    public void mapSingleOpenOrder()
    {
        String openOrderResults = "[{\"orderNumber\":\"117741833082\",\"type\":\"sell\",\"rate\":\"277.79999987\",\"startingAmount\":\"0.73815000\",\"amount\":\"0.73815000\",\"total\":\"205.05806990\",\"date\":\"2017-07-04 14:24:22\",\"margin\":0}]";
        List<PoloniexOpenOrder> openOrders = mapper.mapOpenOrders(openOrderResults);
        assertEquals(1, openOrders.size());

        PoloniexOpenOrder openOrder = openOrders.get(0);
        assertEquals(new BigDecimal("0.73815000"), openOrder.amount);
        assertEquals("117741833082", openOrder.orderNumber);
        assertEquals(new BigDecimal("277.79999987"), openOrder.rate);
        assertEquals(new BigDecimal("205.05806990"), openOrder.total);
        assertEquals("sell", openOrder.type);
    }

    @Test
    public void mapMultipleOpenOrders()
    {
        String data = "[{\"orderNumber\":\"120466\",\"type\":\"sell\",\"rate\":\"0.025\",\"amount\":\"100\",\"total\":\"2.5\"},{\"orderNumber\":\"120467\",\"type\":\"sell\",\"rate\":\"0.04\",\"amount\":\"100\",\"total\":\"4\"}]";
        List<PoloniexOpenOrder> openOrders = mapper.mapOpenOrders(data);
        assertEquals(2, openOrders.size());
    }

    @Test
    public void mapTradeHistory()
    {
        String data = "[{\n"
                + "		\"globalTradeID\": 84912521,\n"
                + "		\"tradeID\": \"1640236\",\n"
                + "		\"date\": \"2017-03-06 18:49:34\",\n"
                + "		\"rate\": \"1273.37202076\",\n"
                + "		\"amount\": \"0.53284726\",\n"
                + "		\"total\": \"678.51279222\",\n"
                + "		\"fee\": \"0.00150000\",\n"
                + "		\"orderNumber\": \"55510230325\",\n"
                + "		\"type\": \"buy\",\n"
                + "		\"category\": \"exchange\"\n"
                + "	}, {\n"
                + "		\"globalTradeID\": 84558071,\n"
                + "		\"tradeID\": \"1628053\",\n"
                + "		\"date\": \"2017-03-04 20:46:16\",\n"
                + "		\"rate\": \"1268.00099400\",\n"
                + "		\"amount\": \"0.52759685\",\n"
                + "		\"total\": \"668.99333023\",\n"
                + "		\"fee\": \"0.00150000\",\n"
                + "		\"orderNumber\": \"55201819045\",\n"
                + "		\"type\": \"sell\",\n"
                + "		\"category\": \"exchange\"\n"
                + "	}, {\n"
                + "		\"globalTradeID\": 83437549,\n"
                + "		\"tradeID\": \"1576412\",\n"
                + "		\"date\": \"2017-03-01 00:51:31\",\n"
                + "		\"rate\": \"1186.00000016\",\n"
                + "		\"amount\": \"0.13349834\",\n"
                + "		\"total\": \"158.32903126\",\n"
                + "		\"fee\": \"0.00150000\",\n"
                + "		\"orderNumber\": \"54147454465\",\n"
                + "		\"type\": \"buy\",\n"
                + "		\"category\": \"exchange\"\n"
                + "	}]";

        List<PoloniexTradeHistory> tradeHistory = mapper.mapTradeHistory(data);
        assertEquals(3, tradeHistory.size());

        PoloniexTradeHistory first = tradeHistory.get(0);
        assertEquals(84912521L, first.globalTradeID.longValue());
        assertEquals("1640236", first.tradeID);
        assertEquals("2017-03-06T18:49:34", first.date.toString());
        assertEquals("1273.37202076", first.rate.toPlainString());
        assertEquals("0.00150000", first.fee.toPlainString());
        assertEquals("55510230325", first.orderNumber);
        assertEquals("buy", first.type);
        assertEquals("exchange", first.category);
    }

}
