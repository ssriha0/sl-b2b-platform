package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupPerformaceLevelDao;

/**
 * @author hoza
 * 
 */
public class LookupPerformanceLevelDaoImpl extends AbstractBaseDao implements LookupPerformaceLevelDao {
	@SuppressWarnings("unchecked")
	public List<LookupPerformanceLevel> getAllPerformancelevels() throws Exception {
		return (List<LookupPerformanceLevel>) super.findAll("LookupPerformanceLevel");

	}

}
