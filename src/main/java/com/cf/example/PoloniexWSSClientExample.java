package com.cf.example;

import com.cf.client.PlainWSSClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author David
 */
public class PoloniexWSSClientExample
{
    private final static Logger LOG = LogManager.getLogger(PoloniexWSSClientExample.class);
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
        try (PlainWSSClient plainWssClient = new PlainWSSClient(ENDPOINT_URL))
        {
            plainWssClient.run(60000);
        }
    }
}
