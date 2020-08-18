package com.newco.marketplace.persistence.iDao.audit;


import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.audit.TransferReasonCodeVO;;

public interface IWalletControlAuditNotesDao {
	public TransferReasonCodeVO getTransferReasonCodeDetails(Integer transferReasonCodeId) throws DataServiceException;
}
