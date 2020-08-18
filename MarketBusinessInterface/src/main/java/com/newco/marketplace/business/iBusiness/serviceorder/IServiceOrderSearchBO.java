package com.newco.marketplace.business.iBusiness.serviceorder;

import java.sql.Timestamp;
import java.util.List;

import com.newco.marketplace.dto.vo.provider.SearchFirmsResponseVO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History
 * $Log: IServiceOrderSearchBO.java,v $
 * Revision 1.18  2008/05/03 01:42:13  glacy
 * Shyam: Fixing build. (1) Corrected method name in interface also.  (2) Added missing constant in OrderConstants.
 *
 * Revision 1.17  2008/05/02 21:24:00  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.16  2008/04/26 00:40:06  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.14.32.2  2008/04/28 23:27:08  mkhair
 * Changed method name from findNotRescheduledPromisedDateSO() to findNotScheduledPromisedDateSO().
 *
 * Revision 1.14.32.1  2008/04/24 22:14:51  mkhair
 * Added findNotRescheduledPromisedDateSO() method.
 *
 * Revision 1.14.12.1  2008/04/23 11:41:13  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.15  2008/04/23 05:16:53  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.14  2008/01/31 15:51:06  mhaye05
 * filter on search tab now works for both status and sub-status
 *
 * Revision 1.13  2008/01/29 22:01:22  mhaye05
 * fixed the sorting of power buyer filter search results
 * 
 * Revision 1.12  2008/01/03 15:15:45  bgangaj
 * Added method findConditionalOffers()
 *
 * Revision 1.11  2007/12/03 17:29:52  iullah2
 * fix for merge build - delete package com.newco.marketplace.business.Utils and organize imports on src
 *
 * Revision 1.10  2007/11/27 00:22:36  mhaye05
 * updated for search paging
 *
 * Revision 1.9  2007/11/20 19:26:02  ajain04
 * Added a method to search orders by status
 *
 * Revision 1.8  2007/11/17 18:41:03  mhaye05
 * changed method signatures so that the return type is a List instead of ArrayList
 *
 */
public interface IServiceOrderSearchBO {
	
	/**
	 * retrieveSOSearchResultsIds returns the service order id's that meet the search criteria
	 * @param criteriaMap
	 * @return
	 * @throws DataServiceException
	 */
	public ProcessResponse retrieveSOSearchResultsIds(CriteriaMap criteriaMap) throws DataServiceException;
	
	/**
	 * retrievePagedSOSearchResults returns paginated data based on an already executed search
	 * @param criteriaMap
	 * @return
	 * @throws DataServiceException
	 */
	public ProcessResponse retrievePagedSOSearchResults(CriteriaMap criteriaMap) throws DataServiceException;
	
	
	//TODO: Remove after Refactoring - Harish
//	public List<ServiceOrderSearchResultsVO> findservicebyuser(serviceOrderTabsVO request) throws DataServiceException ;
	
	//SOM
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes( CriteriaMap criteraMap) throws DataServiceException;
//	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes( CriteriaMap criteraMap,String displayTab) throws DataServiceException;
	
	// returns a list of SO's by status
	public List<ServiceOrderSearchResultsVO> findServiceOrderByStatusForBatch(serviceOrderTabsVO request) 
					throws DataServiceException;
	
	public List<ServiceOrderSearchResultsVO> findConditionalOffers(Timestamp currentDateTime) throws DataServiceException;
	
	/**
	 * Returns a list of accepted OMS SO's that does not have substatus 'Schedule Confirmed' and 
	 * order creation date is past over for 24 hours.
	 * 
	 * @return A list of ServiceOrderSearchResutlsVO objects
	 * @throws BusinessServiceException
	 */
	public List<ServiceOrderSearchResultsVO> findNotScheduledSO()throws BusinessServiceException;

	/**
	 * Returns the list of all draft orders for conditional auto routing
	 * @param request
	 * @return
	 * @throws DataServiceException
	 */
	public List<ServiceOrderSearchResultsVO> findServiceOrderForConditionalRouting(serviceOrderTabsVO request)
		throws DataServiceException;
	/**
	 * Saves the search filter 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public SearchFilterVO saveSearchFilter(SearchFilterVO searchFilterVO )throws BusinessServiceException;
	/**
	 * gets all saved search filters 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public List<SearchFilterVO> getSearchFilters(SearchFilterVO searchFilterVO) throws BusinessServiceException;
	/**
	 * get a particular saved search filter 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public SearchFilterVO getSelectedSearchFilter(SearchFilterVO searchFilterVO) throws BusinessServiceException;
	/**
	 * Deletes the search filter 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public SearchFilterVO deleteSearchFilter(SearchFilterVO searchFilterVO )throws BusinessServiceException;

	//R12_1
	//SL-20379
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes( CriteriaMap criteraMap, String tab) throws DataServiceException;

	//SL-21308 : Standard Service Offerings Search API
	public SearchFirmsResponseVO getSearchFirmsResult(
			SearchFirmsVO searchFirmsVO) throws DataServiceException;
	
	public ServiceDatetimeSlot getDateTimeSlotForOrder(ServiceOrderSearchResultsVO serviceOrderSearchResultsVO) throws DataServiceException;
}
