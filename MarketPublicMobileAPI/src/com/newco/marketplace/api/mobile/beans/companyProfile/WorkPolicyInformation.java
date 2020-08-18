package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("workPolicyInformation")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkPolicyInformation {
	
	@XStreamAlias("drugTestingPolicyInd")
	private Boolean drugTestingPolicyInd;
	
	@XStreamAlias("considerDrugTestInd")
	private Boolean considerDrugTestInd;
	
	@XStreamAlias("workEnvironmentPolicyInd")
	private Boolean workEnvironmentPolicyInd;
	
	@XStreamAlias("considerEthicPolicyInd")
	private Boolean considerEthicPolicyInd;
	
	@XStreamAlias("employeeCitizenShipProofInd")
	private Boolean employeeCitizenShipProofInd;
	
	@XStreamAlias("considerImplPolicyInd")
	private Boolean considerImplPolicyInd;
	
	@XStreamAlias("requireBadgeInd")
	private Boolean requireBadgeInd;
	
	@XStreamAlias("considerBadgeInd")
	private Boolean considerBadgeInd;
	
	public Boolean getDrugTestingPolicyInd() {
		return drugTestingPolicyInd;
	}

	public void setDrugTestingPolicyInd(Boolean drugTestingPolicyInd) {
		this.drugTestingPolicyInd = drugTestingPolicyInd;
	}
	
	public Boolean getConsiderDrugTestInd() {
		return considerDrugTestInd;
	}

	public void setConsiderDrugTestInd(Boolean considerDrugTestInd) {
		this.considerDrugTestInd = considerDrugTestInd;
	}
	
	public Boolean getWorkEnvironmentPolicyInd() {
		return workEnvironmentPolicyInd;
	}

	public void setWorkEnvironmentPolicyInd(Boolean workEnvironmentPolicyInd) {
		this.workEnvironmentPolicyInd = workEnvironmentPolicyInd;
	}
	
	public Boolean getConsiderEthicPolicyInd() {
		return considerEthicPolicyInd;
	}

	public void setConsiderEthicPolicyInd(Boolean considerEthicPolicyInd) {
		this.considerEthicPolicyInd = considerEthicPolicyInd;
	}
	
	public Boolean getEmployeeCitizenShipProofInd() {
		return employeeCitizenShipProofInd;
	}

	public void setEmployeeCitizenShipProofInd(Boolean employeeCitizenShipProofInd) {
		this.employeeCitizenShipProofInd = employeeCitizenShipProofInd;
	}
	
	public Boolean getConsiderImplPolicyInd() {
		return considerImplPolicyInd;
	}

	public void setConsiderImplPolicyInd(Boolean considerImplPolicyInd) {
		this.considerImplPolicyInd = considerImplPolicyInd;
	}	

	public Boolean getRequireBadgeInd() {
		return requireBadgeInd;
	}

	public void setRequireBadgeInd(Boolean requireBadgeInd) {
		this.requireBadgeInd = requireBadgeInd;
	}

	public Boolean getConsiderBadgeInd() {
		return considerBadgeInd;
	}

	public void setConsiderBadgeInd(Boolean considerBadgeInd) {
		this.considerBadgeInd = considerBadgeInd;
	}

	
	
}
