package com.servicelive.orderfulfillment.validation;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;

public interface IServiceOrderValidator {
    void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder);
}
