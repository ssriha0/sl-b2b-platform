package com.newco.marketplace.persistence.iDao.so.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.dto.vo.provider.SearchFirmsVO;
import com.newco.marketplace.dto.vo.provider.ServiceOfferingsVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;

/**
 * $Revision: 1.23 $ $Author: akashya $ $Date: 2008/05/21 22:54:38 $
 */

/*
 * Maintenance History
 * $Log: IServiceOrderSearchDAO.java,v $
 * Revision 1.23  2008/05/21 22:54:38  akashya
 * I21 Merged
 *
 * Revision 1.22.2.1  2008/05/14 14:21:03  gjacks8
 * removed high-bit chars from comments
 *
 * Revision 1.22  2008/05/02 23:38:41  mkhair
 * Modified the method name from getNotScheduledPromisedDateSO() to getNotScheduledDeliveryDateSO()
 *
 * Revision 1.21  2008/05/02 21:23:42  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.20  2008/04/26 00:40:37  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.16.38.2  2008/04/28 23:18:00  mkhair
 * Changed method name from getNotRescheduledPromisedDateSO() to getNotScheduledPromisedDateSO().
 *
 * Revision 1.16.38.1  2008/04/24 22:08:32  mkhair
 * Added getNotRescheduledPromisedDateSO() method.
 *
 * Revision 1.16.20.1  2008/04/23 11:42:18  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.19  2008/04/23 05:02:15  hravi
 * Reverting to build 247.
 *
 * Revision 1.17  2008/04/15 17:51:00  glacy
 * Shyam: Merged I18_THREE_ENH branch
 *
 * Revision 1.16.32.1  2008/04/09 19:40:28  sgopin2
 * Sears00045181 - To add search mechanism by 'Provider Firm Id' in SOM->Search tab for buyer login
 *
 * Revision 1.16  2008/01/29 22:01:21  mhaye05
 * fixed the sorting of power buyer filter search results
 *
 * Revision 1.15  2008/01/03 15:19:13  bgangaj
 * Added method getConditionalOffers() to get the list of conditional offers
 *
 * Revision 1.14  2007/11/27 00:23:49  mhaye05
 * updated for search paging
 *
 * Revision 1.13  2007/11/17 18:42:32  mhaye05
 * changed method signatures so that the return type is a List instead of ArrayList
 *
 */
public interface IServiceOrderSearchDAO {

	public List<ServiceOrderSearchResultsVO> getServiceOrderPagedResults(ServiceOrderSearchResultsVO serviceOrder, SearchCriteria searchCriteria) throws DataServiceException;
	
	public List<String> getServiceOrderByZipCode(ServiceOrderSearchResultsVO serviceOrder) throws DataServiceException;
	public List<String> getServiceOrderByAddress(ServiceOrderSearchResultsVO serviceOrder) throws DataServiceException;
	public List<String> getServiceOrderByPhoneNumber(ServiceOrderSearchResultsVO serviceOrder) throws DataServiceException;
	// Use from ServiceOrderDao 
	public List<String> getServiceOrderBySoID(ServiceOrderSearchResultsVO serviceOrder)throws DataServiceException;
	public List<String> getServiceOrderByEndUserName(ServiceOrderSearchResultsVO serviceOrder) throws DataServiceException;
	public List<String> getServiceOrderByTechnicianName(ServiceOrderSearchResultsVO serviceOrder) throws DataServiceException;
	public List<String> getServiceOrderByTechnicianID(ServiceOrderSearchResultsVO serviceOrder) throws DataServiceException;
	public List<String> getServiceOrderByProviderFirmID(ServiceOrderSearchResultsVO serviceOrder) throws DataServiceException;
	public ServiceOrderSearchResultsVO getBuyerUserName(ServiceOrderSearchResultsVO serviceOrder) throws DataServiceException;
	public List<ServiceOrderSearchResultsVO> getSObyWFStateForBatch(
			serviceOrderTabsVO request) throws DataServiceException;
	public List<ServiceOrderSearchResultsVO> getSOforConditionalRouting(
			serviceOrderTabsVO request) throws DataServiceException;
	public List<ServiceOrderSearchResultsVO> getSObySubState(
			serviceOrderTabsVO request) throws DataServiceException;
//	public List<ServiceOrderSearchResultsVO> getSOBuyerPosted(
//			Integer buyerId) throws DataServiceException;
	
	public List<ServiceOrderSearchResultsVO> setBuyerAndProviderName
	(ServiceOrderSearchResultsVO soSearchVO,List<ServiceOrderSearchResultsVO> soSearchList)throws DataServiceException;
	public List<ServiceOrderSearchResultsVO> getConditionalOffers(Timestamp currentDateTime) throws DataServiceException;
	//SOM
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes( CriteriaMap criteraMap,boolean loadSOMswitch) throws DataServiceException;
	//public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes( CriteriaMap criteraMap,String displayTab) throws DataServiceException;
	 
	/**
	 * Returns a list of accepted OMS SO's that does not have substatus 'Schedule Confirmed' and 
	 * order creation date is past over 24 hours.
	 * 
	 * @return A list of ServiceOrderSearchResutlsVO objects
	 * @throws DataServiceException
	 */
	public List<ServiceOrderSearchResultsVO> getNotScheduledSO() throws DataServiceException;

	public List<String> getServiceOrderIDsByCustomRefID(ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException;
	
	/**
	 * @param soSearchVO
	 * @return List
	 * @throws DataServiceException
	 */
	public List<String> getServiceOrderByCheckNumber(ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException;
	public List<String> getServiceOrderByAdvanceSearch(ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException;
	/**
	 * Saves the search filter 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public SearchFilterVO saveSearchFilter(SearchFilterVO searchFilterVO)throws DataServiceException;
	/**
	 * gets the search filters 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public List<SearchFilterVO> getSearchFilters(SearchFilterVO searchFilterVO) throws DataServiceException;
	/**
	 * gets the selected search filter 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public SearchFilterVO getSelectedSearchFilter(SearchFilterVO searchFilterVO) throws DataServiceException;
	/**
	 * Deletes the search filter 
	 * @param SearchFilterVO
	 * @return
	 * @throws DataServiceException
	 */
	public SearchFilterVO deleteSearchFilter(SearchFilterVO searchFilterVO)throws DataServiceException;
	public List<String> getAutocloseFailedByRule(ServiceOrderSearchResultsVO soSearchVO)throws DataServiceException;

	public List<ServiceOrderSearchResultsVO> getServiceOrdersProviderPhones(
			Map<String, Object> paramMap);
	
	//R12_1
	//SL-20379
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes( CriteriaMap criteraMap, String tab, boolean loadSOMswitch) throws DataServiceException;
	
	/* SL-21308 : Standard Service Offerings Search API
	 */
	public List<ServiceOfferingsVO> getSearchFirmsResult(
			SearchFirmsVO searchFirmsVO) throws DataServiceException;

	public List<String> getZipList(String zip, String radius) throws DataServiceException;

	public List<ServiceOfferingsVO> getAvailableFirmSKUList(
			SearchFirmsVO searchFirmsVO) throws DataServiceException;

	public List<ServiceOfferingsVO> getOfferingAvailabilityList(
			SearchFirmsVO searchFirmsVO) throws DataServiceException;
	
	public Map<Integer, BigDecimal> getAggregateRating(List<Integer> firmIds)throws DataServiceException;
	
	public Map<Long, String> getFirmNames (List<Integer> firmIds)throws DataServiceException;
	
	public ServiceDatetimeSlot getDateTimeSlotForOrder(ServiceOrderSearchResultsVO serviceOrderSearchResultsVO)  throws DataServiceException;


}
