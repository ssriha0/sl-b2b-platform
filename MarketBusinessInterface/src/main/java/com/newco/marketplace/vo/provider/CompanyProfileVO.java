/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.survey.SurveyRatingByQuestionVO;

/**
 * @author sahmad7
 */
public class CompanyProfileVO extends BaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5650554004264698812L;

	private Integer vendorId = -1;
    
    private String businessName = null;
    
    private Integer businessTypeId = -1;
    
    private String dbaName = null;
    
    private String dunsNo = null;
    
    private String einNo = null;
    
    private Integer  companySizeId = -1;
    
    private String webAddress = null;
    
    private Integer foreignOwnedInd;
    
    private String foreignOwnedPct = null;
         
    private Integer yrsInBusiness = -1;
        
    private Date businessStartDate;
    private String yearsInBusiness; // this new guy is for Display on Providerprofile pages
    private Date memberSince;
    
    private String serviceLiveStatus;
  
    private String businessPhone = null;
    
    private String businessFax = null;
    
    private String busPhoneExtn = null;
    private String primaryIndustry = null;
    private String businessType = null;
    private String companySize = null;
    private Integer noOfEmployees=null;
    private String annualSalesVolume = null;
    private String businessDesc = null;
    
	private String busStreet1 = "";
	private String busStreet2 = "";
	private String busCity = "";
	private String busStateCd = null;
	private String busZip = "";
	
	private String lastName= "";
	private String firstName="";
	private String mi="";
	private String suffix="";
	private String title="";
	private String role = null;
	private String email="";
	private String altEmail="";
	private List licensesList = null;
	private List <TeamMemberVO> teamMembers;
	private List<SurveyRatingByQuestionVO> surveyRatings;
	private List<InsurancePolicyVO> insurancePolicies;
	private Integer totalSoCompleted = new Integer(0);
	 private Integer primaryIndustryId = new Integer(-1);
    //End of changes

	//warrantyinfo
	private String warrPeriodLabor = null;//warr_period_labor
	private String warrOfferedLabor = null;//warr_offered_labor
	private String warrOfferedParts = null;//warr_offered_parts
	private String warrPeriodParts = null;//warr_period_parts	
	private String warrantyMeasure = null;//warranty_measure
	private String freeEstimate = null;//free_estimate
	private String conductDrugTest = null;//conduct_drug_test
	private String considerDrugTest = null;//consider_drug_test
	private String hasEthicsPolicy = null;//has_ethics_policy
	private String requireBadge = null;//require_badge
	private String considerBadge = null;//consider_badge
	private String requireUsDoc = null;//require_us_doc
	private String considerImplPolicy = null;//consider_impl_policy
	private String considerEthicPolicy = null;//consider_ethic_policy
	
	private List insuranceList = null;
	
	private List<String> errorList = null;
	private List<VendorCredentialsVO> vendorCredentials;

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public List getInsuranceList() {
		return insuranceList;
	}

	public void setInsuranceList(List insuranceList) {
		this.insuranceList = insuranceList;
	}

	public String getWarrPeriodLabor() {
		return warrPeriodLabor;
	}

	public void setWarrPeriodLabor(String warrPeriodLabor) {
		this.warrPeriodLabor = warrPeriodLabor;
	}

	public String getWarrOfferedLabor() {
		return warrOfferedLabor;
	}

	public void setWarrOfferedLabor(String warrOfferedLabor) {
		this.warrOfferedLabor = warrOfferedLabor;
	}

	public String getWarrOfferedParts() {
		return warrOfferedParts;
	}

	public void setWarrOfferedParts(String warrOfferedParts) {
		this.warrOfferedParts = warrOfferedParts;
	}

	public String getWarrPeriodParts() {
		return warrPeriodParts;
	}

	public void setWarrPeriodParts(String warrPeriodParts) {
		this.warrPeriodParts = warrPeriodParts;
	}

	public String getWarrantyMeasure() {
		return warrantyMeasure;
	}

	public void setWarrantyMeasure(String warrantyMeasure) {
		this.warrantyMeasure = warrantyMeasure;
	}

	public String getFreeEstimate() {
		return freeEstimate;
	}

	public void setFreeEstimate(String freeEstimate) {
		this.freeEstimate = freeEstimate;
	}

	public String getConductDrugTest() {
		return conductDrugTest;
	}

	public void setConductDrugTest(String conductDrugTest) {
		this.conductDrugTest = conductDrugTest;
	}

	public String getConsiderDrugTest() {
		return considerDrugTest;
	}

	public void setConsiderDrugTest(String considerDrugTest) {
		this.considerDrugTest = considerDrugTest;
	}

	public String getHasEthicsPolicy() {
		return hasEthicsPolicy;
	}

	public void setHasEthicsPolicy(String hasEthicsPolicy) {
		this.hasEthicsPolicy = hasEthicsPolicy;
	}

	public String getRequireBadge() {
		return requireBadge;
	}

	public void setRequireBadge(String requireBadge) {
		this.requireBadge = requireBadge;
	}

	public String getConsiderBadge() {
		return considerBadge;
	}

	public void setConsiderBadge(String considerBadge) {
		this.considerBadge = considerBadge;
	}

	public String getRequireUsDoc() {
		return requireUsDoc;
	}

	public void setRequireUsDoc(String requireUsDoc) {
		this.requireUsDoc = requireUsDoc;
	}

	public String getConsiderImplPolicy() {
		return considerImplPolicy;
	}

	public void setConsiderImplPolicy(String considerImplPolicy) {
		this.considerImplPolicy = considerImplPolicy;
	}

	public String getConsiderEthicPolicy() {
		return considerEthicPolicy;
	}

	public void setConsiderEthicPolicy(String considerEthicPolicy) {
		this.considerEthicPolicy = considerEthicPolicy;
	}

	public List getLicensesList() {
		return licensesList;
	}

	public void setLicensesList(List licensesList) {
		this.licensesList = licensesList;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getBusinessDesc() {
		return businessDesc;
	}

	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}

	public String getAnnualSalesVolume() {
		return annualSalesVolume;
	}

	public void setAnnualSalesVolume(String annualSalesVolume) {
		this.annualSalesVolume = annualSalesVolume;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public Integer getCompanySizeId() {
		return companySizeId;
	}

	public void setCompanySizeId(Integer companySizeId) {
		this.companySizeId = companySizeId;
	}

	
	public String getDbaName() {
		return dbaName;
	}

	public void setDbaName(String dbaName) {
		this.dbaName = dbaName;
	}

	public String getDunsNo() {
		return dunsNo;
	}

	public void setDunsNo(String dunsNo) {
		this.dunsNo = dunsNo;
	}

	public String getEinNo() {
		return einNo;
	}

	public void setEinNo(String einNo) {
		this.einNo = einNo;
	}

	

	public String getForeignOwnedPct() {
		return foreignOwnedPct;
	}

	public void setForeignOwnedPct(String foreignOwnedPct) {
		this.foreignOwnedPct = foreignOwnedPct;
	}

	

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	
	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public Integer getYrsInBusiness() {
		return yrsInBusiness;
	}

	public void setYrsInBusiness(Integer yrsInBusiness) {
		this.yrsInBusiness = yrsInBusiness;
	}
	
	public Date getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(Date businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	/**
	 * @return the foreignOwnedInd
	 */
	public Integer getForeignOwnedInd() {
		return foreignOwnedInd;
	}

	/**
	 * @param foreignOwnedInd the foreignOwnedInd to set
	 */
	public void setForeignOwnedInd(Integer foreignOwnedInd) {
		this.foreignOwnedInd = foreignOwnedInd;
	}
	
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getBusinessFax() {
		return businessFax;
	}
	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}
	public String getBusPhoneExtn() {
		return busPhoneExtn;
	}
	public void setBusPhoneExtn(String busPhoneExtn) {
		this.busPhoneExtn = busPhoneExtn;
	}

	public String getBusStreet1() {
		return busStreet1;
	}

	public void setBusStreet1(String busStreet1) {
		this.busStreet1 = busStreet1;
	}

	public String getBusStreet2() {
		return busStreet2;
	}

	public void setBusStreet2(String busStreet2) {
		this.busStreet2 = busStreet2;
	}

	public String getBusCity() {
		return busCity;
	}

	public void setBusCity(String busCity) {
		this.busCity = busCity;
	}

	public String getBusStateCd() {
		return busStateCd;
	}

	public void setBusStateCd(String busStateCd) {
		this.busStateCd = busStateCd;
	}

	public String getBusZip() {
		return busZip;
	}

	public void setBusZip(String busZip) {
		this.busZip = busZip;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public List<TeamMemberVO> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<TeamMemberVO> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getYearsInBusiness() {
		return yearsInBusiness;
	}

	public void setYearsInBusiness(String yearsInBusiness) {
		this.yearsInBusiness = yearsInBusiness;
	}

	public List<SurveyRatingByQuestionVO> getSurveyRatings() {
		return surveyRatings;
	}

	public void setSurveyRatings(List<SurveyRatingByQuestionVO> surveyRatings) {
		this.surveyRatings = surveyRatings;
	}

	public List<InsurancePolicyVO> getInsurancePolicies() {
		return insurancePolicies;
	}

	public void setInsurancePolicies(List<InsurancePolicyVO> insurancePolicies) {
		this.insurancePolicies = insurancePolicies;
	}

	/**
	 * @return the totalSoCompleted
	 */
	public Integer getTotalSoCompleted() {
		return totalSoCompleted;
	}

	/**
	 * @param totalSoCompleted the totalSoCompleted to set
	 */
	public void setTotalSoCompleted(Integer totalSoCompleted) {
		this.totalSoCompleted = totalSoCompleted;
	}

	/**
	 * @return the primaryIndustryId
	 */
	public Integer getPrimaryIndustryId() {
		return primaryIndustryId;
	}

	/**
	 * @param primaryIndustryId the primaryIndustryId to set
	 */
	public void setPrimaryIndustryId(Integer primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}

	public String getServiceLiveStatus() {
		return serviceLiveStatus;
	}

	public void setServiceLiveStatus(String serviceLiveStatus) {
		this.serviceLiveStatus = serviceLiveStatus;
	}

	public Integer getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(Integer noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public List<VendorCredentialsVO> getVendorCredentials() {
		return vendorCredentials;
	}

	public void setVendorCredentials(List<VendorCredentialsVO> vendorCredentials) {
		this.vendorCredentials = vendorCredentials;
	}

	
        
        
}
