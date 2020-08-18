package com.servicelive.orderfulfillment.domain.type;

/**
 * @author Mustafa Motiwala
 * @since Mar 5, 2010 12:06:52 PM
 */
public enum EntityType {
    UNKNOWN(0),
    BUYER(10),
    PROVIDER(20),
    MARKETPLACE(30)
    ;

    private int id;

    private EntityType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EntityType fromId(int contactTypeId) {
        switch (contactTypeId) {
            case 0: return UNKNOWN;
            case 10: return BUYER;
            case 20: return PROVIDER;
            case 30: return MARKETPLACE;
            default: throw new IllegalArgumentException("Invalid ID specified for ContactType!");
        }
    }
}
