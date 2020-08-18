package com.newco.marketplace.persistence.iDao.pagination;

import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.DataServiceException;

/*
 * An interface for Pagination data access implementation. 
 */

public interface PaginationDao {
	public int getResultSetCount(String queryName, Integer workFlowStatusIds[], CriteriaMap criteriaMap) throws DataServiceException;
	
	//R12_1
	//SL-20379
	public int getResultSetCount(String queryName, Integer workFlowStatusIds[], CriteriaMap criteriaMap, String tab) throws DataServiceException;
}
