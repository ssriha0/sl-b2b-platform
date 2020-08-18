/**
 * 
 */
package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupMinimumRating;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupMinimumRatingDao;

/**
 * @author hoza
 *
 */
@Transactional
public class LookupMinimumRatingDaoImpl extends AbstractBaseDao implements LookupMinimumRatingDao {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupMinimumRatingDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<LookupMinimumRating> findAll(int... rowStartIdxAndCount) throws Exception {
		return ( List<LookupMinimumRating>) super.findAll("LookupMinimumRating", rowStartIdxAndCount);
	}

}
