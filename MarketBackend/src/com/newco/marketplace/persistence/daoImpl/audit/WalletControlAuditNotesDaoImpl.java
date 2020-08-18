package com.newco.marketplace.persistence.daoImpl.audit;

import java.util.HashMap;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.audit.IWalletControlAuditNotesDao;
import com.newco.marketplace.vo.audit.TransferReasonCodeVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class WalletControlAuditNotesDaoImpl extends ABaseImplDao implements IWalletControlAuditNotesDao {

	@Override
	public TransferReasonCodeVO getTransferReasonCodeDetails(Integer transferReasonCodeId) throws DataServiceException {
		try {
			TransferReasonCodeVO transferReasonCodeVO = new TransferReasonCodeVO();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("transferReasonCodeId", transferReasonCodeId);
			transferReasonCodeVO = (TransferReasonCodeVO) queryForObject("getTransferReasonCode.query", map);
			return transferReasonCodeVO;
		} catch (Exception e) {
			logger.error(e);
			throw new DataServiceException("Error while fetching transfer reason code");
		}
	}

}
