package com.servicelive.spn.buyer.membermanagement;

import java.io.Serializable;

import com.servicelive.spn.buyer.campaign.SPNCreateCampaignApprovalModel;

public class SPNProviderDetailsNetworkDTO implements Serializable {

	private static final long serialVersionUID = 20100202L;

	private Integer networkId;
	private String networkName;
	private String status;
	private Integer networkGroup;
	private String networkDescription;
	// SL-12300 : Variable to store current network status id.
	private String statusId;
	private Boolean overrideInd;
	private Boolean hasAliasInd;
	private String spnEditEffectiveDate;
	private String spnOverrideEffectiveDate;
	
	//TODO we need to update this to something that is not from Campaign.
	// We can temporarily extend from the SPNCreateCampaignApprovalModel, but we should have our own object
	// and then later refactor campaign approval to use a different object.
	private SPNCreateCampaignApprovalModel approvalItems;

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public SPNCreateCampaignApprovalModel getApprovalItems() {
		return approvalItems;
	}

	public void setApprovalItems(SPNCreateCampaignApprovalModel approvalItems) {
		this.approvalItems = approvalItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNetworkGroup() {
		return networkGroup;
	}

	public void setNetworkGroup(Integer networkGroup) {
		this.networkGroup = networkGroup;
	}

	public Integer getNetworkId()
	{
		return networkId;
	}

	public void setNetworkId(Integer networkId)
	{
		this.networkId = networkId;
	}

	public String getNetworkDescription()
	{
		return networkDescription;
	}

	public void setNetworkDescription(String networkDescription)
	{
		this.networkDescription = networkDescription;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Boolean getOverrideInd() {
		return overrideInd;
	}

	public void setOverrideInd(Boolean overrideInd) {
		this.overrideInd = overrideInd;
	}

	public Boolean getHasAliasInd() {
		return hasAliasInd;
	}

	public void setHasAliasInd(Boolean hasAliasInd) {
		this.hasAliasInd = hasAliasInd;
	}

	public String getSpnEditEffectiveDate() {
		return spnEditEffectiveDate;
	}

	public void setSpnEditEffectiveDate(String spnEditEffectiveDate) {
		this.spnEditEffectiveDate = spnEditEffectiveDate;
	}

	public String getSpnOverrideEffectiveDate() {
		return spnOverrideEffectiveDate;
	}

	public void setSpnOverrideEffectiveDate(String spnOverrideEffectiveDate) {
		this.spnOverrideEffectiveDate = spnOverrideEffectiveDate;
	}


}
