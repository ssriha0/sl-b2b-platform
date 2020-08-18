package com.newco.marketplace.dto.vo.ledger;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

public class LedgerBusinessTransactionVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3011009624452263533L;
	private Integer businessTransId;
	private String businessTransType;
	private String businessTransDesc;
	private ArrayList<TransactionVO> _transactions = new ArrayList<TransactionVO>();
	private boolean hasManyTransactions = false;
	private boolean containsTransaction = false;
	private String entityId;
	private Integer fundingTypeId;
	private Integer autoAchInd;
	
	public ArrayList<TransactionVO> get_transactions() {
		return _transactions;
	}
	
	public void add_transactions(final ArrayList<TransactionVO> _transactions) 
	{
		this._transactions.addAll(_transactions);
		 setContainsTransaction(true);
		 if(_transactions.size() > 1)
		 {
			 setHasManyTransactions(true);
		 }
	}
	
	public void add_transaction( final TransactionVO theTransaction ) {
	 this._transactions.add( theTransaction); 
	 setContainsTransaction(true);
	}
	
	public boolean isHasManyTransactions() {
		return hasManyTransactions;
	}
	
	public void setHasManyTransactions(boolean hasManyTransactions) {
		this.hasManyTransactions = hasManyTransactions;
	}

	public boolean isContainsTransaction() {
		return containsTransaction;
	}

	public void setContainsTransaction(boolean containsTransaction) {
		this.containsTransaction = containsTransaction;
	}

	public String getBusinessTransDesc() {
		return businessTransDesc;
	}

	public void setBusinessTransDesc(String businessTransDesc) {
		this.businessTransDesc = businessTransDesc;
	}

	public Integer getBusinessTransId() {
		return businessTransId;
	}

	public void setBusinessTransId(Integer businessTransId) {
		this.businessTransId = businessTransId;
	}

	public String getBusinessTransType() {
		return businessTransType;
	}

	public void setBusinessTransType(String businessTransType) {
		this.businessTransType = businessTransType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Integer getFundingTypeId() {
		return fundingTypeId;
	}

	public void setFundingTypeId(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	public Integer getAutoAchInd() {
		return autoAchInd;
	}

	public void setAutoAchInd(Integer autoAchInd) {
		this.autoAchInd = autoAchInd;
	}



	

}
