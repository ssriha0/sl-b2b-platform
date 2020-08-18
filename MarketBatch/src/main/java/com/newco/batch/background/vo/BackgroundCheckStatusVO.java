package com.newco.batch.background.vo;

import org.apache.commons.lang.StringUtils;

public class BackgroundCheckStatusVO {

	

	String serviceOrganizationId;
	String techId;
	String techFname;
	String techLname;
	String overall;
	String crim;
	String driv;
	String cert;
	String verificationDate;
	String recertificationDate;
	String plusoneKey;
	String clientCompanyId;
	
	
	// additional columns
	
	String techMiddleName;
	String techSuffix;
	String recertificationInd;
	String drug="";
	String idVerification;
	String screeningStatus;
	
	
	
	public String getClientCompanyId() {
		return clientCompanyId;
	}
	public void setClientCompanyId(String clientCompanyId) {
		this.clientCompanyId = clientCompanyId;
	}
	public String getPlusoneKey() {
		return plusoneKey;
	}
	public void setPlusoneKey(String plusoneKey) {
		this.plusoneKey = plusoneKey;
	}
	public String getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}
	public String getRecertificationDate() {
		return recertificationDate;
	}
	public void setRecertificationDate(String recertificationDate) {
		this.recertificationDate = recertificationDate;
	}
	public String getCert() {
		return cert;
	}
	public void setCert(String cert) {
		this.cert = cert;
	}
	public String getCrim() {
		return crim;
	}
	public void setCrim(String crim) {
		this.crim = crim;
	}
	public String getDriv() {
		return driv;
	}
	public void setDriv(String driv) {
		this.driv = driv;
	}
	public String getOverall() {
		return overall;
	}
	public void setOverall(String overall) {
		this.overall = overall;
	}
	public String getServiceOrganizationId() {
		return serviceOrganizationId;
	}
	public void setServiceOrganizationId(String serviceOrganizationId) {
		this.serviceOrganizationId = serviceOrganizationId;
	}
	public String getTechFname() {
		return techFname;
	}
	public void setTechFname(String techFname) {
		this.techFname = techFname;
	}
	public String getTechId() {
		return techId;
	}
	public void setTechId(String techId) {
		this.techId = techId;
	}
	public String getTechLname() {
		return techLname;
	}
	public void setTechLname(String techLname) {
		this.techLname = techLname;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof BackgroundCheckStatusVO)) {
			return false;
		}		
		BackgroundCheckStatusVO o = (BackgroundCheckStatusVO) obj;
		return (this.serviceOrganizationId.equals(o.getServiceOrganizationId()) && 
				this.techId.equals(o.getTechId()) &&
				this.techFname.equals(o.getTechFname()) &&
				this.techLname.equals(o.getTechLname()) &&
				this.overall.equals(o.getOverall())  &&
				this.crim.equals(o.getCrim()) &&
				this.driv.equals(o.getDriv()) &&
				((StringUtils.isBlank(this.verificationDate) && StringUtils.isBlank(o.getVerificationDate()))
						|| this.verificationDate.equals(o.getVerificationDate())) &&
				((StringUtils.isBlank(this.verificationDate) && StringUtils.isBlank(o.getRecertificationDate())) 
						|| this.recertificationDate.equals(o.getRecertificationDate())) &&
				this.plusoneKey.equals(o.getPlusoneKey()) &&
				this.clientCompanyId.equals(o.getClientCompanyId()));
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("BackgroundCheckStatusVO: ");
		result.append("\nserviceOrganizationId=" + this.serviceOrganizationId);
		result.append("\ntechId=" +this.techId);
		result.append("\ntechFname=" +this.techFname);
		result.append("\ntechLname=" +this.techLname);
		result.append("\noverall=" +this.overall);
		result.append("\ncrim=" +this.crim);
		result.append("\ndriv=" +this.driv);
		result.append("\ncert=" +this.cert);
		result.append("\nverificationDate=" +this.verificationDate);
		result.append("\nrecertificationDate=" +this.recertificationDate);
		result.append("\nclientCompanyId=" +this.clientCompanyId);
		return result.toString();
	}
	
	
	public String getTechMiddleName() {
		return techMiddleName;
	}
	public void setTechMiddleName(String techMiddleName) {
		this.techMiddleName = techMiddleName;
	}
	public String getTechSuffix() {
		return techSuffix;
	}
	public void setTechSuffix(String techSuffix) {
		this.techSuffix = techSuffix;
	}
	public String getDrug() {
		return drug;
	}
	public void setDrug(String drug) {
		this.drug = drug;
	}
	public String getRecertificationInd() {
		return recertificationInd;
	}
	public void setRecertificationInd(String recertificationInd) {
		this.recertificationInd = recertificationInd;
	}
	public String getIdVerification() {
		return idVerification;
	}
	public void setIdVerification(String idVerification) {
		this.idVerification = idVerification;
	}
	public String getScreeningStatus() {
		return screeningStatus;
	}
	public void setScreeningStatus(String screeningStatus) {
		this.screeningStatus = screeningStatus;
	}
	
	
	
	
	
	
}
