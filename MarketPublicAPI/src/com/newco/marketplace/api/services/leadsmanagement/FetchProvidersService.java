/**
 * 
 */
package com.newco.marketplace.api.services.leadsmanagement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.exceptions.ValidationException;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.lead.LeadOFMapper;
import com.newco.marketplace.api.utils.mappers.leadsmanagement.LeadManagementMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmReview;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmReviews;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSPingRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSPingResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSProvider;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ProviderFirms;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CriteraDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadLoggingVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadPostedProVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadStatisticsVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.LeadResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * @author Infosys
 * 
 */
public class FetchProvidersService extends BaseService {

	private static Logger logger = Logger
			.getLogger(FetchProvidersService.class);
	private ILeadProcessingBO leadProcessingBO;
	private ILookupBO lookupBO;
	private LeadManagementMapper leadManagementMapper;
	private LeadManagementValidator leadManagementValidator;
	private LeadOFMapper leadOFMapper;
	private OFHelper ofHelper;
	private String lmsUrl;

	public FetchProvidersService() {
		super(PublicAPIConstant.FETCH_PROVIDER_FIRM_REQUEST_XSD,
				PublicAPIConstant.FETCH_PROVIDER_FIRM_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.FETCH_PROVIDER_FIRM_RESPONSE_SCHEMALOCATION,
				FetchProviderFirmRequest.class, FetchProviderFirmResponse.class);
	}

	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering FetchProvidersService.execute() ");
		long start = System.currentTimeMillis();
		FetchProviderFirmRequest fetchProviderFirmRequest = (FetchProviderFirmRequest) apiVO
				.getRequestFromPostPut();
		LMSPingRequest pingRequest = null;
		LMSPingResponse lmsPingResponse = null;
		LocationVO locationVO = null;
		List<Integer> firmIds = new ArrayList<Integer>();
		Results results = Results.getSuccess();

		// Need to log the request received
		String slLeadId = "";
		String slLeadIdFromOF="";
		List<LeadPostedProVO> postedProviders = new ArrayList<LeadPostedProVO>();
		FetchProviderFirmResponse response = new FetchProviderFirmResponse();

