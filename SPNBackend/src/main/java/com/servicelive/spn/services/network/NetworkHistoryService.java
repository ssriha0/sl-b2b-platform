package com.servicelive.spn.services.network;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.spn.common.detached.NetworkHistoryVO;
import com.servicelive.spn.services.BaseServices;

public class NetworkHistoryService extends BaseServices {

	@Override
	protected void handleDates(Object entity) {
		// do thing
	}

	/**
	 * 
	 * @param spnId
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<NetworkHistoryVO> getNetworkHistory(Integer spnId) throws Exception {
		List<NetworkHistoryVO> list = getSqlMapClient().queryForList("network.history.getListOfNetworkHistory", spnId);
		return list;
	}
}
