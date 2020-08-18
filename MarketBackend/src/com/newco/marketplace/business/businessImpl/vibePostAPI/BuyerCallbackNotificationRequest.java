package com.newco.marketplace.business.businessImpl.vibePostAPI;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author
 *
 */
public class BuyerCallbackNotificationRequest {

	@XStreamAlias("data")
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
