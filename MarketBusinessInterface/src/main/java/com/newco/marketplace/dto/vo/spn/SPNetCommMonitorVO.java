package com.newco.marketplace.dto.vo.spn;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;


public class SPNetCommMonitorVO extends SerializableBaseVO{	
	private static final long serialVersionUID = 20090414;
	private Integer spnId;
	private String businessName;
	private String spnName;
	private Date campStartDate;
	private Date campEndDate;
	private String campaignName;
	private String communicationSubject;
	private Date invModifiedDate;
	private String pfInvStatus;
	private Integer isInvitation;
	private Integer isInvExpired;
	private Integer isNotInterestedInv;
	private Date effectiveDate;
	private Boolean isMember;
	private Date providerFirmStateModifiedDate;

	public String getPfInvStatus() {
		return pfInvStatus;
	}
	public void setPfInvStatus(String pfInvStatus) {
		this.pfInvStatus = pfInvStatus;
	}
	public Date getInvModifiedDate() {
		return invModifiedDate;
	}
	public void setInvModifiedDate(Date invModifiedDate) {
		this.invModifiedDate = invModifiedDate;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public Date getCampStartDate() {
		return campStartDate;
	}
	public void setCampStartDate(Date campStartDate) {
		this.campStartDate = campStartDate;
	}
	public Date getCampEndDate() {
		return campEndDate;
	}
	public void setCampEndDate(Date campEndDate) {
		this.campEndDate = campEndDate;
	}
	public String getCommunicationSubject() {
		return communicationSubject;
	}
	public void setCommunicationSubject(String communicationSubject) {
		this.communicationSubject = communicationSubject;
	}
	public Integer getIsInvitation() {
		return isInvitation;
	}
	public void setIsInvitation(Integer isInvitation) {
		this.isInvitation = isInvitation;
	}
	public Integer getIsInvExpired() {
		return isInvExpired;
	}
	public void setIsInvExpired(Integer isInvExpired) {
		this.isInvExpired = isInvExpired;
	}
	public Integer getIsNotInterestedInv() {
		return isNotInterestedInv;
	}
	public void setIsNotInterestedInv(Integer isNotInterestedInv) {
		this.isNotInterestedInv = isNotInterestedInv;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Boolean getIsMember() {
		return isMember;
	}
	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}
	public Date getProviderFirmStateModifiedDate() {
		return providerFirmStateModifiedDate;
	}
	public void setProviderFirmStateModifiedDate(Date providerFirmStateModifiedDate) {
		this.providerFirmStateModifiedDate = providerFirmStateModifiedDate;
	}	
}