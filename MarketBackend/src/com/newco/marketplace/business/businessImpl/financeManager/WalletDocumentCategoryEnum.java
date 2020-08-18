package com.newco.marketplace.business.businessImpl.financeManager;

public enum WalletDocumentCategoryEnum {

	WALLET_CONTROL("WalletControl"), RELEASE_WALLET_CONTROL("ReleaseWalletControl");

	private String name;

	WalletDocumentCategoryEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
}
