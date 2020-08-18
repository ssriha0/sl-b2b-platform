package com.newco.marketplace.persistence.iDao.integration;

import java.util.List;

import com.newco.marketplace.dto.vo.integration.IntegrationBatchVO;
import com.newco.marketplace.dto.vo.integration.IntegrationTransactionVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IIntegrationDAO {
	public List<IntegrationBatchVO> getFileUploadBatches() throws DataServiceException;
	public List<IntegrationTransactionVO> getTransactions(Long batchId) throws DataServiceException;
	public IntegrationBatchVO getProcessedFileUploadBatchByFilename(String fileName) throws DataServiceException;
}
