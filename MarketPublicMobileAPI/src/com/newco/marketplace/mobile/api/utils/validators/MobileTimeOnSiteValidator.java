package com.newco.marketplace.mobile.api.utils.validators;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.MobileTimeOnSiteResponse;
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

	private Logger logger = Logger.getLogger(MobileTimeOnSiteValidator.class);
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
			MobileSOOnsiteVisitVO latestArrival, Date currentDateTimeZone)
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
		if (currentDateTimeZone.before(latestArrival.getArrivalDate())) {
			// set the error code as invalid departure time
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
					.getInstanceForError(
							ResultsCode.TIMEONSITE_INVALID_DEPARTURETIME, null);

			// return the response object
			return mobileTimeonSiteResponse;
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
		if (!(so.getWfStateId().equals(OrderConstants.ACCEPTED_STATUS))
				&& !(so.getWfStateId().equals(OrderConstants.ACTIVE_STATUS))) {
			// set the error code as invalid so status
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
					.getInstanceForError(
							ResultsCode.TIMEONSITE_INVALID_SO_STATUS, null);

			// return the response object
			return mobileTimeonSiteResponse;
		}

		if (PublicMobileAPIConstant.EVENT_TYPE_DEPARTURE.equals(eventType)) {
			// 0502. if status of SO is ACTIVE|ACCEPTED for timeonsite update
			if (!so.getWfStateId().equals(OrderConstants.ACTIVE_STATUS)) {
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
			String eventType, Date currentDate) throws BusinessServiceException {

		if (logger.isDebugEnabled()) {
			logger.debug("Entering MobileTimeOnSiteValidator validateAcceptedDate().");
		}

		MobileTimeOnSiteResponse mobileTimeonSiteResponse = null;

		// 0506. Arrival date should be greater than time of acceptance of SO
		if (PublicMobileAPIConstant.EVENT_TYPE_ARRIVAL.equals(eventType)) {
			if (null == so.getAcceptedDate()
					|| currentDate.before(so.getAcceptedDate())) {
				// set the error code as invalid arrival time
				mobileTimeonSiteResponse = MobileTimeOnSiteResponse
						.getInstanceForError(
								ResultsCode.TIMEONSITE_INVALID_ARRIVALTIME,
								null);

				// return the response object
				return mobileTimeonSiteResponse;
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

}
