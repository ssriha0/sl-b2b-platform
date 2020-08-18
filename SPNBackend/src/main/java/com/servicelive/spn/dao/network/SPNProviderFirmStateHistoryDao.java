package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.spn.network.SPNProviderFirmStateHistory;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloon
 *
 */
public interface SPNProviderFirmStateHistoryDao extends BaseDao {

	/**
	 * 
	 * @param buyerId
	 * @param providerFirmId
	 * @return List<SPNProviderFirmStateHistory>
	 */
	public List<SPNProviderFirmStateHistory> findProviderFirmStatusHistory(Integer buyerId, Integer providerFirmId);
}
