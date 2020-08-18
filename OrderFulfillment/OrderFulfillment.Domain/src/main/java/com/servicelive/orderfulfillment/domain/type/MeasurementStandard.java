package com.servicelive.orderfulfillment.domain.type;

public enum MeasurementStandard {
    ENGLISH(1, "U.S. (in./lbs)"),
    METRIC(2, "Metric (m/kg)"),
    ;

    Integer id;
    String description;

    MeasurementStandard(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public MeasurementStandard parse(Integer id) {
        switch (id) {
            case 1: return ENGLISH;
            case 2: return METRIC;
            default: throw new IllegalArgumentException("Unknown measurement standard ID.");
        }
    }
}
