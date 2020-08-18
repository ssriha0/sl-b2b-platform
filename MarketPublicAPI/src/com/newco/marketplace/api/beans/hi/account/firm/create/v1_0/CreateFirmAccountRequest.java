

package com.newco.marketplace.api.beans.hi.account.firm.create.v1_0;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import com.newco.marketplace.api.beans.hi.account.firm.v1_0.BusinessAddress;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.InsuranceDetails;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.LicenseAndCertifications;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.MailingAddress;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.NameDetails;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.Roles;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.W9Information;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.RoleWithinCom;


@XSD(name="createFirmAccountRequest.xsd", path="/resources/schemas/ums/")
@XmlRootElement(name="createFirmAccountRequest")
@XStreamAlias("createFirmAccountRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateFirmAccountRequest {

	@XStreamAlias("legalBusinessName")
    private String legalBusinessName;
	
	@XStreamAlias("dBAName")
    private String dBAName;
	
	@XStreamAlias("primaryIndustry")
    private String primaryIndustry;
	
	@XStreamAlias("businessStartedDate")
    private String businessStartedDate;
	
	@XStreamAlias("businessStructure")
    private String businessStructure;
	
	@XStreamAlias("dunsNumber")
    private String dunsNumber;
	
	@XStreamAlias("foreignOwnedPercentage")
    private String foreignOwnedPercentage;
	
	@XStreamAlias("companySize")
    private String companySize;
	
	@XStreamAlias("annualSalesRevenue")
    private String annualSalesRevenue;
	
	@XStreamAlias("websiteAddress")
    private String websiteAddress;
    
	@XStreamAlias("businessDesc")
    private String businessDesc;
	
	@XStreamAlias("mainBusiPhoneNo")
    private String mainBusiPhoneNo;
    
	@XStreamAlias("mainBusinessExtn")
    private String mainBusinessExtn;
	
	@XStreamAlias("businessFax")
    private String businessFax;
	
	@XStreamAlias("businessAddress")
	@XmlElement(name="businessAddress")
    private BusinessAddress businessAddress;
	
	@XStreamAlias("mailingAddress")
	@XmlElement(name="mailingAddress")
    private MailingAddress mailingAddress;
	
	@XStreamAlias("roleWithinCom")
    private String roleWithinCom;
	
	@XStreamAlias("roles")
	@XmlElement(name="roles")
    private Roles roles;
	
	@XStreamAlias("jobTitle")
    private String jobTitle;
	
	@XStreamAlias("nameDetails")
	@XmlElement(name="nameDetails")
    private NameDetails nameDetails;

	@XStreamAlias("email")
    private String email;

	@XStreamAlias("altEmail")
    private String altEmail;
	
	@XStreamAlias("userName")
    private String userName;
	
	@XStreamAlias("howDidYouHear")
    private String howDidYouHear;
	
	@XStreamAlias("referralCode")
    private String referralCode;
	
	@XStreamAlias("serviceCall")
	private Integer serviceCall;
	
	@XStreamAlias("projectEstimatesChargeInd")
    private Integer projectEstimatesChargeInd;
	
	@XStreamAlias("warrantyOnLabor")
    private String warrantyOnLabor;
	
	@XStreamAlias("warrantyOnParts")
    private String warrantyOnParts;
	
	@XStreamAlias("drugTestingPolicyInd")
    private Integer drugTestingPolicyInd;
	
	@XStreamAlias("drugTestingPolicyRequired")
    private Integer drugTestingPolicyRequired;
	
	@XStreamAlias("workEnvironmentPolicyInd")
    private Integer workEnvironmentPolicyInd;
	
	@XStreamAlias("workEnvironmentPolicyRequired")
    private Integer workEnvironmentPolicyRequired;
	
	@XStreamAlias("employeeCitizenShipProofInd")
    private Integer employeeCitizenShipProofInd;
	
	@XStreamAlias("employeeCitizenShipProofRequired")
    private Integer employeeCitizenShipProofRequired;
	
	@XStreamAlias("crewWearBadgesInd")
    private Integer crewWearBadgesInd;
	
	@XStreamAlias("crewWearBadgesRequired")
    private Integer crewWearBadgesRequired;
	
	@XStreamAlias("licenseAndCertifications")
	@XmlElement(name="licenseAndCertifications")
    private LicenseAndCertifications licenseAndCertifications;
	
	@XStreamAlias("insuranceDetails")
	@XmlElement(name="insuranceDetails")
    private InsuranceDetails insuranceDetails;
	
	@XStreamAlias("w9Information")
	@XmlElement(name="w9Information")
    private W9Information w9Information;
	
	@XStreamAlias("firmType")
    private String firmType;
  
	@XStreamAlias("subContractorId")
    private String subContractorId;

	public String getLegalBusinessName() {
		return legalBusinessName;
	}

	public void setLegalBusinessName(String legalBusinessName) {
		this.legalBusinessName = legalBusinessName;
	}

	public String getdBAName() {
		return dBAName;
	}

	public void setdBAName(String dBAName) {
		this.dBAName = dBAName;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public String getBusinessStartedDate() {
		return businessStartedDate;
	}

	public void setBusinessStartedDate(String businessStartedDate) {
		this.businessStartedDate = businessStartedDate;
	}

	public String getBusinessStructure() {
		return businessStructure;
	}

	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}

	public String getDunsNumber() {
		return dunsNumber;
	}

	public void setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
	}

	public String getForeignOwnedPercentage() {
		return foreignOwnedPercentage;
	}

	public void setForeignOwnedPercentage(String foreignOwnedPercentage) {
		this.foreignOwnedPercentage = foreignOwnedPercentage;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getAnnualSalesRevenue() {
		return annualSalesRevenue;
	}

	public void setAnnualSalesRevenue(String annualSalesRevenue) {
		this.annualSalesRevenue = annualSalesRevenue;
	}

	public String getWebsiteAddress() {
		return websiteAddress;
	}

	public void setWebsiteAddress(String websiteAddress) {
		this.websiteAddress = websiteAddress;
	}

	public String getBusinessDesc() {
		return businessDesc;
	}

	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}

	public String getMainBusiPhoneNo() {
		return mainBusiPhoneNo;
	}

	public void setMainBusiPhoneNo(String mainBusiPhoneNo) {
		this.mainBusiPhoneNo = mainBusiPhoneNo;
	}

	public String getMainBusinessExtn() {
		return mainBusinessExtn;
	}

	public void setMainBusinessExtn(String mainBusinessExtn) {
		this.mainBusinessExtn = mainBusinessExtn;
	}

	public String getBusinessFax() {
		return businessFax;
	}

	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}

	public BusinessAddress getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(BusinessAddress businessAddress) {
		this.businessAddress = businessAddress;
	}

	public MailingAddress getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(MailingAddress mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getRoleWithinCom() {
		return roleWithinCom;
	}

	public void setRoleWithinCom(String roleWithinCom) {
		this.roleWithinCom = roleWithinCom;
	}

	
	
	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public NameDetails getNameDetails() {
		return nameDetails;
	}

	public void setNameDetails(NameDetails nameDetails) {
		this.nameDetails = nameDetails;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHowDidYouHear() {
		return howDidYouHear;
	}

	public void setHowDidYouHear(String howDidYouHear) {
		this.howDidYouHear = howDidYouHear;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public Integer getServiceCall() {
		return serviceCall;
	}

	public void setServiceCall(Integer serviceCall) {
		this.serviceCall = serviceCall;
	}

	public Integer getProjectEstimatesChargeInd() {
		return projectEstimatesChargeInd;
	}

	public void setProjectEstimatesChargeInd(Integer projectEstimatesChargeInd) {
		this.projectEstimatesChargeInd = projectEstimatesChargeInd;
	}

	public String getWarrantyOnLabor() {
		return warrantyOnLabor;
	}

	public void setWarrantyOnLabor(String warrantyOnLabor) {
		this.warrantyOnLabor = warrantyOnLabor;
	}

	public String getWarrantyOnParts() {
		return warrantyOnParts;
	}

	public void setWarrantyOnParts(String warrantyOnParts) {
		this.warrantyOnParts = warrantyOnParts;
	}

	public Integer getDrugTestingPolicyInd() {
		return drugTestingPolicyInd;
	}

	public void setDrugTestingPolicyInd(Integer drugTestingPolicyInd) {
		this.drugTestingPolicyInd = drugTestingPolicyInd;
	}

	public Integer getDrugTestingPolicyRequired() {
		return drugTestingPolicyRequired;
	}

	public void setDrugTestingPolicyRequired(Integer drugTestingPolicyRequired) {
		this.drugTestingPolicyRequired = drugTestingPolicyRequired;
	}

	public Integer getWorkEnvironmentPolicyInd() {
		return workEnvironmentPolicyInd;
	}

	public void setWorkEnvironmentPolicyInd(Integer workEnvironmentPolicyInd) {
		this.workEnvironmentPolicyInd = workEnvironmentPolicyInd;
	}

	public Integer getWorkEnvironmentPolicyRequired() {
		return workEnvironmentPolicyRequired;
	}

	public void setWorkEnvironmentPolicyRequired(
			Integer workEnvironmentPolicyRequired) {
		this.workEnvironmentPolicyRequired = workEnvironmentPolicyRequired;
	}

	public Integer getEmployeeCitizenShipProofInd() {
		return employeeCitizenShipProofInd;
	}

	public void setEmployeeCitizenShipProofInd(Integer employeeCitizenShipProofInd) {
		this.employeeCitizenShipProofInd = employeeCitizenShipProofInd;
	}

	public Integer getEmployeeCitizenShipProofRequired() {
		return employeeCitizenShipProofRequired;
	}

	public void setEmployeeCitizenShipProofRequired(
			Integer employeeCitizenShipProofRequired) {
		this.employeeCitizenShipProofRequired = employeeCitizenShipProofRequired;
	}

	public Integer getCrewWearBadgesInd() {
		return crewWearBadgesInd;
	}

	public void setCrewWearBadgesInd(Integer crewWearBadgesInd) {
		this.crewWearBadgesInd = crewWearBadgesInd;
	}

	public Integer getCrewWearBadgesRequired() {
		return crewWearBadgesRequired;
	}

	public void setCrewWearBadgesRequired(Integer crewWearBadgesRequired) {
		this.crewWearBadgesRequired = crewWearBadgesRequired;
	}

	public LicenseAndCertifications getLicenseAndCertifications() {
		return licenseAndCertifications;
	}

	public void setLicenseAndCertifications(
			LicenseAndCertifications licenseAndCertifications) {
		this.licenseAndCertifications = licenseAndCertifications;
	}

	public InsuranceDetails getInsuranceDetails() {
		return insuranceDetails;
	}

	public void setInsuranceDetails(InsuranceDetails insuranceDetails) {
		this.insuranceDetails = insuranceDetails;
	}

	public W9Information getW9Information() {
		return w9Information;
	}

	public void setW9Information(W9Information w9Information) {
		this.w9Information = w9Information;
	}

	public String getFirmType() {
		return firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}

	public String getSubContractorId() {
		return subContractorId;
	}

	public void setSubContractorId(String subContractorId) {
		this.subContractorId = subContractorId;
	}
	
     
  
	
	
   
}
