package com.servicelive.orderfulfillment.orderprep.validator;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.validation.AbstractValidationTask;

public class OrderHdrValidationTask extends AbstractValidationTask {
    public void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
        util.addErrorsIfBlank(serviceOrder.getSowTitle(), validationHolder, ProblemType.MissingOrderTitle);
        util.addErrorIfStringTooLong(serviceOrder.getSowTitle(), 255, validationHolder, ProblemType.OrderTitleLengthExceeds255);
        //Sl-20728 
        util.addErrorIfStringTooLong(serviceOrder.getProviderInstructions(), 10000, validationHolder, ProblemType.ProviderInstructionsLengthExceeds10000);
    }
}