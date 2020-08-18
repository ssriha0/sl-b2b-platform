package com.servicelive.orderfulfillment.domain.type;

/**
 * Based on values in the table: lu_so_provider_resp_reason
 * The values for this enum type are restricted for resp_reason of
 * Conditional Offer.
 * Query to use:
 * SELECT rr.resp_reason_id,rr.descr
 *      FROM lu_so_provider_resp_reason rr
 *          JOIN lu_so_provider_resp r ON r.provider_resp_id=rr.provider_resp_id
 *      WHERE r.descr='Conditional Offer';
 * @author Mustafa Motiwala
 *
 */
public enum ConditionalOfferReason {
    RESCHEDULE_SERVICE_DATE(7),
    SERVICE_DATE_AND_SPEND_LIMIT(8),
    SPEND_LIMIT(9);
    
    private int id;
    
    private ConditionalOfferReason(int id){
        this.id=id;
    }
    
    public int getId(){
        return id;
    }
    
    public static ConditionalOfferReason fromId(int id){
        switch (id) {
        case 7:
            return RESCHEDULE_SERVICE_DATE;
        case 8:
            return SERVICE_DATE_AND_SPEND_LIMIT;
        case 9:
            return SPEND_LIMIT;
        default:
            throw new IllegalArgumentException("Invalid id given for ConditionalOfferReason: " + id);
        } 
    }
}
