package com.newco.marketplace.business.businessImpl.mobile;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.mobile.IManageScheduleBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.mobile.IMangeScheduleDao;
import com.newco.marketplace.vo.mobile.MobileSOOnsiteVisitVO;
import com.newco.marketplace.vo.mobile.ServiceOrder;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;

public class ManageScheduleBOImpl implements IManageScheduleBO {

	private IMangeScheduleDao manageScheduleDao;

	private Logger logger = Logger.getLogger(ManageScheduleBOImpl.class);

	public Integer editSOAppointmentTime(UpdateApptTimeVO scheduleVO) {
		return manageScheduleDao.editSOAppointmentTime(scheduleVO);
	}
	
	public UpdateApptTimeVO fetchServiceDatesAndTimeWndw(String soId){
		return manageScheduleDao.fetchServiceDatesAndTimeWndw(soId);
	}

	public IMangeScheduleDao getManageScheduleDao() {
		return manageScheduleDao;
	}

	public void setManageScheduleDao(IMangeScheduleDao manageScheduleDao) {
		this.manageScheduleDao = manageScheduleDao;
	}

	/**
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public ServiceOrder fetchServiceOrderForVisit(String soId)
			throws BusinessServiceException {
		// initializing variables
		ServiceOrder serviceOrder = null;

		try {
			// call the DAO method to fetch the SO details
			serviceOrder = manageScheduleDao.fetchServiceOrderForVisit(soId);

			// convert dates to SO time zone
			if (serviceOrder == null) {
				if (logger.isDebugEnabled())
					logger.debug("getServiceOrder() - Error getting service order for "
							+ soId);
			}

		} catch (DataServiceException e) {
			logger.error("Exception :" + e);
		}
		return serviceOrder;
	}

	/*private static void GMTToGivenTimeZone(ServiceOrder serviceOrder) {
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		HashMap<String, Object> rescheduleDate1 = null;
		HashMap<String, Object> rescheduleDate2 = null;
		String startTime = null;
		String endTime = null;

		// convert the service order start time and end times
		startTime = serviceOrder.getServiceTimeStart();
		endTime = serviceOrder.getServiceTimeEnd();
		if (serviceOrder.getServiceDate1() != null && startTime != null) {

			serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(
					serviceOrder.getServiceDate1(), startTime,
					serviceOrder.getServiceLocationTimeZone());
			if (serviceDate1 != null && !serviceDate1.isEmpty()) {
				serviceOrder.setServiceDate1((Timestamp) serviceDate1
						.get(OrderConstants.GMT_DATE));
				serviceOrder.setServiceTimeStart((String) serviceDate1
						.get(OrderConstants.GMT_TIME));
			}
		}
		if (serviceOrder.getServiceDate2() != null && endTime != null) {
			serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(
					serviceOrder.getServiceDate2(), endTime,
					serviceOrder.getServiceLocationTimeZone());
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				serviceOrder.setServiceDate2((Timestamp) serviceDate2
						.get(OrderConstants.GMT_DATE));
				serviceOrder.setServiceTimeEnd((String) serviceDate2
						.get(OrderConstants.GMT_TIME));
			}
		}

		// handle reschedule times
		startTime = serviceOrder.getRescheduleServiceTimeStart();
		endTime = serviceOrder.getRescheduleServiceTimeEnd();
		if (serviceOrder.getRescheduleServiceDate1() != null
				&& startTime != null) {

			rescheduleDate1 = TimeUtils.convertGMTToGivenTimeZone(
					serviceOrder.getRescheduleServiceDate1(), startTime,
					serviceOrder.getServiceLocationTimeZone());
			if (rescheduleDate1 != null && !rescheduleDate1.isEmpty()) {
				serviceOrder
						.setRescheduleServiceDate1((Timestamp) rescheduleDate1
								.get(OrderConstants.GMT_DATE));
				serviceOrder
						.setRescheduleServiceTimeStart((String) rescheduleDate1
								.get(OrderConstants.GMT_TIME));
			}
		}
		if (serviceOrder.getRescheduleServiceDate2() != null && endTime != null) {
			rescheduleDate2 = TimeUtils.convertGMTToGivenTimeZone(
					serviceOrder.getRescheduleServiceDate2(), endTime,
					serviceOrder.getServiceLocationTimeZone());
			if (rescheduleDate2 != null && !rescheduleDate2.isEmpty()) {
				serviceOrder
						.setRescheduleServiceDate2((Timestamp) rescheduleDate2
								.get(OrderConstants.GMT_DATE));
				serviceOrder
						.setRescheduleServiceTimeEnd((String) rescheduleDate2
								.get(OrderConstants.GMT_TIME));
			}
		}

	}*/

