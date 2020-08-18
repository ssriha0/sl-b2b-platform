package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.spn.dao.BaseDao;

public interface LookupSPNStatusOverrideReasonDao extends BaseDao {

	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNStatusOverrideReason> findAll ( int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * 
	 * @param wfState
	 * @return List<LookupSPNStatusOverrideReason>
	 */
	public List<LookupSPNStatusOverrideReason> findByWfState(String wfState, String currentWfState);
	
	/**
	 * @param spnId
	 * @param providerFirmId
	 * @return
	 */
	public String findLastFirmNetworkStatus(Integer spnId, Integer providerFirmId);
	
	/**
	 * @param spnId
	 * @param providerResourceId
	 * @return
	 */
	public String findLastProviderNetworkStatus(Integer spnId, Integer providerResourceId);
}
