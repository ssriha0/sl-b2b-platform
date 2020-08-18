package com.newco.marketplace.persistence.iDao.so.order;

import com.newco.marketplace.exception.core.DataServiceException;

public interface IServiceOrderFeatureSetDAO {
	
	public String getFeature(String soID, String feature) throws DataServiceException;
	public void setFeature(String soID, String feature) throws DataServiceException;
	
}
