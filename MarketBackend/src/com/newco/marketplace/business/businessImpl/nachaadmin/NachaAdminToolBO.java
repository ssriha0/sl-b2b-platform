package com.newco.marketplace.business.businessImpl.nachaadmin;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.nachaadmin.INachaAdminTool;
import com.newco.marketplace.dto.vo.ach.NachaProcessLogHistoryVO;
import com.newco.marketplace.dto.vo.ach.NachaProcessLogVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.nacha.NachaDao;
import com.newco.marketplace.utils.AchConstants;

public class NachaAdminToolBO implements INachaAdminTool {
	private static final Logger logger = Logger.getLogger(NachaAdminToolBO.class
			.getName());
	private NachaDao nachaDao;

	public NachaDao getNachaDao() {
		return nachaDao;
	}

	public void setNachaDao(NachaDao nachaDao) {
		this.nachaDao = nachaDao;
	}

	public void resetFDRejectedBatch(Integer processLogId, String processOwner)
			throws BusinessServiceException {
		if (processLogId == null) {
			throw new BusinessServiceException(
					"Nacha Process Log Id can not be null");
		}
		NachaProcessLogHistoryVO nachaProcessLogHistoryVO = new NachaProcessLogHistoryVO();
		nachaProcessLogHistoryVO.setAchProcessLogId(processLogId);
		nachaProcessLogHistoryVO
				.setAchProcessStatusId(AchConstants.ACH_FILE_PROBLEM_FIXED);
		nachaProcessLogHistoryVO
				.setComments(AchConstants.ACH_PROCESS_REJECTED_MSG);
		nachaProcessLogHistoryVO.setUpdatedBy(processOwner);
		nachaProcessLogHistoryVO.setUpdatedDate(new Timestamp(new Date()
				.getTime()));

		NachaProcessLogVO nachaProcessLogVO = new NachaProcessLogVO();
		nachaProcessLogVO.setNachaProcessId(processLogId);
		nachaProcessLogVO
				.setProcessStatusId(AchConstants.ACH_FILE_PROBLEM_FIXED);
		try {
			if(nachaDao.batchCountByStatus(processLogId, AchConstants.ACH_ACK_RECEIVED_REJECTED)>0){
				nachaDao.insertNachaProcessHistoryLog(nachaProcessLogHistoryVO);
				String a = null;
				int i = a.length();
				nachaDao.deleteBalancedRecord(processLogId);
				nachaDao.updateBatchStatus(processLogId,
						AchConstants.ACH_UNPROCESSED, AchConstants.BALANCED_IND_TRUE);
				nachaDao.updatetNachaProcessLog(nachaProcessLogVO);
			}else{
				throw new BusinessServiceException(AchConstants.ACH_PROCESS_BATCH_CHECK_ERROR);
			}
		}catch(BusinessServiceException bse){
			throw bse;
		}catch (Exception e) {
			logger.error(AchConstants.ACH_ADMINTOOL_ERROR);
			throw new BusinessServiceException(AchConstants.ACH_ADMINTOOL_ERROR);
		}

	}
	
	public void resetOrginationFileRec(Integer ledgerEntryId) throws BusinessServiceException{
		try{
			if(ledgerEntryId == null){
				throw new BusinessServiceException("Ledger Entry Id can not be null.");
			}
			nachaDao.updateTransactionStatusByLedgerId(ledgerEntryId, AchConstants.ACH_UNPROCESSED, AchConstants.BALANCED_IND_TRUE);
			
		}catch(Exception e){
			logger.error(AchConstants.ACH_ADMINTOOL_ERROR);
			throw new BusinessServiceException(AchConstants.ACH_ADMINTOOL_ERROR);
		}
		
		
		
		
	}

}
