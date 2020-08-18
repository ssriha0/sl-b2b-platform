package com.newco.marketplace.persistence.iDao.ledger;

import com.newco.marketplace.exception.core.DataServiceException;
public interface IWorkerMapperDao {

	public String findNextAvailableQueue(Long pan) throws DataServiceException;
}
