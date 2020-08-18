package com.servicelive.domain.spn.campaign;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;

/**
 *
 */
@Entity
@Table ( name = "spnet_campaign_hdr")
public class CampaignHeader extends LoggableBaseDomain {
	private static final long serialVersionUID = -8141045032247143520L;

	@Id @GeneratedValue (strategy=IDENTITY)
	@Column (name = "campaign_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer campaignId;

	@Column (name = "campaign_name", unique = false, nullable = false, insertable = true, updatable = true, length = 250)
	private String campaignName;
	
	@Column(name= "start_date", nullable = false, insertable = true, updatable = true)
	private Date startDate;

	@Column(name= "end_date", nullable = false, insertable = true, updatable = true)
	private Date endDate;

	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name="campaign_id", unique = false, nullable = false, insertable=false, updatable=false)
	private List<CampaignApprovalCriteria> approvalCriterias = new ArrayList<CampaignApprovalCriteria> (0);
	
	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name="campaign_id", unique = false, nullable = false, insertable=false, updatable=false)
	private List<CampaignSPN> campaignSPN = new ArrayList<CampaignSPN> (0);

	@OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name="campaign_id", unique = false, nullable = false, insertable=true, updatable=true)
	private List<CampaignProviderFirm> providerFirms = new ArrayList<CampaignProviderFirm> (0);

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
	 * @return the approvalCriterias
	 */
	public List<CampaignApprovalCriteria> getApprovalCriterias() {
		return approvalCriterias;
	}

	/**
	 * @param approvalCriterias the approvalCriterias to set
	 */
	public void setApprovalCriterias(
			List<CampaignApprovalCriteria> approvalCriterias) {
		this.approvalCriterias = approvalCriterias;
	}
	
	/**
	 * @return the campaignSPN
	 */
	public List<CampaignSPN> getCampaignSPN() {
		return campaignSPN;
	}

	/**
	 * @param campaignSPN the campaignSPN to set
	 */
	public void setCampaignSPN(List<CampaignSPN> campaignSPN) {
		this.campaignSPN = campaignSPN;
	}

	public List<CampaignProviderFirm> getProviderFirms() {
		return providerFirms;
	}

	public void setProviderFirms(List<CampaignProviderFirm> providerFirms) {
		this.providerFirms = providerFirms;
	}

}
