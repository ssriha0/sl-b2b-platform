package com.newco.marketplace.api.beans.fundingsources;

import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author ndixit
 * POJO for bank accounts: Used in funding source APIS.
 */
@XStreamAlias("bankAccounts")
public class BankAccounts {
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="bankAccount")
	private List<BankAccount> bankAccount;

	public List<BankAccount> getBankAccountsList() {
		return bankAccount;
	}

	public void setBankAccountsList(List<BankAccount> bankAccount) {
		this.bankAccount = bankAccount;
	}
	
}
