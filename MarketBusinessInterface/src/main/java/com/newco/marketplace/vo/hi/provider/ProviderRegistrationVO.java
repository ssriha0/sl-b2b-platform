
package com.newco.marketplace.vo.hi.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.W9RegistrationVO;
import com.newco.marketplace.vo.provider.WarrantyVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * $Revision: 34804 $ $Author: jrodr09 $ $Date: 2013-11-15 11:40:45 +0530 (Fri, 15 Nov 2013) $
 */

/*
 * Maintenance History: See bottom of file
 */

public class ProviderRegistrationVO extends SerializableBaseVO 
{

	
	/**
	 *  
	 */
	private static final long serialVersionUID = -3186181427118588822L;
	//This is required for DB insert 
	private Integer vendorId= null;
	private Integer vendorContactResourceId=null;
	private String password=null;

	private String subContractorIdVal= null;
	private Integer subContractorId= null;
	private Integer subContractorCrewId= null;
	private String crewVal=null;
	
	private String mainBusiPhoneNoVal =null;
	private Long mainBusiPhoneNo =null;
	private String mainBusiMobileNoVal = null;
	private Long mainBusiMobileNo = null;
	/**
	 * Variables for "Business Information"
	 */
    private String legalBusinessName = null;
    private String DBAName = null;
    private String websiteAddress = null;
    
    private String mainBusiPhoneNo1 = null;
    private String mainBusiPhoneNo2 = null;
    private String mainBusiPhoneNo3 = null;
    private String mainBusinessExtn = null;
    
    private String businessFax1 = null;
    private String businessFax2 = null;
    private String businessFax3 = null;
    
    private String primaryIndustry = null;
    private String otherPrimaryService = null;
    
    private List stateTypeList = new ArrayList();
    private String stateType = null;
    
    /** 
     * Variables for "Business Address"
     */
    private String businessStreet1 = null;
    private String businessStreet2 = null;
    private String businessCity = null;
    private String businessState = null;
    private String businessZip = null;
    private String businessAprt = null;
    
    /**
     *	Variables for "Mailing Address" 
     */
    private boolean mailAddressChk = false;
    private String mailingStreet1 = null;
    private String mailingStreet2 = null;
    private String mailingCity = null;
    private String mailingState = null;
    private String mailingZip = null;
    private String mailingAprt = null;
    
    /**
     * variables for "Primary Contact Information - Administrator"
     */
    private String howDidYouHear = null;
    private String promotionCode = null;
    private String roleWithinCom = null;
    private String jobTitle = null;
    private String serviceCall = null;
    private String firstName = null;
    private String middleName = null;
    private String lastName = null;
    private String nameSuffix = null;
    private String email = null;
    private String confirmEmail = null;
    private String altEmail = null;
    private String confAltEmail = null;
    private String userName = null;
    
    private boolean validateState = true;
    
   /*Credential Deatils for SHIP
    *  
    */
    private String lInsuranceCompany=null;
    private String lPolicyNumberVal=null;
    private Integer lPolicyNumber=null;
    private Date lExpirationDate=null;
    private String wcInsuranceCompany=null;
    private String wcPolicyNumberVal=null;
    private Integer wcPolicyNumber=null;
    private Date wcExpirationDate=null;
    private String aInsuranceCompany=null;
    private String aPolicyNumberVal=null;
    private Integer aPolicyNumber=null;
    private Date aExpirationDate=null;
   
    /**
     * List variables
     */
    private List primaryIndList = new ArrayList();
    private List roleWithinCompany =  new ArrayList();
    private List howDidYouHearList =  new ArrayList();
    
    //added for provider registration using API
    private boolean registerProviderUsingAPI = false;
   //SL 16934 Added to hold the value for SL referral code
    private String referralCode;   
    
    private String dunsNo= null;
    
	private String busStructure; 
	
	private Date busStartDt;
	
	private String businessStartDate;
	
	private String isForeignOwned; 
	
