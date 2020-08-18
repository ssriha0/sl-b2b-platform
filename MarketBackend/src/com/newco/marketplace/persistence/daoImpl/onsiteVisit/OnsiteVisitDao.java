package com.newco.marketplace.persistence.daoImpl.onsiteVisit;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitResultVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao;
import org.apache.log4j.Logger;

//import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;

import com.newco.marketplace.dto.vo.serviceorder.RevisitNeededInfoVO;
import com.newco.marketplace.dto.vo.serviceorder.SOTripDetailsVO;


/**
 * @author pkoppis
 *
 */

public class OnsiteVisitDao extends ABaseImplDao implements
IOnsiteVisitDao {
	
	
	private static final Logger logger = Logger
	.getLogger(OnsiteVisitDao.class.getName());
	
//	RandomGUID random = new RandomGUID();
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao#getVisitResults(java.lang.String)
	 */
	public List<SOOnsiteVisitVO> getVisitResults(String soId)
			throws DataServiceException {
		
		if (null == soId) {
			throw new DataServiceException("soId cannot be null");
		}
		
		List<SOOnsiteVisitVO> results;
		try {
			
			results = queryForList("soOnsiteVisit.query.select",soId);
			
		}  catch (Exception e) {
			logger.info("[soOnsiteVisit.query.select - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}

		return results;
	}
	
	public List<SOOnsiteVisitResultVO> getTimeOnSiteRecords(String soId) throws DataServiceException {
		List<SOOnsiteVisitResultVO> results;
		try {
			results = queryForList("timeOnSiteDetails.query.select",soId);
		}  catch (Exception e) {
			logger.error("[timeOnSiteDetails.query.select - Exception] " + e);
			throw new DataServiceException(e.getMessage(),e);
		}
		return results;
	}
	
	// Method to fetch records in so_onsite_visit which are prior to trip.
	public List<SOOnsiteVisitResultVO> getOnsiteVisitRecords(String soId, Long firstTripVisitId) throws DataServiceException{
		List<SOOnsiteVisitResultVO> results;
		try {
			HashMap<String, Object> params =  new HashMap<String, Object>();
			params.put("soId", soId);
			params.put("firstTripVisitId", firstTripVisitId);
			results = queryForList("onSiteVisitRecords.query.select",params);
		}  catch (Exception e) {
			logger.error("[onSiteVisitRecords.query.select - Exception] " + e);
			throw new DataServiceException(e.getMessage(),e);
		}
		return results;
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao#getVisitResult(java.lang.String)
	 */
	public SOOnsiteVisitVO getVisitIDResult(String visitId)
			throws DataServiceException {
		
		if (null == visitId) {
			throw new DataServiceException("visitId cannot be null");
		}
		
		SOOnsiteVisitVO result;
		try {
		
			result =(SOOnsiteVisitVO)queryForObject("soOnsiteVisit.query.selectvisitid",visitId);
			
		}  catch (Exception e) {
			logger.info("[soOnsiteVisit.query.select - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}

		return result;
	}
	
 
	

    /* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao#getVisitResourceName(java.lang.Integer)
	 */
	public Contact getVisitResourceName(Integer resourceId)
			throws DataServiceException {
		
		if (null == resourceId) {
			throw new DataServiceException("resourceId cannot be null");
		}
		
		Contact ResourceContact;
		try {
			
			ResourceContact =(Contact) queryForObject("soOnsiteVisit.query.fnamelastname",resourceId);
			
		}  catch (Exception e) {
			logger.info("[soOnsiteVisit.query.fnamelastname - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}

		return ResourceContact;
	}
	
	
  	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao#UpdateVisitResult(SOOnsiteVisitVO)
	 */
	public SOOnsiteVisitVO  UpdateVisitResult(SOOnsiteVisitVO SOOnsiteVisitVO)
			throws DataServiceException {
		
		if (null == SOOnsiteVisitVO) {
			throw new DataServiceException("SOOnsiteVisitVO cannot be null");
		}
		
	
		try {
		
			update("soOnsiteVisit.query.update", SOOnsiteVisitVO);
			
		} catch (Exception e) {
			logger.info("[soOnsiteVisit.query.update - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}

	  return null;	
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao#InsertVisitResult(SOOnsiteVisitVO)
	 */
	public SOOnsiteVisitVO  InsertVisitResult(SOOnsiteVisitVO SOOnsiteVisitVO)
			throws DataServiceException {
		
		if (null == SOOnsiteVisitVO) {
			throw new DataServiceException("SOOnsiteVisitVO cannot be null");
		}
		
	
		try {
			
//			SOOnsiteVisitVO.setVisitId(random.generateGUID());
			insert("soOnsiteVisit.query.insert", SOOnsiteVisitVO);
			
		}catch (Exception e) {
			logger.info("soOnsiteVisit.query.insert- Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}

	  return null;	
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.ISOEventDao#updateSOOnSiteVisit(com.newco.marketplace.dto.vo.serviceorder.InOutVO)
	 */
	public int updateSOOnSiteVisit(InOutVO inOutVO) throws DataServiceException {
		return update("soOnSiteVisit.update",inOutVO);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.ISOEventDao#insert(com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO)
	 */
	public SOOnsiteVisitVO insert(SOOnsiteVisitVO soOnsiteVisitVO)
			throws DataServiceException {
		try {
//			SOOnsiteVisitVO.setVisitId(random.generateGUID());

			Long visitId = (Long) insert("soOnSiteVisit.insert",soOnsiteVisitVO);
			soOnsiteVisitVO.setVisitId(visitId);
			return soOnsiteVisitVO;
		} catch (Exception e) {
			logger.error("soOnSiteVisit.insert- Exception] ");
			throw new DataServiceException(e.getMessage(),e);
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.ISOEventDao#select(com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO)
	 */
	public List<SOOnsiteVisitVO> selectSOOnSiteVisit(InOutVO inOutVO) throws DataServiceException {
		return queryForList("soOnSiteVisit.select",inOutVO);
	}
	public SOOnsiteVisitVO getTimeOnSiteResultBySoIDVisitID(SOOnsiteVisitVO vo)	throws DataServiceException {

		

		SOOnsiteVisitVO result =null;
		try {
	
			result = (SOOnsiteVisitVO)queryForObject("soOnsiteVisit.query.selectbySoId.visitId",vo);
	
		}  catch (Exception e) {
			logger.info("[soOnsiteVisit.query.select - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}

		return result;
	}
	
	/**
	 * Description:Method to validate time onsite entries
	 */
	public SOOnsiteVisitVO validateTimeOnsiteArrivalDeparture(String SoId)throws DataServiceException{
		SOOnsiteVisitVO result =null;
		try {
	
			result = (SOOnsiteVisitVO)queryForObject("soOnSiteVisit.valiadteTimeOnSite.select",SoId);
	
		}  catch (Exception e) {
			logger.info("[soOnSiteVisit.valiadteTimeOnSite.select - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}

		return result;
	}
	
	public Integer fetchLatestTripSO(String soId) throws DataServiceException{
		Integer latestTrip = null;
		try{
			latestTrip=  (Integer) queryForObject("soOnSiteVisit.fetchLatestSOtrip.query",soId);
		}catch (Exception e) {
			logger.info("[soOnSiteVisit.fetchLatestSOtrip.query - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}
		return latestTrip;
	}
	
	public String fetchUserName(String resourceId) throws DataServiceException{
		try{
			return (String) queryForObject("soOnSiteVisit.fetchResourceId.query",resourceId);
		}catch (Exception e) {
			logger.info("[soOnSiteVisit.fetchResourceId.query - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	public void insertNewTripIVR(SOTripVO trip) throws DataServiceException{
		try {
			insert("soTrip.IVR.insert",trip);
		} catch (Exception e) {
			logger.error("soTrip.IVR.insert- Exception] ");
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	public void updateTripIVR(SOTripVO trip) throws DataServiceException{
		try {
			insert("soTrip.IVR.update",trip);
		} catch (Exception e) {
			logger.error("soTrip.IVR.update- Exception] ");
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	public String findlatestTripStatus(String soId, int tripNo) throws DataServiceException{
		String latestTripStatus = null;
		try{
			HashMap<String, Object> params =  new HashMap<String, Object>();
			params.put("soId", soId);
			params.put("tripNo", tripNo);
			
			latestTripStatus=  (String) queryForObject("soOnSiteVisit.fetchLatestSOtripStatus.query",params);
		}catch (Exception e) {
			logger.info("[soOnSiteVisit.fetchLatestSOtrip.query - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}
		return latestTripStatus;
	}
	
	/**
	 * Description:Method to get trip revisit/completion info
	 */
	public RevisitNeededInfoVO getTripRevisitDetails(String soId, Integer soTripNo)throws DataServiceException{
		RevisitNeededInfoVO result =null;
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("soTripNo", soTripNo);
		try {
	
			result = (RevisitNeededInfoVO)queryForObject("soOnSiteVisit.query.tripRevisitDetails",params);
	
		}  catch (Exception e) {
			logger.error("[soOnSiteVisit.query.tripRevisitDetails - Exception] "+ e);
			throw new DataServiceException(e.getMessage(),e);
		}

		return result;
	}
	
	
	/**
	 * Description:Method to get soTripDetails from so_trip_details
	 */
	@SuppressWarnings("unchecked")
	public List<SOTripDetailsVO> getTripDetailsList(Integer soTripId)throws DataServiceException
	{
		List<SOTripDetailsVO> tripDetailsList=null;
		try {
			
			tripDetailsList = (List<SOTripDetailsVO>)queryForList("soOnsiteVisit.query.tripDetails",soTripId);
	
		}  catch (Exception e) {
			logger.error("[soOnSiteVisit.query.tripDetails - Exception] "+ e);
			throw new DataServiceException(e.getMessage(),e);
		}
		return tripDetailsList;
	}
	
	
	public SOOnsiteVisitVO findLatestOnsiteVisitEntry(String soId) throws DataServiceException{
		SOOnsiteVisitVO latestOnsiteVisitEntry = null;
		try{
	
			latestOnsiteVisitEntry =  (SOOnsiteVisitVO) queryForObject("soOnsiteVisit.query.fetchLatestOnsiteVisitEntry",soId);
		}catch (Exception e) {
			logger.info("[soOnsiteVisit.query.fetchLatestOnsiteVisitEntry - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}
		return latestOnsiteVisitEntry;
	}
	
	public boolean isRevisitNeededTrip(String soId, Integer currentTripNo) throws DataServiceException{
		String latestTripId = null;
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("currentTripNo", currentTripNo);
		
		try{
			latestTripId = (String) queryForObject("soOnsiteVisit.isRevisitNeededTrip.query",params);
			if(null != latestTripId){
				return true;
			}
		}catch (Exception e) {
			logger.info("[soOnsiteVisit.query.isRevisitNeededTrip - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}
		return false;
	}
	
	// Method to fetch time on site reason codes.
	public List<String> fetchTimeOnSiteReasons() throws DataServiceException {
		List<String> results = null;
		try{
			results = (List<String>)queryForList("soOnsiteVisit.fetchTimeOnSiteReasons.query");
		}catch (Exception e) {
			logger.error("[soOnsiteVisit.fetchTimeOnSiteReasons.query - Exception] " + e);
			throw new DataServiceException(e.getMessage(),e);
		}
		return results;
	}
	// R12_0 Method to update substatus.
	public void updateSubStatusOfSO(String soId,Integer substatusValue) throws DataServiceException {
		Map<String,Object> substatusMap=new HashMap<String, Object>();
		substatusMap.put("soId",soId);
		substatusMap.put("substatusValue",substatusValue);
		try {
			
			update("updateSubStatusOfSO.query.update", substatusMap);
			
		} catch (Exception e) {
			logger.info("[updateSubStatusOfSO.query.update - Exception] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException(e.getMessage(),e);
		}
		
	}
}
