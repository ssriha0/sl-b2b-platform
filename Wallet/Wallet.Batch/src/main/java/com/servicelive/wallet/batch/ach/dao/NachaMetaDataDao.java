package com.servicelive.wallet.batch.ach.dao;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;

// TODO: Auto-generated Javadoc
/**
 * NachaMetaDataDaoImpl.java - This class retrieves various template information
 * for the Nacha extract.
 * 
 * @author Siva
 * @version 1.0
 */
public class NachaMetaDataDao extends ABaseDao implements INachaMetaDataDao {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NachaMetaDataDao.class.getName());

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaMetaDataDao#retrieveFieldDetais(com.servicelive.wallet.batch.ach.vo.FieldDetailVO)
	 */
	public ArrayList<FieldDetailVO> retrieveFieldDetais(FieldDetailVO fieldVO) throws DataServiceException {

		ArrayList<FieldDetailVO> fieldDetailList = new ArrayList<FieldDetailVO>();

		try {
			fieldDetailList = (ArrayList) queryForList("achquery.select", fieldVO);
		} catch (Exception ex) {
			logger.info("[NachaMetaDataDaoImpl.retrieveConfirmation DetailRecord - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("NachaMetaDataDaoImpl.retrieveConfirmationDetailRecord() Exception", ex);
		}
		return fieldDetailList;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.INachaMetaDataDao#retrieveUniqueBatchIds()
	 */
	public ArrayList<TransactionBatchVO> retrieveUniqueBatchIds(Date runDate) throws DataServiceException {

		ArrayList<TransactionBatchVO> transactionBatchList = new ArrayList<TransactionBatchVO>();
		try {
			transactionBatchList = (ArrayList) queryForList("nachatranbatch.query", runDate);
		} catch (Exception ex) {
			logger.info("[NachaMetaDataDaoImpl.retrieveUniqueBatchIds - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("NachaMetaDataDaoImpl.retrieveUniqueBatchIds() Exception", ex);
		}
		return transactionBatchList;
	}

}
