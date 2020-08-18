package com.servicelive.orderfulfillment.validation;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;

public interface IServiceOrderValidationTask {
    void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder);
}
