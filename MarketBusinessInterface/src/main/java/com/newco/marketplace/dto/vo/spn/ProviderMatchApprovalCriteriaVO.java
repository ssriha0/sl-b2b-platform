/**
 * 
 */
package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;
import java.math.BigDecimal;

import com.newco.marketplace.dto.vo.spn.ApprovalModel; 

/**
 * @author hoza
 *
 */
public class ProviderMatchApprovalCriteriaVO implements Serializable {

	/**
	 * 
	 */
	private ApprovalModel model = new ApprovalModel();
	private static final long serialVersionUID = -2305103794258886371L;
	private String skillsREGEXP = null;
	private String serviceTypeREGEXP = null;
	private String languagesREGEXP = null;
	//following are used for Mapping in Ibatis
	private BigDecimal vehicleLiabilityAmtBD;
	private BigDecimal commercialGeneralLiabilityAmtBD;
	//Campaign specific things
	private Integer spnId;
	private Integer campaignId; 
	private Integer vendorId;  
	private Boolean isAdminSearch = null;
	
	
	/**
	 * @return the model
	 */
	public ApprovalModel getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(ApprovalModel model) {
		this.model = model;
	}
	/**
	 * @return the skillsREGEXP
	 */
	public String getSkillsREGEXP() {
		return skillsREGEXP;
	}
	/**
	 * @param skillsREGEXP the skillsREGEXP to set
	 */
	public void setSkillsREGEXP(String skillsREGEXP) {
		this.skillsREGEXP = skillsREGEXP;
	}
	/**
	 * @return the serviceTypeREGEXP
	 */
	public String getServiceTypeREGEXP() {
		return serviceTypeREGEXP;
	}
	/**
	 * @param serviceTypeREGEXP the serviceTypeREGEXP to set
	 */
	public void setServiceTypeREGEXP(String serviceTypeREGEXP) {
		this.serviceTypeREGEXP = serviceTypeREGEXP;
	}
	/**
	 * @return the vehicleLiabilityAmtBD
	 */
	public BigDecimal getVehicleLiabilityAmtBD() {
		return vehicleLiabilityAmtBD;
	}
	/**
	 * @param vehicleLiabilityAmtBD the vehicleLiabilityAmtBD to set
	 */
	public void setVehicleLiabilityAmtBD(BigDecimal vehicleLiabilityAmtBD) {
		this.vehicleLiabilityAmtBD = vehicleLiabilityAmtBD;
	}
	/**
	 * @return the commercialGeneralLiabilityAmtBD
	 */
	public BigDecimal getCommercialGeneralLiabilityAmtBD() {
		return commercialGeneralLiabilityAmtBD;
	}
	/**
	 * @param commercialGeneralLiabilityAmtBD the commercialGeneralLiabilityAmtBD to set
	 */
	public void setCommercialGeneralLiabilityAmtBD(
			BigDecimal commercialGeneralLiabilityAmtBD) {
		this.commercialGeneralLiabilityAmtBD = commercialGeneralLiabilityAmtBD;
	}
	/**
	 * @return the languagesREGEXP
	 */
	public String getLanguagesREGEXP() {
		return languagesREGEXP;
	}
	/**
	 * @param languagesREGEXP the languagesREGEXP to set
	 */
	public void setLanguagesREGEXP(String languagesREGEXP) {
		this.languagesREGEXP = languagesREGEXP;
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
	 * @return the isAdminSearch
	 */
	public Boolean getIsAdminSearch() {
		return isAdminSearch;
	}
	/**
	 * @param isAdminSearch the isAdminSearch to set
	 */
	public void setIsAdminSearch(Boolean isAdminSearch) {
		this.isAdminSearch = isAdminSearch;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	
	

}
