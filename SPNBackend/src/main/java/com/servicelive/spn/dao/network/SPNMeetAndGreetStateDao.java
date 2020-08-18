package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.spn.network.SPNMeetAndGreetState;
import com.servicelive.domain.spn.network.SPNMeetAndGreetStatePk;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloon
 *
 */
public interface SPNMeetAndGreetStateDao extends BaseDao {

	/**
	 * 
	 * @param spnId
	 * @return Boolean
	 */
	public Boolean isMeetAndGreetRequired(Integer spnId);

	/**
	 * 
	 * @param pk
	 * @return SPNMeetAndGreetState
	 */
	public SPNMeetAndGreetState findById(SPNMeetAndGreetStatePk pk);
	
	/**
	 * 
	 * @param buyerId
	 * @param spnId
	 * @param providerFirmId
	 * @return List
	 */
	public List<SPNMeetAndGreetState> findByBuyerIdAndProviderFirmIdExcludingSpnId(Integer buyerId, Integer spnId, Integer providerFirmId);
	
	/**
	 * 
	 * @param state
	 * @return SPNMeetAndGreetState
	 * @throws Exception
	 */
	public SPNMeetAndGreetState update(SPNMeetAndGreetState state) throws Exception;
}
