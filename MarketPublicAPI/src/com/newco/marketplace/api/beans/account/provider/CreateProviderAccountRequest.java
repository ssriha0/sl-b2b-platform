package com.newco.marketplace.api.beans.account.provider;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XmlRootElement(name = "createProviderAccountRequest")
@XStreamAlias("createProviderAccountRequest")
public class CreateProviderAccountRequest {
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.ProviderAccount.NAMESPACE+"/createProviderAccountRequest.xsd";
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.ProviderAccount.NAMESPACE+"/createProviderAccountRequest";
	
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	//Business Information
	@XStreamAlias("legalBusinessName")
	private String legalBusinessName;
	
	@XStreamAlias("DBAName")
	private String DBAName;
	
	@XStreamAlias("primaryIndustry")
	private String primaryIndustry;
	

    @XStreamAlias("otherPrimaryService")
	private String otherPrimaryService;
	
	public String getOtherPrimaryService() {
		return otherPrimaryService;
	}
	public void setOtherPrimaryService(String otherPrimaryService) {
		this.otherPrimaryService = otherPrimaryService;
	}
	@XStreamAlias("websiteAddress")
	private String websiteAddress;
	
	@XStreamAlias("mainBusiPhoneNo1")
	private String mainBusiPhoneNo1;
	
	@XStreamAlias("mainBusinessExtn")
	private String mainBusinessExtn;
	
	@XStreamAlias("businessFax1")
	private String businessFax1;
	
	//Business Address	
	@XStreamAlias("businessStreet1")
	private String businessStreet1;
	
	@XStreamAlias("businessStreet2")
	private String businessStreet2;
	
	@XStreamAlias("businessCity")
	private String businessCity;
	
	@XStreamAlias("businessState")
	private String businessState;
	
	@XStreamAlias("businessZip")
	private String businessZip;
	
	@XStreamAlias("businessAprt")
	private String businessAprt;
	
	//Mailing Address
	@XStreamAlias("mailAddressChk")
	private String mailAddressChk;
	
	@XStreamAlias("mailingStreet1")
	private String mailingStreet1;
	
	@XStreamAlias("mailingStreet2")
	private String mailingStreet2;
	
	@XStreamAlias("mailingCity")
	private String mailingCity;
	
	@XStreamAlias("mailingState")
	private String mailingState;
	
	@XStreamAlias("mailingZip")
	private String mailingZip;
	
	@XStreamAlias("mailingAprt")
	private String mailingAprt;
	
	//Primary Contact Information - Administrator 
	@XStreamAlias("howDidYouHear")
	private String howDidYouHear;
	
	@XStreamAlias("promotionCode")
	private String promotionCode;
	
	@XStreamAlias("roleWithinCom")
	private String roleWithinCom;
	
	@XStreamAlias("jobTitle")
	private String jobTitle;
	
	@XStreamAlias("serviceCallInd")
	private String serviceCallInd;
	
	@XStreamAlias("firstName")
	private String firstName;
	
	@XStreamAlias("middleName")
	private String middleName;
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("nameSuffix")
	private String nameSuffix;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("confirmEmail")
	private String confirmEmail;
	
	@XStreamAlias("altEmail")
	private String altEmail;
	
	@XStreamAlias("confAltEmail")
	private String confAltEmail;
	
	@XStreamAlias("userName")
	private String userName;
	//SL 16934 Added to hold referral code in the request object
	@XStreamAlias("referralCode")
	private String referralCode;
	
	public String getReferralCode() {
		return referralCode;
	}
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getSchemaInstance() {
		return schemaInstance;
	}
	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}
	@XmlElement(required=true)
	public String getLegalBusinessName() {
		return legalBusinessName;
	}
	public void setLegalBusinessName(String legalBusinessName) {
		this.legalBusinessName = legalBusinessName;
	}
	public String getDBAName() {
		return DBAName;
	}
	public void setDBAName(String dBAName) {
		DBAName = dBAName;
	}
	@XmlElement(required=true)
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	public String getWebsiteAddress() {
		return websiteAddress;
	}
	public void setWebsiteAddress(String websiteAddress) {
		this.websiteAddress = websiteAddress;
	}
	@XmlElement(required=true)
	public String getMainBusiPhoneNo1() {
		return mainBusiPhoneNo1;
	}
	public void setMainBusiPhoneNo1(String mainBusiPhoneNo1) {
		this.mainBusiPhoneNo1 = mainBusiPhoneNo1;
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
	@XmlElement(required=true)
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
	public String getMailAddressChk() {
		return mailAddressChk;
	}
	public void setMailAddressChk(String mailAddressChk) {
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
	
	public String getServiceCallInd() {
		return serviceCallInd;
	}
	public void setServiceCallInd(String serviceCallInd) {
		this.serviceCallInd = serviceCallInd;
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
	@Override
    public String toString() {
        return "CreateProviderAccountRequest [legalBusinessName=" + legalBusinessName + ",DBAName=" + DBAName + ",primaryIndustry=" + primaryIndustry + ",websiteAddress="
        		+ websiteAddress + ",mainBusiPhoneNo1=" + mainBusiPhoneNo1 + ",mainBusinessExtn=" + mainBusinessExtn + ",businessFax1=" + businessFax1
        		+ ",businessStreet1=" + businessStreet1 + ",businessStreet2=" + businessStreet2 + ",businessCity=" + businessCity + ",businessState=" + businessState 
        		+ ",businessZip=" + businessZip + ",businessAprt=" + businessAprt + ",mailingStreet1=" + mailingStreet1 + ",mailingStreet2" + mailingStreet2 
        		+ ",mailingCity=" + mailingCity + ",mailingState =" + mailingState + ",mailingZip=" + mailingZip + ",mailingAprt=" + mailingAprt 
        		+ ",howDidYouHear=" + howDidYouHear + ",promotionCode=" + promotionCode + ",roleWithinCom =" + roleWithinCom + ",jobTitle=" + jobTitle 
        		+ ",serviceCall=" + serviceCallInd + ",firstName=" + firstName + ",middleName=" + middleName + ",lastName=" + lastName 
        		+ ",nameSuffix=" + nameSuffix + ",email=" + email + ",confirmEmail=" + confirmEmail + ",altEmail=" + altEmail 
        		+ ",confAltEmail=" + confAltEmail + ",userName=" + userName + ",referralCode=" +referralCode+ "]";
    }	

}
