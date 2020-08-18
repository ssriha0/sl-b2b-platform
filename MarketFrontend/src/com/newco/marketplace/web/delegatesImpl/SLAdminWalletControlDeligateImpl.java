package com.newco.marketplace.web.delegatesImpl;

import java.util.List;

import org.apache.log4j.Logger;


import com.newco.marketplace.business.iBusiness.financeManager.IWalletControlBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.wallet.WalletControlVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.ISLAdminWalletControlDeligate;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
public class SLAdminWalletControlDeligateImpl implements
		ISLAdminWalletControlDeligate {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private IWalletControlBO walletControlBO;

	private static final Logger logger = Logger
			.getLogger(SLAdminWalletControlDeligateImpl.class.getName());

	@Override
	public ProcessResponse uploadWalletControlDocument(DocumentVO documentVO,WalletControlVO walletControlVO)
			throws DelegateException {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		try {
			logger.info("start insertDocument method of SLAdminWalletControlDocumentDeligateImpl");
			processResp = walletControlBO
					.insertWalletControlDocument(documentVO,walletControlVO);
		} catch (BusinessServiceException e) {
			logger.error("Exception thrown inserting vendor document", e);
			
            processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
            processResp.setMessage("Exception thrown inserting wallet document");
            throw new DelegateException("Exception : While Inserting the Wallet Document ", e);
		} 
		
		return processResp;
	}

	@Override
	public DocumentVO downloadWalletControlDocument(Integer documentID)
			throws SLBusinessServiceException {
		try {
			logger.info("calling the document service for downloading the wallet document");
			return walletControlBO.downloadWalletControlDocument(documentID);
		} catch (Exception exception) {
			logger.error("Exception: " + exception);
			throw new SLBusinessServiceException(
					"General Exception downloadWalletControlDocument() due to "
							+ exception.getMessage());
		}
	}
	
	@Override
	public Integer deleteWalletControlDocument(Integer documentID)
			throws SLBusinessServiceException {
		try {
			logger.info("calling the delete document");
			return walletControlBO.deleteWalletControlDocument(documentID);
		} catch (Exception exception) {
			logger.error("Exception: " + exception);
			throw new SLBusinessServiceException(
					"General Exception deleteWalletControlDocument() due to "
							+ exception.getMessage());
		}
	}
	
	public WalletControlVO saveWalletControlDetail(WalletControlVO walletControlVO, List<DocumentVO> documentVOs)
			throws DelegateException {
		
		try {
			logger.info("start saveWalletControl method of SLAdminWalletControlDocumentDeligateImpl");
			walletControlVO= walletControlBO.walletControlInsertOrUpdate(walletControlVO, documentVOs);

		} catch ( BusinessServiceException | DBException exception) {
			logger.error("Error occured in saveWalletControl SLAdminWalletControlDocumentDeligateImpl : ", exception);
			
		}
		logger.info("end saveWalletControl method of SLAdminWalletControlDocumentDeligateImpl");
		return walletControlVO;
	}

	public Integer lookUpWalletControl(String name)throws DelegateException {
		
		Integer result=null;
		try {
			logger.info("start lookUpWalletControl method of SLAdminWalletControlDocumentDeligateImpl");
			result = walletControlBO.lookUpWalletControl(name);

		} catch ( BusinessServiceException | DBException exception) {
			logger.error("Error occured in lookUpWalletControl SLAdminWalletControlDocumentDeligateImpl : ", exception);
			
		}
		logger.info("end saveWalletControl method of SLAdminWalletControlDocumentDeligateImpl");
		return result;
	}
	
	@Override
	public WalletControlVO fetchWalletControl(Integer entityID) throws DelegateException {
		WalletControlVO result = null;
		try {
			logger.info("start fetchWalletControl method of SLAdminWalletControlDocumentDeligateImpl");
			result = walletControlBO.fetchWalletControl(entityID);

		} catch (BusinessServiceException exception) {
			logger.error("Error occured in fetchWalletControl SLAdminWalletControlDocumentDeligateImpl : ", exception);
			throw new DelegateException(exception.getMessage());

		}
		logger.info("end fetchWalletControl method of SLAdminWalletControlDocumentDeligateImpl");
		return result;
	}

	public IWalletControlBO getWalletControlBO() {
		return walletControlBO;
	}



	public void setWalletControlBO(IWalletControlBO walletControlBO) {
		this.walletControlBO = walletControlBO;
	}



}
