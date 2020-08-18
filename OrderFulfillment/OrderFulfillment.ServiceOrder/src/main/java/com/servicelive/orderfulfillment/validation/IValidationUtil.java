package com.servicelive.orderfulfillment.validation;

import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;

import java.math.BigDecimal;

public interface IValidationUtil {
    IValidationUtil addErrors(ValidationHolder validationHolder, ProblemType ... problemTypes);
    IValidationUtil addWarnings(ValidationHolder validationHolder, ProblemType ... problemTypes);
    IValidationUtil addErrorsIfNull(Object obj, ValidationHolder validationHolder, ProblemType ... problemTypes);
    IValidationUtil addWarningsIfNull(Object obj, ValidationHolder validationHolder, ProblemType ... problemTypes);
    IValidationUtil addErrorsIfTrue(boolean addErr, ValidationHolder validationHolder, ProblemType ... problemTypes);
    IValidationUtil addWarningsIfTrue(boolean addWarn, ValidationHolder validationHolder, ProblemType ... problemTypes);
    IValidationUtil addErrorsIfBlank(String str, ValidationHolder validationHolder, ProblemType ... problemTypes);
    IValidationUtil addWarningsIfBlank(String str, ValidationHolder validationHolder, ProblemType ... problemTypes);
    IValidationUtil addErrorIfStringTooLong(String str, int maxLen, ValidationHolder validationHolder, ProblemType problemType);
    IValidationUtil addWarningIfStringTooLong(String str, int maxLen, ValidationHolder validationHolder, ProblemType problemType);
    IValidationUtil addErrorIfEmailNotValid(String email, ValidationHolder validationHolder);
    IValidationUtil addErrorIfPhoneNotValid(String phone, ValidationHolder validationHolder, ProblemType problemType);
    IValidationUtil addErrorIfUSZipNotValid(String zip, ValidationHolder validationHolder, ProblemType problemType);
    IValidationUtil addWarningIfZero(BigDecimal amount, ValidationHolder validationHolder, ProblemType problemType);
    IValidationUtil addWarningIfLessThanOne(BigDecimal amount, ValidationHolder validationHolder, ProblemType problemType);
}
