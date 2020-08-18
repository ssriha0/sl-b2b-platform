/**
 *
 */
package com.newco.marketplace.vo.mobile;


import java.util.List;



public class UserProfileData {


	private static final long serialVersionUID = 1L;

	private String leadPartnerId;
	
	private String leadStatus;
	
	private Integer firmId;
	
	private Integer resourceId; 
	
	private String inLaunchMarket;
	
	private String primaryIndustry;
	
	private String companyName;
	
	private String userName;

	private String password;
	
	private Integer roleId;
	
	private String userRoleType;
	
	private Integer  contactId;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneNo;
	
	private String mobileNo;
	
	private String email ;
	
	private String emailAlt;
	
	private Integer generatedPwdInd;
	
	private boolean flagForUsername;
 
	public boolean isFlagForUsername() {
		return flagForUsername;
	}

	public void setFlagForUsername(boolean flagForUsername) {
		this.flagForUsername = flagForUsername;
	}

	private List<LocationResponseData> listOflocations;

	public String getLeadPartnerId() {
		return leadPartnerId;
	}

	public void setLeadPartnerId(String leadPartnerId) {
		this.leadPartnerId = leadPartnerId;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserRoleType() {
		return userRoleType;
	}

	public void setUserRoleType(String userRoleType) {
		this.userRoleType = userRoleType;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailAlt() {
		return emailAlt;
	}

	public void setEmailAlt(String emailAlt) {
		this.emailAlt = emailAlt;
	}

	public List<LocationResponseData> getListOflocations() {
		return listOflocations;
	}

	public void setListOflocations(List<LocationResponseData> listOflocations) {
		this.listOflocations = listOflocations;
	}

	public String getInLaunchMarket() {
		return inLaunchMarket;
	}

	public void setInLaunchMarket(String inLaunchMarket) {
		this.inLaunchMarket = inLaunchMarket;
	}

	public Integer getGeneratedPwdInd() {
		return generatedPwdInd;
	}

	public void setGeneratedPwdInd(Integer generatedPwdInd) {
		this.generatedPwdInd = generatedPwdInd;
	}
}


	

	

