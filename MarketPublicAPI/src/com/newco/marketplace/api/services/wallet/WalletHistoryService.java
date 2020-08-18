package com.newco.marketplace.api.services.wallet;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
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
import com.newco.marketplace.utils.DateUtils;

/**
 * @author seshu
 * Modified by seshu
 *
 */
public class WalletHistoryService  extends BaseService {
	private IFinanceManagerBO financeManagerBO;
	private WalletHistoryMapper walletHistoryMapper;
	private XStreamUtility conversionUtility;
	private Logger logger = Logger.getLogger(WalletHistoryService.class);
	private String DEFAULT_DATE_FORMAT = "MMddyyyy";
	
 	public WalletHistoryService () {
		super (null,
				PublicAPIConstant.Wallet.History.WALLET_HISTORY_RESPONSE_XSD, 
				PublicAPIConstant.Wallet.History.WALLET_HISTORY_RESPONSE_NAMESPACE, 
				PublicAPIConstant.Wallet.WALLET_RESOURCES_SCHEMAS,
				PublicAPIConstant.Wallet.History.WALLET_HISTORY_RESPONSE_SCHEMALOCATION,	
				null,
				WalletHistoryResponse.class);	
		super.addMoreClass(WalletHistoryResults.class);
		super.addMoreClass(TransactionDetail.class);
		//super.addRequiredGetParam(PublicAPIConstant.Wallet.History.START_DATE, DataTypes.DATE);
		//super.addRequiredGetParam(PublicAPIConstant.Wallet.History.END_DATE, DataTypes.DATE);
	}
	
	/**
	 * This method returns the walletHistoryResponse
	 * @param APIRequestVO
	 * @return walletHistoryResponse
	 */
 	
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering WalletHistoryService.execute()");
		Map<String,String> requestMap = apiVO.getRequestFromGetDelete();
		Integer buyerId   = apiVO.getBuyerIdInteger();
		String startDate = (String)requestMap.get(PublicAPIConstant.Wallet.History.START_DATE); 
		String  endDate  = (String)requestMap.get(PublicAPIConstant.Wallet.History.END_DATE);
		 
