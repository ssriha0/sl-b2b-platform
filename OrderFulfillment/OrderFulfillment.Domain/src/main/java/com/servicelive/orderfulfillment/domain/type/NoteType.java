package com.servicelive.orderfulfillment.domain.type;

public enum NoteType {
    SUPPORT(1),
    GENERAL(2),
    TYPE3(3),
    ;

    private int id;

    NoteType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static NoteType fromId(int id) {
        switch (id){
            case 1:
                return SUPPORT;
            case 2:
                return GENERAL;
            case 3:
                return TYPE3;
            default:
                throw new IllegalArgumentException("Invalid note type id.");
        }
    }
}
