package com.newco.marketplace.business.businessImpl.so.order;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderFeatureSetBO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderFeatureSetDAO;

public class ServiceOrderFeatureSetBOImpl implements IServiceOrderFeatureSetBO {

private static final Logger logger = Logger.getLogger(ServiceOrderFeatureSetBOImpl.class);
	
	private IServiceOrderFeatureSetDAO serviceOrderFeatureSetDAO;
	
	public Boolean validateFeature(String soID, String feature) {
		Boolean foundFeature = null;
		
		try {
			String strfeature = getServiceOrderFeatureSetDAO().getFeature(soID, feature);
			if (strfeature != null) {
				foundFeature = new Boolean(true);
			}
			else {
				foundFeature = new Boolean(false);
			}
		} catch (DataServiceException e) {
			logger.error(e.getMessage(), e);
		}
		return foundFeature;
	}
	
	public void setFeature(String soID, String feature) {
		try {
			getServiceOrderFeatureSetDAO().setFeature(soID, feature);
		} catch (DataServiceException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public IServiceOrderFeatureSetDAO getServiceOrderFeatureSetDAO() {
		return serviceOrderFeatureSetDAO;
	}

	public void setServiceOrderFeatureSetDAO(IServiceOrderFeatureSetDAO serviceOrderFeatureSetDAO) {
		this.serviceOrderFeatureSetDAO = serviceOrderFeatureSetDAO;
	}


}
