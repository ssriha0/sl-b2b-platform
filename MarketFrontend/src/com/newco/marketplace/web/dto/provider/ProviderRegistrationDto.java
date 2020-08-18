/**
 * 
 */
package com.newco.marketplace.web.dto.provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderRegistrationDto extends BaseDto 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//This is required for DB insert 
	private Integer vendorId;
	private Integer vendorContactResourceId;
	private String password;

	/**
	 * Variables for "Business Information"
	 */
    private String legalBusinessName;
    private String DBAName;
    private String websiteAddress;
    
    private String mainBusiPhoneNo1;
    private String mainBusiPhoneNo2;
    private String mainBusiPhoneNo3;
    private String mainBusinessExtn;
    
    private String businessFax1;
    private String businessFax2;
    private String businessFax3;
    
    private String primaryIndustry;
    private String otherPrimaryService;
    
    /**
     * Variables for "Business Address"
     */
    private String businessStreet1;
    private String businessStreet2;
    private String businessCity;
    private String businessState;
    private String businessZip;
    private String businessAprt;
    
    /**
     *	Variables for "Mailing Address" 
     */
    private boolean mailAddressChk;
    private String mailingStreet1;
    private String mailingStreet2;
    private String mailingCity;
    private String mailingState;
    private String mailingZip;
    private String mailingAprt;
    
    /**
     * variables for "Primary Contact Information - Administrator"
     */
    private String howDidYouHear;
    private String promotionCode;
    private String roleWithinCom;
    private String jobTitle;
    private String serviceCall;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameSuffix;
    private String email;
    private String confirmEmail;
    private String altEmail;
    private String confAltEmail;
    private String userName;
    
    /**
     * List variables
     */
    private List primaryIndList = new ArrayList();
    private List roleWithinCompany =  new ArrayList();
    private List howDidYouHearList =  new ArrayList();
    private List stateTypeList = new ArrayList();
    private boolean validateState = true;
    
    private String stateType;
    
    public String getStateType() {
		return stateType;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
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
		if(email != null){
			this.email = email.trim();
		}else{
			this.email = email;
		}		
	}
	
	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		if(confirmEmail != null){
			this.confirmEmail = confirmEmail.trim();
		}else{
			this.confirmEmail = confirmEmail;
		}		
	}
	
	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		if(altEmail != null){
			this.altEmail = altEmail.trim();
		}else{
			this.altEmail = altEmail;
		}
	}
	
	public String getConfAltEmail() {
		return confAltEmail;
	}

	public void setConfAltEmail(String confAltEmail) {
		if(confAltEmail != null){
			this.confAltEmail = confAltEmail.trim();
		}else{
			this.confAltEmail = confAltEmail;
		}		
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

}
