package com.newco.marketplace.business.businessImpl.providerSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.business.iBusiness.providersearch.IETMProviderSearch;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceFilterCriteria;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderRecord;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchResults;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSPNetStateResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.skillTree.ProviderIdsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.providerSearch.ETMProviderSearchDao;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;
import com.newco.marketplace.vo.PaginationVO;
import com.sears.os.business.ABaseBO;

public class ETMProviderSearchBOImpl extends ABaseBO implements IETMProviderSearch{

	private ETMProviderSearchDao etmDAO = null;
	private ProviderSearchDao providerSearchDao;
	
	private static final Logger logger = Logger
				.getLogger(ETMProviderSearchBOImpl.class.getName());
	
	
	
	public ETMProviderSearchResults findProvidersByCriteria(CriteriaMap critera, String username,Boolean filterInd) throws BusinessServiceException {
		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO paginationVo = null;
		int totalRecordCount = 0;
		logger.debug("Start of ETMProviderSearchBOImpl --> findProvidersByCriteria() ");
		ETMProviderSearchResults providerSearchList = new ETMProviderSearchResults();
		try {
			if(critera != null){	
				/*// Remove and get from paging criteria
				logger.debug("Initializing the criteria handler ");
				criteriaHandler.doCommonQueryInit(critera,Boolean.FALSE);*/
				
				
				PagingCriteria pagingCriteria = (PagingCriteria) critera.get(OrderConstants.ETM_PAGING_CRITERIA_KEY);
				if(filterInd)
				{
					pagingCriteria.setStartIndex(1);
					pagingCriteria.setEndIndex(pagingCriteria.getPageSize());
				}
				totalRecordCount = getEtmDAO().getPaginationCount(critera);
				logger.debug("In ETMProviderSearchBOImpl --> The filter result set count is "+totalRecordCount);
				providerSearchList = getEtmDAO().findProvidersByCriteria(critera);
				boolean isAnyProviderAvailable = false;
				if(providerSearchList.getSearchResults() != null )
					isAnyProviderAvailable = providerSearchList.getSearchResults().size()> 0 ? true : false;
				ExploreMktPlaceFilterCriteria filterObj = (ExploreMktPlaceFilterCriteria) critera.get(OrderConstants.ETM_FILTER_CRITERIA_KEY);
				if(filterObj.getSpnId() != null && filterObj.getSpnId().intValue() > 0 && isAnyProviderAvailable) {
					updateSPNETPerformancelevels(filterObj,providerSearchList.getSearchResults());
				}
				
				if(pagingCriteria != null){
					paginationVo = criteriaHandler._getPaginationDetail(
							totalRecordCount, pagingCriteria.getPageSize(),
							pagingCriteria.getStartIndex(), pagingCriteria
									.getEndIndex());			
					providerSearchList.setPaginationVo(paginationVo);				
				}
				logger.debug("End of ETMProviderSearchBOImpl --> findProvidersByCriteria() ");
				return providerSearchList;
			}
		}catch (DataServiceException e) {
			logger.error("Error in ETMProviderSearchBOImpl --> findProvidersByCriteria() getting filter results"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		catch(Exception ex){
			logger.error("Error in ETMProviderSearchBOImpl --> findProvidersByCriteria() getting filter results"
					+ ex.getMessage(),ex);
			throw new BusinessServiceException(ex.getMessage());
		}
		return null;
	}
	
	private void updateSPNETPerformancelevels( ExploreMktPlaceFilterCriteria filterObj, List<ETMProviderRecord> searchResults ) throws Exception {
		ProviderIdsVO providerIds = new ProviderIdsVO();
		Map<Integer,ETMProviderRecord > providerMap = extractproviderIds(searchResults);
		
		ArrayList plist = new ArrayList<Integer>();// I had to use the ArrayList cuz providerIDVos is defined member with ArrayLit and not with List
		plist.addAll(providerMap.keySet());
		providerIds.setProviderIds(plist);
		providerIds.setPerformancelevels(filterObj.getPerflevels());
		providerIds.setSpnId(filterObj.getSpnId());
		
		List<ProviderSPNetStateResultsVO> providerSPNetList = providerSearchDao.getProviderSPNetStates(providerIds);
		updateETMproviderRecordWithSPNETStates(providerSPNetList,providerMap);
		
	}
	
	
	private void updateETMproviderRecordWithSPNETStates(List<ProviderSPNetStateResultsVO> providerSPNetList,Map<Integer,ETMProviderRecord > providerMap) {
		// add list of spns for each resourceID
		for(ProviderSPNetStateResultsVO spnetState:providerSPNetList){
			providerMap.get(spnetState.getProviderResourceId()).getSpnetStates().add(spnetState);
		}
	}
	
	private Map<Integer,ETMProviderRecord > extractproviderIds(List<ETMProviderRecord> searchResults ) {
		Map<Integer, ETMProviderRecord> map = new HashMap<Integer, ETMProviderRecord>();
		for(ETMProviderRecord record : searchResults){
			if(!map.containsKey(record.getResourceId())){
				map.put(record.getResourceId(), record);
			}
		}
		return map;
		
	}
	

	public ETMProviderSearchResults getETMProviderList(CriteriaMap critera,String username) throws BusinessServiceException {
		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO paginationVo = null;
		Boolean filterInd=false;
		logger.debug("Start of ETMProviderSearchBOImpl --> getETMProviderList() ");
		try {
			if(critera != null){
				// Remove and get from paging criteria
				logger.debug("Initializing the criteria handler ");
				criteriaHandler.doCommonQueryInit(critera,Boolean.TRUE);
				
				ETMProviderSearchResults etmProviderList = getEtmDAO().getETMProviderList(critera,username);
				
				if(etmProviderList != null 
						&& etmProviderList.getSearchResults() != null){					
					logger.info("The Search Query key is "+etmProviderList.getSearchQueryKey());
					critera.put(OrderConstants.ETM_SEARCH_KEY, etmProviderList.getSearchQueryKey());
					// Get the first bucket of results
					etmProviderList = findProvidersByCriteria(critera, username,filterInd);
				}
				logger.debug("End of ETMProviderSearchBOImpl --> getETMProviderList() ");
				return etmProviderList;
			}
		}catch (DataServiceException e) {
			logger.error("Error in ETMProviderSearchBOImpl --> getETMProviderList() getting search results"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return null;
	}
	
	
	public ETMProviderSearchResults getETMProviderListPaginateResult(CriteriaMap critera,String username) throws BusinessServiceException {
		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO paginationVo = null;
		Boolean filterInd=false;
		logger.debug("Start of ETMProviderSearchBOImpl --> getETMProviderListPaginateResult() ");
		try {
			if(critera != null){
				// Remove and get from paging criteria
				logger.debug("Initializing the criteria handler ");
				criteriaHandler.doCommonQueryInit(critera,Boolean.TRUE);
				ETMProviderSearchResults etmProviderList = null;				
				etmProviderList = findProvidersByCriteria(critera, username,filterInd);
				logger.debug("End of ETMProviderSearchBOImpl --> getETMProviderListPaginateResult() ");
				return etmProviderList;
			}
		}catch (Exception e) {
			logger.error("Error in ETMProviderSearchBOImpl --> getETMProviderListPaginateResult() getting search results"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return null;
	}
	
	public ETMProviderSearchResults getETMProviderListApplyFilter(CriteriaMap critera,String username) throws BusinessServiceException {
		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO paginationVo = null;
		Boolean filterInd=false;
		logger.debug("Start of ETMProviderSearchBOImpl --> getETMProviderListApplyFilter() ");
		try {
			if(critera != null){
				logger.debug("Initializing the criteria handler ");
				criteriaHandler.doCommonQueryInit(critera,Boolean.TRUE);
				ETMProviderSearchResults etmProviderList = getEtmDAO().getETMProviderListApplyFilter(critera,username);
				if(etmProviderList != null 
						&& etmProviderList.getSearchResults() != null){					
					logger.info("The Search Query key is "+etmProviderList.getSearchQueryKey());
					critera.put(OrderConstants.ETM_SEARCH_KEY, etmProviderList.getSearchQueryKey());
				}
				logger.debug("End of ETMProviderSearchBOImpl --> getETMProviderListApplyFilter() ");
				return etmProviderList; 
			}
		}catch (DataServiceException e) {
			logger.error("Error in ETMProviderSearchBOImpl --> getETMProviderListApplyFilter() getting search results"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return null;
	}
	
	

	public Integer cleanETMTempTable(String searchQueryKey) throws BusinessServiceException {
		Integer numRowsDeleted = null;
		logger.debug("Start of ETMProviderSearchBOImpl --> cleanETMTempTable() ");
		try {
			numRowsDeleted = getEtmDAO().cleanETMTempTable(searchQueryKey);
			logger.info("Number of rows deleted from the Explore the Market Place temp table is "+numRowsDeleted);
		} catch (DataServiceException e) {
			logger.error("Error in ETMProviderSearchBOImpl --> cleanETMTempTable() cleaning search results"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		logger.debug("End of ETMProviderSearchBOImpl --> cleanETMTempTable() ");
		return numRowsDeleted;
	}

	public Integer cleanETMTempTableOldRecords() throws BusinessServiceException {
		logger.debug("Start of ETMProviderSearchBOImpl --> cleanETMTempTableOldRecords() ");
		Integer numRowsDeleted = null;
		try {
			numRowsDeleted = getEtmDAO().cleanETMTempTableOldRecords();
			logger.info("Number of rows deleted from the Explore the Market Place temp table is "+numRowsDeleted);
		} catch (Exception e) {
			logger.error("Error in ETMProviderSearchBOImpl --> cleanETMTempTableOldRecords() cleaning old records"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
		logger.debug("End of ETMProviderSearchBOImpl --> cleanETMTempTableOldRecords() ");
		return numRowsDeleted;
	}
	
	public Integer getDefaultDistance() throws BusinessServiceException{
		Integer distance = null;
		logger.debug("Start of ETMProviderSearchBOImpl --> getDefaultDistance() ");
		try {
			distance = etmDAO.getDefaultDistance();
		} catch (DataServiceException e) {
			logger.error("Error in ETMProviderSearchBOImpl --> getDefaultDistance() "
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		logger.debug("End of ETMProviderSearchBOImpl --> getDefaultDistance() ");
		return distance;
	}

	public ETMProviderSearchDao getEtmDAO() {
		return etmDAO;
	}

	public void setEtmDAO(ETMProviderSearchDao etmDAO) {
		this.etmDAO = etmDAO;
	}


	/**
	 * @return the providerSearchDao
	 */
	public ProviderSearchDao getProviderSearchDao() {
		return providerSearchDao;
	}


	/**
	 * @param providerSearchDao the providerSearchDao to set
	 */
	public void setProviderSearchDao(ProviderSearchDao providerSearchDao) {
		this.providerSearchDao = providerSearchDao;
	}	

	
}
