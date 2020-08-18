package com.servicelive.wallet.batch.mocks;

import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.gl.dao.ISHCOMSDao;

// TODO: Auto-generated Javadoc
/**
 * Class MockSHCOMSDao.
 */
public class MockSHCOMSDao implements ISHCOMSDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.ISHCOMSDao#insertSHCGLHistoryRecord(java.lang.Integer)
	 */
	public void insertSHCGLHistoryRecord(Integer glProcessLogId) throws DataServiceException {

	// TODO Auto-generated method stub

	}

	public void updateOMSSKU(Integer glProcessId, List<String> soIdList) {
		// TODO Auto-generated method stub
		
	}

	public void markServiceOrdersBeingProcessed(Integer glProcessId,
			List<String> soIdList) {
		// TODO Auto-generated method stub
		
	}

}
