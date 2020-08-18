package com.servicelive.spn.buyer.network;

import java.util.Date;


import com.servicelive.spn.buyer.common.SPNCreateModel;
/**
 * 
 * 
 *
 */
public class SPNCreateNetworkModel extends SPNCreateModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SPNCreateNetworkDocumentsVO uploadDocData = null;
	private Date effectiveDate;
	private String comments;
	private Boolean hasCampaign;
	private Integer memberStatusAffected;
	private Boolean auditRequired;
	private Boolean exceptionRequired;
	private Boolean initialExpInd;
	private String criteriaLevel;
	private String criteriaTimeframe;
	private String routingPriorityStatus;
	private Boolean marketPlaceOverFlow;
	//R11_0 SL_19387 For setting the feature set "BACKGROUND_CHECK_RECERTIFICATION"
	private Boolean recertificationBuyerFeatureInd;
	public Boolean getRecertificationBuyerFeatureInd() {
		return recertificationBuyerFeatureInd;
	}

	public void setRecertificationBuyerFeatureInd(
			Boolean recertificationBuyerFeatureInd) {
		this.recertificationBuyerFeatureInd = recertificationBuyerFeatureInd;
	}
	public Boolean getInitialExpInd() {
		return initialExpInd;
	}

	public void setInitialExpInd(Boolean initialExpInd) {
		this.initialExpInd = initialExpInd;
	}

	public Integer getMemberStatusAffected() {
		return memberStatusAffected;
	}

	public void setMemberStatusAffected(Integer memberStatusAffected) {
		this.memberStatusAffected = memberStatusAffected;
	}

	/**
	 * @return the uploadDocData
	 */
	public SPNCreateNetworkDocumentsVO getUploadDocData() {
		return uploadDocData;
	}

	/**
	 * @param uploadDocData the uploadDocData to set
	 */
	public void setUploadDocData(SPNCreateNetworkDocumentsVO uploadDocData) {
		this.uploadDocData = uploadDocData;
	}

	/**
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * 
	 * @param effectiveDate
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * 
	 * @return String
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * 
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getHasCampaign() {
		return hasCampaign;
	}

	/**
	 * 
	 * @param hasCampaign
	 */
	public void setHasCampaign(Boolean hasCampaign) {
		this.hasCampaign = hasCampaign;
	}

	public Boolean getAuditRequired() {
		return auditRequired;
	}

	public void setAuditRequired(Boolean auditRequired) {
		this.auditRequired = auditRequired;
	}

	public Boolean getExceptionRequired() {
		return exceptionRequired;
	}

	public void setExceptionRequired(Boolean exceptionRequired) {
		this.exceptionRequired = exceptionRequired;
	}

	public String getCriteriaLevel() {
		return criteriaLevel;
	}

	public void setCriteriaLevel(String criteriaLevel) {
		this.criteriaLevel = criteriaLevel;
	}

	public String getCriteriaTimeframe() {
		return criteriaTimeframe;
	}

	public void setCriteriaTimeframe(String criteriaTimeframe) {
		this.criteriaTimeframe = criteriaTimeframe;
	}

	public String getRoutingPriorityStatus() {
		return routingPriorityStatus;
	}

	public void setRoutingPriorityStatus(String routingPriorityStatus) {
		this.routingPriorityStatus = routingPriorityStatus;
	}

	public Boolean getMarketPlaceOverFlow() {
		return marketPlaceOverFlow;
	}

	public void setMarketPlaceOverFlow(Boolean marketPlaceOverFlow) {
		this.marketPlaceOverFlow = marketPlaceOverFlow;
	}


}
