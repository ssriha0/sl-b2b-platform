package com.newco.marketplace.api.mobile.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidRequest;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidResponse;
import com.newco.marketplace.api.mobile.beans.vo.RequestBidVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.api.utils.validators.MobilePlaceBidValidator;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOScheduleDate;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class would act as a Service for SO Place Bid
 * 
 * @author Infosys $ $Date: 2015/05/05
 * @version 1.0
 */

@APIRequestClass(MobilePlaceBidRequest.class)
@APIResponseClass(MobilePlaceBidResponse.class)
public class MobilePlaceBidService extends BaseService {
	private Logger LOGGER = Logger.getLogger(MobilePlaceBidService.class);
	private IMobileGenericBO mobileGenericBO;
	private MobilePlaceBidValidator mobilePlaceBidValidator;
	private IActivityLogHelper  activityLogHelper;
	private OFHelper ofHelper;
	
	public MobilePlaceBidService() {
		// calling the BaseService constructor to initialize
		super();
	}
	
	/**
	 * This method handle the main logic for requesting a bid for service order.
	 * @param apiVO
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		boolean saveBidToActivityLogFailed = false;
		ServiceOrder so = null;
		RequestBidVO bidVO = null;
		ProcessResponse processResponse = null;
		SecurityContext securityContext = null;
		MobilePlaceBidRequest request  = (MobilePlaceBidRequest)apiVO.getRequestFromPostPut();
		MobilePlaceBidResponse mobilePlaceBidResponse = null;
		
		try {
			so = mobileGenericBO.getServiceOrder(apiVO.getSOId());
			
			mobilePlaceBidResponse = mobilePlaceBidValidator.validateSOBid(apiVO, request, so);
			
			if(null != mobilePlaceBidResponse){
				return mobilePlaceBidResponse;
			}
			
			securityContext = getSecurityContextForVendor(apiVO.getProviderResourceId());
			
			bidVO = mapBidRequest(apiVO, request);
			// Saving bid activity in B2C Reporting
			bidVO = saveBidActivityLog(bidVO,securityContext);
			saveBidToActivityLogFailed = bidVO.isSaveBidToActivityLogFailed();
			if(saveBidToActivityLogFailed == false){
			
			 processResponse = executeSOBid(bidVO, securityContext);
			
			  mobilePlaceBidResponse = createAcceptResponse(processResponse);
			}else{
				//Create error Response
				mobilePlaceBidResponse = MobilePlaceBidResponse.getInstanceForError(ResultsCode.SO_BID_ACTIVITY_LOG_FAILED);
			}
			
		} catch (Exception ex) {
			LOGGER.error("Exception Occurred inside MobilePlaceBidService.execute"
					+ ex);
			mobilePlaceBidResponse = MobilePlaceBidResponse
			.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		}
		return mobilePlaceBidResponse;
	}

	

	private RequestBidVO mapBidRequest(APIRequestVO apiVO,
			MobilePlaceBidRequest request) throws ParseException {
		Double totalLaborParts = 0.0D;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		RequestBidVO bidVO = new RequestBidVO();
		bidVO.setSoId(apiVO.getSOId());
		if(StringUtils.isNumeric(apiVO.getProviderId())){
			bidVO.setVendorId(Integer.parseInt(apiVO.getProviderId()));
		} 
		bidVO.setRoutedResourceId(request.getBidResourceId());
		if(null != request.getLaborHours()){
			bidVO.setTotalHours(request.getLaborHours().doubleValue());
		}
		if(null != request.getLaborHourlyRate()){
			bidVO.setLaborRate(request.getLaborHourlyRate());
		}
		if(null != request.getLaborPrice()){
			bidVO.setTotalLaborPrice(request.getLaborPrice());
		}else if(null != request.getLaborHourlyRate() && null != request.getLaborHours()){
			bidVO.setTotalLaborPrice(request.getLaborHourlyRate() * request.getLaborHours().doubleValue());
		}
		bidVO.setPartsPrice(request.getPartsPrice());
		if(null != bidVO.getTotalLaborPrice())
			totalLaborParts += bidVO.getTotalLaborPrice();
		if(null != bidVO.getPartsPrice())
			totalLaborParts += bidVO.getPartsPrice();
		bidVO.setTotalLaborPartsPrice(totalLaborParts);
		if(StringUtils.isNotEmpty(request.getBidExpirationDate()) && StringUtils.isNotEmpty(request.getBidExpirationTime()))
		{
			bidVO.setBidExpirationDate(format.parse(request.getBidExpirationDate() + " " + request.getBidExpirationTime()));
		}
		bidVO.setBidExpirationHour(request.getBidExpirationTime());
		bidVO.setNewServiceDateType(false);
		if((OrderConstants.SPECIFIC).equals(request.getNewServiceDateType())){
			if(StringUtils.isNotEmpty(request.getNewServiceStartDate()) && StringUtils.isNotEmpty(request.getNewServiceStartTime()) 
					&& StringUtils.isNotEmpty(request.getNewServiceEndTime())){
				bidVO.setNewServiceDateStart(format.parse(request.getNewServiceStartDate() + " " + request.getNewServiceStartTime()));
				bidVO.setNewServiceDateEnd(format.parse(request.getNewServiceStartDate() + " " + request.getNewServiceEndTime()));
				bidVO.setNewServiceTimeStart(request.getNewServiceStartTime());
				bidVO.setNewServiceTimeEnd(request.getNewServiceEndTime());
			}
		}else{
			if(StringUtils.isNotEmpty(request.getNewServiceStartDate())  
					&& StringUtils.isNotEmpty(request.getNewServiceEndDate())){
				bidVO.setNewServiceDateStart(format.parse(request.getNewServiceStartDate() + " " + PublicMobileAPIConstant.DEFAULT_TIME));
				bidVO.setNewServiceDateEnd(format.parse(request.getNewServiceEndDate() + " " + PublicMobileAPIConstant.DEFAULT_TIME));
				bidVO.setNewServiceTimeStart(PublicMobileAPIConstant.DEFAULT_TIME);
				bidVO.setNewServiceTimeEnd(PublicMobileAPIConstant.DEFAULT_TIME);
			}
		}
		bidVO.setComment(request.getBidComments());
		return bidVO;
	}

	private MobilePlaceBidResponse createAcceptResponse(ProcessResponse processResponse) {
		MobilePlaceBidResponse response;
		if(processResponse == null) {
			//Create error response when ProcessResponse is null.
			response = MobilePlaceBidResponse
					.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {
			//Create Success response when Valid code is set in process response 
			response = new MobilePlaceBidResponse(Results.getSuccess(
					ResultsCode.SO_BID_SUCCESS.getMessage()));					
		} else {
			LOGGER.error("Error occured in Placing Bid, error code: " 
					+ ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getCode() 
					+ ", message: " + ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getMessage());
			response = new MobilePlaceBidResponse(Results.getError(ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getMessage(), ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getCode()));
		}
		return response;
	}
	private RequestBidVO saveBidActivityLog(RequestBidVO bidVO, SecurityContext securityContext) throws Exception {
		boolean saveBidToActivityLogFailed = false;
		String userName = securityContext.getUsername();
		String expireHourTmpStr = "05:00 PM";
		Long bidId = null;
		Long buyerResourceId = null;
		boolean serviceDateSpecifiedAsARange = false;
		try{
			bidVO = mobileGenericBO.fetchServiceOrderDetailsForBid(bidVO);
			
			if(null == bidVO.getBidExpirationDate()){
				if(null != bidVO.getServiceDateEnd()){
					bidVO.setBidExpirationDate(bidVO.getServiceDateEnd());
					bidVO.setBidExpirationHour(bidVO.getServiceTimeEnd());
				} else if(null != bidVO.getServiceDateStart()){
					bidVO.setBidExpirationDate(bidVO.getServiceDateStart());
					bidVO.setBidExpirationHour(expireHourTmpStr);
				}
			}
			activityLogHelper.markBidsFromProviderAsExpired(bidVO.getSoId(), new Long(bidVO.getRoutedResourceId()));
			bidId = activityLogHelper.logBid(bidVO.getSoId(), new Long(bidVO.getVendorId()), new Long(bidVO.getRoutedResourceId()), new Long(bidVO.getBuyerId()), buyerResourceId, bidVO.getTotalHours(), bidVO.getLaborRate(),
					bidVO.getTotalLaborPrice(), bidVO.getPartsPrice(), bidVO.getBidExpirationDate(), bidVO.getNewServiceDateStart(), bidVO.getNewServiceDateEnd(), serviceDateSpecifiedAsARange,
					bidVO.getComment(), userName);
			if(null == bidId || bidId < 0){
				saveBidToActivityLogFailed = true;
			}
			
		}
		catch (RuntimeException e){
			LOGGER.error("Failed to save bid to ActivityLog:" + "soID=" + bidVO.getSoId() + "\n" + "vendorId=" + bidVO.getVendorId() + "\n" + "resourceId="
					+ bidVO.getRoutedResourceId() + "\n" + "buyerId=" + bidVO.getBuyerId() + "\n" + "\n" + "totalHours="
					+ bidVO.getTotalHours() + "\n" + "laborRate=" + bidVO.getLaborRate() + "\n" + "totalLabor=" + bidVO.getTotalLaborPrice() + "\n" + "partsMaterials="
					+ bidVO.getPartsPrice() + "\n" + "bidExpirationDate=" + bidVO.getBidExpirationDate() + "\n" + "newDateRangeFrom="
					+ bidVO.getNewServiceDateStart() + "\n" + "newDateRangeTo=" + bidVO.getNewServiceDateEnd() + "\n" + "comment=" + bidVO.getComment() + "\n"
					+ "username=" + userName + "\n"+"Exception :"+e);
			saveBidToActivityLogFailed = true;
		}		
		catch (Exception e) {
			LOGGER.error("Failed to save bid to ActivityLog:" + "soID=" + bidVO.getSoId() + "\n" + "vendorId=" + bidVO.getVendorId() + "\n" + "resourceId="
					+ bidVO.getRoutedResourceId() + "\n" + "buyerId=" + bidVO.getBuyerId() + "\n" + "buyerResourceId=" + "\n" + "totalHours="
					+ bidVO.getTotalHours() + "\n" + "laborRate=" + bidVO.getLaborRate() + "\n" + "totalLabor=" + bidVO.getTotalLaborPrice() + "\n" + "partsMaterials="
					+ bidVO.getPartsPrice() + "\n" + "bidExpirationDate=" + bidVO.getBidExpirationDate() + "\n" + "newDateRangeFrom="
					+ bidVO.getNewServiceDateStart() + "\n" + "newDateRangeTo=" + bidVO.getNewServiceDateEnd() + "\n" + "comment=" + bidVO.getComment() + "\n"
					+ "username=" + userName + "\n"+"Exception :"+e);
			saveBidToActivityLogFailed = true;
		}
		bidVO.setSaveBidToActivityLogFailed(saveBidToActivityLogFailed);
		return bidVO;
	}
	
	
	public ProcessResponse executeSOBid(RequestBidVO bidVO, SecurityContext securityContext) throws BusinessServiceException{
		ProcessResponse processResponse = null;
		boolean saveBidToActivityLogFailed = bidVO.isSaveBidToActivityLogFailed();
		if(saveBidToActivityLogFailed == false){
			try {
				
				RoutedProvider routedProvider = createBid(bidVO);
				List<com.servicelive.orderfulfillment.domain.RoutedProvider> firmRoutedProviders = new ArrayList<com.servicelive.orderfulfillment.domain.RoutedProvider>();
				firmRoutedProviders.add(routedProvider);
				SOElementCollection routedProviders=new SOElementCollection();
				routedProviders.addAllElements(firmRoutedProviders);
				OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(bidVO.getSoId(), SignalType.CREATE_CONDITIONAL_OFFER, routedProviders, OFMapper.createProviderIdentFromSecCtx(securityContext));
				processResponse = OFMapper.mapProcessResponse(response);
				if(response.isError()){
					LOGGER.error("Save Bid To Database Failed: Error from OF");
					processResponse = createErrorProcessResponse(ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getCode(), ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getMessage());
				}
			}
			catch (RuntimeException e1){
				throw new BusinessServiceException(e1);
			}
		}
		if(saveBidToActivityLogFailed){
			LOGGER.error("save bid to activity log failed");
			processResponse = createErrorProcessResponse(ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getCode(), ResultsCode.SO_BID_ACTIVITY_LOG_FAILED.getMessage());
		}
		return processResponse;
	}
	public static RoutedProvider createBid(RequestBidVO bidVO) {

		SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		RoutedProvider returnVal = new RoutedProvider();
		returnVal.setProviderResourceId((long)bidVO.getRoutedResourceId());
		returnVal.setVendorId(bidVO.getVendorId());
		returnVal.setProviderResponse(ProviderResponseType.CONDITIONAL_OFFER);

		try{
			returnVal.setOfferExpirationDate( formatDateTime.parse(formatDate.format(bidVO.getBidExpirationDate()) + " " + bidVO.getBidExpirationHour()));
		}catch(ParseException e){
			//Should not happen.
		}

		if(bidVO.getNewServiceDateStart() != null){
			returnVal.setSchedule(new SOScheduleDate());
			returnVal.getSchedule().setServiceDate1(bidVO.getNewServiceDateStart());
			returnVal.getSchedule().setServiceTimeStart(bidVO.getNewServiceTimeStart());
			//should not be setting end date without start date
			if(bidVO.getNewServiceDateEnd() != null){
				returnVal.getSchedule().setServiceDate2(bidVO.getNewServiceDateEnd());
				returnVal.getSchedule().setServiceTimeEnd(bidVO.getNewServiceTimeEnd());
			}
		}
		if(null != bidVO.getTotalHours()) returnVal.setTotalHours(bidVO.getTotalHours());
		if(null != bidVO.getTotalLaborPrice()) returnVal.setTotalLabor(new BigDecimal(bidVO.getTotalLaborPrice()));
		if(null != bidVO.getPartsPrice()) returnVal.setPartsMaterials(new BigDecimal(bidVO.getPartsPrice()));
		if(null != bidVO.getTotalLaborPartsPrice()) returnVal.setIncreaseSpendLimit(new BigDecimal(bidVO.getTotalLaborPartsPrice()));
		returnVal.setProviderRespComment(bidVO.getComment());
		if (bidVO.getNewServiceDateStart() != null && bidVO.getTotalLaborPartsPrice() != null)
			returnVal.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT);
		else if (bidVO.getTotalLaborPartsPrice() != null)
			returnVal.setProviderRespReasonId(OrderConstants.SPEND_LIMIT);
		else if (bidVO.getNewServiceDateStart() != null)
			returnVal.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE);

		return returnVal;
	}
	
	/**
	 * Method to create Error ProcessResponse.
	 */
	public ProcessResponse createErrorProcessResponse(String code, String message){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setCode(code);
		processResponse.setMessage(message);
		return processResponse;
	}
	
	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	
	public MobilePlaceBidValidator getMobilePlaceBidValidator() {
		return mobilePlaceBidValidator;
	}

	public void setMobilePlaceBidValidator(
			MobilePlaceBidValidator mobilePlaceBidValidator) {
		this.mobilePlaceBidValidator = mobilePlaceBidValidator;
	}

	public void setActivityLogHelper(IActivityLogHelper activityLogHelper) {
		this.activityLogHelper = activityLogHelper;
	}
	public IActivityLogHelper getActivityLogHelper() {
		return activityLogHelper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}
	
	
}