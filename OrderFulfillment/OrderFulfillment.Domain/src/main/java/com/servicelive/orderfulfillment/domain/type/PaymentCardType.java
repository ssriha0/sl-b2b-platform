/**
 * 
 */
package com.servicelive.orderfulfillment.domain.type;

/**
 * @author sahmed
 * 
 */
public enum PaymentCardType {
	SEARS_CHARGE(0),
	SEARS_COMMERCIAL(3),
	SEARS_MASTERCARD(4),
	VISA(6),
	MASTERCARD(7),
	AMERICAN_EXPRESS(8),
	DISCOVER(5);
	
	private int id;

	private PaymentCardType(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static PaymentCardType fromId(int id) {
		switch (id) {
			case 0: return SEARS_CHARGE;
			case 3: return SEARS_COMMERCIAL;
			case 4: return SEARS_MASTERCARD;
			case 5: return DISCOVER;
			case 6: return VISA;
			case 7: return MASTERCARD;
			case 8: return AMERICAN_EXPRESS;
			default: 
				throw new IllegalArgumentException(String.format(
						"Invalid id specified for %s!", PaymentCardType.class
								.getName()));
		}

	}

}
