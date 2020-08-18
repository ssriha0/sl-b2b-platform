package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.newco.marketplace.api.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("AddRatingsAndReviewRequest")
public class AddRatingsAndReviewRequest {

	@XStreamAlias("LeadId")
	private String leadId;

	@XStreamAlias("Ratings")
	private Ratings ratings;
	
	//Result of validation
	private ResultsCode validationCode;	
	

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public Ratings getRatings() {
		return ratings;
	}

	public void setRatings(Ratings ratings) {
		this.ratings = ratings;
	}

	

}
