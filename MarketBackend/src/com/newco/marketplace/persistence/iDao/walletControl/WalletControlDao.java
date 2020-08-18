package com.newco.marketplace.persistence.iDao.walletControl;


import com.newco.marketplace.dto.vo.wallet.WalletControlVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.wallet.LookupWalletControl;
import com.newco.marketplace.dto.vo.wallet.WalletControlDocumentVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface WalletControlDao {
	
	public WalletControlVO walletControlInsertOrUpdate(WalletControlVO walletControlVO)  throws DataServiceException;	
	public WalletControlDocumentVO walletControlDocumentInsertOrUpdate(WalletControlDocumentVO walletControlDocumentVO)   throws DataServiceException;	
	
	public Integer lookUpWalletControl(String name) throws DataServiceException ;
	public DocumentVO getDocument(Integer documentID) throws DataServiceException;
	Integer deleteDocument(Integer documentID) throws DataServiceException;
	public Integer lookUpDocumentCategory(String type) throws DataServiceException ;
	WalletControlVO fetchWalletControl(Integer entityID) throws DataServiceException;

	//code change foe SLT-2323
	
	public LookupWalletControl getHoldReleaseTemplateIds(Integer walletControlId)  throws DataServiceException ;
}
