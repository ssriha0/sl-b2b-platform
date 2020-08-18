package com.newco.marketplace.business.businessImpl.financeManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.audit.WalletControlAuditNotesBOImpl;
import com.newco.marketplace.business.iBusiness.financeManager.IWalletControlBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.wallet.LookupWalletControl;
import com.newco.marketplace.dto.vo.wallet.WalletControlDocumentVO;
import com.newco.marketplace.dto.vo.wallet.WalletControlVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.walletControl.WalletControlDao;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.newco.marketplace.vo.audit.WalletControlAuditVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.exception.SLBusinessServiceException;

public class WalletControlBO implements IWalletControlBO {
	private DocumentService documentService;
	private WalletControlDao walletControlDao;
	//code change for SLT-2323
	private FinanceManagerBO financeManagerBO;
	private WalletControlAuditNotesBOImpl walletControlAuditNotesBO;

	public WalletControlAuditNotesBOImpl getWalletControlAuditNotesBO() {
		return walletControlAuditNotesBO;
	}

	public void setWalletControlAuditNotesBO(WalletControlAuditNotesBOImpl walletControlAuditNotesBO) {
		this.walletControlAuditNotesBO = walletControlAuditNotesBO;
	}

	public FinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(FinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	private static final Logger logger = Logger.getLogger(WalletControlBO.class
			.getName());

	public ProcessResponse insertWalletControlDocument(DocumentVO documentVO, WalletControlVO walletControlVO)
			throws SLBusinessServiceException {
		ProcessResponse pr = new ProcessResponse();
		documentVO.setDocCategoryId(getDocumentCategory(walletControlVO));
				try {
			logger.info("calling the document service for uploading the wallet document");
			documentVO = documentService.createDocument(documentVO);
			if (null != documentVO.getDocumentId()
					&& documentVO.getDocumentId() > 0) {
				pr.setObj(documentVO.getDocumentId());
			}
		} catch (Exception exception) {
			logger.error("Exception: " + exception);
			throw new SLBusinessServiceException(
					"General Exception @WalletControlBO.insertWalletControlDocument() due to "
							+ exception.getMessage());
		}
		return pr;

	}
	
	//code changes for SLT-2323
	public Integer getHoldReleaseTemplateIds(WalletControlVO walletControlVO) throws SLBusinessServiceException {
		Integer tempId = null;
		try {
			LookupWalletControl tempDetails = walletControlDao.getHoldReleaseTemplateIds(walletControlVO.getWalletControlId());
			if (walletControlVO.getOnHold()) {
				tempId = tempDetails.getEmailTemplateId();
			}
			else {
				tempId=	tempDetails.getReleaseTemplateId();
			}
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			throw new SLBusinessServiceException("General Exception @WalletControlBO.getHoldReleaseTemplateIds() due to"+e.getMessage());
		}
		return tempId;
	}
	
	public WalletControlVO walletControlInsertOrUpdate(WalletControlVO walletControlVO,
			List<DocumentVO> documentVOs) throws SLBusinessServiceException {
		//START:changes for SLT-2367
		WalletControlAuditVO walletControlAuditVO=new WalletControlAuditVO();
		walletControlAuditVO.setVendorId(walletControlVO.getEntityId());
		walletControlAuditVO.setWalletOnHold(walletControlVO.getOnHold());
		walletControlAuditVO.setWalletControlType(walletControlVO.getWalletControlType());
		walletControlAuditVO.setReviewedBy(walletControlVO.getModifiedBy());
		//END: changes for SLT-2367
		Integer documentTypeId = getDocumentCategory(walletControlVO);
		

		Integer templateId =getHoldReleaseTemplateIds(walletControlVO);
		ArrayList<Integer> docIds= new ArrayList<Integer>();
	
		
		try {
			logger.info("calling the dao layer for inserting the wallet control details");
			walletControlVO = walletControlDao
					.walletControlInsertOrUpdate(walletControlVO);

			if (documentTypeId!=null && walletControlVO.getId() != null) {
				List<WalletControlDocumentVO> walletControlDocumentVOList = new ArrayList<WalletControlDocumentVO>();
				for (DocumentVO documentVO : documentVOs) {
					WalletControlDocumentVO walletControlDocumentVO = new WalletControlDocumentVO();
					walletControlDocumentVO.setDocCategoryId(documentTypeId);
					walletControlDocumentVO.setDocumentId(documentVO
							.getDocumentId());
					walletControlDocumentVO
							.setEntityWalletControlId(walletControlVO.getId());
					walletControlDocumentVO = walletControlDao
							.walletControlDocumentInsertOrUpdate(walletControlDocumentVO);
					walletControlDocumentVOList.add(walletControlDocumentVO);
					//code change for SLT-2323
					docIds.add(documentVO.getDocumentId());
				}
				walletControlVO.setWalletControlDocumentVO(walletControlDocumentVOList);
				financeManagerBO.sendAccountHoldReleaseMail(walletControlVO.getEntityId(), OrderConstants.PROVIDER, templateId, docIds);
				//changes for SLT-2367
				walletControlAuditNotesBO.walletControlAuditNotes(walletControlAuditVO);
			}

		} catch (Exception exception) {
			logger.error("Exception : ", exception);
			throw new SLBusinessServiceException(
					"General Exception @ResourceDocumentDaoImpl.uploadDocument()",
					exception);
		}
		
		return walletControlVO;
	}
	
	private Integer getDocumentCategory(WalletControlVO walletControlVO){
	
	Integer documentTypeId = null;
	String docCatogery = null;

	if (walletControlVO.getOnHold()) {
		docCatogery = WalletDocumentCategoryEnum.WALLET_CONTROL.name();
	} else {
		docCatogery = WalletDocumentCategoryEnum.RELEASE_WALLET_CONTROL
				.name();
	}
	try {
		documentTypeId = walletControlDao
				.lookUpDocumentCategory(docCatogery);
	} catch (DataServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return documentTypeId;
	}
	
	@Override
	public DocumentVO downloadWalletControlDocument(Integer documentID)
			throws SLBusinessServiceException {
		try {
			logger.info("calling the document service for downloading the wallet document");
			DocumentVO documentVO = (DocumentVO) walletControlDao.getDocument(documentID);
			return documentService.retrieveDocumentFromDBOrFS(documentVO);
		} catch (Exception exception) {
			logger.error("Exception: " + exception);
			throw new SLBusinessServiceException(
					"General Exception @WalletControlBO.downloadWalletControlDocument() due to "
							+ exception.getMessage());
		}

	}
	
	
	public Integer deleteWalletControlDocument(Integer documentID) throws SLBusinessServiceException {
		try {
			logger.info("Start delete the wallet document");
			DocumentVO documentVO = (DocumentVO) walletControlDao.getDocument(documentID);
			if (documentService.deleteFileFromFileSystem(documentVO)>0) {
				logger.info("calling the dao to delete database document");
				return walletControlDao.deleteDocument(documentID);
			} else {
				return 0;
			}
		} catch (Exception exception) {
			logger.error("Exception: " + exception);
			throw new SLBusinessServiceException(
					"General Exception @WalletControlBO.deleteWalletControlDocument() due to "
							+ exception.getMessage());
		}

	}
	
	@Override
	public WalletControlVO fetchWalletControl(Integer entityID) throws SLBusinessServiceException {
		try {
			logger.info("Start feching the wallet control data.");
			return walletControlDao.fetchWalletControl(entityID);
			
		} catch (Exception exception) {
			logger.error("Exception: " + exception);
			throw new SLBusinessServiceException(
					"General Exception @WalletControlBO.fetchWalletControl() due to "
							+ exception.getMessage());
		}

	}

	
	
	public Integer lookUpWalletControl(String name)
			throws BusinessServiceException, DBException {
		Integer walletControlId =null;
		try {
		walletControlId = walletControlDao.lookUpWalletControl(name);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return walletControlId;
	}

	/**
	 * @return the documentService
	 */
	public DocumentService getDocumentService() {
		return documentService;
	}

	/**
	 * @param documentService
	 *            the documentService to set
	 */
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	/**
	 * @return the walletControlDao
	 */
	public WalletControlDao getWalletControlDao() {
		return walletControlDao;
	}

	/**
	 * @param walletControlDao
	 *            the walletControlDao to set
	 */
	public void setWalletControlDao(WalletControlDao walletControlDao) {
		this.walletControlDao = walletControlDao;
	}

}
