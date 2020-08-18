package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.spn.network.SPNServiceProviderStateHistory;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloon
 *
 */
public interface SPNServiceProviderStateHistoryDao extends BaseDao {

	/**
	 * 
	 * @param buyerId
	 * @param serviceProviderId
	 * @return List<SPNServiceProviderStateHistory>
	 */
	public List<SPNServiceProviderStateHistory> findServiceProviderStateHistory(Integer buyerId, Integer serviceProviderId); 
}
