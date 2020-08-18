package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("firmReviews")
public class LeadFirmReviews {
    
	@XStreamImplicit(itemFieldName = "firmReview")
	private List<LeadFirmReview> firmReview;

	public List<LeadFirmReview> getFirmReview() {
		return firmReview;
	}

	public void setFirmReview(List<LeadFirmReview> firmReview) {
		this.firmReview = firmReview;
	}
	
	

	

}
