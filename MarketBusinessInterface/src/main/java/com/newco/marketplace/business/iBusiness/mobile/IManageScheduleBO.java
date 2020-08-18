package com.newco.marketplace.business.iBusiness.mobile;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.MobileSOOnsiteVisitVO;
import com.newco.marketplace.vo.mobile.ServiceOrder;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;

public interface IManageScheduleBO {

	/**
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOrder fetchServiceOrderForVisit(String soId)
			throws BusinessServiceException;

	/**
	 * 
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer fetchResourceVendorId(Integer resourceId)
			throws BusinessServiceException;

	/**
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public MobileSOOnsiteVisitVO fetchLatestArrivalForSOId(String soId)
			throws BusinessServiceException;

	/**
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean insertSoOnsiteVisit(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws BusinessServiceException;
	
	
	/**
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public long insertSoOnsiteVisitId(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws BusinessServiceException;

	/**
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean updateSoOnsiteVisit(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws BusinessServiceException;

	/**
	 * 
	 * @param scheduleVO
	 * @return
	 */
	public Integer editSOAppointmentTime(UpdateApptTimeVO scheduleVO);

	
	
	public UpdateApptTimeVO fetchServiceDatesAndTimeWndw(String soId);

	/**
	 * @param soId
	 * @return
	 */
	public Integer getSOStatus(String soId)throws BusinessServiceException ;
	
	public void updateSOSubStatus(String soId,String eventType)throws BusinessServiceException ;
	
	/**
	 * This method returns the most recent previous trip number
	 * 
	 * @param serviceOrder
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer closeProvAPITrip(ServiceOrder serviceOrder, MobileSOOnsiteVisitVO onsiteVisitVO) throws BusinessServiceException;
		
	
	
	/**
	 * @param serviceOrder
	 * @param createdBy
	 * @param onsiteVisitVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer fetchLatestValidTripForSO(ServiceOrder serviceOrder,
			String createdBy,MobileSOOnsiteVisitVO onsiteVisitVO)
			throws BusinessServiceException;
	
	
	/**
	 * @param serviceOrder
	 * @param createdBy
	 * @param onsiteVisitVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer closeCurrentTrip(ServiceOrder serviceOrder,
			String createdBy,MobileSOOnsiteVisitVO onsiteVisitVO,Integer currentTripId)
			throws BusinessServiceException;
	
	
	/**
	 * @param curTripNo
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateTrip(Integer curTripNo,String soId)
			throws BusinessServiceException;
	
}
