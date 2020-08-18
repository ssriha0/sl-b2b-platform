/*
 * @(#)LookupSalesVolumeDaoImpl.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.servicelive.domain.lookup.LookupSalesVolume;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSalesVolumeDao;

/**
 * @author Mahmud Khair
 *
 */
@Repository ("lookupSalesVolumeDao")
public class LookupSalesVolumeDaoImpl extends AbstractBaseDao implements LookupSalesVolumeDao {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupSalesVolumeDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<LookupSalesVolume> findAll(int... rowStartIdxAndCount)
			throws Exception {
		return (List <LookupSalesVolume> )super.findAll("LookupSalesVolume", rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupSalesVolumeDao#findById(java.lang.Integer)
	 */
	public LookupSalesVolume findById(Integer id) throws Exception {
		return (LookupSalesVolume) super.findById(LookupSalesVolume.class, id);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupSalesVolumeDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<LookupSalesVolume> findByProperty(String propertyName, Object value,
			int... rowStartIdxAndCount) throws Exception {
		return (List<LookupSalesVolume>) super.findByProperty("LookupSalesVolume",propertyName,value, rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupSalesVolumeDao#getAllSalesVolumes()
	 */
	public List<LookupSalesVolume> getAllSalesVolumes() throws Exception {
		List<LookupSalesVolume> states = findAll();
		return states;
	}
}
