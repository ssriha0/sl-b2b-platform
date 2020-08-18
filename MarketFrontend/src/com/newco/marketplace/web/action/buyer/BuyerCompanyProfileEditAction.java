package com.newco.marketplace.web.action.buyer;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.web.delegates.buyer.IBuyerRegistrationDelegate;
import com.newco.marketplace.web.dto.buyer.BuyerRegistrationDTO;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

@Validation
public class BuyerCompanyProfileEditAction extends ActionSupport {
	/**
	 * 
	 */
	private BuyerRegistrationDTO buyerRegistrationDTO;
	private IBuyerRegistrationDelegate iBuyerRegistrationDelegate;
	private LookupVO lookupVo;   
	private static final Logger logger = Logger.getLogger(BuyerRegistrationAction.class.getName());

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(buyerRegistrationDTO);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.BUSINESSINFO,"errorOn");
		}
	}

	

	/**
	 * @param iBuyerRegistrationDelegate
	 * @param buyerRegistrationDTO
	 */
	public void BuyerRegistrationAction(IBuyerRegistrationDelegate iBuyerRegistrationDelegate,BuyerRegistrationDTO buyerRegistrationDTO) {
		this.iBuyerRegistrationDelegate = iBuyerRegistrationDelegate;
		this.buyerRegistrationDTO = buyerRegistrationDTO;
	}

	/**
	 * Used to prevent validation errors on first hit
	 */
	@SkipValidation
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String submit() throws Exception {
		try{
				validateBusinessPhone();
				validateFaxNumber();
				boolean businessFlag = validateBusinessStateZip(); 
				boolean mailFlag = validateMailingStateZip();
				boolean termsConditionsFlag = validateTermsnConditions();
				
				if (hasFieldErrors() || hasActionErrors()) {
					BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
					baseTabDto.setDtObject(buyerRegistrationDTO);
					baseTabDto.getFieldsError().putAll(getFieldErrors());
					baseTabDto.getTabStatus().put(ActivityRegistryConstants.BUSINESSINFO,"errorOn");
					return INPUT;
				}
				
				if (businessFlag && mailFlag && termsConditionsFlag) {
					iBuyerRegistrationDelegate.updateBuyerCompanyProfile(buyerRegistrationDTO);
					logger.info("State Status in Action-----------------"+buyerRegistrationDTO.isValidateState());
					if(buyerRegistrationDTO.isValidateState()){
						logger.info("Inside False Status in Action-----------------"+buyerRegistrationDTO.isValidateState());
						return "invalidState";
					}
				}
				else {
					return INPUT;
				}
			}catch(DelegateException ex){
				logger.info("Exception Occured while processing the request due to"+ex.getMessage());
				addActionError("Exception Occured while processing the request due to"+ex.getMessage());
				return ERROR;
			}
	return SUCCESS;
	}
	
	
	@SkipValidation
	public String doLoad() throws Exception {
						
		buyerRegistrationDTO.setBuyerId(Integer.parseInt((String)ActionContext.getContext().getSession().get("buyerId")));
		buyerRegistrationDTO = iBuyerRegistrationDelegate.loadData(buyerRegistrationDTO);
		// getting messages from session
		getSessionMessages();
		return "load";
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
		if("0".equals(buyerRegistrationDTO.getTermsAndCondition()))
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
			isValidNumber(list1, "Administrator Phone Number ", "buyerRegistrationDTO.busPhoneNo1");
			list1.add(contactPhone1);
			isValidNumber(list1, "Administrator Phone Number ", "buyerRegistrationDTO.busPhoneNo1");
			
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

}
