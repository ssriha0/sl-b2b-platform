package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerBackgroundDetail")
public class ProviderBackgroundDetail {

	@XStreamAlias("backgroundStatus")
	private String backgroundStatus;
	
	@XStreamAlias("backgroundStatusCount")
	private Integer backgroundStatusCount;
	
	public String getBackgroundStatus() {
		return backgroundStatus;
	}
	public void setBackgroundStatus(String backgroundStatus) {
		this.backgroundStatus = backgroundStatus;
	}
	public Integer getBackgroundStatusCount() {
		return backgroundStatusCount;
	}
	public void setBackgroundStatusCount(Integer backgroundStatusCount) {
		this.backgroundStatusCount = backgroundStatusCount;
	}

}
