package com.newco.marketplace.service;

import java.io.Serializable;

import com.newco.marketplace.exception.core.DataServiceException;

public interface TokenService extends Serializable{
	 public void genrateToken() throws DataServiceException;
}
