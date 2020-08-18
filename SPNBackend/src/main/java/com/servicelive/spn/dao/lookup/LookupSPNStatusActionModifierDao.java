package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNStatusActionModifier;
import com.servicelive.spn.dao.BaseDao;

public interface LookupSPNStatusActionModifierDao extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNStatusActionModifier> findAll ( int... rowStartIdxAndCount) throws Exception;
}
