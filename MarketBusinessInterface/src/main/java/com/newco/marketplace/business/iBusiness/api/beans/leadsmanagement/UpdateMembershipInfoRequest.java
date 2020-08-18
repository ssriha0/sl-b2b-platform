package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.Date;

import com.newco.marketplace.api.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("UpdateMemberInfoRequest")
public class UpdateMembershipInfoRequest {

	@XStreamAlias("LeadId")
	private String leadId;

	@XStreamAlias("MemberShipNumber")
	private String memberShipNumber;
	
	//Result of validation
	private ResultsCode validationCode;	
	
	private String pointsRewarded;	
    
	private Date modifiedDate;
	
	private boolean eligibleForReward;
	
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

	public String getMemberShipNumber() {
		return memberShipNumber;
	}

	public void setMemberShipNumber(String memberShipNumber) {
		this.memberShipNumber = memberShipNumber;
	}

	public String getPointsRewarded() {
		return pointsRewarded;
	}

	public void setPointsRewarded(String pointsRewarded) {
		this.pointsRewarded = pointsRewarded;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isEligibleForReward() {
		return eligibleForReward;
	}

	public void setEligibleForReward(boolean eligibleForReward) {
		this.eligibleForReward = eligibleForReward;
	}

}
