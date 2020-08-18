package com.servicelive.orderfulfillment.orderprep.validator;

import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;

public class PartWithReferenceIdValidationTask extends BasePartValidationTask {
    @Override
    protected void validatePart(ValidationHolder validationHolder, SOPart part) {
        super.validatePart(validationHolder, part);
        util.addWarningsIfBlank(part.getReferencePartId(), validationHolder, ProblemType.MissingPartReferenceId);
    }
}