	/**
	 * 
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer fetchResourceVendorId(Integer resourceId)
			throws BusinessServiceException {
		Integer vendorId = null;
		try {
			// call the DAO method for fetching the vendor Id
			vendorId = manageScheduleDao.fetchResourceVendorId(resourceId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		// returns the vendor Id for the input provider Id
		return vendorId;
	}

	/**
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public MobileSOOnsiteVisitVO fetchLatestArrivalForSOId(String soId)
			throws BusinessServiceException {
		MobileSOOnsiteVisitVO latestArrival = null;
		try {
			// call DAO method for fetching the latest arrival record
			latestArrival = manageScheduleDao.fetchLatestArrivalForSOId(soId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		// returns the latest arrival record as a VO object
		return latestArrival;
	}

	/**
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean insertSoOnsiteVisit(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws BusinessServiceException {
		try {
			// call the DAO method to insert
			long visitId = manageScheduleDao
					.insertSOOnsiteVisitDetails(soOnsiteVisitVO);
			// return true if row is inserted
			if (visitId > 0) {
				return true;
			}
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		// default return is false
		return false;
	}
	
	
	/**
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public long insertSoOnsiteVisitId(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws BusinessServiceException {
		long visitId = 0;
		try {
			// call the DAO method to insert
			visitId = manageScheduleDao.insertSOOnsiteVisitDetails(soOnsiteVisitVO);
			
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		// default return is false
		return visitId;
	}

	/**
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean updateSoOnsiteVisit(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws BusinessServiceException {
		try {
			// call the DAO method for update
			int updatedRows = manageScheduleDao
					.updateSOOnsiteVisitDetails(soOnsiteVisitVO);
			// return true if row is updated
			if (updatedRows > 0) {
				return true;
			}
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		// default return is false
		return false;
	}

	public Integer getSOStatus(String soId) throws BusinessServiceException {
		Integer status = null;
		try {
			status = manageScheduleDao.getSOStatus(soId);
				
		}catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		return status;
	}

	public void updateSOSubStatus(String soId, String eventType)
			throws BusinessServiceException {
		manageScheduleDao.updateSOSubStatus(soId, eventType);
	}

	
	/**
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer fetchLatestValidTripForSO(ServiceOrder serviceOrder,
			String createdBy,MobileSOOnsiteVisitVO onsiteVisitVO)
			throws BusinessServiceException{
		SOTripVO trip = null;
		String soId = serviceOrder.getSoId();	
		Integer currenttripNo = 0;
		Integer newTripNo = 0;
		try {
			trip = manageScheduleDao.fetchLatestValidTripForSO(soId);
			
			if(null!= trip){
				currenttripNo = trip.getTripNo();
			}
			// TODO Get the current visit id - TODO Can this be obtained from the OF Response?
			Long latestVisitId = manageScheduleDao.fetchLatestVisitId(soId);
			
			/*
			 * TODO If there is an valid trip. Ideally this scenario
			 * will come only if the service order was moved to 'Revisit
			 * needed'. That is the only place where the trip can be
			 * created as OPEN with no check in time and the sub status
			 * is anything other than 20 How - ? IF Trip object is empty
			 * Create a new trip ELSE IF Trip status OPEN & Check-in
			 * present & service order sub status is not 'Provider on
			 * site'
			 * 
			 * Create a new trip ELSE IF Trip status OPEN & Check-in
			 * time not present
			 * 
			 * Use the trip number after associating the visit id just
			 * created
			 */ 
			
			logger.info("currenttripNo::"+currenttripNo);			
			logger.info("latestVisitId::"+latestVisitId);
			
			/**
			 * NOTE : Check the third condition below Ideally this
			 * scenario will never come since the web service will not
			 * be invoked from the mobile application if the service
			 * order is already in 'Provider on site' sub status I am
			 * adding this condition in the mobile since just now you
			 * checked in, which should create a new trip. - This is
			 * valid only if the web service is invoked separately.
			 * Latest trip is closed - Create a new trip
			 */
			
