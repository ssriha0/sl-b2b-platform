package com.newco.marketplace.dto;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;


/**
 * MainServiceCategory
Name
Parts Supplied By
Title
Overview
Terms
SpecialInstructions
Attachments (title)
BuyerContact
Parts
Auto Reschedule Accept
Reschedule Limit Days
Allowed # of Reschedules
 * @author gjackson
 *
 */
public class BuyerSOTemplateDTO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8373595236766007290L;
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
	private List<BuyerSOTemplatePartDTO> parts;
	private BuyerSOTemplateContactDTO buyerContact;
	private BuyerSOTemplateContactDTO altBuyerContact;
	private Boolean isNewSpn; 
	private Integer autoAccept;
	private Integer autoAcceptDays;
	private Integer autoAcceptTimes;	
	/**
	 * @return the altBuyerContact
	 */
	public BuyerSOTemplateContactDTO getAltBuyerContact() {
		return altBuyerContact;
	}
	/**
	 * @param altBuyerContact the altBuyerContact to set
	 */
	public void setAltBuyerContact(BuyerSOTemplateContactDTO altBuyerContact) {
		this.altBuyerContact = altBuyerContact;
	}
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
	public List<String> getDocumentTitles() {
		return documentTitles;
	}
	public void setDocumentTitles(List<String> documentTitles) {
		this.documentTitles = documentTitles;
	}
	public List<BuyerSOTemplatePartDTO> getParts() {
		return parts;
	}
	public void setParts(List<BuyerSOTemplatePartDTO> parts) {
		this.parts = parts;
	}
	public BuyerSOTemplateContactDTO getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(BuyerSOTemplateContactDTO buyerContact) {
		this.buyerContact = buyerContact;
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
	 * @return the altBuyerContactId
	 */
	public Integer getAltBuyerContactId() {
		return altBuyerContactId;
	}
	/**
	 * @param altBuyerContactId the altBuyerContactId to set
	 */
	public void setAltBuyerContactId(Integer altBuyerContactId) {
		this.altBuyerContactId = altBuyerContactId;
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
	 * @return the documentLogo
	 */
	public String getDocumentLogo() {
		return documentLogo;
	}
	/**
	 * @param documentLogo the documentLogo to set
	 */
	public void setDocumentLogo(String documentLogo) {
		this.documentLogo = documentLogo;
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

}
