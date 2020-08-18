/**
 * 
 */
package com.newco.marketplace.api.services.leadsmanagement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.lead.LeadOFMapper;
import com.newco.marketplace.api.utils.mappers.leadsmanagement.LeadManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Contact;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmIdPrice;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmIdPriceConvertor;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmIds;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSPostRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSPostResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSProvider;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ProviderFirms;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoRequest;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadLoggingVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadMatchingProVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadStatisticsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.newco.marketplace.leadoutboundnotification.service.ILeadOutBoundNotificationService;
import com.newco.marketplace.util.LocationUtils;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.vo.LeadResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Infosys
 * 
 */
public class PostLeadService extends BaseService {

	private static Logger logger = Logger.getLogger(PostLeadService.class);
	private LeadProcessingBO leadProcessingBO;
	private LeadManagementMapper leadManagementMapper;
	private LeadManagementValidator leadManagementValidator;
	private LeadOFMapper leadOFMapper;
	private OFHelper ofHelper;
	private String lmsUrl;
	private ILeadOutBoundNotificationService leadOutBoundNotificationService;
	
	public PostLeadService() {
		super(PublicAPIConstant.LEAD_REQUEST_XSD,
				PublicAPIConstant.FETCH_PROVIDER_FIRM_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.FETCH_PROVIDER_FIRM_RESPONSE_SCHEMALOCATION,
				LeadRequest.class, FetchProviderFirmResponse.class);
	}


	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		long start = System.currentTimeMillis();
		logger.info("Entering PostLeadService.execute() ");
		LeadRequest leadRequest = (LeadRequest) apiVO.getRequestFromPostPut();
		
