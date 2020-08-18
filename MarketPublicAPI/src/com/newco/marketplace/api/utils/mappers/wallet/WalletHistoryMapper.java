package com.newco.marketplace.api.utils.mappers.wallet;

import java.util.ArrayList;
import com.newco.marketplace.utils.DateUtils;
import java.util.List;
import org.apache.log4j.Logger;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryVO;
import com.newco.marketplace.api.beans.wallet.wallethistory.TransactionDetail;
import com.newco.marketplace.api.beans.wallet.wallethistory.WalletHistoryResponse;
import com.newco.marketplace.api.beans.wallet.wallethistory.WalletHistoryResults;
import com.newco.marketplace.interfaces.OrderConstants;

public class WalletHistoryMapper {
	private Logger logger = Logger.getLogger(WalletHistoryMapper.class);
	private String DEFAULT_DATE_FORMAT = "MMddyyyy";
	/**
	 * This method is for mapping AccountHistoryVOList Object to WalletHistoryResponse Object.
	 *
	 * @param AccountHistoryVO List
	 * @return WalletHistoryResponse
	 */
	public WalletHistoryResponse mapWalletHistoryResponse(List<AccountHistoryVO> accountHistoryVOList)  {
		if(logger.isInfoEnabled()) logger.info("Inside WalletHistoryMapper.mapWalletHistoryResponse()");
		WalletHistoryResponse walletHistoryResponse = new WalletHistoryResponse();

		// Mapping Transaction details
		boolean hasRecords = false;
		int historyTotal=0, recordCount=0;

		if((null!=accountHistoryVOList) && (!accountHistoryVOList.isEmpty())){
			historyTotal = accountHistoryVOList.size();
			WalletHistoryResults walletHistoryResults = new WalletHistoryResults();
			List<TransactionDetail> transactionDetailList = new ArrayList<TransactionDetail>();

			hasRecords = true;
			for (AccountHistoryVO accountHistoryVO : accountHistoryVOList) {
				recordCount++;
				if(recordCount > PublicAPIConstant.Wallet.History.MAX_HISTORY_RESULT_SET){ // Intend to show only 1000 records irrespective of history search total
					break;
				}
				TransactionDetail transactionDetail = new TransactionDetail();
				transactionDetail.setTransactionId(accountHistoryVO.getTransactionId());
				transactionDetail.setStatus(getStatus(accountHistoryVO));
				transactionDetail.setEntryDate(DateUtils.getFormatedDate(accountHistoryVO.getEntryDate(),DEFAULT_DATE_FORMAT));
				transactionDetail.setDescription(accountHistoryVO.getTransactionalType());
				transactionDetail.setAmount(accountHistoryVO.getTransAmount());
				transactionDetail.setSoId(accountHistoryVO.getSoId());

				String type=null;
				if(null!=accountHistoryVO.getCredDebInd()){
					int credDebInd=accountHistoryVO.getCredDebInd();
					if(credDebInd==PublicAPIConstant.INTEGER_ONE){
						type=PublicAPIConstant.DEBIT;
					}
					else if(credDebInd==PublicAPIConstant.TWO){
						type=PublicAPIConstant.CREDIT;
					}
				}
				transactionDetail.setType(type);
				if(accountHistoryVO.getAvailableBalance()!=null)
					transactionDetail.setBalance(Double.parseDouble(accountHistoryVO.getAvailableBalance()));
				transactionDetailList.add(transactionDetail);
			}
			walletHistoryResults.setHistoryTotal(historyTotal);
			walletHistoryResults.setRecordsDisplayed(transactionDetailList.size());
			walletHistoryResults.setTransactionDetailList(transactionDetailList);
			walletHistoryResponse.setWalletHistoryResults(walletHistoryResults);
			recordCount = transactionDetailList.size();
		}


		String resultMessage = null;

		if(!hasRecords){
			resultMessage = CommonUtility.getMessage(PublicAPIConstant.Wallet.History.WALLET_HISTORY_RESULT_CODE);
		}else{
			resultMessage = "Total records found: "+historyTotal+" and returned: "+recordCount;//PublicAPIConstant.SUCCESS;
		}

		Results results = Results.getSuccess(resultMessage);
		results.setError(Results.getError("",PublicAPIConstant.ZERO).getError());
		walletHistoryResponse.setResults(results);

		return walletHistoryResponse;
	}

	/**
	 * This method is to find transaction status
	 *
	 * @param acctHistoryView AccountHistoryVO
	 * @return String
	 */
	private String getStatus(AccountHistoryVO acctHistoryView){
		if (acctHistoryView.getAchProcessId() != null){
			if (acctHistoryView.getAchRejectId() != null && acctHistoryView.getAchRejectId() > 0 &&
					acctHistoryView.getRejectReasonCode()!= null && acctHistoryView.getRejectReasonCode().startsWith("R", 0)){
				return OrderConstants.TRANSACTION_STATUS_FAILED;
			}else if (acctHistoryView.getLedgerReconciledInd() == 1 ){
				return OrderConstants.TRANSACTION_STATUS_COMPLETED;
			}else{
				return OrderConstants.TRANSACTION_STATUS_PENDING;
			}
		}else{
			if (acctHistoryView.getLedgerReconciledInd() == 1 ){
				return OrderConstants.TRANSACTION_STATUS_COMPLETED;
			}else{
				return OrderConstants.TRANSACTION_STATUS_PENDING;
			}
		}
	}
}
