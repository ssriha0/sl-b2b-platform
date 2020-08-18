package com.newco.marketplace.api.beans.alerts;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SendAlertService
 * @author Infosys
 *
 */
@XStreamAlias("subscription")
public class SmsUnSubscriptionRequest {
	
	@XStreamAlias("confirmed-at")
	private String confirmedAt;
	
	@XStreamAlias("opt-in-at")
	private String optOutAt;
	
	@XStreamAlias("subscription-file-upload-id")
	private String subFileId;
	
	@XStreamAlias("user")
	private User user;

	public String getConfirmedAt() {
		return confirmedAt;
	}

	public void setConfirmedAt(String confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	public String getOptOutAt() {
		return optOutAt;
	}

	public void setOptOutAt(String optOutAt) {
		this.optOutAt = optOutAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSubFileId() {
		return subFileId;
	}

	public void setSubFileId(String subFileId) {
		this.subFileId = subFileId;
	}
	
}
