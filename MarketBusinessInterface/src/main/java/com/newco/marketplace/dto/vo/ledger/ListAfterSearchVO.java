package com.newco.marketplace.dto.vo.ledger;

import com.sears.os.vo.SerializableBaseVO;

public class ListAfterSearchVO extends SerializableBaseVO
	{
		private static final long serialVersionUID = 5169985142281155774L;
		private Integer transactionNumber;
		private String date;
		private String time;
		private String type;
		private String soId;
		private String amount;
		private String balanceDesc;
		private boolean amountRed;
		private boolean balanceRed;
		private String status;
		private String balance;
		private String modifiedDate;
		
		
		
		public String getModifiedDate() {
			return modifiedDate;
		}
		public void setModifiedDate(String modifiedDate) {
			this.modifiedDate = modifiedDate;
		}
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public boolean isAmountRed() {
			return amountRed;
		}
		public void setAmountRed(boolean amountRed) {
			this.amountRed = amountRed;
		}
		public String getBalanceDesc() {
			return balanceDesc;
		}
		public void setBalanceDesc(String balanceDesc) {
			this.balanceDesc = balanceDesc;
		}
		public boolean isBalanceRed() {
			return balanceRed;
		}
		public void setBalanceRed(boolean balanceRed) {
			this.balanceRed = balanceRed;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getSoId() {
			return soId;
		}
		public void setSoId(String soId) {
			this.soId = soId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public Integer getTransactionNumber() {
			return transactionNumber;
		}
		public void setTransactionNumber(Integer transactionNumber) {
			this.transactionNumber = transactionNumber;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	
	}