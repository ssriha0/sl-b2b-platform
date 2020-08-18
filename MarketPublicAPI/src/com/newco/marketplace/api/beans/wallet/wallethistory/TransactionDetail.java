package com.newco.marketplace.api.beans.wallet.wallethistory;
import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * This is a generic bean class for storing wallet history information.
 * @author Infosys
 *
 */
@XStreamAlias("transactionDetail")
public class TransactionDetail{

	@OptionalParam
	@XStreamAlias("transactionId")
	private long transactionId;

	
	@OptionalParam
	@XStreamAlias("entryDate")
	private String entryDate;

	
	@OptionalParam
	@XStreamAlias("status")
	private String status;

	
	@OptionalParam
	@XStreamAlias("description")
	private String description;

	
	@OptionalParam
	@XStreamAlias("type")
	private String type;

	
	@OptionalParam
	@XStreamAlias("amount")
	private double amount;

	
	@OptionalParam
	@XStreamAlias("balance")
	private double balance;

	@OptionalParam
	@XStreamAlias("soId")
	private String soId;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}
}
