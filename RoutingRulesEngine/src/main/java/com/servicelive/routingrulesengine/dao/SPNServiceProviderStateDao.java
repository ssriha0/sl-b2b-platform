package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.provider.ServiceProvider;


/**
 * 
 * @author svanloon
 *
 */
public interface SPNServiceProviderStateDao {
	/**
	 * 
	 * @param spnId
	 * @param stateStr
	 * @param providrFirmId
	 * @return List<ServiceProvider> 
	 */
	public List<ServiceProvider> listBySpnIdSPNWorkflowStatesAndProviderFirmId(Integer spnId, String stateStr, Integer providrFirmId );
	public boolean listProviderFirmIdBySpnIdAndSPNWorkflowStates(Integer vendor,String stateStr,Integer spnId);
}
