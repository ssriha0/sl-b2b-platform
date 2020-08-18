package com.servicelive.orderfulfillment.integration.domain;

/**
 * User: Mustafa Motiwala
 * Date: Apr 12, 2010
 * Time: 12:25:44 PM
 */
public enum ProcessingStatus {
    SUCCESS(1),
    ERROR(2),
    CREATED(3),
    PROCESSING(4),
    SKIPPED(5),
	READY_TO_PROCESS(6);

    private int id;

    ProcessingStatus(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
