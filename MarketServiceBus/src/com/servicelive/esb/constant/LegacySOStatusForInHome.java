package com.servicelive.esb.constant;

public enum LegacySOStatusForInHome {
	CREATED (100),
	POSTED (110),
	ACCEPTED (150),
	ACTIVATED (155),
	PROBLEM (170);
    
	private final int statusId;
	
	private LegacySOStatusForInHome(int aStatusId) {
		statusId = aStatusId;
	}
	
	public final int getId() {
		return statusId;
	}
}
