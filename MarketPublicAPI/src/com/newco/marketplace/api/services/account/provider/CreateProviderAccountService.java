package com.newco.marketplace.api.services.account.provider;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountRequest;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.mappers.account.Provider.ProviderAccountMapper;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.provider.IRegistrationBO;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;

public class CreateProviderAccountService{
	private IRegistrationBO providerRegistrationBO;
	private Logger logger = Logger.getLogger(CreateProviderAccountService.class);
	private ILookupBO lookupBO;

	private boolean errorOccured = false;
	/**
	 * This method is for creating Provider account.
	 * Code created by 643272 date 03-09-2013
	 */

	public CreateProviderAccountService () {

		this.errorOccured = false;
	}

	/**
	 * Subclass needs to implement API specific logic here.
	 */
	public CreateProviderAccountResponse execute(CreateProviderAccountRequest request) {		

		logger.info("Entering CreateProviderAccount API's execute method");	
		CreateProviderAccountResponse response = new CreateProviderAccountResponse();
		if(request!=null){
			if(errorOccured){
				errorOccured = false;
			}
			request = replaceSpecialChar(request);

			if(StringUtils.isBlank(request.getMailingStreet1())&& StringUtils.isBlank(request.getMailingCity())&& StringUtils.isBlank(request.getMailingState())&& StringUtils.isBlank(request.getMailingZip())){
				request.setMailingStreet1(request.getBusinessStreet1());
				request.setMailingStreet2(request.getBusinessStreet2());
				request.setMailingCity(request.getBusinessCity());
				request.setMailingAprt(request.getBusinessAprt());
				request.setMailingState(request.getBusinessState());
				request.setMailingZip(request.getBusinessZip());
			}

			response = validateFields(request,response);
			if(errorOccured){
				return response;
			}
			else{
				ProviderRegistrationVO providerRegistrationVO = null;

				try{
					providerRegistrationVO = ProviderAccountMapper.getVOForCreateProviderAccount(request);
					ProviderRegistrationVO loadValues = providerRegistrationBO.loadRegistration(providerRegistrationVO);
					response = validateLookUpValues(loadValues,response,providerRegistrationVO);
					if(!errorOccured){
						response = validateBusinessStateZip(providerRegistrationVO, response);
					}
					if(!errorOccured){
						response = validateMailingStateZip(providerRegistrationVO, response);
					}
					if(!errorOccured){
						response = validateUserName(providerRegistrationVO, response);
					}
					if(errorOccured){
						return response;
					}
					
					//R11_2
					//SL-20421
					if(StringUtils.isNotBlank(providerRegistrationVO.getFirstName())){
						providerRegistrationVO.setFirstName(providerRegistrationVO.getFirstName().trim());
					}
					if(StringUtils.isNotBlank(providerRegistrationVO.getLastName())){
						providerRegistrationVO.setLastName(providerRegistrationVO.getLastName().trim());
					}
					if(StringUtils.isNotBlank(providerRegistrationVO.getMiddleName())){
						providerRegistrationVO.setMiddleName(providerRegistrationVO.getMiddleName().trim());
					}
					
					//R11_2
					//Trimming username before saving in the database
					if(StringUtils.isNotBlank(providerRegistrationVO.getUserName())){
						providerRegistrationVO.setUserName(providerRegistrationVO.getUserName().trim());
					}
					
					
					providerRegistrationVO = providerRegistrationBO.saveRegistration(providerRegistrationVO);
					response = ProviderAccountMapper.convertVOToPOJOForCreateProviderAccount(providerRegistrationVO);

				} 
				catch(BusinessServiceException e) {
					logger.error("CreateProviderService exception detail: " + e.getMessage());
					response.setResults(
							Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
									ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
				}
				catch (DuplicateUserException e) {
					logger.error("Duplicate User Exists- CreateProviderAccountService: error: " + e.getMessage());	
					Results results = Results.getError(ResultsCode.DUPLICATE_USERNAME
							.getMessage(), ResultsCode.DUPLICATE_USERNAME
							.getCode());
					response.setResults(results);
					errorOccured = true;	
				} catch(DataException e) {
					logger.error("CreateProviderService exception detail: " + e.getMessage());
					response.setResults(
							Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
									ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
				}
				catch(Exception e) {
					logger.error("CreateProviderService exception detail: " + e.getMessage());
					response.setResults(
							Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
									ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
				}
				logger.info("Exiting CreateProviderAccount API's execute method");	
			}
		}
		else{
			logger.info("Exiting CreateProviderAccount API's execute method due tor equest null");	

			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return response;

	}


	private CreateProviderAccountResponse validateLookUpValues(
			ProviderRegistrationVO loadValues,
			CreateProviderAccountResponse response, ProviderRegistrationVO providerRegistrationVO) {
		List howDidYouHearList = loadValues.getHowDidYouHearList();
		List primaryIndList = loadValues.getPrimaryIndList();
		List roleWithinCompany = loadValues.getRoleWithinCompany();
		List stateList = providerRegistrationBO.getStates();
		String howDidiYouHearExist = validateValues(howDidYouHearList,providerRegistrationVO.getHowDidYouHear());
		String primaryIndExist = validateValues(primaryIndList,providerRegistrationVO.getPrimaryIndustry());
		String roleWithinCompanyExist = validateRoleValues(roleWithinCompany,providerRegistrationVO.getRoleWithinCom());
		String businessStateExist = validateStateValues(stateList,providerRegistrationVO.getBusinessState());
		String mailStateExist = validateStateValues(stateList,providerRegistrationVO.getMailingState());
		if(howDidiYouHearExist!=null){
			providerRegistrationVO.setHowDidYouHear(howDidiYouHearExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_HOW_DID_YOU_HEAR
					.getMessage(), ResultsCode.INVALID_HOW_DID_YOU_HEAR
					.getCode());
			response.setResults(results);
			errorOccured = true;
			return response;

		}
		if(primaryIndExist!=null){
			providerRegistrationVO.setPrimaryIndustry(primaryIndExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_PRIMARY_INDUSTRY
					.getMessage(), ResultsCode.INVALID_PRIMARY_INDUSTRY
					.getCode());
			response.setResults(results);
			errorOccured = true;
			return response;

		}
		if(roleWithinCompanyExist!=null){
			providerRegistrationVO.setRoleWithinCom(roleWithinCompanyExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_ROLE_WITH_COM
					.getMessage(), ResultsCode.INVALID_ROLE_WITH_COM
					.getCode());
			response.setResults(results);
			errorOccured = true;
			return response;
		}
		if(businessStateExist!=null){
			providerRegistrationVO.setBusinessState(businessStateExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_BUSINESS_STATE
					.getMessage(), ResultsCode.INVALID_BUSINESS_STATE
					.getCode());
			response.setResults(results);
			errorOccured = true;
			return response;
		}
		if(mailStateExist!=null){
			providerRegistrationVO.setMailingState(mailStateExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_MAILING_STATE
					.getMessage(), ResultsCode.INVALID_MAILING_STATE
					.getCode());
			response.setResults(results);
			errorOccured = true;
			return response;
		}
		return response;
	}


	private String validateStateValues(List stateList, String state) {
		Iterator itr = stateList.iterator();
		while (itr.hasNext()) {
			LookupVO lookupVO = ((LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			String id = lookupVO.getType();
			if (state.equals(validValue)) {
				return id;
			}
		}
		return null;
	}

	private String validateRoleValues(List roleWithinCompany,
			String roleWithinCom) {
		Iterator itr = roleWithinCompany.iterator();
		while (itr.hasNext()) {
			com.newco.marketplace.dto.vo.LookupVO lookupVO = ((com.newco.marketplace.dto.vo.LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			Integer id = lookupVO.getId();
			if (roleWithinCom.equals(validValue)) {
				return id.toString();
			}
		}
		return null;
	}

	private String validateValues(List list, String descr) {
		Iterator itr = list.iterator();
		boolean valid = false;
		while (itr.hasNext()) {
			LookupVO lookupVO = ((LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			String id = lookupVO.getId();
			if (descr.equals(validValue)) {
				return id;
			}
		}
		return null;
	}

	public static boolean isNumeric(String s) {   
        return java.util.regex.Pattern.matches("\\d+", s);   
    } 
	
	private CreateProviderAccountResponse validateFields(CreateProviderAccountRequest request,CreateProviderAccountResponse response) {		
		
		
		Results results = null;
		response = validatemainBusiPhoneNo(request,response);		
		if(!errorOccured){
			response = validateFaxNumber(request,response);
		}
		if(!errorOccured){
			response = validateOtherPrimaryServices(request,response);	
		}
		if(!errorOccured){
			if (StringUtils.isBlank(request.getLegalBusinessName())) {

				results = Results.getError(ResultsCode.INVALID_LEGALBUSINESSNAME
						.getMessage(), ResultsCode.INVALID_LEGALBUSINESSNAME
						.getCode());
				response.setResults(results);
				errorOccured = true;
				//response.setResults(ResultsCode.LEGALBUSINESSNAME.getMessage());			

			}
			/*if (StringUtils.isBlank(request.getDBAName())) {

			results = Results.getError(ResultsCode.INVALID_DBANAME
					.getMessage(), ResultsCode.INVALID_DBANAME
					.getCode());
			response.setResults(results);
		}
			 */
			/*else if (StringUtils.isBlank(request.getWebsiteAddress())){

			results = Results.getError(ResultsCode.INVALID_WEBSITEADDRESS
					.getMessage(), ResultsCode.INVALID_WEBSITEADDRESS
					.getCode());
			response.setResults(results);
		}*/
			
	
			else if (!errorOccured && StringUtils.isNotBlank(request.getMainBusinessExtn())&& 
					(request.getMainBusinessExtn().length()>4 || request.getMainBusinessExtn().length()<4 || !isNumeric(request.getMainBusinessExtn()))){						
				
				results = Results.getError(ResultsCode.INVALID_MAINBUSINESSEXTN
						.getMessage(), ResultsCode.INVALID_MAINBUSINESSEXTN
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}	

			else if (!errorOccured && StringUtils.isBlank(request.getBusinessStreet1())) {

				results = Results.getError(ResultsCode.INVALID_BUSINESSSTREET
						.getMessage(), ResultsCode.INVALID_BUSINESSSTREET
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else if (!errorOccured && StringUtils.isBlank(request.getBusinessCity())) {

				results = Results.getError(ResultsCode.BUSINESSCITY
						.getMessage(), ResultsCode.BUSINESSCITY
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			else if (!errorOccured && StringUtils.isBlank(request.getBusinessState())) {

				results = Results.getError(ResultsCode.BUSINESSSTATE
						.getMessage(), ResultsCode.BUSINESSSTATE
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			else if (!errorOccured && StringUtils.isBlank(request.getBusinessZip())) {

				results = Results.getError(ResultsCode.BUSINESSZIP
						.getMessage(), ResultsCode.BUSINESSZIP
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			/*	else if (StringUtils.isBlank(request.getBusinessAprt())) {

				results = Results.getError(ResultsCode.BUSINESSAPART
						.getMessage(), ResultsCode.BUSINESSAPART
						.getCode());
				response.setResults(results);
				errorOccured = true;			
			}*/
			else if (!errorOccured && StringUtils.isBlank(request.getMailingStreet1())) {

				results = Results.getError(ResultsCode.MAILINGSTREET
						.getMessage(), ResultsCode.MAILINGSTREET
						.getCode());
				response.setResults(results);
				errorOccured = true;			

			}
			else if (!errorOccured && StringUtils.isBlank(request.getMailingCity())) {

				results = Results.getError(ResultsCode.MAILINGCITY
						.getMessage(), ResultsCode.MAILINGCITY
						.getCode());
				response.setResults(results);
				errorOccured = true;		

			}
			else if (!errorOccured && StringUtils.isBlank(request.getMailingState())) {

				results = Results.getError(ResultsCode.MAILINGSTATE
						.getMessage(), ResultsCode.MAILINGSTATE
						.getCode());
				response.setResults(results);
				errorOccured = true;	

			}
			else if (!errorOccured && StringUtils.isBlank(request.getMailingZip())) {

				results = Results.getError(ResultsCode.MAILINGZIP
						.getMessage(), ResultsCode.MAILINGZIP
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			/*if (StringUtils.isBlank(request.getMailingAprt())) {

			response.setResults(ResultsCode.MAILINGAPART.getMessage());

		}*/
			else if (!errorOccured && StringUtils.isBlank(request.getHowDidYouHear())) {


				results = Results.getError(ResultsCode.HOWDIDYOUHERE
						.getMessage(), ResultsCode.HOWDIDYOUHERE
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			else if (!errorOccured && StringUtils.isNotBlank(request.getHowDidYouHear()) &&((ProviderConstants.HEAR_FROM_THIRD_PARTY_VALUE).equals(request.getHowDidYouHear()) && StringUtils.isBlank(request.getPromotionCode()))){

				results = Results.getError(ResultsCode.PROMOTIONCODE
						.getMessage(), ResultsCode.PROMOTIONCODE
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			else if (!errorOccured && StringUtils.isBlank(request.getRoleWithinCom())) {

				results = Results.getError(ResultsCode.ROLEWITHINCOM
						.getMessage(), ResultsCode.ROLEWITHINCOM
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			/*else if (StringUtils.isBlank(request.getJobTitle())) {

			results = Results.getError(ResultsCode.JOBTITLE
					.getMessage(), ResultsCode.JOBTITLE
					.getCode());
			response.setResults(results);
			errorOccured = true;

		}
			 */
			else if (!errorOccured && StringUtils.isBlank(request.getServiceCallInd())) {

				results = Results.getError(ResultsCode.SERVICECALL
						.getMessage(), ResultsCode.SERVICECALL
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			else if(!errorOccured && StringUtils.isNotBlank(request.getServiceCallInd())){
				if((!("Yes").equals(request.getServiceCallInd())) && (!("No").equals(request.getServiceCallInd()))){
					results = Results.getError(ResultsCode.INVALID_SERVICE_CALL
							.getMessage(), ResultsCode.INVALID_SERVICE_CALL
							.getCode());
					response.setResults(results);
					errorOccured = true;
				}
			}
			if (!errorOccured && StringUtils.isBlank(request.getFirstName())) {

				results = Results.getError(ResultsCode.FIRSTNAME
						.getMessage(), ResultsCode.FIRSTNAME
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else if (!errorOccured && StringUtils.isBlank(request.getLastName())) {

				results = Results.getError(ResultsCode.LASTNAME
						.getMessage(), ResultsCode.LASTNAME
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}

			else if (!errorOccured && StringUtils.isBlank(request.getEmail())) {

				results = Results.getError(ResultsCode.EMAIL
						.getMessage(), ResultsCode.EMAIL
						.getCode());
				response.setResults(results);
				errorOccured = true;

			} 
			else if(!errorOccured && StringUtils.isNotBlank(request.getEmail())){
				response = validateEmailId(response,request.getEmail(),ProviderConstants.EMAIL_ID);
				if (!errorOccured && (!request.getEmail().equals(
						request.getConfirmEmail()))) {
					results = Results.getError(ResultsCode.CONEMAIL
							.getMessage(), ResultsCode.CONEMAIL
							.getCode());
					response.setResults(results);
					errorOccured = true;
				}
			}
			if(!errorOccured && StringUtils.isNotBlank(request.getAltEmail())){
				response = validateEmailId(response,request.getAltEmail(),ProviderConstants.ALTER_EMAIL_ID);
				if (!errorOccured && (StringUtils.isNotBlank(request.getAltEmail())&& (!request.getAltEmail().equals(
						request.getConfAltEmail())))) {

					results = Results.getError(ResultsCode.ALTEREMAIL
							.getMessage(), ResultsCode.ALTEREMAIL
							.getCode());
					response.setResults(results);
					errorOccured = true;

				}
			}
			if (!errorOccured && StringUtils.isBlank(request.getUserName())) {

				results = Results.getError(ResultsCode.USERNAME
						.getMessage(), ResultsCode.USERNAME
						.getCode());
				response.setResults(results);
				errorOccured = true;
				} 
			else if(!errorOccured && StringUtils.isNotBlank(request.getUserName())
					&& (request.getUserName().trim().length()<8 || request.getUserName().trim().length()>30 || request.getUserName().contains(" "))){
				
				results = Results.getError(ResultsCode.INVALID_USERNAME
						.getMessage(), ResultsCode.INVALID_USERNAME
						.getCode());
				response.setResults(results);
				errorOccured = true;			
			}			
		}

		return response;
	}	

	private CreateProviderAccountResponse validateEmailId(
			CreateProviderAccountResponse response, String email, String type) {
		String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(email);
		if(!m.find()) {
			if(type.equals(ProviderConstants.EMAIL_ID)){
				Results results = Results.getError(ResultsCode.INVALID_EMAIL
						.getMessage(), ResultsCode.INVALID_EMAIL
						.getCode());
				response.setResults(results);
			}
			else if(type.equals(ProviderConstants.ALTER_EMAIL_ID)){
				Results results = Results.getError(ResultsCode.INVALID_ALTERNATE_EMAIL
						.getMessage(), ResultsCode.INVALID_ALTERNATE_EMAIL
						.getCode());
				response.setResults(results);
			}
			errorOccured = true;
		}
		return response;
	}

	private Results isValidNumber(String busPhone1, String type) {


		try {
			if (busPhone1 == null || busPhone1.length() <= 0) {
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBER
							.getMessage(), ResultsCode.PHONENUMBER
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER
							.getMessage(), ResultsCode.FAXNUMBER
							.getCode());
				}
				errorOccured = true;	
				return results;
			}


			else if (busPhone1.trim().length() <= 0 || busPhone1.trim().equals("")){
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBER
							.getMessage(), ResultsCode.PHONENUMBER
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER
							.getMessage(), ResultsCode.FAXNUMBER
							.getCode());
				}
				errorOccured = true;	
				return results;
			}


			// Validating for the NUMBER type
			try {
				long numInt1 = Long.parseLong(busPhone1.trim());

			} catch (NumberFormatException a_Ex) {
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBERVALI
							.getMessage(), ResultsCode.PHONENUMBERVALI
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER_INVALID
							.getMessage(), ResultsCode.FAXNUMBER_INVALID
							.getCode());
				}		
				
				errorOccured = true;	
				return results;
			} catch (Exception a_Ex) {
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBERVALI
							.getMessage(), ResultsCode.PHONENUMBERVALI
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER_INVALID
							.getMessage(), ResultsCode.FAXNUMBER_INVALID
							.getCode());
				}	
				
				errorOccured = true;	
				return results;
			}

			// validation for length
			if (busPhone1.trim().length() != 10) {
				Results results = new Results();
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBERLENGTH
							.getMessage(), ResultsCode.PHONENUMBERLENGTH
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER_INVALID_LENGTH
							.getMessage(), ResultsCode.FAXNUMBER_INVALID_LENGTH
							.getCode());
				}
				errorOccured = true;
				return results;
			}

		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			Results results = null;
			if(ProviderConstants.BUSINESS_NO.equals(type)){
				results = Results.getError(ResultsCode.PHONENUMBERVALI
						.getMessage(), ResultsCode.PHONENUMBERVALI
						.getCode());
			}
			else if(ProviderConstants.FAX_NO.equals(type)){
				results = Results.getError(ResultsCode.FAXNUMBER_INVALID
						.getMessage(), ResultsCode.FAXNUMBER_INVALID
						.getCode());
			}
			
			errorOccured = true;	
			return results;
		}

		return null;
	}

	private CreateProviderAccountResponse validatemainBusiPhoneNo(CreateProviderAccountRequest request, CreateProviderAccountResponse response) {
		String busPhone1 = null;

		try {
			busPhone1 = (request.getMainBusiPhoneNo1() != null) ? request.getMainBusiPhoneNo1().trim() : "";

			Results results = isValidNumber(busPhone1,ProviderConstants.BUSINESS_NO);
			if(results!=null){
				response.setResults(results);
			}

		} catch (Exception a_Ex) {
			errorOccured = true;
			a_Ex.printStackTrace();
			Results results = Results.getError(ResultsCode.MAINBUSINESSPHONE
					.getMessage(), ResultsCode.MAINBUSINESSPHONE
					.getCode());
			response.setResults(results);
		}
		return response;
	}

	private CreateProviderAccountResponse validateFaxNumber(CreateProviderAccountRequest request, CreateProviderAccountResponse response) {
		String busFax1 = null;


		try {
			busFax1 = (request.getBusinessFax1() != null) ? request.getBusinessFax1().trim() : "";

			if (null != busFax1 && busFax1.trim().length() > 0) {
				Results results = isValidNumber(busFax1,ProviderConstants.FAX_NO);
				if(results!=null){
					response.setResults(results);
				}
			}
		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			Results results = Results.getError(ResultsCode.BUSINESSFAX
					.getMessage(), ResultsCode.BUSINESSFAX
					.getCode());
			response.setResults(results);
			errorOccured = true;
		}
		return response;
	}

	private CreateProviderAccountResponse validateOtherPrimaryServices(CreateProviderAccountRequest request, CreateProviderAccountResponse response) {
		if (StringUtils.isNotBlank(request.getPrimaryIndustry())
				&& ProviderConstants.OTHER_PRIMARY_SERVICES_VALUE
				.equals(request.getPrimaryIndustry())
				&& (StringUtils.isBlank(request
						.getOtherPrimaryService()))) {

			Results results = Results.getError(ResultsCode.PRIMARYINDUSTRY
					.getMessage(), ResultsCode.PRIMARYINDUSTRY
					.getCode());
			response.setResults(results);
			errorOccured = true;
		}
		return response;
	}

	private CreateProviderAccountResponse validateBusinessStateZip(ProviderRegistrationVO providerRegistrationVO,CreateProviderAccountResponse response) throws Exception {
		try {
			providerRegistrationVO.setStateType("business");
			providerRegistrationVO = providerRegistrationBO.loadZipSet(providerRegistrationVO);
			List stateTypeList = providerRegistrationVO.getStateTypeList();
			if (!validateStateZip(stateTypeList,
					providerRegistrationVO.getBusinessZip())) {
				Results results = Results.getError(ResultsCode.INVALID_BUSINESSZIP
						.getMessage(), ResultsCode.INVALID_BUSINESSZIP
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
		} catch (Exception a_Ex) {
			logger.log(Level.ERROR,
					"--------- Exception inside validateBusinessStateZip ---",
					a_Ex);
			throw a_Ex;
		}
		return response;
	}

	private CreateProviderAccountResponse validateMailingStateZip(ProviderRegistrationVO providerRegistrationVO,CreateProviderAccountResponse response) throws Exception {
		try {
			providerRegistrationVO.setStateType("mail");
			providerRegistrationVO = providerRegistrationBO.loadZipSet(providerRegistrationVO);
			List stateTypeList = providerRegistrationVO.getStateTypeList();
			if (!validateStateZip(stateTypeList,
					providerRegistrationVO.getMailingZip())) {
				Results results = Results.getError(ResultsCode.INVALID_MAILINGZIP
						.getMessage(), ResultsCode.INVALID_MAILINGZIP
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
		} catch (Exception a_Ex) {
			logger.log(Level.ERROR,
					"--------- Exception inside validateMailingStateZip ---",
					a_Ex);
			throw a_Ex;
		}
		return response;
	}
	
	/**
	 * @param name
	 * 
	 * to replace special html characters in name
	 * 
	 */
	private String replaceSpecialHtmlCharacters(String name) {
		if(StringUtils.isNotBlank(name)){
			name = name.replaceAll("&quot;","\"");
			name = name.replaceAll("&amp;","&");
			name = name.replaceAll("&rsquo;","?");
			name = name.replaceAll("&gt;",">");
			name = name.replaceAll("&lt;","<");
			name = name.replaceAll("&apos;","'");
		}
	
		return name;
	}
	private CreateProviderAccountRequest replaceSpecialChar(CreateProviderAccountRequest request){
				
		try
		{	
			logger.info("in side try block");
			String LegalBusinessName = request.getLegalBusinessName();
			LegalBusinessName = replaceSpecialHtmlCharacters(LegalBusinessName);
			request.setLegalBusinessName(LegalBusinessName);
			
			String DBAName = request.getDBAName();
			DBAName = replaceSpecialHtmlCharacters(DBAName);
			request.setDBAName(DBAName);
			
			String primaryIndustry = request.getPrimaryIndustry();
			primaryIndustry = replaceSpecialHtmlCharacters(primaryIndustry);
			request.setPrimaryIndustry(primaryIndustry);	
			
			String websiteAddress = request.getWebsiteAddress();
			websiteAddress = replaceSpecialHtmlCharacters(websiteAddress);
			request.setWebsiteAddress(websiteAddress);			
			
			String businessStreet1 = request.getBusinessStreet1();
			businessStreet1 = replaceSpecialHtmlCharacters(businessStreet1);
			request.setBusinessStreet1(businessStreet1);
				
			String businessStreet2 = request.getBusinessStreet2();
			businessStreet2 = replaceSpecialHtmlCharacters(businessStreet2);
			request.setBusinessStreet2(businessStreet2);
		
			String businessCity = request.getBusinessCity();
			businessCity = replaceSpecialHtmlCharacters(businessCity);
			request.setBusinessCity(businessCity);
		
			String businessState = (request.getBusinessState());
			businessState = replaceSpecialHtmlCharacters(businessState);
			request.setBusinessState(businessState);
		
			String businessAprt = request.getBusinessAprt();
			businessAprt = replaceSpecialHtmlCharacters(businessAprt);
			request.setBusinessAprt(businessAprt);
		
			String mailAddressChk = request.getMailAddressChk();
			mailAddressChk = replaceSpecialHtmlCharacters(mailAddressChk);
			request.setMailAddressChk(mailAddressChk);
	
			String mailingStreet1 = request.getMailingStreet1();
			mailingStreet1 = replaceSpecialHtmlCharacters(mailingStreet1);
			request.setMailingStreet1(mailingStreet1);
		 
			String mailingStreet2 = (request.getMailingStreet2());
			mailingStreet2 = replaceSpecialHtmlCharacters(mailingStreet2);
			request.setMailingStreet2(mailingStreet2);

			String mailingCity = (request.getMailingCity());
			mailingCity = replaceSpecialHtmlCharacters(mailingCity);
			request.setMailingCity(mailingCity);
	
			String mailingState = (request.getMailingState());
			mailingState = replaceSpecialHtmlCharacters(mailingState);
			request.setMailingState(mailingState);
	
			String mailingAprt = (request.getMailingAprt());
			mailingAprt = replaceSpecialHtmlCharacters(mailingAprt);
			request.setMailingAprt(mailingAprt);
	
			String howDidYouHear = (request.getHowDidYouHear());
			howDidYouHear = replaceSpecialHtmlCharacters(howDidYouHear);
			request.setHowDidYouHear(howDidYouHear);
	
			String promotionCode = request.getPromotionCode();
			promotionCode = replaceSpecialHtmlCharacters(promotionCode);
			request.setPromotionCode(promotionCode);
		
			String roleWithinCom = request.getRoleWithinCom();
			roleWithinCom = replaceSpecialHtmlCharacters(roleWithinCom);
			request.setRoleWithinCom(roleWithinCom);
	
			String jobTitle= request.getJobTitle();
			jobTitle = replaceSpecialHtmlCharacters(jobTitle);
			request.setJobTitle(jobTitle);
		 
			String serviceCallInd= request.getServiceCallInd();
			serviceCallInd = replaceSpecialHtmlCharacters(serviceCallInd);
			request.setServiceCallInd(serviceCallInd);
	
			String firstName= request.getFirstName();
			firstName = replaceSpecialHtmlCharacters(firstName);
			request.setFirstName(firstName);
		
			String middleName= request.getMiddleName();
			middleName = replaceSpecialHtmlCharacters(middleName);
			request.setMiddleName(middleName);
	
			String lastName= request.getLastName();
			lastName = replaceSpecialHtmlCharacters(lastName);
			request.setLastName(lastName);
	
			String nameSuffix= request.getNameSuffix();
			nameSuffix = replaceSpecialHtmlCharacters(nameSuffix);
			request.setNameSuffix(nameSuffix);
	
			/*	String email= request.getEmail();
			email = replaceChar(email);
			request.setEmail(email);

		String confirmEmail= request.getConfirmEmail();
			confirmEmail = replaceChar(confirmEmail);
			request.setConfirmEmail(confirmEmail);
	
			String altEmail= request.getAltEmail();
			altEmail = replaceChar(altEmail);
			request.setAltEmail(altEmail);
		
			String confAltEmail= request.getConfAltEmail();
			confAltEmail = replaceChar(confAltEmail);
			request.setConfAltEmail(confAltEmail);
		
			String userName= request.getUserName();
			userName = replaceChar(userName);
			request.setUserName(userName);*/
		}
		catch (Exception a_Ex) {
			logger.info(
					"--------- Exception inside specialChar validate  ---");		}
		return request;
	}
	
	
	private boolean validateStateZip(List list, String zip) {
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

	private CreateProviderAccountResponse validateUserName(ProviderRegistrationVO providerRegistrationVO,CreateProviderAccountResponse response){
		boolean isDulplicate = providerRegistrationBO.validateUserName(providerRegistrationVO.getUserName());
		if(isDulplicate){
			Results results = Results.getError(ResultsCode.DUPLICATE_USERNAME
					.getMessage(), ResultsCode.DUPLICATE_USERNAME
					.getCode());
			response.setResults(results);
			errorOccured = true;		
		}
		return response;
	}
	
	public IRegistrationBO getProviderRegistrationBO() {
		return providerRegistrationBO;
	}
	public void setProviderRegistrationBO(IRegistrationBO providerRegistrationBO) {
		this.providerRegistrationBO = providerRegistrationBO;
	}
	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

}
