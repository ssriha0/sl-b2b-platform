package com.newco.marketplace.persistence.daoImpl.mobile;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.mobile.IMangeScheduleDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.mobile.MobileSOOnsiteVisitVO;
import com.newco.marketplace.vo.mobile.ServiceOrder;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class ManageScheduleDAOImpl extends ABaseImplDao implements
		IMangeScheduleDao {

	private static final Logger logger = Logger
			.getLogger(ManageScheduleDAOImpl.class.getName());

	/**
	 * Fetches the service order details for Time on site mobile API
	 * 
	 * @param serviceOrderID
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrder fetchServiceOrderForVisit(String serviceOrderID)
			throws DataServiceException {
		// initializing variables
		ServiceOrder serviceOrder = null;
		try {
			// select query to get only required fields from SO
			serviceOrder = (ServiceOrder) queryForObject(
					"mobileSOManageSchedule.onSiteVisit.so.query",
					serviceOrderID);
		} catch (Exception e) {
			// any exception throw as DataServiceException to BO
			logger.info("[mobileSOManageSchedule.onSiteVisit.so.query - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(), e);
		}
		return serviceOrder;
	}

	/**
	 * Fetches the vendor Id for the provider resource Id
	 * 
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer fetchResourceVendorId(Integer resourceId)
			throws DataServiceException {
		// initializing variables
		Integer result = null;
		try {

			// query to get the vendor Id for the resource id given
			result = (Integer) queryForObject(
					"mobileSOManageSchedule.onSiteVisit.vendorResource.query",
					resourceId);

		} catch (Exception e) {
			// any exception throw as DataServiceException to BO
			logger.info("[mobileSOManageSchedule.onSiteVisit.vendorResource.query - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Fetches the latest arrival record for the so Id from the so_onsite_visit
	 * table
	 * 
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public MobileSOOnsiteVisitVO fetchLatestArrivalForSOId(String soId)
			throws DataServiceException {
		// initializing variables
		List<MobileSOOnsiteVisitVO> results = null;
		MobileSOOnsiteVisitVO lastVisit = null;
		try {

			// select query to get list of arrivals for the soId
			results = queryForList(
					"mobileSOManageSchedule.onSiteVisit.latestArrival.query",
					soId);

			// get the latest arrival from the list
			if (results != null && !results.isEmpty()) {
				lastVisit = results.get(0);
			}

		} catch (Exception e) {
			// any exception throw as DataServiceException to BO
			logger.info("[mobileSOManageSchedule.onSiteVisit.latestArrival.query - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(), e);
		}

		return lastVisit;
	}

	/**
	 * Inserts a record into so_onsite_visit table
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws DataServiceException
	 */
	public long insertSOOnsiteVisitDetails(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws DataServiceException {
		// initializing variables
		long visitId = 0;
		try {
			// query to insert the arival/departure details in so_onsite_visit
			visitId = (Long) insert(
					"mobileSOManageSchedule.onSiteVisit.insert",
					soOnsiteVisitVO);

		} catch (Exception e) {
			// any exception throw as DataServiceException to BO
			logger.info("[mobileSOManageSchedule.onSiteVisit.insert - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(), e);
		}
		// return the inserted visit Id
		return visitId;
	}

	/**
	 * Updates the departure details in a so_onsite_visit record
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws DataServiceException
	 */
	public int updateSOOnsiteVisitDetails(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws DataServiceException {
		try {
			// query to update the departure details in so_onsite_visit table
			return update("mobileSOManageSchedule.onSiteVisit.update",
					soOnsiteVisitVO);

		} catch (Exception e) {
			// any exception throw as DataServiceException to BO
			logger.info("[mobileSOManageSchedule.onSiteVisit.update - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param scheduleVO
	 * @return
	 */
	public Integer editSOAppointmentTime(UpdateApptTimeVO scheduleVO) {
		Integer updateStatus = 0;
		try {
			// update appointment time in so_hdr
			updateStatus = update
				("mobileSOManageSchedule.updateAppointmentTime.query",
				scheduleVO);
			// R12.0 Sprint 2 update current appointment details with service date when editing appointment date.
			SOTripVO tripNo = fetchLatestValidTripForSO(scheduleVO.getSoId());
			if(null != tripNo){
				scheduleVO.setTripNo(tripNo.getTripNo());
				update("mobileSOManageSchedule.updateSOTrip.query", scheduleVO);
			}
			// If there are no entries in the so_schedule table, 
			// create a new entry, else update the existing entry
			String soId=scheduleVO.getSoId();
			Integer currentScheduleStatusId = (Integer)queryForObject
				("mobileSOManageSchedule.scheduleStatusId.query",soId);	
			
			Integer newScheduleStatusId = scheduleVO.getScheduleStatusId();			
			if((null==currentScheduleStatusId || (null!=currentScheduleStatusId && 
					0 == currentScheduleStatusId.intValue()))
					&& (null!= newScheduleStatusId && 0 != newScheduleStatusId)){
				
				// insert so_schedule
				insert("mobileSOManageSchedule.insertSchedule.query",
					scheduleVO);
				
				// create the history for first time
				insert("mobileSOManageSchedule.insertPrecallhistory.query",
					scheduleVO);
			}else if(null!=currentScheduleStatusId && currentScheduleStatusId.intValue()!=0 && 
					null!= newScheduleStatusId && 0 != newScheduleStatusId){			
				
				// update appointment time in so_schedule			
				updateStatus = update("mobileSOManageSchedule.updateSchedule.query",
					scheduleVO);
				
				//create the new so_schedule_history for the new record
				insert("mobileSOManageSchedule.insertPrecallhistory.query",
					scheduleVO);
			}
			return updateStatus;
		} catch (Exception e) {
			logger.error("Exception occured in mobileSOManageScheduleDaoImpl.editSOAppointmentTime() due to "
					+ e.getMessage());
			// throw new DataServiceException(e.getMessage(),e);
			return 0;
		}
	}
	
	public UpdateApptTimeVO fetchServiceDatesAndTimeWndw(String soId){
		UpdateApptTimeVO vo = new UpdateApptTimeVO();
		try{
		vo = (UpdateApptTimeVO)queryForObject("mobileSOManageSchedule.fetchServiceDates.query", soId);
		}catch(Exception e){
			logger.error("Exception occured in mobileSOManageScheduleDaoImpl.fetchServiceDates() due to "+e.getMessage());
		}
		return vo;
	}

	public Integer getSOStatus(String soId) throws DataServiceException{
		Integer status = null;
		try {
			status = (Integer)queryForObject("mobileSOManageSchedule.fetchSOStatus.query", soId);
				
		}catch (Exception e) {
			throw new DataServiceException(e.getMessage(), e);
		}
		return status;
	}

	public void updateSOSubStatus(String soId, String eventType)
			throws BusinessServiceException {
		HashMap<String, Object> params =  new HashMap<String, Object>();
		params.put("soid", soId);
		if(MPConstants.EVENT_TYPE_ARRIVAL.equals(eventType)){
			params.put("subStatusid", MPConstants.PROVIDER_ON_SITE_SUBSTATUS_ID);	
			update("soSubStatusData.update",params);
		}/*else if(MPConstants.EVENT_TYPE_DEPARTURE.equals(eventType)){
			params.put("subStatusid", null);	
		}*/
	}
	
	/**
	 * Fetch the valid trip
	 */
	public SOTripVO fetchLatestValidTripForSO(String soId) throws DataServiceException{
		SOTripVO tripVO = null;
		try {
			tripVO = (SOTripVO)queryForObject("mobileSOManageSchedule.fetchSOtrip.query", soId);				
		}catch (Exception e) {
			throw new DataServiceException(e.getMessage(), e);
		}
		return tripVO;
	}
	
	/**
	 * Fetch the latest Visit Id
	 */
	public Long fetchLatestVisitId(String soId) throws DataServiceException{
		Long visitId = null;
		try {
			visitId = (Long)queryForObject("mobileSOManageSchedule.onSiteVisit.latestArrivalId.query", soId);				
		}catch (Exception e) {
			throw new DataServiceException(e.getMessage(), e);
		}
		return visitId;
	}
	
	/**
	 * Create a new trip
	 */
	public Integer createNewTrip(SOTripVO tripVO)throws DataServiceException{
		Integer tripNo = 0;
		Integer soTripId = null;
		try {
			soTripId = (Integer) insert(
					"mobileSOManageSchedule.soTrip.insert",	tripVO);
			if(null!=soTripId){
				tripNo = tripVO.getTripNo();
			}
		}catch (Exception e) {
			throw new DataServiceException(e.getMessage(), e);
		}
		return tripNo;
	}
	
	/**
	 * Update trip
	 */
	public Integer updateCurrentTrip(SOTripVO tripVO)throws DataServiceException{
		Integer tripNo = 0;
		Integer soTripId = null;
		try {
			soTripId = update("mobileSOManageSchedule.soTrip.update",tripVO);
			if(null!=soTripId){
				tripNo = tripVO.getTripNo();
			}
		}catch (Exception e) {
			throw new DataServiceException(e.getMessage(), e);
		}
		return tripNo;
	}
	
	
	/**
	 * Fetch the latest Visit Id
	 */
	public Integer validateTrip(Integer currentTrip, String soId) throws DataServiceException{
		Integer soTripId = null;
		try {
			HashMap<String, Object> params =  new HashMap<String, Object>();
			params.put("soId", soId);
			params.put("tripNo", currentTrip);
			soTripId = (Integer)queryForObject("mobileSOManageSchedule.validateTrip.query", params);
		}catch (Exception e) {
			throw new DataServiceException(e.getMessage(), e);
		}
		return soTripId;
	}
	
}
