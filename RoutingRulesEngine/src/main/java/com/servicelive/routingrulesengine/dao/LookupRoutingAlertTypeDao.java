package com.servicelive.routingrulesengine.dao;

import com.servicelive.domain.lookup.LookupRoutingAlertType;


/**
 * Interface for LookUpRoutingAlertTypeDAO.
 * 
 */

public interface LookupRoutingAlertTypeDao {


	/**
	 * 
	 * @param id
	 * @return LookupRoutingAlertType
	 * @throws Exception
	 */
	public LookupRoutingAlertType findById(Integer id);

	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void insert(LookupRoutingAlertType entity) throws Exception;

	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(LookupRoutingAlertType entity) throws Exception;
}