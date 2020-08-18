package com.servicelive.orderfulfillment.domain.type;

/**
 * @author Mustafa Motiwala
 * @since Feb 25, 2010 11:33:41 AM
 */
public enum PhoneType {
    PRIMARY(1),
    ALTERNATE(2),
    FAX(3)
    ;

    private int databaseId=0;

    private PhoneType(int id){
        databaseId = id;
    }

    public int getId(){
        return databaseId;
    }

    public static PhoneType fromId(int id){
        switch(id){
            case 1:
                return PRIMARY;
            case 2:
                return ALTERNATE;
            case 3:
                return FAX;
            default:
                throw new IllegalArgumentException("Invalid argument passed.");
        }
    }
}
