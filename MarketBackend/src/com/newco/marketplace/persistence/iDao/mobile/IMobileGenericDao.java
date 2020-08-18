package com.newco.marketplace.persistence.iDao.mobile;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.beans.so.assignSO.AssignVO;
import com.newco.marketplace.api.mobile.beans.vo.ProviderParamVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersCriteriaVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersVO;
import com.newco.marketplace.api.mobile.beans.vo.RequestBidVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.v2_0.SOAdvanceSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultsVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RescheduleDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.ServiceOrderDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.criteria.vo.SearchCriteriaVO;
import com.newco.marketplace.api.criteria.vo.SoSearchCriteriaVO;
import com.newco.marketplace.dto.vo.EstimateVO;


/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/21/10
 * Dao layer for processing phase 2 mobile APIs 
 *
 */
public interface IMobileGenericDao {

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean checkIfSOIsActive(String soId)throws DataServiceException;

	/**
	 * @param searchRequestVO
	 * @return
	 */
	public List<SOSearchResultVO> getServiceOrderSearchResults(SOSearchCriteriaVO searchRequestVO)throws DataServiceException;

	public List<ServiceOrderStatusVO> getProblemTypeDescription(int problemStatus)throws DataServiceException;

	//R14.0 GetReasonCodes


	public List<String> getProviderRespReason() throws DataServiceException;

	public List<ReasonCodeVO> getRespReasonCodes(String reasonType)throws DataServiceException;
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean checkIfSOIsInProblem(String soId)throws DataServiceException;

	public String getProblemTypeDescription(String soId)throws DataServiceException;
	/**
	 * method to get the routed resources under the firm
	 * @param assignVO
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getRoutedResourcesUnderFirm(AssignVO assignVO)throws DataServiceException;

	public boolean checkIfReasonIsValid(String reasonCode)throws DataServiceException;

	/**
	 * @param bidVO
	 * @return
	 * @throws DataServiceException
	 */
	public RequestBidVO fetchServiceOrderDetailsForBid(RequestBidVO bidVO) throws DataServiceException;

	public RescheduleVO checkIfRescheduleRequestExists(RescheduleVO rescheduleVo) throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException 
	 */
	public String checkIfReadyForConfirmAppointment(String soId) throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 */
	public String checkIfReadyForPreCall(String soId)throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 */
	public ServiceOrder getServiceOrderAssignmentAndScheduleDetails(String soId) throws DataServiceException ;

	public Integer getServiceOrderStatus(String soId)throws DataServiceException;
	
	public String getVendorIdFromVendorResource(String resourceId)throws DataServiceException;

	/**
	 * @param groupIdsList
	 * @param firmId 
	 * @return
	 * 
	 * to get child orders for the group
	 */
	public List<SOSearchResultVO> getChildOrdersForGroup (
			List<String> groupIdsList, Integer firmId) throws DataServiceException;

	/**
	 * @param groupId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isGroupedOrderInEditMode(String groupId)throws DataServiceException;

	/**
	 * Delete Mobile filter flow
	 * @param resourceId
	 * @param filterId
	 * @throws BusinessServiceException
	 */
	public int deleteFilter(Integer resourceId, String filterId) throws DataServiceException;
	/**
	 * Method to check whether resource is authorized to view SO details in received status
	 * @param detailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAuthorizedToViewSOInPosted(SoDetailsVO detailsVO)throws DataServiceException;
	/**
	 * Method to check whether resource is authorized to view SO details in accepted,active,problem status
	 * @param detailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAuthorizedToViewBeyondPosted(SoDetailsVO detailsVO)throws DataServiceException;
	/**
	 * Method to fetch the service order details for mobile version 3.0
	 * @param detailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrderDetailsVO getServiceOrderDetailsWithTrip(SoDetailsVO detailsVO)throws DataServiceException;



	/**@Description: getting count of service orders satisfying criterias.
	 * @param providerParamVO
	 * @return Integer
	 * @throws DataServiceException
	 */
	public Integer validateCountOfRecords(ProviderParamVO providerParamVO)throws DataServiceException;

	/**Fetching List of service orders available for providers
	 * @param providerParamVO
	 * @return List<ProviderSOSearchVO>
	 * @throws DataServiceException
	 */
	public List<ProviderSOSearchVO> getProviderSOList(ProviderParamVO providerParamVO) throws DataServiceException;

