package com.newco.marketplace.api.beans.leadprofile;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.leadprofile.leadprofilerequest.LeadProfileCreationRequest;
import com.newco.marketplace.api.beans.leadprofile.leadprofileresponse.LeadProfileCreationResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.leadprofile.ILeadProfileBO;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;




public class LeadProfileCreationService {
	
	private ILeadProfileBO leadProfileBO;
	private Logger logger = Logger.getLogger(LeadProfileCreationService.class);
	private boolean errorOccured = false;

//	private LeadProcessingBO leadProcessingBO;
	

	/**
	 * Subclass needs to implement API specific logic here.
	 */
	public LeadProfileCreationResponse execute(LeadProfileCreationRequest request) {		

		logger.info("Entering Lead Profile creation API's execute method");	
		LeadProfileCreationResponse response = new LeadProfileCreationResponse();
		if(request!=null){
			if(errorOccured){
				errorOccured = false;
			}	
			request = replaceSpecialChar(request);
			//Validating any empty fields in the request object
			response = validateFields(request,response);
			
			// TODO - If the firm has a partner ID, please update the data
			
			//Validating if provider firm don't have any partner id 
			if(!errorOccured){
			response = validateProviderFirmLeadEligibility(request,response);
			}
			
			LeadProfileCreationRequestVO leadProfileCreationRequestVO=new LeadProfileCreationRequestVO();
			if(errorOccured){
				return response;
			}
			else{
				try{
					//If no validation error then copying the request data along with the price data of the provided project type to the
					//master data object LeadProfileCreationRequestVO for persisting purpose
					copyRequestDataToVO(leadProfileCreationRequestVO,request);
					
					//Method to persist data related to Lead profile creations
					LeadProfileCreationResponseVO vo = leadProfileBO.createLeadProfile(leadProfileCreationRequestVO);	
					
					// Get the results from the VO and set it to response class
					if(null!=vo && null!=vo.getErrors()){
						Results result = new Results();
						
						// Map errors
						List<String> errors = vo.getErrors().getError();
						List<ErrorResult> errorList = new ArrayList<ErrorResult>();
						for(String error: errors ){
							ErrorResult errorResult = new ErrorResult();
							errorResult.setMessage(error);
							errorList.add(errorResult);
						}
						result.setError(errorList);
						response.setResults(result);
					}else {						
						//Success
						String partnerId = vo.getPartnerId();
						response.setPartnerId(partnerId);
						response.setResults(Results.getSuccess(ResultsCode.LEAD_REGISTRATION_SUCCESS.getMessage()));
					}
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
	
	private void copyRequestDataToVO(LeadProfileCreationRequestVO fetchProviderFirmRequest,
			LeadProfileCreationRequest request)
	{
		if(!StringUtils.isBlank(request.getAvailableDaysOfWeek()))
		{
			String passedDaysOfWeek=request.getAvailableDaysOfWeek();
			String[] passedDaysOfWeekList=passedDaysOfWeek.split(",");
			for(String dayName:passedDaysOfWeekList){
				 if(dayName.equalsIgnoreCase("1")){
					fetchProviderFirmRequest.setSundayInd(1);
				}else if(dayName.equalsIgnoreCase("2")){
					fetchProviderFirmRequest.setMondayInd(1);
				}else if(dayName.equalsIgnoreCase("3")){
					fetchProviderFirmRequest.setTuesndayInd(1);
				}else if(dayName.equalsIgnoreCase("4")){
					fetchProviderFirmRequest.setWednesdayInd(1);}
				else if(dayName.equalsIgnoreCase("5")){
					fetchProviderFirmRequest.setThursdayInd(1);}
				else if(dayName.equalsIgnoreCase("6")){
					fetchProviderFirmRequest.setFridayInd(1);}
				else if(dayName.equalsIgnoreCase("7")){
					fetchProviderFirmRequest.setSaturdayInd(1);
				}
			}
		}
		if(!StringUtils.isBlank(request.getLeadPackageID())){
			fetchProviderFirmRequest.setLeadPackageID(Integer.parseInt(request.getLeadPackageID()));
		}
		//If package id is 4 set the daily limit, monthly budget, monthly limit from request else fetch it from look up table
		if(request.getLeadPackageID().equalsIgnoreCase(PublicAPIConstant.LEAD_CUSTOM_PACKAGE_ID))
		{
			try {
				HashMap<String,Object> packageDetails = new HashMap<String, Object>();
				packageDetails=leadProfileBO.getPackagePriceDetails(request.getLeadPackageID());
				if(null!=packageDetails)
				{
					Object leadPackageDescription= packageDetails.get("leadPackageDescription");
					fetchProviderFirmRequest.setLeadPackageDesc(leadPackageDescription.toString());
				}
			} catch (Exception e) {
				logger.info("Exception occurred while fetching price detail for provided lead package id.");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			if(!StringUtils.isBlank(request.getLeadDailyLimit())){
					fetchProviderFirmRequest.setLeadDailyLimit(request.getLeadDailyLimit());
			}
			if(null!=request.getLeadMonthlyBudget()){
				fetchProviderFirmRequest.setMonthlyBudget((Double.valueOf(request.getLeadMonthlyBudget())));
			}
			if(!StringUtils.isBlank(request.getLeadMonthlyLimit())){
				fetchProviderFirmRequest.setLeadMonthlyLimit(request.getLeadMonthlyLimit());
			}
		}
		else
		{
			
			try {
				HashMap<String,Object> packageDetails = new HashMap<String, Object>();
				packageDetails=leadProfileBO.getPackagePriceDetails(request.getLeadPackageID());
				if(null!=packageDetails)
				{
					Object dailyLimit=packageDetails.get("dailyLimit");
					Object monthlylimit=packageDetails.get("monthlyLimit");
					Object monthlyBudget=packageDetails.get("monthlyBudget");
					Object leadPackageDescription= packageDetails.get("leadPackageDescription");
					
					fetchProviderFirmRequest.setLeadDailyLimit(String.valueOf(dailyLimit));
					fetchProviderFirmRequest.setMonthlyBudget(new Double(monthlyBudget.toString()));
					fetchProviderFirmRequest.setLeadMonthlyLimit(String.valueOf(monthlylimit));
					fetchProviderFirmRequest.setLeadPackageDesc(leadPackageDescription.toString());
				}
				
			} catch (Exception e) {
				logger.info("Exception occurred while fetching price detail for provided lead package id.");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!StringUtils.isBlank(request.getLeadEmailId())){
			fetchProviderFirmRequest.setLeadEmailId(request.getLeadEmailId());
		}	
		if(!StringUtils.isBlank(request.getLeadPhoneNo())){
			fetchProviderFirmRequest.setLeadPhoneNo(request.getLeadPhoneNo());
		}
		if(!StringUtils.isBlank(request.getLeadSmsNo())){
			fetchProviderFirmRequest.setLeadSmsNo(request.getLeadSmsNo());
		}
		if(request.isInterestedInSHSLeads()){
			fetchProviderFirmRequest.setInterestedInSHSLeads(true);
		}else{
			fetchProviderFirmRequest.setInterestedInSHSLeads(false);
		}
		
		if(!StringUtils.isBlank(request.getProviderFirmId())){
			fetchProviderFirmRequest.setProviderFirmId(request.getProviderFirmId());
		}	
		if(!StringUtils.isBlank(request.getLocationType())){
			fetchProviderFirmRequest.setLocationType(request.getLocationType());
		}
		if(!StringUtils.isBlank(request.getCoverageInMiles())){
			fetchProviderFirmRequest.setCoverageInMiles(request.getCoverageInMiles());
		}
		if(!StringUtils.isBlank(request.getSkill())){
			fetchProviderFirmRequest.setSkill(request.getSkill());
		}
		if(null != request.getIsLicensingRequired() && request.getIsLicensingRequired().equalsIgnoreCase("true")){
			fetchProviderFirmRequest.setIsLicensingRequired(1);
		}
		else
		{
			fetchProviderFirmRequest.setIsLicensingRequired(0);
		}
		if(!StringUtils.isBlank(request.getLicensingStates())&& request.getIsLicensingRequired().equalsIgnoreCase("true")){
			fetchProviderFirmRequest.setLicensingStates(request.getLicensingStates());
		}
		else
		{
			fetchProviderFirmRequest.setLicensingStates(null);
		}
		if(!StringUtils.isBlank(request.getUrgencyOfService()))
		{
			fetchProviderFirmRequest.setUrgencyServices(request.getUrgencyOfService());
		}
		fetchProviderFirmRequest.setLmsPassword(AdminUtil.generatePassword());
		
		if(!StringUtils.isBlank(request.getComments())){
			fetchProviderFirmRequest.setComments(request.getComments());
		}
		if(!StringUtils.isBlank(request.getIsMultipleLocation()) &&  request.getIsMultipleLocation().equalsIgnoreCase("y")){
			fetchProviderFirmRequest.setIsMultipleLocation("Y");
		}else{
			fetchProviderFirmRequest.setIsMultipleLocation("N");
		}
		
	}
	
	private LeadProfileCreationResponse validateProviderFirmLeadEligibility(LeadProfileCreationRequest request,LeadProfileCreationResponse response) 
	{
		
		String prviderFirmId=request.getProviderFirmId();
		boolean providerFirmLeadEligibilityInd;
		try {
			providerFirmLeadEligibilityInd = leadProfileBO.validateProviderFirmLeadEligibility(prviderFirmId);
			if(providerFirmLeadEligibilityInd==false)
			{
				Results results = null;
				results = Results.getError(ResultsCode.INVALID_LEAD_ELIGIBILITY
						.getMessage(), ResultsCode.INVALID_LEAD_ELIGIBILITY
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	private LeadProfileCreationResponse validateFields(LeadProfileCreationRequest request,LeadProfileCreationResponse response) {		
		Results results = null;
		if(!errorOccured){
			if (StringUtils.isBlank(request.getProviderFirmId())) {

				results = Results.getError(ResultsCode.INVALID_VENDOR
						.getMessage(), ResultsCode.INVALID_VENDOR
						.getCode());
				response.setResults(results);
				errorOccured = true;
				//response.setResults(ResultsCode.LEGALBUSINESSNAME.getMessage());			

			}
			else if (!errorOccured && StringUtils.isBlank(request.getLeadEmailId())) {
	
					results = Results.getError(ResultsCode.INVALID_EMAIL_ID
							.getMessage(), ResultsCode.INVALID_EMAIL_ID
							.getCode());
					response.setResults(results);
					errorOccured = true;
				}
			else if(!errorOccured && StringUtils.isNotBlank(request.getLeadEmailId()) && validateEmailId(request.getLeadEmailId())==false ){
				results = Results.getError(ResultsCode.INVALID_EMAIL_ID
								.getMessage(), ResultsCode.INVALID_EMAIL_ID
								.getCode());
						response.setResults(results);
						errorOccured = true;
				
			}
			else if (!errorOccured && StringUtils.isBlank(request.getLeadPhoneNo())) {

				results = Results.getError(ResultsCode.INVALID_LEAD_PHONE_NO
						.getMessage(), ResultsCode.INVALID_LEAD_PHONE_NO
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			else if(!errorOccured && StringUtils.isBlank(request.getLeadPackageID())){
				results = Results.getError(ResultsCode.INVALID_PACKAGE_ID
						.getMessage(), ResultsCode.INVALID_PACKAGE_ID
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			//Validating leadDailyLimit only when when custom package id(value 4) selected 
			else if (!errorOccured && StringUtils.isBlank(request.getLeadDailyLimit()) && request.getLeadPackageID().equalsIgnoreCase(PublicAPIConstant.LEAD_CUSTOM_PACKAGE_ID)) {
				//Still some changes need to be made for referral code error message
				results = Results.getError(ResultsCode.INVALID_LEAD_DAILY_LIMIT
						.getMessage(), ResultsCode.INVALID_LEAD_DAILY_LIMIT
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			//Validating leadMonthlyLimit only when custom package id(value 4) selected 
			else if(!errorOccured && StringUtils.isBlank(request.getLeadMonthlyLimit()) && request.getLeadPackageID().equalsIgnoreCase(PublicAPIConstant.LEAD_CUSTOM_PACKAGE_ID)){
				results = Results.getError(ResultsCode.INVALID_LEAD_MONTHLY_LIMIT
						.getMessage(), ResultsCode.INVALID_LEAD_MONTHLY_LIMIT
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			//Validating leadMonthlyBudget only when custom package id(value 4) selected 
			else if(!errorOccured && StringUtils.isBlank(request.getLeadMonthlyBudget())&& request.getLeadPackageID().equalsIgnoreCase(PublicAPIConstant.LEAD_CUSTOM_PACKAGE_ID)){
				results = Results.getError(ResultsCode.INVALID_LEAD_MONTHLY_BUDGET
						.getMessage(), ResultsCode.INVALID_LEAD_MONTHLY_BUDGET
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else if(!errorOccured && StringUtils.isBlank(request.getAvailableDaysOfWeek())){
				results = Results.getError(ResultsCode.INVALID_AVAILABLE_DAYS_WEEK
						.getMessage(), ResultsCode.INVALID_AVAILABLE_DAYS_WEEK
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			//Validation passed available days of week are in predefined format or not
			else if(!errorOccured && StringUtils.isNotBlank(request.getAvailableDaysOfWeek()) && validateAvailableDaysOfWeek(request.getAvailableDaysOfWeek())==false){
				results = Results.getError(ResultsCode.INVALID_FORMAT_AVAILABLE_DAYS_WEEK
						.getMessage(), ResultsCode.INVALID_FORMAT_AVAILABLE_DAYS_WEEK
						.getCode());
				response.setResults(results);
				errorOccured = true;			
			}
			else if(!errorOccured && StringUtils.isBlank(request.getLocationType())){
				results = Results.getError(ResultsCode.INVALID_LOCATION_TYPE
						.getMessage(), ResultsCode.INVALID_LOCATION_TYPE
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else if(!errorOccured && StringUtils.isNotBlank(request.getLocationType()) &&
					!(request.getLocationType().equalsIgnoreCase(PublicAPIConstant.LOCATION_TYPE_RES) || request.getLocationType().equalsIgnoreCase(PublicAPIConstant.LOCATION_TYPE_COM) 
							||request.getLocationType().equalsIgnoreCase(PublicAPIConstant.LOCATION_TYPE_BOTH)) ){
				results = Results.getError(ResultsCode.INVALID_LOCATION_TYPE
						.getMessage(), ResultsCode.INVALID_LOCATION_TYPE
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else if(!errorOccured && StringUtils.isBlank(request.getUrgencyOfService())){
				results = Results.getError(ResultsCode.INVALID_URGENCY_SERVICES
						.getMessage(), ResultsCode.INVALID_URGENCY_SERVICES
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else if(!errorOccured && StringUtils.isBlank(request.getSkill())){
				results = Results.getError(ResultsCode.INVALID_SKILL
						.getMessage(), ResultsCode.INVALID_SKILL
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			else if(!errorOccured && StringUtils.isBlank(request.getCoverageInMiles())){
				results = Results.getError(ResultsCode.INVALID_COVERAGE_IN_MILES
						.getMessage(), ResultsCode.INVALID_COVERAGE_IN_MILES
						.getCode());
				response.setResults(results);
				errorOccured = true;
			
			}
			else if(!errorOccured && StringUtils.isBlank(request.getIsLicensingRequired())){
				results = Results.getError(ResultsCode.INVALID_IS_LICENSING_IND
						.getMessage(), ResultsCode.INVALID_IS_LICENSING_IND
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			//Validating licensingStates only when isLicensingRequired is true
			else if(!errorOccured && StringUtils.isBlank(request.getLicensingStates()) && request.getIsLicensingRequired().equalsIgnoreCase("true")){
				results = Results.getError(ResultsCode.INVALID_LICENSING_STATE
						.getMessage(), ResultsCode.INVALID_LICENSING_STATE
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
		}

		return response;
	}	
	private boolean validateEmailId( String email) {
		String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
        boolean emailInd=false;
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(email);
		if(m.find()) {
			emailInd = true;
		}
		return emailInd;
	}
	
	public boolean validateAvailableDaysOfWeek(String availableDaysOfWeek)
	{
		boolean availableDaysInd=true;
		String[] passedDaysOfWeekList=availableDaysOfWeek.split(",");
		if(passedDaysOfWeekList.length==0)
		{
			availableDaysInd=false;
		}
		return availableDaysInd;
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
	private LeadProfileCreationRequest replaceSpecialChar(LeadProfileCreationRequest request){
				
		try
		{	
			logger.info("in side try block");
			String providerFirmId = request.getProviderFirmId();
			providerFirmId = replaceSpecialHtmlCharacters(providerFirmId);
			request.setProviderFirmId(providerFirmId);
			
			String leadSmsNo = request.getLeadSmsNo();
			leadSmsNo = replaceSpecialHtmlCharacters(leadSmsNo);
			request.setLeadSmsNo(leadSmsNo);
			
			String leadEmailId = request.getLeadEmailId();
			leadEmailId = replaceSpecialHtmlCharacters(leadEmailId);
			request.setLeadEmailId(leadEmailId);	
			
			String leadPhoneNo = request.getLeadPhoneNo();
			leadPhoneNo = replaceSpecialHtmlCharacters(leadPhoneNo);
			request.setLeadPhoneNo(leadPhoneNo);			
			
			String leadDailyLimit = request.getLeadDailyLimit();
			leadDailyLimit = replaceSpecialHtmlCharacters(leadDailyLimit);
			request.setLeadDailyLimit(leadDailyLimit);
				
			String leadMonthlyLimit = request.getLeadMonthlyLimit();
			leadMonthlyLimit = replaceSpecialHtmlCharacters(leadMonthlyLimit);
			request.setLeadMonthlyLimit(leadMonthlyLimit);
		
			String avgDaysOfWeek = request.getAvailableDaysOfWeek();
			avgDaysOfWeek = replaceSpecialHtmlCharacters(avgDaysOfWeek);
			request.setAvailableDaysOfWeek(avgDaysOfWeek);
		
			String urgencyServices = (request.getUrgencyOfService());
			urgencyServices = replaceSpecialHtmlCharacters(urgencyServices);
			request.setUrgencyOfService(urgencyServices);
		
			String leadPackageID = (request.getLeadPackageID());
			leadPackageID = replaceSpecialHtmlCharacters(leadPackageID);
			request.setLeadPackageID(leadPackageID);
			
			Boolean interestedInSHSLeads = (request.isInterestedInSHSLeads());
			request.setInterestedInSHSLeads(interestedInSHSLeads);
			
			String residentialOrCommercial = request.getLocationType();
			residentialOrCommercial = replaceSpecialHtmlCharacters(residentialOrCommercial);
			request.setLocationType(residentialOrCommercial);
			
			String skill = request.getSkill();
			skill = replaceSpecialHtmlCharacters(skill);
			request.setSkill(skill);

			String coverageInMiles = request.getCoverageInMiles();
			coverageInMiles = replaceSpecialHtmlCharacters(coverageInMiles);
			request.setCoverageInMiles(coverageInMiles);
			
			String isLicensingRequired = request.getIsLicensingRequired();
			isLicensingRequired = replaceSpecialHtmlCharacters(isLicensingRequired);
			request.setIsLicensingRequired(isLicensingRequired);
			
			String licensingStates = request.getLicensingStates();
			licensingStates = replaceSpecialHtmlCharacters(licensingStates);
			request.setLicensingStates(licensingStates);
			
			String isMultipleLocation = request.getIsMultipleLocation();
			isMultipleLocation = replaceSpecialHtmlCharacters(isMultipleLocation);
			request.setIsMultipleLocation(isMultipleLocation);
	
		}
		catch (Exception a_Ex) {
			logger.info(
					"--------- Exception inside specialChar validate  ---");		}
		return request;
	}

	public LeadProfileCreationService() {

		this.errorOccured = false;
	}

	public ILeadProfileBO getLeadProfileBO() {
		return leadProfileBO;
	}

	public void setLeadProfileBO(ILeadProfileBO leadProfileBO) {
		this.leadProfileBO = leadProfileBO;
	}



}
