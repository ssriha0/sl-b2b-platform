package com.newco.marketplace.business.businessImpl.inhomeAutoCloseNotification;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeautoclosenotification.IInhomeAutoCloseBO;
import com.newco.marketplace.inhomeautoclosenotification.vo.InHomeAutoCloseVO;
import com.newco.marketplace.persistence.iDao.inhomeAutoCloseNotification.IInhomeAutoCloseDao;

public class InhomeAutoCloseBOImpl implements IInhomeAutoCloseBO {
	private IInhomeAutoCloseDao inhomeAutoCloseDao;
	
	private static final Logger LOGGER = Logger.getLogger(InhomeAutoCloseBOImpl.class.getName());
	
	public void setEmailIndicator(List<String> failureList) throws BusinessServiceException{
		try {
			inhomeAutoCloseDao.setEmailIndicator(failureList);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in updating data " + dse.getMessage(), dse);
		}
	}
	
	public List<InHomeAutoCloseVO> fetchRecords()throws BusinessServiceException{
		List<InHomeAutoCloseVO> listOfRecordsForFailureNotification=new ArrayList<InHomeAutoCloseVO>();
		try {
			listOfRecordsForFailureNotification=inhomeAutoCloseDao.fetchRecords();
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in updating data " + dse.getMessage(), dse);
		}
		return listOfRecordsForFailureNotification;
	}
	
	public String getRecipientIdFromDB(String appKey)throws BusinessServiceException{
		String appValue=null;
		try {
			appValue = inhomeAutoCloseDao.getRecipientIdFromDB(appKey);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in updating data " + dse.getMessage(), dse);
		}
		return appValue;
	}

	public IInhomeAutoCloseDao getInhomeAutoCloseDao() {
		return inhomeAutoCloseDao;
	}

	public void setInhomeAutoCloseDao(IInhomeAutoCloseDao inhomeAutoCloseDao) {
		this.inhomeAutoCloseDao = inhomeAutoCloseDao;
	}
	
	

}
