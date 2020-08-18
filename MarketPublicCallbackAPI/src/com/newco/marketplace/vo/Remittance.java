package com.newco.marketplace.vo;

public class Remittance {
	String transferToStore;
	Integer subAccount;

	public Integer getSubAccount() {
		return subAccount;
	}

	public void setSubAccount(Integer subAccount) {
		this.subAccount = subAccount;
	}

	public String getTransferToStore() {
		return transferToStore;
	}

	public void setTransferToStore(String transferToStore) {
		this.transferToStore = transferToStore;
	}
}
