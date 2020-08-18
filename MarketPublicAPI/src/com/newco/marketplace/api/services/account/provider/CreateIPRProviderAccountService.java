package com.newco.marketplace.api.services.account.provider;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountRequest;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.leads.AddLeadRequest;
import com.newco.marketplace.api.leads.LoginInsideSalesRequest;
import com.newco.marketplace.api.leads.Parameters;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.account.Provider.ProviderAccountMapper;
import com.newco.marketplace.business.iBusiness.leadprofile.ILeadProfileBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.provider.IRegistrationBO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;



//SL 16934 Service class for Provider Registration using IPR
public class CreateIPRProviderAccountService{
	private IRegistrationBO providerRegistrationBO;
	private Logger logger = Logger.getLogger(CreateIPRProviderAccountService.class);
	private ILookupBO lookupBO;
	private ILeadProfileBO leadProfileBO; 
	private String isApiUserName;
	private String isApiPassword;
	private String isApiToken;
	
	public String getIsApiUserName() {
		return isApiUserName;
	}

	public void setIsApiUserName(String isApiUserName) {
		this.isApiUserName = isApiUserName;
	}

	public String getIsApiPassword() {
		return isApiPassword;
	}

	public void setIsApiPassword(String isApiPassword) {
		this.isApiPassword = isApiPassword;
	}

	public String getIsApiToken() {
		return isApiToken;
	}

	public void setIsApiToken(String isApiToken) {
		this.isApiToken = isApiToken;
	}

	private boolean errorOccured = false;
	/**
	 * This method is for creating Provider account.
	 * Code created by 643272 date 03-09-2013 
	 */


