package com.servicelive.spn.services.auditor;

import java.util.List;

import com.servicelive.domain.spn.network.SPNMeetAndGreetState;
import com.servicelive.domain.spn.network.SPNMeetAndGreetStatePk;
import com.servicelive.spn.dao.network.SPNMeetAndGreetStateDao;
import com.servicelive.spn.services.BaseServices;

/**
 * 
 * @author svanloon
 *
 */
public class MeetAndGreetStateService extends BaseServices {

	private SPNMeetAndGreetStateDao spnMeetAndGreetStateDao;

	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}
	
	public Boolean isMeetAndGreetRequired(Integer spnId) {
		return spnMeetAndGreetStateDao.isMeetAndGreetRequired(spnId);
	}

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return SPNMeetAndGreetState
	 */
	public SPNMeetAndGreetState find(Integer spnId, Integer providerFirmId) {
		SPNMeetAndGreetStatePk pk = new SPNMeetAndGreetStatePk(spnId, providerFirmId);
		return spnMeetAndGreetStateDao.findById(pk);
	}

	/**
	 * 
	 * @param buyerId
	 * @param spnId
	 * @param providerFirmId
	 * @return List
	 */
	public List<SPNMeetAndGreetState> findByBuyerIdAndProviderFirmIdExcludingSpnId(Integer buyerId, Integer spnId, Integer providerFirmId) {
		return spnMeetAndGreetStateDao.findByBuyerIdAndProviderFirmIdExcludingSpnId(buyerId, spnId, providerFirmId);
	}
	
	/**
	 * 
	 * @param state
	 */
	public void update(SPNMeetAndGreetState state)
	{		
		try
		{
			spnMeetAndGreetStateDao.update(state);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

	/**
	 * @param spnMeetAndGreetStateDao the spnMeetAndGreetStateDao to set
	 */
	public void setSpnMeetAndGreetStateDao(
			SPNMeetAndGreetStateDao spnMeetAndGreetStateDao) {
		this.spnMeetAndGreetStateDao = spnMeetAndGreetStateDao;
	}
}
