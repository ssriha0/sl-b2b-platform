package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.permission.UserRoleVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * $Revision: 1.5 $ $Author: gjacks8 $ $Date: 2008/05/29 13:29:07 $
 */
@XStreamAlias("BuyerUserProfile")
public class BuyerAdminManageUsersAddEditDTO extends SerializedBaseDTO {

	private static final long serialVersionUID = -3854500967763776961L;

	@XStreamAlias("FirstName")
	private String firstName;
	@XStreamAlias("MiddleName")
	private String middleName;
	@XStreamAlias("LastName")
	private String lastName;
	@XStreamAlias("Suffix")
	private String suffix;
	@XStreamAlias("JobRoleList")
	private List<UserRoleVO> jobRoleList;
	@XStreamAlias("ActivityList")
	private List<ActivityVO> activitiesList;
	@XStreamAlias("JobTitle")
	private String jobTitle;
	@XStreamAlias("UserName")
	private String userName;
	@XStreamOmitField
	private String userNameConfirmation;
	@XStreamAlias("BusPhoneArea")
	private String phoneBusinessAreaCode;
	@XStreamAlias("BusPhonePart1")
	private String phoneBusinessPart1;
	@XStreamAlias("BusPhonePart2")
	private String phoneBusinessPart2;
	@XStreamAlias("BusPhoneExt")
	private String phoneBusinessExt;
	@XStreamAlias("BusFaxArea")
	private String faxBusinessAreaCode;
	@XStreamAlias("BusFaxPart1")
	private String faxBusinessPart1;
	@XStreamAlias("BusFaxPart2")
	private String faxBusinessPart2;
	@XStreamAlias("MobFaxArea")
	private String phoneMobileAreaCode;
	@XStreamAlias("MobFaxPart1")
	private String phoneMobilePart1;
	@XStreamAlias("MobFaxPart2")
	private String phoneMobilePart2;
	@XStreamAlias("PrimaryEmail")
	private String priEmail;
	@XStreamOmitField
	private String confirmPriEmail;
	@XStreamAlias("AltEmail")
	private String altEmail;
	@XStreamOmitField
	private String confirmAltEmail;
	@XStreamAlias("MaxSpendLimit")
	private String maxSpendLimit;
	@XStreamOmitField
	private String acceptTermsAndConditions;
	@XStreamAlias("BuyerId")
	private Integer buyerId;
	@XStreamOmitField
	private Integer termsCondId;
	@XStreamAlias("Editable")
	private boolean editable;
	
	@XStreamAlias("NeverLoggedIn")
	private boolean neverLoggedIn;
	
	public boolean isNeverLoggedIn() {
		return neverLoggedIn;
	}
	public void setNeverLoggedIn(boolean neverLoggedIn) {
		this.neverLoggedIn = neverLoggedIn;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getPhoneBusinessExt() {
		return phoneBusinessExt;
	}
	public void setPhoneBusinessExt(String phoneBusinessExt) {
		this.phoneBusinessExt = phoneBusinessExt;
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
	public String getPriEmail() {
		return priEmail;
	}
	public void setPriEmail(String priEmail) {
		if(priEmail != null){
			this.priEmail = priEmail.trim();
		}else{
			this.priEmail = priEmail;
		}		
	}
	public String getConfirmPriEmail() {
		return confirmPriEmail;
	}
	public void setConfirmPriEmail(String confirmPriEmail) {
		if(confirmPriEmail != null){
			this.confirmPriEmail = confirmPriEmail.trim();
		}else{
			this.confirmPriEmail = confirmPriEmail;
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
	public String getConfirmAltEmail() {
		return confirmAltEmail;
	}
	public void setConfirmAltEmail(String confirmAltEmail) {
		if(confirmAltEmail != null){
			this.confirmAltEmail = confirmAltEmail.trim();
		}else{
			this.confirmAltEmail = confirmAltEmail;
		}		
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
	public List<ActivityVO> getActivitiesList() {
		return activitiesList;
	}
	public void setActivitiesList(List<ActivityVO> activitiesList) {
		this.activitiesList = activitiesList;
	}
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	/**
	 * @return the termsCondId
	 */
	public Integer getTermsCondId() {
		return termsCondId;
	}
	/**
	 * @param termsCondId the termsCondId to set
	 */
	public void setTermsCondId(Integer termsCondId) {
		this.termsCondId = termsCondId;
	}
	public List<UserRoleVO> getJobRoleList() {
		return jobRoleList;
	}
	public void setJobRoleList(List<UserRoleVO> jobRoleList) {
		this.jobRoleList = jobRoleList;
	}
	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getUserNameConfirmation() {
		return userNameConfirmation;
	}
	public void setUserNameConfirmation(String userNameConfirmation) {
		this.userNameConfirmation = userNameConfirmation;
	}
	/**
	 * @return the faxBusinessAreaCode
	 */
	public String getFaxBusinessAreaCode() {
		return faxBusinessAreaCode;
	}
	/**
	 * @param faxBusinessAreaCode the faxBusinessAreaCode to set
	 */
	public void setFaxBusinessAreaCode(String faxBusinessAreaCode) {
		this.faxBusinessAreaCode = faxBusinessAreaCode;
	}
	/**
	 * @return the faxBusinessPart1
	 */
	public String getFaxBusinessPart1() {
		return faxBusinessPart1;
	}
	/**
	 * @param faxBusinessPart1 the faxBusinessPart1 to set
	 */
	public void setFaxBusinessPart1(String faxBusinessPart1) {
		this.faxBusinessPart1 = faxBusinessPart1;
	}
	/**
	 * @return the faxBusinessPart2
	 */
	public String getFaxBusinessPart2() {
		return faxBusinessPart2;
	}
	/**
	 * @param faxBusinessPart2 the faxBusinessPart2 to set
	 */
	public void setFaxBusinessPart2(String faxBusinessPart2) {
		this.faxBusinessPart2 = faxBusinessPart2;
	}
}
