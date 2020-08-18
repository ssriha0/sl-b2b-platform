package com.servicelive.orderfulfillment.domain.type;

import javax.xml.bind.annotation.XmlEnum;

/**
 * @author Mustafa Motiwala
 * @since Feb 26, 2010 6:39:02 PM
 */
@XmlEnum(String.class)
public enum ContactLocationType {
    SERVICE(10),
    END_USER(20),
    BUYER_SUPPORT(30),
    PICKUP(40),
    PROVIDER(50),
    BUYER_ASSOCIATION(60);

    public static ContactLocationType fromId(int locationTypeId) {
        switch (locationTypeId) {
            case 10: return SERVICE;
            case 20: return END_USER;
            case 30: return BUYER_SUPPORT;
            case 40: return PICKUP;
            case 50: return PROVIDER;
            case 60: return BUYER_ASSOCIATION;
            default: throw new IllegalArgumentException("Invalid ID specified for ContactLocationType");
        }
    }

    private int id;

    private ContactLocationType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}