
package com.cf.client.wss.handler;

/**
 *
 * @author David
 */
public class NoOpMessageHandler implements IMessageHandler {

    @Override
    public void handle(String message) {
        // do nothing
    }
    
}
