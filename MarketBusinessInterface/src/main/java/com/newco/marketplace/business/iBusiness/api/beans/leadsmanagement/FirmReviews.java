package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FirmReviews")
public class FirmReviews {
    
	@XStreamImplicit(itemFieldName = "FirmReview")
	private List<FirmReview> firmReview;
	
	public List<FirmReview> getFirmReview() {
		return firmReview;
	}

	public void setFirmReview(List<FirmReview> firmReview) {
		this.firmReview = firmReview;
	}

	

}
