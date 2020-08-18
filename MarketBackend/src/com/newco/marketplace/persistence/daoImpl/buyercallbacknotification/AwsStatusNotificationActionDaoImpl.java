package com.newco.marketplace.persistence.daoImpl.buyercallbacknotification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataAccessException;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyercallbacknotification.IAwsStatusNotificationActionDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class AwsStatusNotificationActionDaoImpl extends ABaseImplDao implements IAwsStatusNotificationActionDao {

	@Override
	public List<BuyerCallbackNotificationVO> getStatusDetails(List<String> param) throws DataServiceException {
		List<BuyerCallbackNotificationVO> buyerCallbackStatusDetails = new ArrayList<BuyerCallbackNotificationVO>();
		try {
			// int updateFlag;
			Date start = new Date();
			buyerCallbackStatusDetails = (List<BuyerCallbackNotificationVO>) queryForList(
					"getbuyerCallbackStatus.aws.query", param);
			// updateFlag = update("updateAlertStatusFlag.aws.query", instant);
			Date end = new Date();
			long timeTakenForQuery = end.getTime() - start.getTime();
			logger.info("getbuyerCallbackStatus.aws.query : " + timeTakenForQuery + " ms");

		} catch (DataAccessException e) {

			logger.error("Exception occured in getbuyerCallbackStatus.aws.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return buyerCallbackStatusDetails;
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

	@Override
	public void setUpdatedAlert_id(List<String> param) throws DataServiceException {
		int updateAlertFlag;
		try {
			Date start = new Date();
			updateAlertFlag = update("updateAlertStatusFlag.aws.query", param);
			Date end = new Date();
			long timeTakenForQuery = end.getTime() - start.getTime();
			logger.info("updateAlertStatusFlag.aws.query : " + timeTakenForQuery + " ms");
			
		} catch (DataAccessException e) {
			logger.error("Exception occured in updateAlertStatusFlag.aws.query due to " + e.getMessage());
			e.printStackTrace();
		}

	}

}
