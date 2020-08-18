package com.newco.marketplace.web.dto.buyerPII;

import java.util.ArrayList;

import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.IWarning;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;

public class BuyerPIIDTO extends SOWBaseTabDTO{


	private static final long serialVersionUID = 1L;
	
	private String einSsnInd;
	private String taxPayerId;
	private String confirmTaxPayerId;
	private String einTaxPayerId;
    private String ssnTaxPayerId;
    private String confirmSsnTaxPayerId;
    private String confirmEinTaxPayerId;    
	//private Boolean ssnIndex;
	private Integer piiIndex;
	//private String dob;
	private String ssnDob;
    private String altIdDob;
	private String documentType;
	private String documentIdNo;
	private String country;
	private Boolean ssnSaveInd;
	private String einTaxPayerIdHidden;
    private String ssnTaxPayerIdHidden;
    private String documentIdNoHidden;

	public String getEinSsnInd() {
		return einSsnInd;
	}
	public void setEinSsnInd(String einSsnInd) {
		this.einSsnInd = einSsnInd;
	}
	public String getTaxPayerId() {
		return taxPayerId;
	}
	public void setTaxPayerId(String taxPayerId) {
		this.taxPayerId = taxPayerId;
	}
	public String getConfirmTaxPayerId() {
		return confirmTaxPayerId;
	}
	public void setConfirmTaxPayerId(String confirmTaxPayerId) {
		this.confirmTaxPayerId = confirmTaxPayerId;
	}
	public String getEinTaxPayerId() {
		return einTaxPayerId;
	}
	public void setEinTaxPayerId(String einTaxPayerId) {
		this.einTaxPayerId = einTaxPayerId;
	}
	public String getSsnTaxPayerId() {
		return ssnTaxPayerId;
	}
	public void setSsnTaxPayerId(String ssnTaxPayerId) {
		this.ssnTaxPayerId = ssnTaxPayerId;
	}
	public String getConfirmSsnTaxPayerId() {
		return confirmSsnTaxPayerId;
	}
	public void setConfirmSsnTaxPayerId(String confirmSsnTaxPayerId) {
		this.confirmSsnTaxPayerId = confirmSsnTaxPayerId;
	}
	public String getConfirmEinTaxPayerId() {
		return confirmEinTaxPayerId;
	}
	public void setConfirmEinTaxPayerId(String confirmEinTaxPayerId) {
		this.confirmEinTaxPayerId = confirmEinTaxPayerId;
	}
	public Integer getPiiIndex() {
		return piiIndex;
	}
	public void setPiiIndex(Integer piiIndex) {
		this.piiIndex = piiIndex;
	}
	public String getSsnDob() {
		return ssnDob;
	}
	public void setSsnDob(String ssnDob) {
		this.ssnDob = ssnDob;
	}
	public String getAltIdDob() {
		return altIdDob;
	}
	public void setAltIdDob(String altIdDob) {
		this.altIdDob = altIdDob;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentIdNo() {
		return documentIdNo;
	}
	public void setDocumentIdNo(String documentIdNo) {
		this.documentIdNo = documentIdNo;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Boolean getSsnSaveInd() {
		return ssnSaveInd;
	}
	public void setSsnSaveInd(Boolean ssnSaveInd) {
		this.ssnSaveInd = ssnSaveInd;
	}
	
	public String getEinTaxPayerIdHidden() {
		return einTaxPayerIdHidden;
	}
	public void setEinTaxPayerIdHidden(String einTaxPayerIdHidden) {
		this.einTaxPayerIdHidden = einTaxPayerIdHidden;
	}
	public String getSsnTaxPayerIdHidden() {
		return ssnTaxPayerIdHidden;
	}
	public void setSsnTaxPayerIdHidden(String ssnTaxPayerIdHidden) {
		this.ssnTaxPayerIdHidden = ssnTaxPayerIdHidden;
	}
	public String getDocumentIdNoHidden() {
		return documentIdNoHidden;
	}
	public void setDocumentIdNoHidden(String documentIdNoHidden) {
		this.documentIdNoHidden = documentIdNoHidden;
	}
	@Override
	public String getTabIdentifier() {
		return "";
	}
	@Override
	public void validate() {
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		
	}
	
}