	private String foreignOwnedPct;
	
	private String companySize; 
	
	private String description = null;
	
	private String annualSalesRevenue;
    
    private WarrantyVO warrantyVO;
    
    private List<LicensesAndCertVO> licensesList;
    
    
    private Boolean generalLInd;
    private Boolean vehicleLInd;
    private Boolean workmanCInd;
    
    private String generalLiabilityAmount;
    
    private CredentialProfile generalLiability;
    
    private String vehicleLiabilityAmount;
    
    private CredentialProfile vehicleLiability;
    
    private String workmanCompensationAmount;
    
    private CredentialProfile workmanCompensation;
    
    private W9RegistrationVO w9RegistrationVO;
 
    
    private boolean generalLiabilityInd=false;
    
    private boolean vehicleLiabilityInd=false;
    
    private boolean workmanCompensationInd=false;
    
    private Results results;
    
    private Integer ownerInd;
    
	private Integer dispatchInd;
	
	private Integer managerInd;
	
	private Integer adminInd;
	
	private Integer otherInd;
	
	private Integer sproInd;
	
	private String  taxStatus;
    
	
	// 
	
	private String firmType;
	
	private String subContractId;
	
	private boolean isInsurancePresent;
	
	private boolean licenseNotNeeded;

	private boolean isLicensePresent;
	
	private boolean createdViaAPI=false;
	
	
	
	
    public boolean isCreatedViaAPI() {
		return createdViaAPI;
	}

	public void setCreatedViaAPI(boolean createdViaAPI) {
		this.createdViaAPI = createdViaAPI;
	}

	public Boolean getGeneralLInd() {
		return generalLInd;
	}

	public void setGeneralLInd(Boolean generalLInd) {
		this.generalLInd = generalLInd;
	}

	public Boolean getVehicleLInd() {
		return vehicleLInd;
	}

	public void setVehicleLInd(Boolean vehicleLInd) {
		this.vehicleLInd = vehicleLInd;
	}

	public Boolean getWorkmanCInd() {
		return workmanCInd;
	}

	public void setWorkmanCInd(Boolean workmanCInd) {
		this.workmanCInd = workmanCInd;
	}

	public boolean isLicensePresent() {
		return isLicensePresent;
	}

	public void setLicensePresent(boolean isLicensePresent) {
		this.isLicensePresent = isLicensePresent;
	}

	public String getGeneralLiabilityAmount() {
		return generalLiabilityAmount;
	}

	public void setGeneralLiabilityAmount(String generalLiabilityAmount) {
		this.generalLiabilityAmount = generalLiabilityAmount;
	}

	public String getVehicleLiabilityAmount() {
		return vehicleLiabilityAmount;
	}

	public void setVehicleLiabilityAmount(String vehicleLiabilityAmount) {
		this.vehicleLiabilityAmount = vehicleLiabilityAmount;
	}

	public String getWorkmanCompensationAmount() {
		return workmanCompensationAmount;
	}

	public void setWorkmanCompensationAmount(String workmanCompensationAmount) {
		this.workmanCompensationAmount = workmanCompensationAmount;
	}

	public boolean isLicenseNotNeeded() {
		return licenseNotNeeded;
	}

	public void setLicenseNotNeeded(boolean licenseNotNeeded) {
		this.licenseNotNeeded = licenseNotNeeded;
	}

	public boolean isInsurancePresent() {
		return isInsurancePresent;
	}

	public void setInsurancePresent(boolean isInsurancePresent) {
		this.isInsurancePresent = isInsurancePresent;
	}

	public String getFirmType() {
		return firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}

	public String getSubContractId() {
		return subContractId;
	}

	public void setSubContractId(String subContractId) {
		this.subContractId = subContractId;
	}

	public String getTaxStatus() {
		return taxStatus;
	}

	public void setTaxStatus(String taxStatus) {
		this.taxStatus = taxStatus;
	}

	private List<LicensesAndCertVO> editLicensesList;
    
    
    
