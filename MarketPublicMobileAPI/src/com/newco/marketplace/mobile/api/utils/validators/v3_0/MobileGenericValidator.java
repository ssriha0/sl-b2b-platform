package com.newco.marketplace.mobile.api.utils.validators.v3_0;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.assignSO.AssignVO;
import com.newco.marketplace.api.beans.so.reportAProblem.ReportProblemVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.beans.so.submitReschedule.SORescheduleVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.counterOffer.WithdrawCounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.deleteFilter.DeleteFilterResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsResponse;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterRequest;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterResponse;
import com.newco.marketplace.api.mobile.beans.so.accept.MobileSOAcceptResponse;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidRequest;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidResponse;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchResponse;
import com.newco.marketplace.api.mobile.beans.so.search.advance.Appointment;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchResponse;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOGetMobileDetailsResponse;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleResponse;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsRequest;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsResponse;
import com.newco.marketplace.api.mobile.beans.vo.AcceptVO;
import com.newco.marketplace.api.mobile.beans.vo.CounterOfferVO;
import com.newco.marketplace.api.mobile.beans.vo.ForgetUnamePwdVO;
import com.newco.marketplace.api.mobile.beans.vo.ProviderParamVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersCriteriaVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.mobile.v2_0.MobileSORejectVO;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.newco.marketplace.vo.mobile.v2_0.SecQuestAnsRequestVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.SOSchedule;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
 * for validating phase 2 mobile APIs
 *
 */
public class MobileGenericValidator {

	private static final Logger LOGGER = Logger.getLogger(MobileGenericValidator.class);

	private IMobileGenericBO mobileGenericBO;

	/**
	 * @param soSearchCriteria
	 * @param pageNo
	 * @param pageSize
	 * @param securityContext 
	 * @return
	 * 
	 * to validate the search criteria input
	 * 
	 */
	public MobileSOSearchResponse validate(Integer pageSize,
			SecurityContext securityContext) {
		MobileSOSearchResponse providerSOSearchResponse = null;
		Results results = null;
		
		// validating logged in resource id
		if (securityContext == null) {
			results = Results.getError(
					ResultsCode.INVALID_RESOURCE_ID.getMessage(),
					ResultsCode.INVALID_RESOURCE_ID.getCode());

			providerSOSearchResponse = new MobileSOSearchResponse();
			providerSOSearchResponse.setResults(results);

		}
		// validating page size

		else if (validatePageSize(pageSize)) {
			results = Results
					.getError(
							ResultsCode.SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE
							.getMessage(),
							ResultsCode.SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE
							.getCode());
			providerSOSearchResponse = new MobileSOSearchResponse();
			providerSOSearchResponse.setResults(results);
		}
		return providerSOSearchResponse;
	}

