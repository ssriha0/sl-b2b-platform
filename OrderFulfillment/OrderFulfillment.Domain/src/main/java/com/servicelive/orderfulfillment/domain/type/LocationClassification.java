package com.servicelive.orderfulfillment.domain.type;

public enum LocationClassification {
    COMMERCIAL(1),
    RESIDENTIAL(2),
    ;

    private int id;

    LocationClassification(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LocationClassification fromId(int id) {
        switch (id){
            case 1:
                return COMMERCIAL;
            case 2:
                return RESIDENTIAL;
            default:
                throw new IllegalArgumentException("Invalid LocationClassification id.");
        }
    }
}
