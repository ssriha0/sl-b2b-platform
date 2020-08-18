package com.servicelive.orderfulfillment.validation;

public abstract class AbstractValidationTask implements IServiceOrderValidationTask {
    protected IValidationUtil util;

    public void setUtil(IValidationUtil util) {
        this.util = util;
    }
}