	/**
	 * gets the search filters 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public List<FiltersVO> getSearchFilterDetails(Integer providerId) throws DataServiceException;

	/**
	 * @param criteriaVO
	 * @return recievedOrdersCount
	 * @throws DataServiceException
	 */
	public Integer getRecievedOrdersCount(RecievedOrdersCriteriaVO criteriaVO) throws DataServiceException;

	/**
	 * @param criteriaVO
	 * @return List<RecievedOrdersVO>
	 * @throws DataServiceException
	 */
	public List<RecievedOrdersVO> getRecievedOrdersList(RecievedOrdersCriteriaVO criteriaVO) throws DataServiceException;

	/**
	 * @param searchRequestVO
	 * @return
	 */
	public Integer getTotalCountForServiceOrderSearchResults(
			SOSearchCriteriaVO searchRequestVO) throws DataServiceException ;

	/**
	 * @param searchRequestVO
	 * @return
	 */
	public Integer getTotalCountForServiceOrderAdvanceSearchResults(
			SOAdvanceSearchCriteriaVO searchRequestVO)  throws DataServiceException ;

	/**
	 * @param searchRequestVO
	 * @return
	 */
	public List<SOSearchResultVO> getServiceOrderAdvanceSearchResults(
			SOAdvanceSearchCriteriaVO searchRequestVO)  throws DataServiceException ;
	/**
	 * method to get the wfstateId,routed resources,and assignment type of the SO
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public AssignVO getProviderSODetails(String soId)throws DataServiceException ;

	public void saveFilter(FiltersVO filtersVO) throws DataServiceException;

	public boolean isFilterExists(Integer resourceId, String filterName) throws DataServiceException;

	/**@Description : Fetch Count of service order in different status.
	 * @param  dashboardVO
	 * @return dashboardVO
	 * @throws DataServiceException
	 */
	public MobileDashboardVO getDashboardCount(MobileDashboardVO dashboardVO) throws DataServiceException;
	/**
	 * Method to fetch the search criterias like market,service providers,status and subStatus
	 * @param roleId
	 * @param resourceId
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public SoSearchCriteriaVO getSearchCriteria(Integer roleId,Integer resourceId, Integer vendorId)throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public RescheduleDetailsVO getRescheduleDetailsForSO(String soId) throws DataServiceException;
	
	/**
	 * @param soId
	 * @param firmId
	 * @return
	 */
	public List<ProviderResultVO> getCounteredResourceDetailsList(String soId, String firmId) throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public ProblemResolutionSoVO getProblemDesc(String soId)throws DataServiceException;;
	
	/***@Description: This method will either add or update a an Estimate for the service order
	 * @param estimateVO
	 * @param estimationId
	 * @return
	 * @throws DataServiceException
	 */
	 public EstimateVO saveEstimationDetails(EstimateVO estimationVO, Integer estimationId) throws DataServiceException;
	
	/**
	 * 
	 * @param soId
	 * @param estimationId
	 * @return
	 * @throws DataServiceException
	 */
	public Boolean isEstimationIdExists(String soId, Integer estimationId) throws DataServiceException;
	
	/**
	 * B2C : method to fetch the estimate details 
	 * @param soId
	 * @param estimateId
	 * @return
	 * @throws BusinessServiceException
	 */
	public EstimateVO getEstimate(String soId, Integer estimateId)throws DataServiceException;

	/**@Description : Get the estimate status for the service order estimate
	 * @param soId
	 * @param estimationId
	 * @return
	 * @throws DataServiceException
	 */
	
	public EstimateVO getEstimateByVendorAndResource(Map<String, Object> param) throws DataServiceException;
	
	
	public String validateEstimateStatus(String soId, Integer estimationId)throws DataServiceException;
	
	/**@Description : This method will fetch the service location timezone for the service order.
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public String getServiceLocnTimeZone(String soId) throws DataServiceException;
	
	/** Returns service date time zone offset for an so
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getServiceDateTimeZoneOffset(String soId) throws DataServiceException;

	/**
	 * @param bidResourceId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getVendorId(Integer bidResourceId)throws DataServiceException;

	/**SL-21451: fetching bidRequests
	 * @param dashboardVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getBidRequestCount(MobileDashboardVO dashboardVO)throws DataServiceException;
	
	
	public Map<String,String> getOrderTypes(List<String> serviceOrderIds)throws DataServiceException;

	public List<EstimateVO> getEstimationList(RecievedOrdersCriteriaVO criteriaVO)throws DataServiceException;
	
	
	public Integer isvalidSoState(String soId)throws DataServiceException;

 
	
}
