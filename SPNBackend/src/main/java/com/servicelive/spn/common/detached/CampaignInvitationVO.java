package com.servicelive.spn.common.detached;

import java.util.Date;

import com.servicelive.domain.BaseDomain;

/**
 * 
 * @author SVANLOO
 *
 */
public class CampaignInvitationVO  extends BaseDomain {

	private static final long serialVersionUID = 20081215L;

	private Integer campaignId;

	private String campaignName;
	
	private Date endDate;

	private Date startDate;
	
	private String state;

	private Integer spnId;
	private Integer buyerId;

	private String spnCompanyName;
	private String spnName;
	private String spnContactName;
	private String spnContactPhone;
	private String spnContactEmail;
	private Boolean isSpecificFirmCampaign;
	
	private Date invModifiedDate;
	private String pfInvStatus;

	public Date getInvModifiedDate() {
		return invModifiedDate;
	}

	public void setInvModifiedDate(Date invModifiedDate) {
		this.invModifiedDate = invModifiedDate;
	}

	public String getPfInvStatus() {
		return pfInvStatus;
	}

	public void setPfInvStatus(String pfInvStatus) {
		this.pfInvStatus = pfInvStatus;
	}

	/**
	 * 
	 */
	public CampaignInvitationVO() {
		super();
	}

	/**
	 * @return the campaignId
	 */
	public Integer getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId the campaignId to set
	 */
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the campaignName
	 */
	public String getCampaignName() {
		return campaignName;
	}

	/**
	 * @param campaignName the campaignName to set
	 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}

	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	/**
	 * @return the spnCompanyName
	 */
	public String getSpnCompanyName() {
		return spnCompanyName;
	}

	/**
	 * @param spnCompanyName the spnCompanyName to set
	 */
	public void setSpnCompanyName(String spnCompanyName) {
		this.spnCompanyName = spnCompanyName;
	}

	/**
	 * @return the spnName
	 */
	public String getSpnName() {
		return spnName;
	}

	/**
	 * @param spnName the spnName to set
	 */
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}

	/**
	 * @return the spnContactName
	 */
	public String getSpnContactName() {
		return spnContactName;
	}

	/**
	 * @param spnContactName the spnContactName to set
	 */
	public void setSpnContactName(String spnContactName) {
		this.spnContactName = spnContactName;
	}

	/**
	 * @return the spnContactPhone
	 */
	public String getSpnContactPhone() {
		return spnContactPhone;
	}

	/**
	 * @param spnContactPhone the spnContactPhone to set
	 */
	public void setSpnContactPhone(String spnContactPhone) {
		this.spnContactPhone = spnContactPhone;
	}

	/**
	 * @return the spnContactEmail
	 */
	public String getSpnContactEmail() {
		return spnContactEmail;
	}

	/**
	 * @param spnContactEmail the spnContactEmail to set
	 */
	public void setSpnContactEmail(String spnContactEmail) {
		this.spnContactEmail = spnContactEmail;
	}

	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the isSpecificFirmCampaign
	 */
	public Boolean getIsSpecificFirmCampaign() {
		return isSpecificFirmCampaign;
	}

	/**
	 * @param isSpecificFirmCampaign the isSpecificFirmCampaign to set
	 */
	public void setIsSpecificFirmCampaign(Boolean isSpecificFirmCampaign) {
		this.isSpecificFirmCampaign = isSpecificFirmCampaign;
	}
}
