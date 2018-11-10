package com.cf.data.model.poloniex;

import com.google.gson.Gson;

/**
 * @author guodong
 */
public class PoloniexWithdrawResult {
	public final String response;
	public final String error;

	public PoloniexWithdrawResult(String response, String error) {
		this.response = response;
		this.error = error;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
