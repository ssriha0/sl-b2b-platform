package com.newco.marketplace.business.iBusiness.financeManager;


import java.util.List;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.wallet.WalletControlVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.exception.SLBusinessServiceException;


public interface IWalletControlBO {

	public ProcessResponse insertWalletControlDocument(DocumentVO documentVO, WalletControlVO walletControlVO)  throws BusinessServiceException;
	
	public WalletControlVO walletControlInsertOrUpdate(WalletControlVO walletControlVO, List<DocumentVO> documentVOs) throws BusinessServiceException,DBException;
	public Integer lookUpWalletControl(String name) throws BusinessServiceException,DBException;
	DocumentVO downloadWalletControlDocument(Integer documentID) throws SLBusinessServiceException;

	Integer deleteWalletControlDocument(Integer documentID) throws SLBusinessServiceException;

	WalletControlVO fetchWalletControl(Integer entityID) throws SLBusinessServiceException;
	
}
