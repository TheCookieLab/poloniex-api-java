package com.cf.data.model.poloniex.deserialize;

import com.cf.data.model.poloniex.PoloniexChartData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 *
 * @author David
 */
public class PoloniexChartDataDeserializer implements JsonDeserializer<PoloniexChartData> {

    @Override
    public PoloniexChartData deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jo = (JsonObject) json;
        ZonedDateTime date = ZonedDateTime.ofInstant(Instant.ofEpochMilli(jo.get("date").getAsLong() * 1000), ZoneOffset.UTC);
        BigDecimal high = BigDecimal.valueOf(jo.get("high").getAsDouble());
        BigDecimal low = BigDecimal.valueOf(jo.get("low").getAsDouble());
        BigDecimal open = BigDecimal.valueOf(jo.get("open").getAsDouble());
        BigDecimal close = BigDecimal.valueOf(jo.get("close").getAsDouble());
        BigDecimal volume = BigDecimal.valueOf(jo.get("volume").getAsDouble());
        BigDecimal quoteVolume = BigDecimal.valueOf(jo.get("quoteVolume").getAsDouble());
        BigDecimal weightedAverage = BigDecimal.valueOf(jo.get("weightedAverage").getAsDouble());

        return new PoloniexChartData(date, high, low, open, close, volume, quoteVolume, weightedAverage);
    }

}
