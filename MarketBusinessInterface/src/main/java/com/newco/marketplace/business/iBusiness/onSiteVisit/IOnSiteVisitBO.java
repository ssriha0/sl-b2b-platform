package com.newco.marketplace.business.iBusiness.onSiteVisit;


import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitResultVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.dto.vo.serviceorder.RevisitNeededInfoVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;


 /**
  * @author pkoppis
  *
  *  
  */



public interface IOnSiteVisitBO {
	
	
	
		
		/**
		 * Returns a List of SOOnsiteVisitVO's  that are records  available for that particular ServiceOrderId.  The results
		 * are sorted according to the arrival date
		 * @param soId
		 * @return
		 * @throws DataServiceException
		 */
		
		public List<SOOnsiteVisitVO> getTimeOnSiteResults(String soId)	throws BusinessServiceException;
		
		/**
		 * R12.0 Sprint 3: Method to fetch the time on site data from so_trip table
		 * @param soId
		 * @return
		 * @throws BusinessServiceException
		 */
		public List<SOOnsiteVisitResultVO> getTimeOnSiteRecords(String soId) throws BusinessServiceException;
		
		
		/**
		 * Returns a SOOnsiteVisitVO  for a given visitid  
		 * @param soId
		 * @return
		 * @throws DataServiceException
		 */
		
		public SOOnsiteVisitVO getTimeOnSiteRecord(String visitId)	throws BusinessServiceException;
		
		
		/**
		 * Returns a Contact  for a given  resourceId 
		 * @param resourceId
		 * @return
		 * @throws DataServiceException
		 */
		
		public Contact getVisitResourceName(Integer resourceId)	throws BusinessServiceException;
	   
		
		/**
		 * Updates the record based on the values in SOOnsiteVisitVO 
		 * @param SOOnsiteVisitVO
		 * @return
		 * @throws DataServiceException
		 */
		
		
		
		public void UpdateTimeOnSiteRow(SOOnsiteVisitVO soOnsiteVisitVO) throws BusinessServiceException;
		  
	
		
		
		/**
		 * Inserts the record taking the values from SOOnsiteVisitVO 
		 * @param SOOnsiteVisitVO
		 * @return
		 * @throws DataServiceException
		 */
		
		public void  InsertVisitResult(SOOnsiteVisitVO soOnsiteVisitVO) throws BusinessServiceException;


		public SOOnsiteVisitVO insert(SOOnsiteVisitVO outSOOnSiteVisitVO) throws BusinessServiceException;


		public void updateSOOnSiteVisit(InOutVO inOutwhereVisit) throws BusinessServiceException;


		public List<SOOnsiteVisitVO> selectSOOnSiteVisit(InOutVO inOutVisit) throws BusinessServiceException; 
	
		public SOOnsiteVisitVO getTimeOnSiteResultBySoIDVisitID(SOOnsiteVisitVO vo)	throws BusinessServiceException;
		
		public SOOnsiteVisitVO validateTimeOnsiteArrivalDeparture (String SoId)throws BusinessServiceException;
		
		public RevisitNeededInfoVO getTripRevisitDetails(String soId,Integer soTripNo)throws BusinessServiceException;
	
		public Integer fetchLatestTripSO(String soId) throws BusinessServiceException;
		
		public String fetchUserName(String resourceId) throws BusinessServiceException;
		
		public void insertNewTripIVR(SOTripVO trip) throws BusinessServiceException;
		
		public void updateTripIVR(SOTripVO trip) throws BusinessServiceException;
		
		public String findlatestTripStatus(String soId, int tripNo) throws BusinessServiceException;
		
		public SOOnsiteVisitVO findLatestOnsiteVisitEntry(String soId) throws BusinessServiceException;
		
		public boolean isRevisitNeededTrip(String soId, Integer currentTripNo) throws BusinessServiceException;
		
		/**
		 * @return
		 * @throws BusinessServiceException
		 */
		public List<String> fetchTimeOnSiteReasons() throws BusinessServiceException;
		
		public void updateSubStatusOfSO(String soId, Integer substatusValue)throws BusinessServiceException;
		
}
