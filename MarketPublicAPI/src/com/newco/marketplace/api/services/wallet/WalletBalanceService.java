package com.newco.marketplace.api.services.wallet;

import java.util.Map;


import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.wallet.WalletBalanceResponse;
import com.newco.marketplace.api.beans.wallet.wallethistory.WalletHistoryResponse;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;

import com.newco.marketplace.api.common.IAPIResponse;

import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.wallet.WalletBalanceMapper;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.utils.MoneyUtil;

public class WalletBalanceService  extends BaseService {
	private IFinanceManagerBO financeManagerBO;
	private WalletBalanceMapper walletBalanceMapper;
	private XStreamUtility conversionUtility;
	
	private Logger logger = Logger.getLogger(WalletBalanceService.class);
	
	public WalletBalanceService () {
		super (null,
				PublicAPIConstant.Wallet.WalletBalance.RESPONSE_XSD, 
				PublicAPIConstant.Wallet.WalletBalance.NAMESPACE, 
				PublicAPIConstant.Wallet.WALLET_RESOURCES_SCHEMAS,
				PublicAPIConstant.Wallet.WalletBalance.SCHEMALOCATION,	
				null,
				WalletBalanceResponse.class);
	}
	
	/**
	 * This method returns the walletBalanceResponse
	 * @param APIRequestVO
	 * @return walletBalanceResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO)  
	{
		if(logger.isInfoEnabled())logger.info("Entering WalletBalanceService.execute()");
		
		Map<String,String> requestMap = apiVO.getRequestFromGetDelete();
		Integer buyerId   =  apiVO.getBuyerIdInteger();
		
		/**
		 * buyerId validation is done by base class
		 */
//		try{
//			SecurityContext securityContext = super.getSecurityContext(new Integer(requestMap.get("buyerid")));
//			if (buyerId==null) {			
//				Results results= Results.getError("Missing buyerId", ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
//				return (new WalletBalanceResponse(results));
//			}else if(securityContext==null){
//				Results results= Results.getError("Invalid buyerId", ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
//				return (new WalletBalanceResponse(results));
//			}			
//		}catch(NumberFormatException nfe){
//			Results results= Results.getError("Invalid buyerId", ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
//			return (new WalletBalanceResponse(results));
//		}

		AjaxCacheVO ajaxCacheVO = new AjaxCacheVO();
		WalletBalanceResponse walletBalanceResponse = null;
		ajaxCacheVO.setCompanyId(buyerId);
		ajaxCacheVO.setRoleType("Buyer");
		
		walletBalanceResponse = walletBalanceMapper.mapWalletBallanceResponse(
				MoneyUtil.subtract(financeManagerBO.getcurrentBalance(ajaxCacheVO),financeManagerBO.getavailableBalance(ajaxCacheVO)),
				financeManagerBO.getcurrentBalance(ajaxCacheVO),financeManagerBO.getavailableBalance(ajaxCacheVO));
		if(logger.isInfoEnabled()){ 
			logger.info("Exiting WalletBalanceService.execute()");
		}	
		return walletBalanceResponse;
	}

	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	public WalletBalanceMapper getWalletBalanceMapper() {
		return walletBalanceMapper;
	}

	public void setWalletBalanceMapper(WalletBalanceMapper walletBalanceMapper) {
		this.walletBalanceMapper = walletBalanceMapper;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}
}
