package com.servicelive.esb.integration.domain;

public enum IntegrationName {
	RI_INBOUND(1L),
	RI_OUTBOUND(2L),
	ASSURANT_INBOUND(3L),
	ASSURANT_OUTBOUND(4L),
	HSR_INBOUND_NEW_REQUESTS(5L),
	HSR_INBOUND_UPDATE_REQUESTS(6L),
	FILE_UPLOAD(7L),
	HSR_OUTBOUND(8L),
	NPS_CLOSE_AUDIT(9L),
	RI_CANCEL_OUTBOUND(10L),
	NPS_CANCEL_AUDIT(11L);

	private final Long id;
	private IntegrationName(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}
	
	public static IntegrationName fromId(Long id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format("Cannot create a '%s' with a null id", IntegrationName.class.getName()));
		}
		
		switch (id.intValue()) {
			case 1: return RI_INBOUND;
			case 2: return RI_OUTBOUND;
			case 3: return ASSURANT_INBOUND;
			case 4: return ASSURANT_OUTBOUND;
			case 5: return HSR_INBOUND_NEW_REQUESTS;
			case 6: return HSR_INBOUND_UPDATE_REQUESTS;
			case 7: return FILE_UPLOAD;
			case 8: return HSR_OUTBOUND;
			case 10: return RI_CANCEL_OUTBOUND;
			case 11: return NPS_CANCEL_AUDIT;
			default: throw new IllegalArgumentException(String.format("Invalid id (%d) specified for %. ", id, IntegrationName.class.getName()));
		}
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}
