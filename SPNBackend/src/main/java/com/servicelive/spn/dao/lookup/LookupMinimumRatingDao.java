/**
 * 
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupMinimumRating;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface LookupMinimumRatingDao extends BaseDao{
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupMinimumRating> findAll ( int... rowStartIdxAndCount) throws Exception;
}
