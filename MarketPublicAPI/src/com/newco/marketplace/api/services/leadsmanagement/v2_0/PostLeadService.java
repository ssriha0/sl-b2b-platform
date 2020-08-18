/**
 * 
 */
package com.newco.marketplace.api.services.leadsmanagement.v2_0;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.leadsmanagement.LeadManagementValidator;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.lead.LeadOFMapper;
import com.newco.marketplace.api.utils.mappers.leadsmanagement.LeadManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmIdPriceConvertor;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.Contact;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmIds;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.ProviderFirms;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
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

@Namespace("http://www.servicelive.com/namespaces/postLead")
@APIRequestClass(LeadRequest.class)
@APIResponseClass(FetchProviderFirmResponse.class)
public class PostLeadService extends BaseService {

	private static Logger logger = Logger.getLogger(PostLeadService.class);
	private ILeadProcessingBO leadProcessingBO;
	private LeadManagementMapper leadManagementMapper;
	private LeadManagementValidator leadManagementValidator;
	private LeadOFMapper leadOFMapper;
	private OFHelper ofHelper;
	private String lmsUrl;
	private ILeadOutBoundNotificationService leadOutBoundNotificationService;
	
	public PostLeadService() {
		super();
	}


	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		LeadRequest leadRequest = (LeadRequest) apiVO.getRequestFromPostPut();
		SLLeadVO slLeadVO = new SLLeadVO();
		SLLeadVO leadInfoVO = new SLLeadVO();
		FetchProviderFirmResponse response = new FetchProviderFirmResponse();
		List<LeadMatchingProVO>matchedProviders=new ArrayList<LeadMatchingProVO>();
		ArrayList<Object> argumentList = new ArrayList<Object>();
		SecurityContext securityContext = null;
		LeadLoggingVO leadLoggingVO = null;
		FirmIds  firmIds = null;
		int zipCodeValidation=0;
		String state = "";
		String zip = "";
		String slLeadId = "";
		String modifiedBy = "";
		String createdBy = "";
		String comments = "";
		Integer entityId;
		int firmNo;
		
		
		// Invoke Validation Service to validate the request
		try {
			// Fetching lead details for validation
			slLeadVO=leadProcessingBO.getLead(leadRequest.getLeadId());
			leadRequest = leadManagementValidator.validatePostLead(slLeadVO,leadRequest);
			if (ResultsCode.SUCCESS != leadRequest.getValidationCode()) {
				return createErrorResponse(leadRequest.getValidationCode().getMessage(),
					leadRequest.getValidationCode().getCode());
			}
			leadRequest.setNonLaunchZip(false);
			if(null != slLeadVO && slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.NON_LAUNCH_MARKET_LEAD_STATUS)){
				leadRequest.setNonLaunchZip(true);			
			} else if(null != slLeadVO && !slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_UNMATCHED)
					&& !slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_MATCHED)){
				return createErrorResponse(ResultsCode.INVALID_LEAD_STATUS_FOR_POST_V2
						.getMessage(), ResultsCode.INVALID_LEAD_STATUS_FOR_POST_V2.getCode());
			}
		} catch (Exception e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		// Validate zipCode and State and return error response if it fails.
		state = leadRequest.getCustContact().getContact().getState();
		zip = leadRequest.getCustContact().getContact().getCustomerZipCode();
		
		if(null != leadRequest && null != leadRequest.getCustContact() && null!= leadRequest.getCustContact().getContact()
				&& StringUtils.isNotBlank(state) && StringUtils.isNotBlank(zip)){
			zipCodeValidation = validateZipAndState(zip,state);
			switch (zipCodeValidation) {
				case Constants.LocationConstants.ZIP_NOT_VALID : 
					response= createErrorResponse(ResultsCode.INVALID_ZIP_CODE.getMessage(),ResultsCode.INVALID_ZIP_CODE.getCode());
			    	return response;
				case Constants.LocationConstants.ZIP_STATE_NO_MATCH :
			    	response= createErrorResponse(ResultsCode.INVALID_ZIP_STATE.getMessage(),ResultsCode.INVALID_ZIP_STATE.getCode());
			    	return response;
			    case Constants.LocationConstants.ZIP_VALID :
			        String timeZone=LocationUtils.getTimeZone(zip);
			        leadRequest.setServiceTimeZone(timeZone);
			}
		}
	
		try{
			// Marking this request as No Provider to update only customer informations if firm ids are not provided in the request.		
			if(!leadRequest.isNonLaunchZip()){
				firmIds = leadRequest.getFirmIds();
				leadRequest.setNoProvLead(false);
				if(null == firmIds){
					leadRequest.setNoProvLead(true);
				}else{
					Map<String, String> validateResponse = leadProcessingBO.validateLeadPostFirmIds(slLeadVO.getSlLeadId(), firmIds);
					if(null != validateResponse){
						argumentList.add(validateResponse.get("invalidFirms"));
						if(NewServiceConstants.INVALID_FIRM_ID.equals(validateResponse.get("validationType"))){
							response = createErrorResponse(ResultsCode.FIRM_INVALID.getMessage(argumentList),ResultsCode.FIRM_INVALID.getCode());
					    	return response;
						}else if(NewServiceConstants.LEAD_NOT_POSTED_TO_FIRM.equals(validateResponse.get("validationType"))){
							response = createErrorResponse(ResultsCode.FIRM_NOT_POSTED.getMessage(argumentList),ResultsCode.FIRM_NOT_POSTED.getCode());
					    	return response;
						}else if(NewServiceConstants.LEAD_ALREADY_MATCHED_TO_FIRM.equals(validateResponse.get("validationType"))){
							response = createErrorResponse(ResultsCode.FIRM_ALREADY_MATCHED.getMessage(argumentList),ResultsCode.FIRM_ALREADY_MATCHED.getCode());
					    	return response;
						}
					}
				}
			}
			
			// Code to insert the lead statistics
			// Save customer contact info
			leadInfoVO = leadProcessingBO.savePostLeadFirmInfo(leadRequest, convertReqObjectToXMLString(leadRequest, LeadRequest.class));
			slLeadId = leadInfoVO.getSlLeadId();
			
			leadRequest.setUrgencyOfService(slLeadVO.getUrgencyOfService());
			leadRequest.setLeadSource(slLeadVO.getLeadSource());
			leadRequest.setProjectType(slLeadVO.getPrimaryProject());
			leadRequest.setSkill(slLeadVO.getSkill());
			leadRequest.setLeadCategory(slLeadVO.getLeadCategory());
			leadRequest.setClientProjectType(slLeadVO.getClientProjectType());
			
			// Returning the response if the request is for non launch zip
			if(leadRequest.isNonLaunchZip()){
				response.setResults(Results.getSuccess(NewServiceConstants.NON_LAUNCH_ZIP_SUCCESS));
				response.setLeadId(slLeadId);
				//SL-20893 Send customer email for unmatched and out of area leads
				if(null != slLeadVO && (slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_UNMATCHED)
						|| slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.NON_LAUNCH_MARKET_LEAD_STATUS))){
					leadProcessingBO.sendConfirmationMailToCustomerForUnmatchedAndOutOfArea(leadRequest);
				}
				saveLeadStatistics(slLeadId, response);
				return response;
			}
			
			// Returning the response if the request has no firms
			if(leadRequest.isNoProvLead()){
				response.setResults(Results.getSuccess(NewServiceConstants.NON_LAUNCH_ZIP_SUCCESS));
				response.setLeadId(slLeadId);
				//SL-20893 Send customer email for unmatched and out of area leads
				if(null != slLeadVO && (slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_UNMATCHED)
						|| slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.NON_LAUNCH_MARKET_LEAD_STATUS))){
					leadProcessingBO.sendConfirmationMailToCustomerForUnmatchedAndOutOfArea(leadRequest);
				}
				saveLeadStatistics(slLeadId, response);
				return response;
			}
		
		}catch(Exception e){
			logger.info("Exception in  saving Request information from postMemberLead in DB"+e.getMessage());
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		matchedProviders = leadManagementMapper.mapPostedProviders(leadRequest,slLeadId);	
			  
		try{
			// Code to insert selected provider firm info
			leadProcessingBO.saveMatchedProvidersInfo(matchedProviders,leadRequest);		
				
			// Logging details into lead history
			// setting posted no of firm 
			firmNo = leadRequest.getFirmIds().getFirmId().size();
			// getting security context
			securityContext = getSecurityContextForBuyerAdmin(NewServiceConstants.HOME_SERVICES_BUYER_ID);	
			modifiedBy = securityContext.getUsername();
			entityId = securityContext.getVendBuyerResId();
			createdBy = leadProcessingBO.getBuyerName(NewServiceConstants.HOME_SERVICES_BUYER_ID);	
			comments = NewServiceConstants.LEAD_POSTED_MATCHED4 + firmNo + NewServiceConstants.LEAD_POSTED_MATCHED5;
			
			leadLoggingVO=new LeadLoggingVO(slLeadId , NewServiceConstants.LEAD_POSTED, 
					NewServiceConstants.OLD_VALUE, NewServiceConstants.NEW_VALUE,comments, 
					createdBy,modifiedBy, NewServiceConstants.ROLE_ID_BUYER,entityId);
					
			leadProcessingBO.insertLeadLogging(leadLoggingVO);
										
			//Updating lead contact info with the reward points awarded.
			if(StringUtils.isNotBlank(leadRequest.getSYWRMemberId()) && !(leadRequest.isNonLaunchZip() || leadRequest.isNoProvLead()) && StringUtils.isBlank(slLeadVO.getSwyrRewardPoints())){
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
		
		setResponse(leadRequest,response, slLeadVO.getLeadWfStatus());
		//POC for JBPM implementation for LMS status of 'Matched'
		if(null != slLeadVO && slLeadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_UNMATCHED)){
			processLeadOFRequest(slLeadId);
		}
		
		saveLeadStatistics(slLeadId, response);
		
		try{
	    	leadOutBoundNotificationService.insertLeadOutBoundDetails(leadRequest.getLeadId());
	    }
	    catch(BusinessServiceException businessServiceException){
	    	logger.info("Exception occurred in insertLeadOutBoundDetails: "+businessServiceException.getMessage());
	    }
        return response;
	}
	
	private void saveLeadStatistics(String slLeadId, FetchProviderFirmResponse response){
		LeadStatisticsVO statisticsVO = new LeadStatisticsVO();
		statisticsVO.setSlLeadId(slLeadId);
		statisticsVO.setTypeOfInteraction(PublicAPIConstant.TypeOfInteraction.POST_LEAD_INTERACTION);
		statisticsVO.setReqResXML(convertReqObjectToXMLString(response, FetchProviderFirmResponse.class));
		statisticsVO = leadManagementMapper.setLeadStatistics(statisticsVO);
		try{
			leadProcessingBO.saveLeadStats(statisticsVO);
		}catch(Exception e){
			logger.info("Exception in logging lead Statistics"+e.getMessage());
		}
	}
	
	
	private String convertReqObjectToXMLString(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return xstream.toXML(request).toString();
	}
	
	private void setResponse(LeadRequest leadRequest, FetchProviderFirmResponse response, String leadStatus) {
		
		response.setLeadId(leadRequest.getLeadId());
		List<FirmDetails> firmdetailsList = new ArrayList<FirmDetails>();
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
		leadProcessingBO.sendConfirmationMailToCustomer(leadRequest,firmdetailsList,leadStatus);
		//to send mail to providers
		leadProcessingBO.sendConfirmationMailToProvider(leadRequest,firmdetailsList,leadStatus);
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
	
	/**
	 * This method is for validating the zipCode and State
	 * 
	 * @param locationType String
	 * @param zip String
	 * @param state String
	 */
	private int validateZipAndState(String zip,String state) {
		int zipValidation = LocationUtils.checkIfZipAndStateValid(zip,state);
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
	
	public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
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
