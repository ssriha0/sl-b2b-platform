package com.newco.marketplace.api.beans.so.soDetails.vo;

public class RetrieveSODetailsMobileVO {

	private ServiceOrderDetailsVO soDetails;
    //This added to for timeZone conversion
	private String timeZone;
	public ServiceOrderDetailsVO getSoDetails() {
		return soDetails;
	}

	public void setSoDetails(ServiceOrderDetailsVO soDetails) {
		this.soDetails = soDetails;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	
}
