package com.servicelive.orderfulfillment.domain.type;

import java.util.HashMap;
import java.util.Map;

public enum PaymentType {
	CHECK("CA"),
	DISCOVER_CARD("DD"),
	SEARS_COMMERCIAL_CARD("CM"),
	SEARS_CHARGE_CARD("SS"),
	SEARS_CHARGE_PLUS_CARD("SP"),
	VISA("VI"),
	MASTERCARD("MC"),
	AMERICAN_EXPRESS("AX");

	private static Map<String, PaymentType> shortNameMap = new HashMap<String, PaymentType>();
	static {
		for (PaymentType paymentType : PaymentType.values()) {
			shortNameMap.put(paymentType.shortName, paymentType);
		}
	}
	
	private String shortName;
	private PaymentType(String shortName) {
		this.shortName = shortName;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	public static PaymentType fromShortName(String shortName) {
		if (shortNameMap.containsKey(shortName)) return shortNameMap.get(shortName);
		throw new IllegalArgumentException(String.format(
				"Invalid shortName specified for %s!", PaymentCardType.class
						.getName()));
	}
}
