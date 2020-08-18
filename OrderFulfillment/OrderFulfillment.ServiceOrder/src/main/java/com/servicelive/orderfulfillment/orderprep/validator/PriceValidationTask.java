package com.servicelive.orderfulfillment.orderprep.validator;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.validation.AbstractValidationTask;
import com.servicelive.orderfulfillment.domain.ValidationHolder;

public class PriceValidationTask extends AbstractValidationTask {
    public void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
        //util.addErrorsIfNull(serviceOrder.getRetailPrice(), validationHolder, ProblemType.NoRetailPrice);
        util.addWarningIfLessThanOne(serviceOrder.getTotalSpendLimit(), validationHolder, ProblemType.NoRetailPrice);
    }
}
