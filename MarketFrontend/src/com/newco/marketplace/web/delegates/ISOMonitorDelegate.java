package com.newco.marketplace.web.delegates;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.ReasonLookupVO;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.so.order.SoCancelVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.dto.IncreaseSpendLimitDTO;
import com.newco.marketplace.web.dto.RejectServiceOrderDTO;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderMonitorTabTitleCount;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface ISOMonitorDelegate {
/*
	public ArrayList<ServiceOrderDTO> getServiceOrdersByStatusTypes(ServiceOrdersCriteria soCriteria, 
	                                                                 FilterCriteria filters,
									 SortCriteria sorts, 
									 PagingCriteria paging) 
									 throws Exception;*/
	public String getSummaryCounts(AjaxCacheVO ajaxCacheVO); 

	public String getDetailedCounts(AjaxCacheVO ajaxCacheVO);

	public double getAvailableBalance (AjaxCacheVO ajaxCacheVO)  throws BusinessServiceException;
	public double getCurrentBalance (AjaxCacheVO ajaxCacheVO)  throws BusinessServiceException;
	public List<LookupVO> getAutocloseRules() throws BusinessServiceException;
	
//	public String getAllSOMCounts(AjaxCacheVO ajaxCacheVo);
	
	/**
	 * Updates the spend limit for given SO.
	 * If groupId is not null then spend limit would be updated for entire group along with child's spend limit updated proportionally.
	 * 
	 * @param soId
	 * @param groupId
	 * @param currentSpendLimitLabor
	 * @param currentSpendLimitParts
	 * @param totalSpendLimit
	 * @param totalSpendLimitParts
	 * @param increasedSpendLimitComment
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProcessResponse increaseSpendLimit(IncreaseSpendLimitDTO soIncSpendLimitDto)
			throws BusinessServiceException;

	/**
	 * Updates the routed_provider table with the provider's response as 'rejected';
	 * If groupId is not null then all children of given group would be marked 'rejected'.
	 * 
	 * @param resourceId
	 * @param soId
	 * @param groupId
	 * @param reasonId
	 * @param responseId
	 * @param modifiedBy
	 * @param reasonDesc
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProcessResponse rejectServiceOrder(List<Integer> checkedResourceID, String soId, String groupId, int reasonId,
			int responseId, String modifiedBy, String reasonDesc) throws BusinessServiceException;
	
	public ProcessResponse serviceOrderVoid(SOCancelDTO soCancelDto) throws BusinessServiceException;
	
	/**
	 * Updates the routed_provider table with the provider's response as 'rejected' for SO-ID in given DTO;
	 * If groupId in given DTO is not null then all children of that group would be marked 'rejected'.
	 * 
	 * @param rso
	 * @return
	 * @throws BusinessServiceException
	 */
	public String rejectServiceOrder( RejectServiceOrderDTO rso,List<Integer> checkedResourceID) throws BusinessServiceException;

	public ArrayList<ReasonLookupVO> getRejectReasonCodes()
			throws BusinessServiceException;

	public ArrayList<ServiceOrderMonitorTabTitleCount> getTabs(
			AjaxCacheVO ajaxCacheVo);

	// So widget methods
	public ProcessResponse serviceOrderAddNote(Integer resourceId, Integer roleId, ServiceOrderNoteDTO note,LoginCredentialVO lvRoles) throws BusinessServiceException;

//	public List<ServiceOrderNote> serviceOrderGetNotes(Integer buyerId, String serviceOrderId);
	
	public String serviceOrderAccept(String soId, String userName, Integer resourceId, Integer companyId,Integer termAndCond, boolean validate);
	
	
//	TODO:SC - commented
	/*
	public ArrayList<ServiceOrderDTO> getReceivedTabsData(ServiceOrdersCriteria soc);
	*/
	public ArrayList getSubStatusDetails(Integer statusId, Integer roleId);
	
	public ServiceOrderMonitorResultVO getSoSearchList(CriteriaMap criteriaMap) ;
	
	public List<String> getSoSearchIdsList(CriteriaMap criteriaMap) ;
	
	public ByteArrayOutputStream getPDF(String soId);
	
	//for SL_15642
	//MERGE pdf
	
	public ByteArrayOutputStream printPaperwork(List<String>checkedSoIds,List<String> checkedOptions);
	
	public ServiceOrderMonitorResultVO fetchServiceOrderResults(CriteriaMap criteriaMap);
	
	//R12_1
	//SL-20379
	public ServiceOrderMonitorResultVO fetchServiceOrderResults(CriteriaMap criteriaMap, String tab);
	
	//public ServiceOrderMonitorResultVO fetchServiceOrderResults(CriteriaMap criteriaMap,String displayTab);
	
	public ArrayList<LuProviderRespReasonVO> getRejectReasons( LuProviderRespReasonVO luReasonVO ) throws BusinessServiceException;
	
	public ProcessResponse withdrawCondOffer(String soId, Integer resourceId, Integer providerRespId) throws BusinessServiceException;

	/**
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProcessResponse deleteDraft(SOCancelDTO soCancelDto) throws BusinessServiceException;
	
	public double getPostSOLedgerAmount(String soId)  throws BusinessServiceException;
	/**  checks whether the user requested to view So is associated with SOiD requested*/

	public boolean isAssociatedToViewSOAsPDF(String soId, Integer roleId,
			Integer requestingUserId) throws Exception;
	
	public List<ServiceOrderSearchResultsVO> getServiceOrdersForGroup(
			String parentGroupId);	
	
	public ServiceOrder getServiceOrder(String soId );
	public SoCancelVO getServiceOrderForCancel(String soId );
	
	/**
	 * This method returns the order group details for given group id.  Used to render order quick links info on grouped order SOD.
	 * 
	 * @param groupId
	 * @return
	 */
	public OrderGroupVO getOrderGroupById(String groupId);
	
	//SL-21465
	public EstimateVO getServiceOrderEstimationDetails(String soID,int vendorId,int resourceId);
	public EstimateVO getServiceOrderEstimationDetails(String soID);
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForVendor(String soID,int vendorId,int ResourceId);
	public List<EstimateHistoryVO> getServiceOrderEstimationHistoryDetailsForBuyer(String soID);
	// Utility function for Upsell
	public void setAddonPricesForServiceOrderVOList(List<ServiceOrderSearchResultsVO> serviceOrders);
	public void setAddonPricesForServiceOrderDTOList(List<ServiceOrderDTO> serviceOrders);
	/**
	 * This method is used to fetch all the provider names under a firm to load the filter
	 * @param resourceId
	 * @return ArrayList
	 */
	public ArrayList<ProviderDetail> loadServiceProviders(Integer resourceId);

	public ProcessResponse withdrawGroupCondOffer(String groupId, Integer resourceId, Integer providerRespId) throws BusinessServiceException;
		/**
	 * This method is used to fetch all the market names under a firm to load the filter
	 * @param resourceId
	 * @return ArrayList
	 */
	public ArrayList<ProviderDetail> loadServiceProviderMarkets(Integer resourceId);
	public List<LookupVO> getNotBlackedOutStateCodes();
	public List<LookupVO> getSkills();
	public List<LookupVO> getMarkets();
	/**
	 * This method is used to fetch all the main Service Categories
	 */
	public ArrayList<SkillNodeVO> loadMainCategories();
	/**
	 * This method is used to fetch all the  Service Categories and Sub Categories
	 */
	public ArrayList<SkillNodeVO> loadCategoryAndSubCategories (Integer selectedId);
	/**
	 * This method is used to fetch all markets by startindex and endindex
	 */
	public List<LookupVO> getMarketsByIndex(String sIndex,String eIndex);
	
	public List<LookupVO> getSubStatusList(Integer statusId);
	
	public SearchFilterVO saveSearchFilter(SearchFilterVO searchFilterVO);
	
	public List<SearchFilterVO> getSearchFilters(SearchFilterVO searchFilterVO);
	
	public SearchFilterVO getSelectedSearchFilter(SearchFilterVO searchFilterVO);
	
	public SearchFilterVO deleteSearchFilter(SearchFilterVO searchFilterVO);

	/**Description:Checking non funded feature set is enabled for the buyer
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public boolean isNonFundedBuyer(Integer buyerId);
	/**
	 * This method is used to fetch spend limit details of so
	 * 
	 * @param soid
	 * @return InitialPriceDetailsVO
	 * @throws BusinessServiceException
	 */
	public InitialPriceDetailsVO getInitialPrice(String soId) throws BusinessServiceException;

}