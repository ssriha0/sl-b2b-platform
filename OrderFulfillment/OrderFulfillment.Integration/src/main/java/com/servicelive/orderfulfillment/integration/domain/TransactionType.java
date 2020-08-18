package com.servicelive.orderfulfillment.integration.domain;

/**
 * User: Mustafa Motiwala
 * Date: Apr 9, 2010
 * Time: 4:20:41 PM
 */
public enum TransactionType {
	NEW(1),
	UPDATE(2),
	CANCEL(3),
	ACKNOWLEDGEMENT(4),
	INFO(5),
	BUYER_NOTIFICATION(6),
	CLOSE_ACKNOWLEDGEMENT(7),
	ACKNOWLEDGE_UPDATE(8),
	AUDIT(9);
	
	private final int id;
	private TransactionType(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public static TransactionType fromId(int id) {
		switch(id) {
			case 1: return NEW;
			case 2: return UPDATE;
			case 3: return CANCEL;
			case 4: return ACKNOWLEDGEMENT;
			case 5: return INFO;
			case 6: return BUYER_NOTIFICATION;
			case 7: return CLOSE_ACKNOWLEDGEMENT;
			case 8: return ACKNOWLEDGE_UPDATE;
			case 9: return AUDIT;
			default: throw new IllegalArgumentException(String.format("Invalid ID specified for %s", TransactionType.class.getName()));
		}
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}
