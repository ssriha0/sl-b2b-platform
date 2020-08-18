package com.servicelive.orderfulfillment.validation;

import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import org.apache.commons.lang.StringUtils;

import java.util.Set;

public class ServiceContactValidationTask extends AbstractValidationTask {
    public void validate(ServiceOrder serviceOrder, ValidationHolder validationHolder) {
        SOContact svcContact = serviceOrder.getServiceContact();

        util.addWarningsIfNull(svcContact, validationHolder, ProblemType.MissingFirstName, ProblemType.MissingLastName, ProblemType.MissingPhone);
        if (svcContact != null) {
            validateSvcContactElements(svcContact, validationHolder);
        }

    }

    private void validateSvcContactElements(SOContact svcContact, ValidationHolder validationHolder) {
        util.addErrorIfStringTooLong(svcContact.getBusinessName(), 100, validationHolder, ProblemType.BusinessNameLengthExceeds100);
        String svcContactFirstNm = svcContact.getFirstName();
        String svcContactLastNm = svcContact.getLastName();
        String svcContactEmail = svcContact.getEmail();
        util.addWarningsIfNull(svcContactFirstNm, validationHolder, ProblemType.MissingFirstName)
        	.addWarningsIfBlank(svcContactFirstNm, validationHolder, ProblemType.MissingFirstName)
            .addErrorIfStringTooLong(svcContactFirstNm, 50, validationHolder, ProblemType.FirstNameLengthExceeds50)
            .addWarningsIfNull(svcContactLastNm, validationHolder, ProblemType.MissingLastName)
            .addWarningsIfBlank(svcContactLastNm, validationHolder, ProblemType.MissingLastName)
            .addErrorIfStringTooLong(svcContactLastNm, 50, validationHolder, ProblemType.LastNameLengthExceeds50)
            .addErrorIfStringTooLong(svcContactEmail, 255, validationHolder, ProblemType.EmailLengthExceeds255)
            .addErrorIfEmailNotValid(svcContactEmail, validationHolder);

        if (svcContact.getPhones() != null) {
            validateSvcContactPhones(svcContact.getPhones(), validationHolder);
        }
    }

    private void validateSvcContactPhones(Set<SOPhone> svcPhones, ValidationHolder validationHolder) {
        String primaryPhone = null;
        String alternatePhone = null;
        String fax = null;

        for (SOPhone phone : svcPhones) {
            if (phone.getPhoneTypeId() == null) {
                util.addErrors(validationHolder, ProblemType.UnknownPhoneType);
            } else {
                switch (phone.getPhoneType()) {
                    case PRIMARY:
                        primaryPhone = phone.getPhoneNo();
                        break;
                    case ALTERNATE:
                        alternatePhone = phone.getPhoneNo();
                        break;
                    case FAX:
                        fax = phone.getPhoneNo();
                        break;
                }
            }
            
            if (phone.getPhoneClass() == null) {
            	util.addErrors(validationHolder, ProblemType.UnknownPhoneClass);
        }
        }

        util.addErrorIfPhoneNotValid(primaryPhone, validationHolder, ProblemType.InvalidPrimaryPhone);
        util.addErrorIfPhoneNotValid(alternatePhone, validationHolder, ProblemType.InvalidAlternatePhone);
        util.addErrorIfPhoneNotValid(fax, validationHolder, ProblemType.InvalidFaxNumber);

        if (StringUtils.isBlank(primaryPhone) && StringUtils.isBlank(alternatePhone)) {
            util.addErrors(validationHolder, ProblemType.MissingContactPhone);
        }
    }
}
