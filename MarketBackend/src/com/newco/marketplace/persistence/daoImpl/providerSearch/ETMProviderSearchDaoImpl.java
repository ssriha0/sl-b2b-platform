package com.newco.marketplace.persistence.daoImpl.providerSearch;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceFilterCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceSearchCriteria;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderFilterRequest;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderRecord;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchRequest;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchResults;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.providerSearch.ETMProviderSearchDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.utils.UIUtils;


public class ETMProviderSearchDaoImpl
	extends ProviderSearchDaoImpl
	implements ETMProviderSearchDao
{

	private static final Logger logger = Logger.getLogger(ETMProviderSearchDaoImpl.class.getName());
	private LookupDao lookupDao = null;
	

	private ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
	private static int _defaultDistanceFilter = Integer.MIN_VALUE;


	private int getDefaultDistanceFilter()
	{
		if (_defaultDistanceFilter == Integer.MIN_VALUE) {
			_defaultDistanceFilter = Integer.parseInt(PropertiesUtils
					.getPropertyValue(Constants.AppPropConstants.EXPLORE_MARKETPLACE_SEARCH_DISTANCE_FILTER));
		}
		return _defaultDistanceFilter;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.persistence.iDao.providerSearch.ETMProviderSearchDao#findProvidersByCriteria(com.newco.marketplace.dto.vo.serviceorder
	 * .CriteriaMap) Get the filter list of providers who match the criteria
	 */
	public ETMProviderSearchResults findProvidersByCriteria(CriteriaMap criteria)
		throws DataServiceException
	{

		ETMProviderFilterRequest filterVo = buildFilterVo(criteria, Boolean.FALSE);
		List<ETMProviderRecord> filterList = (List<ETMProviderRecord>) queryForList("etm.filterby.query", filterVo);
		ETMProviderSearchResults filterResults = new ETMProviderSearchResults();
		filterResults.setSearchResults(filterList);

		if (filterVo != null) {
			filterResults.setSearchQueryKey(filterVo.getSearchQueryKey());
		}

		return filterResults;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.persistence.iDao.providerSearch.ETMProviderSearchDao#getETMProviderList(com.newco.marketplace.dto.vo.serviceorder.CriteriaMap
	 * , java.lang.String) Get all the providers who match the criteria
	 */
	public ETMProviderSearchResults getETMProviderList(CriteriaMap criteria, String username)
		throws DataServiceException
	{
		Integer buyerId = null;
		criteriaHandler.doCommonQueryInit(criteria, true);
		Object etmSearchObj = criteria.get(OrderConstants.ETM_SEARCH_CRITERIA_KEY);
		if(etmSearchObj != null ) {
		
		ExploreMktPlaceSearchCriteria etmSearchCriteria = (ExploreMktPlaceSearchCriteria)etmSearchObj;
			buyerId = etmSearchCriteria.getBuyerID();
		}
		
		if (criteriaHandler.getSearchQueryKey() == null) {
			List<ETMProviderRecord> proResults = getSearchResultsList(criteria);

			ETMProviderSearchResults theResults = new ETMProviderSearchResults();
			try {
				if (proResults != null) {
					theResults.setSearchQueryKey(new RandomGUID().generateGUID().toString());
					theResults.setSearchResults(proResults);

					if (username != null) {
						theResults.setUsername(username);
					}
					loadETMTempTable(theResults);
					//update must happen after initial load
					updateETMtablesWithMyRatingsInfo(buyerId,theResults.getSearchQueryKey());
				}
			} catch (Exception e) {
				logger.error("Error in ETMProviderSearchDaoImpl --> getETMProviderList() gettting search result" + e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return theResults;
		} else if (criteriaHandler.getSearchQueryKey() != null) {
			cleanETMTempTable(criteriaHandler.getSearchQueryKey());
			List<ETMProviderRecord> proResults = getSearchResultsList(criteria);
			ETMProviderSearchResults theResults = new ETMProviderSearchResults();
			try {
				theResults.setSearchQueryKey(new RandomGUID().generateGUID().toString());
				theResults.setSearchResults(proResults);

				if (username != null) {
					theResults.setUsername(username);
				}

				loadETMTempTable(theResults);
				//update must happen after initial load
				updateETMtablesWithMyRatingsInfo(buyerId,theResults.getSearchQueryKey());
			} catch (Exception e) {
				logger.error("Error in ETMProviderSearchDaoImpl --> getETMProviderList() gettting search result" + e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return theResults;
		}

		return null;
	}
	
	public ETMProviderSearchResults getETMProviderListApplyFilter(CriteriaMap criteria, String username)
			throws DataServiceException
		{
			Integer buyerId = null;
			criteriaHandler.doCommonQueryInit(criteria, true);
			Object etmSearchObj = criteria.get(OrderConstants.ETM_SEARCH_CRITERIA_KEY);
			if(etmSearchObj != null ) {
			
			ExploreMktPlaceSearchCriteria etmSearchCriteria = (ExploreMktPlaceSearchCriteria)etmSearchObj;
				buyerId = etmSearchCriteria.getBuyerID();
			}
			if (criteriaHandler.getSearchQueryKey() == null) {
				List<ETMProviderRecord> proResults = getSearchResultsListApplyFilter(criteria); 

				ETMProviderSearchResults theResults = new ETMProviderSearchResults();
				try {
					if (proResults != null) {
						theResults.setSearchQueryKey(new RandomGUID().generateGUID().toString());
						theResults.setSearchResults(proResults);

						if (username != null) {
							theResults.setUsername(username);
						}
						loadETMTempTable(theResults);
						//update must happen after initial load
						updateETMtablesWithMyRatingsInfo(buyerId,theResults.getSearchQueryKey());
					}
				} catch (Exception e) {
					logger.error("Error in ETMProviderSearchDaoImpl --> getETMProviderListApplyFilter() gettting search result" + e.getMessage());
					throw new DataServiceException(e.getMessage());
				}
				return theResults;
			} else if (criteriaHandler.getSearchQueryKey() != null) {
				cleanETMTempTable(criteriaHandler.getSearchQueryKey());
				List<ETMProviderRecord> proResults = getSearchResultsListApplyFilter(criteria);
				ETMProviderSearchResults theResults = new ETMProviderSearchResults();
				try {
					theResults.setSearchQueryKey(new RandomGUID().generateGUID().toString());
					theResults.setSearchResults(proResults);

					if (username != null) {
						theResults.setUsername(username);
					}
					loadETMTempTable(theResults);
					//update must happen after initial load
					updateETMtablesWithMyRatingsInfo(buyerId,theResults.getSearchQueryKey());
				} catch (Exception e) {
					logger.error("Error in ETMProviderSearchDaoImpl --> getETMProviderListApplyFilter() gettting search result" + e.getMessage());
					throw new DataServiceException(e.getMessage());
				}
				return theResults;
			}

			return null;
		}
	
	//criteria should have search_query_key and buyerId available on this 
	private void updateETMtablesWithMyRatingsInfo(final Integer buyerId, final String searchQuerykey ) throws DataServiceException{
		ETMProviderSearchRequest searchRequest = new ETMProviderSearchRequest();
		searchRequest.setBuyerID(buyerId);
		searchRequest.setSearchQueryKey(searchQuerykey);
		 List<SurveyRatingsVO> surverys = fetchMyRatingsInfo(searchRequest);
		 if(surverys != null && surverys.size() > 0 ) {
			 updateMyInfo(surverys,searchQuerykey);
		 }
	}
	private Integer updateMyInfo(final List<SurveyRatingsVO> surverys, final String searchkey) {
		Integer result = Integer.valueOf(0);
		if(searchkey != null ) {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException
				{
					executor.startBatch();
					Calendar cal = new GregorianCalendar();
					long currentTimeInMillis = cal.getTimeInMillis();
					Timestamp currentDateTime = new Timestamp(currentTimeInMillis);
					for (int x = 0; x < surverys.size(); x++) {
						SurveyRatingsVO providerSurveyRec = surverys.get(x);
						if (providerSurveyRec != null && providerSurveyRec.getVendorResourceID() != null ) {
							 ETMProviderRecord record = new ETMProviderRecord();
							 record.setResourceId(providerSurveyRec.getVendorResourceID());
							 record.setSearchQueryKey(searchkey);
							 record.setBuyersTotalOrder(providerSurveyRec.getMyOrdersComplete());
							 if(providerSurveyRec.getMyRating() != null) {
								 record.setBuyersRatingScore(providerSurveyRec.getMyRating());
								 record.setBuyersRatingsImageId(UIUtils.calculateScoreNumber(providerSurveyRec.getMyRating().doubleValue()));
							 }
							 record.setBuyersRatingCount(providerSurveyRec.getNumberOfRatingsGiven());
							 
							 record.setTotalOrdersCompleted(providerSurveyRec.getTotalOrdersComplete());
							 if(providerSurveyRec.getHistoricalRating() != null) {
								 record.setRatingScore(providerSurveyRec.getHistoricalRating());
								 record.setRatingImageId(UIUtils.calculateScoreNumber(providerSurveyRec.getHistoricalRating().doubleValue()));
							 }
							 record.setRatingCount(providerSurveyRec.getNumberOfRatingsGiven());
							 executor.update("etm.provider.myratings.update", record);
						}
					}
					return new Integer(executor.executeBatch());
				}
			});
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.providerSearch.ETMProviderSearchDao#cleanETMTempTable(java.lang.String) Delete all the rows for
	 * that particular search query key
	 */
	public int cleanETMTempTable(String searchQueryKey)
		throws DataServiceException
	{

		logger.debug("In ETMProviderSearchDaoImpl --> cleanETMTempTable()");
		if (searchQueryKey != null) {
			return delete("etm.provider.record.delete", searchQueryKey);
		} else {
			logger.error("Error in ETMProviderSearchDaoImpl --> cleanETMTempTable() search query key is null");
			throw new DataServiceException("Error in ETMProviderSearchDaoImpl --> cleanETMTempTable() search query key is null");
		}

	}
	
	



	public Integer cleanETMTempTableOldRecords()
		throws Exception
	{
		logger.debug("In ETMProviderSearchDaoImpl --> cleanETMTempTable()");

		return delete("etm.provider.old.record.delete", null);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.newco.marketplace.persistence.iDao.providerSearch.ETMProviderSearchDao#loadETMTempTable(com.newco.marketplace.dto.vo.providerSearch.
	 * ETMProviderSearchResults) Insert the providers list in the temp table
	 */
	public int loadETMTempTable(ETMProviderSearchResults results)
		throws DataServiceException
	{
		if (results != null && results.getSearchResults() != null && results.getSearchResults().size() > 0) {
			final int totalResultSize = results.getSearchResults().size();
			// we need to make a copy
			final List<ETMProviderRecord> recordList = new ArrayList();
			final String searchQueryKey = results.getSearchQueryKey();
			final String username = results.getUsername();

			recordList.addAll(results.getSearchResults());

			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException
				{
					executor.startBatch();
					Calendar cal = new GregorianCalendar();
					long currentTimeInMillis = cal.getTimeInMillis();
					Timestamp currentDateTime = new Timestamp(currentTimeInMillis);
					for (int x = 0; x < totalResultSize; x++) {
						ETMProviderRecord providerRec = recordList.get(x);
						if (providerRec != null) {
							// int percentageMatch =
							// (int)
							// ((skillResultsVO.getProviderSkillRating() /
							// (10 * 1))* 100);
							providerRec.setSearchQueryKey(searchQueryKey);
							providerRec.setUsername(username);
							providerRec.setCreatedDate(currentDateTime);
							executor.insert("etm.provider.record.insert", providerRec);
						}
					}
					return new Integer(executor.executeBatch());
				}
			});
			
		}
		return 0;
	}


	/**
	 * @param criteria
	 * @return List of all providers who match the criteria
	 * @throws DataServiceException
	 */
	private List<ETMProviderRecord> getSearchResultsList(CriteriaMap criteria)
		throws DataServiceException
	{
		LocationVO theLocation = null;
		ETMProviderSearchRequest searchRequest = null;
		List<ETMProviderRecord> proResults = new ArrayList<ETMProviderRecord>();
		ExploreMktPlaceSearchCriteria etmSearchCriteria = null;
		criteriaHandler.doCommonQueryInit(criteria, true);

		// Allow unit tests to sneak in an alternate version of the query to run
		String marketId= System.getProperty   ("etm.get_market_ready_providers_by_vertical.query",     "etm.get_market_ready_providers_by_vertical.query");
		String notMarketId= System.getProperty("etm.get_not_market_ready_providers_by_vertical.query", "etm.get_not_market_ready_providers_by_vertical.query");

		if (criteria != null) {
			etmSearchCriteria = (ExploreMktPlaceSearchCriteria) criteria.get(OrderConstants.ETM_SEARCH_CRITERIA_KEY);

			if (etmSearchCriteria != null) {
				theLocation = getLookupDao().getZipCodeEntry(etmSearchCriteria.getBuyerZipCodeStr());

				if (theLocation != null) {
					searchRequest = buildSearchVo(criteria, theLocation);
				}
			}
		}

		if (searchRequest != null) {
			if (criteriaHandler.getMarketReady() != null
					&& criteriaHandler.getMarketReady().intValue() == OrderConstants.ETM_MARKET_READY_INDICATOR.intValue()) {
				proResults = (List<ETMProviderRecord>) queryForList(marketId, searchRequest);
			} else if (criteriaHandler.getMarketReady() != null) {
				proResults = (List<ETMProviderRecord>) queryForList(notMarketId, searchRequest);
			} else {
				logger.error("Error in ETMProviderSearchDaoImpl --> getSearchResultsList() Market ready indicator not set");
				throw new DataServiceException("Error in ETMProviderSearchDaoImpl --> getSearchResultsList() Market ready indicator not set");
			}
		}
		populateRatingsStarImage(proResults);
		return proResults;
	}
	
	
	

	/**
	 * @param criteria
	 * @return List of all providers who match the criteria
	 * @throws DataServiceException
	 */
	private List<ETMProviderRecord> getSearchResultsListApplyFilter(CriteriaMap criteria)
		throws DataServiceException
	{
		LocationVO theLocation = null;
		ETMProviderSearchRequest searchRequest = null;
		List<ETMProviderRecord> proResults = new ArrayList<ETMProviderRecord>();
		ExploreMktPlaceSearchCriteria etmSearchCriteria = null;
		criteriaHandler.doCommonQueryInit(criteria, true);

		String marketId= System.getProperty   ("etm.get_market_ready_providers_by_vertical.query",     "etm.get_market_ready_providers_by_vertical.query");
		String notMarketId= System.getProperty("etm.get_not_market_ready_providers_by_vertical.query", "etm.get_not_market_ready_providers_by_vertical.query");

		if (criteria != null) {
			etmSearchCriteria = (ExploreMktPlaceSearchCriteria) criteria.get(OrderConstants.ETM_SEARCH_CRITERIA_KEY);

			if (etmSearchCriteria != null) {
				theLocation = getLookupDao().getZipCodeEntry(etmSearchCriteria.getBuyerZipCodeStr());

				if (theLocation != null) {
					searchRequest = buildSearchVoApplyFilter(criteria, theLocation);
				}
			}
		}

		if (searchRequest != null) {
			if (criteriaHandler.getMarketReady() != null
					&& criteriaHandler.getMarketReady().intValue() == OrderConstants.ETM_MARKET_READY_INDICATOR.intValue()) {
				proResults = (List<ETMProviderRecord>) queryForList(marketId, searchRequest);
			} else if (criteriaHandler.getMarketReady() != null) {
				proResults = (List<ETMProviderRecord>) queryForList(notMarketId, searchRequest);
			} else {
				logger.error("Error in ETMProviderSearchDaoImpl --> getSearchResultsList() Market ready indicator not set");
				throw new DataServiceException("Error in ETMProviderSearchDaoImpl --> getSearchResultsList() Market ready indicator not set");
			}
		}
		populateRatingsStarImage(proResults);
		return proResults;
	}
	
	//this method expect at least buyerId and Search_query_
	private List<SurveyRatingsVO> fetchMyRatingsInfo(ETMProviderSearchRequest searchRequest) throws DataServiceException{
		logger.debug("----Start of fetchMyRatingsInfo()----");
		List<SurveyRatingsVO> surveyRatingsVOs = null;
		try {
			surveyRatingsVOs = (List<SurveyRatingsVO>)queryForList("exploreResults.myRatings.query",searchRequest);			
		}
		catch (Exception ex) {
			logger.info("[fetchMyRatingsInfo - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("----End of fetchMyRatingsInfo()----");
		return surveyRatingsVOs;
	}
	
	private void populateRatingsStarImage(List<ETMProviderRecord> proResults) {
		for(ETMProviderRecord precord : proResults) {
			if(precord.getRatingScore() != null  && precord.getRatingScore().doubleValue() > 0.0  ) {
				
				precord.setRatingImageId(Integer.valueOf(UIUtils.calculateScoreNumber(precord.getRatingScore().doubleValue())));
			}
			else { 
				precord.setRatingImageId(0);
			}
		}
	}
	

	/**
	 * ensureSort sets the database fields that sorting will be performed on based on data in the input parameter. A Map is returned containing the
	 * key and sort order
	 * 
	 * @param sortColumn
	 * @param sortOrder
	 * @return map
	 */
	protected Map<String, String> ensureETMSortProviderSearch(String sortColumn, String sortOrder)
	{
		Map<String, String> sort = new HashMap<String, String>();
		boolean sortOrderSet = false;

		if (ServiceLiveStringUtils.isNotEmpty(sortColumn) && !ServiceLiveStringUtils.equals(sortColumn, OrderConstants.NULL_STRING)) {
			if ("ETMProvider".equalsIgnoreCase(sortColumn)) {
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_ETM_COLUMN_AS_PROVIDERNAME);
			} else if ("ETMTotalOrder".equalsIgnoreCase(sortColumn)) {
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_ETM_COLUMN_AS_TOTAL_ORDERS);
			} else if ("ETMDistance".equalsIgnoreCase(sortColumn)) {
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_ETM_COLUMN_AS_DISTANCE);
				
			} else if ("ETMSlRatings".equalsIgnoreCase(sortColumn)) {
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_ETM_COLUMN_AS_SL_RATINGS);
			}
			else {
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_ETM_COLUMN_AS_PROVIDERNAME);
				sort.put(OrderConstants.SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
				sortOrderSet = true;
			}
		} else {
			sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_ETM_COLUMN_AS_DISTANCE);
			sort.put(OrderConstants.SORT_ORDER_KEY, OrderConstants.SORT_ORDER_ASC);
			sortOrderSet = true;
		}

		if (!sortOrderSet) {
			if (ServiceLiveStringUtils.isNotEmpty(sortOrder) && !ServiceLiveStringUtils.equals(sortColumn, OrderConstants.NULL_STRING)) {
				sort.put(OrderConstants.SORT_ORDER_KEY, sortOrder);
			} else {
				sort.put(OrderConstants.SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			}
		}

		return sort;
	}


	public Integer getPaginationCount(CriteriaMap critera)
		throws DataServiceException
	{
		ETMProviderFilterRequest request = new ETMProviderFilterRequest();
		request = buildFilterVo(critera, Boolean.TRUE);
		Integer resultCount = null;
		/*if (request != null && request.getSelectedLangs() != null && request.getSelectedLangs().size() > 0) {
			resultCount = (Integer) queryForObject("etm.getFilterCountForLang.query", request);
		} else {*/
			resultCount = (Integer) queryForObject("etm.getFilterCount.query", request);
		/*}*/
		return resultCount;
	}
	
	public Integer getDefaultDistance() throws DataServiceException{
		Integer distance = null;
		distance=Integer.valueOf(getDefaultDistanceFilter());
		return distance;
	}



	/**
	 * @param criteria
	 * @param pagination
	 * @return
	 */
	private ETMProviderFilterRequest buildFilterVo(CriteriaMap criteria, boolean pagination)
	{
		ETMProviderFilterRequest filterRequest = new ETMProviderFilterRequest();

		PagingCriteria pagingObj = (PagingCriteria) criteria.get(OrderConstants.ETM_PAGING_CRITERIA_KEY);
		OrderCriteria orderObj = (OrderCriteria) criteria.get(OrderConstants.ETM_ORDER_CRITERIA_KEY);
		SortCriteria sortObj = (SortCriteria) criteria.get(OrderConstants.ETM_SORT_CRITERIA_KEY);
		ExploreMktPlaceFilterCriteria filterObj = (ExploreMktPlaceFilterCriteria) criteria.get(OrderConstants.ETM_FILTER_CRITERIA_KEY);
		String searchQueryKey = (String) criteria.get(OrderConstants.ETM_SEARCH_KEY);

		if (!pagination && pagingObj != null) {
			filterRequest.setStartIndex(pagingObj.getStartIndex() - 1); // Limit stats from 0. The start index that is passed is 1.
			filterRequest.setNumberOfRecords(pagingObj.getPageSize());
		}

		if (!pagination && sortObj != null) {
			Map<String, String> sortOrder = ensureETMSortProviderSearch(sortObj.getSortColumnName(), sortObj.getSortOrder());
			if (sortOrder != null) {
				filterRequest.setSortColumnName(sortOrder.get(OrderConstants.SORT_COLUMN_KEY));
				filterRequest.setSortOrder(sortOrder.get(OrderConstants.SORT_ORDER_KEY));
			}
		}

		if (filterObj != null) {
			filterRequest.setDistance(filterObj.getDistance());
			filterRequest.setCredentialCategory(filterObj.getCredentialCategory());
			filterRequest.setRating(filterObj.getRating());
			filterRequest.setSelectedCredential(filterObj.getSelectedCredential());
			filterRequest.setSpnId(filterObj.getSpnId());
			filterRequest.setPerformancelevels(filterObj.getPerflevels());
			
			if (filterObj.getSelectedLangs() != null) {
				filterRequest.setSelectedLangs(filterObj.getSelectedLangs());
				filterRequest.setSelectedLangsCount(filterObj.getSelectedLangs().size());
			}
		}

		if (searchQueryKey != null) {
			filterRequest.setSearchQueryKey(searchQueryKey);
		}
	

		return filterRequest;
	}


	private ETMProviderSearchRequest buildSearchVo(CriteriaMap criteria, LocationVO theLocation)
	{
		ETMProviderSearchRequest searchRequest = null;
		if (criteria != null) {
			criteriaHandler.doCommonQueryInit(criteria, true);
			
			searchRequest = new ETMProviderSearchRequest();

			// searchRequest.setDistanceFilter(new Integer(200));
			searchRequest.setDistanceFilter(Integer.valueOf(getDefaultDistanceFilter()));
			searchRequest.setZip(theLocation.getZip());
			searchRequest.setZipLatitude(new Double(theLocation.getLatitude()));
			searchRequest.setZipLongitude(new Double(theLocation.getLongitude()));
			searchRequest.setNodeId(criteriaHandler.getNodeId());
		}
		return searchRequest;
	}
	
	
	private ETMProviderSearchRequest buildSearchVoApplyFilter(CriteriaMap criteria, LocationVO theLocation)
	{
		ETMProviderSearchRequest searchRequest = null;
		if (criteria != null) {
			criteriaHandler.doCommonQueryInit(criteria, true);
			searchRequest = new ETMProviderSearchRequest();
			ExploreMktPlaceFilterCriteria filterObj =(ExploreMktPlaceFilterCriteria) criteria.get(OrderConstants.ETM_FILTER_CRITERIA_KEY);
			Integer searchDistance=filterObj.getDistance();
			searchRequest.setDistanceFilter(searchDistance);
			searchRequest.setZip(criteriaHandler.getZipCode().toString());
			searchRequest.setZipLatitude(new Double(theLocation.getLatitude()));
			searchRequest.setZipLongitude(new Double(theLocation.getLongitude()));
			searchRequest.setNodeId(criteriaHandler.getNodeId());
		}
		return searchRequest;
	}


	public LookupDao getLookupDao()
	{
		return lookupDao;
	}


	public void setLookupDao(LookupDao lookupDao)
	{
		this.lookupDao = lookupDao;
	}



}
