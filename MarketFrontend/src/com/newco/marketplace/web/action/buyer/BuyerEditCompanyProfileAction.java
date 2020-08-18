package com.newco.marketplace.web.action.buyer;

/*
 * $Id: BuyerEditCompanyProfileAction.java,v 1.7 2008/06/04 21:53:00 cgarc03 Exp $
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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.buyer.IBuyerRegistrationDelegate;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.buyer.BuyerRegistrationDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.thoughtworks.xstream.XStream;

@Validation
public class BuyerEditCompanyProfileAction extends SLBaseAction implements Preparable,ModelDriven<BuyerRegistrationDTO>
{
	
	private BuyerRegistrationDTO buyerRegistrationDTO;
	private IBuyerRegistrationDelegate iBuyerRegistrationDelegate;
	private IAuditLogDelegate auditLogDelegates;
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
    
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();  
		Integer buyerId = this.get_commonCriteria().getCompanyId(); 
		buyerRegistrationDTO = iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO,buyerId);
	}
    
	@SkipValidation
	public String loadData() throws Exception{
		try
		{
			createCommonServiceOrderCriteria();
			buyerRegistrationDTO.setBuyerId(this.get_commonCriteria().getCompanyId());
			SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
			//SL-20461 changes starts
			
			ActionContext.getContext().getSession().put("isPermissionForBuyerAdminNameChange", securityContext.getRoles().isPermissionForBuyerAdminNameChange());
			//SL-20461 changes ends
			// changes for SL-20536 starts
			if(securityContext.isSlAdminInd())
				ActionContext.getContext().getSession().put("showTermsAndConditionsOfBuyer",Boolean.FALSE );
			else
				ActionContext.getContext().getSession().put("showTermsAndConditionsOfBuyer", Boolean.TRUE);
		
			// changes for SL-20536 ends
			
			buyerRegistrationDTO = iBuyerRegistrationDelegate.loadData(buyerRegistrationDTO);
			buyerRegistrationDTO = iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO,buyerRegistrationDTO.getBuyerId());

		}
		catch(DelegateException ex){

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
		loadData();
		
		String headerTxt = getParameter("headerTxt");
		
		if(SLStringUtils.isNullOrEmpty(headerTxt) == false)
		{
			setAttribute("headerTxt", headerTxt);
		}
		
		return SUCCESS;
	}
	
    
	public BuyerEditCompanyProfileAction(IBuyerRegistrationDelegate iBuyerRegistrationDelegate,BuyerRegistrationDTO buyerRegistrationDTO) {
		this.iBuyerRegistrationDelegate = iBuyerRegistrationDelegate;
		this.buyerRegistrationDTO = buyerRegistrationDTO;
	}
	
	// Button
	public String submit() throws Exception
	{
		
    	try{
			validateBusinessPhone();
			validateFaxNumber();
			boolean businessFlag = validateBusinessStateZip(); 
			boolean mailFlag = validateMailingStateZip();
			boolean termsConditionsFlag = validateTermsnConditions();
			createCommonServiceOrderCriteria();
			buyerRegistrationDTO.setBuyerId(this.get_commonCriteria().getCompanyId());
			
			
			if (hasFieldErrors() || hasActionErrors()) {
				iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO,buyerRegistrationDTO.getBuyerId());
				return INPUT;
			}
			
			
			if (businessFlag && mailFlag && termsConditionsFlag) {
				if(iBuyerRegistrationDelegate.isBlackoutState(buyerRegistrationDTO.getBusinessState())) 
				{			
					addFieldError("buyerRegistrationDTO.BusinessState", "We are unable to fulfill buyer requests in the following states/U.S. Territories: AS, FM, GU, MH, MP, PW, VI, VT.");
					iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO,buyerRegistrationDTO.getBuyerId());	
					return INPUT;
				} 
				createCommonServiceOrderCriteria();
				buyerRegistrationDTO.setBuyerId(this.get_commonCriteria().getCompanyId());
				//Changes for SL-20461 starts
				SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
				if(securityContext.isSlAdminInd())
					buyerRegistrationDTO.setModifiedBy(securityContext.getSlAdminUName());
				else
					buyerRegistrationDTO.setModifiedBy(securityContext.getUsername());
				
				//Changes for SL-20461 ends
				
				if(iBuyerRegistrationDelegate.updateBuyerCompanyProfile(buyerRegistrationDTO))
				{
					//changes for SL-20461 starts
					if(null!=buyerRegistrationDTO.getNewAdminUserName())
					{
					ActionContext.getContext().getSession().put("buyerAdmin", buyerRegistrationDTO.getNewAdminUserName());
					iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO,buyerRegistrationDTO.getBuyerId());
					}
					//changes for SL-20461 ends
					
					getSession()
					.setAttribute(Constants.SESSION.SOD_MSG,
							"Company profile has been successfully updated");
					auditUserProfileLog(buyerRegistrationDTO);
					return SUCCESS;
				}
				else
					return "ERROR";
			}
			else {
				iBuyerRegistrationDelegate.loadListData(buyerRegistrationDTO,buyerRegistrationDTO.getBuyerId());	
				return INPUT;
			}
		}catch(DelegateException ex){
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			return ERROR;
		}
	}
	
	private void auditUserProfileLog(BuyerRegistrationDTO buyerRegistrationDTO)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {BuyerRegistrationDTO.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(buyerRegistrationDTO);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("BUYER_COMPANY_PROFILE_EDIT");
		auditUserProfileVO.setLoginCompanyId(this.get_commonCriteria().getCompanyId());
		auditUserProfileVO.setLoginResourceId(this.get_commonCriteria().getVendBuyerResId());
		auditUserProfileVO.setRoleId(this.get_commonCriteria().getRoleId());
		if(this.get_commonCriteria().getSecurityContext().isSlAdminInd())
			auditUserProfileVO.setIsSLAdminInd(1);
		auditUserProfileVO.setModifiedBy(this.get_commonCriteria().getTheUserName());
		auditUserProfileVO.setUserProfileData(xmlContent);
		auditLogDelegates.auditUserProfile(auditUserProfileVO);
	}


	private boolean isFormValid()
	{
		SOWError error;
		List<IError> errors = new ArrayList<IError>();		
		BuyerRegistrationDTO dto = getModel();

		// Have errors, form is invalid
		if(errors.size() > 0)
			return false;
		
		// No Errors, Form is valid
		return true;
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
		//Code change for SL-20536 starts
		if("0".equals(buyerRegistrationDTO.getTermsAndCondition()) && ActionContext.getContext().getSession().get("showTermsAndConditionsOfBuyer").equals(true) )
			//Code change for SL-20536 ends
		{
			addFieldError("buyerRegistrationDTO.TermsAndCondition", "Acceptance of Terms and Conditions must be selected.");
			return false;
		}
		return true;
	}
	
	/*
	 * author: paugus2
	 * This method is used to validate business phone number
	 */
	private void validateBusinessPhone() {
		String busPhone1 = null;
		String busPhone2 = null;
		String busPhone3 = null;
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
									
			busPhone1 = busPhone1+busPhone2+busPhone3;
			list1.add(busPhone1);
			isValidNumber(list1, "Main Business Phone Number ", "buyerRegistrationDTO.phoneAreaCode");
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
					"buyerRegistrationDTO.faxAreaCode", "Error while validating the Business Fax Number.");
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
						+ " must be a number");
				return false;
			} catch (Exception a_Ex) {
				addFieldError(fieldName, errorMsg
						+ " must be a number");
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

	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}

	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}


	
}