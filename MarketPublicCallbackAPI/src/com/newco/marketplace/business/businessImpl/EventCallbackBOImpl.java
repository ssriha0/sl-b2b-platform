package com.newco.marketplace.business.businessImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.IEventCallbackBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.IEventCallbackDao;

public class EventCallbackBOImpl implements IEventCallbackBO {

	private IEventCallbackDao eventCallbackDao;

	private static final Logger LOGGER = Logger.getLogger(EventCallbackBOImpl.class);

	@Override
	public Map<String, String> getEventCallbackDetails(List<String> appKeys) throws BusinessServiceException {

		Map<String, String> eventCallbackDetails = new HashMap<String, String>();
		try {
			eventCallbackDetails = eventCallbackDao.getEventCallbackDetails(appKeys);
		} catch (DataServiceException e) {
			LOGGER.error("error occured in EventCallbackBOImpl.getEventCallbackServiceDetails()" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return eventCallbackDetails;
	}

	public IEventCallbackDao getEventCallbackDao() {
		return eventCallbackDao;
	}

	public void setEventCallbackDao(IEventCallbackDao eventCallbackDao) {
		this.eventCallbackDao = eventCallbackDao;
	}

}