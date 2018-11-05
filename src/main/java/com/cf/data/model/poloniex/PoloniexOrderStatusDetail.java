package com.cf.data.model.poloniex;

import com.google.gson.Gson;

/**
 * @author guodong
 */
public class PoloniexOrderStatusDetail {
	public final String status;
	public final String rate;
	public final String amount;
	public final String currencyPair;
	public final String date;
	public final String total;
	public final String type;
	public final String startingAmount;

	public PoloniexOrderStatusDetail(String status, String rate, String amount, String currencyPair, String date, String total, String type, String startingAmount) {
		this.status = status;
		this.rate = rate;
		this.amount = amount;
		this.currencyPair = currencyPair;
		this.date = date;
		this.total = total;
		this.type = type;
		this.startingAmount = startingAmount;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
