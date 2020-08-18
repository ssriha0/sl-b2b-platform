package com.newco.marketplace.api.utils.constants;
/**
 * 
 * @author Shekhar Nirkhe
 * Modified by Nilesh Dixit
 * This is used to get/set the account type enum values for different funding source.These values will be send over to API clients.
 *
 */

public enum AccountType {
	Bank("Bank"),
	CreditCard("CreditCard"),
	None("None"),
	Both("Both");

	private String value;
	private AccountType(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

	public static AccountType getObject(String value) {
		if (value.equalsIgnoreCase(Bank.toString())){
			return Bank;
		}else if(value.equalsIgnoreCase(CreditCard.toString())){
			return CreditCard;
		}else if (value.equalsIgnoreCase(None.toString())){
			return None;
		}else{
			return Both;
		}
	}
}