			if ((null == trip)
					|| (null != trip && null != trip.getTripStartVisitId() 
							&& MPConstants.OPEN.equalsIgnoreCase(trip.getTripStatus()) 
							&& MPConstants.PROVIDER_ON_SITE_SUBSTATUS_ID != serviceOrder
							.getWfSubStatusId())
					|| (null != trip && null != trip.getTripStartVisitId() 
							&& MPConstants.OPEN.equalsIgnoreCase(trip.getTripStatus())
							&& MPConstants.PROVIDER_ON_SITE_SUBSTATUS_ID == serviceOrder
							.getWfSubStatusId())
					|| MPConstants.END.equalsIgnoreCase(trip.getTripStatus())) {
				logger.debug("Creating the trip");				
				
				// Create a new trip
				SOTripVO tripVO = mapServiceOrderTrip(soId, onsiteVisitVO,
						latestVisitId, currenttripNo, serviceOrder, createdBy,
						MPConstants.OPEN,null);

				newTripNo = manageScheduleDao.createNewTrip(tripVO);
			} else if (null == trip.getTripStartVisitId() && MPConstants.OPEN.equalsIgnoreCase(trip.getTripStatus())) {
				logger.debug("Updating the trip");
				
				// OPEN trip with no check in - UPDATE 
				SOTripVO tripVO = mapServiceOrderTrip(soId, onsiteVisitVO,
						latestVisitId, currenttripNo, serviceOrder, createdBy,
						MPConstants.UPDATE,trip.getSoTripId());
				
				newTripNo = manageScheduleDao.updateCurrentTrip(tripVO);
			} 
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessServiceException(e.getMessage(), e);			
		}
		return newTripNo;
	}
	
	/**
	 * Close the current trip
	 */
	public Integer closeCurrentTrip(ServiceOrder serviceOrder,
			String createdBy,MobileSOOnsiteVisitVO onsiteVisitVO,Integer currentTripId)
			throws BusinessServiceException{
		Integer currentTrip = 0;
		String soId = serviceOrder.getSoId();
		try {
			Long closeVisitId = onsiteVisitVO.getVisitId();
			SOTripVO tripVO = mapServiceOrderTrip(soId, onsiteVisitVO,
					closeVisitId, onsiteVisitVO.getCurrentTripNo(), serviceOrder, createdBy,
					MPConstants.END,currentTripId);
			currentTrip = manageScheduleDao.updateCurrentTrip(tripVO);
			
			//R12.3 - SL-20673 Removing the New Trip creation logic in case of revisit needed status.
		}catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessServiceException(e.getMessage(), e);			
		}
		
		return currentTrip;
		
	}
	
	/**
	 * Validate the trip for departure
	 */
	public Integer validateTrip(Integer curTripNo,String soId) throws BusinessServiceException{
		try {
			return manageScheduleDao.validateTrip(curTripNo,soId);
		}catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessServiceException(e.getMessage(), e);			
		}
	}
	

	
	/**
	 * 
	 * @param soId
	 * @param onsiteVisitVO
	 * @param latestVisitId
	 * @param tripNo
	 * @param so
	 * @param createdBy
	 * @param action
	 * @param soTripId
	 * @return
	 */
	public SOTripVO mapServiceOrderTrip(String soId,
			MobileSOOnsiteVisitVO onsiteVisitVO, Long latestVisitId,
			Integer tripNo, ServiceOrder so, String createdBy, String action,
			Integer currentTripId) {

		// Create the trip object
		SOTripVO soTripVO = new SOTripVO();
		soTripVO.setSoId(soId);
		if(null!=currentTripId){
			soTripVO.setSoTripId(currentTripId);
		}
		if (!MPConstants.END.equalsIgnoreCase(action)){
			soTripVO.setCurrentApptStartDate(so.getServiceDate1());
			soTripVO.setCurrentApptEndDate(so.getServiceDate2());
			soTripVO.setCurrentApptStartTime(so.getServiceTimeStart());
			soTripVO.setCurrentApptEndTime(so.getServiceTimeEnd());
		}
		if (MPConstants.OPEN.equalsIgnoreCase(action)) {
			soTripVO.setTripStartSource(MPConstants.MOBILE);
			soTripVO.setTripStatus(MPConstants.OPEN);
			soTripVO.setTripNo(tripNo + 1); // Assuming new trip
			soTripVO.setTripStartVisitId(latestVisitId);
			soTripVO.setModifiedBy(createdBy); 
			soTripVO.setCreatedBy(createdBy);
		} else if (MPConstants.UPDATE.equalsIgnoreCase(action)) {
			soTripVO.setTripStartVisitId(latestVisitId);
			soTripVO.setTripNo(tripNo);
			soTripVO.setModifiedBy(createdBy);
		} else if (MPConstants.END.equalsIgnoreCase(action)) {
			soTripVO.setTripStatus(MPConstants.END);
			soTripVO.setModifiedBy(createdBy);
			soTripVO.setTripNo(tripNo);
			soTripVO.setTripEndSource(MPConstants.MOBILE);
			soTripVO.setTripEndVisitId(latestVisitId);
		}
		return soTripVO;
	}
	
	/**
	 * This method returns the most recent previous trip number
	 * 
	 * @param serviceOrder
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer closeProvAPITrip(ServiceOrder serviceOrder,MobileSOOnsiteVisitVO onsiteVisitVO) throws BusinessServiceException {
		
		SOTripVO tripVO = null;
		Integer currenttripNo = 0;
		try {
			tripVO = manageScheduleDao.fetchLatestValidTripForSO(serviceOrder.getSoId());
			
			tripVO = mapServiceOrderTrip(serviceOrder.getSoId(), onsiteVisitVO, tripVO.getTripEndVisitId(), tripVO.getTripNo(), serviceOrder,
					onsiteVisitVO.getModifiedBy(), MPConstants.END, tripVO.getSoTripId());
			currenttripNo = manageScheduleDao.updateCurrentTrip(tripVO);
			if (null != tripVO) {
				currenttripNo = tripVO.getTripNo();
			}
		} catch (DataServiceException e) {
			e.printStackTrace();
			throw new BusinessServiceException(e.getMessage(), e);
		}
		return currenttripNo;
	}
}