	public ReportProblemVO validateReportAProblem(ReportProblemVO reportProblemVO) throws BusinessServiceException {
		boolean isValidStatus = false;
		String problemTypeDescription= null;
		try{
			isValidStatus = validateServiceOrderStatus(reportProblemVO.getSoId());
			if(isValidStatus){
				problemTypeDescription = mobileGenericBO.getProblemTypeDescription(MPConstants.PROBLEM_STATUS,reportProblemVO.getProblemReasonCode());
				reportProblemVO.setProblemReasonCodeDescription(problemTypeDescription);
				reportProblemVO.setValidationCode(ResultsCode.SUCCESS);
			}// Error Message for invalid service order status(Not in active)
			else{
				reportProblemVO.setValidationCode(ResultsCode.INVALID_SO_STATUS);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in validating request"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return reportProblemVO;
	}
	private boolean validateServiceOrderStatus(String soId) throws BusinessServiceException{
		boolean isValidStatus = false;
		try{
			isValidStatus = mobileGenericBO.checkIfSOIsActive(soId);

		}catch (Exception e) {
			LOGGER.error("Exception in validating service order status"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return isValidStatus;
	}
	/**
	 * 
	 * @param apiVO
	 * @param errorOccured
	 * @param validationErrors
	 * @return
	 * @throws BusinessServiceException
	 * method to validate the SO status
	 */	
	public boolean validateAssignSOProviderStatus(AssignVO assignVO,Integer wfStateId, List<ErrorResult> validationErrors) throws BusinessServiceException{
		boolean errorOccured = false;
		if(null != assignVO.getSoId() && null != assignVO.getFirmId()){
			try{

				if(OrderConstants.ACCEPTED_STATUS == wfStateId || 
						OrderConstants.ACTIVE_STATUS == wfStateId || OrderConstants.PROBLEM_STATUS == wfStateId){
					errorOccured = false;
				}
				else{
					errorOccured = true;
					addError(ResultsCode.ASSIGN_SO_STATE_ERROR.getMessage(),ResultsCode.ASSIGN_SO_STATE_ERROR.getCode(),validationErrors);
				}
			}
			catch(Exception e){
				LOGGER.error("Exception in validating aservice order status for AssignSOProvider"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
		}
		return errorOccured;
	}

	/**
	 * 
	 * @param apiVO
	 * @param errorOccured
	 * @param validationErrors
	 * @return
	 * @throws BusinessServiceException
	 * method to check whether the resource is already assigned to the so or not
	 */
	private  boolean validateAssignSOProvider(AssignVO assignVO,List<RoutedProvider>routedResources, List<ErrorResult> validationErrors)throws BusinessServiceException {
		boolean errorOccured=false;
		Integer providerResourceId = assignVO.getResourceId();
		boolean isValidResource = false ;
		if(null != assignVO.getSoId() && null != assignVO.getFirmId()){
			try{				
				//List<RoutedProvider> routedProviderList = so.getRoutedResources();				
				for(RoutedProvider provider : routedResources){
					if (providerResourceId.intValue() == provider.getResourceId().intValue()) {	
						if(null != provider.getProviderRespId() && 1 == provider.getProviderRespId()){
							errorOccured = true;
							addError(ResultsCode.ASSIGN_SO_PROVIDER_ERROR.getMessage(),ResultsCode.ASSIGN_SO_PROVIDER_ERROR.getCode(),validationErrors);
						}
						isValidResource = true;
						break;
					}
				}
				if(!isValidResource){
					errorOccured = true;
					addError(ResultsCode.ASSIGN_SO_PROVIDER_NOTMATCH.getMessage(),ResultsCode.ASSIGN_SO_PROVIDER_NOTMATCH.getCode(),validationErrors);
				}
			}

			catch(Exception e)	{
				LOGGER.error("Exception in validating assign so provider"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
		}
		return errorOccured;


	}

	/**
	 * 
	 * @param apiVO
	 * @return
	 * @throws BusinessServiceException
	 * Method to validate the Assign/Reassign API
	 */
	public List<ErrorResult> validateAssignOrReassignSO(AssignVO assignVO) throws BusinessServiceException{

		boolean errorOccured = false;
		List<ErrorResult> validationErrors = null;
	//	ServiceOrder so = null;

		try{

			validationErrors = new ArrayList<ErrorResult>();

		//	so = mobileGenericBO.getSoDetails(assignVO.getSoId());
			AssignVO vo = mobileGenericBO.getProviderSODetails(assignVO.getSoId());

			if(MPConstants.ASSIGN_SO.equals(assignVO.getRequestFor())){
				
				if(assignVO.getResourceId().intValue() == 0){
					errorOccured = true;
					addError(ResultsCode.INVALID_RESOURCE_ID.getMessage(),ResultsCode.INVALID_RESOURCE_ID.getCode(),validationErrors);
				}
				// Method to validate the resource against resources under the firm routed to the so
				if(!errorOccured){
				errorOccured = validateResourceUnderFirm(assignVO, validationErrors);
				}
				if(!errorOccured){
					//Method to perform the role level validations
					errorOccured = validateResourceRole(assignVO, validationErrors);
				}
				if(!errorOccured){
					// method to validate the SO status
					errorOccured = validateAssignSOProviderStatus(assignVO,vo.getWfStateId(), validationErrors);
				}
				if(!errorOccured){
					// method to check whether the resource is already assigned to the so or not
					errorOccured = validateAssignSOProvider(assignVO,vo.getRoutedResources(), validationErrors);
				}
			}else if(MPConstants.REASSIGN_SO.equals(assignVO.getRequestFor())){
				
				if(assignVO.getResourceId().intValue() == 0){
					errorOccured = true;
					addError(ResultsCode.INVALID_RESOURCE_ID.getMessage(),ResultsCode.INVALID_RESOURCE_ID.getCode(),validationErrors);
				}
				// Method to validate the resource against resources under the firm routed to the so
				if(!errorOccured){
				validateResourceRole(assignVO, validationErrors);
			}
		}
		}
		catch(Exception e){
			LOGGER.error("Exception in validate method():assign/reassign SO"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return validationErrors;
	}

	/**
	 * @param message
	 * @param code
	 */
	private void addError(String message, String code,List<ErrorResult> validationErrors) {
		ErrorResult result = new ErrorResult();
		result.setCode(code);
		result.setMessage(message);
		validationErrors.add(result);
	}
	/**
	 * @param AcceptVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public MobileSOAcceptResponse validateSOAcceptMobile(AcceptVO acceptVO,SecurityContext securityContext) throws BusinessServiceException{
		MobileSOAcceptResponse response = null;
		ResultsCode validationCode = null;
		try{
			validationCode = mobileGenericBO.validateSoAccept(acceptVO);
			if(null==securityContext){
				response = MobileSOAcceptResponse.getInstanceForError(ResultsCode.SO_ACCEPT_PROVIDER_ID);
			}
			if(null!= validationCode){
				response = MobileSOAcceptResponse.getInstanceForError(validationCode);
			}

		}catch (Exception e) {
			LOGGER.error("Exception in validating service order "+acceptVO.getSoId()+ "error"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return response;

	}
	/**@Description: validating reasonCodes in request and DB.
	 * @param reasonLists,reasonCodesInDB
	 * @return
	 */
	public List<String> validateGetReasonCodes(List<String> reasonLists, List<String> reasonCodesInDB) {
		List<String> errorReasonsCodeTypes = null;
		// Checking size of reasonCode Types from Request And DB.
		if (!(reasonLists.isEmpty()) && !(reasonCodesInDB.isEmpty()) && !reasonCodesInDB.containsAll(reasonLists)) {
			errorReasonsCodeTypes = new ArrayList<String>();
			for(String reasonCode : reasonLists){
				if(!reasonCodesInDB.contains(reasonCode)){
					errorReasonsCodeTypes.add(reasonCode);
				}
			}
		}
		return errorReasonsCodeTypes;
	}

	private boolean validateRoleforCounterOffer(CounterOfferVO offerVO){
		boolean validRole = false;
		int roleId = offerVO.getRoleId();
		Integer firmIdFromDB = null;
		Integer resourseIdUrl = offerVO.getProviderResourceId();
		if(roleId == MPConstants.ROLE_LEVEL_ONE.intValue()){
			validRole = false;
		}
		List<Integer> resourceIdListRequest = new ArrayList<Integer>();
		if(null!=offerVO.getResourceIds() 
				&& null != offerVO.getResourceIds().getResourceId() 
				&& offerVO.getResourceIds().getResourceId().size()>0){
			resourceIdListRequest = offerVO.getResourceIds().getResourceId();
			for(Integer resourceIdRequest: resourceIdListRequest){
				if(roleId == MPConstants.ROLE_LEVEL_TWO.intValue()){
					if(null!= resourseIdUrl && resourseIdUrl.equals(resourceIdRequest)){// step 1-->checking if resourceID in url and resourceId present in request are same
						for (RoutedProvider routedProvider : offerVO.getSo().getRoutedResources()) {
							if (null!=resourceIdRequest && resourceIdRequest.equals(routedProvider.getResourceId())) {//step 2--> if step 1 is true, check if resourceId present in request is present in routed list
									 validRole = true;
									 break;
							}else{
									validRole = false;
							}
						}
					}else{
						validRole = false;
					}
				}else if(roleId == MPConstants.ROLE_LEVEL_THREE.intValue()){
					try {
						firmIdFromDB = mobileGenericBO.validateProviderId(resourceIdRequest);//getting the vendor id for resourceId present in request body
						if(null!=firmIdFromDB && (firmIdFromDB.toString()).equals(offerVO.getFirmId())){//Step1-->firmId of resource present in request should be same as the firmid present in url
							for (RoutedProvider routedProvider : offerVO.getSo().getRoutedResources()) {
								if (null!= resourceIdRequest && resourceIdRequest.equals(routedProvider.getResourceId())) {//Step2-->If step1 is true,check if resourceId present in request is present in routed list
									 validRole = true;
									 break;
								}else{
									validRole = false;
								}
							}
						}else{
							 validRole = false;
						}
					} catch (BusinessServiceException e) {
						LOGGER.error("Exception in getting firm ID");
					}
				}
			
			}
		}
		return validRole;
	}
	
	/**
	 * Method contains all the validations for counter offer
	 * @param offerVO
	 * @param response
	 * @return CounterOfferResponse
	 * @throws BusinessServiceException
	 */
	public CounterOfferResponse validateCounterOffer(CounterOfferVO offerVO, CounterOfferResponse response) throws BusinessServiceException{
		Results results = null;
		boolean isError=false;
		Date serviceDate1=null;
		Date serviceDate2 = null;
		Date today = new Date();
		int count = 0;
		Integer firmId= 0;
		if(StringUtils.isNumeric(offerVO.getFirmId())){
			firmId = Integer.parseInt(offerVO.getFirmId());
		}
		//boolean validRole = false;
		try{
			//validating role
			//validRole = validateRoleforCounterOffer(offerVO);
			if(offerVO.getRoleId() == 3){
			// check whether serviceDate1 is in desired format yyyy-MM-dd'T'HH:mm:ss
			if(null!= offerVO.getServiceDateTime1()){
				try{
					serviceDate1 = CommonUtility.sdfToDate.parse(offerVO.getServiceDateTime1());
					count = serviceDate1.compareTo(today);
					// check whether serviceDate1 is a past date
					if(count<0){
						results = Results.getError(
								ResultsCode.SO_CONDITIONAL_OFFER_START_DATE_PAST
								.getMessage(),
								ResultsCode.SO_CONDITIONAL_OFFER_START_DATE_PAST
								.getCode());
						response.setResults(results);
						isError = true;
					}
				}catch (ParseException e) {
					results = Results.getError(
							ResultsCode.SO_CONDITIONAL_OFFER_INVALID_START_DATE
							.getMessage(),
							ResultsCode.SO_CONDITIONAL_OFFER_INVALID_START_DATE
							.getCode());
					response.setResults(results);
					isError = true;
				}
			}
			// check whether serviceDate2 is in desired format yyyy-MM-dd'T'HH:mm:ss
			if(!isError && null!= offerVO.getServiceDateTime2()){
				try{
					serviceDate2 = CommonUtility.sdfToDate.parse(offerVO.getServiceDateTime2());
					count = serviceDate2.compareTo(today);
					// check whether serviceDate2 is a past date
					if(count<0){
						results = Results.getError(
								ResultsCode.SO_CONDITIONAL_OFFER_END_DATE_PAST
								.getMessage(),
								ResultsCode.SO_CONDITIONAL_OFFER_END_DATE_PAST
								.getCode());
						response.setResults(results);
						isError = true;
					}
				}catch (ParseException e) {
					results = Results.getError(
							ResultsCode.SO_CONDITIONAL_OFFER_INVALID_END_DATE
							.getMessage(),
							ResultsCode.SO_CONDITIONAL_OFFER_INVALID_END_DATE
							.getCode());
					response.setResults(results);
					isError = true;
				}
			}
			//check if serviceDate2 is less than serviceDate1
			if(!isError && null!= serviceDate1 && null!= serviceDate2){
				count = serviceDate2.compareTo(serviceDate1);
				if(count<0){
					results = Results.getError(
							ResultsCode.SO_CONDITIONAL_OFFER_END_DATE_PAST_THAN_START_DATE
							.getMessage(),
							ResultsCode.SO_CONDITIONAL_OFFER_END_DATE_PAST_THAN_START_DATE
							.getCode());
					response.setResults(results);
					isError = true;
				}
			}
			// check whether offerExpirationDate is in desired format yyyy-MM-dd'T'HH:mm:ss
			if(!isError && null!= offerVO.getOfferExpirationDate()){
				try{
					CommonUtility.sdfToDate.parse(offerVO.getOfferExpirationDate());
				}catch (ParseException e) {
					results = Results.getError(
							ResultsCode.SO_CONDITIONAL_OFFER_INVALID_OFFER_EXPIRE_DATE
							.getMessage(),
							ResultsCode.SO_CONDITIONAL_OFFER_INVALID_OFFER_EXPIRE_DATE
							.getCode());
					response.setResults(results);
					isError = true;
				}
			}
			// check whether spendLimit is a double value
			if(!isError && null!= offerVO.getSpendLimit()){
				try{
					Double.parseDouble(offerVO.getSpendLimit());
				}catch (NumberFormatException e) {
					results = Results.getError(
							ResultsCode.SO_CONDITIONAL_OFFER_INVALID_SPEND_LIMIT
							.getMessage(),
							ResultsCode.SO_CONDITIONAL_OFFER_INVALID_SPEND_LIMIT
							.getCode());
					response.setResults(results);
					isError = true;
				}
			}
			//check whether it is a CAR routed SO
			if (!isError && mobileGenericBO.isCARroutedSO(offerVO.getSoId())) {
				results = Results.getError(ResultsCode.SO_CONDITIONAL_CAR_SO.getMessage(), ResultsCode.SO_CONDITIONAL_CAR_SO.getCode());
				response.setResults(results);
				isError = true;
			}
			//check whether order is in routed status
			if(!isError && OrderConstants.ROUTED_STATUS != offerVO.getSo().getWfStateId()){
				results = Results.getError(
						ResultsCode.SO_CONDITIONAL_OFFER_STATUS_NOTMATCH
						.getMessage(),
						ResultsCode.SO_CONDITIONAL_OFFER_STATUS_NOTMATCH
						.getCode());
				response.setResults(results);
			}
			// check whether non funded buyer has spend limit
			if(!isError && mobileGenericBO.checkNonFunded(offerVO.getSoId()) && (offerVO.getSpendLimit()!=null)){
				results = Results
						.getError(
								ResultsCode.SO_CONDITIONAL_COUNTER_OFFER_NOT_ALLOWED_FOR_NON_FUNDED
								.getMessage(),
								ResultsCode.SO_CONDITIONAL_COUNTER_OFFER_NOT_ALLOWED_FOR_NON_FUNDED
								.getCode());
				response.setResults(results);
				isError = true;
			}

			//check whether SO is routed to the resource in the list
			if(!isError){
				List<Integer> resourceIdList = new ArrayList<Integer>();
				if(null!=offerVO.getResourceIds() 
						&& null != offerVO.getResourceIds().getResourceId() 
						&& offerVO.getResourceIds().getResourceId().size()>0){
					resourceIdList = offerVO.getResourceIds().getResourceId();
				}
				for(Integer resourceId: resourceIdList){
					boolean isRoutedToResource=false;
					for (RoutedProvider routedProvider : offerVO.getSo().getRoutedResources()) {
						if (firmId.equals(routedProvider.getVendorId()) && resourceId.equals(routedProvider.getResourceId())) {
							isRoutedToResource=true;

							//check whether a counter offer is already placed by the resource for this so
							if(!isError && (null != routedProvider.getProviderRespId() 
									&& PublicMobileAPIConstant.PROVIDER_RESP_ID_COUNTER_OFFER_PLACED.equals(routedProvider.getProviderRespId()))){
								results = Results.getError(
										ResultsCode.SO_CONDITIONAL_OFFER_EXISTS
										.getMessage(),
										ResultsCode.SO_CONDITIONAL_OFFER_EXISTS
										.getCode());
								response.setResults(results);
								isError = true;
								break;

							}
						}
					}
					if(!isRoutedToResource){
						results = Results.getError(
								ResultsCode.SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH
								.getMessage(),
								ResultsCode.SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH
								.getCode());
						response.setResults(results);
					}
					if(isError){
						break;
					}
				}
			}
			//check whether both Start Date and max price is empty
			if (!isError && offerVO.getServiceDateTime1() == null
					&& offerVO.getSpendLimit() == null) {
				results = Results
						.getError(
								ResultsCode.SO_CONDITIONAL_START_DATE_OR_SPEND_LIMIT_REQUIRED
								.getMessage(),
								ResultsCode.SO_CONDITIONAL_START_DATE_OR_SPEND_LIMIT_REQUIRED
								.getCode());
				response.setResults(results);
				isError = true;
			}
			//check whether counter offer max price is negative
			if (!isError && (offerVO.getSpendLimit() != null)
					&& (new Double(offerVO.getSpendLimit()) < 0.00)) {
				results = Results
						.getError(
								ResultsCode.SO_CONDITIONAL_OFFER_NEGATIVE_SPEND_LIMIT
								.getMessage(),
								ResultsCode.SO_CONDITIONAL_OFFER_NEGATIVE_SPEND_LIMIT
								.getCode());
				response.setResults(results);
				isError = true;
			} 
			//check whether offer expiration date is empty
			if (offerVO.getOfferExpirationDate() == null) {
				results = Results
						.getError(
								ResultsCode.SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED
								.getMessage(),
								ResultsCode.SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED
								.getCode());
				response.setResults(results);
				isError = true;
			}
			//check whether service date end is present and service date start is empty
			if(!isError && offerVO.getServiceDateTime1() == null 
					&& offerVO.getServiceDateTime2() != null){
				results = Results.getError(
						ResultsCode.SO_CONDITIONAL_OFFER_PROVIDE_START_DATE
						.getMessage(),
						ResultsCode.SO_CONDITIONAL_OFFER_PROVIDE_START_DATE
						.getCode());
				response.setResults(results);
				isError = true;
			}
			//check whether the conditional offer upper limit is lesser than the spend_limit_labour in so_hdr
			if (!isError && null!=offerVO.getSpendLimit() && null!=offerVO.getSo()) {
				Double increasedCounterofferPrice =   Double.parseDouble(offerVO.getSpendLimit());
				Double maximumSOPrice = 0.0;
				if(null!=offerVO.getSo().getSpendLimitLabor()){
				 maximumSOPrice = offerVO.getSo().getSpendLimitLabor();
				}
				if(null!= offerVO.getSo().getSpendLimitParts()){
					maximumSOPrice +=  offerVO.getSo().getSpendLimitParts();
				}
				//rounding off to two decimal places
				DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL);      
				maximumSOPrice = Double.valueOf(df.format(maximumSOPrice));
				if(null!=increasedCounterofferPrice && ((increasedCounterofferPrice.doubleValue()) <= maximumSOPrice)){
					results = Results.getError(ResultsCode.SO_CONDITIONAL_SPEND_LIMIT_VALUE_SMALL.getMessage(), ResultsCode.SO_CONDITIONAL_SPEND_LIMIT_VALUE_SMALL.getCode());
					response.setResults(results);
					isError = true;
				}
			}
		}else{
			results = Results.getError(
					ResultsCode.SO_CONDITIONAL_OFFER_INVALID_ROLE
					.getMessage(),
					ResultsCode.SO_CONDITIONAL_OFFER_INVALID_ROLE
					.getCode());
			response.setResults(results);
		}
			return response;
		}catch (Exception e) {
			LOGGER.error("Exception in validating request for validateCounterOffer:"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

	}

	
	/**
	 * Method contains all the validations for withdraw counnter offer
	 * @param offerVO
	 * @param response
	 * @return WithdrawCounterOfferResponse
	 * @throws BusinessServiceException
	 */
	public WithdrawCounterOfferResponse validateWithdrawCounterOffer(CounterOfferVO offerVO, WithdrawCounterOfferResponse response) throws BusinessServiceException{
		Results results = null;
		boolean isError=false;
		Integer firmId= 0;
		if(StringUtils.isNumeric(offerVO.getFirmId())){
			firmId = Integer.parseInt(offerVO.getFirmId());
		}
		try{
			//validating role
			if(!isError && offerVO.getRoleId() !=3){
				results = Results.getError(
						ResultsCode.SO_CONDITIONAL_OFFER_INVALID_ROLE
						.getMessage(),
						ResultsCode.SO_CONDITIONAL_OFFER_INVALID_ROLE
						.getCode());
				response.setResults(results);
				isError = true;
			}
			//check whether order is in routed status
			if(!isError && OrderConstants.ROUTED_STATUS != offerVO.getSo().getWfStateId()){
				results = Results.getError(
						ResultsCode.SO_WITHDRAW_OFFER_STATUS_NOTMATCH
						.getMessage(),
						ResultsCode.SO_WITHDRAW_OFFER_STATUS_NOTMATCH
						.getCode());
				response.setResults(results);
				isError = true;
			}
			//check whether SO is routed to the resource in the list
			//check whether a counter offer is placed by the resource for this so
			if(!isError){
				List<Integer> resourceIdList = new ArrayList<Integer>();
				if(null!=offerVO.getResourceIds() 
						&& null != offerVO.getResourceIds().getResourceId() 
						&& offerVO.getResourceIds().getResourceId().size()>0){
					resourceIdList = offerVO.getResourceIds().getResourceId();
				}
				for(Integer resourceId: resourceIdList){
					boolean isRoutedToResource=false;
					for (RoutedProvider routedProvider : offerVO.getSo().getRoutedResources()) {
						if (firmId.equals(routedProvider.getVendorId()) && resourceId.equals(routedProvider.getResourceId())) {
							isRoutedToResource=true;

							//check whether a counter offer is placed by the resource for this so
							if(!isError && null == routedProvider.getProviderRespId() 
									|| !PublicMobileAPIConstant.PROVIDER_RESP_ID_COUNTER_OFFER_PLACED.equals(routedProvider.getProviderRespId())){
								results = Results.getError(
										ResultsCode.SO_WITHDRAW_NO_COUNTER_OFFER
										.getMessage(),
										ResultsCode.SO_WITHDRAW_NO_COUNTER_OFFER
										.getCode());
								response.setResults(results);
								isError = true;
								break;

							}
						}
					}
					//check whether SO is routed to the resource in the list
					if(!isError && !isRoutedToResource){
						results = Results.getError(
								ResultsCode.SO_WITHDRAW_OFFER_PROVIDER_NOTMATCH
								.getMessage(),
								ResultsCode.SO_WITHDRAW_OFFER_PROVIDER_NOTMATCH
								.getCode());
						response.setResults(results);
						isError = true;
						break;
					}
					if(isError){
						break;
					}
				}
			}
			return response;
		}catch (Exception e) {
			LOGGER.error("Exception in validating request for validateWithdrawCounterOffer:"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

	}
	public ReportProblemVO validateResolveProblem(ReportProblemVO reportProblemVO) throws BusinessServiceException {
		boolean isValidStatus = false;
		String problemDescription =null;
		try{
			isValidStatus = mobileGenericBO.checkIfSOInProblem(reportProblemVO.getSoId());
			if(isValidStatus){
				problemDescription = mobileGenericBO.getProblemTypeDescription(reportProblemVO.getSoId());
				reportProblemVO.setProblemReasonCodeDescription(problemDescription);
				reportProblemVO.setValidationCode(ResultsCode.SUCCESS);
			}else{
				reportProblemVO.setValidationCode(ResultsCode.INVALID_SO_STATUS);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in validating service order status"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return reportProblemVO;
	}

	 /*public MobilePlaceBidResponse validateSOBid(APIRequestVO apiVO,
			MobilePlaceBidRequest request, ServiceOrder so) {
		boolean isError = false;
		MobilePlaceBidResponse response = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// check whether newServiceStartDate is in desired format
		if(null != request.getNewServiceStartDate()){
			try{
				format.parse(request.getNewServiceStartDate() + " " + request.getNewServiceStartTime());
			}catch (ParseException e) {
				response = MobilePlaceBidResponse
						.getInstanceForError(ResultsCode.SO_BID_INVALID_START_DATE);
				isError = true;
			}
		}
		// check whether newServiceEndDate is in desired format
		if(!isError && null != request.getNewServiceEndDate()){
			try{
				if(("Specific").equals(request.getNewServiceDateType())){
					format.parse(request.getNewServiceStartDate() + " " + request.getNewServiceEndTime());
				}else{
					format.parse(request.getNewServiceEndDate() + " " + request.getNewServiceEndTime());
				}
			}catch (ParseException e) {
				response = MobilePlaceBidResponse
						.getInstanceForError(ResultsCode.SO_BID_INVALID_END_DATE);
				isError = true;
			}
		}
		// check whether offerExpirationDate is in desired format
		if(!isError && null != request.getBidExpirationDate()){
			try{
				format.parse(request.getBidExpirationDate() + " " + request.getBidExpirationTime());
			}catch (ParseException e) {
				response = MobilePlaceBidResponse
						.getInstanceForError(ResultsCode.SO_BID_INVALID_EXPIRATION_DATE);
				isError = true;
			}
		}
		// check whether spendLimit is a double value
		if(!isError && (null == request.getLaborPrice() && (null == request.getLaborHourlyRate() || null == request.getLaborHours()))){
			response = MobilePlaceBidResponse
					.getInstanceForError(ResultsCode.SO_BID_INVALID_LABOR_PRICE);
			isError = true;

		}
		if(!isError){
			boolean isRoutedToLoggedInProvider=false;
			for (RoutedProvider routedProvider : so.getRoutedResources()) {
				if (apiVO.getResourceId().equals(routedProvider.getResourceId())) {
					isRoutedToLoggedInProvider=true;
				}
			}
			if(!isRoutedToLoggedInProvider){
				response = MobilePlaceBidResponse
						.getInstanceForError(ResultsCode.SO_BID_PROVIDER_NOT_ROUTED);
				isError = true;
			}
		}
		//check whether bid labor and parts price is negative
		if (!isError && ((null != request.getLaborPrice() && request.getLaborPrice() < 0.00) || (null != request.getLaborHourlyRate() && request.getLaborHourlyRate() < 0.00) || (null != request.getPartsPrice() && request.getPartsPrice() < 0.00))) {
			response = MobilePlaceBidResponse
					.getInstanceForError(ResultsCode.SO_BID_NEGATIVE_LABOR_OR_PARTS_PRICE);
			isError = true;
		}
		return response;
	}*/

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	/**
	 * 
	 * @param problemVO
	 * @param so
	 * @param errorOccured
	 * @param validationErrors
	 * @return
	 * @throws BusinessServiceException
	 * Method to validate the resource against resources under the firm routed to the so
	 */
	private boolean validateResourceUnderFirm(AssignVO assignVO,List<ErrorResult> validationErrors)throws BusinessServiceException{

		boolean errorOccured = false;
		boolean isValidResource = false ;

		Integer providerResourceId = assignVO.getResourceId();

		if(null != assignVO.getSoId() && null != assignVO.getFirmId()){
			try{			

				List<Integer> resourcesUnderFirm = mobileGenericBO.getRoutedResourcesUnderFirm(assignVO);
				for(Integer resource : resourcesUnderFirm){
					if(resource.intValue() == providerResourceId.intValue()){
						isValidResource =true;
						break;
					}
				}
				if(!isValidResource){
					errorOccured = true;
					addError(ResultsCode.RESOURCE_NOT_UNDER_FIRM.getMessage(),ResultsCode.RESOURCE_NOT_UNDER_FIRM.getCode(),validationErrors);
				}

			}
			catch(Exception e){
				LOGGER.error("Exception in validating assign so provider"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
		}
		return errorOccured;
	}

	/**
	 * Method to check the logged in user is a level2 provider
	 * @param assignVO
	 * @param validationErrors
	 * @return
	 * @throws BusinessServiceException
	 */
	private boolean validateResourceRole(AssignVO assignVO , List<ErrorResult> validationErrors)throws BusinessServiceException{
		boolean errorOccured = false;
		if(MPConstants.ASSIGN_SO.equals(assignVO.getRequestFor())||MPConstants.REASSIGN_SO.equals(assignVO.getRequestFor())){
			if(MPConstants.ROLE_LEVEL_TWO.equals(assignVO.getRoleId().intValue())){
				if(assignVO.getResourceId().intValue()==assignVO.getUrlResourceId().intValue())
				{
					errorOccured = false;
				}
				else
				{
					errorOccured = true;
					addError(ResultsCode.PERMISSION_ERROR.getMessage(),ResultsCode.PERMISSION_ERROR.getCode(),validationErrors);
				}
			}
			else if(MPConstants.ROLE_LEVEL_ONE.equals(assignVO.getRoleId().intValue())){
				errorOccured = true;
				addError(ResultsCode.PERMISSION_ERROR.getMessage(),ResultsCode.PERMISSION_ERROR.getCode(),validationErrors);
			}
			/*else if(MPConstants.ROLE_LEVEL_THREE.equals(assignVO.getRoleId().intValue())){
				//for role level 3,the provider can assign the so to any provider who is there in the routed list under the firm.
			}*/
		}
		return errorOccured;
	}

	public boolean validateRelease(MobileSOReleaseVO releaseVO)throws BusinessServiceException{
		boolean isvalidReleaseRequest = false;
		try{
			isvalidReleaseRequest = mobileGenericBO.checkIfValidReason(releaseVO.getReason());
		}catch (Exception e) {
			LOGGER.error("Exception in validating reason code"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return isvalidReleaseRequest;
	}


	public ForgetUnamePwdServiceResponse validateForgetUnamePwdServiceForPassword(final ForgetUnamePwdVO forgetUnamePwdVO, ForgetUnamePwdServiceResponse response){
		Results results = new Results();
		boolean isError = false;

					// check whether user profile exist
					if(!isError && !forgetUnamePwdVO.isUserProfileExists()){
						results = Results.getError(
								ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
								.getMessage(),
								ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
								.getCode());
						response.setResults(results);
						isError = true;
					}

					// check whether user is not provider
					if(!isError && !(forgetUnamePwdVO.getUserId().equalsIgnoreCase(PublicMobileAPIConstant.PROVIDER_ROLE_ID))){
						results = Results.getError(
								ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
								.getMessage(),
								ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
								.getCode());
						response.setResults(results);
						isError = true;
					}
					
					// check whether user profile is locked
					if(!isError && PublicMobileAPIConstant.LOCKED_IND== forgetUnamePwdVO.getLockedInd()){
						results = Results.getError(
								ResultsCode.FORGET_UNAME_PWD_LOCKED_USER
								.getMessage(),
								ResultsCode.FORGET_UNAME_PWD_LOCKED_USER
								.getCode());
						response.setResults(results);
						isError = true;
					}

					// check whether pwd ind is -1, then return email not found error
					if(!isError && PublicMobileAPIConstant.NEGATIVE_ONE== forgetUnamePwdVO.getPwdInd()){
						results = Results.getError(
								ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
								.getMessage(),
								ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
								.getCode());
						response.setResults(results);
						isError = true;
					}
					
					// check whether VerificationCount exceeded maximum limit, then ask for additional verification
					if(!isError){
						final int maxSecretQuestionAttempts = Integer
								.parseInt(PropertiesUtils
										.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));
						if (forgetUnamePwdVO.getVerificationCount() >= maxSecretQuestionAttempts || forgetUnamePwdVO.getVerificationCount() == -1) {
							results = Results.getError(
									ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP
									.getMessage(),
									ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP
									.getCode());
							response.setResults(results);
							isError = true;
						}
					}
					
		return response;
	}

	private boolean isEmailAddressValid(String emailAddress) {
		if (emailAddress != null) {
			Pattern email = Pattern.compile("^\\S+@\\S+$");
			Matcher fit = email.matcher(emailAddress);
			if (fit.matches())
				return true;
		}
		return false;
	}
	public RescheduleVO validateRescheduleRespond(RescheduleVO rescheduleVo) throws BusinessServiceException{
		try{
				rescheduleVo = mobileGenericBO.checkIfRescheduleRequestExists(rescheduleVo);
				if(null!= rescheduleVo && rescheduleVo.isValidSignal()){
					rescheduleVo.setValidationCode(ResultsCode.SUCCESS);
				}else{
					rescheduleVo.setValidationCode(ResultsCode.RESPOND_RESCHEDULE_NOT_ALLOWED);
				}
		}catch (Exception e) {
			LOGGER.error("Exception in validating respond type of reschedule request"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return rescheduleVo;
	}

	/**
	 * This method converts the given date to GMT or given timezone format
	 * 
	 * @param timeZoneDate
	 *            Date
	 * @param TimeZone
	 *            String
	 * @return Date
	 */
	private static Date getDateinGMT(Date timeZoneDate) {
		Calendar cal = Calendar.getInstance();
		TimeZone timeZone = cal.getTimeZone();

		Timestamp ts = new Timestamp(timeZoneDate.getTime());

		Date gmtDate = TimeUtils.convertTimeToGMT(ts, timeZone.getID());
		return gmtDate;
	}
	
	/**
	 * 
	 * @param timeZone
	 * @param rescheduleVO
	 * @return
	 * method to validate reschedule request
	 */
	public MobileSOSubmitRescheduleResponse validateSubmitReschedule(String timeZone,SOSchedule rescheduleVO,MobileSOSubmitRescheduleResponse rescheduleResponse,String soId) {

		boolean isValidWfStateId = false;
		Integer wfstateId = null;
		try {
			wfstateId =mobileGenericBO.getServiceOrderStatus(soId);
		} catch (BusinessServiceException e) {
			LOGGER.error("Exception in getting wf state id"+ e.getMessage());
		}
		Results results = null;
		boolean errorOccured = false;
		
		List<Integer> validWfStates =Arrays.asList(MPConstants.WF_VALID_STATES_SUBMIT_RESCHEDULE);
		
		if((null != wfstateId)&& !(validWfStates.isEmpty())){
			isValidWfStateId = validWfStates.contains(wfstateId);
		}
		
		if(!isValidWfStateId){
			results= Results.getError(
					ResultsCode.RESCHEDULE_WF_STATE_INVALID
					.getMessage(),
					ResultsCode.RESCHEDULE_WF_STATE_INVALID
					.getCode());
			rescheduleResponse.setResults(results);
			errorOccured = true;
		}
		//making the validation in sync with FE, ie now date will consider seconds. CC-1067
		java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
		
		Timestamp newStartDate1 = null;	
		Timestamp newStartTimeCombined = null;
		if (!errorOccured) {
			newStartDate1 = new Timestamp(rescheduleVO.getServiceDate1().getTime());
			newStartTimeCombined = new Timestamp(combineDateAndTime(newStartDate1, rescheduleVO.getServiceTimeStart(),timeZone).getTime());
			if(newStartTimeCombined.compareTo(now) < 0) {
					results= Results.getError(
					ResultsCode.RESCHEDULE_WRONG_START_DATE
					.getMessage(),
					ResultsCode.RESCHEDULE_WRONG_START_DATE
					.getCode());
			rescheduleResponse.setResults(results);
			errorOccured = true;
			}
	    }
		
		
		if (!errorOccured &&  rescheduleVO.isRequestedServiceTimeTypeDateRange() && null == rescheduleVO.getServiceDate2()) {
			results= Results.getError(
					ResultsCode.RESCHEDULE_END_DATE_ABSENT
					.getMessage(),
					ResultsCode.RESCHEDULE_END_DATE_ABSENT
					.getCode());
			rescheduleResponse.setResults(results);
			errorOccured = true;
		}
		if (!errorOccured && rescheduleVO.getServiceDate2() != null && rescheduleVO.isRequestedServiceTimeTypeDateRange()) {

			Timestamp newEndDate1 = new Timestamp(rescheduleVO.getServiceDate2().getTime());

			Timestamp newEndTimeCombined = new Timestamp(combineDateAndTime(newEndDate1, rescheduleVO.getServiceTimeEnd(), timeZone).getTime());

			if (newStartTimeCombined!=null && newStartTimeCombined.compareTo(newEndTimeCombined) >= 0) {	
				results= Results.getError(
						ResultsCode.RESCHEDULE_WRONG_END_DATE
						.getMessage(),
						ResultsCode.RESCHEDULE_WRONG_END_DATE
						.getCode());
				rescheduleResponse.setResults(results);
				errorOccured = true;
			}
		}
		return rescheduleResponse;
	}


	private static Date combineDateAndTime(Timestamp ts, String tm, String zone){
		if(zone == null){
			zone = "EST";
		}
		Calendar cal = null;
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		String timeType = null;
		DateFormat sdf_yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf_yyyymmdd.format(ts);
		if (dt != null && tm != null && tm.length()==8 && zone != null) {
			try {
				year = Integer.parseInt(dt.substring(0, 4));
				month = Integer.parseInt(dt.substring(5, 7));
				dayOfMonth = Integer.parseInt(dt.substring(8, 10));

				hours = Integer.parseInt(tm.substring(0, 2));
				min = Integer.parseInt(tm.substring(3, 5));
				sec = Integer.parseInt("00");
				timeType = tm.substring(6, 8);

			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			TimeZone tz = TimeZone.getTimeZone(zone);

			cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);

			cal.setTimeZone(tz);
			if(timeType.equalsIgnoreCase("AM")){
				cal.set(Calendar.AM_PM,Calendar.AM);
			}else if(timeType.equalsIgnoreCase("PM")){
				cal.set(Calendar.AM_PM, Calendar.PM);
			}
		}
		return cal.getTime();

	}

	public ProcessResponse validateRejectRequest(
			MobileSORejectVO rejectRequestVO) {

		return mobileGenericBO.validateRejectRequest(rejectRequestVO);
	}

	/**
	 * @param updateScheduleDetailsRequest
	 * 
	 * to validate pre call/confirm appointment request
	 * @param soId 
	 * @return 
	 */
	/**
	 * @param updateScheduleDetailsRequest
	 * @param soId
	 * @return
	 */
	/*public UpdateScheduleDetailsResponse validateUpdateScheduleRequest(
			UpdateScheduleDetailsRequest updateScheduleDetailsRequest, String soId) {
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse= null;
		Boolean custAvailableFlag = updateScheduleDetailsRequest.getCustomerAvailableFlag();
		Integer custNotAvailableReason = updateScheduleDetailsRequest.getCustNotAvailableReasonCode();
		String source = updateScheduleDetailsRequest.getSource();
		try{

			if(!custAvailableFlag){
				updateScheduleDetailsResponse = validateCustNotAvailableReason(custNotAvailableReason);

			}
			if(null == updateScheduleDetailsResponse){
				updateScheduleDetailsResponse = validateServiceOrder(soId,source);
			}
			if(null == updateScheduleDetailsResponse){
				updateScheduleDetailsResponse = validateServiceOrderAssignment(soId);
			}
			if(null == updateScheduleDetailsResponse && custAvailableFlag){
				updateScheduleDetailsResponse = validateServiceOrderConfirmedAppointment(soId,updateScheduleDetailsRequest);
			}
		}
		catch(BusinessServiceException e){
			LOGGER.error("Exception inside validateUpdateScheduleRequest"+soId);
		}
		return updateScheduleDetailsResponse;

	}*/

	/**
	 * @param updateScheduleDetailsRequest 
	 * @param soId 
	 * @return
	 * method to check if appointment is ocnfirmed for a today order when customer is available.
	 */
/*	private UpdateScheduleDetailsResponse validateServiceOrderConfirmedAppointment(String soId, UpdateScheduleDetailsRequest updateScheduleDetailsRequest) {
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse = null;
		boolean serviceOrderNotConfirmApptInd =false;
		Integer custAvailableRespCode = updateScheduleDetailsRequest.getCustAvailableResponseReasonCode();
		try {
			serviceOrderNotConfirmApptInd = mobileGenericBO.validateServiceOrderConfirmedAppointment(soId,custAvailableRespCode);
			if(serviceOrderNotConfirmApptInd){
				Results results = Results.getError(
						ResultsCode.SERVICE_ORDER_NOT_CONFIRMED_BUT_TODAY
						.getMessage(),
						ResultsCode.SERVICE_ORDER_NOT_CONFIRMED_BUT_TODAY
						.getCode());
				updateScheduleDetailsResponse = new UpdateScheduleDetailsResponse();
				updateScheduleDetailsResponse.setResults(results);
			}
		} catch (BusinessServiceException e) {
			LOGGER.error("Exception inside validateServiceOrderAssignment"+soId);
		}
		return updateScheduleDetailsResponse;
	}*/

	/**
	 * @param soId
	 * @param source
	 * @return
	 * to validate if the service order is assigned, if service order is scheduled for today
	 */
/*	private UpdateScheduleDetailsResponse validateServiceOrderAssignment(
			String soId) {
		boolean serviceOrderNotAssignedAndTodayInd =false;
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse = null;
		try {
			serviceOrderNotAssignedAndTodayInd = mobileGenericBO.validateServiceOrderAssignment(soId);
			if(serviceOrderNotAssignedAndTodayInd){
				Results results = Results.getError(
						ResultsCode.SERVICE_ORDER_NOT_ASSIGNE_BUT_TODAY
						.getMessage(),
						ResultsCode.SERVICE_ORDER_NOT_ASSIGNE_BUT_TODAY
						.getCode());
				updateScheduleDetailsResponse = new UpdateScheduleDetailsResponse();
				updateScheduleDetailsResponse.setResults(results);
			}
		} catch (BusinessServiceException e) {
			LOGGER.error("Exception inside validateServiceOrderAssignment"+soId);
		}
		return updateScheduleDetailsResponse;

	}*/

	/**
	 * @param custNotAvailableReason
	 * @return
	 */
/*	private UpdateScheduleDetailsResponse validateCustNotAvailableReason(
			Integer custNotAvailableReason) {
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse= null;
		if(null == custNotAvailableReason){
			Results results = Results.getError(
					ResultsCode.INVALID_CUST_UNAVILABLE_REASON
					.getMessage(),
					ResultsCode.INVALID_CUST_UNAVILABLE_REASON
					.getCode());
			updateScheduleDetailsResponse = new UpdateScheduleDetailsResponse();
			updateScheduleDetailsResponse.setResults(results);
		}
		return updateScheduleDetailsResponse;

	}*/

/*	private UpdateScheduleDetailsResponse validateServiceOrder(String soId, String source) throws BusinessServiceException{
		UpdateScheduleDetailsResponse updateScheduleDetailsResponse= null;
		try {
			if(!mobileGenericBO.validateServiceOrder(soId,source)){
				updateScheduleDetailsResponse = new UpdateScheduleDetailsResponse();
				ArrayList<Object> errorArgumentList = new ArrayList<Object>();
				if(MPConstants.PRE_CALL.equals(source)){
					errorArgumentList.add(MPConstants.PRE_CALL_ACTION);
				}
				else if(MPConstants.CONFIRM_APPOINTMENT.equals(source)){
					errorArgumentList.add(MPConstants.CONFIRM_APPOINTMENT_ACTION);
				}
				Results results = Results.getError(
						ResultsCode.SERVICE_ORDER_NOT_READY_FOR_ACTION
						.getMessage(errorArgumentList),
						ResultsCode.SERVICE_ORDER_NOT_READY_FOR_ACTION
						.getCode());
				updateScheduleDetailsResponse.setResults(results);
			}
		} catch (BusinessServiceException e) {
			throw e;
		}
		return updateScheduleDetailsResponse;

	}*/
	/**
	 * Method to validate the sodetails and to check whether the logged in user is authorized to view the So details
	 * @param detailsVO
	 * @param soGetResponse
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public SOGetMobileDetailsResponse validateSODetails(SoDetailsVO detailsVO)throws BusinessServiceException {

		Results results = null;
		boolean validationTrue = true;
		boolean isAuthorizedToViewSODetails = false;
		SOGetMobileDetailsResponse soGetResponse = new SOGetMobileDetailsResponse();
		Integer firmId;

		try {
			if (StringUtils.isBlank(detailsVO.getProviderId().toString())) {

				results = Results.getError(
						ResultsCode.INVALID_RESOURCE_ID
						.getMessage(),
						ResultsCode.INVALID_RESOURCE_ID.getCode());
				soGetResponse.setResults(results);
				
			}
			if(validationTrue){
				firmId = mobileGenericBO.validateProviderId(detailsVO.getProviderId());
				if (null == firmId) {			
					results = Results.getError(
							ResultsCode.SO_SEARCH_INVALID_PROVIDER
							.getMessage(),
							ResultsCode.SO_SEARCH_INVALID_PROVIDER
							.getCode());
					soGetResponse.setResults(results);			
					
				}
			}
			if(validationTrue){
				if(null != detailsVO){
					isAuthorizedToViewSODetails = mobileGenericBO.isAuthorizedToViewSODetails(detailsVO);
					if(!isAuthorizedToViewSODetails){
						results = Results.getError(
								ResultsCode.PERMISSION_ERROR
								.getMessage(), 
								ResultsCode.PERMISSION_ERROR
								.getCode());
						soGetResponse.setResults(results);	
						
					}
				}
			}
		} catch (BusinessServiceException e) {			
			LOGGER.error("Exception occured in validateSODetails "+ e.getMessage());
			throw e;
		}
		return soGetResponse;

	}

	public ProviderParamVO validateGetSoRequest(ProviderParamVO providerParamVO) throws BusinessServiceException{
		ResultsCode validationCode = ResultsCode.SUCCESS;
		try{
			//Validate PageNo 
			if(null == providerParamVO.getPageNo()){
				validationCode = ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER;
			}
			if(validationCode.equals(ResultsCode.SUCCESS)){
			   //Validate pageSize
				if(null== providerParamVO.getPageSize()){
					validationCode = ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_SIZE;
					
				}else{
					// Validate Limit --> 250
					if(providerParamVO.getPageSize().intValue() > MPConstants.PAGE_SIZE_LIMIT ){
						validationCode = ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_SIZE;
					}
				}
			}
			//Validate So Status
			if(null!= providerParamVO && StringUtils.isNotBlank(providerParamVO.getSoStatus())&& validationCode.equals(ResultsCode.SUCCESS)){
				validationCode = validateSoStatus(providerParamVO); 
			}// SoStatus cannot be null,we are setting DEFAULT Active status.
		}catch (Exception e) {
			LOGGER.error("Exception in validating request for getProviderSoList "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		// Setting Validation code after validating pageNo,pageSize and soStatus.
		if(!validationCode.equals(ResultsCode.SUCCESS)){
		   providerParamVO.setValidationCode(validationCode);
		}
		return providerParamVO;
	}

	private ResultsCode validateSoStatus(ProviderParamVO providerParamVO) throws BusinessServiceException {
		//Declaring variables
		List<String> soStatusList = null;
		List<String> validStatusList = null;
		String soStatus = null;
		ResultsCode invalidSoStatus = ResultsCode.SUCCESS;
		List<Integer> WfStatusList = null;

		try{
			soStatus = providerParamVO.getSoStatus();
			// To convert the underscore separated status to List of Status
			soStatusList = new ArrayList<String>(Arrays.asList(soStatus.split(MPConstants.UNDERSCORE)));
			validStatusList = new ArrayList<String>(Arrays.asList(MPConstants.VALID_STATUS_ARRAY));
			//Checking the soStaus is valid one -->('Active','Accepted','Problem');
			if(!(validStatusList.containsAll(soStatusList))){
				invalidSoStatus=ResultsCode.PROVIDER_SO_SEARCH_INVALID_STATUS;
			}else{
				WfStatusList = mobileGenericBO.getServiceOrderStatus(soStatusList);
			    providerParamVO.setWfStatusIdList(WfStatusList);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in validating So status in request "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return invalidSoStatus;
	}

	/**
	 * Validate whether the role id of provider is 1, if so no permission error
	 * @param roleId
	 * @return RecievedOrdersResponse
	 */
	public Results validateGetRecievedOrders(RecievedOrdersCriteriaVO criteriaVO, Integer countOfSO, boolean validateAfterCount){
		boolean isError = false;
		Results results = null;
		double lastPage = 0.0;
		
		if(!validateAfterCount){
		if(criteriaVO.getRoleId() == 1){
			results = Results.getError(ResultsCode.PERMISSION_ERROR.getMessage(),
					ResultsCode.PERMISSION_ERROR.getCode());
			isError = true;
		}

		//Validate PageNo 
		//validate Limit-- pageNo > 25
		/*if(!isError && criteriaVO.getPageNo() > MPConstants.PAGE_NO_LIMIT){
			results = Results.getError(ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER.getMessage(),
					ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER.getCode());
			isError = true;
		}*/
		//Validate PageNo 
		//validate Limit-- pageSize > 250
		if(!isError && criteriaVO.getPageSize() > MPConstants.PAGE_SIZE_LIMIT){
			results = Results.getError(ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_SIZE.getMessage(),
					ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_SIZE.getCode());
			isError = true;
		}		

		}else{
		//no so available for provider
		if(!isError){
			if(countOfSO == 0){
				results = Results.getError(ResultsCode.RECIEVED_ORDERS_NO_SO.getMessage(),
						ResultsCode.RECIEVED_ORDERS_NO_SO.getCode());
				isError = true;
			}
		}

		//page doesnot exist
		if(!isError){
			if(null!= countOfSO && countOfSO.intValue()> 0){
				// Checking the last page No from request and validate 
				lastPage = Math.ceil((double) countOfSO/ criteriaVO.getPageSize()); 
				if(criteriaVO.getPageNo()>lastPage){
					results = Results.getError(ResultsCode.RECIEVED_ORDERS_PAGENO_NOT_EXIST.getMessage(),
							ResultsCode.RECIEVED_ORDERS_PAGENO_NOT_EXIST.getCode());
					isError = true;
				}
			}
		}
		}
		return results;
	}
	
	
	/**
	 * validate whether filterId to delete is present and is a valid one
	 * @param filterId
	 * @return DeleteFilterResponse
	 */
	public DeleteFilterResponse validateDeleteFilterParam(final String filterId){
		DeleteFilterResponse response = null;
		//check whether filterId is blank
		if(StringUtils.isBlank(filterId)){
			response= DeleteFilterResponse.getInstanceForError(ResultsCode.DELETE_FILTER_FILTER_ID_NO_INVALID_FILTERID_ERROR);
		}
		//check whether filter Id is an Integer
		if(null == response){
			try{
				Integer.parseInt(filterId);
			}catch (NumberFormatException e) {
				response= DeleteFilterResponse.getInstanceForError(ResultsCode.DELETE_FILTER_FILTER_ID_NO_INVALID_FILTERID_ERROR);
			}
		}
		return response;
				
	}

	/**
	 * @param mobileSOAdvanceSearchRequest
	 * @param securityContext
	 * @param roleId 
	 * @return
	 */
	public MobileSOAdvanceSearchResponse validateAdvanceSearchRequest(
			MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest,
			SecurityContext securityContext, Integer roleId) {
		MobileSOAdvanceSearchResponse providerSOSearchResponse = null;
		Results results = null;
		Integer pageSize = mobileSOAdvanceSearchRequest.getPageSize();
		// validating logged in resource id
		if (securityContext == null) {
			results = Results.getError(
					ResultsCode.INVALID_RESOURCE_ID.getMessage(),
					ResultsCode.INVALID_RESOURCE_ID.getCode());

			providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
			providerSOSearchResponse.setResults(results);

		}
		// validating page size

		else if (validatePageSize(pageSize)) {
			results = Results
					.getError(
							ResultsCode.SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE
							.getMessage(),
							ResultsCode.SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE
							.getCode());
			providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
			providerSOSearchResponse.setResults(results);
		}
		// If role level is 1, recieved status is not permitted in the request.
		else if (validateStatusInSearchRequest(mobileSOAdvanceSearchRequest, roleId)){
			results = Results
					.getError(
							ResultsCode.PERMISSION_ERROR
							.getMessage(),
							ResultsCode.PERMISSION_ERROR
							.getCode());
			providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
			providerSOSearchResponse.setResults(results);
		}
		// validate if start and end range is present when appointment filter is Range
		else if (validateAppointRange(mobileSOAdvanceSearchRequest)){
			results = Results
					.getError(
							ResultsCode.APPOINTMENT_SEARCH_ERROR
							.getMessage(),
							ResultsCode.APPOINTMENT_SEARCH_ERROR
							.getCode());
			providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
			providerSOSearchResponse.setResults(results);
		}
		// to validate appointment range date values whether end range is before start range
		else if (validateAppointRangeValue(mobileSOAdvanceSearchRequest)){
			results = Results
					.getError(
							ResultsCode.APPOINTMENT_SEARCH_VALUE_ERROR
							.getMessage(),
							ResultsCode.APPOINTMENT_SEARCH_VALUE_ERROR
							.getCode());
			providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
			providerSOSearchResponse.setResults(results);
		}
		return providerSOSearchResponse;
	}
	
	/**
	 * @param mobileSOAdvanceSearchRequest
	 * @return
	 * to validate appointment range date values whether end range is before start range
	 */
	private boolean validateAppointRangeValue(
			MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest) {
		boolean errorOccured = false;
		if (null != mobileSOAdvanceSearchRequest
				&& null != mobileSOAdvanceSearchRequest
						.getAdvanceSearchCriteria()
				&& null != mobileSOAdvanceSearchRequest
						.getAdvanceSearchCriteria().getAppointment()
				&& null != mobileSOAdvanceSearchRequest
						.getAdvanceSearchCriteria().getAppointment()) {

			Appointment appointment = mobileSOAdvanceSearchRequest
					.getAdvanceSearchCriteria().getAppointment();
			String startRange = appointment.getStartRange();
			String endRange = appointment.getEndRange();
			Date startRangeDate = DateUtils.defaultFormatStringToDate(startRange);
			Date endRangeDate = DateUtils.defaultFormatStringToDate(endRange);
			if(endRangeDate.before(startRangeDate)){
				errorOccured = true;
			}
		}
		return errorOccured;
	}

	/**
	 * @param mobileSOAdvanceSearchRequest
	 * @return
	 * validate whether start range and end range is available in request
	 */
	private boolean validateAppointRange(
			MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest) {
		boolean errorOccured = false;
		if (null != mobileSOAdvanceSearchRequest
				&& null != mobileSOAdvanceSearchRequest
						.getAdvanceSearchCriteria()
				&& null != mobileSOAdvanceSearchRequest
						.getAdvanceSearchCriteria().getAppointment()
				&& null != mobileSOAdvanceSearchRequest
						.getAdvanceSearchCriteria().getAppointment()) {

			Appointment appointment = mobileSOAdvanceSearchRequest
					.getAdvanceSearchCriteria().getAppointment();
			if (MPConstants.APPOINTMENT_VALUE_RANGE.equalsIgnoreCase(appointment
					.getAppointmentFilter())
					&& (StringUtils.isBlank(appointment.getStartRange()) || StringUtils
							.isBlank(appointment.getEndRange()))) {
				errorOccured = true;
			}
		}
		return errorOccured;
	}

	/**
	 * @param pageSize
	 * @return
	 * validate page size is 10,20,50,100,200,250
	 */
	private boolean validatePageSize(Integer pageSize){
		boolean errorOccured = false;
		if (null !=pageSize && !PublicMobileAPIConstant.PAGE_SIZE_SET_VALUES.contains(pageSize)){
			errorOccured =true;
		}
		return errorOccured;
	}
	
	/**
	 * @param pageSize
	 * @return
	 * role level 1 cannot search for recieved orders
	 */
	private boolean validateStatusInSearchRequest(MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest,Integer roleId){
		boolean errorOccured = false;
		if (
				null != mobileSOAdvanceSearchRequest
				&& null != mobileSOAdvanceSearchRequest.getAdvanceSearchCriteria()
				&& null != mobileSOAdvanceSearchRequest.getAdvanceSearchCriteria()
				.getStatuses()
				&& null != mobileSOAdvanceSearchRequest.getAdvanceSearchCriteria()
				.getStatuses().getValue()
				&& !mobileSOAdvanceSearchRequest.getAdvanceSearchCriteria().getStatuses()
				.getValue().isEmpty()) {

			List<Integer> statuses = mobileSOAdvanceSearchRequest.getAdvanceSearchCriteria().getStatuses()
					.getValue();
			if(PublicMobileAPIConstant.ROLE_LEVEL_ONE.intValue() == roleId.intValue() && statuses.contains(OrderConstants.ROUTED_STATUS)){
				errorOccured = true;
			}
		}
		return errorOccured;
	}
	
	public SaveFilterResponse validateSaveFilter(final SaveFilterRequest request, final Integer resourceId) throws BusinessServiceException{
		SaveFilterResponse response = null;
		boolean isError = false;
		
		try{
			
			//validate if any one of search criteria is present
			if(!isError){
				if(null == request.getFilterCriterias() || 
						(null == request.getFilterCriterias().getMarkets() &&
						 null == request.getFilterCriterias().getServiceProIds() &&
						 null == request.getFilterCriterias().getStatuses() &&
						 null == request.getFilterCriterias().getSubStatuses() && 
						 null == request.getFilterCriterias().getAppointment() &&
						 null == request.getFilterCriterias().getFlaggedOnlyInd() &&
						 null == request.getFilterCriterias().getUnAssignedInd())){
					response= SaveFilterResponse.getInstanceForError(ResultsCode.SAVE_FILTER_CRITERIA_MANDATORY_ERROR);
					isError = true;
				}
			}
			
			//validate appointment contains start and end if type=range and start/end date is of desired format yyyy-MM-dd
			//validate appointment end is greater than start
			if(!isError && null != request.getFilterCriterias() && 
					null != request.getFilterCriterias().getAppointment()){

				Appointment appointment= request.getFilterCriterias().getAppointment();
				String appointmentType = "";
				String startRange = "";
				String endRange = "";
				
				if(null != appointment){
					appointmentType = appointment.getAppointmentFilter();
					startRange = appointment.getStartRange();
					endRange = appointment.getEndRange();
				}

				if(MPConstants.APPOINTMENT_VALUE_RANGE.equals(appointmentType)){
					if(StringUtils.isBlank(startRange) || StringUtils.isBlank(endRange)){
						response= SaveFilterResponse.getInstanceForError(ResultsCode.APPOINTMENT_SEARCH_ERROR);
						isError = true;
					}
					
					if(!isError){
						Date startRangeDate = DateUtils.defaultFormatStringToDate(startRange);
						Date endRangeDate = DateUtils.defaultFormatStringToDate(endRange);
						if(endRangeDate.before(startRangeDate)){
							response= SaveFilterResponse.getInstanceForError(ResultsCode.APPOINTMENT_SEARCH_VALUE_ERROR);
							isError = true;
						}
					}
				}
			}
			//validate if filter name already exists while create filter functionality
			if(!isError && null == request.getFilterId()){
				boolean isFilterExists = mobileGenericBO.isFilterExists(resourceId,request.getFilterName());
				if(isFilterExists){
					response= SaveFilterResponse.getInstanceForError(ResultsCode.SAVE_FILTER_NAME_EXISTS_ERROR);
					isError = true;
				}
			}
			
		}catch (Exception e) {
			LOGGER.error("Exception in validating request"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return response;
	}
	
/*	private boolean isValidDate(String date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		boolean isValid = false;
		
		if(StringUtils.isNotBlank(date)){
			try{
				format.parse(date);
				isValid = true;
			}catch (ParseException e) {
				isValid = false;
			}
		}
		return isValid;
	}*/


	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	public ValidateSecQuestAnsResponse validateSecQuestAnsRequest(
			SecQuestAnsRequestVO secQuestAnsRequestVO) {
		ValidateSecQuestAnsResponse secQuestAnsResponse = null;
		//General Error for both Username/Password
		// check whether user profile exist
		 if(PublicMobileAPIConstant.REQUEST_FOR_USERNAME.equals(secQuestAnsRequestVO.getRequestFor())){
				secQuestAnsResponse = validateForgotUserNameRequest(secQuestAnsRequestVO);
			}
		else if(PublicMobileAPIConstant.REQUEST_FOR_PASSWORD.equals(secQuestAnsRequestVO.getRequestFor())){
			
			secQuestAnsResponse = validateForgotPwdRequest(secQuestAnsRequestVO);
			
		}
		if(null == secQuestAnsResponse){
			secQuestAnsResponse = validateGeneralDetails(secQuestAnsRequestVO);
		}

		return secQuestAnsResponse;
	}

	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	private ValidateSecQuestAnsResponse validateForgotUserNameRequest(
			SecQuestAnsRequestVO secQuestAnsRequestVO) {
	
		ValidateSecQuestAnsResponse secQuestAnsResponse = null;
		Results results =null;
		
		//check whether user id is present
		if(null == secQuestAnsRequestVO.getUserId() || secQuestAnsRequestVO.getUserId().intValue()==PublicMobileAPIConstant.ZERO){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_USERID_REQUIRED
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_USERID_REQUIRED
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		// check whether user profile exist

		else if(!secQuestAnsRequestVO.isUserProfileExists()){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		return secQuestAnsResponse;

	}

	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	private ValidateSecQuestAnsResponse validateForgotPwdRequest(
			SecQuestAnsRequestVO secQuestAnsRequestVO) {
		
		ValidateSecQuestAnsResponse secQuestAnsResponse = null;
		Results results =null;
		
		//check whether userName is present
		if(StringUtils.isBlank(secQuestAnsRequestVO.getUserName())){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_USERNAME_BLANK
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_USERNAME_BLANK
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}

		// check whether user profile exist

		else if(!secQuestAnsRequestVO.isUserProfileExists()){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}

		// check whether pwd ind is -1, then return email not found error
		else if(-1== secQuestAnsRequestVO.getPwdInd()){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		return secQuestAnsResponse;

	}

	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	private ValidateSecQuestAnsResponse validateGeneralDetails(
			SecQuestAnsRequestVO secQuestAnsRequestVO) {
		ValidateSecQuestAnsResponse secQuestAnsResponse = null;
		Results results =null;
	   final int maxSecretQuestionAttempts = Integer
				.parseInt(PropertiesUtils
						.getPropertyValue(Constants.AppPropConstants.MAX_SECRET_QUESTION_ATTEMPTS_LIMIT));
	   int attemptCount = 0;
	   if(null!=secQuestAnsRequestVO.getVerificationAttemptCount()){
		   attemptCount=secQuestAnsRequestVO.getVerificationAttemptCount().intValue();
	   }
		// check whether user profile is locked
		if(1== secQuestAnsRequestVO.getLockedInd()){
					results = Results.getError(
							ResultsCode.FORGET_UNAME_PWD_LOCKED_USER
							.getMessage(),
							ResultsCode.FORGET_UNAME_PWD_LOCKED_USER
							.getCode());
					secQuestAnsResponse = new ValidateSecQuestAnsResponse();
					secQuestAnsResponse.setResults(results);
		}
		//check whether attempt count has exceeded in case of validate security question
		else if (!secQuestAnsRequestVO.isAdditionalVerification() && (attemptCount >= maxSecretQuestionAttempts || attemptCount == -1)) {
			// ask for additional verification
			results = Results.getError(
					ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP
					.getMessage(),
					ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		//check whether emailId is present
		else if(StringUtils.isBlank(secQuestAnsRequestVO.getEmail())){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		//check whether emailId is valid
		else if(!isEmailAddressValid(secQuestAnsRequestVO.getEmail())){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		else if(!(secQuestAnsRequestVO.getEmail().equalsIgnoreCase(secQuestAnsRequestVO.getEmailFromDB()))){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_NO_USER_EXISTS
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		else if(null == secQuestAnsResponse && !secQuestAnsRequestVO.isAdditionalVerification()){
			
			secQuestAnsResponse = validateSecurityQuestDetails(secQuestAnsRequestVO);
		}
		else if(null == secQuestAnsResponse){
			secQuestAnsResponse = validateAdditionalVerificationDetails(secQuestAnsRequestVO);
		}
		return secQuestAnsResponse;
	}

	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	private ValidateSecQuestAnsResponse validateSecurityQuestDetails(
			SecQuestAnsRequestVO secQuestAnsRequestVO) {
		
		ValidateSecQuestAnsResponse secQuestAnsResponse = null;
		Results results =null;
		
		/*if(secQuestAnsRequestVO.getActualQuestionId() == null){
			
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_NO_SEC_QN
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_NO_SEC_QN
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		else if ((null == secQuestAnsRequestVO.getQuestionId() || !secQuestAnsRequestVO
						.getActualQuestionId().equals(
								secQuestAnsRequestVO.getQuestionId()))) {
			results = Results.getError(
					ResultsCode.INVALID_SECRET_QUESTION.getMessage(),
					ResultsCode.INVALID_SECRET_QUESTION.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}else */
		if(StringUtils.isBlank(secQuestAnsRequestVO.getUserAnswer())){
			results = Results.getError(
					ResultsCode.EMPTY_SECRET_QUESTION_ANSWER
					.getMessage(),
					ResultsCode.EMPTY_SECRET_QUESTION_ANSWER
					.getCode());
			secQuestAnsResponse = new ValidateSecQuestAnsResponse();
			secQuestAnsResponse.setResults(results);
		}
		
		return secQuestAnsResponse;
	}

	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	private ValidateSecQuestAnsResponse validateAdditionalVerificationDetails(
			SecQuestAnsRequestVO secQuestAnsRequestVO) {
		//check whether emailId is present
		ValidateSecQuestAnsResponse secQuestAnsResponse = null;
		Results results =null;

		//check whether phone number is present
		if(secQuestAnsRequestVO.isAdditionalVerification()){
			if(StringUtils.isBlank(secQuestAnsRequestVO.getUserPhoneNumber())){

				results = Results.getError(
						ResultsCode.FORGET_UNAME_PWD_PHONE_NUMBER_BLANK_INVALID
						.getMessage(),
						ResultsCode.FORGET_UNAME_PWD_PHONE_NUMBER_BLANK_INVALID
						.getCode());
				secQuestAnsResponse = new ValidateSecQuestAnsResponse();
				secQuestAnsResponse.setResults(results);
			}
			//check whether zip code is present
			else if(StringUtils.isBlank(secQuestAnsRequestVO.getUserZipCode())){
				results = Results.getError(
						ResultsCode.FORGET_UNAME_PWD_ZIP_CODE_BLANK_INVALID
						.getMessage(),
						ResultsCode.FORGET_UNAME_PWD_ZIP_CODE_BLANK_INVALID
						.getCode());
				secQuestAnsResponse = new ValidateSecQuestAnsResponse();
				secQuestAnsResponse.setResults(results);
			}
			//check whether company name is present
			else if(StringUtils.isBlank(secQuestAnsRequestVO.getUserCompanyName())){
				results = Results.getError(
						ResultsCode.FORGET_UNAME_PWD_COMPANY_NAME_BLANK_INVALID
						.getMessage(),
						ResultsCode.FORGET_UNAME_PWD_COMPANY_NAME_BLANK_INVALID
						.getCode());
				secQuestAnsResponse = new ValidateSecQuestAnsResponse();
				secQuestAnsResponse.setResults(results);
			}
			
		}
		return secQuestAnsResponse;
	}
	
	public ForgetUnamePwdServiceResponse validateForgetUnamePwdServiceRequest(final ForgetUnamePwdServiceRequest request, ForgetUnamePwdServiceResponse response){
		Results results = new Results();
		boolean isError = false;

		//General Error for both UserName/Password

		//check whether emailId is present
		if(!isError && StringUtils.isBlank(request.getEmail())){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL
					.getCode());
			response.setResults(results);
			isError = true;
		}
		//check whether emailId is valid
		if(!isError && !isEmailAddressValid(request.getEmail())){
			results = Results.getError(
					ResultsCode.FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL
					.getMessage(),
					ResultsCode.FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL
					.getCode());
			response.setResults(results);
			isError = true;
		}


		if(PublicMobileAPIConstant.REQUEST_FOR_PASSWORD.equalsIgnoreCase(request.getRequestFor())){

			//check whether userName is present
			if(!isError && StringUtils.isBlank(request.getUserName())){
				results = Results.getError(
						ResultsCode.FORGET_UNAME_PWD_USERNAME_BLANK
						.getMessage(),
						ResultsCode.FORGET_UNAME_PWD_USERNAME_BLANK
						.getCode());
				response.setResults(results);
				isError = true;
			}


		}else if(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_USERID.equalsIgnoreCase(request.getRequestFor())){
			// This validation is executed only for Forgot UserName with emailId, userId input to return the security question of the corresponding userId.
			// In this case userId is mandatory
			
			// This if condition handles scenario when userId is empty 
			if(!isError && (null==request.getUserId() || request.getUserId().intValue()==PublicMobileAPIConstant.ZERO)){
				results = Results.getError(
						ResultsCode.FORGET_UNAME_PWD_USERID_REQUIRED
						.getMessage(),
						ResultsCode.FORGET_UNAME_PWD_USERID_REQUIRED
						.getCode());
				response.setResults(results);
				isError = true;
			}
		}
		return response;
	}

}
