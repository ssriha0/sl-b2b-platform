package com.newco.marketplace.mobile.api.utils.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidRequest;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;


/**
 * The class acts as the entry point for the validation done for
 * add estimate web service 
 * 
 */
public class MobilePlaceBidValidator {

	private Logger LOGGER = Logger.getLogger(MobilePlaceBidValidator.class);
	private IMobileGenericBO mobileGenericBO;
    
	public MobilePlaceBidResponse validateSOBid(APIRequestVO apiVO,
			MobilePlaceBidRequest request, ServiceOrder so) throws BusinessServiceException {
		boolean isError = false;
		MobilePlaceBidResponse response = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		Date serviceStartDate;
		Date serviceEndDate;
		//validating logged in resource role
		response = validateResourceRole(apiVO.getRoleId(), apiVO.getProviderResourceId(),request.getBidResourceId(),isError);
		
		if(!isError){
			//validating whether resourceID in the request is associated with the firmId in url
			Integer vendorId = mobileGenericBO.getVendorId(request.getBidResourceId());
			if (null == vendorId || (null!=apiVO.getProviderIdInteger() &&  vendorId.intValue()!= apiVO.getProviderIdInteger().intValue())){
				response = MobilePlaceBidResponse
						.getInstanceForError(ResultsCode.SO_BID_INVALID_RESOURCE_ID);
				isError = true;
			}
		}
		
		if (!isError){
			//price validations
			if(PublicMobileAPIConstant.FIXED.equalsIgnoreCase(request.getPriceType())){
				//check whether labor price is present for fixed price type
				if (null==request.getLaborPrice()){
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_LABOR_PRICE_REQUIRED);
					isError = true;
				}
				else if (request.getLaborPrice().doubleValue() <= PublicMobileAPIConstant.ZERO_PRICE_DOUBLE){
					//check whether bid labor price is negative or 0
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_INVALID_LABOR_PRICE);
					isError = true;
				}
				else if (null!=request.getLaborHourlyRate() || null!= request.getLaborHours()){
					//hourly rate and labor hours should not be present for fixed price type orders
					LOGGER.error("hourly rate or labor hours present for fixed price type orders");
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_FIXED_INVALID_PARAMS);
					isError = true;
				}
			}
			else {
				//hourly rate
				if (null==request.getLaborHourlyRate() || null== request.getLaborHours()){
					//check whether hourly rate and labor hours is present for hourly rate price type
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_HOURLY_RATE_REQUIRED);
					isError = true;
				}
				else if (request.getLaborHourlyRate().doubleValue() <= PublicMobileAPIConstant.ZERO_PRICE_DOUBLE){
					//check whether bid labor rate is negative or 0
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_INVALID_HOURLY_RATE);
					isError = true;
				}
				else if (request.getLaborHours().intValue() <= PublicMobileAPIConstant.ZERO){
					//check whether bid labor hours is negative or 0
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_INVALID_LABOR_HOURS);
					isError = true;
				}
				else if (null!=request.getLaborPrice()){
					//labor price should not be present for hourly rate price type orders
					LOGGER.error("labor price present for fixed price type orders");
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_HOURLY_RATE_INVALID_PARAMS);
					isError = true;
				}
			}
		}
		
		// check whether offerExpirationDate is in desired format
				if(!isError && (null != request.getBidExpirationDate()|| null != request.getBidExpirationTime())){
					try{
						if (null== request.getBidExpirationDate()|| null == request.getBidExpirationTime()){
							// if expiration date is present, time not present or vice-versa
							response = MobilePlaceBidResponse
									.getInstanceForError(ResultsCode.SO_BID_INVALID_EXPIRATION_DATE);
							isError = true;
						}
						if (!isError){
							Date bidExpirationDate= format.parse(request.getBidExpirationDate() + " " + request.getBidExpirationTime());
							Calendar currentDateTime = Calendar.getInstance();
				        	
				        	int currentDateTimeOffset = currentDateTime.getTimeZone().getOffset(currentDateTime.getTimeInMillis()) / 1000 / 3600;  // convert from milliseconds to hours
							try {
								int serviceLocationTimeZoneOffset = mobileGenericBO.getServiceDateTimeZoneOffset(apiVO.getSOId());
								currentDateTime.add(Calendar.HOUR_OF_DAY, serviceLocationTimeZoneOffset - currentDateTimeOffset);  // convert to service location's current time
					        	if (bidExpirationDate.before(currentDateTime.getTime())) {
					        		response = MobilePlaceBidResponse
											.getInstanceForError(ResultsCode.BID_EXPIRATION_PAST_ERROR);
					        		isError = true;
					            }
							} catch (BusinessServiceException e) {
								LOGGER.error("MobilePlaceBidValidator: Exception in retreiving service date timezone offset"+ apiVO.getSOId());
								response = MobilePlaceBidResponse
										.getInstanceForError(ResultsCode.SO_BID_ACTIVITY_LOG_FAILED);
								isError = true;
							}
							
				        	
							/*Date today = new Date();
							if (today.after(bidExpirationDate)) {
								response = MobilePlaceBidResponse
											.getInstanceForError(ResultsCode.BID_EXPIRATION_PAST_ERROR);
								isError = true;
								}*/
						}
					}catch (ParseException e) {
						response = MobilePlaceBidResponse
								.getInstanceForError(ResultsCode.SO_BID_INVALID_EXPIRATION_DATE);
						isError = true;
					}
				}
		//new service date validations
		if (!isError){
			if((PublicMobileAPIConstant.RANGE).equalsIgnoreCase(request.getNewServiceDateType())){
				// new service date type is range
				if (null==request.getNewServiceStartDate() || null==request.getNewServiceEndDate()){
					//start date and end date not present
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_RANGE_DATES_REQUIRED);
					isError = true;
				}
				else if (null!= request.getNewServiceStartTime() || null!=request.getNewServiceEndTime()){
					//start time or end time present for range date type
					LOGGER.error("start time or end time present for range date type");
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_RANGE_INVALID_PARAMS);
					isError = true;
				}
				else{
					try{
						// check whether newServiceStartDate and end date is in desired format
						serviceStartDate= format.parse(request.getNewServiceStartDate() + " " + PublicMobileAPIConstant.DEFAULT_TIME);
			            serviceEndDate = format.parse(request.getNewServiceEndDate() + " " + PublicMobileAPIConstant.DEFAULT_TIME);
						Date today = new Date();
						Calendar cal = Calendar.getInstance();
                        cal.setTime(today);
                        cal.set(Calendar.HOUR, PublicMobileAPIConstant.ZERO);
                        cal.set(Calendar.MINUTE, PublicMobileAPIConstant.ZERO);
                        cal.set(Calendar.SECOND, PublicMobileAPIConstant.ZERO);
                        cal.set(Calendar.MILLISECOND, PublicMobileAPIConstant.ZERO);
                        today=cal.getTime();
						if (today.after(serviceStartDate)) {
							response = MobilePlaceBidResponse
										.getInstanceForError(ResultsCode.START_DATE_PAST_ERROR);
							isError = true;
							}
						else if (serviceStartDate.after(serviceEndDate)) {
							response = MobilePlaceBidResponse
										.getInstanceForError(ResultsCode.INVALID_START_DATE_GREATER_END_DATE);
							isError = true;
						}
					}
					catch (ParseException e) {
						response = MobilePlaceBidResponse
								.getInstanceForError(ResultsCode.SO_BID_RANGE_DATES_REQUIRED);
						isError = true;
					}
				}
			}
			else if((PublicMobileAPIConstant.SPECIFIC).equalsIgnoreCase(request.getNewServiceDateType())){
				//new service date type is specific
				if (null==request.getNewServiceStartDate() || StringUtils.isBlank(request.getNewServiceStartTime()) 
						|| StringUtils.isBlank(request.getNewServiceEndTime())){
					// start date or start time or end time not specified
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_SPECIFIC_DATES_REQUIRED);
					isError = true;
				}
				else if (null!=request.getNewServiceEndDate()){
					// start date or start time or end time not specified
					LOGGER.error("end date should not be specified for specific service date type");
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.SO_BID_SPECIFIC_INVALID_PARAMS);
					isError = true;
				}
				else{
					try{
						// check whether newServiceStartDate is in desired format
						serviceStartDate= format.parse(request.getNewServiceStartDate() + " " + request.getNewServiceStartTime());
						serviceEndDate= format.parse(request.getNewServiceStartDate() + " " + request.getNewServiceEndTime());
						Date today = new Date();
						if (today.after(serviceStartDate)) {
							response = MobilePlaceBidResponse
										.getInstanceForError(ResultsCode.START_DATE_PAST_ERROR);
							isError = true;
							}
						else if (serviceStartDate.after(serviceEndDate)) {
							response = MobilePlaceBidResponse
										.getInstanceForError(ResultsCode.INVALID_START_TIME_GREATER_END_TIME);
							isError = true;
						}
					}
					catch (ParseException e) {
						response = MobilePlaceBidResponse
								.getInstanceForError(ResultsCode.SO_BID_SPECIFIC_DATES_REQUIRED);
						isError = true;
					}	
			}
		}
		}
		if(!isError){
			boolean isRoutedToProvider=false;
			for (RoutedProvider routedProvider : so.getRoutedResources()) {
				if (request.getBidResourceId().equals(routedProvider.getResourceId())) {
					isRoutedToProvider=true;
				}
			}
			if(!isRoutedToProvider){
				response = MobilePlaceBidResponse
						.getInstanceForError(ResultsCode.SO_BID_PROVIDER_NOT_ROUTED);
				isError = true;
			}
		}
			return response;

	}
	
	

	/**Method to check the logged in user has permission to place bid
	 * @param role
	 * @param UrlResourceId
	 * @param bidResourceId
	 * @param isError
	 * @return
	 * @throws BusinessServiceException
	 */
	private MobilePlaceBidResponse validateResourceRole(Integer role, Integer UrlResourceId, Integer bidResourceId,boolean isError)throws BusinessServiceException{
		MobilePlaceBidResponse response = null;
		if(MPConstants.ROLE_LEVEL_TWO.equals(role.intValue()) && bidResourceId.intValue()!=UrlResourceId.intValue()){
					response = MobilePlaceBidResponse
							.getInstanceForError(ResultsCode.PERMISSION_ERROR);
					isError = true;
				}
			else if(MPConstants.ROLE_LEVEL_ONE.equals(role.intValue())){
				response = MobilePlaceBidResponse
						.getInstanceForError(ResultsCode.PERMISSION_ERROR);
				isError = true;
			}
				//for role level 3,the provider can place the bid to any provider who is there in the routed list under the firm.
		return response;
	}
	
	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}
	
}
