package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.spn.dao.BaseDao;

public interface LookupSPNStatusActionMapperDao extends BaseDao {

	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNStatusActionMapper> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param modifiedBy
	 * @param wfState
	 * @param actionType
	 * @return LookupSPNStatusActionMapper
	 */
	public LookupSPNStatusActionMapper findByModifierStateAndActionType(String modifiedBy, String wfState, String actionType);
}