	public CreateIPRProviderAccountService () {

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
					if(errorOccured){
						return response;
					}
					//Setting referral code as promo_cd column in vendor_hdr table
					providerRegistrationVO.setPromotionCode(request.getReferralCode());
					providerRegistrationVO.setBusinessFax1("");
					providerRegistrationVO.setBusinessFax2("");
					providerRegistrationVO.setBusinessFax3("");
					
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
					
					int crmResponse = 0;
					String isResponse = null;

					if(StringUtils.isNotEmpty(request.getMainBusiPhoneNo1()) && 
							StringUtils.isNotEmpty(request.getEmail()) &&
							StringUtils.isNotEmpty(request.getBusinessZip()) &&
							StringUtils.isNotEmpty(request.getLegalBusinessName())
							){
						boolean insideSalesApiInvocationSwitch=false;
						try{
						// switch to check whether Inside Sales API need to be invoked
						insideSalesApiInvocationSwitch =  leadProfileBO.getInsideSalesApiInvocationSwitch();
						}catch(Exception e){
							logger.error("Exception while fetching insideSales API switch"+e.getMessage());
						}
						if(insideSalesApiInvocationSwitch){
							if(null!= request.getReferralCode() && request.getReferralCode().trim().toUpperCase().startsWith(PublicAPIConstant.INSIDE_SALES_REFERRAL_PREFIX)){
								isResponse  = retrieveIsResponse(request.getReferralCode());
								leadProfileBO.updateInsideSalesResponse(isResponse,response.getVendorId(),PublicAPIConstant.IS_CRM_SUCCESS_CODE);
							}else{
								try{
									// Call to the Inside Sales API to complete Provider registration in Inside Sales. 
									isResponse = leadProfileBO.createInsideSalesProfile(getInsideSalesRequestJSON(request,response,providerRegistrationVO),getInsideSalesLoginRequestJSON());			
									Integer crmStatus=PublicAPIConstant.IS_CRM_FAILURE_CODE;
									if(StringUtils.isNotBlank(isResponse) && StringUtils.isNumeric(isResponse)){
								     crmStatus=PublicAPIConstant.IS_CRM_SUCCESS_CODE;
									}
									leadProfileBO.updateInsideSalesResponse(isResponse,response.getVendorId(),crmStatus);
									}
									catch(Exception e){
										logger.error("Exception while invoking insideSales API "+e.getMessage());
									}
							}
						}else{
							// if switch is OFF invoke the normal loadstar crm API
							// Call to the CRM API to complete Provider registration in CRM. 
							// TODO - Log the Status of CRM provider creation
							// TODO - Do we have to add this to a table saying that we have created a provi7der in CRM??
							// for example vendor_hdr.crm_status (CRM_Created/CRM_Not_Created)
							crmResponse = leadProfileBO.createCRMProfile(getCRMRequestJSON(request,response));	
							logger.info("CRM Respose is "+crmResponse);
							// TODO Update vendor_hdr table
							if(201 == crmResponse){
								logger.info("Set the CRM indicator as success");
								leadProfileBO.updateCrmStatus(PublicAPIConstant.CRM_SUCCESS_CODE,response.getVendorId());
							}else if(500 == crmResponse || 400 == crmResponse){
								logger.info("Set the CRM indicator as failure");
								leadProfileBO.updateCrmStatus(PublicAPIConstant.CRM_FAILURE_CODE,response.getVendorId());
							}							
						}
					}else{
						logger.info("Mandatory fields are missing in the request : Not able to invoke CRM for registration");
					}
					
					
				} 
				catch(BusinessServiceException e) {
					logger.error("CreateProviderService exception detail: " + e.getMessage());
					response.setResults(
							Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
									ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
				}
				catch (DuplicateUserException e) {
					logger.error("Duplicate User Exists- CreateProviderAccountService: error: " + e.getMessage());	
					Results results = Results.getError(ResultsCode.DUPLICATE_USERNAME_IPR
							.getMessage(), ResultsCode.DUPLICATE_USERNAME_IPR
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
	
	

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	private String getInsideSalesLoginRequestJSON() {
		LoginInsideSalesRequest insideSalesLoginRequest = new LoginInsideSalesRequest();
		insideSalesLoginRequest.setOperation(ProviderConstants.INSIDE_SALES_LOGIN_OPERATION);
		List<String> parameters = new ArrayList<String>();
		parameters.add(isApiUserName);
		parameters.add(isApiPassword);
		parameters.add(isApiToken);
		insideSalesLoginRequest.setParameters(parameters);
		String insideSaleLoginReq = formJson(insideSalesLoginRequest);
		return insideSaleLoginReq;
	}

	/**
	 * @param insideSalesLoginRequest
	 */
	private String formJson(LoginInsideSalesRequest insideSalesLoginRequest) {
		// TODO Auto-generated method stub
		String insideSaleLoginReq = null;
		JSON j = JSONSerializer.toJSON(insideSalesLoginRequest);
		insideSaleLoginReq  = j.toString();  
		return insideSaleLoginReq;
	}

	private String getCRMRequestJSON(CreateProviderAccountRequest request,CreateProviderAccountResponse createProviderAccountResponse){
		StringBuffer requestString = new StringBuffer();
		
		// Mandatory
		try {
			requestString.append(PublicAPIConstant.REQ_PHONE+PublicAPIConstant.EQUALS+URLEncoder.encode(request.getMainBusiPhoneNo1(),PublicAPIConstant.UTF)+PublicAPIConstant.AND);			
			requestString.append(PublicAPIConstant.REQ_EMAIL+PublicAPIConstant.EQUALS+URLEncoder.encode(request.getEmail(),PublicAPIConstant.UTF)+PublicAPIConstant.AND);
			requestString.append(PublicAPIConstant.REQ_ZIP+PublicAPIConstant.EQUALS+URLEncoder.encode(request.getBusinessZip(),PublicAPIConstant.UTF)+PublicAPIConstant.AND);
			requestString.append(PublicAPIConstant.REQ_LEGALNAME+PublicAPIConstant.EQUALS+URLEncoder.encode(request.getLegalBusinessName(),PublicAPIConstant.UTF));
			
			//Commented as part of IPR SL-16934 -Stop coping Legal Business name to DBA Name
			// Set legal Name as doing business as name
			//requestString.append(PublicAPIConstant.REQ_DOINGBUSINESSAS+PublicAPIConstant.EQUALS+URLEncoder.encode(request.getLegalBusinessName(),PublicAPIConstant.UTF));	
			
			// Optional
			if(StringUtils.isNotEmpty(request.getFirstName()) || StringUtils.isNotEmpty(request.getLastName())){
				requestString.append(PublicAPIConstant.AND);
				String contactFirstLastName = request.getFirstName() +" "+ request.getLastName();
				requestString.append(PublicAPIConstant.REQ_NAME+PublicAPIConstant.EQUALS+URLEncoder.encode(contactFirstLastName,PublicAPIConstant.UTF));
			}
			if(StringUtils.isNotEmpty(request.getPrimaryIndustry())){
				requestString.append(PublicAPIConstant.AND);
				requestString.append(PublicAPIConstant.REQ_SERVICE_INDUSTRY+PublicAPIConstant.EQUALS+URLEncoder.encode(request.getPrimaryIndustry(),PublicAPIConstant.UTF));
			}
			if(StringUtils.isNotEmpty(request.getBusinessStreet1())){
				requestString.append(PublicAPIConstant.AND);
				requestString.append(PublicAPIConstant.REQ_BUSSINESS_ADDRESS_STREET1+PublicAPIConstant.EQUALS+URLEncoder.encode(request.getBusinessStreet1(),PublicAPIConstant.UTF));
			}	
			if(StringUtils.isNotEmpty(request.getReferralCode())){
				requestString.append(PublicAPIConstant.AND);
				requestString.append(PublicAPIConstant.REQ_REF_CODE+PublicAPIConstant.EQUALS+URLEncoder.encode(request.getReferralCode(),PublicAPIConstant.UTF));
			}	
			if(null != createProviderAccountResponse.getVendorId()){
				requestString.append(PublicAPIConstant.AND);
				requestString.append(PublicAPIConstant.OPTIONAL_FIRM_ID+PublicAPIConstant.EQUALS+URLEncoder.encode(createProviderAccountResponse.getVendorId().toString(),PublicAPIConstant.UTF));
			}
			if(null != createProviderAccountResponse.getVendorResourceId()){
				requestString.append(PublicAPIConstant.AND);
				requestString.append(PublicAPIConstant.OPTIONAL_PROVIDER_ID+PublicAPIConstant.EQUALS+URLEncoder.encode(createProviderAccountResponse.getVendorResourceId().toString(),PublicAPIConstant.UTF));
			}
			
			requestString.append(PublicAPIConstant.AND);
			requestString.append(PublicAPIConstant.OPTIONAL_DECISION_MAKER+PublicAPIConstant.EQUALS+URLEncoder.encode("true",PublicAPIConstant.UTF));
		
			
			logger.info("CRM Request is ::"+ requestString.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestString.toString();
	}
	
	/**
	 * @param request
	 * @param createProviderAccountResponse
	 * @return
	 * method to form inside sales request
	 */
	private String getInsideSalesRequestJSON(CreateProviderAccountRequest request,CreateProviderAccountResponse createProviderAccountResponse,ProviderRegistrationVO providerRegistrationVO){
		String insideSaleReq = null;
		AddLeadRequest addLeadRequest = null;
		try{
			if(null !=request && null!=createProviderAccountResponse ){
				addLeadRequest = mapLeadRequestDetails(request,providerRegistrationVO);
				insideSaleReq = formJson(addLeadRequest);
			}
		
			logger.info("Inside Sales Request: "+insideSaleReq); 
				
		}catch(Exception e){
			e.printStackTrace();
		}
		return insideSaleReq;
	}
	
	/**
	 * @param addLeadRequest
	 */
	private String formJson(AddLeadRequest addLeadRequest) {
		// TODO Auto-generated method stub
		String insideSaleReq = null;
		JSON j = JSONSerializer.toJSON(addLeadRequest);
		insideSaleReq  = j.toString();  
		return insideSaleReq;
	}

	/**
	 * @param createProviderAccountResponse 
	 * @param request 
	 * @return
	 * method to map required parameters
	 */
	private AddLeadRequest mapLeadRequestDetails(CreateProviderAccountRequest request,ProviderRegistrationVO providerRegistrationVO) {
		AddLeadRequest addLeadRequest = new AddLeadRequest();  
		Parameters lead = new Parameters();
		if(StringUtils.isNotBlank(request.getFirstName())){
			lead.setFirst_name(request.getFirstName());
		}
		/*if(StringUtils.isNotBlank(request.getMiddleName())){
			lead.setMiddle_name(request.getMiddleName());
		}*/
		if(StringUtils.isNotBlank(request.getLastName())){
			lead.setLast_name(request.getLastName());
		}
		/*if(StringUtils.isNotBlank(request.getJobTitle())){
			lead.setTitle(request.getJobTitle());
		}*/
		if(StringUtils.isNotBlank(request.getMainBusiPhoneNo1())){
			lead.setPhone(request.getMainBusiPhoneNo1());
		}
		/*if(StringUtils.isNotBlank(request.getBusinessFax1())){
			lead.setFax(request.getBusinessFax1());
		}*/
		if(StringUtils.isNotBlank(request.getEmail())){
			lead.setEmail(request.getEmail());
		}
		/*if(StringUtils.isNotBlank(request.getWebsiteAddress())){
			lead.setWebsite(request.getWebsiteAddress());
		}*/
		if(StringUtils.isNotBlank(request.getBusinessStreet1())){
			lead.setAddr1(request.getBusinessStreet1());
		}
		/*if(StringUtils.isNotBlank(request.getBusinessStreet2())){
			lead.setAddr2(request.getBusinessStreet2());
		}*/
		if(StringUtils.isNotBlank(request.getBusinessZip())){
			lead.setZip(request.getBusinessZip());
		}
		if(StringUtils.isNotBlank(request.getLegalBusinessName())){
			lead.setCompany_name(request.getLegalBusinessName());
		}
		if(StringUtils.isNotBlank(request.getPrimaryIndustry())){
			lead.setIndustry(request.getPrimaryIndustry());
		}
		if(null!=providerRegistrationVO.getVendorId()){
			lead.setExternal_id(providerRegistrationVO.getVendorId().toString());
		}
		if(StringUtils.isNotBlank(providerRegistrationVO.getBusinessCity())){
			lead.setCity(providerRegistrationVO.getBusinessCity()); 
		} 
		if(StringUtils.isNotBlank(providerRegistrationVO.getBusinessState())){
			lead.setState_abbrev(providerRegistrationVO.getBusinessState());
			lead.setState(leadProfileBO.getState(providerRegistrationVO.getBusinessState()));
		} 
		List<Parameters> parameters = new ArrayList<Parameters>();
		parameters.add(lead);
		addLeadRequest.setParameters(parameters);
		addLeadRequest.setOperation(ProviderConstants.ADD_LEAD_OPERATION);
		return addLeadRequest;
	}

	private CreateProviderAccountResponse validateLookUpValues(
			ProviderRegistrationVO loadValues,
			CreateProviderAccountResponse response, ProviderRegistrationVO providerRegistrationVO) {
		List primaryIndList = loadValues.getPrimaryIndList();
		String primaryIndExist = validateValues(primaryIndList,providerRegistrationVO.getPrimaryIndustry());
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
		return response;
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
			if (StringUtils.isBlank(request.getLegalBusinessName())) {

				results = Results.getError(ResultsCode.INVALID_LEGALBUSINESSNAME
						.getMessage(), ResultsCode.INVALID_LEGALBUSINESSNAME
						.getCode());
				response.setResults(results);
				errorOccured = true;
				//response.setResults(ResultsCode.LEGALBUSINESSNAME.getMessage());			

			}
			else if (!errorOccured && StringUtils.isBlank(request.getPrimaryIndustry())) {

				results = Results.getError(ResultsCode.INVALID_PRIMARY_INDUSTRY
						.getMessage(), ResultsCode.INVALID_PRIMARY_INDUSTRY
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			else if (!errorOccured && StringUtils.isBlank(request.getMainBusiPhoneNo1())) {

				results = Results.getError(ResultsCode.PHONENUMBER
						.getMessage(), ResultsCode.PHONENUMBER
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
						
			else if (!errorOccured && StringUtils.isBlank(request.getFirstName())) {

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
			else if(!errorOccured && StringUtils.isBlank(request.getEmail())){
				results = Results.getError(ResultsCode.INVALID_EMAIL
						.getMessage(), ResultsCode.INVALID_EMAIL
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else if(!errorOccured && StringUtils.isNotBlank(request.getEmail()) && validateEmailId(request)==false){
				 results = Results.getError(ResultsCode.INVALID_EMAIL
						.getMessage(), ResultsCode.INVALID_EMAIL
						.getCode());
				response.setResults(results);
				
			}
			/*
			else if (!errorOccured && StringUtils.isBlank(request.getReferralCode())) {
				//Still some changes need to be made for referral code error message
				results = Results.getError(ResultsCode.INVALID_REFERRAL_CODE
						.getMessage(), ResultsCode.INVALID_REFERRAL_CODE
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}	
			*/	
				
		}

		return response;
	}	

	private boolean validateEmailId(
			CreateProviderAccountRequest request) {
		String email=request.getEmail();
		String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(email);
		boolean emailInd=false;
		if(m.find()) {
			if(null==request.getUserName())
			{
				request.setUserName(request.getEmail());
				if(null==request.getHowDidYouHear())
				{
					request.setHowDidYouHear("-1");
				}
			}
			emailInd=true;
		}
		return emailInd;
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
				errorOccured = true;	
				return results;
			} catch (Exception a_Ex) {
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBERVALI
							.getMessage(), ResultsCode.PHONENUMBERVALI
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

	private CreateProviderAccountResponse validateBusinessStateZip(ProviderRegistrationVO providerRegistrationVO,CreateProviderAccountResponse response) throws Exception {
		try {
			providerRegistrationVO.setStateType("business");
			LocationVO locationVO= lookupBO.checkIFZipExists(providerRegistrationVO.getBusinessZip());
			if (null==locationVO) {
				Results results = Results.getError(ResultsCode.INVALID_ZIP_CODE
						.getMessage(), ResultsCode.INVALID_ZIP_CODE
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else
			{
				providerRegistrationVO.setBusinessState(locationVO.getState());
				providerRegistrationVO.setBusinessCity(locationVO.getCity());
			}
		} catch (Exception a_Ex) {
			logger.log(Level.ERROR,
					"--------- Exception inside validateBusinessStateZip ---",
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
			
			String primaryIndustry = request.getPrimaryIndustry();
			primaryIndustry = replaceSpecialHtmlCharacters(primaryIndustry);
			request.setPrimaryIndustry(primaryIndustry);
	
			String promotionCode = request.getReferralCode();
			promotionCode = replaceSpecialHtmlCharacters(promotionCode);
			request.setReferralCode(promotionCode);	
			
	
			String firstName= request.getFirstName();
			firstName = replaceSpecialHtmlCharacters(firstName);
			request.setFirstName(firstName);
	
			String lastName= request.getLastName();
			lastName = replaceSpecialHtmlCharacters(lastName);
			request.setLastName(lastName);
			
		}
		catch (Exception a_Ex) {
			logger.info(
					"--------- Exception inside specialChar validate  ---");		}
		return request;
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

	public ILeadProfileBO getLeadProfileBO() {
		return leadProfileBO;
	}

	public void setLeadProfileBO(ILeadProfileBO leadProfileBO) {
		this.leadProfileBO = leadProfileBO;
	}

	
	/** SL-20971: private method to retrieve IsResponse from referral code
	 * @param referralCode
	 * @return IsResponse
	 */
	private String retrieveIsResponse(String referralCode) {
		String[] referralCodeSplit = referralCode.split("__");
		String isResponse = null;
		if (referralCodeSplit.length == 3){
			isResponse = referralCodeSplit[2];
		}
		return isResponse;
	}
}
