package com.newco.marketplace.persistence.daoImpl.ach;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.ach.FieldDetailVO;
import com.newco.marketplace.dto.vo.ach.TransactionBatchVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.ach.NachaMetaDataDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * NachaMetaDataDaoImpl.java - This class retrieves various template information
 * for the Nacha extract.
 * 
 * @author Siva
 * @version 1.0
 */
public class NachaMetaDataDaoImpl extends ABaseImplDao implements NachaMetaDataDao {

	private static final Logger logger = Logger.getLogger(NachaMetaDataDaoImpl.class.getName());

	/**
	 * This method retrieves the unique batch Ids for the set of user ledger
	 * transactions. A batch represents the type of SEC code.
	 * 
	 * @return ArrayList<TransactionBatchVO>
	 */
	public ArrayList<TransactionBatchVO> retrieveUnbalancedBatches() throws DataServiceException {
		ArrayList<TransactionBatchVO> transactionBatchList = new ArrayList<TransactionBatchVO>();
		try {
			transactionBatchList = (ArrayList) queryForList("unbalancedBatch.query", null);
		} catch (Exception ex) {
			logger.info("[NachaMetaDataDaoImpl.retrieveUnbalancedBatches - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("NachaMetaDataDaoImpl.retrieveUnbalancedBatches() Exception", ex);
		}
		return transactionBatchList;
	}

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

	/**
	 * This method retrieves the file record template from the database
	 * 
	 * @return ArrayList<FieldDetailVO>
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

	public static void main(String k[]) {
		try {
			NachaMetaDataDaoImpl nachaMetaDataDaoImpl = (NachaMetaDataDaoImpl) MPSpringLoaderPlugIn.getCtx().getBean("nachaMetadataDao");
			System.out.println("looked up the bean");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
