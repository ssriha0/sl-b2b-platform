package com.newco.marketplace.iso.processors;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.iso.IWorkerMapper;
import com.newco.marketplace.persistence.daoImpl.ledger.WorkerMapperDaoImpl;

public class WorkerMapper implements IWorkerMapper{
	private static final Logger logger = Logger.getLogger(WorkerMapper.class);
	private WorkerMapperDaoImpl workerMapperDao ;
	public String findNextAvailableQueue(Long pan) throws Exception
	{
		String queueName = workerMapperDao.findNextAvailableQueue(pan);
		return queueName;
	}
	public WorkerMapperDaoImpl getWorkerMapperDao() {
		return workerMapperDao;
	}
	public void setWorkerMapperDao(WorkerMapperDaoImpl workerMapperDao) {
		this.workerMapperDao = workerMapperDao;
	}
}
