package com.newco.marketplace.business.iBusiness;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IEventCallbackBO {

	public Map<String, String> getEventCallbackDetails(List<String> appKeys) throws BusinessServiceException;

}
