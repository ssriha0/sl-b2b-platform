package com.newco.marketplace.persistence.daoImpl.pagination;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.dto.vo.pagination.WorkflowStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.pagination.PaginationDao;
import com.sears.os.dao.impl.ABaseImplDao;


/*
 * An implementation for Pagination DAO. This implementation does the following
 * 1. Get the record count for a given query
 */

public class PaginationDaoImpl extends ABaseImplDao implements PaginationDao {

	private static final Logger logger = Logger
	.getLogger(PaginationDaoImpl.class.getName());
	
	public int getResultSetCount(String queryName, Integer workFlowStatusIds[], CriteriaMap criteriaMap) throws DataServiceException{
		WorkflowStatusVO wfStatus = new WorkflowStatusVO();
		wfStatus.setWorkFlowStatusIds(workFlowStatusIds);
		FilterCriteria filterCriteria = (FilterCriteria)criteriaMap.get(OrderConstants.FILTER_CRITERIA_KEY);
		String soSubstatusId=null;
		if (filterCriteria !=null) {
			soSubstatusId = filterCriteria.getSubStatus();
			if(soSubstatusId!=null) {
				if(!soSubstatusId.equals(""))
					wfStatus.setSoSubstatusId(new Integer(soSubstatusId));
				}
			if(filterCriteria.getServiceProName() != null){ 
                 wfStatus.setServiceProName(filterCriteria.getServiceProName().toString()); 
             }
			if(filterCriteria.getBuyerRoleId() != null){ 
                wfStatus.setBuyerRoleId(filterCriteria.getBuyerRoleId()); 
            } 			
		    if(filterCriteria.getMarketName() != null){ 
                 wfStatus.setMarketName(filterCriteria.getMarketName().toString()); 
             } 
		    if(filterCriteria.isManageSOFlag()) {
		    	wfStatus.setResourceId(filterCriteria.getResourceId());
		    }
		}
		OrderCriteria aOrderCriteria = (OrderCriteria)criteriaMap.get(OrderConstants.ORDER_CRITERIA_KEY);
		
		if(workFlowStatusIds != null && workFlowStatusIds.length > 0){
			int statusCount = workFlowStatusIds.length;
			wfStatus.setRoutedTab(false);
			for(int i=0; i<statusCount; i++){
				if(workFlowStatusIds[i] != null && OrderConstants.ROUTED_STATUS == workFlowStatusIds[i]){
					logger.debug("getResultSetCount()-->RoutedTab");
					wfStatus.setRoutedTab(true);
					break;
				}
			}
		}
		
		if(aOrderCriteria!=null){
			wfStatus.setUserId(aOrderCriteria.getCompanyId().toString());
		}
		
		try {
			wfStatus = (WorkflowStatusVO)queryForObject(queryName, wfStatus);
		}catch(Exception exception){
			throw new DataServiceException("PaginationDaoImpl - getResultSetCount query Failed", exception);
		}		
		return wfStatus.getCount();
	}
	
	//R12_1
	//SL-20379
	public int getResultSetCount(String queryName, Integer workFlowStatusIds[], CriteriaMap criteriaMap, String tab) throws DataServiceException{
		WorkflowStatusVO wfStatus = new WorkflowStatusVO();
		wfStatus.setWorkFlowStatusIds(workFlowStatusIds);
		FilterCriteria filterCriteria = (FilterCriteria)criteriaMap.get(OrderConstants.FILTER_CRITERIA_KEY+"_"+tab);
		String soSubstatusId=null;
		if (filterCriteria !=null) {
			soSubstatusId = filterCriteria.getSubStatus();
			if(soSubstatusId!=null) {
				if(!soSubstatusId.equals(""))
					wfStatus.setSoSubstatusId(new Integer(soSubstatusId));
				}
			if(filterCriteria.getServiceProName() != null){ 
                 wfStatus.setServiceProName(filterCriteria.getServiceProName().toString()); 
             }
			if(filterCriteria.getBuyerRoleId() != null){ 
                wfStatus.setBuyerRoleId(filterCriteria.getBuyerRoleId()); 
            } 			
		    if(filterCriteria.getMarketName() != null){ 
                 wfStatus.setMarketName(filterCriteria.getMarketName().toString()); 
             } 
		    if(filterCriteria.isManageSOFlag()) {
		    	wfStatus.setResourceId(filterCriteria.getResourceId());
		    }
		}
		OrderCriteria aOrderCriteria = (OrderCriteria)criteriaMap.get(OrderConstants.ORDER_CRITERIA_KEY+"_"+tab);
		
		if(workFlowStatusIds != null && workFlowStatusIds.length > 0){
			int statusCount = workFlowStatusIds.length;
			wfStatus.setRoutedTab(false);
			for(int i=0; i<statusCount; i++){
				if(workFlowStatusIds[i] != null && OrderConstants.ROUTED_STATUS == workFlowStatusIds[i]){
					logger.debug("getResultSetCount()-->RoutedTab");
					wfStatus.setRoutedTab(true);
					break;
				}
			}
		}
		
		if(aOrderCriteria!=null){
			wfStatus.setUserId(aOrderCriteria.getCompanyId().toString());
		}
		
		try {
			wfStatus = (WorkflowStatusVO)queryForObject(queryName, wfStatus);
		}catch(Exception exception){
			throw new DataServiceException("PaginationDaoImpl - getResultSetCount query Failed", exception);
		}		
		return wfStatus.getCount();
	}
	
	public static void main(String k[]){
		try {
			PaginationDaoImpl paginationDAOImpl = (PaginationDaoImpl)MPSpringLoaderPlugIn.getCtx().getBean("paginationDao");
			/*int statuses[] = {};
			long count = paginationDAOImpl.getResultSetCount("soSearch.queryInactiveCount", statuses);
			*/
/*			int myStatus[] = {100, 110,130, 125, 120,140,150,155,160,170,180};
			serviceOrderTabsVO s = new serviceOrderTabsVO();
			s.setWorkFlowStatusIds(myStatus);
			s.setBuyerId("2");
			s.setSoSubStatus(new Integer(31));
			
			ArrayList a = paginationDAOImpl.getResultSet(s);*/
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	
	
}