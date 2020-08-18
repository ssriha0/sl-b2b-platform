/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.vo.vibes.VendorResourceSmsSubscription;
import com.sears.os.vo.SerializableBaseVO;

public class MarketPlaceVO extends SerializableBaseVO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -557617049790867500L;
	/**
	 * Variable for 'Manage User' and 'Manage Service Order' 
	 */
	private String manageUser = null;
	private String manageSO = null;
	
	private String resourceId = null;
	
	/**
	 * Variables for the 'Communication Preferences'
	 */
	private String businessPhone = null;
	private String businessExtn = null;
	private String smsAddress = null; 
	
	private String mobilePhone = null;
	
	private String email = null;
	private String confirmEmail = null;
	private String altEmail = null;
	private String confirmAltEmail = null;
	
	private Integer secondaryContact1 = -1;
	private Integer secondaryContact2 = -1;
	
	private Integer primaryContact = -1;
	
	private String serviceCall = null;
	private String userName = null;
	private String editorUserName = null;
	private String contactID = null;
	private String roleID = null;
	private String activityID = null;
	private String roleActivityID = null;
	private String editEmailInd = null;
	private String entityId = null;
	private Integer loggedInResourceId = null;
	
	private List primaryContList = new ArrayList();
	private List secondaryContList = new ArrayList();
	private List<ProviderActivityVO> activityList = new ArrayList();
	private List resultList = new ArrayList();
	
	/*
	 * Added for 'Provider Admin'
	 */
	private String primaryIndicator = null;
	
	//for SMS subscription
	private String firstName = null;
	private String lastName = null;
	
	private String otherJobTitle;
	
	//R16_1: SL-18979: Variable to store vibes error,statusCode, response, loggedInUserName, smsSubscription
	private String vibesError = null;
	private int vibesStatusCode =0;
	private String vibesResponse = null;
	private String loggedInUserName = null;
	private VendorResourceSmsSubscription smsSubscription = null;

	

	public String getOtherJobTitle() {
		return otherJobTitle;
	}

	public void setOtherJobTitle(String otherJobTitle) {
		this.otherJobTitle = otherJobTitle;
	}

	public String getPrimaryIndicator() {
		return primaryIndicator;
	}

	public void setPrimaryIndicator(String primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public List getActivityList() {
		return activityList;
	}

	public void setActivityList(List activityList) {
		this.activityList = activityList;
	}

	public List getPrimaryContList() {
		return primaryContList;
	}

	public void setPrimaryContList(List primaryContList) {
		this.primaryContList = primaryContList;
	}

	public List getSecondaryContList() {
		return secondaryContList;
	}

	public void setSecondaryContList(List secondaryContList) {
		this.secondaryContList = secondaryContList;
	}	

	public String getBusinessExtn() {
		return businessExtn;
	}

	public void setBusinessExtn(String businessExtn) {
		this.businessExtn = businessExtn;
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

	public String getConfirmAltEmail() {
		return confirmAltEmail;
	}

	public void setConfirmAltEmail(String confirmAltEmail) {
		this.confirmAltEmail = confirmAltEmail;
	}
	
	public String getServiceCall() {
		return serviceCall;
	}

	public void setServiceCall(String serviceCall) {
		this.serviceCall = serviceCall;
	}
	public String getManageUser() {
		return manageUser;
	}

	public void setManageUser(String manageUser) {
		this.manageUser = manageUser;
	}

	public String getManageSO() {
		return manageSO;
	}

	public void setManageSO(String manageSO) {
		this.manageSO = manageSO;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}

	public Integer getSecondaryContact1() {
		return secondaryContact1;
	}

	public void setSecondaryContact1(Integer secondaryContact1) {
		this.secondaryContact1 = secondaryContact1;
	}

	public Integer getSecondaryContact2() {
		return secondaryContact2;
	}

	public void setSecondaryContact2(Integer secondaryContact2) {
		this.secondaryContact2 = secondaryContact2;
	}

	public Integer getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(Integer primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}

	public String getRoleActivityID() {
		return roleActivityID;
	}

	public void setRoleActivityID(String roleActivityID) {
		this.roleActivityID = roleActivityID;
	}

	public String getSmsAddress() {
		return smsAddress;
	}

	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}
	
	public String getResourceID() {
		return resourceId;
	}

	public void setResourceID(String resourceID) {
		this.resourceId = resourceID;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getEditEmailInd() {
		return editEmailInd;
	}

	public void setEditEmailInd(String editEmailInd) {
		this.editEmailInd = editEmailInd;
	}

	public String getEditorUserName() {
		return editorUserName;
	}

	public void setEditorUserName(String editorUserName) {
		this.editorUserName = editorUserName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Integer getLoggedInResourceId() {
		return loggedInResourceId;
	}

	public void setLoggedInResourceId(Integer loggedInResourceId) {
		this.loggedInResourceId = loggedInResourceId;
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

	public String getVibesError() {
		return vibesError;
	}

	public void setVibesError(String vibesError) {
		this.vibesError = vibesError;
	}

	public int getVibesStatusCode() {
		return vibesStatusCode;
	}

	public void setVibesStatusCode(int vibesStatusCode) {
		this.vibesStatusCode = vibesStatusCode;
	}

	public String getVibesResponse() {
		return vibesResponse;
	}

	public void setVibesResponse(String vibesResponse) {
		this.vibesResponse = vibesResponse;
	}

	public String getLoggedInUserName() {
		return loggedInUserName;
	}

	public void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
	}

	public VendorResourceSmsSubscription getSmsSubscription() {
		return smsSubscription;
	}

	public void setSmsSubscription(VendorResourceSmsSubscription smsSubscription) {
		this.smsSubscription = smsSubscription;
	}


}