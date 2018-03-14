package com.cf.example;

import com.cf.client.WSSClient;
import com.cf.client.poloniex.wss.model.PoloniexWSSSubscription;
import com.cf.client.wss.handler.LoggerMessageHandler;
import com.cf.client.wss.handler.TickerMessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author David
 */
public class PoloniexWSSClientExample
{
    private static final Logger LOG = LogManager.getLogger(PoloniexWSSClientExample.class);
    private static final String ENDPOINT_URL = "wss://api2.poloniex.com";

    public static void main(String[] args)
    {
        try
        {
            new PoloniexWSSClientExample().run();
        }
        catch (Exception ex)
        {
            LOG.fatal("An exception occurred when running PoloniexWSSClientExample - {}", ex.getMessage());
            System.exit(-1);
        }
    }

    public void run() throws Exception
    {
        try (WSSClient wssClient = new WSSClient(ENDPOINT_URL))
        {
            wssClient.addSubscription(PoloniexWSSSubscription.USDT_ETH, new LoggerMessageHandler());
            wssClient.addSubscription(PoloniexWSSSubscription.TICKER, new TickerMessageHandler());
            wssClient.run(20000);
        }
    }
}