		LMSPostRequest postRequest = null;
		LMSPostResponse lmsPostResponse = null;
		SLLeadVO slLeadVO = new SLLeadVO();
		// Invoke Validation Service to validate the request
		try {
			slLeadVO=leadProcessingBO.getLead(leadRequest.getLeadId());
			leadRequest = leadManagementValidator.validate(slLeadVO,leadRequest);
			if (ResultsCode.SUCCESS != leadRequest.getValidationCode()) {
				return createErrorResponse(leadRequest.getValidationCode().getMessage(),
					leadRequest.getValidationCode().getCode());
					}
			
			if(null!=slLeadVO && slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.NON_LAUNCH_MARKET_LEAD_STATUS) ){
				leadRequest.setNonLaunchZip(true);			
			}else if(null!=slLeadVO && !slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_UNMATCHED)){
				return createErrorResponse(ResultsCode.INVALID_LEAD_STATUS_FOR_POST
						.getMessage(), ResultsCode.INVALID_LEAD_STATUS_FOR_POST.getCode());
			}
		} catch (Exception e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		//Validate zipCode and State and return error response if it fails.
		String state = leadRequest.getCustContact().getContact().getState();
		String zip = leadRequest.getCustContact().getContact().getCustomerZipCode();
		FetchProviderFirmResponse response = new FetchProviderFirmResponse();
		List<LeadMatchingProVO>matchedProviders=new ArrayList<LeadMatchingProVO>();
		int zipCodeValidation=0;
		String slLeadId="";
		if(null != leadRequest 
				&& null != leadRequest.getCustContact() 
				&& null!= leadRequest.getCustContact().getContact()
				&& StringUtils.isNotBlank(state)
				&& StringUtils.isNotBlank(zip)){
			    zipCodeValidation= validateZipAndState(zip,state);
			    switch (zipCodeValidation) {
			       case Constants.LocationConstants.ZIP_NOT_VALID:
			    	   response= createErrorResponse(ResultsCode.INVALID_ZIP_CODE.getMessage(),ResultsCode.INVALID_ZIP_CODE.getCode());
			    	   return response;
			       case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
			    	   response= createErrorResponse(ResultsCode.INVALID_ZIP_STATE.getMessage(),ResultsCode.INVALID_ZIP_STATE.getCode());
			    	   return response;
			       case Constants.LocationConstants.ZIP_VALID:
			        	String timeZone=LocationUtils.getTimeZone(zip);
			        	leadRequest.setServiceTimeZone(timeZone);
			    	   }
			     }
		
		//Code to insert selected provider firm info
		//Code to insert the lead stats
		//Save customer contact info
	
		try{
			SLLeadVO leadInfoVO=new SLLeadVO();
			//Marking this request as No Provider to update only customer informations
						
			if(!leadRequest.isNonLaunchZip()){
				FirmIds  firmIds= leadRequest.getFirmIds();
				if(null==firmIds){
					leadRequest.setNoProvLead(true);
				}
			}
			leadInfoVO=leadProcessingBO.savePostLeadFirmInfo(leadRequest);
			slLeadId=leadInfoVO.getSlLeadId();
			//returning the response if the request is for non launch zip
			if(leadRequest.isNonLaunchZip()){
				response.setResults(Results.getSuccess(NewServiceConstants.NON_LAUNCH_ZIP_SUCCESS));
				response.setLeadId(slLeadId);
				return response;
			}
			
			if(leadRequest.isNoProvLead()){
				response.setResults(Results.getSuccess(NewServiceConstants.NON_LAUNCH_ZIP_SUCCESS));
				response.setLeadId(slLeadId);
				return response;
			}
				leadRequest.setUrgencyOfService(slLeadVO.getUrgencyOfService());
			leadRequest.setLeadSource(slLeadVO.getLeadSource());
			leadRequest.setProjectType(slLeadVO.getPrimaryProject());
			leadRequest.setSkill(slLeadVO.getSkill());
			leadRequest.setLeadCategory(slLeadVO.getLeadCategory());
			leadRequest.setClientProjectType(slLeadVO.getClientProjectType());
		
		}catch(Exception e){
			logger.info("Exception in  saving Request information from postMemberLead in DB"+e.getMessage());
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		} 
		//logging lead price and fetching lead_profile_emails
		List<FirmDetailsVO> firmVOForMail=getLeadPriceAndLmsPartnerIdForFirms(leadRequest);
		if(leadRequest.isInvalidFirmPriceData()){
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		//setTestResponse(leadRequest,response);
		
		// Use the mapper to map or create the LMS Post request
		postRequest = leadManagementMapper
				.mapClientToLMS(leadRequest);

		// Post LMS and get the response
		try {
			long lmsPostStart=System.currentTimeMillis();
			lmsPostResponse = matchSelectLMSProviderFirms(postRequest,leadRequest.getLeadId());
			long lmsPostEnd=System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
	            logger.info("Inside PostLeadService.execute()..>>" +
	            		"Time Taken for lead to post to lms And Getting response >>"+(lmsPostEnd-lmsPostStart));
			}
		} catch (Exception e1) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		if (null != lmsPostResponse) {
			if (PublicAPIConstant.LMSLeadStatus.ERROR.equals(lmsPostResponse
					.getStatus())) {
				logger.error("Error in posting lead from LMS "
						+ lmsPostResponse.getError());
				return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
						.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
			} else if (PublicAPIConstant.LMSLeadStatus.UNMATCHED
					.equals(lmsPostResponse.getStatus())) {
				//response = createErrorResponse(ResultsCode.UNMATCHED.getMessage(), ResultsCode.UNMATCHED.getCode());
				return createErrorResponse(ResultsCode.UNMATCHED.getMessage(), ResultsCode.UNMATCHED.getCode());
			}else{
				leadRequest.getFirmIds().getFirmId().clear();
				List firmIdList = new ArrayList<String>();
				for(LMSProvider provider: lmsPostResponse.getProviders().getLmsProvider()){
					firmIdList.add(provider.getFirmId());
				}
				leadRequest.getFirmIds().setFirmId(firmIdList);
				
				matchedProviders = leadManagementMapper.setPostedproviders(leadRequest,slLeadId);
				LeadStatisticsVO statisticsVO = new LeadStatisticsVO();
				statisticsVO.setSlLeadId(slLeadId);
				statisticsVO.setTypeOfInteraction(PublicAPIConstant.TypeOfInteraction.POST_LEAD_INTERACTION);
				statisticsVO = leadManagementMapper.setLeadStatistics(statisticsVO);
				try{
					leadProcessingBO.saveMatchedProvidersInfo(matchedProviders,statisticsVO,leadRequest);
					//Logging details into lead history
					//setting posted no of firm 
					int firmNo=firmIdList.size();
					//getting security context 					
					SecurityContext securityContext=null;					
					securityContext = getSecurityContextForBuyerAdmin(NewServiceConstants.HOME_SERVICES_BUYER_ID);
					
					String modifiedBy=securityContext.getUsername();
					Integer entityId=securityContext.getVendBuyerResId();
					String createdBy=leadProcessingBO.getBuyerName(NewServiceConstants.HOME_SERVICES_BUYER_ID);
					String comments="";
					if (firmNo == 1) {
						comments = NewServiceConstants.LEAD_POSTED_MATCHED1;
					} else if (firmNo == 2) {
						comments = NewServiceConstants.LEAD_POSTED_MATCHED2;
					} else {
						comments = NewServiceConstants.LEAD_POSTED_MATCHED3;
					}
					LeadLoggingVO leadLoggingVO=new LeadLoggingVO(slLeadId , NewServiceConstants.LEAD_POSTED, 
							NewServiceConstants.OLD_VALUE, NewServiceConstants.NEW_VALUE,comments, 
							createdBy,modifiedBy, NewServiceConstants.ROLE_ID_BUYER,entityId);					
					leadProcessingBO.insertLeadLogging(leadLoggingVO);
					//Updating lead contact info with the reward points awarded.
					if(StringUtils.isNotBlank(leadRequest.getSYWRMemberId()) && !(leadRequest.isNonLaunchZip() || leadRequest.isNoProvLead())){
						  UpdateMembershipInfoRequest updateMembershipInfoRequest = new UpdateMembershipInfoRequest();
						  updateMembershipInfoRequest.setModifiedDate(new Date());
						  updateMembershipInfoRequest.setLeadId(leadRequest.getLeadId());
						  updateMembershipInfoRequest.setMemberShipNumber(leadRequest.getSYWRMemberId());
						  updateMembershipInfoRequest.setPointsRewarded(NewServiceConstants.SWYR_REWARD_POINTS_AWARDED);
						  leadProcessingBO.updateMembershipInfo(updateMembershipInfoRequest);
						  //Lead Logging
						  LeadLoggingVO sWyrLogging =new LeadLoggingVO(updateMembershipInfoRequest.getLeadId(),NewServiceConstants.LEAD_MEMBERSHIP_INFO_UPDATED, 
									NewServiceConstants.OLD_VALUE, NewServiceConstants.NEW_VALUE,NewServiceConstants.SWYR_NUMBER_UPDATED+" and "+ NewServiceConstants.SWYR_POINTS_AWARDED, 
									createdBy,modifiedBy, NewServiceConstants.ROLE_ID_BUYER,entityId);					
							leadProcessingBO.insertLeadLogging(sWyrLogging);
						}
				}catch (Exception e) {
					logger.info("Exception in  saving matched providers  info in postMemberLead in DB"+e.getMessage());
				}
				
			}
		}
		setResponse(leadRequest,response,lmsPostResponse.getStatus());
		//POC for JBPM implementation for LMS status of 'Matched'
		if(null!=lmsPostResponse.getStatus() && lmsPostResponse.getStatus().equalsIgnoreCase(PublicAPIConstant.LMSLeadStatus.MATCHED)){
			LeadResponse leadResponse = processLeadOFRequest(slLeadId);
					LeadStatisticsVO statisticsVO = new LeadStatisticsVO();
		statisticsVO.setSlLeadId(slLeadId);
		statisticsVO.setTypeOfInteraction(PublicAPIConstant.TypeOfInteraction.POST_LEAD_INTERACTION);
		statisticsVO = leadManagementMapper.setLeadStatistics(statisticsVO);
		try{
			leadProcessingBO.saveLeadStats(statisticsVO);
		}catch(Exception e){
			logger.info("Exception in logging lead Statistics"+e.getMessage());
		}
			
			try{
		    	leadOutBoundNotificationService.insertLeadOutBoundDetails(leadResponse.getLeadId());
		    }
		    catch(BusinessServiceException businessServiceException){
		    	logger.info("Exception occurred in insertLeadOutBoundDetails: "+businessServiceException.getMessage());
		    }
		}
		long end = System.currentTimeMillis();
    	if (logger.isInfoEnabled()) {
            logger.info("Inside PostLeadService.execute()..>>" +
            		"Time Taken for lead to post from service class >>"+(end-start));
		}
        return response;
	}
	
	private LMSPostResponse matchSelectLMSProviderFirms(
			LMSPostRequest postRequest, String leadId) throws BusinessServiceException {
		//Logging request date and time into lead statistics table
		Date todayRequest = new Date();
		LeadStatisticsVO statisticsVOrequest = new LeadStatisticsVO(leadId,todayRequest,null,
				PublicAPIConstant.DataFlowDirection.DATA_FLOW_SL_TO_LMS,
				PublicAPIConstant.TypeOfInteraction.POST_LEAD_INTERACTION,
				todayRequest, todayRequest, "SL", "SL");
		leadProcessingBO.saveLeadStats(statisticsVOrequest);
		URL url = null;
		try {
			url = new URL(lmsUrl);
		} catch (MalformedURLException e) {
			logger.error("Exception in setting the URL " + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}
		HttpURLConnection httpURLConnection = null;
		LMSPostResponse lmsPostResponse = null;
		try {

			httpURLConnection = (HttpURLConnection) url.openConnection();
			// I provide the input
			httpURLConnection.setDoInput(true);

			httpURLConnection.setConnectTimeout(PublicAPIConstant.API_TIME_OUT);
			httpURLConnection.setReadTimeout(PublicAPIConstant.API_TIME_OUT);

			// Requires output
			httpURLConnection.setDoOutput(true);

			// No caching
			httpURLConnection.setUseCaches(false);

			httpURLConnection.setRequestProperty("Content-type",
					"application/xml");

			httpURLConnection.setRequestMethod("POST");
			DataOutputStream dataOutputStream = null;
			DataInputStream dataInputStream = null;

			httpURLConnection.connect();

			String request = serializeRequest(postRequest, LMSPostRequest.class);
			
			logger.info("LMSPostRequest: " + request);
			dataOutputStream = new DataOutputStream(httpURLConnection
					.getOutputStream());
			dataOutputStream.writeBytes(request);
			dataOutputStream.flush();
			dataOutputStream.close();

			String postResponse;
			StringBuffer responseBuffer = new StringBuffer();

			dataInputStream = new DataInputStream(httpURLConnection
					.getInputStream());
			while (null != ((postResponse = dataInputStream.readLine()))) {
				responseBuffer.append(postResponse);
				logger.info("response is ::" + postResponse);
			}
			dataInputStream.close();
            //Logging response date and details into lead Statistics table
			Date todayresponse = new Date();
			LeadStatisticsVO statisticsVOResponse = new LeadStatisticsVO(leadId,todayRequest,todayresponse,
					PublicAPIConstant.DataFlowDirection.DATA_FLOW_LMS_TO_SL,
					PublicAPIConstant.TypeOfInteraction.POST_LEAD_INTERACTION,
					todayresponse, todayresponse, "SL", "SL");
			leadProcessingBO.saveLeadStats(statisticsVOResponse);

			if (200 != httpURLConnection.getResponseCode()) {
				logger
						.error("Exception in getting response from LMS. Response code is: "
								+ httpURLConnection.getResponseCode());
				logger.error("Response message is: "
						+ httpURLConnection.getResponseMessage());
				throw new BusinessServiceException("Response code is:"
						+ httpURLConnection.getResponseCode());
			}

			logger.info("response is ::" + responseBuffer.toString());
			String responseString=responseBuffer.toString();
			responseString=removeSpecialCharacters(responseString);
			lmsPostResponse = (LMSPostResponse) deserializeResponse(
					responseString, LMSPostResponse.class);

		} catch (IOException e) {
			logger
					.error("Exception in connecting to LMS while posting matching provider firms "
							+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		} catch (Exception e) {
			logger
					.error("Exception in posting matching provider firms from LMS "
							+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return lmsPostResponse;
	}

	private String removeSpecialCharacters(String responseString) {
		if(StringUtils.isNotBlank(responseString)){
			responseString=responseString.replaceAll("&","&amp;");
		}
		return responseString;
	}

	private List<FirmDetailsVO>  getLeadPriceAndLmsPartnerIdForFirms(LeadRequest leadRequest){
		Map<String,Double> firmIdPriceMap = new HashMap<String, Double>();
		leadRequest.setLMSFirmIdPriceMap(firmIdPriceMap);
		List<FirmDetailsVO> firmDetailsVO=null;
		List<FirmIdPrice> firmIdPriceList = new ArrayList<FirmIdPrice>();
		
		List<String> firmIds = leadRequest.getFirmIds().getFirmId();
		//default value is COMPETITIVE
		String leadPricingType = "COMPETITIVE";
		if(!firmIds.isEmpty()){
			int size=firmIds.size();
			if(1==size){
				leadPricingType="EXCLUSIVE";
			}
		}
		leadRequest.setLeadPricingType(leadPricingType);
		try{
			 firmDetailsVO= leadProcessingBO.getLeadPriceAndLmsPartnerIdForFirms(leadRequest);
			if(null!=firmDetailsVO){
				if(firmIds.size()>firmDetailsVO.size()){
					leadRequest.setInvalidFirmPriceData(true);
				}else{
					for(FirmDetailsVO firmVO : firmDetailsVO){
						FirmIdPrice firmIdPrice = new FirmIdPrice();
						firmIdPrice.setSlFirmId(firmVO.getFirmId());
						if(null!=firmVO.getLeadPrice()){
							logger.info("Lead " +leadPricingType+" price for firm: " +firmVO.getFirmId()+ " is: "+ firmVO.getLeadPrice());
							String price = new Double(firmVO.getLeadPrice()).toString();
							firmIdPrice.setPrice(price);
							//leadRequest.getLMSFirmIdPriceMap().put(firmVO.getFirmId(), firmVO.getLeadPrice());						
						}else{
							leadRequest.setInvalidFirmPriceData(true);
						}
						
						if(null!=firmVO.getLmsPartnerId()){
							firmIdPrice.setId(firmVO.getLmsPartnerId());
						}else{
							leadRequest.setInvalidFirmPriceData(true);
						}
						firmIdPriceList.add(firmIdPrice);
					}
					leadRequest.setFirmIdPriceList(firmIdPriceList);
				}				
			}else{
				leadRequest.setInvalidFirmPriceData(true);
			}
		}catch(Exception ex){
			logger.info("Exception occured in getLeadPriceAndLmsPartnerIdForFirms method of PostLeadService: " +ex.getMessage());
		}
		return firmDetailsVO;
	}
	
	private void setResponse(LeadRequest leadRequest, FetchProviderFirmResponse response, String lmsResponseStatus) {
		
		response.setLeadId(leadRequest.getLeadId());
		List<FirmDetails> firmdetailsList = new ArrayList<FirmDetails>();
        List<Integer>firmIdList=new ArrayList<Integer>();
        Map firmInfoMap=null;
        if(null != leadRequest.getFirmIds()&& null != leadRequest.getFirmIds().getFirmId() && !leadRequest.getFirmIds().getFirmId().isEmpty()){
        	firmInfoMap=getFirmprofileDetails(leadRequest);
        	
    		for(String firmID:leadRequest.getFirmIds().getFirmId()){
    			FirmDetailsVO firmInfo=(FirmDetailsVO) firmInfoMap.get(firmID);
    			FirmDetails firmdetails = new FirmDetails();
    			Contact contact = new Contact();
    			if(null!= firmInfo){
    			    contact.setEmail(firmInfo.getEmail());
    			    if(StringUtils.isNotBlank(firmInfo.getPhoneNo())){
    			       contact.setPhone(SOPDFUtils.removeHyphenFromPhoneNumber(firmInfo.getPhoneNo()));
    			    }
    				contact.setAddress(firmInfo.getAddress());
    				contact.setCity(firmInfo.getCity());
    				contact.setState(firmInfo.getState());
    				contact.setCustomerZipCode(firmInfo.getZip());
    				firmdetails.setContact(contact);
    				firmdetails.setFirmId(Integer.parseInt(firmID));
    				firmdetails.setFirmName(firmInfo.getFirmName());
    				firmdetails.setFirmOwner(firmInfo.getFirmOwner());
    				firmdetails.setFirmRating(firmInfo.getRating());
    				firmdetails.setProviderFirmRank(firmInfo.getProviderFirmRank());
    				
    				if(StringUtils.isNotBlank(leadRequest.getSYWRMemberId())){
    			       firmdetails.setPointsAwarded(NewServiceConstants.SWYR_REWARD_POINTS);
    				}
    				firmdetailsList.add(firmdetails);
    			}
    			
    		}
		}

		ProviderFirms providerFirms = new ProviderFirms();
		providerFirms.setFirmDetailsList(firmdetailsList);
		response.setResults(Results.getSuccess());
		response.setFirmDetailsList(providerFirms);
		//Currently writing code to send mails to selected providers.Need to be implemented using JPDL flow
		leadProcessingBO.sendConfirmationMailToCustomer(leadRequest,firmdetailsList,lmsResponseStatus);
		//to send mail to providers
		//leadProcessingBO.sendConfirmationMailToProvider(leadRequest,firmdetailsList,lmsResponseStatus);
	}

	private Map getFirmprofileDetails(LeadRequest leadRequest) {
		Map firmDeatilsMap=null;
		try{
			firmDeatilsMap=leadProcessingBO.getFirmDetailsPost(leadRequest);
		}
		catch (Exception e) {
			logger.info("Exception in getting firmProfile Details"+ e.getMessage());
		}
		return firmDeatilsMap;
	}
	private String getYearsInBusiness(Date busStartDate)
	{
		long numMilBusStart;
		long numMilToday;
		long dateDiff = 0;
		float numYears;
		String numYearsStr;
		Date todayDate = new Date();

		numMilBusStart = busStartDate.getTime();
		numMilToday = todayDate.getTime();

		dateDiff = numMilToday - numMilBusStart;

		numYears = (float) dateDiff / 1000 / 60 / 60 / 24 / 365;
		numYearsStr = String.valueOf(numYears);

		return numYearsStr;
	}
	/**
	 * This method is for validating the zipCode and State
	 * 
	 * @param locationType String
	 * @param zip String
	 * @param state String
	 */
	private int validateZipAndState(String zip,String state) {
		int zipValidation = LocationUtils.checkIfZipAndStateValid(zip,state);
		switch (zipValidation) {
		case Constants.LocationConstants.ZIP_NOT_VALID:
			return Constants.LocationConstants.ZIP_NOT_VALID;
		case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
			return Constants.LocationConstants.ZIP_STATE_NO_MATCH;
			}
		return zipValidation;
	}
	private FetchProviderFirmResponse createErrorResponse(String message, String code){
		FetchProviderFirmResponse createResponse = new FetchProviderFirmResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}
	
	/* Method which accepts ServiceLive lead id and executes JBPM transition
	 * 
	 */
	private LeadResponse processLeadOFRequest(String leadId) {
		LeadResponse response = new LeadResponse();		
		//creating security context
		Integer buyerId = 7000;
		SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
		try{
			OrderFulfillmentLeadRequest leadRequest = leadOFMapper.createFetchProvidersLeadRequest(leadId, securityContext);
			response = ofHelper.runLeadFulfillmentProcess(leadId, SignalType.MATCHED_POST, leadRequest);
		}catch(Exception e){
			logger.info("Exception occurred in processLeadOFRequest of PostLeadService: "+e.getMessage());
		}
		
		return response;
	}
	
	//We have customised the serialize request to handle xstream '_' as 
	//it is an escape character in xstream
	private String serializeRequest(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		//String requestXml  = xstream.toXML(request).toString();
		return xstream.toXML(request).toString().replaceAll("__", "_");
	}

	private XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream
				.registerConverter(new DateConverter("yyyy-MM-dd",
						new String[0]));
		xstream.registerConverter(new FirmIdPriceConvertor());
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}

	@SuppressWarnings("rawtypes")
	private Object deserializeResponse(String objectXml, Class<?> clazz) {
		//return this.<IAPIResponse>deserializeRequest(objectXml,c);
		Object obj = new Object();
		try {
			XStream xstream = getXstream(clazz);
			obj = (Object) xstream.fromXML(objectXml);
			logger.info("Exiting deserializeResponse()");

		} catch (Exception e) {
			logger.error(e);
		}
		return obj;
	}
	
	public LeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}
	public LeadManagementMapper getLeadManagementMapper() {
		return leadManagementMapper;
	}

	public void setLeadManagementMapper(LeadManagementMapper leadManagementMapper) {
		this.leadManagementMapper = leadManagementMapper;
	}

	public LeadManagementValidator getLeadManagementValidator() {
		return leadManagementValidator;
	}

	public void setLeadManagementValidator(
			LeadManagementValidator leadManagementValidator) {
		this.leadManagementValidator = leadManagementValidator;
	}

	public LeadOFMapper getLeadOFMapper() {
		return leadOFMapper;
	}

	public void setLeadOFMapper(LeadOFMapper leadOFMapper) {
		this.leadOFMapper = leadOFMapper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public String getLmsUrl() {
		return lmsUrl;
	}

	public void setLmsUrl(String lmsUrl) {
		this.lmsUrl = lmsUrl;
	}
	
	public ILeadOutBoundNotificationService getLeadOutBoundNotificationService() {
		return leadOutBoundNotificationService;
	}

	public void setLeadOutBoundNotificationService(
			ILeadOutBoundNotificationService leadOutBoundNotificationService) {
		this.leadOutBoundNotificationService = leadOutBoundNotificationService;
	}
}
