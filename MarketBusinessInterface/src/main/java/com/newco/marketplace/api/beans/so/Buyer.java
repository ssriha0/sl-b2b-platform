package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("buyer")
public class Buyer {

	@XStreamAlias("buyerId")
	private Integer buyerId;

	@OptionalParam
	@XStreamAlias("minTimeWindow")
	private Integer minTimeWindow;
	
	@OptionalParam
	@XStreamAlias("maxTimeWindow")
	private Integer maxTimeWindow;
	
	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getMinTimeWindow() {
		return minTimeWindow;
	}

	public void setMinTimeWindow(Integer minTimeWindow) {
		this.minTimeWindow = minTimeWindow;
	}

	public Integer getMaxTimeWindow() {
		return maxTimeWindow;
	}

	public void setMaxTimeWindow(Integer maxTimeWindow) {
		this.maxTimeWindow = maxTimeWindow;
	}
	
}
