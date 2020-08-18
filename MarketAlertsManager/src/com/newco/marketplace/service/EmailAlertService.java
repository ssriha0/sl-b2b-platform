package com.newco.marketplace.service;

import java.io.Serializable;

import com.newco.marketplace.exception.core.DataServiceException;

public interface EmailAlertService extends Serializable{

	  public void processAlert()throws DataServiceException;
}