    public List<LicensesAndCertVO> getEditLicensesList() {
		return editLicensesList;
	}

	public void setEditLicensesList(List<LicensesAndCertVO> editLicensesList) {
		this.editLicensesList = editLicensesList;
	}


	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public boolean isRegisterProviderUsingAPI() {
		return registerProviderUsingAPI;
	}

	public void setRegisterProviderUsingAPI(boolean registerProviderUsingAPI) {
		this.registerProviderUsingAPI = registerProviderUsingAPI;
	}

	/**
     * Getters and Setters
     */
    
    public String getLegalBusinessName()
    {
	return legalBusinessName;
    }
    
    public void setLegalBusinessName(String legalBusinessName)
    {
	this.legalBusinessName = legalBusinessName;
    }
    
    public String getDBAName()
    {
	return DBAName;
    }
    
    public void setDBAName(String name)
    {
	DBAName = name;
    }
    
    public String getWebsiteAddress()
    {
	return websiteAddress;
    }
    
    public void setWebsiteAddress(String websiteAddress)
    {
	this.websiteAddress = websiteAddress;
    }
    
    public String getMainBusiPhoneNo1()
    {
	return mainBusiPhoneNo1;
    }
    
    public void setMainBusiPhoneNo1(String mainBusiPhoneNo1)
    {
	this.mainBusiPhoneNo1 = mainBusiPhoneNo1;
    }
    
    public String getMainBusiPhoneNo2()
    {
	return mainBusiPhoneNo2;
    }
    
    public void setMainBusiPhoneNo2(String mainBusiPhoneNo2)
    {
	this.mainBusiPhoneNo2 = mainBusiPhoneNo2;
    }
    
    public String getMainBusiPhoneNo3()
    {
	return mainBusiPhoneNo3;
    }
    
    public void setMainBusiPhoneNo3(String mainBusiPhoneNo3)
    {
	this.mainBusiPhoneNo3 = mainBusiPhoneNo3;
    }
    
    public String getMainBusinessExtn() {
		return mainBusinessExtn;
	}

	public void setMainBusinessExtn(String mainBusinessExtn) {
		this.mainBusinessExtn = mainBusinessExtn;
	}
    
	public String getBusinessFax1() {
		return businessFax1;
	}

	public void setBusinessFax1(String businessFax1) {
		this.businessFax1 = businessFax1;
	}
	
	public String getBusinessFax2() {
		return businessFax2;
	}

	public void setBusinessFax2(String businessFax2) {
		this.businessFax2 = businessFax2;
	}
	
	public String getBusinessFax3() {
		return businessFax3;
	}

	public void setBusinessFax3(String businessFax3) {
		this.businessFax3 = businessFax3;
	}
	
	 public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
		
	public String getBusinessStreet1()
    {
	return businessStreet1;
    }
    
    public void setBusinessStreet1(String businessStreet1)
    {
	this.businessStreet1 = businessStreet1;
    }
    
    public String getBusinessStreet2()
    {
	return businessStreet2;
    }
    
    public void setBusinessStreet2(String businessStreet2)
    {
	this.businessStreet2 = businessStreet2;
    }
    
    public String getBusinessCity()
    {
	return businessCity;
    }
    
    public void setBusinessCity(String businessCity)
    {
	this.businessCity = businessCity;
    }
    
    public String getBusinessState()
    {
	return businessState;
    }
    
    public void setBusinessState(String businessState)
    {
	this.businessState = businessState;
    }
    
    public String getBusinessZip()
    {
	return businessZip;
    }
    
    public void setBusinessZip(String businessZip)
    {
	this.businessZip = businessZip;
    }
    
    public String getBusinessAprt() {
		return businessAprt;
	}

	public void setBusinessAprt(String businessAprt) {
		this.businessAprt = businessAprt;
	}
	
	public boolean getMailAddressChk()
    {
	return mailAddressChk;
    }
    
