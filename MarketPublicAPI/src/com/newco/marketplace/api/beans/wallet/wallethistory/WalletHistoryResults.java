
package com.newco.marketplace.api.beans.wallet.wallethistory;
import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.wallet.wallethistory.TransactionDetail;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing wallet history results information for 
 * the WalletHistoryService
 * @author Infosys
 *
 */
@XStreamAlias("walletHistoryResults")
public class WalletHistoryResults {
	@OptionalParam
	@XStreamAlias("recordCount")
	private int recordCount;
	
	@OptionalParam
	@XStreamAlias("recordsDisplayed")
	private int recordsDisplayed;

	@OptionalParam
	@XStreamImplicit(itemFieldName="transactionDetail")
	private  List<TransactionDetail> transactionDetailList;

	public int getRecordCount(){
		return recordCount;
	}
	
	public void setHistoryTotal(int recordCount){
		this.recordCount = recordCount;
	}
	
	public int getRecordsDisplayed(){
		return recordsDisplayed;
	}
	
	public void setRecordsDisplayed(int recordsDisplayed){
		this.recordsDisplayed = recordsDisplayed;
	}
	
	public List<TransactionDetail> getTransactionDetailList() {
		return transactionDetailList;
	}

	public void setTransactionDetailList(List<TransactionDetail> transactionDetailList) {
		this.transactionDetailList = transactionDetailList;
	}
}
