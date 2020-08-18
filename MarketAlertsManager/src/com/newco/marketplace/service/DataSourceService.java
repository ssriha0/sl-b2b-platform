package com.newco.marketplace.service;

import java.io.Serializable;

import com.newco.marketplace.exception.core.DataServiceException;

public interface DataSourceService extends Serializable{

	  public void processDataSources(String name) throws DataServiceException;
}
