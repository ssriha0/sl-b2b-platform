package com.newco.marketplace.persistence.daoImpl.ledger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.ledger.IWorkerMapperDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class WorkerMapperDaoImpl extends ABaseImplDao implements IWorkerMapperDao{
	public String findNextAvailableQueue(Long pan) throws DataServiceException
	{
		String returnQueue = (String) queryForObject("checkPanOnAnyQueue.query", pan); 
		if(returnQueue==null)
			returnQueue = (String) queryForObject("checkIfAnyQueueIsAvailable.query", null);
		return returnQueue;
	}
}
