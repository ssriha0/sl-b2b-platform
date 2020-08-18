package com.newco.marketplace.business.businessImpl.financeManager;

import java.util.Arrays;
import java.util.List;

public enum WalletControlEnum {

	IRS_LEVY("irsLevy"), LEGAL_HOLD("legalHold");

	private String name;

	WalletControlEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static boolean isValidEnum(String name) {
		List<WalletControlEnum> enums = Arrays.asList(values());
		for (WalletControlEnum enumName : enums) {
			if (enumName.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getEnumName(String name){
	
		for (WalletControlEnum walletControlEnum : WalletControlEnum.values()) {
			if (walletControlEnum.getName().equals(name))
					return walletControlEnum.name();
		}
		return null;
	}
}