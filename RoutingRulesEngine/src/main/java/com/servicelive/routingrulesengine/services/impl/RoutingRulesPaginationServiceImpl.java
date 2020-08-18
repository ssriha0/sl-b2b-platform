package com.servicelive.routingrulesengine.services.impl;

import com.servicelive.routingrulesengine.dao.RoutingRuleBuyerAssocDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleFileHdrDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.services.RoutingRulesPaginationService;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesSearchVO;

public class RoutingRulesPaginationServiceImpl implements RoutingRulesPaginationService {
	
	private RoutingRuleHdrDao routingRuleHdrDao;
	private RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao;
	private RoutingRuleFileHdrDao routingRuleFileHdrDao;

	public RoutingRulesPaginationVO setManageRulePagination(Integer buyerId, RoutingRulesPaginationVO paginationCriteria){
		Integer ruleCount=routingRuleHdrDao.getRoutingRulesCount(buyerId);
		if(null==paginationCriteria){
			return setInitialPaginationDetails(ruleCount);
		}
		else{
			paginationCriteria.setTotalRecords(ruleCount);
			return setPaginationDetails(paginationCriteria);
		}	
	}
	
    public RoutingRulesPaginationVO setRuleHistoryPagination(Integer buyerId, RoutingRulesPaginationVO paginationCriteria, boolean archiveIndicator){
		
		if(null==paginationCriteria){
			Integer ruleCount=routingRuleBuyerAssocDao.getRulesHistoryCount(buyerId, archiveIndicator);
			return setInitialPaginationDetails(ruleCount);
		}
		else{
			return setPaginationDetails(paginationCriteria);
		}	
	}
	
    public RoutingRulesPaginationVO setSearchResultPagination(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO paginationCriteria,
			boolean archiveIndicator) 
    {
    	int column = 0;
    	if(null!=routingRulesSearchVo){
    		column = routingRulesSearchVo.getSearchColumn().intValue();
    	}
    	Integer count = 0;
		switch (column) 
		{
		case 1:
			count = routingRuleBuyerAssocDao.searchResultCountForFirmId(
					routingRulesSearchVo, archiveIndicator);
			break;
		case 2:
			count = routingRuleBuyerAssocDao.searchResultCountForFirmName(
					routingRulesSearchVo, archiveIndicator);
			break;
		case 3:
			count = routingRuleBuyerAssocDao.searchResultCountForRuleName(
					routingRulesSearchVo, archiveIndicator);
			break;
		case 4:
			count = routingRuleBuyerAssocDao
					.searchResultCountForUploadFileName(
							routingRulesSearchVo, archiveIndicator);
			break;
		case 5:
			count = routingRuleBuyerAssocDao.searchResultCountForRuleId(
					routingRulesSearchVo, archiveIndicator);
			break;
		case 6:
			count = routingRuleBuyerAssocDao.searchResultCountForProcessId(
					routingRulesSearchVo, archiveIndicator);
			break;
		case 7:
			count = routingRuleBuyerAssocDao.searchResultCountForAutoAcceptance(
					routingRulesSearchVo, archiveIndicator);
			break;
		}
		if (null == paginationCriteria) 
		{
			return setInitialPaginationDetails(count);
		} 
		else 
		{
			paginationCriteria.setTotalRecords(count);
			return setPaginationDetails(paginationCriteria);
		}
	}
	
	private RoutingRulesPaginationVO setInitialPaginationDetails(Integer ruleCount){
		RoutingRulesPaginationVO paginationDetails=new RoutingRulesPaginationVO();
		
			Integer totalPages=((ruleCount/DEFAULT_RULES_PAGE_SIZE)+((ruleCount%DEFAULT_RULES_PAGE_SIZE)==0?0:1));
			paginationDetails.setTotalRecords(ruleCount);
			paginationDetails.setCurrentIndex(1);
			paginationDetails.setPageRequested(1);
			paginationDetails.setTotalPages(totalPages);
			paginationDetails.setPageComment(createPageComment(paginationDetails.getPageRequested(), paginationDetails.getTotalRecords()));
			return paginationDetails;
		
	}
	
