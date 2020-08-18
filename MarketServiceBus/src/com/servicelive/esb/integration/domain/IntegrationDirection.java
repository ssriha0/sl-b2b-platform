package com.servicelive.esb.integration.domain;

public enum IntegrationDirection {
	INCOMING,
	OUTGOING;
	
	@Override
	public String toString() {
		return this.name();
	}
}
