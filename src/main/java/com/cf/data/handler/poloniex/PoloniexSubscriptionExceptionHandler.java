package com.cf.data.handler.poloniex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rx.functions.Action1;

/**
 *
 * @author David
 */
public class PoloniexSubscriptionExceptionHandler implements Action1<Throwable>
{
    private final static Logger LOG = LogManager.getLogger();
    private final String name;

    public PoloniexSubscriptionExceptionHandler(String name)
    {
        this.name = name;
    }

    @Override
    public void call(Throwable t)
    {
        LOG.warn("{} handler encountered exception - {}", name, t.getMessage());

    }

}
