package com.cf.data.model.poloniex;

import com.google.gson.Gson;

/**
 * @author guodong
 */
public class PoloniexOrderStatusCheck {
	public final Integer success;
	public final Object result;

	public PoloniexOrderStatusCheck(Integer success, Object result) {
		this.success = success;
		this.result = result;
	}


	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
