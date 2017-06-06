package com.cf.example;

import com.cf.client.WSSClient;
import com.cf.data.handler.poloniex.PoloniexSubscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author David
 */
public class PoloniexWSSClientExample
{
    private final static Logger LOG = LogManager.getLogger(PoloniexWSSClientExample.class);
    private static final String ENDPOINT_URL = "wss://api.poloniex.com";
    private static final String DEFAULT_REALM = "realm1";

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
        try (WSSClient poloniexWSSClient = new WSSClient(ENDPOINT_URL, DEFAULT_REALM))
        {
            poloniexWSSClient.subscribe(PoloniexSubscription.TICKER);
            poloniexWSSClient.run(60000);
        }
    }
}
