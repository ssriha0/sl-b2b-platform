/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.servicelive.domain.spn.detached.ApprovalModel;

/**
 * @author hoza
 *
 */
public class ProviderMatchApprovalCriteriaVO implements Serializable {

	
	private static final long serialVersionUID = 20090317;

	private ApprovalModel model = new ApprovalModel();
	private String skillsREGEXP = null;
	private String skillsCategoryREGEXP = null;
	private String serviceTypeREGEXP = null;

	private List<Integer> languageIds = null;
	private List<Integer> providerFirmCredentialIds = null;
	private List<Integer> serviceProviderCredentialIds = null;
	private List<Integer> providerFirmCredCategoryIds = null;
	private List<Integer> serviceProviderCategoryIds = null;

	private String languagesREGEXP = null;
	private String providerFirmCredentialsREGEXP = null;
	private String serviceProviderCredentialsREGEXP = null;
	private String providerFirmCredCategoryREGEXP = null;
	private String serviceProviderCategoryREGEXP = null;

	private String documentREGEXP = null;
	private String electronicDocumentREGEXP = null;
	//following are used for Mapping in Ibatis
	private BigDecimal vehicleLiabilityAmtBD;
	private BigDecimal commercialGeneralLiabilityAmtBD;
	//Campaign specific things
	private Integer spnId;
	private Integer campaignId; 
	private Boolean isAdminSearch = null;
	private Integer specificProviderFirmId = null; //this is supplied for checking if Specific provider Firm  Member maintenance
	private List<Integer> skillsList = null;
	private List<Integer> serviceTypeList = null;
	private List<Integer> skillsCategoryList = null;
	//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	//Add new list attribute to hold the primary industry values in the VO
	private List<Integer> primaryIndustryList = null;
	private Boolean recertification;
	
	//R10.3 SL-20134, variable for prepending AND to wf_state condition, which is always executed
	//Because of R10.3 CR SL-20126, the skillsList condition is excluded if primary industry is present.
	//In skillsList condition, an additional prepend="AND" condition is present, which is first AND excluded inside the dynamic WHERE.
	//As part of CR SL-20126, if primary industry is present, then need to exclude the skillsList, 
	//hence ibatis is expecting two prepend "AND" in the very next static criteria, which is not present, gave way to  SL-20134.
	//This variable is given as an always true and condition wf_states.
	private String dummyVariableForPrependAnd = null;
	/**
	 * @return the dummyVariableForPrependAnd
	 */
	public String getDummyVariableForPrependAnd() {
		return dummyVariableForPrependAnd;
	}

