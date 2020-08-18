package com.servicelive.orderfulfillment.domain.type;

/**
 * @author Mustafa Motiwala
 * @since Feb 25, 2010 11:39:15 AM
 */
public enum PhoneClassification {
    HOME(0),
    WORK(1),
    MOBILE(2),
    PAGER(4),
    OTHER(5),
    FAX(6)
    ;
    
    private int id;

    private PhoneClassification(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public static PhoneClassification fromId(int id){
        switch(id){
            case 0:
                return HOME;
            case 1:
                return WORK;
            case 2:
                return MOBILE;
            case 4:
                return PAGER;
            case 5:
                return OTHER;
            case 6:
                return FAX;
            default:
                throw new IllegalArgumentException("Invalid id specified for PhoneClassification.");
        }
    }
}
