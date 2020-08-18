package com.newco.marketplace.business.iBusiness.audit;

import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.vo.audit.WalletControlAuditVO;

public interface IWalletControlAuditNotesBO {
	public String walletControlAuditNotes(WalletControlAuditVO walletControlAuditVO) throws AuditException;
	public String addWalletControlAuditNotesSLBucks(WalletControlAuditVO walletControlAuditVO) throws AuditException;
	}