	/**
	 * @param dummyVariableForPrependAnd
	 */
	public void setDummyVariableForPrependAnd(String test) {
		this.dummyVariableForPrependAnd = test;
	}

	
	public ProviderMatchApprovalCriteriaVO(ApprovalModel model) {
		this.model = model;
	}	
	
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
	/**
	 * @return the providerFirmCredentialsREGEXP
	 */
	public String getProviderFirmCredentialsREGEXP() {
		return providerFirmCredentialsREGEXP;
	}
	/**
	 * @param providerFirmCredentialsREGEXP the providerFirmCredentialsREGEXP to set
	 */
	public void setProviderFirmCredentialsREGEXP(
			String providerFirmCredentialsREGEXP) {
		this.providerFirmCredentialsREGEXP = providerFirmCredentialsREGEXP;
	}
	/**
	 * @return the serviceProviderCredentialsREGEXP
	 */
	public String getServiceProviderCredentialsREGEXP() {
		return serviceProviderCredentialsREGEXP;
	}
	/**
	 * @param serviceProviderCredentialsREGEXP the serviceProviderCredentialsREGEXP to set
	 */
	public void setServiceProviderCredentialsREGEXP(
			String serviceProviderCredentialsREGEXP) {
		this.serviceProviderCredentialsREGEXP = serviceProviderCredentialsREGEXP;
	}
	/**
	 * @return the providerFirmCredCategoryREGEXP
	 */
	public String getProviderFirmCredCategoryREGEXP() {
		return providerFirmCredCategoryREGEXP;
	}
	/**
	 * @param providerFirmCredCategoryREGEXP the providerFirmCredCategoryREGEXP to set
	 */
	public void setProviderFirmCredCategoryREGEXP(
			String providerFirmCredCategoryREGEXP) {
		this.providerFirmCredCategoryREGEXP = providerFirmCredCategoryREGEXP;
	}
	/**
	 * @return the serviceProviderCategoryREGEXP
	 */
	public String getServiceProviderCategoryREGEXP() {
		return serviceProviderCategoryREGEXP;
	}
	/**
	 * @param serviceProviderCategoryREGEXP the serviceProviderCategoryREGEXP to set
	 */
	public void setServiceProviderCategoryREGEXP(
			String serviceProviderCategoryREGEXP) {
		this.serviceProviderCategoryREGEXP = serviceProviderCategoryREGEXP;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSkillsCategoryREGEXP() {
		return skillsCategoryREGEXP;
	}
	/**
	 * 
	 * @param skillsCategoryREGEXP
	 */
	public void setSkillsCategoryREGEXP(String skillsCategoryREGEXP) {
		this.skillsCategoryREGEXP = skillsCategoryREGEXP;
	}
	/**
	 * @return the documentREGEXP
	 */
	public String getDocumentREGEXP() {
		return documentREGEXP;
	}
	/**
	 * @param documentREGEXP the documentREGEXP to set
	 */
	public void setDocumentREGEXP(String documentREGEXP) {
		this.documentREGEXP = documentREGEXP;
	}
	/**
	 * @return the electronicDocumentREGEXP
	 */
	public String getElectronicDocumentREGEXP() {
		return electronicDocumentREGEXP;
	}
	/**
	 * @param electronicDocumentREGEXP the electronicDocumentREGEXP to set
	 */
	public void setElectronicDocumentREGEXP(String electronicDocumentREGEXP) {
		this.electronicDocumentREGEXP = electronicDocumentREGEXP;
	}

	/**
	 * @return the skillsList
	 */
	public List<Integer> getSkillsList() {
		return skillsList;
	}

	/**
	 * @param skillsList the skillsList to set
	 */
	public void setSkillsList(List<Integer> skillsList) {
		this.skillsList = skillsList;
	}

	/**
	 * @return the serviceTypeList
	 */
	public List<Integer> getServiceTypeList() {
		return serviceTypeList;
	}

	/**
	 * @param serviceTypeList the serviceTypeList to set
	 */
	public void setServiceTypeList(List<Integer> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}

	/**
	 * @return the skillsCategoryList
	 */
	public List<Integer> getSkillsCategoryList() {
		return skillsCategoryList;
	}

	/**
	 * @param skillsCategoryList the skillsCategoryList to set
	 */
	public void setSkillsCategoryList(List<Integer> skillsCategoryList) {
		this.skillsCategoryList = skillsCategoryList;
	}

	/**
	 * @return the languageIds
	 */
	public List<Integer> getLanguageIds() {
		return languageIds;
	}

	/**
	 * @param languageIds the languageIds to set
	 */
	public void setLanguageIds(List<Integer> languageIds) {
		this.languageIds = languageIds;
	}

	/**
	 * @return the providerFirmCredentialIds
	 */
	public List<Integer> getProviderFirmCredentialIds() {
		return providerFirmCredentialIds;
	}

	/**
	 * @param providerFirmCredentialIds the providerFirmCredentialIds to set
	 */
	public void setProviderFirmCredentialIds(List<Integer> providerFirmCredentialIds) {
		this.providerFirmCredentialIds = providerFirmCredentialIds;
	}

	/**
	 * @return the serviceProviderCredentialIds
	 */
	public List<Integer> getServiceProviderCredentialIds() {
		return serviceProviderCredentialIds;
	}

	/**
	 * @param serviceProviderCredentialIds the serviceProviderCredentialIds to set
	 */
	public void setServiceProviderCredentialIds(List<Integer> serviceProviderCredentialIds) {
		this.serviceProviderCredentialIds = serviceProviderCredentialIds;
	}

	/**
	 * @return the providerFirmCredCategoryIds
	 */
	public List<Integer> getProviderFirmCredCategoryIds() {
		return providerFirmCredCategoryIds;
	}

	/**
	 * @param providerFirmCredCategoryIds the providerFirmCredCategoryIds to set
	 */
	public void setProviderFirmCredCategoryIds(List<Integer> providerFirmCredCategoryIds) {
		this.providerFirmCredCategoryIds = providerFirmCredCategoryIds;
	}

	/**
	 * @return the serviceProviderCategoryIds
	 */
	public List<Integer> getServiceProviderCategoryIds() {
		return serviceProviderCategoryIds;
	}

	/**
	 * @param serviceProviderCategoryIds the serviceProviderCategoryIds to set
	 */
	public void setServiceProviderCategoryIds(List<Integer> serviceProviderCategoryIds) {
		this.serviceProviderCategoryIds = serviceProviderCategoryIds;
	}

	/**
	 * @return the specificProviderFirmId
	 */
	public Integer getSpecificProviderFirmId() {
		return specificProviderFirmId;
	}

	/**
	 * @param specificProviderFirmId the specificProviderFirmId to set
	 */
	public void setSpecificProviderFirmId(Integer specificProviderFirmId) {
		this.specificProviderFirmId = specificProviderFirmId;
	}

	/**
	 * @return the primaryIndustryList
	 */
	public List<Integer> getPrimaryIndustryList() {
		return primaryIndustryList;
	}

	/**
	 * @param primaryIndustryList the primaryIndustryList to set
	 */
	public void setPrimaryIndustryList(List<Integer> primaryIndustryList) {
		this.primaryIndustryList = primaryIndustryList;
	}

	public Boolean getRecertification() {
		return recertification;
	}

	public void setRecertification(Boolean recertification) {
		this.recertification = recertification;
	}
	
	
	
}
