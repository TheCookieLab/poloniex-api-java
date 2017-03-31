package com.cf.example;

import com.cf.ExchangeService;
import com.cf.client.poloniex.PoloniexExchangeService;
import com.cf.data.model.poloniex.PoloniexChartData;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author David
 */
public class GetDailyBTCChartDataExample
{
    private final static Logger LOG = LogManager.getLogger(GetDailyBTCChartDataExample.class);
    private final static String DEFAULT_PROPERTIES_FILE = "app.properties";
    private final static String POLONIEX_API_KEY_PROP_NAME = "poloniex.api.key";
    private final static String POLONIEX_API_SECRET_PROP_NAME = "poloniex.api.secret";

    public static void main(String[] args)
    {
        String propertiesFileName = args.length > 0 ? args[0] : DEFAULT_PROPERTIES_FILE;
        new GetDailyBTCChartDataExample().run(propertiesFileName);
    }

    public void run(String propertiesFileName)
    {
        Properties properties = this.loadProperties(propertiesFileName);

        String tradingAPIKey = properties.getProperty(POLONIEX_API_KEY_PROP_NAME);
        if (tradingAPIKey == null)
        {
            LOG.warn("Did not find value for " + POLONIEX_API_KEY_PROP_NAME + " in " + propertiesFileName + ". Trading API commands will fail");
        }

        String tradingAPISecret = properties.getProperty(POLONIEX_API_SECRET_PROP_NAME);
        if (tradingAPISecret == null)
        {
            LOG.warn("Did not find value for " + POLONIEX_API_SECRET_PROP_NAME + " in " + propertiesFileName + ". Trading API commands will fail");
        }

        PoloniexExchangeService service = new PoloniexExchangeService(tradingAPIKey, tradingAPISecret);
        List<PoloniexChartData> btcDailyChartData = service.returnBTCChartData(ExchangeService.DAILY_TIME_PERIOD, ExchangeService.LONG_LONG_AGO);
        LOG.info(btcDailyChartData);
    }

    private Properties loadProperties(String propertiesFileName)
    {
        Properties properties = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName))
        {
            properties.load(in);
        }
        catch (IOException ex)
        {
            LOG.error("Could not load properties file " + propertiesFileName + " - " + ex.getMessage());
        }

        return properties;
    }
}
