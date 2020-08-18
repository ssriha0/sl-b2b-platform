package com.newco.marketplace.business.businessImpl.audit;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.provider.ABaseBO;
import com.newco.marketplace.business.iBusiness.audit.IAuditProfileBO;
import com.newco.marketplace.business.iBusiness.audit.IWalletControlAuditNotesBO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.audit.IWalletControlAuditNotesDao;
import com.newco.marketplace.vo.audit.TransferReasonCodeVO;
import com.newco.marketplace.vo.audit.WalletControlAuditVO;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.sears.os.service.ServiceConstants;

public class WalletControlAuditNotesBOImpl extends ABaseBO implements IWalletControlAuditNotesBO {
	private final static Logger logger = Logger.getLogger(WalletControlAuditNotesBOImpl.class.getName());
	protected IAuditProfileBO auditProfileBoTarget;
	protected IWalletControlAuditNotesDao walletControlAuditNotesDao;

	public String walletControlAuditNotes(WalletControlAuditVO walletControlAuditVO) throws AuditException {
		logger.info("[START]:WalletControlAuditNotesBOImpl.walletControlAuditNotes");
		StringBuilder noteBody = new StringBuilder();
		int vendorId = walletControlAuditVO.getVendorId();
		if (walletControlAuditVO.isWalletOnHold()) {
			noteBody.append(ServiceConstants.ACCOUNT_UNDER_WALLET_CONTROL);
		} else {
			noteBody.append(ServiceConstants.ACCOUNT_RELEASED__WALLET_CONTROL);
		}
		noteBody.append(" " + walletControlAuditVO.getWalletControlType());

		if (walletControlAuditVO.isSendEmailNotice()) {
			noteBody.append(ServiceConstants.HYPHEN_SYMBOL+ServiceConstants.WALLET_CONTROL_EMAIL_SENT);
		} else {
			noteBody.append(ServiceConstants.HYPHEN_SYMBOL +ServiceConstants.WALLET_CONTROL_EMAIL_NOT_SENT);
		}
		VendorNotesVO vendorNotesVO = new VendorNotesVO();
		vendorNotesVO.setVendorId(vendorId);
		vendorNotesVO.setNote(noteBody.toString());
		vendorNotesVO.setModifiedBy(walletControlAuditVO.getReviewedBy());
		try {
			auditProfileBoTarget.addVendorNote(vendorNotesVO);
		} catch (BusinessServiceException e) {
			logger.error("[ERROR]:WalletControlAuditNotesBOImpl.walletControlAuditNotes - error in adding audit notes "+e);
			throw new AuditException("Currently we're not able to add notes. Please try again.");
		}
		return ServiceConstants.SUCCESS;
	}
	
	public String addWalletControlAuditNotesSLBucks(WalletControlAuditVO walletControlAuditVO) throws AuditException{
		logger.info("[START]:WalletControlAuditNotesBOImpl.walletControlAuditNotes");
		
		StringBuilder noteBody = new StringBuilder();
		int vendorId = walletControlAuditVO.getVendorId();
		Integer transferReasonCodeId=walletControlAuditVO.getEscheatmentTransferReasonCodeId();
		Double escheatmentAmount=walletControlAuditVO.getAmount();
		TransferReasonCodeVO transferReasonCodeVO;
		try {
			transferReasonCodeVO=walletControlAuditNotesDao.getTransferReasonCodeDetails(transferReasonCodeId);
		} catch (DataServiceException ex) {
			logger.error("[ERROR]:WalletControlAuditNotesBOImpl.walletControlAuditNotes"+ex);
			throw new AuditException(ex.getMessage());
			
		}
		noteBody.append(transferReasonCodeVO.getDescription());
		noteBody.append(ServiceConstants.HYPHEN_SYMBOL+ServiceConstants.DOLLAR_SYMBOL+escheatmentAmount);
		noteBody.append(ServiceConstants.HYPHEN_SYMBOL+transferReasonCodeVO.getTransferReasonNote());
		if (walletControlAuditVO.isSendEmailNotice()) {
			noteBody.append(ServiceConstants.HYPHEN_SYMBOL+ServiceConstants.WALLET_CONTROL_EMAIL_SENT);
		}
		VendorNotesVO vendorNotesVO = new VendorNotesVO();
		vendorNotesVO.setVendorId(vendorId);
		vendorNotesVO.setNote(noteBody.toString());
		vendorNotesVO.setModifiedBy(walletControlAuditVO.getReviewedBy());
		try {
			auditProfileBoTarget.addVendorNote(vendorNotesVO);
		} catch (BusinessServiceException e) {
			logger.error("[ERROR]:WalletControlAuditNotesBOImpl.walletControlAuditNotesSLBucks"+e);
			throw new AuditException("Currently we're not able to add notes. Please try again.");
		}
		return ServiceConstants.SUCCESS;
	}

	public IAuditProfileBO getAuditProfileBoTarget() {
		return auditProfileBoTarget;
	}

	public void setAuditProfileBoTarget(IAuditProfileBO auditProfileBoTarget) {
		this.auditProfileBoTarget = auditProfileBoTarget;
	}

	public IWalletControlAuditNotesDao getWalletControlAuditNotesDao() {
		return walletControlAuditNotesDao;
	}

	public void setWalletControlAuditNotesDao(IWalletControlAuditNotesDao walletControlAuditNotesDao) {
		this.walletControlAuditNotesDao = walletControlAuditNotesDao;
	}

}
