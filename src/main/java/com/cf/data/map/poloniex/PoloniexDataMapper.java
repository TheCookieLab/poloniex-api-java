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
import com.cf.data.model.poloniex.deserialize.PoloniexChartDataDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author David
 */
public class PoloniexDataMapper {

    private final Gson gson;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
    private final static String EMPTY_RESULTS = "[]";
    private final static String INVALID_CHART_DATA_DATE_RANGE_RESULT = "[{\"date\":0,\"high\":0,\"low\":0,\"open\":0,\"close\":0,\"volume\":0,\"quoteVolume\":0,\"weightedAverage\":0}]";
    private final static String INVALID_CHART_DATA_CURRENCY_PAIR_RESULT = "{\"error\":\"Invalid currency pair.\"}";

    public PoloniexDataMapper() {
        gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
                    @Override
                    public ZonedDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString(), DTF);
                    }
                }).registerTypeAdapter(PoloniexChartData.class, new PoloniexChartDataDeserializer())
                .create();
    }

    public List<PoloniexChartData> mapChartData(String chartDataResult) {

        if (INVALID_CHART_DATA_DATE_RANGE_RESULT.equals(chartDataResult) || INVALID_CHART_DATA_CURRENCY_PAIR_RESULT.equals(chartDataResult)) {
            return Collections.EMPTY_LIST;
        }

        List<PoloniexChartData> results;
        try {
            PoloniexChartData[] chartDataResults = gson.fromJson(chartDataResult, PoloniexChartData[].class);
            results = Arrays.asList(chartDataResults);
        } catch (JsonSyntaxException | DateTimeParseException ex) {
            LogManager.getLogger().error("Exception mapping chart data {} - {}", chartDataResult, ex.getMessage());
            results = Collections.EMPTY_LIST;
        }
        return results;

    }

    public PoloniexFeeInfo mapFeeInfo(String feeInfoResult) {
        PoloniexFeeInfo feeInfo = gson.fromJson(feeInfoResult, new TypeToken<PoloniexFeeInfo>() {
        }.getType());

        return feeInfo;
    }

    public PoloniexActiveLoanTypes mapActiveLoans(String activeLoansResult) {
        PoloniexActiveLoanTypes activeLoanTypes = gson.fromJson(activeLoansResult, PoloniexActiveLoanTypes.class);

        return activeLoanTypes;
    }

    public PoloniexTicker mapTickerForCurrency(String currencyType, String tickerData) {
        Map<String, PoloniexTicker> tickerResults = gson.fromJson(tickerData, new TypeToken<Map<String, PoloniexTicker>>() {
        }.getType());
        return tickerResults.get(currencyType);
    }

    public List<String> mapMarkets(String tickerData) {
        Map<String, PoloniexTicker> tickerResults = gson.fromJson(tickerData, new TypeToken<Map<String, PoloniexTicker>>() {
        }.getType());
        return new ArrayList<>(tickerResults.keySet());
    }

    public PoloniexCompleteBalance mapCompleteBalanceResultForCurrency(String currencyType, String completeBalanceResults) {
        Map<String, PoloniexCompleteBalance> balanceResults = gson.fromJson(completeBalanceResults, new TypeToken<Map<String, PoloniexCompleteBalance>>() {
        }.getType());
        return balanceResults.get(currencyType);
    }

    public List<PoloniexOpenOrder> mapOpenOrders(String openOrdersResults) {
        List<PoloniexOpenOrder> openOrders = gson.fromJson(openOrdersResults, new TypeToken<List<PoloniexOpenOrder>>() {
        }.getType());
        return openOrders;
    }

    public List<PoloniexTradeHistory> mapTradeHistory(String tradeHistoryResults) {
        List<PoloniexTradeHistory> tradeHistory = gson.fromJson(tradeHistoryResults, new TypeToken<List<PoloniexTradeHistory>>() {
        }.getType());
        return tradeHistory;
    }

    public boolean mapCancelOrder(String cancelOrderResult) {
        int success = gson.fromJson(cancelOrderResult, JsonObject.class).get("success").getAsInt();
        return success == 1;
    }

    public PoloniexOrderResult mapTradeOrder(String orderResult) {
        PoloniexOrderResult tradeOrderResult = gson.fromJson(orderResult, new TypeToken<PoloniexOrderResult>() {
        }.getType());
        return tradeOrderResult;
    }

    public List<PoloniexLendingHistory> mapLendingHistory(String lendingHistoryResults) {
        List<PoloniexLendingHistory> lendingHistory = gson.fromJson(lendingHistoryResults, new TypeToken<List<PoloniexLendingHistory>>() {
        }.getType());
        return lendingHistory;
    }

    public List<PoloniexLoanOffer> mapOpenLoanOffers(String currency, String results) {
        if (EMPTY_RESULTS.equals(results)) {
            return Collections.EMPTY_LIST;
        }
        Map<String, List<PoloniexLoanOffer>> offers = gson.fromJson(results, new TypeToken<Map<String, List<PoloniexLoanOffer>>>() {
        }.getType());
        return offers.get(currency);
    }

    public PoloniexLendingResult mapLendingResult(String result) {
        PoloniexLendingResult plr = gson.fromJson(result, new TypeToken<PoloniexLendingResult>() {
        }.getType());
        return plr;
    }

}
