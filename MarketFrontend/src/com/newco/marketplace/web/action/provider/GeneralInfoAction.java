/*
 * $Id: GeneralInfoAction.java,v 1.38 2008/04/25 21:03:02 glacy Exp $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.newco.marketplace.web.action.provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.google.gwt.user.client.rpc.core.java.lang.boolean_Array_CustomFieldSerializer;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.IGeneralInfoDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.GeneralInfoDto;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.thoughtworks.xstream.XStream;
import com.google.gson.Gson;

/**
 * @author KSudhanshu
 */
@Validation
public class GeneralInfoAction extends ActionSupport implements ServletRequestAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IGeneralInfoDelegate iGeneralInfoDelegate;
	private GeneralInfoDto generalInfoDto;
	private IAuditLogDelegate auditLogDelegates;
	private String radChkUser;
	private static final Logger logger = Logger.getLogger(GeneralInfoAction.class.getName());
	private HttpServletRequest request;
	private Map sSessionMap;
	private HttpSession session;
	private  boolean errorOccured =false;
	private IManageUsersDelegate manageUsersDelegate;
	private InputStream inputStream;
	private int beginIndex=13;
	
	/**
	 * @param generalInfoDelegate
	 * @param generalInfoDto
	 */
	public GeneralInfoAction(IGeneralInfoDelegate generalInfoDelegate,
			GeneralInfoDto generalInfoDto) {
		iGeneralInfoDelegate = generalInfoDelegate;
		this.generalInfoDto = generalInfoDto;
	}
	
	private boolean validateBusinessStateZip() throws Exception
	{
		try
		{
			generalInfoDto.setStateType("business");
			generalInfoDto = iGeneralInfoDelegate.loadZipSet(generalInfoDto);
			List stateTypeList = generalInfoDto.getStateTypeList();
			if(!validateStateZip(stateTypeList, generalInfoDto.getDispAddZip()))
			{
				addFieldError("generalInfoDto.dispAddZip", "Address Zip doesn't match selected state");
				return false;
			}else if(null == generalInfoDto.getCoverageZip() || generalInfoDto.getCoverageZip().length == 0 || StringUtils.isBlank(generalInfoDto.getCoverageZip()[0])) {
				addFieldError("generalInfoDto.zipcodesCovered",
						"Please open the map in the Dispatch Address & Coverage area to confirm your zip code coverage.");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--Exception inside generalInfoDto.dispAddZip[validateBusinessStateZip]", a_Ex);
			throw a_Ex;
		}
		return true;
	}
	
	
	/**Description Code as part of R15_1 to validate start and end time for weekday availability
	 * @return
	 * @throws Exception
	 */
	private boolean validateStartEndTime() throws Exception
	{
		try
		{
			errorOccured =false;

			validateStartGreaterEndTime(generalInfoDto.getMonStart(),generalInfoDto.getMonEnd());
			validateStartGreaterEndTime(generalInfoDto.getTueStart(),generalInfoDto.getTueEnd());
			validateStartGreaterEndTime(generalInfoDto.getWedStart(),generalInfoDto.getWedEnd());
			validateStartGreaterEndTime(generalInfoDto.getThuStart(),generalInfoDto.getThuEnd());
			validateStartGreaterEndTime(generalInfoDto.getFriStart(),generalInfoDto.getFriEnd());
			validateStartGreaterEndTime(generalInfoDto.getSatStart(),generalInfoDto.getSatEnd());
			validateStartGreaterEndTime(generalInfoDto.getSunStart(),generalInfoDto.getSunEnd());	
		}
		catch(Exception a_Ex)
		{
			logger.error("--Exception inside GeneralInfoAction.[validateStartEndTime]", a_Ex);
			throw a_Ex;
		}
		return true;
	}

	/**
	 * 
	 */
	private boolean validateStartGreaterEndTime(String startTime,String endTime) {
		if(errorOccured)
		{
			return false;
		}
		
		Date startDateTime=covertTimeStringToDate(startTime);
		Date endstartDateTime = covertTimeStringToDate(endTime);
		if(null!=startDateTime && null!=endstartDateTime && !errorOccured)
		{
			if(startDateTime.after(endstartDateTime) || startDateTime.compareTo(endstartDateTime)==0)
			{
				addFieldError("generalInfoDto.monStart",Config
						.getResouceBundle().getString("Start_End_Time_Greater"));
				errorOccured =true;
				return false;
			}
		}
		return true;
	}
	
	
	
	
	private Date covertTimeStringToDate(String str){
		if (str!=null && str.trim().length()>0) {
			str="20070101:" + str;
			SimpleDateFormat timeStringformater = new SimpleDateFormat("yyyyMMdd:hh:mm a");
			try {
				return timeStringformater.parse(str);
			}catch (Exception e) {
				logger.error("covertTimeStringToDate failed to process for input inside GeneralInfoAction.[validateStartEndTime]");
				return null;
			}
		}
		return null;
	}
	
	
	private boolean validateForDuplicateUser() 
	{
		try
		{
			boolean isDuplicateUser=false;
           if(StringUtils.isNotBlank(generalInfoDto.getUserName())){
        	   isDuplicateUser = iGeneralInfoDelegate.isUserExist(generalInfoDto.getUserName(),generalInfoDto.getResourceId());
			
           }
           if(isDuplicateUser)
			{
				//Added as per JIRA#SL-20083 -- Change the field error attribute from registrationDto to generalInfoDto.
        	   addFieldError("generalInfoDto.userName", Config
        	            .getResouceBundle().getString("Duplicate_User_Name"));

				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--Exception inside generalInfoDto.dispUserName[validateForDuplicateUser]", a_Ex);
		}
		return true;
	}
	
	
	// SL-19667 validate if the resource having same personal info existing for the same firm
	private boolean validateForResourceHavingSamePersonalInfo() {
		try {
			boolean isDuplicateUser = false;
			isDuplicateUser = iGeneralInfoDelegate.isSameResourceExist(
						generalInfoDto);
			if (isDuplicateUser) {
				addFieldError("generalInfoDto.ssn", Config
						.getResouceBundle().getString("Same_resource"));
				return false;
			}
		} catch (Exception a_Ex) {
			logger.log(
					Level.ERROR,
					"--Exception inside validateForResourceHavingSamePersonalInfo]",
					a_Ex);
		}
		return true;
	}
	
	
	private boolean validateStateZip(List list, String zip)
	{	
		Iterator itr = list.iterator();
		boolean valid = false;
		while (itr.hasNext()) {
			String validZip = ((LocationVO) itr.next()).getZip();
			if (zip.equals(validZip)) {
				return true;
			}
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		logger.info("GeneralInfoAction.validate()");
			//Fix For Sears00047895 offshore
			if(ActionContext.getContext().getSession().get("resourceId") == null||(generalInfoDto.getUserName() != null&&generalInfoDto.getUserName().trim().length()>0))
			{
				validateUserName();
			}
			super.validate();
			firstNameValidation();
			lastNameValidation();
			validateForDuplicateUser();
			//sl-19667 check id the resource with same personal information already exist for a firm.
			validateForResourceHavingSamePersonalInfo();
		if (hasFieldErrors()) {
			//SL-19643: Fixing issue:
			generalInfoDto.setPopNovalue(false);
			ActionContext.getContext().getSession().put("popUpInd", 0);
			System.out.println("VALIDATION ERROR-------" + getFieldErrors());
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(generalInfoDto);
			baseTabDto.setFieldsError(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
			if(!StringUtils.isBlank(generalInfoDto.getUserName())){
				generalInfoDto.setEditUserName(true);
			}
		}
		
	}
	public void validateUserName()
	{
		if(radChkUser != null && radChkUser.equals("1"))
		{
			if(generalInfoDto.getUserName() != null)
			{
				String userName =generalInfoDto.getUserName().trim();
				if(userName.length()< 8 || userName.length()>100)
				{
					addFieldError("generalInfoDto.userName", "User Name can not be less than 8 and greater than 100 characters");
				}
			}
		}
	}
	
	private void firstNameValidation(){
		//check if first name and last name valued are the default warning text??if so then add validation errors.
		if (null != generalInfoDto.getFirstName() && generalInfoDto.getFirstName().equals("Enter your LEGAL first name")){
			addFieldError("generalInfoDto.firstName", "Please enter a First Name");
		}
	}
	
	private void lastNameValidation(){
		//check if first name and last name valued are the default warning text??if so then add validation errors.
				if (null != generalInfoDto.getLastName() && generalInfoDto.getLastName().equals("Enter your LEGAL last name")){
					addFieldError("generalInfoDto.lastName", "Please enter a Last Name");
				}
	}
	
	public void validateDispAddGeographicRange()
	{
		if(generalInfoDto.getDispAddGeographicRange() == null || generalInfoDto.getDispAddGeographicRange().trim().length()==0)
		{
			addFieldError("generalInfoDto.dispAddGeographicRange", "Please select a Geographical Range");
		}
		return;
	}
	
	/**
	 * 
	 * 
	 * @return boolean
	 * 
	 * **/
	private boolean validateSsn(){
		// Validating ssn
		logger.info("Inside validateSsn()");
		double value = 0;
		if (generalInfoDto.getSsnValInd()!=null && generalInfoDto.getSsnValInd().length()>0){
			if (generalInfoDto.getSsn()==null || generalInfoDto.getSsn().trim().length()==0){
				if (getText("generalInfoAction.generalInfoDto.ssn")!=null && getText("generalInfoAction.generalInfoDto.ssn").trim().length()>0) {
					addFieldError("generalInfoDto.ssn", getText("generalInfoAction.generalInfoDto.ssn"));
				} else {
					addFieldError("generalInfoDto.ssn", "generalInfoAction.generalInfoDto.ssn");
				}
				return false;
			} else {
			try {
			
				if(generalInfoDto.getSsnRight().trim().length()==0)
				{   
					if (getText("generalInfoAction.generalInfoDto.ssn")!=null && getText("generalInfoAction.generalInfoDto.ssn").trim().length()>0) {
						addFieldError("generalInfoDto.ssn", getText("generalInfoAction.generalInfoDto.ssn"));
					} else {
						addFieldError("generalInfoDto.ssn", "generalInfoAction.generalInfoDto.ssn");
					}
				}
				value = Double.parseDouble(generalInfoDto.getSsn());
				if (generalInfoDto.getSsn().length()!=9){
					throw new NumberFormatException();
				}
				if (generalInfoDto.getSsn().indexOf(".")>0){
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				if (getText("generalInfoAction.generalInfoDto.ssn")!=null && getText("generalInfoAction.generalInfoDto.ssn").trim().length()>0) {
					addFieldError("generalInfoDto.ssn", getText("generalInfoAction.generalInfoDto.ssn.format"));
				} else {
					addFieldError("generalInfoDto.ssn", "generalInfoAction.generalInfoDto.ssn.format");
				}
				return false;
			}
			}
		}
		
		return true;
	}
	
	public void currencyValidate() {
		
		String privacyValue = (generalInfoDto.getHourlyRate() != null) ? generalInfoDto.getHourlyRate().trim() : "";
		
		try
		{
			double val = Double.parseDouble(privacyValue);
		}catch(Exception a_Ex)
		{
			addFieldError("generalInfoDto.hourlyRate", "Preferred Billing Rate must be a number");
			return;
		}
		
		if(privacyValue.indexOf(".")!=-1){
			if(privacyValue.substring(privacyValue.indexOf("."),privacyValue.length()).length() > 3){
				addFieldError("generalInfoDto.hourlyRate", "In Preferred Billing Rate " + privacyValue + ", you have entered more than 2 digits after the decimal");
			}
			
			//Added later for allowing only 4 places before decimal - START
			if(privacyValue.substring(0,privacyValue.indexOf(".") ).length() > 4){
				addFieldError("generalInfoDto.hourlyRate", "In Preferred Billing Rate " + privacyValue + ", you have entered more than 4 digits before the decimal");
			}
			//Added later for allowing only 4 places before decimal - START
		}//End of outer IF

		//Added later for allowing only 4 places before decimal - START
		else if (privacyValue.length() > 4) {
			addFieldError("generalInfoDto.hourlyRate", "In Preferred Billing Rate " + privacyValue + ", you have entered more than 4 digits before the decimal");
		}
		//Added later for allowing only 4 places before decimal - START

	}//End of currencyValidate method
	
	/**
	 * @throws Exception
	 */
	private void saveData() throws Exception {
		System.out.println("GeneralInfoAction.saveData()");
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		
		try {
			
			Map paramMap = request.getParameterMap();
			for (Object objKey : paramMap.keySet()) {
				String[] values = (String[]) paramMap.get(objKey);
				String key = (String) objKey;
				
				if (key.toLowerCase().startsWith("sp_")) {
					StringTokenizer st = new StringTokenizer(key, "_");
					st.nextToken();
					int sproInd = new Integer(st.nextToken().trim());
					System.out.println("spro value here is==="+sproInd);
					generalInfoDto.setSproInd(sproInd);
				}
			}
			if(null !=baseTabDto && null!=baseTabDto.getDtObject()){
				this.generalInfoDto=(GeneralInfoDto) baseTabDto.getDtObject();
			}
			
			//R11_2
			//SL-20421
			if(StringUtils.isNotBlank(generalInfoDto.getFirstName())){
				generalInfoDto.setFirstName(generalInfoDto.getFirstName().trim());
			}
			if(StringUtils.isNotBlank(generalInfoDto.getLastName())){
				generalInfoDto.setLastName(generalInfoDto.getLastName().trim());
			}
			if(StringUtils.isNotBlank(generalInfoDto.getMiddleName())){
				generalInfoDto.setMiddleName(generalInfoDto.getMiddleName().trim());
			}
			
			//R11_2
			//Trimming username before saving in the database
			if(StringUtils.isNotBlank(generalInfoDto.getUserName())){
				generalInfoDto.setUserName(generalInfoDto.getUserName().trim());
			}
			
			if(StringUtils.isNotBlank(generalInfoDto.getUserNameAdmin())){
				generalInfoDto.setUserNameAdmin(generalInfoDto.getUserNameAdmin().trim());
			}
			
			String selectedCheckboxes=request.getParameter("selectedCheckBox");
			if(selectedCheckboxes!=null){
				 List<String> list = Arrays.asList(selectedCheckboxes.split("\\s*,\\s*"));
				Iterator<StateZipcodeVO> steitr=generalInfoDto.getStateZipCodeList().iterator();
				while(steitr.hasNext()){
					StateZipcodeVO state=steitr.next();
					if(list.contains("checkbox_"+state.getStateCd()))
						state.setLicenseConfirmation(true);
					else
						state.setLicenseConfirmation(false);
				}
			}
			
			generalInfoDto = iGeneralInfoDelegate.saveUserInfo(this.generalInfoDto);
			auditUserProfileLog(generalInfoDto);
			
			/**
			 * Added for Disabling Skills and Services page based on the Primary Indicator.
			 * Covansys - Offshore
			 */
			ActionContext.getContext().getSession().put("PrimaryIndicator", generalInfoDto.getResourceInd());
			
			/**
			 * Added for Displaying First Name and Last Name
			 */
			String resourName = generalInfoDto.getFirstName().trim() + " " + generalInfoDto.getLastName().trim(); 
			ActionContext.getContext().getSession().put("resourceName", resourName);
			
		}catch (DuplicateUserException due){
			System.out.println("EXCEPTION--" + due.getMessage());
			
			
			addFieldError("generalInfoDto.userName", due.getMessage());
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			
			ActionContext.getContext().getSession().put("editUser",false);
			
			baseTabDto.setDtObject(generalInfoDto);
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
			throw due;
		}
		ActionContext.getContext().getSession().put("resourceId",generalInfoDto.getResourceId());
		ActionContext.getContext().getSession().put("ProviderUser",generalInfoDto.getUserName());
		ActionContext.getContext().getSession().put("editUser",true);
		if (baseTabDto!=null) {
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"complete");
			//SL-20225 : if the resource is shared, update the background status a Complete
			if(null != generalInfoDto && generalInfoDto.isBgComplete()){
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,"complete");
			}
		    baseTabDto.getActionMessages().addAll(getActionMessages());
		}
		//R16.0 SL-20968
		String providerId = (String) ActionContext.getContext()
				.getSession().get("resourceId");
		if(null != generalInfoDto.getOldDispatcherInd() && generalInfoDto.getOldDispatcherInd().intValue() != generalInfoDto.getDispatchInd()){
			manageUsersDelegate.expireMobileTokenforFrontEnd(
					Integer.parseInt(providerId),
					MPConstants.ACTIVE);
		}
		
	}
	
	private void auditUserProfileLog(GeneralInfoDto generalInfoDto)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {GeneralInfoDto.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(generalInfoDto);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("PROVIDER_USER_PROFILE_EDIT");
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		if(securityContext!=null)
		{
			auditUserProfileVO.setLoginCompanyId(securityContext.getCompanyId());
			auditUserProfileVO.setLoginResourceId(securityContext.getVendBuyerResId());
			auditUserProfileVO.setRoleId(securityContext.getRoleId());
			if(securityContext.isSlAdminInd())
				auditUserProfileVO.setIsSLAdminInd(1);
			auditUserProfileVO.setModifiedBy(securityContext.getUsername());
			auditUserProfileVO.setUserProfileData(xmlContent);
			auditLogDelegates.auditUserProfile(auditUserProfileVO);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		System.out.println("GeneralInfoAction.execute()");
		String action = generalInfoDto.getAction();
		if("Save".equals(action)){
			return doSave();
		}
		if("Next".equals(action)){
			return doNext();
		}
		if("Update".equals(action)){
			return updateProfile();
		}
		/*try{
			
			if (!validateSsn()){
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(generalInfoDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
				return INPUT;
			}
			 state - zip valition
			validateBusinessStateZip();
			currencyValidate();
			validateDispAddGeographicRange();
			if (hasFieldErrors())
			{
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(generalInfoDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
				return INPUT;
			}
			//Called the validate method to validate the user name length-Covansys
			validate();
			
			//saveData();
			//add for header
			String resourceName=(String)ActionContext.getContext().getSession().get("resourceName");
			generalInfoDto.setFullResoueceName(resourceName); 
		}catch(DuplicateUserException e){
			return SUCCESS;
		}*/
		return SUCCESS;
	}
	


	
	@SkipValidation
	public String doLoad() throws Exception {
		System.out.println("GeneralInfoAction.doLoad()");
		String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
		generalInfoDto.setVendorId(vendorId);
		generalInfoDto.setResourceId(resourceId);
		generalInfoDto.setIsNotRemoveUser(0);
		generalInfoDto = iGeneralInfoDelegate.getUserInfo(generalInfoDto);
		if (resourceId!=null) 
			setZipCoverage(vendorId, resourceId);

		setUserEditable(generalInfoDto.getUserName());
		
		ActionContext.getContext().getSession().put("ProviderUser",generalInfoDto.getUserName());
		if (resourceId == null || resourceId.trim().length() <= 0)
		{
			ActionContext.getContext().getSession().put("resourceName", "");
		}
		
		//add for header
		String resourceName=(String)ActionContext.getContext().getSession().get("resourceName");
		
		generalInfoDto.setFullResoueceName(resourceName);
		
	
	String user= (String) sSessionMap.get("username");
		if (user.equalsIgnoreCase(generalInfoDto.getUserNameAdmin())){
			boolean tempFlag=true;
			generalInfoDto.setFlagAdmin(tempFlag);
		}
		else
		{
			boolean tempFlag=false;
			generalInfoDto.setFlagAdmin(tempFlag);
			
		}
		getSessionMessages();
		generalInfoDto.setMapboxUrl(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MAPBOX_URL));
		return "load";
		
	}
	public String removeUser() throws Exception
	{
		boolean flagTemp = iGeneralInfoDelegate.removeUser(generalInfoDto.getResourceId(), generalInfoDto.getUserName(), generalInfoDto.getFirstName(), generalInfoDto.getLastName(), generalInfoDto.getUserNameAdmin(), generalInfoDto.getVendorId());
		if(!flagTemp)
		{
		generalInfoDto.setIsNotRemoveUser(1);		
		addActionError(getText("generalInfoAction.generalInfoDto.isNotRemoveUser.message"));			
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.setDtObject(generalInfoDto);
		baseTabDto.getActionErrors().addAll(getActionErrors());
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"complete");
		return ERROR;
		
		}		
		
		return "remove_user";		
	}
	@SkipValidation
	public String doInput() throws Exception {
		System.out.println("GeneralInfoAction.doInput()");
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		// getting messages from session
		getSessionMessages();
		String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
		if (baseTabDto!= null) {
			
			GeneralInfoDto bDto = iGeneralInfoDelegate.getUserInfo(generalInfoDto);
			if (baseTabDto.getDtObject()!=null)
				generalInfoDto = (GeneralInfoDto)baseTabDto.getDtObject();
					
			if(bDto != null && generalInfoDto !=null)
//				generalInfoDto.setStatesList(bDto.getStatesList());
				generalInfoDto.setGeographicalRange(bDto.getGeographicalRange());
				generalInfoDto.setScheduleTimeList(bDto.getScheduleTimeList());	
				generalInfoDto.setUserExistInd(bDto.getUserExistInd());
				generalInfoDto.setMapboxUrl(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MAPBOX_URL));
			if (resourceId!=null && generalInfoDto.getZipcodesCovered()==null) 
				setZipCoverage(vendorId, resourceId);
			else
				ServletActionContext.getRequest().setAttribute("selectdzipcodesJson", generalInfoDto.getZipcodesCovered());
			
				
		}
		return "load";
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	
	public String doSave() throws Exception {
		logger.info("GeneralInfoAction.doSave()" );
		try{
			ActionContext.getContext().getSession().put("popUpInd", 0);
			ActionContext.getContext().getSession().put("fromNext", 0);
			// SLT-1475: Provider reg by Zipcode Coverage
			//this.generalInfoDto=(GeneralInfoDto) ActionContext.getContext().getSession().get("providerdetailsDto");
			if (StringUtils.isNotEmpty(generalInfoDto.getZipcodesCovered()))
				setZipCodesCovered();
		   if (!validateSsn()){
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(generalInfoDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
				return INPUT;
			}
			validateBusinessStateZip(); 
			currencyValidate();
			validateDispAddGeographicRange();
			Map paramMap = request.getParameterMap();
			
			validateStartEndTime();
			
			if (hasFieldErrors())
			{
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(generalInfoDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
				return INPUT;
			}
			else{
				//set  pop up indicator,set this.generalInfoDto in session.
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(generalInfoDto);
				//String[] oldZipValues=(String[])ActionContext.getContext().getSession().get("oldCoverageZips");
				//if(generalInfoDto.getCoverageZip()!=null&& oldZipValues!=null && 
					//	!Arrays.deepEquals(generalInfoDto.getCoverageZip(), oldZipValues)){
				List<StateZipcodeVO> stateNameToDisplay = validateStateZipList();
					
				//}
				//Assume the flow from add new user
				if(StringUtils.isBlank(generalInfoDto.getResourceId())){
					//SSN is entering first time,must show pop up
				  if(StringUtils.isBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){
					   if(!StringUtils.isBlank(generalInfoDto.getUserName())){
							generalInfoDto.setEditUserName(true);
						}
						ActionContext.getContext().getSession().put("popUpInd", 1);
						String fullname=generalInfoDto.getFirstName()+" "+generalInfoDto.getMiddleName()+" "+generalInfoDto.getLastName();
					    ActionContext.getContext().getSession().put("providerLegalName", fullname);
					    String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());	
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
						
					
				  }//SSN old and new there,ie:he clicked No in pop up
					if(StringUtils.isNotBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn()))
					{  if(!generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						if(!StringUtils.isBlank(generalInfoDto.getUserName())){
							generalInfoDto.setEditUserName(true);
						}
						ActionContext.getContext().getSession().put("popUpInd", 1);
						String fullname=generalInfoDto.getFirstName()+" "+generalInfoDto.getMiddleName()+" "+generalInfoDto.getLastName();
					    ActionContext.getContext().getSession().put("providerLegalName", fullname);
					    String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					  }else if(generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						  /*SSN old and New are equal.Later point of time, we might have display pop up.Now doing nothing
						   * We are checking the value of PopNovalue which declared by default as true.Become true only when yes button in pop up is clicked */
						 if(!generalInfoDto.isPopNovalue()){
							 if(!StringUtils.isBlank(generalInfoDto.getUserName())){
									generalInfoDto.setEditUserName(true);
								}
							 ActionContext.getContext().getSession().put("popUpInd", 1);
							 String fullname=generalInfoDto.getFirstName()+" "+generalInfoDto.getMiddleName()+" "+generalInfoDto.getLastName();
							 ActionContext.getContext().getSession().put("providerLegalName", fullname);
							 String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
							 String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
							 ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
							 generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
							 generalInfoDto.setPopNovalue(false);
							 ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
						 }
					  }
					}
					generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());	
				}
				/*This is to handle edit flow,clicked on save
				 * */
				else if (!(generalInfoDto.isLockedSsn()) && StringUtils.isNotBlank(generalInfoDto.getResourceId())){
					
					//old SSN will be blank,new SSN must have values
					if(StringUtils.isBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){
					{if(!StringUtils.isBlank(generalInfoDto.getUserName())){
							generalInfoDto.setEditUserName(true);
						}
						ActionContext.getContext().getSession().put("popUpInd", 2);
						String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
						
					}
				  }	
				//Assume he entered SSN and Old value there
				else if(StringUtils.isNotBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){
					if(!generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						if(!StringUtils.isBlank(generalInfoDto.getUserName())){
							generalInfoDto.setEditUserName(true);
						}
						ActionContext.getContext().getSession().put("popUpInd", 2);
						String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					}  /*SSN old and New are equal.Later point of time, we might have display pop up.Now doing nothing
					   * We are checking the value of PopNovalue which declared by default as true.Become true again only when yes button in pop up is clicked */
					else if(generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						if(!generalInfoDto.isPopNovalue()){
							if(!StringUtils.isBlank(generalInfoDto.getUserName())){
								generalInfoDto.setEditUserName(true);
							}
						ActionContext.getContext().getSession().put("popUpInd", 2);
						String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					}
						}
				  }else if(stateNameToDisplay!=null&& stateNameToDisplay.size()>0){
						return showOutOfStatePopUp(stateNameToDisplay);
					}
					//First time without editing ssn,SSN old and new are blank.
					else if(StringUtils.isBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isBlank(generalInfoDto.getSsn())){
						generalInfoDto.setEditUserName(false);
						saveData();
						ActionContext.getContext().getSession().put("popUpInd", 0);
						ActionContext.getContext().getSession().put("fromUpdateProfile", 0);
						ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					}
				}
				
				else if(generalInfoDto.isLockedSsn() && StringUtils.isNotBlank(generalInfoDto.getResourceId())){
					if(stateNameToDisplay!=null&& stateNameToDisplay.size()>0)
						return showOutOfStatePopUp(stateNameToDisplay);
					saveData();
					ActionContext.getContext().getSession().put("popUpInd", 0);
					ActionContext.getContext().getSession().put("fromUpdateProfile", 0);
					return INPUT;
				}
				else{
					generalInfoDto.setEditUserName(false);
					saveData();
					ActionContext.getContext().getSession().put("popUpInd", 0);
					ActionContext.getContext().getSession().put("fromUpdateProfile", 0);
				 }
				return INPUT;
			}
		}catch(DuplicateUserException e){
			return INPUT;
		}
	
		
	}

	/*
	 * method for yes button in  pop up
	 * 
	 */
	@SkipValidation
	public String saveDetails()  throws Exception {
		logger.info("GeneralInfoAction.saveDetails()" );
		try
		{
		Integer isFormNext = (Integer)ActionContext.getContext().getSession().get("fromNext");
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		this.generalInfoDto=(GeneralInfoDto) ActionContext.getContext().getSession().get("providerdetailsDto");
		baseTabDto.setDtObject(this.generalInfoDto);
		Integer popup=(Integer)ActionContext.getContext().getSession().get("popUpInd");
		if(popup!=0 && popup!=3){
			List<StateZipcodeVO> stateNameToDisplay = validateStateZipList();
			if(stateNameToDisplay!=null && stateNameToDisplay.size()>0)
				return showOutOfStatePopUp(stateNameToDisplay);
		}
		ActionContext.getContext().getSession().put("popUpInd", 0);
		if(null!=generalInfoDto)
		{
			saveData();
			generalInfoDto.setPopNovalue(true);
			if(null != isFormNext && isFormNext.equals(1)){
				return "next";
			}
			return SUCCESS;
		}
		}
		catch(DuplicateUserException e){
			return INPUT;
		}
		return  SUCCESS;
		
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String doNext() throws Exception {
		logger.info("GeneralInfoAction.doNext()" );
		System.out.println("GeneralInfoAction.doNext()");
		ActionContext.getContext().getSession().put("popUpInd", 0);
		try{
			// SLT-1475: Provider reg by Zipcode Coverage
			if (StringUtils.isNotEmpty(generalInfoDto.getZipcodesCovered()))
				setZipCodesCovered();
			if(null != generalInfoDto){
				   logger.info("generalInfoDto.isLockedSsn() : " + generalInfoDto.isLockedSsn());	
			}
			if (!validateSsn()){
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(generalInfoDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
				return INPUT;
			}
			validateBusinessStateZip();
			currencyValidate();
			validateDispAddGeographicRange();
			
			validateStartEndTime();
			
			if (hasFieldErrors())
			{
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(generalInfoDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
				if(!StringUtils.isBlank(generalInfoDto.getUserName())){
					generalInfoDto.setEditUserName(true);
				}
				return INPUT;
			}else{
				//if the ssn is masked, user has not edited ssn
				//hence setting ssn value as empty string
				if("##".equals(generalInfoDto.getSsnMiddle())){
					generalInfoDto.setSsn("");
				}
				//set  pop up indicator,set this.generalInfoDto in session.
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(generalInfoDto);
				// String oldZipValues=(String)ActionContext.getContext().getSession().get("oldCoverageZips");
				List<StateZipcodeVO> stateNameToDisplay = validateStateZipList();
			    if(StringUtils.isBlank(generalInfoDto.getResourceId())){
					//SSN is entering first time,must show pop up
				  if(StringUtils.isBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){
					   if(!StringUtils.isBlank(generalInfoDto.getUserName())){
							generalInfoDto.setEditUserName(true);
						}
						ActionContext.getContext().getSession().put("popUpInd", 1);
						String fullname=generalInfoDto.getFirstName()+" "+generalInfoDto.getMiddleName()+" "+generalInfoDto.getLastName();
					    ActionContext.getContext().getSession().put("providerLegalName", fullname);
					    String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());	
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("fromNext", 1);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					    return INPUT;
					 }
				  else if(StringUtils.isNotBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){ 
			          if(!generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						 if(!StringUtils.isBlank(generalInfoDto.getUserName())){
							generalInfoDto.setEditUserName(true);
						}
						ActionContext.getContext().getSession().put("popUpInd", 1);
						String fullname=generalInfoDto.getFirstName()+" "+generalInfoDto.getMiddleName()+" "+generalInfoDto.getLastName();
					    ActionContext.getContext().getSession().put("providerLegalName", fullname);
					    String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("fromNext", 1);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					    return INPUT;
					  }else if(generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						 if(!generalInfoDto.isPopNovalue()){
							 if(!StringUtils.isBlank(generalInfoDto.getUserName())){
									generalInfoDto.setEditUserName(true);
								}
							 ActionContext.getContext().getSession().put("popUpInd", 1);
							 String fullname=generalInfoDto.getFirstName()+" "+generalInfoDto.getMiddleName()+" "+generalInfoDto.getLastName();
							 ActionContext.getContext().getSession().put("providerLegalName", fullname);
							 String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
							 String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
							 ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
							 generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
							 generalInfoDto.setPopNovalue(false);
							 ActionContext.getContext().getSession().put("fromNext", 1);
							 ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
							 return INPUT;
							 
						 }
					  }
					}
					
				}else if (!(generalInfoDto.isLockedSsn()) && StringUtils.isNotBlank(generalInfoDto.getResourceId())){
					
					//old SSN will be blank,new SSN must have values
				  if(StringUtils.isBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){
					{if(!StringUtils.isBlank(generalInfoDto.getUserName())){
							generalInfoDto.setEditUserName(true);
						}
						ActionContext.getContext().getSession().put("popUpInd", 2);
						String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("fromNext", 1);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					    return INPUT;
						
					}
				  }	
				//Assume he entered SSN and Old value there
				else if(StringUtils.isNotBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){
					if(!generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						if(!StringUtils.isBlank(generalInfoDto.getUserName())){
							generalInfoDto.setEditUserName(true);
						}
						ActionContext.getContext().getSession().put("popUpInd", 2);
						String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
					    String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
					    ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					    generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
					    generalInfoDto.setPopNovalue(false);
					    ActionContext.getContext().getSession().put("fromNext", 1);
					    ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					    return INPUT;
					}/*SSN old and New are equal.Later point of time */
					else if(generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						//Assume we saved data using save button through pop uo.Then clicking next.
						  if(!generalInfoDto.isPopNovalue()){
							 if(!StringUtils.isBlank(generalInfoDto.getUserName())){
									generalInfoDto.setEditUserName(true);
								}
							 ActionContext.getContext().getSession().put("popUpInd", 2);
							 String ssnLeftMiddleRight = generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
							 String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
							 ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
							 generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
							 generalInfoDto.setPopNovalue(false);
							 ActionContext.getContext().getSession().put("fromNext", 1);
							 ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
							 return INPUT;
						 }
					}
				  }else if(stateNameToDisplay!=null&& stateNameToDisplay.size()>0){
						return showOutOfStatePopUp(stateNameToDisplay);
					} 
				    //First time both value will be null.
				   else if(StringUtils.isBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isBlank(generalInfoDto.getSsn())){
						generalInfoDto.setEditUserName(false);
						saveData();
						ActionContext.getContext().getSession().put("popUpInd", 0);
						return "next";
					}
				}
				else if(generalInfoDto.isLockedSsn() && StringUtils.isNotBlank(generalInfoDto.getResourceId())){
					if(stateNameToDisplay!=null&& stateNameToDisplay.size()>0)
						return showOutOfStatePopUp(stateNameToDisplay);
					saveData();
					ActionContext.getContext().getSession().put("popUpInd", 0);
					return "next";
				}
				else{
					generalInfoDto.setEditUserName(false);
					saveData();
					ActionContext.getContext().getSession().put("popUpInd", 0);
					return "next";
				}
				return "next";
			}
		}catch(DuplicateUserException e){
			return INPUT;
		}
		
	}
	
	
	private void sessionCall() throws Exception{

		String fullname=generalInfoDto.getFirstName()+" "+generalInfoDto.getMiddleName()+" "+generalInfoDto.getLastName();
		ActionContext.getContext().getSession().put("providerLegalName", fullname);
		//String socialSecurityNumber=generalInfoDto.getSsnLeft()+"-"+generalInfoDto.getSsnMiddle()+"-"+generalInfoDto.getSsnRight();
		String socialSecurityNumber = generalInfoDto.getSsn().substring(0, 3)+"-"+generalInfoDto.getSsn().substring(3, 5)+"-"+generalInfoDto.getSsnRight();
		ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
		//ActionContext.getContext().getSession().put("makeSSNEditable", 1);
		ActionContext.getContext().getSession().put("fromNext", 1);
		ActionContext.getContext().getSession().put("fromUpdateProfile", 0);
	}
	
	/**
	 * GGanapathy
	 * Used to update profile after saving the General Info page
	 * @return String
	 * @throws Exception
	 */	
	public String updateProfile() throws Exception {
		logger.info("GeneralInfoAction.updateProfile()" );
		ActionContext.getContext().getSession().put("fromNext", 0);
		if(null != generalInfoDto){
			   logger.info("generalInfoDto.isLockedSsn() : " + generalInfoDto.isLockedSsn());	
		}
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.setDtObject(generalInfoDto);
		ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
		// SLT-1475: Provider reg by Zipcode Coverage
		if (StringUtils.isNotEmpty(generalInfoDto.getZipcodesCovered()))
			setZipCodesCovered();
		validateBusinessStateZip();
		currencyValidate();
		validateDispAddGeographicRange();
		
		validateStartEndTime();
		
		if (hasFieldErrors())
		{
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_GENERALINFO,"errorOn");
			if(!StringUtils.isBlank(generalInfoDto.getUserName())){
				generalInfoDto.setEditUserName(true);
			}
			return INPUT;
		}else{
			List<StateZipcodeVO> stateNameToDisplay = validateStateZipList();
			if (!(generalInfoDto.isLockedSsn())) {
				if (!validateSsn()) {
					baseTabDto.getFieldsError().putAll(getFieldErrors());
					baseTabDto.getTabStatus().put(
							ActivityRegistryConstants.RESOURCE_GENERALINFO,
							"errorOn");
					return INPUT;
				}
				
				if(StringUtils.isBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){
				     ActionContext.getContext().getSession().put("popUpInd", 2);
				     ActionContext.getContext().getSession().put("fromUpdateProfile", 1);
				     String socialSecurityNumber = generalInfoDto.getSsnLeft() + "-"+ generalInfoDto.getSsnMiddle() + "-"+ generalInfoDto.getSsnRight();
				     ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
				     generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
				     generalInfoDto.setPopNovalue(false);
				     ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
				     return INPUT;
			   }
				else if(StringUtils.isNotBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isNotBlank(generalInfoDto.getSsn())){
					if(!generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
					      ActionContext.getContext().getSession().put("popUpInd", 2);
					      ActionContext.getContext().getSession().put("fromUpdateProfile", 1);
					      String socialSecurityNumber = generalInfoDto.getSsnLeft() + "-"+ generalInfoDto.getSsnMiddle() + "-"+ generalInfoDto.getSsnRight();
					      ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
					      generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
					      generalInfoDto.setPopNovalue(false);
					      ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
				          return INPUT;
					} /*SSN old and New are equal.Later point of time,
					   we might have display pop up.Now doing nothing */
					else if(generalInfoDto.getSsn().equals(generalInfoDto.getSsnOldValue())){
						if(!generalInfoDto.isPopNovalue()){
					    	 ActionContext.getContext().getSession().put("popUpInd", 2);
						     ActionContext.getContext().getSession().put("fromUpdateProfile", 1);
						     String socialSecurityNumber = generalInfoDto.getSsnLeft() + "-"+ generalInfoDto.getSsnMiddle() + "-"+ generalInfoDto.getSsnRight();
						     ActionContext.getContext().getSession().put("legalSSN", socialSecurityNumber);
						     generalInfoDto.setSsnOldValue(generalInfoDto.getSsn());
						     generalInfoDto.setPopNovalue(false);
						     ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
					         return INPUT;
					     }
					
					}
				}
				else if(stateNameToDisplay!=null&& stateNameToDisplay.size()>0)
					return showOutOfStatePopUp(stateNameToDisplay);
			 }
			else if(stateNameToDisplay!=null&& stateNameToDisplay.size()>0)
				return showOutOfStatePopUp(stateNameToDisplay);
			else if(StringUtils.isBlank(generalInfoDto.getSsnOldValue())&& StringUtils.isBlank(generalInfoDto.getSsn())){
				saveData();
				return "updateProfile";
			}else{
				  saveData();
				  return "updateProfile";
			}
		}
		return "updateProfile";
	}

	@SkipValidation
     public String saveUpdateProfile()throws Exception{
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		this.generalInfoDto=(GeneralInfoDto) ActionContext.getContext().getSession().get("providerdetailsDto");
		Integer popup=(Integer)ActionContext.getContext().getSession().get("popUpInd");
		baseTabDto.setDtObject(this.generalInfoDto);
		try		
		{	
			if(popup!=0 && popup!=3){
				List<StateZipcodeVO> stateNameToDisplay = validateStateZipList();
				if(stateNameToDisplay!=null && stateNameToDisplay.size()>0){
					return showOutOfStatePopUp(stateNameToDisplay);
				}
				
			}
			if(null!=generalInfoDto)
			{
        	saveData();
        	generalInfoDto.setPopNovalue(true);
        	ActionContext.getContext().getSession().put("popUpInd", 0);
        	ActionContext.getContext().getSession().remove("generalInfoDto");
        	}
			return "updateProfile";
		}
		catch (DelegateException ex) {
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		} catch (DuplicateUserException dpe) {
			dpe.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ dpe.getMessage());

			if (dpe.getMessage().substring(0, 1).equals("2")) {
				addFieldError("registrationDto.email", dpe.getMessage()
						.substring(1));
			} else {
				addFieldError("registrationDto.userName", Config
						.getResouceBundle().getString("Duplicate_User_Name"));
			}
			return "duplicate";
	}
		
	}
	
		
	private void getSessionMessages(){
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		// getting messages from session
		if (baseTabDto!= null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap());
			setActionErrors(baseTabDto.getActionErrors());
			baseTabDto.setActionErrors(new ArrayList<String>());
			setActionMessages(baseTabDto.getActionMessages());
			baseTabDto.setActionMessages(new ArrayList<String>());
		}
	}
	
	@SkipValidation
	public String verifyDispatchZipcode() throws Exception {
		String zipcode = request.getParameter("zipcode");
		logger.info("GeneralInfoAction.verifyDispatchZipcode()");
		logger.info("Zip Code = " + zipcode);

		// Initialize ajax response object DTO

		// Validate zipcode number format
		boolean validFormat = false;
		String responseString="";

		try {
			Integer.parseInt(zipcode);
			validFormat = true;
		} catch (NumberFormatException nfEx) {
			logger.info("Invalid zipcode format - NumberFormatException");
			validFormat = false;
			responseString="Invalid ZipCode Format";
		}

		if (validFormat) {
			// Validate zipcode from database
			boolean isValidZip = iGeneralInfoDelegate.zipExists(zipcode);

			if (!isValidZip) {
				responseString="Please Provide a Valid ZipCode";
			} else {
				responseString="Valid Zip Code";

			}
		}
		inputStream = new ByteArrayInputStream(responseString.getBytes());
		return "dispatchZipCode";

	}
	
	
	private List<StateZipcodeVO> validateStateZipList() {
		List<StateZipcodeVO> stNameList = null;
		List<String> validZipList = new ArrayList<String>();
		List<String> invalidZipList = new ArrayList<String>();
		String[] zipList = generalInfoDto.getCoverageZip();
		List stateTypeList = generalInfoDto.getStateTypeList();

		try {
			Iterator itr = stateTypeList.iterator();
			while (itr.hasNext()) {
				validZipList.add(((LocationVO) itr.next()).getZip());
			}

			for (String zip : zipList) {
				String zipTrimmed = zip.replace("\"", "");
				if (!(validZipList.contains(zipTrimmed))) 
					invalidZipList.add(zipTrimmed);
			}
			if(invalidZipList!=null && invalidZipList.size()>0){
				stNameList = iGeneralInfoDelegate.getStateCdAndZipAgainstCoverageZip(invalidZipList);
				if(stNameList!=null && stNameList.size()>0 && StringUtils.isNotBlank(generalInfoDto.getResourceId())){
					List<StateZipcodeVO> existingOutOfStates=iGeneralInfoDelegate.getOutOfStateDetails(generalInfoDto.getResourceId());
					if(existingOutOfStates!=null && existingOutOfStates.size()>0 && existingOutOfStates.get(0).getLicenseConfirmation()!=null){
						List<String> selectedOutOfStates=new ArrayList<String>();
						List<String> existingStates=new ArrayList<String>();
						for (StateZipcodeVO selectState : stNameList) {
							selectedOutOfStates.add(selectState.getStateCd());
						}
						for (StateZipcodeVO existState : existingOutOfStates) {
							existingStates.add(existState.getStateCd());
						}
						Collections.sort(selectedOutOfStates);
				        Collections.sort(existingStates);
				        if(selectedOutOfStates.equals(existingStates)){
				        	stNameList=null;
				        	generalInfoDto.setStateZipCodeList(existingOutOfStates);
				        }
					}
				}
			}
			return stNameList;

		} catch (Exception a_Ex) {
			logger.log(Level.ERROR, "--Exception inside iGeneralInfoDelegate.loadStateforZip ", a_Ex);
		}
		return stNameList;
	}

	private void setZipCodesCovered(){
		String sample =generalInfoDto.getZipcodesCovered();
		String zipCodes=sample.substring(beginIndex, sample.length()-2);
		zipCodes=zipCodes.replaceAll("\\[", "");
		String[] stringArray = zipCodes.split(",");
		generalInfoDto.setCoverageZip(stringArray);
		
	}
	
	private void setZipCoverage(String vendorId,String resourceId) throws Exception{
		String[] zipcodes = iGeneralInfoDelegate.getSelectedZipCodesByFirmIdAndFilter(Integer.parseInt(vendorId),
				Integer.parseInt(resourceId));
		if (null != zipcodes && zipcodes.length != 0) {
			Gson gson = new Gson();
			String json = gson.toJson(zipcodes);
			json = "{\"zipcodes\": " + json + "}";
			ServletActionContext.getRequest().setAttribute("selectdzipcodesJson", json);
			//ActionContext.getContext().getSession().put("oldCoverageZips", zipcodes);
		}
	}
	
	private String showOutOfStatePopUp(List<StateZipcodeVO> stateZipCodeList){
		generalInfoDto.setStateZipCodeList(stateZipCodeList);
		ActionContext.getContext().getSession().put("popUpInd", 3);
		ActionContext.getContext().getSession().put("providerdetailsDto",this.generalInfoDto);
		return INPUT;
	}
	/**
	 * @return
	 */
	public GeneralInfoDto getGeneralInfoDto() {
		return this.generalInfoDto;
	}

	/**
	 * @param generalInfoDto
	 */
	public void setGeneralInfoDto(GeneralInfoDto generalInfoDto) {
		this.generalInfoDto = generalInfoDto;
	}
	
	private void setUserEditable(String str){
		if (str==null || str.trim().length() ==0)
			ActionContext.getContext().getSession().put("editUser",false);
		else 
			ActionContext.getContext().getSession().put("editUser",true);
	}

	public String getRadChkUser() {
		return radChkUser;
	}

	public void setRadChkUser(String radChkUser) {
		this.radChkUser = radChkUser;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public void setSession(Map ssessionMap) {
		this.sSessionMap = ssessionMap;		
	}	
	public Map getSession() {
		return this.sSessionMap;		
	}

	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}

	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}

	/**
	 * @return the manageUsersDelegate
	 */
	public IManageUsersDelegate getManageUsersDelegate() {
		return manageUsersDelegate;
	}

	/**
	 * @param manageUsersDelegate the manageUsersDelegate to set
	 */
	public void setManageUsersDelegate(IManageUsersDelegate manageUsersDelegate) {
		this.manageUsersDelegate = manageUsersDelegate;
	}
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	
}