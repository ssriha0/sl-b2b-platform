package com.newco.marketplace.api.common;

public enum CreditCardType {
	VISA(6),
	MASTER_CARD(7),
	//AMEX(8),
	SEARS_MASTER_CARD(4),
	INVALID(0);

	private int id;
	private CreditCardType(int id) {
		this.id = id;
	}

	public static CreditCardType getCardType(Long id) {
		if (id == null)
			return INVALID;
		
		switch (id.intValue()) {
		case 4:
			return SEARS_MASTER_CARD;
		case 6:
			return VISA;
		case 7:
			return MASTER_CARD;
		/*case 8:
			return AMEX;*/
		default:
			return INVALID;
		}
	}

}
