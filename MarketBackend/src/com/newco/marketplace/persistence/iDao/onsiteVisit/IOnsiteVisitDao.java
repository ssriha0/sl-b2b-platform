
package com.newco.marketplace.persistence.iDao.onsiteVisit;


import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitResultVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;
import com.newco.marketplace.dto.vo.serviceorder.RevisitNeededInfoVO;
import com.newco.marketplace.dto.vo.serviceorder.SOTripDetailsVO;





public interface IOnsiteVisitDao {
	
	
	/**
	 * Returns a List of SOOnsiteVisitVO's  that are records  available for that particular ServiceOrderId.  The results
	 * are sorted according to the arrival date
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	
	public List<SOOnsiteVisitVO> getVisitResults(String soId)	throws DataServiceException;
	
	/**
	 * R12.0 Sprint 3: Method to fetch the time on site data from so_trip table
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SOOnsiteVisitResultVO> getTimeOnSiteRecords(String soId)	throws DataServiceException;
	
	/**
	 * Returns a  SOOnsiteVisitVO  that matches ServiceOrderId and visit id
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	
	public SOOnsiteVisitVO getVisitIDResult(String visitId)	throws DataServiceException;
	
	

	/**
		 * Returns a  Contact  that matches ServiceOrderId and visit id
		 * @param resourceID
		 * @return
		 * @throws DataServiceException
		 */
		
		public Contact getVisitResourceName(Integer resourceIdInteger)	throws DataServiceException;
	
	
	
	/**
	 * Updates the record based on the values in SOOnsiteVisitVO 
	 * @param SOOnsiteVisitVO
	 * @return
	 * @throws DataServiceException
	 */
	
	
	
	public SOOnsiteVisitVO  UpdateVisitResult(SOOnsiteVisitVO SOOnsiteVisitVO) throws DataServiceException;
	
	/**
	 * Inserts the record taking the values from SOOnsiteVisitVO 
	 * @param SOOnsiteVisitVO
	 * @return
	 * @throws DataServiceException
	 */
	
	public SOOnsiteVisitVO  InsertVisitResult(SOOnsiteVisitVO SOOnsiteVisitVO) throws DataServiceException; 
	
	
	List<SOOnsiteVisitVO>  selectSOOnSiteVisit(InOutVO inOutVO) throws DataServiceException;
	
	SOOnsiteVisitVO insert(SOOnsiteVisitVO SOOnsiteVisitVO) throws DataServiceException;

	int updateSOOnSiteVisit(InOutVO inOutVO) throws DataServiceException;	
	
	public SOOnsiteVisitVO getTimeOnSiteResultBySoIDVisitID(SOOnsiteVisitVO vo)	throws DataServiceException;
	
	public SOOnsiteVisitVO validateTimeOnsiteArrivalDeparture(String SoId)throws DataServiceException;
	
	public Integer fetchLatestTripSO(String soId) throws DataServiceException;
	
	public String fetchUserName(String resourceIdId) throws DataServiceException;
	
	public void insertNewTripIVR(SOTripVO trip) throws DataServiceException;
	
	public void updateTripIVR(SOTripVO trip) throws DataServiceException;
	
	public String findlatestTripStatus(String soId, int tripNo) throws DataServiceException;
	
	public RevisitNeededInfoVO getTripRevisitDetails(String soId,Integer soTripNo)throws DataServiceException;
	
	public List<SOTripDetailsVO> getTripDetailsList(Integer soTripId)throws DataServiceException;
	
	public SOOnsiteVisitVO findLatestOnsiteVisitEntry(String soId) throws DataServiceException;
	
	public boolean isRevisitNeededTrip(String soId, Integer currentTripNo) throws DataServiceException;
	
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<String> fetchTimeOnSiteReasons() throws DataServiceException;

	public List<SOOnsiteVisitResultVO> getOnsiteVisitRecords(String soId, Long firstTripVisitId) throws DataServiceException;
	
	public void updateSubStatusOfSO(String soId, Integer substatusValue)throws DataServiceException;

}
