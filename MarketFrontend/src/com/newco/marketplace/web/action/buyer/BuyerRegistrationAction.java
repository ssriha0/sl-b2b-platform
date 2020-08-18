package com.newco.marketplace.web.action.buyer;

/*
 * $Id: BuyerRegistrationAction.java,v 1.12 2008/05/29 19:46:16 awadhwa Exp $
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.buyer.IBuyerRegistrationDelegate;
import com.newco.marketplace.web.dto.buyer.BuyerRegistrationDTO;
import com.newco.marketplace.web.utils.Config;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.sears.os.utils.DateValidationUtils;

@Validation
public class BuyerRegistrationAction extends SLBaseAction implements Preparable, ModelDriven<BuyerRegistrationDTO>
{
	
	private BuyerRegistrationDTO buyerRegistrationDTO;
	private IBuyerRegistrationDelegate iBuyerRegistrationDelegate;
	private LookupVO lookupVo;   
	private static final Logger logger = Logger.getLogger(BuyerRegistrationAction.class.getName());
	
	/**
	 * Variables for the Radio button in the Provider Registration Page
	 */
	private static List tncList;    
	
	/**
	 * load the radio buttons statically so new arrays are not created each time
	 */
	static {
		//List for Terms and Condition radio
		tncList = new ArrayList();
		tncList.add("I accept the Terms & Conditions");
		tncList.add("I do not accept the Terms & Conditions.");
	}
	
	public void prepare() throws Exception
	{
		doLoad();
	}	
	private static final long serialVersionUID = -1791530059244642916L;
	/**
	 * Variables for the Combo box in the Provider Registration Page.
	 */
	private List businessStructureList = new ArrayList();
	private List primaryIndList = new ArrayList();
	private List sizeOfCompanyList = new ArrayList();
	private List annualSalesRevenueList = new ArrayList();
    private List roleWithinCompanyList =  new ArrayList();
    private List howDidYouHearList =  new ArrayList();
    private List stateTypeList = new ArrayList();
    private List<String> blackoutStates;
    
	@SkipValidation
	public String doLoad() throws Exception {
		
		try{
			buyerRegistrationDTO = new BuyerRegistrationDTO();
			buyerRegistrationDTO = iBuyerRegistrationDelegate.loadRegistration(buyerRegistrationDTO);
			buyerRegistrationDTO = iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO);
			
			//Loads the Primary Industry Combo from DTO
			setPrimaryIndList(buyerRegistrationDTO.getPrimaryIndList());
			
			//Loads the Role within Company Combo from DTO
			setRoleWithinCompanyList(buyerRegistrationDTO.getRoleWithinCompanyList());
			
			//Loads the How did you hear about ServiceLive? Combo from DTO
			setHowDidYouHearList(buyerRegistrationDTO.getHowDidYouHearList());
			
			//get blackout state list and add to value stack
			blackoutStates = iBuyerRegistrationDelegate.getBlackoutStates();
			
		}catch(DelegateException ex){

			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			throw ex;
		}
		return SUCCESS;
	}
	
	/**
	 * Used to prevent validation errors on first hit
	 */
	@SkipValidation
	public String execute() throws Exception {
		
		doLoad();
		return SUCCESS;
	}
	
    
	public BuyerRegistrationAction(IBuyerRegistrationDelegate iBuyerRegistrationDelegate,BuyerRegistrationDTO buyerRegistrationDTO) {
		this.iBuyerRegistrationDelegate = iBuyerRegistrationDelegate;
		this.buyerRegistrationDTO = buyerRegistrationDTO;
	}

	// Button
	public String submit() throws Exception
	{
		try
		{
			boolean mailFlag = true;
			validateConfirmUserName();
			validateBusinessPhone();
			validateFaxNumber();
			validateMobNumber();
			validateEmailConfirm();
			validateBusinessStartDate();
			boolean businessFlag = validateBusinessStateZip(); 
			if(buyerRegistrationDTO.getMailingZip()!= null && !buyerRegistrationDTO.getMailingZip().equals(""))
				mailFlag = validateMailingStateZip();	
			boolean termsConditionsFlag = validateTermsnConditions();
			
			if (hasFieldErrors() || hasActionErrors()) 
			{
				buyerRegistrationDTO = iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO);
				return INPUT;
			}
			if (businessFlag && mailFlag && termsConditionsFlag) 
			{
				// if blackout state, save buyer and forward to registration error page
				if(iBuyerRegistrationDelegate.isBlackoutState(buyerRegistrationDTO.getBusinessState())) 
				{
					iBuyerRegistrationDelegate.saveBuyerLead(buyerRegistrationDTO);
					SSoWSessionFacility.getInstance().invalidateSessionFacilityStates();
					return "registrationErrorPage";
				} 
				else 
				{	
					iBuyerRegistrationDelegate.saveBuyerRegistration(buyerRegistrationDTO);
					logger.info("State Status in Action-----------------"+buyerRegistrationDTO.isValidateState());
					if(buyerRegistrationDTO.isValidateState())
					{
						logger.info("Inside False Status in Action-----------------"+buyerRegistrationDTO.isValidateState());
						return "invalidState";
					}
				}
			}
			else 
			{
				buyerRegistrationDTO = iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO);
				return INPUT;
			}
		}catch(DelegateException ex){
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			return ERROR;
		}catch(DuplicateUserException dpe){
			dpe.printStackTrace();
			logger.info("Exception Occured while processing the request due to"+dpe.getMessage());
			
			if(dpe.getMessage().substring(0, 1).equals("2"))
				{
					addFieldError("buyerRegistrationDTO.email",dpe.getMessage().substring(1));
				}else{
					addFieldError("buyerRegistrationDTO.userName",Config.getResouceBundle().getString("Duplicate_User_Name"));
				}
			return "duplicate";
		}
		return "congrats";
	}
	
	private boolean validateBusinessStateZip() throws Exception
	{
		try
		{
			buyerRegistrationDTO.setStateType("business");
			buyerRegistrationDTO = iBuyerRegistrationDelegate.loadZipSet(buyerRegistrationDTO);
			List stateTypeList = buyerRegistrationDTO.getStateTypeList();
			if(!validateStateZip(stateTypeList, buyerRegistrationDTO.getBusinessZip()))
			{
				addFieldError("buyerRegistrationDTO.businessZip", "Business zip doesn't match selected state");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--------- Exception inside validateBusinessStateZip ---", a_Ex);
			throw a_Ex;
		}
		return true;
	}

	
	private boolean validateMailingStateZip() throws Exception 
	{
		try
		{
			buyerRegistrationDTO.setStateType("mail");
			buyerRegistrationDTO = iBuyerRegistrationDelegate.loadZipSet(buyerRegistrationDTO);
			List stateTypeList = buyerRegistrationDTO.getStateTypeList();
			if(!validateStateZip(stateTypeList, buyerRegistrationDTO.getMailingZip()))
			{
				addFieldError("buyerRegistrationDTO.mailingZip", "Mailing zip doesn't match selected state");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--------- Exception inside validateMailingStateZip ---", a_Ex);
			throw a_Ex;
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
	
	
	private boolean validateTermsnConditions()
	{
		if(buyerRegistrationDTO.getTermsAndCondition().equals("false"))
		{
			if(buyerRegistrationDTO.getServiceLiveBucksInd().equals("false")){
				addFieldError("buyerRegistrationDTO.serviceLiveBucksText", "Acceptance of ServiceLive Bucks agreement must be selected.");
			}
			addFieldError("buyerRegistrationDTO.TermsAndCondition", "Acceptance of Terms and Conditions must be selected.");
			return false;
		}
		if(buyerRegistrationDTO.getServiceLiveBucksInd().equals("false"))
		{
			if(buyerRegistrationDTO.getTermsAndCondition().equals("false")){
				addFieldError("buyerRegistrationDTO.TermsAndCondition", "Acceptance of Terms and Conditions must be selected.");
			}
			addFieldError("buyerRegistrationDTO.serviceLiveBucksText", "Acceptance of ServiceLive Bucks agreement must be selected.");
			return false;
		}
		return true;
	}
	
	private void validateConfirmUserName() {
		buyerRegistrationDTO.setUserName(buyerRegistrationDTO.getUserName().trim());
		buyerRegistrationDTO.setConfirmUserName(buyerRegistrationDTO.getConfirmUserName().trim());
		
		String username = buyerRegistrationDTO.getUserName();
		String confirmUsername = buyerRegistrationDTO.getConfirmUserName();
		
		Integer nameLength = Integer.parseInt(Config.getResouceBundle().getString("accountDTO.username.length"));
		
		if(!username.equalsIgnoreCase(confirmUsername)) {
			addFieldError("buyerRegistrationDTO.userName", Config.getResouceBundle().getString("accountDTO.username.error.mismatch"));
		}
		else if(StringUtils.trim(username).length() < nameLength){
			addFieldError("buyerRegistrationDTO.userName", Config.getResouceBundle().getString("accountDTO.username.error.length"));
		}
	}
	
	/*
	 * author: paugus2
	 * This method is used to validate business phone number
	 */
	private void validateBusinessPhone() {
		String busPhone1 = null;
		String busPhone2 = null;
		String busPhone3 = null;
		String contactPhone1 = null;
		String contactPhone2 = null;
		String contactPhone3 = null;
		String busExtn = null;
		
		List list1 = new ArrayList();
		
		try
		{
			busPhone1 = (buyerRegistrationDTO.getPhoneAreaCode()!= null) ? 
					buyerRegistrationDTO.getPhoneAreaCode().trim() : "";
			busPhone2 = (buyerRegistrationDTO.getPhonePart1() != null) ? 
					buyerRegistrationDTO.getPhonePart1().trim() : "";
			busPhone3 = (buyerRegistrationDTO.getPhonePart2() != null) ? 
					buyerRegistrationDTO.getPhonePart2().trim() : "";
			contactPhone1 = (buyerRegistrationDTO.getBusPhoneNo1()!= null) ? 
					buyerRegistrationDTO.getBusPhoneNo1().trim() : "";
			contactPhone2 = (buyerRegistrationDTO.getBusPhoneNo2() != null) ? 
					buyerRegistrationDTO.getBusPhoneNo2().trim() : "";
			contactPhone3 = (buyerRegistrationDTO.getBusPhoneNo3() != null) ? 
					buyerRegistrationDTO.getBusPhoneNo3().trim() : "";
									
			busPhone1 = busPhone1+busPhone2+busPhone3;
			contactPhone1 = contactPhone1+contactPhone2+contactPhone3;
			list1.add(busPhone1);
			isValidNumber(list1, "Main Business Phone number ", "buyerRegistrationDTO.phoneAreaCode");
			list1 = new ArrayList();
			list1.add(contactPhone1);
			isValidNumber(list1, "Administrator Phone number", "buyerRegistrationDTO.busPhoneNo1");
			
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
		}
	}
	/*
	 * author: paugus2
	 * This method is used to validate business fax number
	 */
	private void validateFaxNumber() {
		String busFax1 = null;
		String busFax2 = null;
		String busFax3 = null;
		
		List list1 = new ArrayList();
		
		try
		{
			busFax1 = (buyerRegistrationDTO.getFaxAreaCode()!= null) ? 
					buyerRegistrationDTO.getFaxAreaCode().trim() : "";
			busFax2 = (buyerRegistrationDTO.getFaxPart1() != null) ? 
					buyerRegistrationDTO.getFaxPart1().trim() : "";
			busFax3 = (buyerRegistrationDTO.getFaxPart2() != null) ? 
					buyerRegistrationDTO.getFaxPart2().trim() : "";
			busFax1 = busFax1+busFax2+busFax3;
			
			if(null != busFax1 && busFax1.trim().length() > 0)
			{
				list1.add(busFax1);
				isValidNumber(list1, "Business Fax ", "buyerRegistrationDTO.faxAreaCode");
			}
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
			addFieldError(
					"buyerRegistrationDTO.businessFax1", "Error while validating the Business Fax Number.");
		}
	}
	
	/*
	 * author: paugus2
	 * This method is used to validate mobile number
	 */
	private void validateMobNumber() {
		String mob1 = null;
		String mob2 = null;
		String mob3 = null;
		
		List list1 = new ArrayList();
		
		try
		{
			mob1 = (buyerRegistrationDTO.getMobPhoneNo1()!= null) ? 
					buyerRegistrationDTO.getMobPhoneNo1().trim() : "";
			mob2 = (buyerRegistrationDTO.getMobPhoneNo2() != null) ? 
					buyerRegistrationDTO.getMobPhoneNo2().trim() : "";
			mob3 = (buyerRegistrationDTO.getMobPhoneNo3() != null) ? 
			buyerRegistrationDTO.getMobPhoneNo3().trim() : "";
			mob1 = mob1+mob2+mob3;
			
			if(null != mob1 && mob1.trim().length() > 0)
			{
				list1.add(mob1);
				isValidNumber(list1, "Administrator Mobile number", "buyerRegistrationDTO.mobPhoneNo1");
			}
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
			addFieldError(
					"buyerRegistrationDTO.mobPhoneNo1", "Error while validating the Administrator mobile number.");
		}
	}
	
	/*
	 * author: paugus2
	 * This method is used to validate email id's
	 */
	private void validateEmailConfirm() {
		String email1 = null;
		String email2 = null;
		String altEmail1 = null;
		String altEmail2 = null;
		try
		{
			email1 = (buyerRegistrationDTO.getEmail()!= null) ? 
					buyerRegistrationDTO.getEmail().trim() : "";
			email2 = (buyerRegistrationDTO.getConfirmEmail() != null) ? 
					buyerRegistrationDTO.getConfirmEmail().trim() : "";
			altEmail1 = (buyerRegistrationDTO.getEmail()!= null) ? 
			buyerRegistrationDTO.getEmail().trim() : "";
			altEmail2 = (buyerRegistrationDTO.getConfirmEmail() != null) ? 
			buyerRegistrationDTO.getConfirmEmail().trim() : "";
			if(!email1.equals(email2))
				addFieldError("email", "Email and Confirmation Email fields do not match");
			if(!altEmail1.equals(altEmail2))
				addFieldError("altEmail", "Email and Confirmation Email fields do not match");
		}
		catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
		}
	}
	
	private boolean isValidNumber(List numberList, String errorMsg, String fieldName) {
		String num1 = null;
		boolean flag = true;
		
		try {
			if (numberList == null || numberList.size() <= 0) {
				addFieldError(fieldName, errorMsg
						+ " is incomplete");
				return false;
			}

			num1 = (numberList.get(0) != null) ? (String)numberList.get(0) : "";
			
			
			if (num1.trim().length() <= 0 ||num1.trim().equals("") )
				addFieldError(fieldName, errorMsg
						+ " is incomplete");

			// Validating for the NUMBER type
			try {
				long numInt1 = Long.parseLong(num1.trim());

			} catch (NumberFormatException a_Ex) {
				addFieldError(fieldName, errorMsg
						+ " must be a numeric");
				return false;
			} catch (Exception a_Ex) {
				addFieldError(fieldName, errorMsg
						+ " must be a numeric");
				return false;
			}

			// validation for length
			if (num1.trim().length() < 10) {
				addFieldError(fieldName, errorMsg + 
						"  can not be less than 10 characters");
				flag = false;
			}


		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			return false;
		}
		
		return flag;
	}
	
	private boolean validateBusinessStartDate() throws Exception
	{
		try
		{
			if(DateUtils.isValidDate(buyerRegistrationDTO.getBusinessStarted()))
			{
				boolean isCurrentDate=DateValidationUtils.fromDateGreaterThanCurrentDate(buyerRegistrationDTO.getBusinessStarted());		
				if(isCurrentDate)
				{
					addFieldError("buyerRegistrationDTO.businessStarted", "Business start date must not be a future date");				
					return false;
				}
			}
			else
			{
				addFieldError("buyerRegistrationDTO.businessStarted", "Enter a valid business start date");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--Exception inside businessinfoDto.busStartDt", a_Ex);
			throw a_Ex;
		}
		return true;		
	}
	
	
	public BuyerRegistrationDTO getModel()
	{
		return buyerRegistrationDTO;
	}


	public BuyerRegistrationDTO getBuyerRegistrationDTO() {
		return buyerRegistrationDTO;
	}


	public void setBuyerRegistrationDTO(BuyerRegistrationDTO buyerRegistrationDTO) {
		this.buyerRegistrationDTO = buyerRegistrationDTO;
	}

	public LookupVO getLookupVo() {
		return lookupVo;
	}


	public void setLookupVo(LookupVO lookupVo) {
		this.lookupVo = lookupVo;
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

	public List<String> getBlackoutStates() {
		return blackoutStates;
	}

	public void setBlackoutStates(List<String> blackoutStates) {
		this.blackoutStates = blackoutStates;
	}
	
}