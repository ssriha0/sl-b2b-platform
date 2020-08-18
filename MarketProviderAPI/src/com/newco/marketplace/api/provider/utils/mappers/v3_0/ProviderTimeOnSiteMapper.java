package com.newco.marketplace.api.provider.utils.mappers.v3_0;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.provider.beans.v3_0.ProviderTimeOnSiteRequest;
import com.newco.marketplace.api.provider.constants.ProviderAPIConstant;
import com.newco.marketplace.vo.mobile.MobileSOOnsiteVisitVO;
import com.newco.marketplace.vo.mobile.ServiceOrder;
import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;

public class ProviderTimeOnSiteMapper {

	private final Logger logger = Logger.getLogger(ProviderTimeOnSiteMapper.class);

	/**
	 * Generates the SOOnSiteVisit object to call the OF Signal
	 * 
	 * @param onsiteVisitVO
	 * @return
	 */
	public SOOnSiteVisit mapSOOnsiteVisitForOF(
			MobileSOOnsiteVisitVO onsiteVisitVO) {
		SOOnSiteVisit onSiteVisit = new SOOnSiteVisit();
		onSiteVisit.setArrivalDate(onsiteVisitVO.getArrivalDate());
		onSiteVisit
				.setArrivalInputMethod(onsiteVisitVO.getArrivalInputMethod());
		onSiteVisit.setDeleteIndicator(onsiteVisitVO.getDeleteInd());
		if (onsiteVisitVO.getDepartureCondition() != null)
			onSiteVisit.setDepartureCondition(onsiteVisitVO
					.getDepartureCondition().toString());
		onSiteVisit.setDepartureDate(onsiteVisitVO.getDepartureDate());
		onSiteVisit.setDepartureInputMethod(onsiteVisitVO
				.getDepartureInputMethod());
		if (onsiteVisitVO.getDepartureResourceId() != null)
			onSiteVisit.setDepartureResourceId((long) onsiteVisitVO
					.getDepartureResourceId());
		onSiteVisit.setIvrCreateDate(onsiteVisitVO.getIvrcreatedate());
		if (onsiteVisitVO.getResourceId() != null)
			onSiteVisit.setResourceId((long) onsiteVisitVO.getResourceId());

		/*
		 * adding the new latitude and longitude fields to the OnSiteVisit
		 * entity for Mobile time on site API
		 */
		onSiteVisit.setArrivalLatitude(onsiteVisitVO.getArrivalLatitude());
		onSiteVisit.setArrivalLongitude(onsiteVisitVO.getArrivalLongitude());
		onSiteVisit.setDepartureLatitude(onsiteVisitVO.getDepartureLatitude());
		onSiteVisit
				.setDepartureLongitude(onsiteVisitVO.getDepartureLongitude());

		return onSiteVisit;
	}

	/**
	 * Generates the OnSiteVisit VO object for insert and update
	 * 
	 * @param so
	 * @param eventType
	 * @param currentDate
	 * @return
	 */
	public MobileSOOnsiteVisitVO mapSOOnsiteVisitVO(
			MobileSOOnsiteVisitVO onsiteVisitVO, ServiceOrder so,
			ProviderTimeOnSiteRequest mobileTimeonSiteRequest, Date currentDate, Date checkInOutDate) {
		// create the VO object in case of insert
		if (null == onsiteVisitVO) {
			onsiteVisitVO = new MobileSOOnsiteVisitVO();
		}

		Calendar cal = Calendar.getInstance();
		Date createdDate = cal.getTime();

		// getting the values from the request object
		String eventType = mobileTimeonSiteRequest.getEventType();
		Double latitude = mobileTimeonSiteRequest.getLatitude();
		Double longitide = mobileTimeonSiteRequest.getLongitude();

		// setting values for arrival time
		if (ProviderAPIConstant.EVENT_TYPE_ARRIVAL.equals(eventType)) {
			if (logger.isInfoEnabled()) {
				logger.info("Event type is Arrival " + eventType);
			}
			onsiteVisitVO.setSoId(so.getSoId());
			if(null != checkInOutDate){
				onsiteVisitVO.setArrivalDate(checkInOutDate);
			}else{
				onsiteVisitVO.setArrivalDate(currentDate);
			}
			
			onsiteVisitVO.setArrivalInputMethod(ProviderAPIConstant.ONSITE_VISIT_INPUT_METHOD);
			onsiteVisitVO.setResourceId(so.getAcceptedResourceId());
			onsiteVisitVO.setCreatedDate(createdDate);
			onsiteVisitVO
					.setDeleteInd(ProviderAPIConstant.ONSITE_DELETE_IND);

			// set latitude and longitude for arrival event
			onsiteVisitVO.setArrivalLatitude(latitude);
			onsiteVisitVO.setArrivalLongitude(longitide);
		} else if (ProviderAPIConstant.EVENT_TYPE_DEPARTURE
				.equals(eventType)) {
			// setting common values for departure
			if(null != checkInOutDate){
				onsiteVisitVO.setDepartureDate(checkInOutDate);
			}else{
				onsiteVisitVO.setDepartureDate(currentDate);
			}
			onsiteVisitVO.setDepartureInputMethod(ProviderAPIConstant.ONSITE_VISIT_INPUT_METHOD);
			onsiteVisitVO.setDepartureResourceId(so.getAcceptedResourceId());

			// departure in case of update
			if (null != onsiteVisitVO.getArrivalDate()) {
				if (logger.isInfoEnabled()) {
					logger.info("Inside update departure date");
				}
				onsiteVisitVO.setModifiedDate(createdDate);
			} else {
				// departure details in case of insert
				if (logger.isInfoEnabled()) {
					logger.info("Inside insert departure date");
				}
				onsiteVisitVO.setSoId(so.getSoId());
				onsiteVisitVO.setCreatedDate(createdDate);
				onsiteVisitVO
						.setDeleteInd(ProviderAPIConstant.ONSITE_DELETE_IND);
			}

			// set latitude and longitude for departure event
			onsiteVisitVO.setDepartureLatitude(latitude);
			onsiteVisitVO.setDepartureLongitude(longitide);
			
			// set reason code and current trip
			onsiteVisitVO.setCurrentTripNo(mobileTimeonSiteRequest.getCurrentTripNo());
			onsiteVisitVO.setDepartureReason(mobileTimeonSiteRequest.getReasonCode());
		}
		// Added for Large Provider 
		onsiteVisitVO.setTripSource(mobileTimeonSiteRequest.getTripSource());	

		return onsiteVisitVO;
	}
}
