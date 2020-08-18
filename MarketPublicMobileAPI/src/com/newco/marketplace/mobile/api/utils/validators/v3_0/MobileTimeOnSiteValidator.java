package com.newco.marketplace.mobile.api.utils.validators.v3_0;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.v3_0.MobileTimeOnSiteResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.business.iBusiness.mobile.IManageScheduleBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.mobile.MobileSOOnsiteVisitVO;
import com.newco.marketplace.vo.mobile.ServiceOrder;

/**
 * The class acts as the entry point for the validation done for update SO
 * Completion
 * 
 */
public class MobileTimeOnSiteValidator {

	private final Logger logger = Logger.getLogger(MobileTimeOnSiteValidator.class);
	private IManageScheduleBO manageScheduleBO;

	/**
	 * Validation for arrival record existence
	 * 
	 * @param latestArrival
	 * @param currentDateTimeZone
	 * @return
	 * @throws BusinessServiceException
	 */
	public MobileTimeOnSiteResponse validateLatestArrival(
			MobileSOOnsiteVisitVO latestArrival, Date currentDateTimeZone, Date checkOutDateTimeZone)
			throws BusinessServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering MobileTimeOnSiteValidator validateLatestArrival().");
		}

		MobileTimeOnSiteResponse mobileTimeonSiteResponse = null;

		// 0504. Arrival time should be existing for adding departure
		// time
		if (null == latestArrival) {
			// set the error code as no arrival time
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
					.getInstanceForError(ResultsCode.TIMEONSITE_NO_ARRIVALTIME,
							null);

			// return the response object
			return mobileTimeonSiteResponse;
		}

		// 0505. Departure Date should be greater than the Arrival time
		if(null == checkOutDateTimeZone){
			if (currentDateTimeZone.before(latestArrival.getArrivalDate())) {
				// set the error code as invalid departure time
				mobileTimeonSiteResponse = MobileTimeOnSiteResponse
						.getInstanceForError(
								ResultsCode.TIMEONSITE_INVALID_DEPARTURETIME, null);
				logger.info("currentDateTimeZone is before latestArrival");
				
				// return the response object
				return mobileTimeonSiteResponse;
			}
		}else{
			if (checkOutDateTimeZone.compareTo(latestArrival.getArrivalDate()) == 0 
					|| checkOutDateTimeZone.before(latestArrival.getArrivalDate())) {
				// set the error code as invalid departure time
				mobileTimeonSiteResponse = MobileTimeOnSiteResponse
						.getInstanceForError(
								ResultsCode.TIMEONSITE_INVALID_DEPARTURETIME, null);

				logger.info("checkOutDateTimeZone is latestArrival or checkOutDateTimeZone is before latestArrival");
				// return the response object
				return mobileTimeonSiteResponse;
			}
		}
		

		return mobileTimeonSiteResponse;
	}

	/**
	 * Validation for SO status
	 * 
	 * @param so
	 * @param eventType
	 * @return
	 * @throws BusinessServiceException
	 */
	public MobileTimeOnSiteResponse validateSOStatus(ServiceOrder so,
			String eventType) throws BusinessServiceException {

		if (logger.isDebugEnabled()) {
			logger.debug("Entering MobileTimeOnSiteValidator validateSOStatus().");
		}

		MobileTimeOnSiteResponse mobileTimeonSiteResponse = null;

		// 0502. if status of SO is ACTIVE|ACCEPTED for timeonsite update
		//Allow checkout to happen even after Completed and Closed state, R12.0 change
		if (!(so.getWfStateId().equals(OrderConstants.ACCEPTED_STATUS))
				&& !(so.getWfStateId().equals(OrderConstants.ACTIVE_STATUS))
				&& !(so.getWfStateId().equals(OrderConstants.COMPLETED_STATUS))
				&& !(so.getWfStateId().equals(OrderConstants.CLOSED_STATUS)) && PublicMobileAPIConstant.EVENT_TYPE_DEPARTURE.equals(eventType)) {
			// set the error code as invalid so status
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
					.getInstanceForError(
							ResultsCode.TIMEONSITE_INVALID_SO_STATUS, null);

			logger.info("Error for EVENT_TYPE_DEPARTURE ");
			
			// return the response object
			return mobileTimeonSiteResponse;
		}
		
		if (!(so.getWfStateId().equals(OrderConstants.ACCEPTED_STATUS))
				&& !(so.getWfStateId().equals(OrderConstants.ACTIVE_STATUS))
				&& PublicMobileAPIConstant.EVENT_TYPE_ARRIVAL.equals(eventType)) {
			// set the error code as invalid so status
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
					.getInstanceForError(
							ResultsCode.TIMEONSITE_INVALID_SO_STATUS, null);

			logger.info("Error for EVENT_TYPE_ARRIVAL ");
			
			// return the response object
			return mobileTimeonSiteResponse;
		}

		
		// THIS IS UNREACHABLE CODE - PLEASE REMOVE THIS
		// THERE IS NO OPTION FROM MOBILE TO ADD CHECKOUT BEFORE ADDING CHECKIN
		
		if (PublicMobileAPIConstant.EVENT_TYPE_DEPARTURE.equals(eventType)) {
			// 0502. if status of SO is ACTIVE|ACCEPTED for timeonsite update
			if (!so.getWfStateId().equals(OrderConstants.ACTIVE_STATUS) && !so.getWfStateId().equals(OrderConstants.COMPLETED_STATUS)
					&& !so.getWfStateId().equals(OrderConstants.CLOSED_STATUS)) {
				// set the error code as invalid so status
				mobileTimeonSiteResponse = MobileTimeOnSiteResponse
						.getInstanceForError(
								ResultsCode.TIMEONSITE_NO_ARRIVALTIME, null);

				// return the response object
				return mobileTimeonSiteResponse;
			}
		}

		return mobileTimeonSiteResponse;
	}

	/**
	 * Validation for accepted date
	 * 
	 * @param so
	 * @param eventType
	 * @param currentDate
	 * @return
	 * @throws BusinessServiceException
	 */
	public MobileTimeOnSiteResponse validateAcceptedDate(ServiceOrder so,
			String eventType, Date currentDate, Date checkInDateTimeZone) throws BusinessServiceException {

		if (logger.isDebugEnabled()) {
			logger.debug("Entering MobileTimeOnSiteValidator validateAcceptedDate().");
		}

		MobileTimeOnSiteResponse mobileTimeonSiteResponse = null;

		// 0506. Arrival date should be greater than time of acceptance of SO
		if (PublicMobileAPIConstant.EVENT_TYPE_ARRIVAL.equals(eventType)) {
			if(null == checkInDateTimeZone){
				if (null == so.getAcceptedDate()
						|| currentDate.before(so.getAcceptedDate())) {
					// set the error code as invalid arrival time
					mobileTimeonSiteResponse = MobileTimeOnSiteResponse
							.getInstanceForError(
									ResultsCode.TIMEONSITE_INVALID_ARRIVALTIME,
									null);

					logger.info("Error occured for acceptedDate is null or currentDate is before acceptedDate");
					logger.info("acceptedDate : " + so.getAcceptedDate() + " currentDate: " + currentDate);
					
					// return the response object
					return mobileTimeonSiteResponse;
				}
			}else{
				if (null == so.getAcceptedDate()
						|| checkInDateTimeZone.before(so.getAcceptedDate())) {
					// set the error code as invalid arrival time
					mobileTimeonSiteResponse = MobileTimeOnSiteResponse
							.getInstanceForError(
									ResultsCode.TIMEONSITE_INVALID_ARRIVALTIME,
									null);

					logger.info("Error occured for acceptedDate is null or checkInDateTimeZone is before acceptedDate");
					logger.info("acceptedDate : " + so.getAcceptedDate() + " checkindate: " + checkInDateTimeZone);
					
					// return the response object
					return mobileTimeonSiteResponse;
				}
			}
		}
		return mobileTimeonSiteResponse;
	}


	public IManageScheduleBO getManageScheduleBO() {
		return manageScheduleBO;
	}

	public void setManageScheduleBO(IManageScheduleBO manageScheduleBO) {
		this.manageScheduleBO = manageScheduleBO;
	}

	public MobileTimeOnSiteResponse validateLatitudeAndLongitude(
			Double latitude, Double longitude) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering MobileTimeOnSiteValidator validateLatitudeAndLongitude().");
		}

		MobileTimeOnSiteResponse mobileTimeonSiteResponse = null;

		if( null!= latitude && latitude.isNaN()){
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
			.getInstanceForError(
					ResultsCode.TIMEONSITE_INVALID_LATITUDE,
					null);
			return mobileTimeonSiteResponse;

		}
		else if (null!= longitude && longitude.isNaN()){
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
			.getInstanceForError(
					ResultsCode.TIMEONSITE_INVALID_LONGITUDE,
					null);
			return mobileTimeonSiteResponse;
		}
		return mobileTimeonSiteResponse;
		
	}
	
	/**
	 * validateTripAndReasonCode
	 * @param eventType
	 * @param curTripNo
	 * @param reasonCode
	 * @return
	 */
	public MobileTimeOnSiteResponse validateTripAndReasonCode(
			String eventType,Integer curTripNo,String reasonCode) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering MobileTimeOnSiteValidator validateTripAndReasonCode().");
		}

		MobileTimeOnSiteResponse mobileTimeonSiteResponse = null;
		if (PublicMobileAPIConstant.EVENT_TYPE_DEPARTURE.equals(eventType)) {

			/*if( null!= curTripNo && 0==curTripNo){
				mobileTimeonSiteResponse = MobileTimeOnSiteResponse
				.getInstanceForError(
						ResultsCode.TIMEONSITE_INVALID_TRIP,
						null);
				return mobileTimeonSiteResponse;
	
			}
			else*/ if (StringUtils.isBlank(reasonCode)){
				mobileTimeonSiteResponse = MobileTimeOnSiteResponse
				.getInstanceForError(
						ResultsCode.TIMEONSITE_INVALID_REASONCODE,
						null);
				return mobileTimeonSiteResponse;
			}
		}
		
		return mobileTimeonSiteResponse;
		
	}
	/**
	 * R14.0 check whether the logged in resource Id and so accepted_resource_id are same, else throw error
	 * this validation is removed from the BaseService in order to incorporate the Role level permissions
	 * For TimeOnsiteService and UpdateSOCompletionService, irrespective of the role id, the accepted resource only can do the activities.
	 * hence reinstating the validation in these services.
	 * 
	 * @param loggedInResourceId
	 * @param acceptedResourceId
	 * @return MobileTimeOnSiteResponse
	 */
	public MobileTimeOnSiteResponse validateResourcePermission (Integer loggedInResourceId, Integer acceptedResourceId){
		MobileTimeOnSiteResponse mobileTimeonSiteResponse = null;
		
		if(!loggedInResourceId.equals(acceptedResourceId)){
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
					.getInstanceForError(
							ResultsCode.PERMISSION_ERROR, null);
		}
		return mobileTimeonSiteResponse;
	}

	public MobileTimeOnSiteResponse validateCheckInCheckOutDate(
			String eventType, Date currentDateTimeZone,
			Date checkInCheckOutDateTimeZone) {
		MobileTimeOnSiteResponse mobileTimeOnSiteResponse = null;
			if(null != checkInCheckOutDateTimeZone){
				if(currentDateTimeZone.before(checkInCheckOutDateTimeZone)){
					if(PublicMobileAPIConstant.EVENT_TYPE_ARRIVAL.equals(eventType)){
						mobileTimeOnSiteResponse = MobileTimeOnSiteResponse.getInstanceForError(ResultsCode.TIMEONSITE_INVALID_CHECKIN_DATE,null);
					}else if(PublicMobileAPIConstant.EVENT_TYPE_DEPARTURE.equals(eventType)){
						mobileTimeOnSiteResponse = MobileTimeOnSiteResponse.getInstanceForError(ResultsCode.TIMEONSITE_INVALID_CHECKOUT_DATE,null);
					}
					
				}
		}
			
		return mobileTimeOnSiteResponse;
	}

}
