package com.newco.marketplace.web.delegates;

import java.io.Serializable;
import java.util.List;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.wallet.WalletControlVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.exception.SLBusinessServiceException;


public interface ISLAdminWalletControlDeligate extends Serializable {
	
	/**
	 * @param documentVO
	 * @return
	 * @throws BusinessServiceException 
	 */
	public ProcessResponse uploadWalletControlDocument(DocumentVO documentVO,WalletControlVO walletControlVO)  throws DelegateException;
	
	public WalletControlVO saveWalletControlDetail(WalletControlVO WalletControlVO, List<DocumentVO> documentVOs)  throws DelegateException;
	public Integer lookUpWalletControl(String name)throws DelegateException;

	DocumentVO downloadWalletControlDocument(Integer documentID) throws SLBusinessServiceException;

	Integer deleteWalletControlDocument(Integer documentID) throws SLBusinessServiceException;

	WalletControlVO fetchWalletControl(Integer entityID) throws DelegateException;
	
	

}
