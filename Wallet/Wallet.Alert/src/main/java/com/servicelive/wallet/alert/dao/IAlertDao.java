package com.servicelive.wallet.alert.dao;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.vo.AlertTaskVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IAlertDao.
 */
public interface IAlertDao {

	/**
	 * addAlertToQueue.
	 * 
	 * @param alertTask 
	 * 
	 * @return boolean
	 * 
	 * @throws DataServiceException 
	 */
	public boolean addAlertToQueue(AlertTaskVO alertTask) throws DataServiceException;
}