    public void setMailAddressChk(boolean mailAddressChk)
    {
	this.mailAddressChk = mailAddressChk;
    }
	
	public String getMailingStreet1()
    {
	return mailingStreet1;
    }
    
    public void setMailingStreet1(String mailingStreet1)
    {
	this.mailingStreet1 = mailingStreet1;
    }
    
    public String getMailingStreet2()
    {
	return mailingStreet2;
    }
    
    public void setMailingStreet2(String mailingStreet2)
    {
	this.mailingStreet2 = mailingStreet2;
    }
	
    public String getMailingCity()
    {
	return mailingCity;
    }
    
    public void setMailingCity(String mailingCity)
    {
	this.mailingCity = mailingCity;
    }
    
    public String getMailingState()
    {
	return mailingState;
    }
    
    public void setMailingState(String mailingState)
    {
	this.mailingState = mailingState;
    }
    
    public String getMailingZip()
    {
	return mailingZip;
    }
    
    public void setMailingZip(String mailingZip)
    {
	this.mailingZip = mailingZip;
    }
    
    public String getMailingAprt() {
		return mailingAprt;
	}

	public void setMailingAprt(String mailingAprt) {
		this.mailingAprt = mailingAprt;
	}
	
	public String getHowDidYouHear() {
		return howDidYouHear;
	}

	public void setHowDidYouHear(String howDidYouHear) {
		this.howDidYouHear = howDidYouHear;
	}
	
	 public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	
	public String getRoleWithinCom() {
		return roleWithinCom;
	}

	public void setRoleWithinCom(String roleWithinCom) {
		this.roleWithinCom = roleWithinCom;
	}
    
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
    
	public String getServiceCall() {
		return serviceCall;
	}

