package com.newco.marketplace.persistence.daoImpl.buyercallbacknotification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataAccessException;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyercallbacknotification.IFailedCommunicationToAwsActionDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class FailedCommunicationToAwsActionDaoImpl extends ABaseImplDao implements IFailedCommunicationToAwsActionDao {

	@Override
	public List<BuyerCallbackNotificationVO> getFailureDetails(String error) throws DataServiceException {
		List<BuyerCallbackNotificationVO> buyerCallbackFailureDetails = new ArrayList<BuyerCallbackNotificationVO>();
		try {
			Date start = new Date();
			buyerCallbackFailureDetails = (List<BuyerCallbackNotificationVO>) queryForList(
					"buyerCallbackFailureNotification.aws.query", error);
			Date end = new Date();
			long timeTakenForQuery = end.getTime() - start.getTime();
			logger.info("buyerCallbackFailureNotification.aws.query : " + timeTakenForQuery + " ms");
		} catch (DataAccessException e) {
			logger.error("Exception occured in buyerCallbackFailureNotification.aws.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return buyerCallbackFailureDetails;
	}

	@Override
	public void setUpdatedAlert_id(String error) throws DataServiceException {
		int updateAlertFlag;
		try {
			Date start = new Date();
			updateAlertFlag = update("updateFailureAlertStatusFlag.aws.query", error);
			Date end = new Date();
			long timeTakenForQuery = end.getTime() - start.getTime();
			logger.info("updateFailureAlertStatusFlag.aws.query : " + timeTakenForQuery + " ms");

		} catch (DataAccessException e) {
			logger.error("Exception occured in updateFailureAlertStatusFlag.aws.query due to " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public boolean setAlertStatus(AlertTask alertTask) throws DataServiceException {
		try {
			Date start = new Date();
			insert("alert.insert", alertTask);
			Date end = new Date();
			long timeTakenForQuery = end.getTime() - start.getTime();
			logger.info("alert.insert : " + timeTakenForQuery + " ms");

		} catch (DataAccessException e) {
			logger.error("Exception occured in alert.insert due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return true;
	}

}
