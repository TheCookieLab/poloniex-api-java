package com.cf.data.model.poloniex;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @author guodong
 */
public class PoloniexOrderStatus {
	public final Integer success;
	public final String error;
	public final Map<String, PoloniexOrderStatusDetail> result;

	public PoloniexOrderStatus(Integer success, String error, Map<String, PoloniexOrderStatusDetail> result) {
		this.success = success;
		this.error = error;
		this.result = result;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
