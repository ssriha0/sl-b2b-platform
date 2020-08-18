package com.servicelive.domain.so;

import java.io.Serializable;
import java.util.List;

import com.servicelive.domain.common.Contact;

public class BuyerOrderTemplate implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -620431419258510700L;
	private Integer templateRecordId;
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
    private Integer documentLogoId;
    private Integer confirmServiceTime;
    private List<BuyerOrderTemplatePart> parts;
    private Contact buyerContact;
    private Integer buyerResourceId;
    private Boolean isNewSpn;
    private Integer autoAccept;
	private Integer autoAcceptDays;
	private Integer autoAcceptTimes;

    public Integer getTemplateRecordId() {
        return templateRecordId;
    }

    public void setTemplateRecordId(Integer templateRecordId) {
        this.templateRecordId = templateRecordId;
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

    public Integer getDocumentLogoId() {
        return documentLogoId;
    }

    public void setDocumentLogoId(Integer documentLogoId) {
        this.documentLogoId = documentLogoId;
    }

    public Integer getConfirmServiceTime() {
        return confirmServiceTime;
    }

    public void setConfirmServiceTime(Integer confirmServiceTime) {
        this.confirmServiceTime = confirmServiceTime;
    }

    public List<BuyerOrderTemplatePart> getParts() {
        return parts;
    }

    public void setParts(List<BuyerOrderTemplatePart> parts) {
        this.parts = parts;
    }

    public Contact getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(Contact buyerContact) {
        this.buyerContact = buyerContact;
    }

    public Integer getBuyerResourceId() {
        return buyerResourceId;
    }

    public void setBuyerResourceId(Integer buyerResourceId) {
        this.buyerResourceId = buyerResourceId;
    }

    public Boolean getNewSpn() {
        return isNewSpn;
    }

    public void setNewSpn(Boolean newSpn) {
        isNewSpn = newSpn;
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
