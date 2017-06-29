package com.cf.data.map.poloniex;

import com.cf.data.model.poloniex.PoloniexActiveLoanTypes;
import com.cf.data.model.poloniex.PoloniexChartData;
import com.cf.data.model.poloniex.PoloniexCompleteBalance;
import com.cf.data.model.poloniex.PoloniexFeeInfo;
import com.cf.data.model.poloniex.PoloniexLendingHistory;
import com.cf.data.model.poloniex.PoloniexLendingResult;
import com.cf.data.model.poloniex.PoloniexLoanOffer;
import com.cf.data.model.poloniex.PoloniexOpenOrder;
import com.cf.data.model.poloniex.PoloniexOrderResult;
import com.cf.data.model.poloniex.PoloniexTicker;
import com.cf.data.model.poloniex.PoloniexTradeHistory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author David
 */
public class PoloniexDataMapper
{
    private final Gson gson;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PoloniexDataMapper()
    {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>()
        {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
            {
                return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DTF);
            }
        }).create();
    }

    public List<PoloniexChartData> mapChartData(String chartDataResult)
    {
        PoloniexChartData[] chartDataResults = gson.fromJson(chartDataResult, PoloniexChartData[].class);
        return Arrays.asList(chartDataResults);
    }

    public PoloniexFeeInfo mapFeeInfo(String feeInfoResult)
    {
        PoloniexFeeInfo feeInfo = gson.fromJson(feeInfoResult, new TypeToken<PoloniexFeeInfo>()
        {
        }.getType());

        return feeInfo;
    }

    public PoloniexActiveLoanTypes mapActiveLoans(String activeLoansResult)
    {
    	PoloniexActiveLoanTypes activeLoanTypes = gson.fromJson(activeLoansResult, PoloniexActiveLoanTypes.class);

        return activeLoanTypes;
    }

    public PoloniexTicker mapTickerForCurrency(String currencyType, String tickerData)
    {
        Map<String, PoloniexTicker> tickerResults = gson.fromJson(tickerData, new TypeToken<Map<String, PoloniexTicker>>()
        {
        }.getType());
        return tickerResults.get(currencyType);
    }

    public List<String> mapMarkets(String tickerData)
    {
        Map<String, PoloniexTicker> tickerResults = gson.fromJson(tickerData, new TypeToken<Map<String, PoloniexTicker>>()
        {
        }.getType());
        return new ArrayList<>(tickerResults.keySet());
    }

    public PoloniexCompleteBalance mapCompleteBalanceResultForCurrency(String currencyType, String completeBalanceResults)
    {
        Map<String, PoloniexCompleteBalance> balanceResults = gson.fromJson(completeBalanceResults, new TypeToken<Map<String, PoloniexCompleteBalance>>()
        {
        }.getType());
        return balanceResults.get(currencyType);
    }

    public List<PoloniexOpenOrder> mapOpenOrders(String openOrdersResults)
    {
        List<PoloniexOpenOrder> openOrders = gson.fromJson(openOrdersResults, new TypeToken<List<PoloniexOpenOrder>>()
        {
        }.getType());
        return openOrders;
    }

    public List<PoloniexTradeHistory> mapTradeHistory(String tradeHistoryResults)
    {
        List<PoloniexTradeHistory> tradeHistory = gson.fromJson(tradeHistoryResults, new TypeToken<List<PoloniexTradeHistory>>()
        {
        }.getType());
        return tradeHistory;
    }

    public boolean mapCancelOrder(String cancelOrderResult)
    {
        int success = gson.fromJson(cancelOrderResult, JsonObject.class).get("success").getAsInt();
        return success == 1;
    }

    public PoloniexOrderResult mapTradeOrder(String orderResult)
    {
        PoloniexOrderResult tradeOrderResult = gson.fromJson(orderResult, new TypeToken<PoloniexOrderResult>()
        {
        }.getType());
        return tradeOrderResult;
    }

    public List<PoloniexLendingHistory> mapLendingHistory(String lendingHistoryResults)
    {
        List<PoloniexLendingHistory> lendingHistory = gson.fromJson(lendingHistoryResults, new TypeToken<List<PoloniexLendingHistory>>()
        {
        }.getType());
        return lendingHistory;
    }

    public List<PoloniexLoanOffer> mapOpenLoanOffers(String currency, String results)
    {
        // Trading API returns '[]' instead of '{}' when no offer exists.
        if ("[]".equals(results)) {
            return Collections.EMPTY_LIST;
        }
        Map<String,List<PoloniexLoanOffer>> offers = gson.fromJson(results, new TypeToken<Map<String,List<PoloniexLoanOffer>>>()
        {
        }.getType());
        return offers.get(currency);
    }

    public PoloniexLendingResult mapLendingResult(String result)
    {
        PoloniexLendingResult plr = gson.fromJson(result, new TypeToken<PoloniexLendingResult>()
        {
        }.getType());
        return plr;
    }

}
