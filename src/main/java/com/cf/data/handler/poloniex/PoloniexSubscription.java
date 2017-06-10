package com.cf.data.handler.poloniex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rx.functions.Action1;
import ws.wamp.jawampa.PubSubData;

/**
 *
 * @author David
 */
public class PoloniexSubscription implements Action1<PubSubData>
{
    public static final PoloniexSubscription TICKER = new PoloniexSubscription("ticker");

    protected final static Logger LOG = LogManager.getLogger();
    public final String feedName;

    public PoloniexSubscription(String feedName)
    {
        this.feedName = feedName;
    }

    @Override
    public void call(PubSubData event)
    {
        try
        {
            LOG.trace(event.arguments());
        }
        catch (Exception ex)
        {
            LOG.warn("Exception processing event data - " + ex.getMessage());
        }
    }
}
