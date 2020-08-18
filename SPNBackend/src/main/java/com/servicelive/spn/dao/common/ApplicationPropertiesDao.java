package com.servicelive.spn.dao.common;

import java.util.List;

import com.servicelive.domain.common.ApplicationProperties;

public interface ApplicationPropertiesDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<ApplicationProperties> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return ApplicationProperties
	 * @throws Exception
	 */
	public ApplicationProperties findById(String id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<ApplicationProperties> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
}
