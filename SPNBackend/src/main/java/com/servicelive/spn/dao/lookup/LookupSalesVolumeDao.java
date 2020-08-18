/*
 * @(#)LookupSalesVolumeDao.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSalesVolume;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Mahmud Khair
 *
 */

public interface LookupSalesVolumeDao extends BaseDao{
	
	/**
	 * Gets the list of all LookupSalesVolume objects
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSalesVolume> getAllSalesVolumes()  throws Exception;
	
	/**
	 * Gets the list of LookupSalesVolume objects
	 * @param rowStartIdxAndCount
	 * @return A List of LookupSalesVolume
	 * @throws Exception
	 */
	public List<LookupSalesVolume> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * Gets a specific LookupSalesVolume object
	 * @param id
	 * @return  A LookupState object
	 * @throws Exception
	 */
	public LookupSalesVolume findById(Integer id) throws Exception ;
	
	
	/**
	 * Gets a list of LookupSalesVolume objects for specific property value
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return  A List of LookupSalesVolume
	 * @throws Exception
	 */
	public List<LookupSalesVolume> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
}
