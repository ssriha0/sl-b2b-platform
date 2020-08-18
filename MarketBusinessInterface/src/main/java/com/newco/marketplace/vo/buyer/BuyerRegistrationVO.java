package com.newco.marketplace.vo.buyer;

import java.util.ArrayList;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class BuyerRegistrationVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4402626294339696347L;
	//This is required for DB insert 
	private Integer buyerId= null;
	private Integer roleId = null;
	private Integer buyerContactResourceId=null;
	private String password=null;
	private String deepLinkKey=null;
	private boolean simpleBuyerInd = false;
	
	// Start Business Information Panel
	private String businessName;
	
	private String phoneAreaCode;
	private String phonePart1;
	private String phonePart2;
	private String phoneExtn;
	private String faxAreaCode;
	private String faxPart1;
	private String faxPart2;
	
	// Dropdown results
	private String businessStructure = null;
	private String primaryIndustry = null;
	private String sizeOfCompany = null;
	private String annualSalesRevenue = null;
	private String websiteAddress = null;
	private String businessStarted = null;
	
    /**
     * Variables for "Business Address Panel"
     */
    private String businessStreet1 = null;
    private String businessStreet2 = null;
    private String businessCity = null;
    private String businessState = null;
    private String businessZip = null;
    private String businessAprt = null;
    
    /**
     *	Variables for "Mailing Address Panel" 
     */
    private boolean mailAddressChk = false;
    private String mailingStreet1 = null;
    private String mailingStreet2 = null;
    private String mailingCity = null;
    private String mailingState = null;
    private String mailingZip = null;
    private String mailingAprt = null;

    private String locName = null; // For simple buyer support
    private String homeAddressInd = null; // For simple buyer support
	
	// Primary Contact Info Panel
    private String roleWithinCom = null;
    private String jobTitle = null;
    private String firstName = null;
    private String middleName = null;
    private String lastName = null;
    private String nameSuffix = null;
    private String busPhoneNo1 = null;
    private String busPhoneNo2 = null;
    private String busPhoneNo3 = null;
    private String busExtn = null;
    
    private String mobPhoneNo1 = null;
    private String mobPhoneNo2 = null;
    private String mobPhoneNo3 = null;
    
    private String email = null;
    private String confirmEmail = null;
    private String altEmail = null;
    private String confAltEmail = null;
    
    private String howDidYouHear = null;
    private String promotionCode = null;
    

    private String userName = null;
    private int contactId;
    private int locationId;
	
	private List businessStructureList = new ArrayList();
	private List primaryIndList = new ArrayList();
	private List sizeOfCompanyList = new ArrayList();
	private List annualSalesRevenueList = new ArrayList();
    private List roleWithinCompanyList =  new ArrayList();
    private List howDidYouHearList =  new ArrayList();
    private List stateTypeList = new ArrayList();
    
    //Validating state and zip code
    private String stateType = null;
    
    //Terms and conditions
    private String termsAndConditions = null;
    
    private String termsAndCondition = null; // For simple buyer support
    private String serviceLiveBucksInd = null; // For simple buyer support
    private String serviceLiveBucksText = null; // For simple buyer support
    
    private int promotionalMailInd;
    
    private int fundingTypeId;
    
	//Validate State
    private boolean validateState = true;
    
    //Additional fields for FinCen
	private String einNo;
	private String ssnInd;
	private String dateOfBirth;
	private String altIdType;
	private String altIdCountry;
    
    private boolean apiFlag = false;
    
    //Changes for SL-20461 starts
    @XStreamOmitField
	private List buyerList = new ArrayList();
   
   
    private String buyerDropDownValue;
    private String buyerUserDropDownValue;
    @XStreamOmitField
	private String newAdminResourceId;
	@XStreamOmitField
	private String currentAdminResourceId;
	@XStreamOmitField
	private String currentAdminFirstname;
	@XStreamOmitField
	private String currentAdminLastName;
	@XStreamOmitField
	private String currentAdminUserName;
	@XStreamOmitField
	private String modifiedBy;
	
	@XStreamOmitField
	private String newAdminFirstName;
	@XStreamOmitField
	private String newAdminLastName;
	@XStreamOmitField
	private String newAdminUserName;
	
    //Changes for SL-20461 ends
    
    public String getStateType() {
		return stateType;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPhoneAreaCode() {
		return phoneAreaCode;
	}
	public void setPhoneAreaCode(String phoneAreaCode) {
		this.phoneAreaCode = phoneAreaCode;
	}
	public String getPhonePart1() {
		return phonePart1;
	}
	public void setPhonePart1(String phonePart1) {
		this.phonePart1 = phonePart1;
	}
	public String getPhonePart2() {
		return phonePart2;
	}
	public void setPhonePart2(String phonePart2) {
		this.phonePart2 = phonePart2;
	}
	public String getPhoneExtn() {
		return phoneExtn;
	}
	public void setPhoneExtn(String phoneExtn) {
		this.phoneExtn = phoneExtn;
	}
	public String getFaxAreaCode() {
		return faxAreaCode;
	}
	public void setFaxAreaCode(String faxAreaCode) {
		this.faxAreaCode = faxAreaCode;
	}
	public String getFaxPart1() {
		return faxPart1;
	}
	public void setFaxPart1(String faxPart1) {
		this.faxPart1 = faxPart1;
	}
	public String getFaxPart2() {
		return faxPart2;
	}
	public void setFaxPart2(String faxPart2) {
		this.faxPart2 = faxPart2;
	}
	public String getBusinessStructure() {
		return businessStructure;
	}
	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	public String getSizeOfCompany() {
		return sizeOfCompany;
	}
	public void setSizeOfCompany(String sizeOfCompany) {
		this.sizeOfCompany = sizeOfCompany;
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
	public String getBusinessStarted() {
		return businessStarted;
	}
	public void setBusinessStarted(String businessStarted) {
		this.businessStarted = businessStarted;
	}
	public String getBusinessStreet1() {
		return businessStreet1;
	}
	public void setBusinessStreet1(String businessStreet1) {
		this.businessStreet1 = businessStreet1;
	}
	public String getBusinessStreet2() {
		return businessStreet2;
	}
	public void setBusinessStreet2(String businessStreet2) {
		this.businessStreet2 = businessStreet2;
	}
	public String getBusinessCity() {
		return businessCity;
	}
	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}
	public String getBusinessState() {
		return businessState;
	}
	public void setBusinessState(String businessState) {
		this.businessState = businessState;
	}
	public String getBusinessZip() {
		return businessZip;
	}
	public void setBusinessZip(String businessZip) {
		this.businessZip = businessZip;
	}
	public String getBusinessAprt() {
		return businessAprt;
	}
	public void setBusinessAprt(String businessAprt) {
		this.businessAprt = businessAprt;
	}
	public boolean isMailAddressChk() {
		return mailAddressChk;
	}
	public void setMailAddressChk(boolean mailAddressChk) {
		this.mailAddressChk = mailAddressChk;
	}
	public String getMailingStreet1() {
		return mailingStreet1;
	}
	public void setMailingStreet1(String mailingStreet1) {
		this.mailingStreet1 = mailingStreet1;
	}
	public String getMailingStreet2() {
		return mailingStreet2;
	}
	public void setMailingStreet2(String mailingStreet2) {
		this.mailingStreet2 = mailingStreet2;
	}
	public String getMailingCity() {
		return mailingCity;
	}
	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}
	public String getMailingState() {
		return mailingState;
	}
	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}
	public String getMailingZip() {
		return mailingZip;
	}
	public void setMailingZip(String mailingZip) {
		this.mailingZip = mailingZip;
	}
	public String getMailingAprt() {
		return mailingAprt;
	}
	public void setMailingAprt(String mailingAprt) {
		this.mailingAprt = mailingAprt;
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
	public String getBusPhoneNo1() {
		return busPhoneNo1;
	}
	public void setBusPhoneNo1(String busPhoneNo1) {
		this.busPhoneNo1 = busPhoneNo1;
	}
	public String getBusPhoneNo2() {
		return busPhoneNo2;
	}
	public void setBusPhoneNo2(String busPhoneNo2) {
		this.busPhoneNo2 = busPhoneNo2;
	}
	public String getBusPhoneNo3() {
		return busPhoneNo3;
	}
	public void setBusPhoneNo3(String busPhoneNo3) {
		this.busPhoneNo3 = busPhoneNo3;
	}
	public String getBusExtn() {
		return busExtn;
	}
	public void setBusExtn(String busExtn) {
		this.busExtn = busExtn;
	}
	public String getMobPhoneNo1() {
		return mobPhoneNo1;
	}
	public void setMobPhoneNo1(String mobPhoneNo1) {
		this.mobPhoneNo1 = mobPhoneNo1;
	}
	public String getMobPhoneNo2() {
		return mobPhoneNo2;
	}
	public void setMobPhoneNo2(String mobPhoneNo2) {
		this.mobPhoneNo2 = mobPhoneNo2;
	}
	public String getMobPhoneNo3() {
		return mobPhoneNo3;
	}
	public void setMobPhoneNo3(String mobPhoneNo3) {
		this.mobPhoneNo3 = mobPhoneNo3;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List getBusinessStructureList() {
		return businessStructureList;
	}
	public void setBusinessStructureList(List businessStructureList) {
		this.businessStructureList = businessStructureList;
	}
	public List getPrimaryIndList() {
		return primaryIndList;
	}
	public void setPrimaryIndList(List primaryIndList) {
		this.primaryIndList = primaryIndList;
	}
	public List getSizeOfCompanyList() {
		return sizeOfCompanyList;
	}
	public void setSizeOfCompanyList(List sizeOfCompanyList) {
		this.sizeOfCompanyList = sizeOfCompanyList;
	}
	public List getAnnualSalesRevenueList() {
		return annualSalesRevenueList;
	}
	public void setAnnualSalesRevenueList(List annualSalesRevenueList) {
		this.annualSalesRevenueList = annualSalesRevenueList;
	}
	public List getRoleWithinCompanyList() {
		return roleWithinCompanyList;
	}
	public void setRoleWithinCompanyList(List roleWithinCompanyList) {
		this.roleWithinCompanyList = roleWithinCompanyList;
	}
	public List getHowDidYouHearList() {
		return howDidYouHearList;
	}
	public void setHowDidYouHearList(List howDidYouHearList) {
		this.howDidYouHearList = howDidYouHearList;
	}
	public List getStateTypeList() {
		return stateTypeList;
	}
	public void setStateTypeList(List stateTypeList) {
		this.stateTypeList = stateTypeList;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getBuyerContactResourceId() {
		return buyerContactResourceId;
	}
	public void setBuyerContactResourceId(Integer buyerContactResourceId) {
		this.buyerContactResourceId = buyerContactResourceId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isValidateState() {
		return validateState;
	}

	public void setValidateState(boolean validateState) {
		this.validateState = validateState;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getHomeAddressInd() {
		return homeAddressInd;
	}

	public void setHomeAddressInd(String homeAddressInd) {
		this.homeAddressInd = homeAddressInd;
	}

	public String getTermsAndCondition() {
		return termsAndCondition;
	}

	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}

	public String getServiceLiveBucksInd() {
		return serviceLiveBucksInd;
	}

	public void setServiceLiveBucksInd(String serviceLiveBucksInd) {
		this.serviceLiveBucksInd = serviceLiveBucksInd;
	}

	public boolean isSimpleBuyerInd() {
		return simpleBuyerInd;
	}

	public void setSimpleBuyerInd(boolean simpleBuyerInd) {
		this.simpleBuyerInd = simpleBuyerInd;
	}

	public String getServiceLiveBucksText() {
		return serviceLiveBucksText;
	}

	public void setServiceLiveBucksText(String serviceLiveBucksText) {
		this.serviceLiveBucksText = serviceLiveBucksText;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getDeepLinkKey() {
		return deepLinkKey;
	}

	public void setDeepLinkKey(String deepLinkKey) {
		this.deepLinkKey = deepLinkKey;
	}
    
	public int getPromotionalMailInd() {
		return promotionalMailInd;
	}

	public void setPromotionalMailInd(int promotionalMailInd) {
		this.promotionalMailInd = promotionalMailInd;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public boolean isApiFlag() {
		return apiFlag;
	}

	public void setApiFlag(boolean apiFlag) {
		this.apiFlag = apiFlag;
	}
    
    public int getFundingTypeId() {
		return fundingTypeId;
	}

	public void setFundingTypeId(int fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	public String getEinNo() {
		return einNo;
	}

	public void setEinNo(String einNo) {
		this.einNo = einNo;
	}

	public String getSsnInd() {
		return ssnInd;
	}

	public void setSsnInd(String ssnInd) {
		this.ssnInd = ssnInd;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAltIdType() {
		return altIdType;
	}

	public void setAltIdType(String altIdType) {
		this.altIdType = altIdType;
	}

	public String getAltIdCountry() {
		return altIdCountry;
	}

	public void setAltIdCountry(String altIdCountry) {
		this.altIdCountry = altIdCountry;
	}

	public List getBuyerList() {
		return buyerList;
	}

	public void setBuyerList(List buyerList) {
		this.buyerList = buyerList;
	}

	public String getBuyerDropDownValue() {
		return buyerDropDownValue;
	}

	public void setBuyerDropDownValue(String buyerDropDownValue) {
		this.buyerDropDownValue = buyerDropDownValue;
	}

	public String getBuyerUserDropDownValue() {
		return buyerUserDropDownValue;
	}

	public void setBuyerUserDropDownValue(String buyerUserDropDownValue) {
		this.buyerUserDropDownValue = buyerUserDropDownValue;
	}

	public String getNewAdminResourceId() {
		return newAdminResourceId;
	}

	public void setNewAdminResourceId(String newAdminResourceId) {
		this.newAdminResourceId = newAdminResourceId;
	}

	public String getCurrentAdminResourceId() {
		return currentAdminResourceId;
	}

	public void setCurrentAdminResourceId(String currentAdminResourceId) {
		this.currentAdminResourceId = currentAdminResourceId;
	}

	public String getCurrentAdminFirstname() {
		return currentAdminFirstname;
	}

	public void setCurrentAdminFirstname(String currentAdminFirstname) {
		this.currentAdminFirstname = currentAdminFirstname;
	}

	public String getCurrentAdminLastName() {
		return currentAdminLastName;
	}

	public void setCurrentAdminLastName(String currentAdminLastName) {
		this.currentAdminLastName = currentAdminLastName;
	}

	public String getCurrentAdminUserName() {
		return currentAdminUserName;
	}

	public void setCurrentAdminUserName(String currentAdminUserName) {
		this.currentAdminUserName = currentAdminUserName;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getNewAdminFirstName() {
		return newAdminFirstName;
	}

	public void setNewAdminFirstName(String newAdminFirstName) {
		this.newAdminFirstName = newAdminFirstName;
	}

	public String getNewAdminLastName() {
		return newAdminLastName;
	}

	public void setNewAdminLastName(String newAdminLastName) {
		this.newAdminLastName = newAdminLastName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNewAdminUserName() {
		return newAdminUserName;
	}

	public void setNewAdminUserName(String newAdminUserName) {
		this.newAdminUserName = newAdminUserName;
	}




}
