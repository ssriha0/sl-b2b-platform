package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;

public class TemplateMaintenanceDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1045800825828321622L;
	
	
	private Boolean editTemplate = Boolean.FALSE;
	private Boolean disableAddTemplate = Boolean.FALSE;
	private Integer confirmServiceTime;
	private String templateName;
	private String overview;
	private String specialInstructions;
	private String buyerTandC;	
	private Map<Integer, String> partsSuppliedByList;
	private Integer partsSuppliedBy;
	private Map<Integer, String> buyerSOTemplateList;
	private Integer selectedBuyerSOTemplate;	
	private ArrayList<SkillNodeVO> mainServiceCategory;
	private Integer mainServiceCategoryId;	
	private Map<Integer, String> spnList;
	private Integer selectedSpn;
	private Integer spnPercentageMatch;
	private List<SOWAltBuyerContactDTO> altBuyerContactList;
	private Integer selectedAltContact;
	private Map<Integer, String> buyerDocumentList;
	private Map<Integer, String> selectedDocuments;
	private List<Integer> selectedBuyerDocument;
	private Map<Integer, String> buyerLogo;
	private Integer selectedBuyerLogo;
	private Boolean isSearsBuyer;
	private Map<Integer, String> searsSpnList;
	private Integer searsSelectedSpn;
	private Integer autoAccept;
	private Integer autoAcceptDays;
	private Integer autoAcceptTimes;	
	/**
	 * @return the altBuyerContactList
	 */
	public List<SOWAltBuyerContactDTO> getAltBuyerContactList() {
		return altBuyerContactList;
	}
	/**
	 * @param altBuyerContactList the altBuyerContactList to set
	 */
	public void setAltBuyerContactList(
			List<SOWAltBuyerContactDTO> altBuyerContactList) {
		this.altBuyerContactList = altBuyerContactList;
	}
	/**
	 * @return the buyerDocumentList
	 */
	public Map<Integer, String> getBuyerDocumentList() {
		return buyerDocumentList;
	}
	/**
	 * @param buyerDocumentList the buyerDocumentList to set
	 */
	public void setBuyerDocumentList(Map<Integer, String> buyerDocumentList) {
		this.buyerDocumentList = buyerDocumentList;
	}
	/**
	 * @return the buyerSOTemplateList
	 */
	public Map<Integer, String> getBuyerSOTemplateList() {
		return buyerSOTemplateList;
	}
	/**
	 * @param buyerSOTemplateList the buyerSOTemplateList to set
	 */
	public void setBuyerSOTemplateList(Map<Integer, String> buyerSOTemplateList) {
		this.buyerSOTemplateList = buyerSOTemplateList;
	}
	/**
	 * @return the buyerTandC
	 */
	public String getBuyerTandC() {
		return buyerTandC;
	}
	/**
	 * @param buyerTandC the buyerTandC to set
	 */
	public void setBuyerTandC(String buyerTandC) {
		this.buyerTandC = buyerTandC;
	}
	/**
	 * @return the editTemplate
	 */
	public Boolean getEditTemplate() {
		return editTemplate;
	}
	/**
	 * @param editTemplate the editTemplate to set
	 */
	public void setEditTemplate(Boolean editTemplate) {
		this.editTemplate = editTemplate;
	}
	/**
	 * @return the mainServiceCategory
	 */
	public ArrayList<SkillNodeVO> getMainServiceCategory() {
		return mainServiceCategory;
	}
	/**
	 * @param mainServiceCategory the mainServiceCategory to set
	 */
	public void setMainServiceCategory(ArrayList<SkillNodeVO> mainServiceCategory) {
		this.mainServiceCategory = mainServiceCategory;
	}
	/**
	 * @return the mainServiceCategoryId
	 */
	public Integer getMainServiceCategoryId() {
		return mainServiceCategoryId;
	}
	/**
	 * @param mainServiceCategoryId the mainServiceCategoryId to set
	 */
	public void setMainServiceCategoryId(Integer mainServiceCategoryId) {
		this.mainServiceCategoryId = mainServiceCategoryId;
	}
	/**
	 * @return the overview
	 */
	public String getOverview() {
		return overview;
	}
	/**
	 * @param overview the overview to set
	 */
	public void setOverview(String overview) {
		this.overview = overview;
	}
	/**
	 * @return the partsSuppliedBy
	 */
	public Integer getPartsSuppliedBy() {
		return partsSuppliedBy;
	}
	/**
	 * @param partsSuppliedBy the partsSuppliedBy to set
	 */
	public void setPartsSuppliedBy(Integer partsSuppliedBy) {
		this.partsSuppliedBy = partsSuppliedBy;
	}
	/**
	 * @return the partsSuppliedByList
	 */
	public Map<Integer, String> getPartsSuppliedByList() {
		return partsSuppliedByList;
	}
	/**
	 * @param partsSuppliedByList the partsSuppliedByList to set
	 */
	public void setPartsSuppliedByList(Map<Integer, String> partsSuppliedByList) {
		this.partsSuppliedByList = partsSuppliedByList;
	}
	/**
	 * @return the selectedAltContact
	 */
	public Integer getSelectedAltContact() {
		return selectedAltContact;
	}
	/**
	 * @param selectedAltContact the selectedAltContact to set
	 */
	public void setSelectedAltContact(Integer selectedAltContact) {
		this.selectedAltContact = selectedAltContact;
	}
	
	/**
	 * @return the selectedDocuments
	 */
	public Map<Integer, String> getSelectedDocuments() {
		return selectedDocuments;
	}
	/**
	 * @param selectedDocuments the selectedDocuments to set
	 */
	public void setSelectedDocuments(Map<Integer, String> selectedDocuments) {
		this.selectedDocuments = selectedDocuments;
	}
	/**
	 * @return the spnList
	 */
	public Map<Integer, String> getSpnList() {
		return spnList;
	}
	/**
	 * @param spnList the spnList to set
	 */
	public void setSpnList(Map<Integer, String> spnList) {
		this.spnList = spnList;
	}
	/**
	 * @return the spnPercentageMatch
	 */
	public Integer getSpnPercentageMatch() {
		return spnPercentageMatch;
	}
	/**
	 * @param spnPercentageMatch the spnPercentageMatch to set
	 */
	public void setSpnPercentageMatch(Integer spnPercentageMatch) {
		this.spnPercentageMatch = spnPercentageMatch;
	}
	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}
	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	/**
	 * @return the selectedBuyerSOTemplate
	 */
	public Integer getSelectedBuyerSOTemplate() {
		return selectedBuyerSOTemplate;
	}
	/**
	 * @param selectedBuyerSOTemplate the selectedBuyerSOTemplate to set
	 */
	public void setSelectedBuyerSOTemplate(Integer selectedBuyerSOTemplate) {
		this.selectedBuyerSOTemplate = selectedBuyerSOTemplate;
	}
	/**
	 * @return the selectedBuyerDocument
	 */
	public List<Integer> getSelectedBuyerDocument() {
		return selectedBuyerDocument;
	}
	/**
	 * @param selectedBuyerDocument the selectedBuyerDocument to set
	 */
	public void setSelectedBuyerDocument(List<Integer> selectedBuyerDocument) {
		this.selectedBuyerDocument = selectedBuyerDocument;
	}
	/**
	 * @return the selectedSpn
	 */
	public Integer getSelectedSpn() {
		return selectedSpn;
	}
	/**
	 * @param selectedSpn the selectedSpn to set
	 */
	public void setSelectedSpn(Integer selectedSpn) {
		this.selectedSpn = selectedSpn;
	}
	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	/**
	 * @return the disableAddTemplate
	 */
	public Boolean getDisableAddTemplate() {
		return disableAddTemplate;
	}
	/**
	 * @param disableAddTemplate the disableAddTemplate to set
	 */
	public void setDisableAddTemplate(Boolean disableAddTemplate) {
		this.disableAddTemplate = disableAddTemplate;
	}
	/**
	 * @return the buyerLogo
	 */
	public Map<Integer, String> getBuyerLogo() {
		return buyerLogo;
	}
	/**
	 * @param buyerLogo the buyerLogo to set
	 */
	public void setBuyerLogo(Map<Integer, String> buyerLogo) {
		this.buyerLogo = buyerLogo;
	}
	/**
	 * @return the confirmServiceTime
	 */
	public Integer getConfirmServiceTime() {
		return confirmServiceTime;
	}
	/**
	 * @param confirmServiceTime the confirmServiceTime to set
	 */
	public void setConfirmServiceTime(Integer confirmServiceTime) {
		this.confirmServiceTime = confirmServiceTime;
	}
	/**
	 * @return the selectedBuyerLogo
	 */
	public Integer getSelectedBuyerLogo() {
		return selectedBuyerLogo;
	}
	/**
	 * @param selectedBuyerLogo the selectedBuyerLogo to set
	 */
	public void setSelectedBuyerLogo(Integer selectedBuyerLogo) {
		this.selectedBuyerLogo = selectedBuyerLogo;
	}
	/**
	 * @return the isSearsBuyer
	 */
	public Boolean getIsSearsBuyer() {
		return isSearsBuyer;
	}
	/**
	 * @param isSearsBuyer the isSearsBuyer to set
	 */
	public void setIsSearsBuyer(Boolean isSearsBuyer) {
		this.isSearsBuyer = isSearsBuyer;
	}
	/**
	 * @return the searsSpnList
	 */
	public Map<Integer, String> getSearsSpnList() {
		return searsSpnList;
	}
	/**
	 * @param searsSpnList the searsSpnList to set
	 */
	public void setSearsSpnList(Map<Integer, String> searsSpnList) {
		this.searsSpnList = searsSpnList;
	}
	/**
	 * @return the searsSelectedSpn
	 */
	public Integer getSearsSelectedSpn() {
		return searsSelectedSpn;
	}
	/**
	 * @param searsSelectedSpn the searsSelectedSpn to set
	 */
	public void setSearsSelectedSpn(Integer searsSelectedSpn) {
		this.searsSelectedSpn = searsSelectedSpn;
	}
	public Integer getAutoAccept() {
		return autoAccept;
	}
	public void setAutoAccept(Integer autoAccept) {
		this.autoAccept = autoAccept;
	}
	public Integer getAutoAcceptDays() {
		return autoAcceptDays;
	}
	public void setAutoAcceptDays(Integer autoAcceptDays) {
		this.autoAcceptDays = autoAcceptDays;
	}
	public Integer getAutoAcceptTimes() {
		return autoAcceptTimes;
	}
	public void setAutoAcceptTimes(Integer autoAcceptTimes) {
		this.autoAcceptTimes = autoAcceptTimes;
	}
	
	
}
