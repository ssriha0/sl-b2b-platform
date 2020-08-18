package com.servicelive.orderfulfillment.validation;

import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;

public class ServiceLocationValidationTask extends AbstractValidationTask {
    public void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
        SOLocation svcLocation =  serviceOrder.getServiceLocation();
        util.addErrorsIfNull(svcLocation, validationHolder, ProblemType.MissingContactLocation);
        util.addWarningsIfNull(svcLocation, validationHolder, 
                ProblemType.MissingStreetName, ProblemType.MissingCity, ProblemType.MissingStateLoc);

        if (svcLocation != null) {
            util.addWarningsIfBlank(svcLocation.getStreet1(), validationHolder, ProblemType.MissingStreetName)
            	.addErrorIfStringTooLong(svcLocation.getStreet1(), 100, validationHolder, ProblemType.StreetLengthExceeds100)
                .addWarningIfStringTooLong(svcLocation.getStreet2(), 30, validationHolder, ProblemType.Street2LengthExceeds30)
                .addWarningsIfBlank(svcLocation.getCity(), validationHolder, ProblemType.MissingCity)
                .addErrorIfStringTooLong(svcLocation.getCity(), 30, validationHolder, ProblemType.CityLengthExceeds30)
                .addWarningsIfBlank(svcLocation.getState(), validationHolder, ProblemType.MissingStateLoc)
                .addErrorsIfBlank(svcLocation.getZip(), validationHolder, ProblemType.InvalidUSZip)
                .addErrorIfUSZipNotValid(svcLocation.getZip(), validationHolder, ProblemType.InvalidUSZip);
        }
    }
}
