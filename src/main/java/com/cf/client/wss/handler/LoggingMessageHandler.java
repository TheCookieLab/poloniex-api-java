package com.cf.client.wss.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author David
 */
public class LoggingMessageHandler implements IMessageHandler {

    private final static Logger LOG = LogManager.getLogger();

    @Override
    public void handle(String message) {
        LOG.info(message);
    }

}
