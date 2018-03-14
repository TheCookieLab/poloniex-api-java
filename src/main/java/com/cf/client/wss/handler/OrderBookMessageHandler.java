package com.cf.client.wss.handler;

import com.cf.client.poloniex.wss.model.PoloniexWSSOrderBookUpdate;
import com.google.gson.Gson;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author David
 */
public class OrderBookMessageHandler implements IMessageHandler {

    private final static Logger LOG = LogManager.getLogger();
    private final static Gson GSON = new Gson();
    
    @Override
    public void handle(String message) {
        PoloniexWSSOrderBookUpdate orderBook = this.mapMessageToPoloniexOrderBook(message);
        LOG.debug(orderBook);
    }
    
    protected PoloniexWSSOrderBookUpdate mapMessageToPoloniexOrderBook(String message) {
        List results = GSON.fromJson(message, List.class);
        if (results.size() < 3) {
            return null;
        }

        return null;
    }

}
