package com.newco.marketplace.purchaseAmountMigration.service;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.thoughtworks.xstream.XStream;

public interface IPurchaseAmountMigrationService {
	
	public Integer getSOCount(Map<Object, Object> dateMap) throws BusinessServiceException;

	public List<String> fetchSO(Map<Object, Object> dateMap) throws BusinessServiceException;

	public void migratePurchaseAmount(String soId, XStream xstream) throws BusinessServiceException;
	
	public Map<Object, Object> getinterval(Map<Object, Object> dateMap);

}
