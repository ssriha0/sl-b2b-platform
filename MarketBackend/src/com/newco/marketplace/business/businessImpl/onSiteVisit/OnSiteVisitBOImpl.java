package com.newco.marketplace.business.businessImpl.onSiteVisit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.RevisitNeededInfoVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitResultVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.dto.vo.serviceorder.SOTripDetailsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.onsiteVisit.IOnsiteVisitDao;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;

public class OnSiteVisitBOImpl implements IOnSiteVisitBO {
  	
	
	private static final Logger logger = Logger
	.getLogger(OnSiteVisitBOImpl.class.getName());
	
	private  IOnsiteVisitDao onsitevisitdao;
	
	
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO.getTimeOnSiteResults(java.lang.String)
	 */
	
	public List<SOOnsiteVisitVO> getTimeOnSiteResults(String soId)	throws BusinessServiceException{
		
		List<SOOnsiteVisitVO> results;
		
		
		
		try {
		results =onsitevisitdao.getVisitResults(soId);
		
		} catch (DataServiceException e) {
			logger.error("OnSiteVisitBOImpl :Unable to get the OnsiteVisit records  for thr given ServiceOrderID :"+ soId);
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
		return results;
	}
	
	// R12.0 Sprint 3 : Time On Site changes to incorporate trip.
	public List<SOOnsiteVisitResultVO> getTimeOnSiteRecords(String soId)	throws BusinessServiceException{
		List<SOOnsiteVisitResultVO> timeOnSiteRecords = null;
		try {
			// Method to fetch records in so_onsite_visit which are prior to trip.
			timeOnSiteRecords = onsitevisitdao.getTimeOnSiteRecords(soId);
			if(null != timeOnSiteRecords && !timeOnSiteRecords.isEmpty()){
				for(SOOnsiteVisitResultVO onsiteVisitResultVO : timeOnSiteRecords){
					if(null == onsiteVisitResultVO.getResName()){
						if(null != onsiteVisitResultVO.getArrivalResourceId()){
							onsiteVisitResultVO.setResName(fetchUserName(onsiteVisitResultVO.getArrivalResourceId().toString()));
						}else if(null != onsiteVisitResultVO.getDepartureResourceId()){
							onsiteVisitResultVO.setResName(fetchUserName(onsiteVisitResultVO.getDepartureResourceId().toString()));
						}else{
							onsiteVisitResultVO.setResName("");
						}
					}
				}
			}
		} catch (DataServiceException e) {
			logger.error("OnSiteVisitBOImpl :Unable to get the OnsiteVisit records  for the given ServiceOrderID :"+ soId);
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return timeOnSiteRecords;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO.getTimeOnSiteResults(java.lang.String)
	 */
	
	public SOOnsiteVisitVO getTimeOnSiteRecord(String visitId)	throws BusinessServiceException{
		
		SOOnsiteVisitVO result;
		
		
		
		try {
		result =onsitevisitdao.getVisitIDResult(visitId);
		
		} catch (DataServiceException e) {
			logger.error("OnSiteVisitBOImpl :Unable to get the OnsiteVisit records  for thr given VISIT ID ");
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
		return result;
	}
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO.UpdateTimeOnSiteRow(com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO)
	 */
	
	
	public void UpdateTimeOnSiteRow(SOOnsiteVisitVO soOnsiteVisitVO) throws BusinessServiceException{
		
		try {
			onsitevisitdao.UpdateVisitResult(soOnsiteVisitVO);
			
			
		} catch (Exception e) {
			logger.error("OnSiteVisitBOImpl :Unable to update the OnsiteVisit record ");
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
		
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO.InsertVisitResult(com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO)
	 */
	
	
	
	public void  InsertVisitResult(SOOnsiteVisitVO soOnsiteVisitVO) throws BusinessServiceException{
		
		try {
			onsitevisitdao.InsertVisitResult(soOnsiteVisitVO);
			
			
		} catch (Exception e) {
			
			logger.error("OnSiteVisitBOImpl :Unable to insert the OnsiteVisit record  ");
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}


	   /* (non-Javadoc)
		 * @see com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO.getVisitResourceName(java.lang.Integer)
		 */
		
		public Contact getVisitResourceName(Integer resourceId)	throws BusinessServiceException{
			
			Contact result;
			
			
			
			try {
			result =onsitevisitdao.getVisitResourceName(resourceId);
			
			} catch (DataServiceException e) {
				logger.error("OnSiteVisitBOImpl :Unable to get the Contact records  for thr given RESOURCE ID ");
				throw new BusinessServiceException(e.getMessage(),e);
			}
			
			return result;
		}


	public IOnsiteVisitDao getOnsitevisitdao() {
		return onsitevisitdao;
	}




	public void setOnsitevisitdao(IOnsiteVisitDao onsitevisitdao) {
		this.onsitevisitdao = onsitevisitdao;
	}




	public SOOnsiteVisitVO insert(SOOnsiteVisitVO outSOOnSiteVisitVO) throws BusinessServiceException {
		try {
			return onsitevisitdao.insert(outSOOnSiteVisitVO);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}




	public List<SOOnsiteVisitVO> selectSOOnSiteVisit(InOutVO inOutVisit) throws BusinessServiceException {
		try {
			return onsitevisitdao.selectSOOnSiteVisit(inOutVisit);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}




	public void updateSOOnSiteVisit(InOutVO inOutwhereVisit) throws BusinessServiceException {
		try {
			onsitevisitdao.updateSOOnSiteVisit(inOutwhereVisit);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}
	
public SOOnsiteVisitVO getTimeOnSiteResultBySoIDVisitID(SOOnsiteVisitVO vo)	throws BusinessServiceException{
		
		SOOnsiteVisitVO result = null;
		
		
		
		try {
		result =onsitevisitdao.getTimeOnSiteResultBySoIDVisitID(vo);
		
		} catch (DataServiceException e) {
			logger.error("OnSiteVisitBOImpl :Unable to get the OnsiteVisit records  for thr given ServiceOrderID :"+ vo.getSoId());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
		return result;
	}

/**
 * Description:Method to validate time onsite entries.
 */
	public SOOnsiteVisitVO validateTimeOnsiteArrivalDeparture(String SoId)
			throws BusinessServiceException {
		SOOnsiteVisitVO result = null;
		try {
			result = onsitevisitdao.validateTimeOnsiteArrivalDeparture(SoId);

		} catch (DataServiceException e) {
			logger.error("OnSiteVisitBOImpl :Unable to get the OnsiteVisit records  for the given ServiceOrderID :"
					+ SoId);
			throw new BusinessServiceException(e.getMessage(), e);
		}
		return result;
	}
	
	public Integer fetchLatestTripSO(String soId) throws BusinessServiceException{
		try {
			return onsitevisitdao.fetchLatestTripSO(soId);
		} catch (DataServiceException e) {
			logger.error("Error occured while latest trip fetch in IVR");
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
	
	public String fetchUserName(String resourceId) throws BusinessServiceException{
		try {
			return onsitevisitdao.fetchUserName(resourceId);
		} catch (DataServiceException e) {
			logger.error("Error occured while user name fetch in IVR");
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
	
	public void insertNewTripIVR(SOTripVO trip) throws BusinessServiceException{
		try {
			onsitevisitdao.insertNewTripIVR(trip);
		} catch (DataServiceException e) {
			logger.error("Error occured while create new trip IVR");
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
	public void updateTripIVR(SOTripVO trip) throws BusinessServiceException{
		try {
			onsitevisitdao.updateTripIVR(trip);
		} catch (DataServiceException e) {
			logger.error("Error occured while update trip IVR");
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
	public String findlatestTripStatus(String soId, int tripNo) throws BusinessServiceException{
		try {
			return onsitevisitdao.findlatestTripStatus(soId,tripNo);
		} catch (DataServiceException e) {
			logger.error("Error occured while latest trip status fetch in IVR");
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
	
	/**
	 * Description:Method to get the trip revisit/completion info details
	 */
		public RevisitNeededInfoVO getTripRevisitDetails(String soId, Integer soTripNo)
				throws BusinessServiceException {
			RevisitNeededInfoVO result = null;
			List<SOTripDetailsVO> tripDetails=null;
			try {
				result = onsitevisitdao.getTripRevisitDetails(soId,soTripNo);
				
				//To get list of trip details
				if(null != result.getSoTripId()){
					tripDetails=onsitevisitdao.getTripDetailsList(result.getSoTripId());	
				}
				
				
				if(null != tripDetails){
					List<String> td=new ArrayList<String>();
					for(SOTripDetailsVO tripDt:tripDetails)
					{
						if(null!=tripDt.getChangeType())
						{
							if(!td.contains(tripDt.getChangeType()))
								td.add(tripDt.getChangeType());
							
						}
					}
					result.setChangeTypes(td);
				}

			} catch (DataServiceException e) {
				logger.error("OnSiteVisitBOImpl :Unable to get the trip details for the given soTripNo :"
						+ soTripNo);
				throw new BusinessServiceException(e.getMessage(), e);
			}
			return result;
	}
		
		public SOOnsiteVisitVO findLatestOnsiteVisitEntry(String soId) throws BusinessServiceException{
			try {
				return onsitevisitdao.findLatestOnsiteVisitEntry(soId);
			} catch (DataServiceException e) {
				logger.error("Error occured while latest onsite visit status fetch");
				throw new BusinessServiceException(e.getMessage(), e);
			}
		}
		
		public boolean isRevisitNeededTrip(String soId, Integer currentTripNo) throws BusinessServiceException{
			try{
				return onsitevisitdao.isRevisitNeededTrip(soId,currentTripNo);
			}catch (DataServiceException e) {
				logger.error("Error occured while isRevisitNeededTrip fetch");
				throw new BusinessServiceException(e.getMessage(), e);
			}
		}
		
		// Method to fetch time on site reason codes.
		public List<String> fetchTimeOnSiteReasons() throws BusinessServiceException {
			try{
				return onsitevisitdao.fetchTimeOnSiteReasons();
			}catch (DataServiceException e) {
				logger.error("Error occured while fetchTimeOnSiteReasons fetch");
				throw new BusinessServiceException(e.getMessage(), e);
			}
		}
		// R12_0 Method to update substatus.
		public void updateSubStatusOfSO(String soId,Integer substatusValue)throws BusinessServiceException {
			try {
				onsitevisitdao.updateSubStatusOfSO(soId,substatusValue);
			} catch (DataServiceException e) {
				logger.error("Error occured in updateSubStatusOfSO");
				throw new BusinessServiceException(e.getMessage(), e);
			}
			
		}
}