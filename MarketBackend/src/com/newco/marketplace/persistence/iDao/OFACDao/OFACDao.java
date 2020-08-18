package com.newco.marketplace.persistence.iDao.OFACDao;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.newco.marketplace.dto.vo.ach.OFACProcessLogVO;
import com.newco.marketplace.dto.vo.ach.OFACProcessQueueVO;
import com.newco.marketplace.exception.DataServiceException;


public interface OFACDao  {

	/**
	 * @return
	 * @throws DataServiceException
	 */
	public  ArrayList<OFACProcessQueueVO> getOfacData()throws DataServiceException;
    
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public  Timestamp getPeriodStartDate()throws DataServiceException;
	
	/**
	 * @param ofacProcessLogVO
	 * @throws DataServiceException
	 */
	public  void insertOFACProcessLog(OFACProcessLogVO ofacProcessLogVO)throws DataServiceException;
}
