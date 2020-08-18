/**
 * 
 */
package com.newco.marketplace.web.dto.provider;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.vo.provider.ProviderActivityVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


/**
 * @author Covansys - Offshore
 *
 */
@XStreamAlias("MarketPlace")
public class MarketPlaceDTO extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3367328010139107874L;
	/**
	 * Variable for 'Manage User' and 'Manage Service Order' 
	 */
	@XStreamAlias("ManageUser")
	private String manageUser;
	@XStreamAlias("ManageSO")
	private String manageSO;
	
	@XStreamAlias("ResourceId")
	private String resourceID;
	/**
	 * Variables for the 'Communication Preferences'
	 */
	@XStreamAlias("BusPhone1")
	private String businessPhone1;
	@XStreamAlias("BusPhone2")
	private String businessPhone2;
	@XStreamAlias("BusPhone3")
	private String businessPhone3;
	@XStreamAlias("BusPhoneExtn")
	private String businessExtn;
	
	@XStreamAlias("MobPhone1")
	private String mobilePhone1;
	@XStreamAlias("MobPhone2")
	private String mobilePhone2;
	@XStreamAlias("MobPhone3")
	private String mobilePhone3;
	
	@XStreamAlias("Email")
	private String email;
	@XStreamOmitField
	private String confirmEmail;
	@XStreamAlias("AltEmail")
	private String altEmail;
	@XStreamOmitField
	private String confirmAltEmail;
	
	@XStreamAlias("SmsAddress1")
	private String smsAddress1;
	@XStreamAlias("SmsAddress2")
	private String smsAddress2;
	@XStreamAlias("SmsAddress3")
	private String smsAddress3;
	
	@XStreamOmitField
	private String confirmSmsAddress1;
	@XStreamOmitField
	private String confirmSmsAddress2;
	@XStreamOmitField
	private String confirmSmsAddress3;
	
	@XStreamAlias("SecContact1")
	private Integer secondaryContact1 = -1;
	@XStreamAlias("SecContact2")
	private Integer secondaryContact2 = -1;
	
	@XStreamAlias("PrimaryContact")
	private Integer primaryContact = 1;
	
	@XStreamAlias("ServiceCall")
	private String serviceCall;
	@XStreamAlias("UserName")
	private String userName;
	@XStreamAlias("EditEmailInd")
	private String editEmailInd;
	
	@XStreamOmitField
	private List primaryContList = new ArrayList();
	@XStreamOmitField
	private List secondaryContList = new ArrayList();
	
	private List<UserActivityVO> activityList = new ArrayList();
	@XStreamOmitField
	private List generalActivityList = new ArrayList();
	@XStreamOmitField
	private List resultList = new ArrayList();
	
	private String entityId;
	private String roleId;
	private Integer loggedInResourceId;
	String action;
	/*
	 * Added for 'Provider Admin'
	 */
	@XStreamAlias("PrimaryIndicator")
	private String primaryIndicator;
	
	//R16_1: SL-18979: Variable to store vibes error, loginUserName
	private String vibesError = null;
	private String loginUserName = null;

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
	
	public void setIsChecked(int index , boolean isChecked) {
		((ProviderActivityVO)this.activityList.get(index)).setChecked(isChecked);
	}

	public boolean getIsChecked(int index ) {
		return ((ProviderActivityVO)this.activityList.get(index)).isChecked();
	}

	
	public List getGeneralActivityList() {
		return generalActivityList;
	}

	public void setGeneralActivityList(List generalActivityList) {
		this.generalActivityList = generalActivityList;
	}

	/**
	 * Getters And Setters
	 */
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
	
	public String getBusinessPhone1() {
		return businessPhone1;
	}

	public void setBusinessPhone1(String businessPhone1) {
		this.businessPhone1 = businessPhone1;
	}

	public String getBusinessPhone2() {
		return businessPhone2;
	}

	public void setBusinessPhone2(String businessPhone2) {
		this.businessPhone2 = businessPhone2;
	}

	public String getBusinessPhone3() {
		return businessPhone3;
	}

	public void setBusinessPhone3(String businessPhone3) {
		this.businessPhone3 = businessPhone3;
	}

	public String getBusinessExtn() {
		return businessExtn;
	}

	public void setBusinessExtn(String businessExtn) {
		this.businessExtn = businessExtn;
	}

	public String getMobilePhone1() {
		return mobilePhone1;
	}

	public void setMobilePhone1(String mobilePhone1) {
		this.mobilePhone1 = mobilePhone1;
	}

	public String getMobilePhone2() {
		return mobilePhone2;
	}

	public void setMobilePhone2(String mobilePhone2) {
		this.mobilePhone2 = mobilePhone2;
	}

	public String getMobilePhone3() {
		return mobilePhone3;
	}

	public void setMobilePhone3(String mobilePhone3) {
		this.mobilePhone3 = mobilePhone3;
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

	public String getSmsAddress1() {
		return smsAddress1;
	}

	public void setSmsAddress1(String smsAddress1) {
		this.smsAddress1 = smsAddress1;
	}

	public String getSmsAddress2() {
		return smsAddress2;
	}

	public void setSmsAddress2(String smsAddress2) {
		this.smsAddress2 = smsAddress2;
	}

	public String getSmsAddress3() {
		return smsAddress3;
	}

	public void setSmsAddress3(String smsAddress3) {
		this.smsAddress3 = smsAddress3;
	}

	public String getConfirmSmsAddress1() {
		return confirmSmsAddress1;
	}

	public void setConfirmSmsAddress1(String confirmSmsAddress1) {
		this.confirmSmsAddress1 = confirmSmsAddress1;
	}

	public String getConfirmSmsAddress2() {
		return confirmSmsAddress2;
	}

	public void setConfirmSmsAddress2(String confirmSmsAddress2) {
		this.confirmSmsAddress2 = confirmSmsAddress2;
	}

	public String getConfirmSmsAddress3() {
		return confirmSmsAddress3;
	}

	public void setConfirmSmsAddress3(String confirmSmsAddress3) {
		this.confirmSmsAddress3 = confirmSmsAddress3;
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

	public List getActivityList() {
		return activityList;
	}

	public void setActivityList(List activityList) {
		this.activityList = activityList;
		for(int i=0;i<activityList.size();++i){
			((UserActivityVO)activityList.get(i)).setIndex(i+"");
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setSecondaryContact1(Integer secondaryContact1) {
		this.secondaryContact1 = secondaryContact1;
	}

	public void setSecondaryContact2(Integer secondaryContact2) {
		this.secondaryContact2 = secondaryContact2;
	}

	public void setPrimaryContact(Integer primaryContact) {
		this.primaryContact = primaryContact;
	}

	public Integer getSecondaryContact1() {
		return secondaryContact1;
	}

	public Integer getSecondaryContact2() {
		return secondaryContact2;
	}

	public Integer getPrimaryContact() {
		return primaryContact;
	}

	public String getEditEmailInd() {
		return editEmailInd;
	}

	public void setEditEmailInd(String editEmailInd) {
		this.editEmailInd = editEmailInd;
	}

	public String getResourceID() {
		return resourceID;
	}

	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Integer getLoggedInResourceId() {
		return loggedInResourceId;
	}

	public void setLoggedInResourceId(Integer loggedInResourceId) {
		this.loggedInResourceId = loggedInResourceId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getVibesError() {
		return vibesError;
	}

	public void setVibesError(String vibesError) {
		this.vibesError = vibesError;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	
	
}
