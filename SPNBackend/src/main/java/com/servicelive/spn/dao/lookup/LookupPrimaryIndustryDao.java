package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupPrimaryIndustry;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Karthik_Hariharan01
 * R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
 * New DAO class which has method to fetch Primary Industry values from lu_primary_industry table 
 * to show in frontend 
 */
public interface LookupPrimaryIndustryDao extends BaseDao {
	/**
	 * @return List<LookupPrimaryIndustry>
	 * @throws Exception
	 */
	public List<LookupPrimaryIndustry> getPrimaryIndustry() throws Exception;
}