		// Invoke Validation Service to validate the request
		try {
			long valStart = System.currentTimeMillis();
			fetchProviderFirmRequest = leadManagementValidator
					.validate(fetchProviderFirmRequest);
			long valEnd = System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
	            logger.info("Inside Service class..>>Validation  Time Taken>>"+(valEnd-valStart));
			}
			if (ResultsCode.SUCCESS != fetchProviderFirmRequest
					.getValidationCode()) {
				response = createErrorResponse(fetchProviderFirmRequest
						.getValidationCode().getMessage(),
						fetchProviderFirmRequest.getValidationCode().getCode());
				if (ResultsCode.INVALID_ZIP_CODES == fetchProviderFirmRequest
						.getValidationCode()) {
					//Method to persist LeadObject in SL in case of non launch market.
					//JBPM workflow is not created as there won't be much transitions.
					slLeadId = leadProcessingBO
							.saveLeadInfoforNonLaunchMarket(fetchProviderFirmRequest);	
                    //Logging details into lead history
					insertIntoHistory(slLeadId,NewServiceConstants.INVALID_ZIP);
					
					// Setting lead id in case were the zip provided is outside
					// launch market					
					response.setLeadId(slLeadId);					
				}else if(ResultsCode.INVALID_PROJECT == fetchProviderFirmRequest.getValidationCode()){
					return createErrorResponse(ResultsCode.INVALID_PROJECT
							.getMessage(), ResultsCode.INVALID_PROJECT.getCode());
				}
				return response;
			}
		} catch (Exception e) {
			return createErrorResponse(
					ResultsCode.UNABLE_TO_PROCESS.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS.getCode());
		}

		Integer slNodeId = fetchProviderFirmRequest.getSlNodeId();
		String lmsProject = fetchProviderFirmRequest.getLmsProjectDescription();
		String leadCategory = fetchProviderFirmRequest.getLeadCategory();
		fetchProviderFirmRequest = (FetchProviderFirmRequest) apiVO
				.getRequestFromPostPut();

		fetchProviderFirmRequest.setSlNodeId(slNodeId);
		fetchProviderFirmRequest.setLmsProjectDescription(lmsProject);
		fetchProviderFirmRequest.setLeadCategory(leadCategory);

		// Fetching locationVO for given Zip to calculate Distance.
		try {
			locationVO = lookupBO.checkIFZipExists(fetchProviderFirmRequest
					.getCustomerZipCode());
			fetchProviderFirmRequest.setLocationVO(locationVO);
		} catch (Exception e) {
			return createErrorResponse(
					ResultsCode.UNABLE_TO_PROCESS.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		try{
			long OfStart=System.currentTimeMillis();
			LeadResponse leadResponse = createUsingOF(fetchProviderFirmRequest);
			long OfEnd=System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
	            logger.info("Inside Service class..>>Lead Creation using OF Time Taken>>"+(OfEnd-OfStart));
			}
			if (!leadResponse.getErrors().isEmpty()) {
			return createErrorResponse(leadResponse.getErrorMessage(),
					ResultsCode.LEAD_CREATION_VIA_OF_FAILED.getCode());
			}else{
				slLeadIdFromOF=leadResponse.getLeadId();
			}
		}catch(Exception e){
			logger.error("Exception by FetchService for OF to create lead: "+e.getStackTrace());
		}
		// Method to persist LeadObject in SL
		try {
			if(null!=slLeadIdFromOF){
				slLeadId=slLeadIdFromOF;
				fetchProviderFirmRequest.setSlLeadId(slLeadIdFromOF);
				logger.info("Lead id created using OF: "+slLeadId);
				Date today = new Date();
				LeadStatisticsVO statisticsVO = new LeadStatisticsVO(slLeadId, today,
						today,
						PublicAPIConstant.DataFlowDirection.DATA_FLOW_CLIENT_TO_SL,
						PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION,
						today, today, "SL", "SL");
				leadProcessingBO.saveLeadStats(statisticsVO);
				//Logging details into lead history
				insertIntoHistory(slLeadId,NewServiceConstants.VALID_ZIP);
			}else{
				//slLeadId = leadProcessingBO.saveLeadInfo(fetchProviderFirmRequest);
				logger.info("Lead creation through JPDL seems to be not successful");
			}			
		} catch (Exception e) {
			return createErrorResponse(
					ResultsCode.UNABLE_TO_PROCESS.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS.getCode());
		}

		//Use the mapper to map or create the LMS Ping request
		pingRequest = leadManagementMapper
				.mapClientToLMS(fetchProviderFirmRequest);

		//Ping LMS and get the reponse
		try {
			long lmsStart=System.currentTimeMillis();
			lmsPingResponse = matchLMSProviderFirms(pingRequest,slLeadId);
			long lmsEnd=System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
	            logger.info("Inside Service class..>>Time Taken for Lms API>>"+(lmsEnd-lmsStart)+ 
	            		" for lead id: "+fetchProviderFirmRequest.getSlLeadId());
			}
			
		} catch (Exception e1) {
			return createErrorResponse(
					ResultsCode.UNABLE_TO_PROCESS.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		//Handle the response appropriately
		if (null != lmsPingResponse) {
			//Error in matching lead from LMS
			if (PublicAPIConstant.LMSLeadStatus.ERROR.equals(lmsPingResponse
					.getStatus())) {
				logger.error("Error in matching lead from LMS "
						+ lmsPingResponse.getError());
				return createErrorResponse(
						ResultsCode.UNABLE_TO_PROCESS.getMessage(),
						ResultsCode.UNABLE_TO_PROCESS.getCode());
			} else if (PublicAPIConstant.LMSLeadStatus.UNMATCHED
					.equals(lmsPingResponse.getStatus())) {
				response = createErrorResponse(
						ResultsCode.UNMATCHED.getMessage(),
						ResultsCode.UNMATCHED.getCode());
				firmIds = null;
			} else {
				if (null != lmsPingResponse.getProviders()
						|| null != lmsPingResponse.getProviders()
								.getLmsProvider()) {
					for (LMSProvider provider : lmsPingResponse.getProviders()
							.getLmsProvider()) {
						firmIds.add(Integer.parseInt(provider.getFirmId()));
					}
				}
			}
			response.setLeadId(lmsPingResponse.getLeadId());
			response.setLmsLeadId(lmsPingResponse.getLeadId());
		} else {
			return createErrorResponse(
					ResultsCode.UNABLE_TO_PROCESS.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		//Change in case if we need to return lms leadId in response
		response.setSlLeadId(slLeadId);
		Date today = new Date();

		//Create LMS response Lead stats object
		LeadStatisticsVO statisticsVO = new LeadStatisticsVO(slLeadId, today,
				today, PublicAPIConstant.DataFlowDirection.DATA_FLOW_LMS_TO_SL,
				PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION,
				today, today, "SL", "SL");

		try {
			leadProcessingBO.saveLeadStats(statisticsVO);
		} catch (BusinessServiceException e1) {
			logger.error("Error in Lead stats " + e1.getMessage());
		}
        
		if (null != firmIds) {
			String lmsId=response.getLmsLeadId();
			fetchProviderFirmRequest.setFirmIds(firmIds);
			//Method which creates response
			setResponse(response, fetchProviderFirmRequest);
			//Returning No matching Provider Firms 
			if(null!= response.getFirmDetailsList()&& null!= response.getFirmDetailsList().getFirmDetailsList()
					&& response.getFirmDetailsList().getFirmDetailsList().isEmpty()){
				response = createErrorResponse(ResultsCode.UNMATCHED.getMessage(),ResultsCode.UNMATCHED.getCode());
				response.setLeadId(slLeadId);
				SLLeadVO leadVO = new SLLeadVO();
				//setting lmsleadId from response to statisticsVO to update sl_lead_hdr
				leadVO.setLmsLeadId(lmsId);
				leadVO.setLeadWfStatus(NewServiceConstants.LEAD_UNMATCHED);
				leadVO.setSlLeadId(slLeadId);
				 statisticsVO = new LeadStatisticsVO();
				statisticsVO.setSlLeadId(slLeadId);
				statisticsVO.setTypeOfInteraction(PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION);
				statisticsVO = leadManagementMapper.setLeadStatistics(statisticsVO);
				try{
					leadProcessingBO.updateLmsLeadIdAndWfStatus(leadVO);
					leadProcessingBO.saveLeadStats(statisticsVO);
				}catch(Exception e){
					logger.info("Exception in logging lead Statistics" +e.getMessage());
				}
				return response;
			}
			SLLeadVO leadVO = new SLLeadVO();
			//setting lmsleadId from response to statisticsVO to update sl_lead_hdr
			leadVO.setLmsLeadId(response.getLmsLeadId());
			leadVO.setLeadWfStatus(NewServiceConstants.LEAD_UNMATCHED);
			leadVO.setSlLeadId(response.getSlLeadId());
			
			// Method which will save the providers whose no=NoOfMatches from Request
			// firms after Ranking based on specific algorithm. Assuming the lead_wf_status is "Unmatched" Now.
			postedProviders = leadManagementMapper.setTestProviders(response);

			// Method to save response Info in SL as well as logging lead statistics
			try {
				leadProcessingBO.saveResponseInfo(postedProviders,statisticsVO,leadVO);
			} catch (Exception e) {
				logger.error("Exception in saving Response Info in ServiceLive"
						+ e.getMessage());
			}
		}else{
			SLLeadVO leadVO = new SLLeadVO();
			//setting lmsleadId from response to statisticsVO to update sl_lead_hdr
			leadVO.setLmsLeadId(response.getLmsLeadId());
			leadVO.setLeadWfStatus(NewServiceConstants.LEAD_UNMATCHED);
			leadVO.setSlLeadId(response.getSlLeadId());
			try{
				leadProcessingBO.updateLmsLeadIdAndWfStatus(leadVO);
			}catch(Exception e){
				logger.error("Exception in saving lmslead id and wf status Info in ServiceLive"+ e.getMessage());
			}
		}

		//generating lmsLeadId and set it as lmsleadId in response, */
		if (StringUtils.isNotBlank(lmsPingResponse.getLeadId())) {
			response.setLmsLeadId(lmsPingResponse.getLeadId());
		}		
		//Based On Confirmation we can set leadId in response. As if now,setting slLeadId as LeadId in response
		response.setLeadId(response.getSlLeadId());
		if (null == response.getResults()) {
			response.setResults(results);
		}
		//POC for executing JBPM code
		if(null!=slLeadIdFromOF){
			LeadResponse leadResponse = processLeadOFRequest(slLeadIdFromOF);
		}
		 statisticsVO = new LeadStatisticsVO();
		statisticsVO.setSlLeadId(slLeadId);
		statisticsVO.setTypeOfInteraction(PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION);
		statisticsVO = leadManagementMapper.setLeadStatistics(statisticsVO);
		try{
			leadProcessingBO.saveLeadStats(statisticsVO);
		}catch(Exception e){
			logger.error("Exception in logging lead Statistics" +e.getMessage());
		}
		long end = System.currentTimeMillis();
    	if (logger.isInfoEnabled()) {
            logger.info("Inside FetchProvidersService.execute()..>>" +
            		"Creation Time Taken for lead from service class >>"+(end-start));
		}
		return response;
	}


	

	private LMSPingResponse matchLMSProviderFirms(LMSPingRequest pingRequest, String slLeadId)
			throws BusinessServiceException {
		//Create LMS response Lead stats object
		Date todayRequest = new Date();
        LeadStatisticsVO statisticsVORequest = new LeadStatisticsVO(slLeadId, todayRequest,null, 
        		PublicAPIConstant.DataFlowDirection.DATA_FLOW_SL_TO_LMS,
				PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION,
				todayRequest, todayRequest, "SL", "SL");

		try {
			leadProcessingBO.saveLeadStats(statisticsVORequest);
		} catch (BusinessServiceException e1) {
			logger.error("Error in Lead stats " + e1.getMessage());
		}
		URL url = null;
		try {
			url = new URL(lmsUrl);
		} catch (MalformedURLException e) {
			logger.error("Exception in setting the URL " + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}
		HttpURLConnection httpURLConnection = null;
		LMSPingResponse lmsPingResponse = null;
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

			String request = serializeRequest(pingRequest, LMSPingRequest.class)
					.replaceAll("__", "_");
			logger.info("LMSPingRequest: " + request);
			dataOutputStream = new DataOutputStream(
					httpURLConnection.getOutputStream());
			dataOutputStream.writeBytes(request);
			dataOutputStream.flush();
			dataOutputStream.close();

			String pingResponse;
			StringBuffer responseBuffer = new StringBuffer();

			dataInputStream = new DataInputStream(
					httpURLConnection.getInputStream());
			while (null != ((pingResponse = dataInputStream.readLine()))) {
				responseBuffer.append(pingResponse);
				logger.info("response is ::" + pingResponse);
			}
			dataInputStream.close();
			Date todayResponse = new Date();
	        LeadStatisticsVO statisticsVOResponse = new LeadStatisticsVO(slLeadId, todayRequest,todayResponse, 
	        		PublicAPIConstant.DataFlowDirection.DATA_FLOW_LMS_TO_SL,
					PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION,
					todayResponse, todayResponse, "SL", "SL");

			try {
				leadProcessingBO.saveLeadStats(statisticsVOResponse);
			} catch (BusinessServiceException e1) {
				logger.error("Error in Lead stats " + e1.getMessage());
			}
			if (200 != httpURLConnection.getResponseCode()) {
				logger.error("Exception in getting response from LMS. Response code is: "
						+ httpURLConnection.getResponseCode());
				logger.error("Response message is: "
						+ httpURLConnection.getResponseMessage());
				throw new BusinessServiceException("Response code is:"
						+ httpURLConnection.getResponseCode());
			}

			logger.info("response is ::" + responseBuffer.toString());
			String responseString=responseBuffer.toString();
			responseString=removeSpecialCharacters(responseString);
			lmsPingResponse = (LMSPingResponse) deserializeResponse(
					responseString, LMSPingResponse.class);

		} catch (IOException e) {
			logger.error("Exception in connecting to LMS while fetching matching provider firms "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		} catch (Exception e) {
			logger.error("Exception in fetching matching provider firms from LMS "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return lmsPingResponse;
	}

	private String removeSpecialCharacters(String responseString) {
		if(StringUtils.isNotBlank(responseString)){
			responseString=responseString.replaceAll("&","&amp;");
		}
		return responseString;
	}

	private void setResponse(FetchProviderFirmResponse response,
			FetchProviderFirmRequest fetchProviderFirmRequest) {

		Map firmDetailsVOs = new HashMap();

		// This method will fetch Firm rating,reviews,firm name,firmowner and
		// yearsofservice
		try {
			long firmDeatilsStart=System.currentTimeMillis();
			firmDetailsVOs = leadProcessingBO.getFirmDetails(fetchProviderFirmRequest);
			long firmDeatilsEnd=System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
	            logger.info("Inside FetchProvidersService..>>" +
	            		"Time Taken for fetching firmDetails,reviews & Ratings >>"+(firmDeatilsEnd-firmDeatilsStart));
			}
		} catch (BusinessServiceException e) {
			logger.info("Exception in getting  firmDetails " + e.getMessage());
			e.printStackTrace();
		}
		FirmDetailsVO firmdetailsVO = null;
		List<FirmDetails> firmDetailsList = new ArrayList<FirmDetails>();
		for (Integer firmId : fetchProviderFirmRequest.getFirmIds()) {
			FirmDetails resultFirm = null;
			List<FirmReview> reviewList = new ArrayList<FirmReview>();
			if (null != firmId && null != firmDetailsVOs
					&& null != firmDetailsVOs.get(firmId)) {
				resultFirm = new FirmDetails();
				firmdetailsVO = (FirmDetailsVO) firmDetailsVOs.get(firmId);
				resultFirm.setFirmId(firmId);
				if (null != firmdetailsVO) {
					resultFirm.setFirmName(firmdetailsVO.getFirmName());
					resultFirm.setFirmOwner(firmdetailsVO.getFirmOwner());
					resultFirm.setFirmdistance(firmdetailsVO.getDistance());
					// check business start date null or not
					if (null != firmdetailsVO
							&& null != firmdetailsVO.getBusinessStartDate()) {
						String yearsOfService = getYearsInBusiness(firmdetailsVO
								.getBusinessStartDate());

						if (StringUtils.isNotBlank(yearsOfService)) {
							resultFirm.setYearsOfService(Double
									.parseDouble(yearsOfService));
						}

					}
					if (null!=firmdetailsVO && null!=firmdetailsVO.getRating() ) {
						 resultFirm.setFirmRating(firmdetailsVO.getRating());
					} else {
						resultFirm.setFirmRating(0.0d);
					}

					FirmReview reviewes = new FirmReview();
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String dateString = firmdetailsVO.getReviewdDate();
					String reviewRatingString = firmdetailsVO.getReviewRating();
					Double rating = null;
					Date date = null;
					try {
						if (StringUtils.isNotBlank(dateString)
								&& StringUtils.isNotBlank(reviewRatingString)) {
							date = formatter.parse(dateString);
							rating = Double.parseDouble(reviewRatingString);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					// setting reviews
					reviewes.setComment(firmdetailsVO.getReviewComment());
					reviewes.setReviewerName(firmdetailsVO.getReviewerName());
					reviewes.setRating(rating);
					reviewes.setDate(date);
					reviewList.add(reviewes);
					FirmReviews firmReviews = new FirmReviews();
					firmReviews.setFirmReview(reviewList);
					resultFirm.setFirmReviews(firmReviews);
					//resultFirm.setFirmRank(3);
					firmDetailsList.add(resultFirm);
				}
			}
		}
		/*
		 * Response Returned From LMS will Go through 3 filtering 
		 * 1.Validate SPN
		 * 2.Ranking Algorithm 
		 * 3.No Of Matches validation
		 */
		// This method will validate spnId and remove invalid firms from response
		  long StartSPnFilter=System.currentTimeMillis();
		  if(null!=firmDetailsList && !firmDetailsList.isEmpty()){
		     firmDetailsList =excludeInvalidFirmsForSpn(firmDetailsList,fetchProviderFirmRequest.getLeadCategory()); 
		     }
		  long EndSPnFilter=System.currentTimeMillis();
		  if(logger.isInfoEnabled()){
			  logger.info("Inside FetchProvidersService..>>" +
	            		"Time Taken for Spn Filtering >>"+(EndSPnFilter-StartSPnFilter)+
	            		" for lead id: "+fetchProviderFirmRequest.getSlLeadId());
		  }
		  
		//Following code will remove the firms from response if they don't have a lead profile or if they don't have
        //exclusive or competitive price is not available for the project selected
        if(null!=firmDetailsList && !firmDetailsList.isEmpty()){
        	try{
        		firmDetailsList=leadProcessingBO.filterFirmsWithLeadProfileAndPrice(firmDetailsList, fetchProviderFirmRequest);
        	}catch(BusinessServiceException e){
        		logger.error("Error occurred while executing leadProcessingBO.filterFirmsWithLeadProfileAndPrice: "+e.getMessage()+
        				" for lead id: "+fetchProviderFirmRequest.getSlLeadId());
        	}     	
        }
        
		/*
		 * This Method will Rank the Firms based on
		 * firmRating,Distance,completes per week
		 */
		long startRanking=System.currentTimeMillis();
		if (null != firmDetailsList && !firmDetailsList.isEmpty()) {
			try {
				SetRank(firmDetailsList, fetchProviderFirmRequest.getFirmIds());
			} catch (BusinessServiceException e) {
				logger.error("Exception in calling rank method " + e.getMessage());
				e.printStackTrace();
			}
		}
		// Sort Based On Rank
		if(null!=firmDetailsList && !firmDetailsList.isEmpty() ){
		Collections.sort(firmDetailsList, new Comparator<FirmDetails>() {
            public int compare(FirmDetails o1, FirmDetails o2) {
				return o1.getFirmRank().compareTo(o2.getFirmRank());
			   }
            });
            for(FirmDetails firmVO:firmDetailsList){
			   logger.info("Provider Firm Id :"+ firmVO.getFirmId()+"Rank is: "+ firmVO.getFirmRank());
		     }
		}
		long endRanking=System.currentTimeMillis();
		if(logger.isInfoEnabled()){
			 logger.info("Inside FetchProvidersService..>>" +
	            		"Time Taken for Ranking  >>"+(endRanking-startRanking)+
	            		" for lead id: "+fetchProviderFirmRequest.getSlLeadId());
		}
		if (null != firmDetailsList && !firmDetailsList.isEmpty()) {
			int firmListSize = firmDetailsList.size();
			int noOfMatches = fetchProviderFirmRequest.getNoOfMatches();
			if (firmListSize > noOfMatches) {
				int count = firmListSize - noOfMatches;
				int toBeRemoved = firmListSize - 1;
				for (int j = 1; j <= count; j++) {
					firmDetailsList.remove(toBeRemoved);
					toBeRemoved = toBeRemoved - 1;
				}
			}

		}
		ProviderFirms providerFirms = new ProviderFirms();
		providerFirms.setFirmDetailsList(firmDetailsList);
		response.setResults(Results.getSuccess());
		response.setFirmDetailsList(providerFirms);

	}

	  /*
     * This method will check for SPN against category Electrical/Plumbing and
     * remove rest from Response List
     */

    private List<FirmDetails> excludeInvalidFirmsForSpn(
            List<FirmDetails> firmDetailsList, String leadCategory) {
       
        try {
           
           if (StringUtils.isNotBlank(leadCategory)
                    && leadCategory.equals(NewServiceConstants.ELECTRICAL_LEAD_CATEGORY)) {
           
                Integer spnId = leadProcessingBO.getSpnId(NewServiceConstants.ELECTRICAL_LEAD_CATEGORY);
              //Returning the list without filtering if no spn is assigned  for the particular lead category
                if(spnId.intValue() == 0){
                    return firmDetailsList;
                }
                else{   
                    firmDetailsList=getAvailableSpnForFirms(firmDetailsList,spnId);
                }
               

            } else if (StringUtils.isNotBlank(leadCategory)
                    && leadCategory.equals(NewServiceConstants.PLUMBING_LEAD_CATEGORY)) {
                Integer spnId =  leadProcessingBO.getSpnId(NewServiceConstants.PLUMBING_SPN_ID);
                if(spnId.intValue() == 0){
                    return firmDetailsList;
                }
                else{
                    firmDetailsList=getAvailableSpnForFirms(firmDetailsList,spnId);
                }
            }
            //resultList.addAll(firmDetailsList);
        } catch (BusinessServiceException e) {
            logger.error("Exception in excluding Invalid Firms For Spn");
        }
        return firmDetailsList;

    }
    /* Removing FirmId which are not from this SPN.
    */
    public List<FirmDetails> getAvailableSpnForFirms(
            List<FirmDetails> firmDetailsList, Integer spnId) {
        // Create FirmId List
        List<Integer> firmIdList = new ArrayList<Integer>();
        List<FirmDetails> resultList = new ArrayList<FirmDetails>();
        for (FirmDetails firmDetail : firmDetailsList) {
            if (null != firmDetail && null != firmDetail.getFirmId()) {
                firmIdList.add(firmDetail.getFirmId());
            }
        }
        try {

            Map spnMap = leadProcessingBO.getAvailableSpnForFirms(firmIdList);
            if (null != spnMap && spnMap.size() == 0) {
                // Since map is null we are returning the firmdetailslist to be removed later
                return new ArrayList<FirmDetails>();
            }
            for (FirmDetails firmDetail : firmDetailsList) {
                if (null != firmDetail && null != firmDetail.getFirmId()) {
                    List<Integer> spnIdList = (List<Integer>) spnMap
                            .get(firmDetail.getFirmId());
                    if (null != spnIdList && !spnIdList.isEmpty()
                            && spnIdList.contains(spnId)) {
                        resultList.add(firmDetail);
                    }
                }
            }
        } catch (BusinessServiceException e) {
            logger.info("Exception in getting spn available for Firms");
        }
        return resultList;

    }

    /*
	 * This Method will Rank the Firms based on firmRating,Distance,completes
	 * per week 1.FirmRating Sorted in:Highest To Lowest 2.Distance Sorted
	 * in:Lowest To Highest. 3.Completes Per Week :Sorted in Lowest To Highest.
	 */
	private void SetRank(List<FirmDetails> firmDetailsList,List<Integer> firmIdList) throws BusinessServiceException {
		List<CriteraDetailsVO> ratingList = new ArrayList<CriteraDetailsVO>();
		List<CriteraDetailsVO> distanceList = new ArrayList<CriteraDetailsVO>();
		List<CriteraDetailsVO> completesPerWeekList = new ArrayList<CriteraDetailsVO>();
	    Map completesPerWeekMap=new HashMap();
		try{
		completesPerWeekMap=leadProcessingBO.getCompletesPerWeek(firmIdList);
		CompleteVO compVO=null;
		if(null!= completesPerWeekMap && completesPerWeekMap.size()!=0){
			for(Integer firmId:firmIdList){
			compVO=(CompleteVO)completesPerWeekMap.get(firmId);
			CriteraDetailsVO vo = new CriteraDetailsVO();
			if(null!= compVO && null != compVO.getFirmId() && null!= firmId){
			    vo.setFirmId(compVO.getFirmId());
			    vo.setCompletesPerWeek(compVO.getNoOfCompletedDate());
				}
			    completesPerWeekList.add(vo);
			}	
		
		}
		}
		catch (BusinessServiceException e) {
			logger.info("Exception in getting completesperweek for Firms");
		}
		
		if (null != firmDetailsList && !firmDetailsList.isEmpty()) {
			for (FirmDetails firmDetails : firmDetailsList) {
				if (null != firmDetails && null != firmDetails.getFirmRating()
						&& null != firmDetails.getFirmdistance()
						&& null != firmDetails.getFirmId()) {
					CriteraDetailsVO vo1 = new CriteraDetailsVO();
					CriteraDetailsVO vo2 = new CriteraDetailsVO();
					vo1.setFirmId(firmDetails.getFirmId());
					vo1.setRating(firmDetails.getFirmRating());
					vo1.setFirmName(firmDetails.getFirmOwner());
					vo2.setFirmId(firmDetails.getFirmId());
					vo2.setFirmName(firmDetails.getFirmOwner());
					vo2.setDistance(firmDetails.getFirmdistance());
					
					distanceList.add(vo2);
					ratingList.add(vo1);
				}
			}
		}
		List<Double>ratingListDouble=null;
		List<Double>distanceListDouble=null;
		List<Integer>completesPerWeekListInteger=null;
		if(null!= ratingList && !ratingList.isEmpty()){
			ratingListDouble=removeDuplicateRatings(ratingList);
		}
		if(null!= distanceList && !distanceList.isEmpty()){
			distanceListDouble=removeDuplicateDistance(distanceList);
		}
		if(null!= completesPerWeekList && !completesPerWeekList.isEmpty()){
			completesPerWeekListInteger=removeDuplicateCompletesPerWeek(completesPerWeekList);
		}
		/* Calling Comparator to Rank Rating from Highest To Lowest */
		if(null!= ratingListDouble && !ratingListDouble.isEmpty()){
			Collections.sort(ratingListDouble,new Comparator<Double>() {
            public int compare(Double o1, Double o2) {
					if(o1==o2){
						return 0;
					}else if(o1 < o2){
						return 1;
					}else{
					   return -1;
					}
				}
			});
		}
		/* calling Comparator To Rank Distance from Lowest To Highest */
		if(null!= distanceListDouble &&! distanceListDouble.isEmpty()){
		   Collections.sort(distanceListDouble, new Comparator<Double>() {
			public int compare(Double o1, Double o2) {
				return o1.compareTo(o2);
			   }
		   });
		 }
		/* calling Comparator To Rank Completes Per week  from Lowest To Highest */
		 if(null!= completesPerWeekListInteger && !completesPerWeekListInteger.isEmpty()){
		    Collections.sort(completesPerWeekListInteger, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			   }
		    });
		   }
		HashMap ratingMap = null;
		HashMap distanceMap = null;
		HashMap completesMap = null;
		HashMap unRankedMap = null;
		// Create a map,having firmId as key and its rank(rating/distance).
		ratingMap = setRatingRankedMap(ratingList,ratingListDouble);
		distanceMap = setDistanceRankedMap(distanceList,distanceListDouble);
		completesMap = SetRankedMapForCompletesPerWeek(completesPerWeekList,completesPerWeekListInteger);
		/*
		 * This method will calculate overall rating based on weightage for
		 * rating and Distance
		 */
		unRankedMap = calculateRatingAndSetRank(firmDetailsList, ratingMap,
				distanceMap,completesMap);
		// Create list with firmId and OverallRating
		List<CriteraDetailsVO> resultList = setRankInVO(unRankedMap);
		//Setting firmName in result list for sorting if ratings found equal
		resultList=setFirmnameInList(resultList,firmDetailsList);
		// Sorting the Result List based on OverAllRating from lowest to highest
		if(null!=resultList && !resultList.isEmpty() ){
		Collections.sort(resultList, new Comparator<CriteraDetailsVO>() {
			public int compare(CriteraDetailsVO o1, CriteraDetailsVO o2) {
				double overallRating1=o1.getOverallRating();
				double overAllRating2=o2.getOverallRating();
				if(overallRating1 == overAllRating2){
					//Use sorting based on firmname if ratings same
					return o1.getFirmName().compareToIgnoreCase(o2.getFirmName());
				}else if(overallRating1 > overAllRating2){
					return 1;
				}else{
					return -1;
				}
			}
		});
		}
		// Setting Rank in ResultList.
		if(null!= resultList && !resultList.isEmpty()){
		  int listSize = resultList.size();
		  for (int i = 0; i < listSize; i++) {
			CriteraDetailsVO vo = resultList.get(i);
			vo.setRank(i + 1);
		  }
		}
		// Setting Rank in firmDetails List
		for (FirmDetails fimDetails : firmDetailsList) {
			for (CriteraDetailsVO vo : resultList) {
				if(null!= vo){
				   if (fimDetails.getFirmId().equals(vo.getFirmId())) {
					fimDetails.setFirmRank(vo.getRank());
				  }
				}
			}
		}
	}


private List<CriteraDetailsVO> setFirmnameInList(
			List<CriteraDetailsVO> resultList, List<FirmDetails> firmDetailsList) {
		if(null!=resultList && null!=firmDetailsList && !resultList.isEmpty() && !firmDetailsList.isEmpty()){
		for(CriteraDetailsVO resultVo:resultList){
			  for(FirmDetails firmVo:firmDetailsList){
				if(resultVo.getFirmId().equals(firmVo.getFirmId())){
					resultVo.setFirmName(firmVo.getFirmOwner());
				}
			}
		  }
		}
		return resultList;
	}

	
	
	private HashMap setRatingRankedMap(List<CriteraDetailsVO> list, List<Double> ratingListDouble) {
		HashMap rankedMap = new HashMap();
		if (null != list && !list.isEmpty()) {
			int size = ratingListDouble.size();
			for (int i = 0; i < size; i++) {
				for(CriteraDetailsVO voList:list){
					if(voList.getRating().equals(ratingListDouble.get(i))){
					  rankedMap.put(voList.getFirmId(), i + 1);
					}
				}
			}
		}
		return rankedMap;
	}
	
	private HashMap setDistanceRankedMap(List<CriteraDetailsVO> list, List<Double> distanceListDouble) {
		HashMap rankedMap = new HashMap();
		if (null != list && !list.isEmpty()) {
			int size = distanceListDouble.size();
			for (int i = 0; i < size; i++) {
				for(CriteraDetailsVO voList:list){
					if(voList.getDistance().equals(distanceListDouble.get(i))){
					  rankedMap.put(voList.getFirmId(), i + 1);
					}
				}
			}
		}
		return rankedMap;
	}
	private HashMap SetRankedMapForCompletesPerWeek(List<CriteraDetailsVO> list, List<Integer> completesPerWeekListInteger) {
		HashMap rankedMap = new HashMap();
		if (null != list && !list.isEmpty()) {
			int size = completesPerWeekListInteger.size();
			List<Integer> unRankedProv = new ArrayList<Integer>();
			int maxRank = 0;
			for (int i = 0; i < size; i++) {
				for(CriteraDetailsVO voList:list){
				    if(voList.getCompletesPerWeek().equals(completesPerWeekListInteger.get(i))){
						rankedMap.put(voList.getFirmId(), i + 1);
					 }
				  }
			   }
			}
		return rankedMap;
	}
	/*
	 * This method will calculate overall rating based on weightage for rating
	 * and Distance rating: 33.3% distance:33.3% completes per week:33.3%
	 */
	private HashMap calculateRatingAndSetRank(
			List<FirmDetails> firmDetailsList, HashMap ratingMap,
			HashMap distanceMap,HashMap completesMap) {
		HashMap unRankedMap = new HashMap();
		if (null != firmDetailsList && !firmDetailsList.isEmpty()) {
			if (null != ratingMap && null != distanceMap
					&& ratingMap.size() == distanceMap.size()&& ratingMap.size()!=0) {
				Double overallRating = 0d;
				Object distanceRankObj = null;
				Object ratingRankObj = null;
				Object completesPerWeekRankObj = null;
				Integer distanceRank = null;
				Integer ratingRank = null;
				Integer completesPerWeekRank = null;
                //Getting weightages from lu tables.
				Map weigthageMap=null;
				double distanceWeightageAsDouble=0.333;
				double ratingsWeightageAsDouble=0.333;
				double completesPerWeekasDouble=0.334;
				try {
					weigthageMap=leadProcessingBO.getRankWeigthtages();
				} catch (BusinessServiceException e) {
					logger.info("Exception in getting criteria weightage");
				}
				if(null != weigthageMap && weigthageMap.size()!= 0){
				   BigDecimal distanceWeightage=(BigDecimal) weigthageMap.get(NewServiceConstants.DISTANCE_WEIGHTAGE);
				   BigDecimal ratingsWeightage=(BigDecimal) weigthageMap.get(NewServiceConstants.RATING_WEIGHTAGE);
				   BigDecimal completesPerWeekWeightage=(BigDecimal) weigthageMap.get(NewServiceConstants.COMPLETES_PERWEEK_WEIGTHTAGE);
				   distanceWeightageAsDouble=distanceWeightage.doubleValue();
				   ratingsWeightageAsDouble=ratingsWeightage.doubleValue();
				   completesPerWeekasDouble=completesPerWeekWeightage.doubleValue();
				}
				// Iterate
				for (FirmDetails firmDetail : firmDetailsList) {
					try {
						if(null != distanceMap && null!= ratingMap && null != completesMap){
						    distanceRankObj = distanceMap.get(firmDetail.getFirmId());
						    ratingRankObj = ratingMap.get(firmDetail.getFirmId());
						    completesPerWeekRankObj = completesMap.get(firmDetail.getFirmId());
						    if(null!= distanceRankObj){
						       distanceRank = (Integer) distanceRankObj;
						    }
						    if(null!=ratingRankObj ){
						       ratingRank = (Integer) ratingRankObj;
						    }
						    if(null==completesPerWeekRankObj){
						      //This object will be null iff the map is empty.Puting unique rank to all firms
						      completesPerWeekRank =new Integer("1");
						    }else{
						      completesPerWeekRank = (Integer) completesPerWeekRankObj;
						    }
						    if(null!= completesPerWeekRank && null!= distanceRank && null!= ratingRank ){
						      overallRating = (double)(completesPerWeekasDouble * completesPerWeekRank) +(double) (distanceRank * distanceWeightageAsDouble) +(double) (ratingRank * ratingsWeightageAsDouble);
						      unRankedMap.put(firmDetail.getFirmId(), overallRating);
						 }
					  }
					} catch (NullPointerException e) {
						logger.info("Object is Null" + e.getMessage());
					} catch (ClassCastException e) {
						logger.info("Expected Integer class,but getting"
								+ distanceRankObj.getClass()
								+ ratingRankObj.getClass() + "Exception is"
								+ e.getMessage());
					}
				}
			}
		}
		return unRankedMap;
	}

	// This method will sort the overall rating,and then set Rank in a VO
	private List<CriteraDetailsVO> setRankInVO(HashMap unRankedMap) {
		List<CriteraDetailsVO> voList = new ArrayList<CriteraDetailsVO>();
		if(null!= unRankedMap && unRankedMap.size()!=0){
		for (Object firmId : unRankedMap.keySet()) {
			CriteraDetailsVO vo = new CriteraDetailsVO();
			try {
				if( null!= firmId){
				   Integer vendorId = new Integer(firmId.toString());
				   Object overallRating = unRankedMap.get(firmId);
				   logger.info("Class type for Key" + firmId.getClass());
				   logger.info("Class for Value is " + overallRating.getClass());
				   Integer id = (Integer) firmId;
				   Double rating = (Double) overallRating;
				   vo.setFirmId(vendorId);
				   vo.setOverallRating(rating);
				   voList.add(vo);
				}
			} catch (NullPointerException e) {
				logger.info("Object is Null" + e.getMessage());
			} catch (ClassCastException e) {
				logger.info("Expected Integer class,but getting"
						+ firmId.getClass() + "Exception is" + e.getMessage());
			  }
			}
	  }
		return voList;
	}

	private String getYearsInBusiness(Date busStartDate) {
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

	private synchronized String getNextId(String sourceId) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbOrder = new StringBuilder();
		Random rand = new Random();
		String ts = "";
		String random = "";

		// current time stamp is the 2nd part of the service order
		// it is at least 13 characters as of today...
		ts = String.valueOf(Calendar.getInstance().getTimeInMillis());
		// remove first 3 chars
		ts = ts.substring(3, 13);

		// a random number is the 3rd part of the key
		random = String.valueOf(Math.abs(rand.nextInt()));
		random = random.substring(0, 2);

		// put the 3 pieces together
		sb.append(sourceId);
		sb.append(ts);
		sb.append(random);

		// construct the service order format XXX-XXXX-XXXX-XXXX
		sbOrder.append(sb.substring(0, 3));
		sbOrder.append("-");
		sbOrder.append(sb.substring(3, 7));
		sbOrder.append("-");
		sbOrder.append(sb.substring(7, 11));
		sbOrder.append("-");
		sbOrder.append(sb.substring(11));

		return sbOrder.toString();
	}

	private FetchProviderFirmResponse createErrorResponse(String message,
			String code) {
		FetchProviderFirmResponse createResponse = new FetchProviderFirmResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}

	/**
	 * Method which will be used to create lead using OF and JPDL.
	 * 
	 * @param soId
	 * @param titleList
	 * @param ownerId
	 * @param attachments
	 * @return
	 */
	private LeadResponse createUsingOF(FetchProviderFirmRequest firmRequest) {
		LeadRequest leadReq = null;
		CreateLeadRequest leadRequest = null;
		LeadResponse leadResponse = new LeadResponse();

		try {
			//creating security context
			Integer buyerId = 3000;
			SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
			// mapper logic for lead mapping
			leadRequest = leadOFMapper.createLead(firmRequest, securityContext);
		} catch (ValidationException slb) {
			leadResponse.addError(ResultsCode.LEAD_CREATION_VIA_OF_FAILED.getCode());
			return leadResponse;
		} catch (BusinessServiceException de) {
			logger.error(
					"FetchProviderService.CreateLeadRequest(): DataException occured: ",
					de);
			leadResponse.addError(de.getMessage());
			return leadResponse;
		}
		leadResponse = ofHelper.createLead(leadRequest);
		
		return leadResponse;
	}
	/* Method which accepts ServiceLive lead id and executes JBPM transition
	 * 
	 */
	private LeadResponse processLeadOFRequest(String leadId) {
		LeadResponse response = new LeadResponse();		
		//creating security context
		Integer buyerId = 3000;
		SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
		try{
			OrderFulfillmentLeadRequest leadRequest = leadOFMapper.createFetchProvidersLeadRequest(leadId, securityContext);
			response = ofHelper.runLeadFulfillmentProcess(leadId, SignalType.UNMATCHED_POST, leadRequest);
		}catch(Exception e){
			logger.info("Exception occurred in processLeadOFRequest of FetchProvidersService: "+e.getMessage());
		}
		
		return response;
	}

	private FetchProviderFirmResponse createGenericErrorResponse() {
		return createErrorResponse(ResultsCode.GENERIC_ERROR.getMessage(),
				ResultsCode.GENERIC_ERROR.getCode());
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

	public void setLeadManagementMapper(
			LeadManagementMapper leadManagementMapper) {
		this.leadManagementMapper = leadManagementMapper;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public LeadOFMapper getLeadOFMapper() {
		return leadOFMapper;
	}

	public void setLeadOFMapper(LeadOFMapper leadOFMapper) {
		this.leadOFMapper = leadOFMapper;
	}

	//We have customised the serialize request to handle xstream '_' as 
	//it is an escape character in xstream
	private String serializeRequest(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		// String requestXml = xstream.toXML(request).toString();
		return xstream.toXML(request).toString().replaceAll("__", "_");
	}

	private XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}

	@SuppressWarnings("rawtypes")
	private Object deserializeResponse(String objectXml, Class<?> clazz) {
		// return this.<IAPIResponse>deserializeRequest(objectXml,c);
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
	private List<Integer> removeDuplicateCompletesPerWeek(List<CriteraDetailsVO> completesPerWeekList) {
		  List<Integer> nonDuplicateList=new ArrayList<Integer>();
		  List<CriteraDetailsVO> duplicateObjList=new ArrayList<CriteraDetailsVO>();
		  duplicateObjList.addAll(completesPerWeekList);
		  Iterator<CriteraDetailsVO> dupIter = duplicateObjList.iterator();
		  while(dupIter.hasNext()){
			  CriteraDetailsVO nextElement=dupIter.next();
			  if(nonDuplicateList.contains(nextElement.getCompletesPerWeek())){
				  dupIter.remove();
			  }else{
				  nonDuplicateList.add(nextElement.getCompletesPerWeek());
			  }
		   }
		return nonDuplicateList;
	}
	private List<Double> removeDuplicateDistance(List<CriteraDetailsVO> distanceList) {
		List<Double> nonDuplicateList=new ArrayList<Double>();
		  List<CriteraDetailsVO> duplicateObjList=new ArrayList<CriteraDetailsVO>();
		  duplicateObjList.addAll(distanceList);
		  Iterator<CriteraDetailsVO> dupIter = duplicateObjList.iterator();
		  while(dupIter.hasNext()){
			  CriteraDetailsVO nextElement=dupIter.next();
			  if(nonDuplicateList.contains(nextElement.getDistance())){
				  dupIter.remove();
			  }else{
				  nonDuplicateList.add(nextElement.getDistance());
			  }
		   }
		return nonDuplicateList;
	}
	private List<Double> removeDuplicateRatings(List<CriteraDetailsVO> ratingList) {
		  List<Double> nonDuplicateList=new ArrayList<Double>();
		  List<CriteraDetailsVO> duplicateObjList=new ArrayList<CriteraDetailsVO>();
		  duplicateObjList.addAll(ratingList);
		  Iterator<CriteraDetailsVO> dupIter = duplicateObjList.iterator();
		  while(dupIter.hasNext()){
			  CriteraDetailsVO nextElement=dupIter.next();
			  if(nonDuplicateList.contains(nextElement.getRating())){
				  dupIter.remove();
			  }else{
				  nonDuplicateList.add(nextElement.getRating());
			  }
		   }
		return nonDuplicateList;
	}
	public LeadManagementValidator getLeadManagementValidator() {
		return leadManagementValidator;
	}

	public void setLeadManagementValidator(
			LeadManagementValidator leadManagementValidator) {
		this.leadManagementValidator = leadManagementValidator;
	}

	public String getLmsUrl() {
		return lmsUrl;
	}

	public void setLmsUrl(String lmsUrl) {
		this.lmsUrl = lmsUrl;
	}
	private void insertIntoHistory(String slLeadId,String zipStatus){
		//getting security context 					
		SecurityContext securityContext=null;					
		securityContext = getSecurityContextForBuyerAdmin(NewServiceConstants.HOME_SERVICES_BUYER_ID);

		String modifiedBy=securityContext.getUsername();
		Integer entityId=securityContext.getVendBuyerResId();
		String comment= NewServiceConstants.LEAD_CREATED_INVALID_ZIP;
		if(zipStatus.equalsIgnoreCase(NewServiceConstants.VALID_ZIP)){
			comment=NewServiceConstants.LEAD_CREATED_VALID_ZIP;
		}
		try{
		String createdBy=leadProcessingBO.getBuyerName(NewServiceConstants.HOME_SERVICES_BUYER_ID);
		LeadLoggingVO leadLoggingVO=new LeadLoggingVO(slLeadId , NewServiceConstants.LEAD_CREATED, 
				NewServiceConstants.OLD_VALUE, NewServiceConstants.NEW_VALUE,comment, 
				createdBy,modifiedBy, NewServiceConstants.ROLE_ID_BUYER,entityId);					
		leadProcessingBO.insertLeadLogging(leadLoggingVO);
		}
		catch (BusinessServiceException e) {
			logger.error("Error in Lead stats " + e.getMessage());
		}
	}

}
