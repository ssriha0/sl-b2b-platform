package com.newco.marketplace.web.dto.provider;

import java.util.List;

import com.newco.marketplace.web.dto.SPNProviderProfileBuyerTable;

/**
 * @author 
 *
 */
public class CompanyProfileDto extends BaseDto {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2210249532340286120L;

	private Integer vendorId = -1;
    
    private String businessName;
    
    private Integer businessTypeId = -1;
    
    private String dbaName;
    
    private String dunsNo;
    
    private String einNo;
    
    private Integer  companySizeId = -1;
    
    private String webAddress;
    
    private Integer foreignOwnedInd;
    
    private String foreignOwnedPct;
         
    private Integer yrsInBusiness = -1;
        
    private String businessStartDate;
    private String yearsInBusiness;
 
    private String businessPhone;
    
    private String businessFax;
    
    private String busPhoneExtn;
    private String primaryIndustry;
    
    private String businessType;
    private String companySize;
    private String annualSalesVolume;
    private String businessDesc;
    
    private String busStreet1 = "";
	private String busStreet2 = "";
	private String busCity = "";
	private String busStateCd;
	private String busZip = "";
	
	private String lastName= "";
	private String firstName="";
	private String mi="";
	private String suffix="";
	private String title="";
	private String role;
	private String email="";
	private String altEmail="";
	
	//warranty info
	private String warrPeriodLabor;//warr_period_labor
	private int warrOfferedLabor = -1;//warr_offered_labor
	private int warrOfferedParts = -1;//warr_offered_parts
	private String warrPeriodParts;//warr_period_parts	
	private int warrantyMeasure = -1;//warranty_measure
	private int freeEstimate = -1;//free_estimate
	private int conductDrugTest = -1;//conduct_drug_test
	private int considerDrugTest = -1;//consider_drug_test
	private int hasEthicsPolicy = -1;//has_ethics_policy
	private int requireBadge = -1;//require_badge
	private int considerBadge = -1;//consider_badge
	private int requireUsDoc = -1;//require_us_doc
	private int considerImplPolicy = -1;//consider_impl_policy
	private int considerEthicPolicy = -1;//consider_ethic_policy
	private String memberSince=null;

	private List licensesList;
    //End of changes

	private List insuranceList;
	public List getInsuranceList() {
		return insuranceList;
	}
	
	// Select Provider Networks (SPN)panel	
	private List<SPNProviderProfileBuyerTable> buyerSPNList;

	public void setInsuranceList(List insuranceList) {
		this.insuranceList = insuranceList;
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

	public void setForeignOwnedPct(String string) {
		this.foreignOwnedPct = string;
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
	
	public String getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(String businessStartDate) {
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getAnnualSalesVolume() {
		return annualSalesVolume;
	}

	public void setAnnualSalesVolume(String annualSalesVolume) {
		this.annualSalesVolume = annualSalesVolume;
	}

	public String getBusinessDesc() {
		return businessDesc;
	}

	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}

	public List getLicensesList() {
		return licensesList;
	}

	public void setLicensesList(List licensesList) {
		this.licensesList = licensesList;
	}

	public String getWarrPeriodLabor() {
		return warrPeriodLabor;
	}

	public void setWarrPeriodLabor(String warrPeriodLabor) {
		this.warrPeriodLabor = warrPeriodLabor;
	}

	public int getWarrOfferedLabor() {
		return warrOfferedLabor;
	}

	public void setWarrOfferedLabor(int warrOfferedLabor) {
		this.warrOfferedLabor = warrOfferedLabor;
	}

	public int getWarrOfferedParts() {
		return warrOfferedParts;
	}

	public void setWarrOfferedParts(int warrOfferedParts) {
		this.warrOfferedParts = warrOfferedParts;
	}

	public String getWarrPeriodParts() {
		return warrPeriodParts;
	}

	public void setWarrPeriodParts(String warrPeriodParts) {
		this.warrPeriodParts = warrPeriodParts;
	}

	public int getWarrantyMeasure() {
		return warrantyMeasure;
	}

	public void setWarrantyMeasure(int warrantyMeasure) {
		this.warrantyMeasure = warrantyMeasure;
	}

	public int getFreeEstimate() {
		return freeEstimate;
	}

	public void setFreeEstimate(int freeEstimate) {
		this.freeEstimate = freeEstimate;
	}

	public int getConductDrugTest() {
		return conductDrugTest;
	}

	public void setConductDrugTest(int conductDrugTest) {
		this.conductDrugTest = conductDrugTest;
	}

	public int getConsiderDrugTest() {
		return considerDrugTest;
	}

	public void setConsiderDrugTest(int considerDrugTest) {
		this.considerDrugTest = considerDrugTest;
	}

	public int getHasEthicsPolicy() {
		return hasEthicsPolicy;
	}

	public void setHasEthicsPolicy(int hasEthicsPolicy) {
		this.hasEthicsPolicy = hasEthicsPolicy;
	}

	public int getRequireBadge() {
		return requireBadge;
	}

	public void setRequireBadge(int requireBadge) {
		this.requireBadge = requireBadge;
	}

	public int getConsiderBadge() {
		return considerBadge;
	}

	public void setConsiderBadge(int considerBadge) {
		this.considerBadge = considerBadge;
	}

	public int getRequireUsDoc() {
		return requireUsDoc;
	}

	public void setRequireUsDoc(int requireUsDoc) {
		this.requireUsDoc = requireUsDoc;
	}

	public int getConsiderImplPolicy() {
		return considerImplPolicy;
	}

	public void setConsiderImplPolicy(int considerImplPolicy) {
		this.considerImplPolicy = considerImplPolicy;
	}

	public int getConsiderEthicPolicy() {
		return considerEthicPolicy;
	}

	public void setConsiderEthicPolicy(int considerEthicPolicy) {
		this.considerEthicPolicy = considerEthicPolicy;
	}

	public String getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(String memberSince) {
		this.memberSince = memberSince;
	}

	public List<SPNProviderProfileBuyerTable> getBuyerSPNList() {
		return buyerSPNList;
	}

	public void setBuyerSPNList(List<SPNProviderProfileBuyerTable> buyerSPNList) {
		this.buyerSPNList = buyerSPNList;
	}

	public String getYearsInBusiness() {
		return yearsInBusiness;
	}

	public void setYearsInBusiness(String yearsInBusiness) {
		this.yearsInBusiness = yearsInBusiness;
	}

}
