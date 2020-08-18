package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("completeProfile")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompleteProfile {

	@XStreamAlias("businessInformation")
	private BusinessInformation businessInformation; 
	
	@XStreamAlias("companyOverview")
	private	CompanyOverview companyOverview;
	
	@XStreamAlias("businessAddress")
	private	BusinessAddress businessAddress;
	
	@XStreamAlias("primaryContactInformation")
	private	PrimaryContactInformation primaryContactInformation;
	
	@XStreamAlias("warrantyInformation")
	private	WarrantyInformation warrantyInformation;
	
	@XStreamAlias("workPolicyInformation")
	private WorkPolicyInformation workPolicyInformation;
	
	@XStreamAlias("licenseAndCertificationsFile")
	private	LicenseAndCertificationsFile licenseAndCertificationsFile;
	
	@XStreamAlias("insurancePoliciesOnFile")
	private	InsurancePoliciesOnFile insurancePoliciesOnFile;
	
	
	public BusinessInformation getBusinessInformation() {
		return businessInformation;
	}

	public void setBusinessInformation(BusinessInformation businessInformation) {
		this.businessInformation = businessInformation;
	}

	public CompanyOverview getCompanyOverview() {
		return companyOverview;
	}

	public void setCompanyOverview(CompanyOverview companyOverview) {
		this.companyOverview = companyOverview;
	}

	public BusinessAddress getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(BusinessAddress businessAddress) {
		this.businessAddress = businessAddress;
	}

	public PrimaryContactInformation getPrimaryContactInformation() {
		return primaryContactInformation;
	}

	public void setPrimaryContactInformation(
			PrimaryContactInformation primaryContactInformation) {
		this.primaryContactInformation = primaryContactInformation;
	}

	public WarrantyInformation getWarrantyInformation() {
		return warrantyInformation;
	}

	public void setWarrantyInformation(WarrantyInformation warrantyInformation) {
		this.warrantyInformation = warrantyInformation;
	}

	public LicenseAndCertificationsFile getLicenseAndCertificationsFile() {
		return licenseAndCertificationsFile;
	}

	public void setLicenseAndCertificationsFile(
			LicenseAndCertificationsFile licenseAndCertificationsFile) {
		this.licenseAndCertificationsFile = licenseAndCertificationsFile;
	}


	public InsurancePoliciesOnFile getInsurancePoliciesOnFile() {
		return insurancePoliciesOnFile;
	}

	public void setInsurancePoliciesOnFile(
			InsurancePoliciesOnFile insurancePoliciesOnFile) {
		this.insurancePoliciesOnFile = insurancePoliciesOnFile;
	}

	
	public WorkPolicyInformation getWorkPolicyInformation() {
		return workPolicyInformation;
	}

	public void setWorkPolicyInformation(WorkPolicyInformation workPolicyInformation) {
		this.workPolicyInformation = workPolicyInformation;
	}
		
}
