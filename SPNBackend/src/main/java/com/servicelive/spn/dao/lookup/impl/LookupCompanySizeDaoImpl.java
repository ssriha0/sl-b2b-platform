/*
 * @(#)LookupCompanySizeDaoImpl.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.servicelive.domain.lookup.LookupCompanySize;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupCompanySizeDao;

/**
 * @author Mahmud Khair
 *
 */
@Repository ("lookupCompanySizeDao")
public class LookupCompanySizeDaoImpl extends AbstractBaseDao implements LookupCompanySizeDao {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupCompanySizeDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<LookupCompanySize> findAll(int... rowStartIdxAndCount)
			throws Exception {
		return (List <LookupCompanySize> )super.findAll("LookupCompanySize", rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupCompanySizeDao#findById(java.lang.Integer)
	 */
	public LookupCompanySize findById(Integer id) throws Exception {
		return (LookupCompanySize) super.findById(LookupCompanySize.class, id);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupCompanySizeDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<LookupCompanySize> findByProperty(String propertyName, Object value,
			int... rowStartIdxAndCount) throws Exception {
		return (List<LookupCompanySize>) super.findByProperty("LookupCompanySize",propertyName,value, rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupCompanySizeDao#getAllCompanySizes()
	 */
	public List<LookupCompanySize> getAllCompanySizes() throws Exception {
		List<LookupCompanySize> states = findAll();
		return states;
	}
}
