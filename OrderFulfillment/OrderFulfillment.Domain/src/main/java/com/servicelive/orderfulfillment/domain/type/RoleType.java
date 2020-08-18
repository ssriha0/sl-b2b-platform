package com.servicelive.orderfulfillment.domain.type;

/**
 * Definitions taken from MarketCommon OrderConstants
 */
public enum RoleType {
    Provider(1),
    SLAdmin(2),
    Buyer(3),
    SimpleBuyer(5),
//    OTHER_WEBSITE_OR_FORUM(5),
    System(6),
    SLAdminCompany(9),
    SimpleBuyerCompany(50),
    Unknown(-1);

    private int id;

    RoleType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RoleType fromId(int id) {
        switch (id) {
            case 1:
                return Provider;
            case 2:
                return SLAdmin;
            case 3:
                return Buyer;
            case 5:
                return SimpleBuyer;
            case 6:
                return System;
            case 9:
                return SLAdminCompany;
            case 50:
                return SimpleBuyerCompany;
            default:
                return Unknown;
        }
    }
}
