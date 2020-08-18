package com.servicelive.orderfulfillment.domain.type;

/**
 * Maps to lu_so_provider_resp table. Values updated there should
 * be updated here.
 * @author Mustafa Motiwala
 *
 */
public enum ProviderResponseType{
    ACCEPTED(1),
    CONDITIONAL_OFFER(2),
    REJECTED(3),
    WITHDRAW_CONDITIONAL_OFFER(4),
    RELEASED(5),
    EXPIRED(6),
    RELEASED_BY_FIRM(7);
    
    private int id;
    
    private ProviderResponseType(int id){
        this.id=id;
    }
    
    public int getId(){
        return id;
    }
    
    public static ProviderResponseType fromId(int id){
        switch(id){
        case 1:
            return ACCEPTED;
        case 2:
            return CONDITIONAL_OFFER;
        case 3:
            return REJECTED;
        case 4:
            return WITHDRAW_CONDITIONAL_OFFER;
        case 5:
            return RELEASED;
        case 6:
        	return EXPIRED;
        case 7:
        	return RELEASED_BY_FIRM;
        default:
                throw new IllegalArgumentException("Illegal value passed for ID in ProviderResponseType: " + id);
        }
    }
}
