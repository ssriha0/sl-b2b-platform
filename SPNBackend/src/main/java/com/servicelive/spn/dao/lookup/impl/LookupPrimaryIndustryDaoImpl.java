package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupPrimaryIndustry;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupPrimaryIndustryDao;

/**
 * @author Karthik_Hariharan01
 * R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
 * New DAO class which has method to fetch Primary Industry values from lu_primary_industry table 
 * to show in frontend 
 */
@Transactional
public class LookupPrimaryIndustryDaoImpl extends AbstractBaseDao implements LookupPrimaryIndustryDao {

	/* (non-Javadoc)
	 * The method fetches the primary industry values from lu_primary_industry table
	 * This method represents an exact replica of another existing functionality in Campaign criteria page.
	 */
	public List<LookupPrimaryIndustry> getPrimaryIndustry() throws Exception {
		// TODO Auto-generated method stub
		List<LookupPrimaryIndustry> primaryIndustry = findAll();
		return primaryIndustry;
	}
	@SuppressWarnings("unchecked")
	public List <LookupPrimaryIndustry> findAll ( int... rowStartIdxAndCount) throws Exception {
		List <LookupPrimaryIndustry> orderList = (List <LookupPrimaryIndustry>)super.findAllOrderByDesc("LookupPrimaryIndustry", "descr", rowStartIdxAndCount);
		return orderList;
	}
}