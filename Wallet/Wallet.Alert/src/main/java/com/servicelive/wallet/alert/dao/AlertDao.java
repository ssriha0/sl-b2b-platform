package com.servicelive.wallet.alert.dao;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.vo.AlertTaskVO;

// TODO: Auto-generated Javadoc
/**
 * Class AlertDao.
 */
public class AlertDao extends ABaseDao implements IAlertDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.dao.IAlertDao#addAlertToQueue(com.servicelive.wallet.alert.vo.AlertTaskVO)
	 */
	public boolean addAlertToQueue(AlertTaskVO alertTask) throws DataServiceException {

		try {
			insert("alert.insert", alertTask);
		} catch (Exception e) {
			throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertToQueue", e);
		}
		return true;
	}

}