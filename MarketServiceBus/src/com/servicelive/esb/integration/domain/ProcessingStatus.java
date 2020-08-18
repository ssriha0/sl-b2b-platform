package com.servicelive.esb.integration.domain;

/**
 * Maps to entries in the servicelive_integration.processing_status table
 * @author sahmed
 *
 */
public enum ProcessingStatus {
	SUCCESS(1),
	ERROR(2),
	CREATED(3),
	PROCESSING(4),
	SKIPPED(5),
	READY_TO_PROCESS(6),
	READY_TO_RESEND(7),
	RESENT(8);

	private final int id;
	private ProcessingStatus(Integer id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
	
	public static ProcessingStatus fromId(int id) {
		switch (id) {
			case 1: return SUCCESS;
			case 2: return ERROR;
			case 3: return CREATED;
			case 4: return PROCESSING;
			case 5: return SKIPPED;
			case 6: return READY_TO_PROCESS;
			case 7: return READY_TO_RESEND;
			case 8: return RESENT;
			default: throw new IllegalArgumentException(String.format("Invalid ID specified for %s", ProcessingStatus.class.getName()));
		}
	}

	@Override
	public String toString() {
		return this.name();
	}
}
