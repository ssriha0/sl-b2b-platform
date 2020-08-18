package com.servicelive.orderfulfillment.validation;

import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidationUtil implements IValidationUtil {
    private static final Pattern emailPattern = Pattern.compile("^[\\w!#$%&*+/=?`{|}~^-]+(?:\\.[\\w!#$%&*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private static final Pattern phonePattern = Pattern.compile("(\\d{10})");
    private static final Pattern usZipPattern = Pattern.compile("(\\d{5})");

    public IValidationUtil addErrors(ValidationHolder validationHolder, ProblemType ... problemTypes) {
        addErrorsToHolder(validationHolder, problemTypes);
        return this;
    }

    public IValidationUtil addWarnings(ValidationHolder validationHolder, ProblemType ... problemTypes) {
        addWarningsToHolder(validationHolder, problemTypes);
        return this;
    }

    public IValidationUtil addErrorsIfNull(Object obj, ValidationHolder validationHolder, ProblemType ... problemTypes) {
        if (obj == null) {
            addErrorsToHolder(validationHolder, problemTypes);
        }
        return this;
    }

    public IValidationUtil addWarningsIfNull(Object obj, ValidationHolder validationHolder, ProblemType ... problemTypes) {
        if (obj == null) {
            addWarningsToHolder(validationHolder, problemTypes);
        }
        return this;
    }

    public IValidationUtil addErrorsIfTrue(boolean addErr, ValidationHolder validationHolder, ProblemType ... problemTypes) {
        if (addErr) {
            addErrorsToHolder(validationHolder, problemTypes);
        }
        return this;
    }

    public IValidationUtil addWarningsIfTrue(boolean addWarn, ValidationHolder validationHolder, ProblemType ... problemTypes) {
        if (addWarn) {
            addWarningsToHolder(validationHolder, problemTypes);
        }
        return this;
    }

    public IValidationUtil addErrorsIfBlank(String str, ValidationHolder validationHolder, ProblemType ... problemTypes) {
        if (StringUtils.isBlank(str)) {
            addErrorsToHolder(validationHolder, problemTypes);
        }
        return this;
    }

    public IValidationUtil addWarningsIfBlank(String str, ValidationHolder validationHolder, ProblemType ... problemTypes) {
        if (StringUtils.isBlank(str)) {
            addWarningsToHolder(validationHolder, problemTypes);
        }
        return this;
    }

    public IValidationUtil addErrorIfStringTooLong(String str, int maxLen, ValidationHolder validationHolder, ProblemType problemType) {
        if (str != null && str.length() > maxLen) {
            addErrorsToHolder(validationHolder, problemType);
        }
        return this;
    }

    public IValidationUtil addWarningIfStringTooLong(String str, int maxLen, ValidationHolder validationHolder, ProblemType problemType) {
        if (str != null && str.length() > maxLen) {
            addWarningsToHolder(validationHolder, problemType);
        }
        return this;
    }

    public IValidationUtil addErrorIfEmailNotValid(String email, ValidationHolder validationHolder) {
        if (StringUtils.isNotBlank(email) && !emailPattern.matcher(email).matches()) {
            addErrorsToHolder(validationHolder, ProblemType.EmailNotValid);
        }
        return this;
    }

    public IValidationUtil addErrorIfPhoneNotValid(String phone, ValidationHolder validationHolder, ProblemType problemType) {
        if (StringUtils.isNotBlank(phone) && !phonePattern.matcher(phone).matches()) {
            addErrorsToHolder(validationHolder, problemType);
        }
        return this;
    }

    public IValidationUtil addErrorIfUSZipNotValid(String zip, ValidationHolder validationHolder, ProblemType problemType) {
        if (StringUtils.isNotBlank(zip) && !usZipPattern.matcher(zip).matches()) {
            addErrorsToHolder(validationHolder, problemType);
        }
        return this;
    }

    public IValidationUtil addWarningIfZero(BigDecimal amount, ValidationHolder validationHolder, ProblemType problemType) {
        if(amount.equals(PricingUtil.ZERO)){
            addWarnings(validationHolder, problemType);
        }
        return this;
    }
    
    public IValidationUtil addWarningIfLessThanOne(BigDecimal amount, ValidationHolder validationHolder, ProblemType problemType) {
    	if (amount.compareTo(PricingUtil.ONE_SCALED_WITH_4) < 0) {
            addWarnings(validationHolder, problemType);
        }
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////
    // PRIVATE HELPER METHODS
    ////////////////////////////////////////////////////////////////////////////

    private void addErrorsToHolder(ValidationHolder validationHolder, ProblemType ... problemTypes) {
        for (ProblemType problemType : problemTypes) {
            validationHolder.addError(problemType);
        }
    }

    private void addWarningsToHolder(ValidationHolder validationHolder, ProblemType ... problemTypes) {
        for (ProblemType problemType : problemTypes) {
            validationHolder.addWarning(problemType);
        }
    }
}
