package com.cf.data.model.poloniex;

import com.google.gson.Gson;

import java.util.List;

/**
 *
 * @author cheolhee
 */
public class PoloniexLendingResult
{
    public final String success;
    public final String message;
    public final String orderID;

    public PoloniexLendingResult(String success, String message, String orderID)
    {
        this.success = success;
        this.message = message;
        this.orderID = orderID;
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }

}
