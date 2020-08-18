package com.newco.marketplace.persistence.daoImpl.integration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.integration.IntegrationBatchVO;
import com.newco.marketplace.dto.vo.integration.IntegrationTransactionVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.integration.IIntegrationDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class IntegrationDAOImpl extends ABaseImplDao implements IIntegrationDAO {
	private static final Logger logger = Logger.getLogger(IntegrationDAOImpl.class.getName());
	
	@SuppressWarnings("unchecked")
	public List<IntegrationBatchVO> getFileUploadBatches() throws DataServiceException {
		logger.info("Inside of IntegrationDAOImpl::::getFileUploadBatches()");
		try {
			ArrayList<IntegrationBatchVO> batches = (ArrayList<IntegrationBatchVO>) queryForList("integration.queryFileUploadBatches");
			return batches;
		}catch(Exception ex){
			logger.info("[IntegrationDAOImpl.getFileUploadBatches - Exception] "
	                + StackTraceHelper.getStackTrace(ex));
	        throw new DataServiceException("Error querying for file upload batches: ", ex);
		}
	}
	
	public IntegrationBatchVO getProcessedFileUploadBatchByFilename(String fileName) throws DataServiceException {
		logger.info("Inside of IntegrationDAOImpl::::getProcessedFileUploadBatchByFilename()");
		try {
			IntegrationBatchVO batch = (IntegrationBatchVO) queryForObject("integration.queryFileUploadBatchByFilename", fileName);
			return batch;
		}catch(Exception ex){
			logger.info("[IntegrationDAOImpl.getProcessedFileUploadBatchByFilename - Exception] "
	                + StackTraceHelper.getStackTrace(ex));
	        throw new DataServiceException("Error querying for the file upload batch matching filename '" + fileName + "' : ", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<IntegrationTransactionVO> getTransactions(Long batchId) throws DataServiceException {
		logger.info("Inside of IntegrationDAOImpl::::getTransactions()");
		try {
			ArrayList<IntegrationTransactionVO> transactions = (ArrayList<IntegrationTransactionVO>) queryForList("integration.queryTransactions", batchId);
			return transactions;
		}catch(Exception ex){
			logger.info("[IntegrationDAOImpl.getTransactions - Exception] "
	                + StackTraceHelper.getStackTrace(ex));
	        throw new DataServiceException("Error querying for transactions: ", ex);
		}
	}
}
