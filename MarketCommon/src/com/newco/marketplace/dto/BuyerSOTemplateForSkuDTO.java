package com.newco.marketplace.dto;

import java.util.List;
/* DTO class to map all the information from XML to java class for the template Id
* @author Infosys 
*/
public class BuyerSOTemplateForSkuDTO {
	private String mainServiceCategory;
	private String title;
	private Integer partsSuppliedBy;
	private String overview;
	private String terms;
	private String specialInstructions;
	private Integer spnId;
	private Integer spnPercentageMatch;
	private Integer altBuyerContactId;
	private List<String> documentTitles;
	private String documentLogo;
	private Integer confirmServiceTime;
	private Boolean isNewSpn; 
	private Integer autoAccept;
	private Integer autoAcceptDays;
	private Integer autoAcceptTimes;
	//SL-21355 : Added for Holding selected buyer Logo
	private Integer buyerDocumentLogo;
	public String getMainServiceCategory() {
		return mainServiceCategory;
	}
	public void setMainServiceCategory(String mainServiceCategory) {
		this.mainServiceCategory = mainServiceCategory;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPartsSuppliedBy() {
		return partsSuppliedBy;
	}
	public void setPartsSuppliedBy(Integer partsSuppliedBy) {
		this.partsSuppliedBy = partsSuppliedBy;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Integer getSpnPercentageMatch() {
		return spnPercentageMatch;
	}
	public void setSpnPercentageMatch(Integer spnPercentageMatch) {
		this.spnPercentageMatch = spnPercentageMatch;
	}
	public Integer getAltBuyerContactId() {
		return altBuyerContactId;
	}
	public void setAltBuyerContactId(Integer altBuyerContactId) {
		this.altBuyerContactId = altBuyerContactId;
	}
	public List<String> getDocumentTitles() {
		return documentTitles;
	}
	public void setDocumentTitles(List<String> documentTitles) {
		this.documentTitles = documentTitles;
	}
	public String getDocumentLogo() {
		return documentLogo;
	}
	public void setDocumentLogo(String documentLogo) {
		this.documentLogo = documentLogo;
	}
	public Integer getConfirmServiceTime() {
		return confirmServiceTime;
	}
	public void setConfirmServiceTime(Integer confirmServiceTime) {
		this.confirmServiceTime = confirmServiceTime;
	}
	public Boolean getIsNewSpn() {
		return isNewSpn;
	}
	public void setIsNewSpn(Boolean isNewSpn) {
		this.isNewSpn = isNewSpn;
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
	public Integer getBuyerDocumentLogo() {
		return buyerDocumentLogo;
	}
	public void setBuyerDocumentLogo(Integer buyerDocumentLogo) {
		this.buyerDocumentLogo = buyerDocumentLogo;
	}
	

}
