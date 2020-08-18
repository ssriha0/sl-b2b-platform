package com.newco.marketplace.persistence.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.IEventCallbackDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class EventCallbackDaoImpl extends ABaseImplDao implements IEventCallbackDao {

	@Override
	public Map<String, String> getEventCallbackDetails(List<String> appKeys) throws DataServiceException {

		Map<String, String> serviceMap = new HashMap<String, String>();
		try {
			serviceMap = getSqlMapClient().queryForMap("getEventCallbackDetails.query", appKeys, "appKey", "appValue");
		} catch (Exception e) {
			logger.error("Exception occurred in getEventCallbackDetails: " + e.getMessage());
			throw new DataServiceException("Exception occurred in getEventCallbackDetails: " + e.getMessage(), e);
		}
		return serviceMap;
	}
}
