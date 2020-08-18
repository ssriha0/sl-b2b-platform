package com.servicelive.orderfulfillment.domain.type;

import javax.xml.bind.annotation.XmlEnum;

/**
 * @author Mustafa Motiwala
 * @since Feb 26, 2010 5:11:30 PM
 */
@XmlEnum(String.class)
public enum LocationType {
    SERVICE(10),
    BUYER_SUPPORT(30),
    PICKUP(40),
    PROVIDER(50),
    BUYER_ASSOCIATION(60)
    ;

    private int id;
    private LocationType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LocationType fromId(int locationTypeId) {
        switch (locationTypeId) {
            case 10: return SERVICE;
            case 30: return BUYER_SUPPORT;
            case 40: return PICKUP;
            case 50: return PROVIDER;
            case 60: return BUYER_ASSOCIATION;
            default: throw new IllegalArgumentException("Invalid ID given for LocationType!");
        }
    }
}
