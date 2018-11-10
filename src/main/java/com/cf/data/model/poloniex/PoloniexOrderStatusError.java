package com.cf.data.model.poloniex;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @author guodong
 */
public class PoloniexOrderStatusError {
	public final Integer success;
	public final Map<String, String> result;

	public PoloniexOrderStatusError(Integer success, Map<String, String> result) {
		this.success = success;
		this.result = result;
	}


	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
