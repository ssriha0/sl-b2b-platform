package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("UserProfile")
public class AdminAddEditUserDTO extends SerializedBaseDTO
{

	private static final long serialVersionUID = -2190566517731842965L;
	// General Information panel.
	@XStreamAlias("FirstName")
	private String firstName;
	
	@XStreamAlias("LastName")
	private String lastName;
	
	@XStreamAlias("MiddleName")
	private String middleName;
	
	@XStreamAlias("Suffix")
	private String suffix;
	
	@XStreamAlias("JobTitle")
	private String  jobTitle;
	
	@XStreamAlias("JobRole")
	private String  jobRole;
	
	@XStreamAlias("UserName")
	private String username;
	
	@XStreamAlias("Email")
	private String email;
	
	@XStreamAlias("EmailConfirm")
	private String emailConfirm;
	
	// Marketplace Preferences panel
	@XStreamAlias("CreateServiceOrders")
	private String createServiceOrders;
	
	@XStreamAlias("ManageFinances")
	private String manageFinances;
	
	@XStreamAlias("ManangeUsers")
	private String manageUsers;
	
	@XStreamAlias("ManangeCompanyProfile")
	private String manageCompanyProfile;
	
	@XStreamAlias("PhoneBusAreaCode")
	private String phoneBusinessAreaCode;
	
	@XStreamAlias("PhoneBusPart1")
	private String phoneBusinessPart1;
	
	@XStreamAlias("PhoneBusPart2")
	private String phoneBusinessPart2;
	
	@XStreamAlias("PhoneMobAreaCode")
	private String phoneMobileAreaCode;
	
	@XStreamAlias("PhoneMobPart1")
	private String phoneMobilePart1;
	
	@XStreamAlias("PhoneMobPart2")
	private String phoneMobilePart2;
	
	@XStreamAlias("MaxSpendLimit")
	private String maxSpendLimit;
	
	@XStreamAlias("Editable")
	private boolean editable;
	
	
	@XStreamAlias("NeverLoggedIn")
	private boolean neverLoggedIn;
	
	// Terms and Conditions panel
	@XStreamAlias("AcceptTermsCond")
	private String acceptTermsAndConditions;
	
	// **********  Lists for dropdowns and radio buttons ***************
	private List<LookupVO> jobRoleList = new ArrayList<LookupVO>();
	private List<ActivityVO> activitiesList = new ArrayList<ActivityVO>();
	private List<LabelValueBean> acceptYesNo = new ArrayList<LabelValueBean>();
	
	
	
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
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getEmailConfirm() {
		return emailConfirm;
	}
	public void setEmailConfirm(String emailConfirm) {
		if(emailConfirm != null){
			this.emailConfirm = emailConfirm.trim();
		}else{
			this.emailConfirm = emailConfirm;
		}
	}
	public String getCreateServiceOrders() {
		return createServiceOrders;
	}
	public void setCreateServiceOrders(String createServiceOrders) {
		this.createServiceOrders = createServiceOrders;
	}
	public String getManageFinances() {
		return manageFinances;
	}
	public void setManageFinances(String manageFinances) {
		this.manageFinances = manageFinances;
	}
	public String getManageUsers() {
		return manageUsers;
	}
	public void setManageUsers(String manageUsers) {
		this.manageUsers = manageUsers;
	}
	public String getManageCompanyProfile() {
		return manageCompanyProfile;
	}
	public void setManageCompanyProfile(String manageCompanyProfile) {
		this.manageCompanyProfile = manageCompanyProfile;
	}
	public String getPhoneBusinessAreaCode() {
		return phoneBusinessAreaCode;
	}
	public void setPhoneBusinessAreaCode(String phoneBusinessAreaCode) {
		this.phoneBusinessAreaCode = phoneBusinessAreaCode;
	}
	public String getPhoneBusinessPart1() {
		return phoneBusinessPart1;
	}
	public void setPhoneBusinessPart1(String phoneBusinessPart1) {
		this.phoneBusinessPart1 = phoneBusinessPart1;
	}
	public String getPhoneBusinessPart2() {
		return phoneBusinessPart2;
	}
	public void setPhoneBusinessPart2(String phoneBusinessPart2) {
		this.phoneBusinessPart2 = phoneBusinessPart2;
	}
	public String getPhoneMobileAreaCode() {
		return phoneMobileAreaCode;
	}
	public void setPhoneMobileAreaCode(String phoneMobileAreaCode) {
		this.phoneMobileAreaCode = phoneMobileAreaCode;
	}
	public String getPhoneMobilePart1() {
		return phoneMobilePart1;
	}
	public void setPhoneMobilePart1(String phoneMobilePart1) {
		this.phoneMobilePart1 = phoneMobilePart1;
	}
	public String getPhoneMobilePart2() {
		return phoneMobilePart2;
	}
	public void setPhoneMobilePart2(String phoneMobilePart2) {
		this.phoneMobilePart2 = phoneMobilePart2;
	}
	public String getMaxSpendLimit() {
		return maxSpendLimit;
	}
	public void setMaxSpendLimit(String maxSpendLimit) {
		this.maxSpendLimit = maxSpendLimit;
	}
	public String getAcceptTermsAndConditions() {
		return acceptTermsAndConditions;
	}
	public void setAcceptTermsAndConditions(String acceptTermsAndConditions) {
		this.acceptTermsAndConditions = acceptTermsAndConditions;
	}
	public List<LookupVO> getJobRoleList() {
		return jobRoleList;
	}
	public void setJobRoleList(List<LookupVO> jobRoleList) {
		this.jobRoleList = jobRoleList;
	}

	public List<ActivityVO> getActivitiesList() {
		return activitiesList;
	}
	public void setActivitiesList(List<ActivityVO> activitiesList) {
		this.activitiesList = activitiesList;
	}
	public List<LabelValueBean> getAcceptYesNo() {
		return acceptYesNo;
	}
	public void setAcceptYesNo(List<LabelValueBean> acceptYesNo) {
		this.acceptYesNo = acceptYesNo;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isNeverLoggedIn() {
		return neverLoggedIn;
	}
	public void setNeverLoggedIn(boolean neverLoggedIn) {
		this.neverLoggedIn = neverLoggedIn;
	}
	
}
