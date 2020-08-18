package com.newco.marketplace.persistence.iDao;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.DataServiceException;

public interface IEventCallbackDao {

	public Map<String, String> getEventCallbackDetails(List<String> appKeys) throws DataServiceException;

}
