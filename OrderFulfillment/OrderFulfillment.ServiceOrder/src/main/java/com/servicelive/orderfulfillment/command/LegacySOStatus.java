package com.servicelive.orderfulfillment.command;

public enum LegacySOStatus {
	START (-1),
	DRAFT (100),
	DELETED (105),
	POSTED (110),
	CANCELLED (120),
	VOIDED (125),
	EXPIRED(130),
	CONDITIONAL_OFFER (140),
	ACCEPTED (150),
	ACTIVE (155),
	COMPLETED (160),
	PROBLEM (170),
	CLOSED (180),
	INACTIVE_GROUP (190),
	PENDINGCANCEL (165)
//	TODAY (9000),
//	INACTIVE (9500),
//	SEARCH (9600),
	;
	
	private final int statusId;
	
	private LegacySOStatus(int aStatusId) {
		statusId = aStatusId;
	}
	
	public final int getId() {
		return statusId;
	}
}