		if (startDate==null && endDate==null){
			endDate   = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),DEFAULT_DATE_FORMAT);//   todaysDate();
			startDate = getCalculatedHistoryDate(endDate);
		} else if(startDate!=null && endDate==null){
			endDate   = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),DEFAULT_DATE_FORMAT);			
		} else if(startDate==null && endDate!=null){
			startDate = getCalculatedHistoryDate(endDate);
		}

		//try{
			/*
			SecurityContext securityContext = super.getSecurityContext(new Integer(requestMap.get("buyerid")));
			if (buyerId==null) {			
				Results results= Results.getError("Missing buyerId", ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
				return (new WalletHistoryResponse(results));
			}else if(securityContext==null){
				Results results= Results.getError("Invalid buyerId", ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
				return (new WalletHistoryResponse(results));
			}else */ 
				
			if (!validateRequestParameters(startDate,endDate)){
				return InvalidDateArgumentsResponse();
			}	
			
		//}catch(NumberFormatException nfe){
		//	Results results= Results.getError("Invalid buyerId", ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
		//	return (new WalletBalanceResponse(results));
		//}
		
		AccountHistoryVO accountHistoryVO = new AccountHistoryVO();
		if (startDate != null) { 
			accountHistoryVO.setFromDate(new Date(getDateFromString(startDate).getTime()));
		}
		
		if (endDate != null) {
			accountHistoryVO.setToDate(new Date(getDateFromString(endDate).getTime()));
		}
		
		WalletHistoryResponse walletHistoryResponse = null;
		
		accountHistoryVO.setEntityId(buyerId);
		accountHistoryVO.setEntityType(PublicAPIConstant.Wallet.BUYER);
		accountHistoryVO.setEntityTypeId(OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER);
		accountHistoryVO.setReturnCountLimit(OrderConstants.ACCOUNT_HISTORY_LIMIT_COUNT);
				
		try {
			List<AccountHistoryVO> walletHistoryResponseList = financeManagerBO.getAccountOverviewHistory(accountHistoryVO);
			walletHistoryResponse = walletHistoryMapper.mapWalletHistoryResponse(walletHistoryResponseList);
			HashMap einMap = new HashMap<String, Object>();
			einMap=financeManagerBO.getBuyerTotalDeposit(buyerId);
			if(einMap.get("buyerTotalDeposit")!=null){
				walletHistoryResponse.setTotalDeposit((Double)einMap.get("buyerTotalDeposit"));
			}
		} catch(OutOfMemoryError ome){
			logger.error("WalletHistoryService-->execute()-->Exception-->" + ome.getMessage(), ome);
			Results results = Results.getError(ome.getMessage(), ResultsCode.TOO_MANY_WALLET_HISTORY_RECORDS.getCode());
			walletHistoryResponse = new WalletHistoryResponse();
			walletHistoryResponse.setResults(results);
		} catch (BusinessServiceException ex){
			logger.error("WalletHistoryService-->execute()-->Exception-->" + ex.getMessage(), ex);
			Results results = Results.getError(ex.getMessage(), ResultsCode.GENERIC_ERROR.getCode());
			walletHistoryResponse = new WalletHistoryResponse();
			walletHistoryResponse.setResults(results);
		}		
		
		logger.info("Exiting dispatchWalletHistory.execute()");
		return walletHistoryResponse;
	}

	/**
	 * This method is for validating input parameters startdate, enddate to make sure startdate is not greater than enddate
	 * 
	 * @param 
	 * @return WalletHistoryResponse
	 */
	private WalletHistoryResponse InvalidDateArgumentsResponse(){
		String resultMessage = CommonUtility.getMessage(PublicAPIConstant.Wallet.History.WALLET_HISTORY_DATES_ERROR_CODE);
		Results results= Results.getError(resultMessage, ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
		return (new WalletHistoryResponse(results));
	}
	
	/**
	 * This method is for validating input parameters startdate, enddate
	 * 
	 * @param startDate String
	 * @param endDate String
	 * @return boolean
	 */
	private boolean validateRequestParameters(String startDate,String endDate){
		if(startDate!=null && endDate!=null){ 
			if(getDateFromString(startDate).compareTo(getDateFromString(endDate))>0){
				return false; // if startDate > endDate
			}else{
				return true;  // if startDate <= endDate
			}			
		}else{ 
			return true;
		}
	}

	/**
	 * This method is to get Date from String
	 * 
	 * @param dateStr String
	 * @return java.util.Date
	 */
	private java.util.Date getDateFromString(String dateStr){
		return DateUtils.getDateFromString(dateStr, DEFAULT_DATE_FORMAT);
	}

	/**
	 * This method is to get String from Date
	 * 
	 * @param java.util.Date
	 * @return dateStr String
	 */
	private String getStringFromDate(java.util.Date date){
		return DateUtils.getFormatedDate(date, DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * This method is to get the date for a given date with year/month/days backwards
	 * 
	 * @param dateStr String
	 * @return String
	 */
	private String getCalculatedHistoryDate(String givenDate){
		return getStringFromDate(DateUtils.calcDateBasedOnDateNInterval(getDateFromString(givenDate),
																		(new Integer(PublicAPIConstant.Wallet.History.HISTORY_PERIOD_YEAR)),
																		PublicAPIConstant.Wallet.History.HISTORY_PERIOD_YEAR_STRING));
	}
	
	/**
	 * This method is to get IFinanceManagerBO
	 * 
	 * @param 
	 * @return IFinanceManagerBO
	 */	
	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	/**
	 * This method is to set IFinanceManagerBO
	 * 
	 * @param financeManagerBO IFinanceManagerBO
	 * @return 
	 */
	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	/**
	 * This method is to get WalletHistoryMapper
	 * 
	 * @param 
	 * @return WalletHistoryMapper
	 */
	public WalletHistoryMapper getWalletHistoryMapper() {
		return walletHistoryMapper;
	}

	/**
	 * This method is to set WalletHistoryMapper
	 * 
	 * @param walletHistoryMapper WalletHistoryMapper
	 * @return 
	 */
	public void setWalletHistoryMapper(WalletHistoryMapper walletHistoryMapper) {
		this.walletHistoryMapper = walletHistoryMapper;
	}

	/**
	 * This method is to get XStreamUtility
	 * 
	 * @param 
	 * @return XStreamUtility
	 */
	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	/**
	 * This method is to set XStreamUtility
	 * 
	 * @param conversionUtility XStreamUtility
	 * @return  
	 */
	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}
}