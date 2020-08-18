package com.newco.marketplace.interfaces;

public enum CallbackEvent {
	CLOSE_ORDER ("CLOSE_ORDER"),
	ACCEPT_RESCHEDULE ("accept-reschedule");
	
	private final String value;
	
	
	private CallbackEvent(String value) {
		 this.value = value;
	}
	
	public String getValue() {
        return value;
    }
}
