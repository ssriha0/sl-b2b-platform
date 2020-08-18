package com.newco.marketplace.persistence.daoImpl.walletControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.wallet.WalletControlVO;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.walletControl.WalletControlDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.wallet.LookupWalletControl;
import com.newco.marketplace.dto.vo.wallet.WalletControlDocumentVO;

public class WalletControlDaoImpl extends ABaseImplDao implements WalletControlDao {
	
	private static final Logger logger = Logger.getLogger(WalletControlDaoImpl.class
			.getName());

	
	public WalletControlVO walletControlInsertOrUpdate(WalletControlVO walletControlVO) 
			throws DataServiceException {
		 logger.info("Inisde insertWalletControlDetail() of WalletControlDaoImpl");
		 Integer id=null;
			
			try {
				id = (Integer)getSqlMapClient().insert("walletControl.insertOrUpdate", walletControlVO);
				if (!(walletControlVO.getId()!=null) && id.intValue()>0 ){
				 walletControlVO.setId(id.intValue());
				}
			} catch (Exception e) {
				logger.error("WalletControlDaoImpl.getWalletControlDetail" + StackTraceHelper.getStackTrace(e));
				throw new DataServiceException("Error", e);
			}
			
			 return walletControlVO;
	}

	public WalletControlDocumentVO  walletControlDocumentInsertOrUpdate(WalletControlDocumentVO walletControlDocumentVO)  throws DataServiceException{
		 logger.info("Inisde insertWalletControlDocument() of WalletControlDaoImpl");
		 Integer id=null;
			
			try {
				id = (Integer)getSqlMapClient().insert("walletControlDocument.insertOrUpdate", walletControlDocumentVO);
				walletControlDocumentVO.setId(id.intValue());
			} catch (Exception e) {
				logger.error("WalletControlDaoImpl.getWalletControlDetail" + StackTraceHelper.getStackTrace(e));
				throw new DataServiceException("Error", e);
			}
			
			 return walletControlDocumentVO;
	}

//	public LookupWalletControl lookUpWalletControl(String name) throws DBException {
//		LookupWalletControl lookupWalletControl = new LookupWalletControl();
//		
//		try {
//			lookupWalletControl = (LookupWalletControl)getSqlMapClient().queryForObject("lookup.walletControl.query", name);
//		} catch (Exception ex) {
//			logger.info("General Exception @WalletControlDaoImpl.lookUpWalletControl() due to" + ex.getMessage());
//			throw new DBException("General Exception @WalletControlDaoImpl.lookUpWalletControl() due to " + ex.getMessage(), ex);
//		}
//		return lookupWalletControl;
//	}
	
	public Integer lookUpWalletControl(String name) throws DataServiceException {
		Integer result=null;
		
		try {
			result = (Integer)getSqlMapClient().queryForObject("lookup.walletControl.query", name);
		} catch (Exception ex) {
			logger.info("General Exception @WalletControlDaoImpl.lookUpWalletControl() due to" + ex.getMessage());
			throw new DataServiceException("General Exception @WalletControlDaoImpl.lookUpWalletControl() due to " + ex.getMessage(), ex);
		}
		return result;
	}
	
	@Override
	public DocumentVO getDocument(Integer documentID) throws DataServiceException {
		try {
			 logger.info("Start getDocument() of WalletControlDaoImpl");
			return (DocumentVO) getSqlMapClient().queryForObject("document.query_document_by_document_id", documentID);
		} catch (Exception e) {
			logger.info("General Exception @WalletControlDaoImpl.getDocument() due to" + e.getMessage());
			throw new DataServiceException(
					"General Exception @WalletControlDaoImpl.getDocument() due to " + e.getMessage(), e);
		}
	}
	
	@Override
	public Integer deleteDocument(Integer documentID)throws DataServiceException{
		try{
			 logger.info("Start deleteDocument() of WalletControlDaoImpl");
			if(getSqlMapClient().delete("document.delete_entity_wallet_control_document_by_documentId",documentID)>0){
				logger.info("Now delete wallet control mapping of WalletControlDaoImpl");
				return getSqlMapClient().delete("document.delete_document_by_documentId",documentID);
			}else{
				return 0;
			}
		}catch(Exception e){
			logger.info("General Exception @WalletControlDaoImpl.deleteDocument() due to" + e.getMessage());
			throw new DataServiceException(
					"General Exception @WalletControlDaoImpl.deleteDocument() due to " + e.getMessage(), e);
		}
	}
	
	@Override
	public Integer lookUpDocumentCategory(String type) throws DataServiceException {
		Integer result=null;
		
		try {
			result = (Integer)getSqlMapClient().queryForObject("lookup.documentCategory.query", type);
		} catch (Exception ex) {
			logger.info("General Exception @WalletControlDaoImpl.lookUpDocumentCategory() due to" + ex.getMessage());
			throw new DataServiceException("General Exception @WalletControlDaoImpl.lookUpDocumentCategory() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	
	@Override
	public WalletControlVO fetchWalletControl(Integer entityID) throws DataServiceException {
		logger.info("Start fetchWalletControl() of WalletControlDaoImpl");
		WalletControlVO walletControlVO = null;
		List<WalletControlDocumentVO> walletControlDocumentVOs = new ArrayList<>();
		try {
			walletControlVO = (WalletControlVO) getSqlMapClient()
					.queryForObject("walletControl.selectForEntityID.query", entityID);
			if (walletControlVO != null && walletControlVO.getOnHold() == true) {
				Integer entityWalletControlId = walletControlVO.getId();
				logger.info("fetchWalletControlDocument of entityWalletControlId: " + entityWalletControlId);
				walletControlDocumentVOs = (List<WalletControlDocumentVO>) getSqlMapClient().queryForList(
						"walletControlDocument.selectForEntityWalletControlID.query", entityWalletControlId);
				walletControlVO.setWalletControlDocumentVO(walletControlDocumentVOs);
			} else {
				logger.info("No hold for given entity");
				walletControlVO = null;
			}
		} catch (Exception e) {
			logger.error("WalletControlDaoImpl.getWalletControlDetail" + StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}

		return walletControlVO;
	}
	
	//code change for SLT-2323
	public LookupWalletControl getHoldReleaseTemplateIds(Integer walletControlId)  throws DataServiceException {
		LookupWalletControl result=new LookupWalletControl();
		Map<String, Integer> parameter = new HashMap<String, Integer>();
		parameter.put("walletControlId", walletControlId);
		try {
			result = (LookupWalletControl)getSqlMapClient().queryForObject("template.getHoldReleaseTemplateIds.query", parameter);
		} catch (Exception ex) {
			logger.info("General Exception @WalletControlDaoImpl.getHoldReleaseTemplateIds() due to" + ex.getMessage());
			throw new DataServiceException("General Exception @WalletControlDaoImpl.getHoldReleaseTemplateIds() due to " + ex.getMessage(), ex);
		}
		return result;
	}
	
}
