package com.servicelive.orderfulfillment.domain.type;

import javax.xml.bind.annotation.XmlEnum;

/**
 * @author Mustafa Motiwala
 * @since Feb 26, 2010 5:39:37 PM
 */
@XmlEnum(String.class)
public enum ContactType {
    PRIMARY(10),
    ALTERNATE(20);

    private int id;

    ContactType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ContactType fromId(int contactTypeId) {
        switch (contactTypeId) {
            case 10: return PRIMARY;
            case 20: return ALTERNATE;
            default: throw new IllegalArgumentException("Invalid ID specified for ContactType!");
        }
    }
}