	private RoutingRulesPaginationVO setPaginationDetails(RoutingRulesPaginationVO paginationCriteria){
		
		paginationCriteria.setTotalPages(((paginationCriteria.getTotalRecords()/DEFAULT_RULES_PAGE_SIZE)+((paginationCriteria.getTotalRecords()%DEFAULT_RULES_PAGE_SIZE)==0?0:1)));
		/*
		if(paginationCriteria.getPageRequested()>paginationCriteria.getPaginetEndIndex()||
				paginationCriteria.getPageRequested().equals(1)){
			paginationCriteria.setPaginetStartIndex(paginationCriteria.getPageRequested());
			paginationCriteria.setPaginetEndIndex(paginationCriteria.getPaginetStartIndex()+4);
			if(paginationCriteria.getTotalPages()<=paginationCriteria.getPaginetEndIndex()){
				paginationCriteria.setPaginetEndIndex(paginationCriteria.getTotalPages());
			}
		}
	
		if(paginationCriteria.getPageRequested()<paginationCriteria.getPaginetStartIndex()||
				paginationCriteria.getPageRequested().equals(paginationCriteria.getTotalPages())){
			paginationCriteria.setPaginetEndIndex(paginationCriteria.getPageRequested());
			if(paginationCriteria.getTotalPages()>4){
				paginationCriteria.setPaginetStartIndex(paginationCriteria.getPaginetEndIndex()-4);
			}
			if(paginationCriteria.getTotalPages()<=paginationCriteria.getPaginetEndIndex()){
				paginationCriteria.setPaginetEndIndex(paginationCriteria.getTotalPages());
			}
		}*/
		paginationCriteria.setCurrentIndex(paginationCriteria.getPageRequested());
		paginationCriteria.setPageComment(createPageComment(paginationCriteria.getPageRequested(), paginationCriteria.getTotalRecords()));
		return paginationCriteria;
	}
	
	private String createPageComment(Integer pageRequested, Integer totalCount){
		Integer start=((pageRequested-1)*DEFAULT_RULES_PAGE_SIZE)+1;
		Integer end=start+(DEFAULT_RULES_PAGE_SIZE-1);
		if(end>totalCount){
			end=totalCount;
		}
		String comment="Showing <b>"+start+"</b> - <b>"+end+"</b> of <b>"+totalCount+"</b> total";
		return comment;
		
	}
	
	/**
	 * set the upload pagination criteria
	 * @param paginationCriteria
	 * @return
	 * @throws Exception 
	 */
	public RoutingRulesPaginationVO setUploadFileCriteria(
		RoutingRulesPaginationVO paginationCriteria,Integer buyerId) throws Exception{
		
		if(null==paginationCriteria){
			Integer fileCount=routingRuleFileHdrDao.getUploadedFilesCount(buyerId);
			return setInitialPaginationDetails(fileCount);
		}
		else{
			return setPaginationDetails(paginationCriteria);
		}	
	}
	
	public RoutingRuleHdrDao getRoutingRuleHdrDao() {
		return routingRuleHdrDao;
	}

	public void setRoutingRuleHdrDao(RoutingRuleHdrDao routingRuleHdrDao) {
		this.routingRuleHdrDao = routingRuleHdrDao;
	}

	public RoutingRuleBuyerAssocDao getRoutingRuleBuyerAssocDao() {
		return routingRuleBuyerAssocDao;
	}

	public void setRoutingRuleBuyerAssocDao(
			RoutingRuleBuyerAssocDao routingRuleBuyerAssocDao) {
		this.routingRuleBuyerAssocDao = routingRuleBuyerAssocDao;
	}
	
	public RoutingRuleFileHdrDao getRoutingRuleFileHdrDao() {
		return routingRuleFileHdrDao;
	}

	public void setRoutingRuleFileHdrDao(
			RoutingRuleFileHdrDao routingRuleFileHdrDao) {
		this.routingRuleFileHdrDao = routingRuleFileHdrDao;
	}


}