	public void setServiceCall(String serviceCall) {
		this.serviceCall = serviceCall;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	
	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
	
	public String getConfAltEmail() {
		return confAltEmail;
	}

	public void setConfAltEmail(String confAltEmail) {
		this.confAltEmail = confAltEmail;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
    public List getPrimaryIndList() {
		return primaryIndList;
	}

	public void setPrimaryIndList(List primaryIndList) {
		this.primaryIndList = primaryIndList;
	}
    
	public List getRoleWithinCompany() {
		return roleWithinCompany;
	}

	public void setRoleWithinCompany(List roleWithinCompany) {
		this.roleWithinCompany = roleWithinCompany;
	}
	
	public List getHowDidYouHearList() {
		return howDidYouHearList;
	}

	public void setHowDidYouHearList(List howDidYouHearList) {
		this.howDidYouHearList = howDidYouHearList;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getVendorContactResourceId() {
		return vendorContactResourceId;
	}

	public void setVendorContactResourceId(Integer vendorContactResourceId) {
		this.vendorContactResourceId = vendorContactResourceId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List getStateTypeList() {
		return stateTypeList;
	}

	public void setStateTypeList(List stateTypeList) {
		this.stateTypeList = stateTypeList;
	}

	public String getStateType() {
		return stateType;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	public boolean isValidateState() { 
		return validateState;
	}

	public void setValidateState(boolean validateState) {
		this.validateState = validateState;
	}

	public String getOtherPrimaryService() {
		return otherPrimaryService;
	}

	public void setOtherPrimaryService(String otherPrimaryService) {
		this.otherPrimaryService = otherPrimaryService;
	}

	public String getSubContractorIdVal() {
		return subContractorIdVal;
	}

	public void setSubContractorIdVal(String subContractorIdVal) {
		this.subContractorIdVal = subContractorIdVal;
	}

	public Integer getSubContractorId() {
		return subContractorId;
	}

	public void setSubContractorId(Integer subContractorId) {
		this.subContractorId = subContractorId;
	}

	public Integer getSubContractorCrewId() {
		return subContractorCrewId;
	}

	public void setSubContractorCrewId(Integer subContractorCrewId) {
		this.subContractorCrewId = subContractorCrewId;
	}

	public String getCrewVal() {
		return crewVal;
	}

	public void setCrewVal(String crewVal) {
		this.crewVal = crewVal;
	}

	public Long getMainBusiPhoneNo() {
		return mainBusiPhoneNo;
	}

	public void setMainBusiPhoneNo(Long mainBusiPhoneNo) {
		this.mainBusiPhoneNo = mainBusiPhoneNo;
	}

	public Long getMainBusiMobileNo() {
		return mainBusiMobileNo;
	}

	public void setMainBusiMobileNo(Long mainBusiMobileNo) {
		this.mainBusiMobileNo = mainBusiMobileNo;
	}

	public String getlInsuranceCompany() {
		return lInsuranceCompany;
	}

	public void setlInsuranceCompany(String lInsuranceCompany) {
		this.lInsuranceCompany = lInsuranceCompany;
	}

	public Integer getlPolicyNumber() {
		return lPolicyNumber;
	}

	public void setlPolicyNumber(Integer lPolicyNumber) {
		this.lPolicyNumber = lPolicyNumber;
	}

	

	public String getWcInsuranceCompany() {
		return wcInsuranceCompany;
	}

	public void setWcInsuranceCompany(String wcInsuranceCompany) {
		this.wcInsuranceCompany = wcInsuranceCompany;
	}

	public Integer getWcPolicyNumber() {
		return wcPolicyNumber;
	}

	public void setWcPolicyNumber(Integer wcPolicyNumber) {
		this.wcPolicyNumber = wcPolicyNumber;
	}

	

	public String getaInsuranceCompany() {
		return aInsuranceCompany;
	}

	public void setaInsuranceCompany(String aInsuranceCompany) {
		this.aInsuranceCompany = aInsuranceCompany;
	}

	public Integer getaPolicyNumber() {
		return aPolicyNumber;
	}

	public void setaPolicyNumber(Integer aPolicyNumber) {
		this.aPolicyNumber = aPolicyNumber;
	}
    public String getlPolicyNumberVal() {
		return lPolicyNumberVal;
	}

	public void setlPolicyNumberVal(String lPolicyNumberVal) {
		this.lPolicyNumberVal = lPolicyNumberVal;
	}

	public String getWcPolicyNumberVal() {
		return wcPolicyNumberVal;
	}

	public void setWcPolicyNumberVal(String wcPolicyNumberVal) {
		this.wcPolicyNumberVal = wcPolicyNumberVal;
	}

	public String getaPolicyNumberVal() {
		return aPolicyNumberVal;
	}

	public void setaPolicyNumberVal(String aPolicyNumberVal) {
		this.aPolicyNumberVal = aPolicyNumberVal;
	}

	public String getMainBusiPhoneNoVal() {
		return mainBusiPhoneNoVal;
	}

	public void setMainBusiPhoneNoVal(String mainBusiPhoneNoVal) {
		this.mainBusiPhoneNoVal = mainBusiPhoneNoVal;
	}

	public String getMainBusiMobileNoVal() {
		return mainBusiMobileNoVal;
	}

	public void setMainBusiMobileNoVal(String mainBusiMobileNoVal) {
		this.mainBusiMobileNoVal = mainBusiMobileNoVal;
	}

	public Date getlExpirationDate() {
		return lExpirationDate;
	}

	public void setlExpirationDate(Date lExpirationDate) {
		this.lExpirationDate = lExpirationDate;
	}

	public Date getWcExpirationDate() {
		return wcExpirationDate;
	}

	public void setWcExpirationDate(Date wcExpirationDate) {
		this.wcExpirationDate = wcExpirationDate;
	}

	public Date getaExpirationDate() {
		return aExpirationDate;
	}

	public void setaExpirationDate(Date aExpirationDate) {
		this.aExpirationDate = aExpirationDate;
	}

	public WarrantyVO getWarrantyVO() {
		return warrantyVO;
	}

	public void setWarrantyVO(WarrantyVO warrantyVO) {
		this.warrantyVO = warrantyVO;
	}



	public List<LicensesAndCertVO> getLicensesList() {
		return licensesList;
	}

	public void setLicensesList(List<LicensesAndCertVO> licensesList) {
		this.licensesList = licensesList;
	}

	public W9RegistrationVO getW9RegistrationVO() {
		return w9RegistrationVO;
	}

	public void setW9RegistrationVO(W9RegistrationVO w9RegistrationVO) {
		this.w9RegistrationVO = w9RegistrationVO;
	}

	public CredentialProfile getGeneralLiability() {
		return generalLiability;
	}

	public void setGeneralLiability(CredentialProfile generalLiability) {
		this.generalLiability = generalLiability;
	}

	public CredentialProfile getVehicleLiability() {
		return vehicleLiability;
	}

	public void setVehicleLiability(CredentialProfile vehicleLiability) {
		this.vehicleLiability = vehicleLiability;
	}

	public CredentialProfile getWorkmanCompensation() {
		return workmanCompensation;
	}

	public void setWorkmanCompensation(CredentialProfile workmanCompensation) {
		this.workmanCompensation = workmanCompensation;
	}

	public String getDunsNo() {
		return dunsNo;
	}

	public void setDunsNo(String dunsNo) {
		this.dunsNo = dunsNo;
	}

	public String getBusStructure() {
		return busStructure;
	}

	public void setBusStructure(String busStructure) {
		this.busStructure = busStructure;
	}

	public Date getBusStartDt() {
		return busStartDt;
	}

	public void setBusStartDt(Date busStartDt) {
		this.busStartDt = busStartDt;
	}

	public String getIsForeignOwned() {
		return isForeignOwned;
	}

	public void setIsForeignOwned(String isForeignOwned) {
		this.isForeignOwned = isForeignOwned;
	}

	public String getForeignOwnedPct() {
		return foreignOwnedPct;
	}

	public void setForeignOwnedPct(String foreignOwnedPct) {
		this.foreignOwnedPct = foreignOwnedPct;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnnualSalesRevenue() {
		return annualSalesRevenue;
	}

	public void setAnnualSalesRevenue(String annualSalesRevenue) {
		this.annualSalesRevenue = annualSalesRevenue;
	}

	public String getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(String businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public boolean isGeneralLiabilityInd() {
		return generalLiabilityInd;
	}

	public void setGeneralLiabilityInd(boolean generalLiabilityInd) {
		this.generalLiabilityInd = generalLiabilityInd;
	}

	public boolean isVehicleLiabilityInd() {
		return vehicleLiabilityInd;
	}

	public void setVehicleLiabilityInd(boolean vehicleLiabilityInd) {
		this.vehicleLiabilityInd = vehicleLiabilityInd;
	}

	public boolean isWorkmanCompensationInd() {
		return workmanCompensationInd;
	}

	public void setWorkmanCompensationInd(boolean workmanCompensationInd) {
		this.workmanCompensationInd = workmanCompensationInd;
	}

	public Integer getOwnerInd() {
		return ownerInd;
	}

	public void setOwnerInd(Integer ownerInd) {
		this.ownerInd = ownerInd;
	}

	public Integer getDispatchInd() {
		return dispatchInd;
	}

	public void setDispatchInd(Integer dispatchInd) {
		this.dispatchInd = dispatchInd;
	}

	public Integer getManagerInd() {
		return managerInd;
	}

	public void setManagerInd(Integer managerInd) {
		this.managerInd = managerInd;
	}

	public Integer getAdminInd() {
		return adminInd;
	}

	public void setAdminInd(Integer adminInd) {
		this.adminInd = adminInd;
	}

	public Integer getOtherInd() {
		return otherInd;
	}

	public void setOtherInd(Integer otherInd) {
		this.otherInd = otherInd;
	}

	public Integer getSproInd() {
		return sproInd;
	}

	public void setSproInd(Integer sproInd) {
		this.sproInd = sproInd;
	}
	
	
}
