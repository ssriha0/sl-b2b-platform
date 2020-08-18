package com.newco.marketplace.persistence.iDao.mobile;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.MobileSOOnsiteVisitVO;
import com.newco.marketplace.vo.mobile.ServiceOrder;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;

public interface IMangeScheduleDao {

	/**
	 * Fetches the service order details for Time on site mobile API
	 * 
	 * @param serviceOrderID
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrder fetchServiceOrderForVisit(String serviceOrderID)
			throws DataServiceException;

	/**
	 * Fetches the vendor Id for the provider resource Id
	 * 
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer fetchResourceVendorId(Integer resourceId)
			throws DataServiceException;

	/**
	 * Fetches the latest arrival record for the so Id from the so_onsite_visit
	 * table
	 * 
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public MobileSOOnsiteVisitVO fetchLatestArrivalForSOId(String soId)
			throws DataServiceException;

	/**
	 * Inserts a record into so_onsite_visit table
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws DataServiceException
	 */
	public long insertSOOnsiteVisitDetails(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws DataServiceException;

	/**
	 * Updates the departure details in a so_onsite_visit record
	 * 
	 * @param soOnsiteVisitVO
	 * @return
	 * @throws DataServiceException
	 */
	public int updateSOOnsiteVisitDetails(MobileSOOnsiteVisitVO soOnsiteVisitVO)
			throws DataServiceException;

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
	public Integer getSOStatus(String soId) throws DataServiceException;
	
	public void updateSOSubStatus(String soId,String eventType)throws BusinessServiceException ;
	
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public SOTripVO fetchLatestValidTripForSO(String soId)throws DataServiceException;
	
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public Long fetchLatestVisitId(String soId)throws DataServiceException;
	
	/**
	 * @param tripVO
	 * @return
	 * @throws DataServiceException
	 */
	public Integer createNewTrip(SOTripVO tripVO)throws DataServiceException;
	
	
	/**
	 * @param tripVO
	 * @return
	 * @throws DataServiceException
	 */
	public Integer updateCurrentTrip(SOTripVO tripVO)throws DataServiceException;
	
	/**
	 * @param currentTrip
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer validateTrip(Integer currentTrip,String soId)throws DataServiceException;

}
