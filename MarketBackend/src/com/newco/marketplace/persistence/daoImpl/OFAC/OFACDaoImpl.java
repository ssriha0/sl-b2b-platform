package com.newco.marketplace.persistence.daoImpl.OFAC;


import java.sql.Timestamp;
import java.util.ArrayList;


import com.newco.marketplace.dao.impl.MPBaseDaoImpl;

import com.newco.marketplace.dto.vo.ach.OFACProcessLogVO;
import com.newco.marketplace.dto.vo.ach.OFACProcessQueueVO;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.OFACDao.OFACDao;


public class OFACDaoImpl extends MPBaseDaoImpl implements OFACDao {
	
	public   ArrayList<OFACProcessQueueVO> getOfacData()throws DataServiceException
	{
		ArrayList<OFACProcessQueueVO> ofacprocessqueuevo =null;
		
		try{
			 ofacprocessqueuevo =  (ArrayList<OFACProcessQueueVO>) getSqlMapClient().queryForList("OFAC.processQueue.select",ofacprocessqueuevo);
		}catch(Exception e){
			throw new DataServiceException(
					"Exception occured in OFACDaoImpl.getOfacData", e);
		}
	
		return ofacprocessqueuevo;
		
	}
	
	// Fetching period end date of last KYC file from ofac_process_log table.
	public Timestamp getPeriodStartDate() throws DataServiceException {
		Timestamp periodStartDate = null;
		try{
			periodStartDate =  (Timestamp) getSqlMapClient().queryForObject("OFAC.getPeriodStartDate.select", null);
		}catch(Exception e){
			throw new DataServiceException(
					"Exception occured in OFACDaoImpl.getPeriodStartDate", e);
		}
		return periodStartDate;
	}
	
	// Inserting KYC log to ofac_process_log.
	public void insertOFACProcessLog(OFACProcessLogVO ofacProcessLogVO) throws DataServiceException {
		try{
			insert("OFAC.processLog.insert", ofacProcessLogVO);
		}catch(Exception e){
			throw new DataServiceException(
					"Exception occured in OFACDaoImpl.insertOFACProcessLog", e);
		}
	}
	
}
