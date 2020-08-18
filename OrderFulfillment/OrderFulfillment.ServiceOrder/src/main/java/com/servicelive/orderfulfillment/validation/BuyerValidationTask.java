package com.servicelive.orderfulfillment.validation;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;

public class BuyerValidationTask extends AbstractValidationTask {
    public void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
        util.addErrorsIfNull(serviceOrder.getBuyerId(), validationHolder, ProblemType.MissingBuyerId);
    }
}
