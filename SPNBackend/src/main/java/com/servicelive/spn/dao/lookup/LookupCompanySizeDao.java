/*
 * @(#)LookupCompanySizeDao.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupCompanySize;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Mahmud Khair
 *
 */

public interface LookupCompanySizeDao extends BaseDao{
	
	/**
	 * Gets the list of all LookupCompanySize objects
	 * @return List
	 * @throws Exception
	 */
	public List<LookupCompanySize> getAllCompanySizes()  throws Exception;
	
	/**
	 * Gets the list of LookupCompanySize objects
	 * @param rowStartIdxAndCount
	 * @return A List of LookupCompanySize
	 * @throws Exception
	 */
	public List<LookupCompanySize> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * Gets a specific LookupCompanySize object
	 * @param id
	 * @return  A LookupState object
	 * @throws Exception
	 */
	public LookupCompanySize findById(Integer id) throws Exception ;
	
	
	/**
	 * Gets a list of LookupCompanySize objects for specific property value
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return  A List of LookupCompanySize
	 * @throws Exception
	 */
	public List<LookupCompanySize> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
}
