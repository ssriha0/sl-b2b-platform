package com.servicelive.orderfulfillment.orderprep.validator;

import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.validation.AbstractValidationTask;

public class BasePartValidationTask extends AbstractValidationTask {
    public void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
        if (serviceOrder.getParts() == null) return;
        for (SOPart part : serviceOrder.getParts()) {
            validatePart(validationHolder, part);
        }
    }

    protected void validatePart(ValidationHolder validationHolder, SOPart part) {
        util.addWarningsIfBlank(part.getManufacturer(), validationHolder, ProblemType.MissingPartManufacturer);
        util.addWarningsIfBlank(part.getModelNumber(), validationHolder, ProblemType.MissingPartModelNumber);
    }
}
