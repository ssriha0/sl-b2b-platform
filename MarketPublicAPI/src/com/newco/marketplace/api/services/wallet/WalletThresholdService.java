package com.newco.marketplace.api.services.wallet;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.wallet.BuyerWalletThresholdResponse;
import com.newco.marketplace.api.beans.wallet.wallethistory.TransactionDetail;
import com.newco.marketplace.api.beans.wallet.wallethistory.WalletHistoryResponse;
import com.newco.marketplace.api.beans.wallet.wallethistory.WalletHistoryResults;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.wallet.WalletHistoryMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.piiThreshold.IPIIThresholdDao;
import com.newco.marketplace.utils.DateUtils;

/**
 * @author seshu
 * Modified by seshu
 *
 */
public class WalletThresholdService  extends BaseService {
	private IPIIThresholdDao piiThresholdDao;
	private Logger logger = Logger.getLogger(WalletThresholdService.class);
	
	
 	public WalletThresholdService () {
		super (null,
				PublicAPIConstant.Wallet.BuyerWalletThreshold.RESPONSE_XSD, 
				PublicAPIConstant.Wallet.BuyerWalletThreshold.NAMESPACE, 
				PublicAPIConstant.Wallet.WALLET_RESOURCES_SCHEMAS,
				PublicAPIConstant.Wallet.BuyerWalletThreshold.SCHEMALOCATION,	
				null,
				BuyerWalletThresholdResponse.class);
	}
	
	/**
	 * This method returns the walletHistoryResponse
	 * @param APIRequestVO
	 * @return walletHistoryResponse
	 */
 	
	public IAPIResponse execute(APIRequestVO apiVO) {
		BuyerWalletThresholdResponse response= null;
		try{
		com.newco.marketplace.dto.vo.PIIThresholdVO thresholdVO = piiThresholdDao.getThreshold("Buyer");
		Results results = Results.getSuccess();
		response = new BuyerWalletThresholdResponse(results, thresholdVO.getThresholdIndex(),thresholdVO.getThresholdValue());
		}catch(Exception ex){
			Results results = Results.getError(ex.getMessage(), ResultsCode.GENERIC_ERROR.getCode());
			response = new BuyerWalletThresholdResponse();
			response.setResults(results);
		}
		return response;	
	}

	public IPIIThresholdDao getPiiThresholdDao() {
		return piiThresholdDao;
	}

	public void setPiiThresholdDao(IPIIThresholdDao piiThresholdDao) {
		this.piiThresholdDao = piiThresholdDao;
	}
	
}